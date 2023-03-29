package skcnc.msa3.framework.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import skcnc.msa3.framework.model.AppHeader;
import skcnc.msa3.framework.model.AppRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.core.MethodParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import org.slf4j.MDC;
import skcnc.msa3.framework.utils.ServletUtil;

@RestControllerAdvice
@Slf4j
public class AppCommonRequestControllerAdvice implements RequestBodyAdvice {
    @Value("${spring.profiles.active}")
    String serverTp;

    static final String FILE_INOUT = "FILE_INOUT";

    //클래스 + 메소드명으로 메소드를 저장하는 캐시
    private static final Map<String, Method> voMethodsCache = new ConcurrentReferenceHashMap<>(256);

    //@Autowired
    //DbioMapper dbioMapper;

    @Override
    public Object afterBodyRead(Object arg0, HttpInputMessage arg1, MethodParameter arg2, Type arg3,
                                Class<? extends HttpMessageConverter<?>> arg4) {

        var appRequest = ((AppRequest<?>) arg0);
        var auth = SecurityContextHolder.getContext().getAuthentication();

        AppCommonRequestControllerAdvice.beforeProcess(appRequest, auth);

        //myd_cust_id 체크(헤더와 바디부의 데이터 일치하는가?, 계정은 유효한가?, 중복 로그인은 아닌가? 등)
        checkMsa_id(appRequest.getCh(), appRequest.getData(), auth);

        return arg0;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage arg0, MethodParameter arg1, Type arg2,
                                           Class<? extends HttpMessageConverter<?>> arg3) throws IOException {
        return arg0;
    }

    @Override
    public Object handleEmptyBody(Object arg0, HttpInputMessage arg1, MethodParameter arg2, Type arg3,
                                  Class<? extends HttpMessageConverter<?>> arg4) {
        return arg0;
    }

    @Override
    public boolean supports(MethodParameter arg0, Type arg1, Class<? extends HttpMessageConverter<?>> arg2) {
        return AppRequest.class.isAssignableFrom(arg0.getParameterType());
    }

    /**
     * AppRequest 제네릭 클래스의 VO를 기준으로 전처리를 한다.
     * - 공통 헤더 처리
     * - logging 용 추척id 설정
     * - 클라이언트 ip 획득 후 공통헤더에 설정
     * - mydata_id 를 SpringSecurity 에서 추출하여 공통헤더에 설정
     * @param body
     */
    public static <T> AppRequest<T> beforeProcess(T body) {
        AppRequest<T> inData = AppRequest.<T>builder()
                .data(body)
                .build();
        beforeProcess(inData);
        return inData;
    }

    public static void beforeProcess(AppRequest<?> appRequest) {
        beforeProcess(appRequest, null);
    }

