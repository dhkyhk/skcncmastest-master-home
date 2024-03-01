package skcnc.framework.common;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import skcnc.framework.database.MetaCommonInterceptor;
import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppRequest;
import skcnc.framework.utils.ServletUtil;

@RestControllerAdvice
//@Slf4j
public class AppCommonRequestControllerAdvice implements RequestBodyAdvice {
    @Value("${spring.profiles.active}")
    String serverTp;

    static final String FILE_INOUT = "FILE_INOUT";

    @Override
    public Object afterBodyRead(Object arg0, HttpInputMessage arg1, MethodParameter arg2, Type arg3,
                                Class<? extends HttpMessageConverter<?>> arg4) {

        var appRequest = ((AppRequest<?>) arg0);
        var auth = SecurityContextHolder.getContext().getAuthentication();

        AppCommonRequestControllerAdvice.beforeProcess(appRequest, auth);
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
                .body(body)
                .build();
        beforeProcess(inData);
        return inData;
    }

    public static void beforeProcess(AppRequest<?> appRequest) {
        beforeProcess(appRequest, null);
    }

    public static void beforeProcess(AppRequest<?> appRequest, Authentication auth) {

        var ch = appRequest.getHead();
        if (ch == null) {
            ch = new AppHeader();
            appRequest.setHead(ch);
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
        ContextStoreHelper.setData(MetaCommonInterceptor.FW_OPR_ID    , StringUtils.mid(ch.getOpr_id() , 0, 20) );
        //ContextStoreHelper.setData(MetaCommonInterceptor.FW_OPR_TRD_ID, StringUtils.mid(ch.getScr_no(), 0, 8));
        ContextStoreHelper.setData(MetaCommonInterceptor.FW_OPR_TRD_ID, "");
        ContextStoreHelper.setData(MetaCommonInterceptor.FW_OPR_TMN_ID, StringUtils.mid(ch.getIp(), 0, 20));
        ContextStoreHelper.setData("OCSID.ACTION", ch.getGuid());

        //헤더에 mydata_id 설정
        if(auth == null) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        if(auth != null) {
            if(!"anonymousUser".equals(auth.getName())) {
                ch.setMsa_id(auth.getName());
            }
        }
    }
}
