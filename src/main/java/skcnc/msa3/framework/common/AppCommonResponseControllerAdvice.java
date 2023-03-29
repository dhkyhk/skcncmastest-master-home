package skcnc.msa3.framework.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import skcnc.msa3.framework.model.AppHeader;
import skcnc.msa3.framework.model.AppResponse;

@RestControllerAdvice
public class AppCommonResponseControllerAdvice implements ResponseBodyAdvice<AppResponse<?>> {

    static final String FILE_INOUT = "FILE_INOUT";

    @Override
    public AppResponse<?> beforeBodyWrite(AppResponse<?> body, MethodParameter returnType, MediaType selectedContentType,
                                          Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return AppCommonResponseControllerAdvice.afterProcess(body);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AppResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * AppResponse 제네릭 클래스의 VO를 기준으로 후처리를 한다.
     * - 공통 헤더 설정
     * @param body
     * @return
     */
    public static AppResponse<?> afterProcess(AppResponse<?> body) {
        //응답에 공통 헤더 설정
        try {
            var ch = body.getCh();
            var reqCh = ContextStoreHelper.getData(AppHeader.ATTR_KEY, AppHeader.class);

            if(ch == null) {
                ch = AppHeader.builder().build();
                body.setCh(ch);
            }

            ch.setTr_type("S"); //R:request, S:response

            if(reqCh != null) {
                if(StringUtils.isBlank(ch.getIp())) ch.setIp(reqCh.getIp());
                if(StringUtils.isBlank(ch.getMac())) ch.setMac(reqCh.getMac());
                if(StringUtils.isBlank(ch.getGuid())) ch.setGuid(reqCh.getGuid());
                if(StringUtils.isBlank(ch.getScr_no())) ch.setScr_no(reqCh.getScr_no());
                if(StringUtils.isBlank(ch.getTr_cont())) ch.setTr_cont(reqCh.getTr_cont());
                if(StringUtils.isBlank(ch.getHts_id())) ch.setHts_id(reqCh.getHts_id());
                if(StringUtils.isBlank(ch.getSphn_os_dvsn_cd())) ch.setSphn_os_dvsn_cd(reqCh.getSphn_os_dvsn_cd());
            }

        }catch(Exception ex) {
            Logger log = ContextStoreHelper.getLog();
            log.error("공통 헤더 설정 오류", ex);
        }

        Logger inoutLog = LoggerFactory.getLogger( FILE_INOUT );
        String url = ContextStoreHelper.getData( ContextStoreHelper.API_URL_KEY, String.class);
        inoutLog.debug( "{} output : {} ", url.substring(1) , body  );

        ContextStoreHelper.clear();

        return body;
    }
}