    public static void beforeProcess(AppRequest<?> appRequest, Authentication auth) {

        var ch = appRequest.getCh();
        if (ch == null) {
            ch = new AppHeader();
            appRequest.setCh(ch);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = "not_found";
        if ( request != null ) {
            url = request.getRequestURI().replaceAll("/", ".");
            ContextStoreHelper.setData( ContextStoreHelper.API_URL_KEY, url );

            //클라이언트 Ip 설정 - 호출시 강제로 client IP 설정하도록 수정.
            String ip = ServletUtil.getUserIp(request);
            ch.setIp(ip);
        }

        //헤더에 추척 아이디 설정 및 메타 공통 처리
        if(StringUtils.isBlank(ch.getGuid()))
            ch.setGuid(RandomStringUtils.randomAlphanumeric(16));

        //추적 아이디를 logger 공통적으로 사용되도록 설정
        MDC.put("GUID", ch.getGuid());
        MDC.put("TRACE_ID", url );

        Logger inoutLog = LoggerFactory.getLogger( FILE_INOUT );
        inoutLog.debug( "{} input  : {} ", url.substring(1), appRequest );

        //요청 헤더 보관 처리
        ContextStoreHelper.setData(AppHeader.ATTR_KEY, ch);

        ContextStoreHelper.initLog();
        Logger log = ContextStoreHelper.getLog();
        log.debug( "ch : {}", ch );
        log.debug( "url : {}", url );

        //TODO : 항목이 정해진 후 MetaCommonInterceptor 수정 후 주석 제거하자.
        //메타 공통 전처리로 조작거래코드(oprt_tr_cd 에 화면 번호를 넣어준다, 12자리만 가능)
        //ContextStoreHelper.setData(MetaCommonInterceptor.FW_COMMON_KEY_OPRT_STFNO, null);
        //ContextStoreHelper.setData(MetaCommonInterceptor.FW_COMMON_KEY_OPRT_TMNO, null);
        //ContextStoreHelper.setData(MetaCommonInterceptor.FW_COMMON_KEY_TR_CD, StringUtils.mid(ch.getScr_no(), 0, 12));
        //ContextStoreHelper.setData("OCSID.ACTION", ch.getGuid());

        //헤더에 mydata_id 설정
        /*if(auth == null) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        if(auth != null) {
            if(!"anonymousUser".equals(auth.getName())) {
                ch.setMsa_id(auth.getName());
            }
        }*/
    }

    /**
     * myd_cust_id 계정 체크
     * - 계정 데이터 일치 체크 : 헤더의 토큰에 있는 값과 VO 바디에 있는 값이 일치하는가 체크
     * - 계정은 현재 유효한 상태인가?
     * - 중복 로그인되어 있지 않은지?
     */
    private void checkMsa_id(AppHeader ch, Object data, Authentication auth) {

        if(ch == null || StringUtils.isBlank(ch.getMsa_id()) || data == null) return;
        var attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //사용자 계정이 비어 있으면 점검안함
        if(StringUtils.isBlank(ch.getMsa_id())) return;

        //noauth 로 인증 불필요한 주소일 때는 체크 안함.
        boolean isNoauth = attr.getRequest().getRequestURI().startsWith("/demo1/controller001/login.do");

        //#1. 계정 데이터 일치 체크
        /*if(!isNoauth) {
            Object result = null;
            Method m = findMethod(data.getClass(), "getMyd_cust_id");
            if(m != null) {
                //VO에 myd_cust_id 가 정의되어 있고 값이 있을 때 체크한다.
                try {
                    result = m.invoke(data);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    result = null;
                }
                if(result != null && result instanceof String) {
                    String id = (String) result;
                    if(StringUtils.isNotBlank(id)) {
                        if(!id.equals(ch.getMsa_id())) {

                            log.error("ID불일치(인증ID={}, 전문의 myd_cust_id={}) ==> 해킹시도 가능성 확인필요!!!", ch.getMsa_id(), id );
                            throw AppCommonException.create("MYER9003", ""); //MYER9003=잘못된 요청입니다.
                        }
                    }
                }
            }
        }*/

        if(!isNoauth) {
            /* 일단 필요 없는것 같아 주석 처리함.
            //#2. 계정은 현재 유효한 상태인가?
            MetaHashMap param = new MetaHashMap();
            Map<String, Object> qryOut = new MetaHashMap();

            param.put("myd_cust_id", ch.getMydata_id());
            param.put("mac", ch.getMac());

            //아래부분의 쿼리 및 결과에 따른 유효성 체크하시고 알맞는 AppCommonException을 throw 하세요.
            try{
                //아래부분의 쿼리 및 결과에 따른 유효성 체크하시고 알맞는 AppCommonException을 throw 하세요.
                //String queryId = "mapper.appapi.common.fw.selectOneMYSV001M001";
                //DB query 실행
                //qryOut = dbioMapper.select(queryId, param);

                String cust_svc_join_stat_cd = MapUtil.getString(qryOut, "cust_svc_join_stat_cd");	//고객서비스가입상태코드

                if(cust_svc_join_stat_cd != null) {

                    if(!"01".equals(cust_svc_join_stat_cd))
                    {
                        txLog.error("고객서비스가입상태가 정상이 아닙니다!!!", ch.getMydata_id());
                        throw AppCommonException.create("MYER9006", "");
                    }
                } else {
                    txLog.error("MYD고객ID가 존재하지 않습니다!!!", ch.getMydata_id());
                    throw AppCommonException.create("MYER9006", "");
                }

            } catch (RuntimeException e) {
                throw AppCommonException.create("MYER9006", ""); //MYER9006=고객서비스가입상태가 정상이 아닙니다.
            }*/

            //#3. 중복 기기 체크 (동일고객이 각각 다른 2개 이상의 단말로 로그인을 했을 경우, 최근 로그인을 한 단말을 제외한 나머지 단말들은 오류응답을 리턴해줌
            if("prod".equals(serverTp)) {	//local / dev / qa  / prod / dr / 로컬 개발 테스트 운영 DR 순
                Date tokenIssuedAt = null;	//토큰 최초 발급시간
                if(auth != null) {
                    //TODO : 주석 제거하고 수정하자..
                    //AuthUserDetailVO userDetail = (AuthUserDetailVO) auth.getDetails();
                    //if(userDetail != null) tokenIssuedAt = userDetail.getTokenIssuedAt();
                }
            }

            /*try{
                //#4. cano 비교(서비스고객기본TB cano vs API InVO cano)
                Object result = null;
                Method m = findMethod(data.getClass(), "getCano");
                if(m != null) {

                    //String myd_cano = MapUtil.getString(qryOut, "cano");	//계좌번호

                    // 2022.01.03 윤형일 - IRP 계좌개설 + 위탁계좌개설 계좌 2개 개설이 가능하므로 계좌 개설 시에는 아예 cano 비교 체크를 안하도록 함.
                    boolean isAcntOpenAdmn = attr.getRequest().getRequestURI().startsWith("/appapi/suppadmn/ivstlnkd/acntopenadmn");
                    boolean isAcntOpenInqr = attr.getRequest().getRequestURI().startsWith("/appapi/suppadmn/ivstlnkd/acntopeninqr");
                    boolean isPrsfCtfcAdmn = attr.getRequest().getRequestURI().startsWith("/appapi/suppadmn/ivstlnkd/prsfctfcadmn");

                    if(!isAcntOpenAdmn && !isAcntOpenInqr && !isPrsfCtfcAdmn) {
                        if(StringUtils.isNotBlank(myd_cano)) {	//1원인증 등 계좌개설 중에는 InVO로 cano가 넘어오는데, DB에는 아직생성되지 않았기 때문에 예외처리해줌
                            try {
                                result = m.invoke(data);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                result = null;
                            }
                            if(result != null && result instanceof String) {
                                String cano = (String) result;
                                if(StringUtils.isNotBlank(cano)) {
                                    if(!cano.equals(myd_cano)) {
                                        //txLog.error("cano 불일치(myd_cust_id cano={}, 전문의 cano={})", myd_cano, cano );
                                        throw AppCommonException.create("MYER9007", ""); //MYER9007=계좌정보와 사용자정보가 상이하여 업무처리가 불가능합니다.
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                throw AppCommonException.create("MYER9007", ""); //MYER9007=계좌정보와 사용자정보가 상이하여 업무처리가 불가능합니다.
            }*/

        }
    }

    //파라미터 없는 메소드 찾기
    //리플렉션으로 인한 성능 개선을 위해 캐시 사용하여 반환하도록 함.
    private Method findMethod(Class<?> clazz, String methodName) {
        Method m = null;
        String key = clazz.getName() + "::" + methodName;
        if (voMethodsCache.containsKey(key)) {
            m = voMethodsCache.get(key);
        }else {
            m = ReflectionUtils.findMethod(clazz, methodName);
            voMethodsCache.put(key, m);
        }

        return m;
    }
}
