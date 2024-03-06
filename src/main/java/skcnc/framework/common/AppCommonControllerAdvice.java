package skcnc.framework.common;

import java.net.BindException;

import org.slf4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppResponse;
import skcnc.framework.utils.HexUtil;

//@RestControllerAdvice(basePackages = {GlobalConfig.BASE_PACKAGE + ".appapi", GlobalConfig.BASE_PACKAGE + ".demoapi", GlobalConfig.BASE_PACKAGE + ".framework"})
@RestControllerAdvice
public class AppCommonControllerAdvice {

    @Autowired
    private FileMessageSource messageSource;

    /**
     * @Method Name : handleCommonException
     * @description : 개발자에 의해 핸들링된 Exception으로 응답 생성
     * @return
     */
    @ExceptionHandler(AppCommonException.class)
    public AppResponse<?> handleCommonException(AppCommonException ex) {
        Logger log = ContextStoreHelper.getLog();
        log.debug("Handled Exception :: {}", ex.getMessage()); //, ex); //핸들링된 오류이므로 stacktrace 출력 안함
        //오류 응답을 헤더에만 실어서 보낸다. body 에는 null로 상세 오류를 반환하지 않도록 한다.
        return AppResponse.create((AppHeader) null, ex.getRtCd(), ex.getMsgCd(), ex.getMsg(), null);
    }

    /**
     * @Method Name : handleUnhandledExceptiopn
     * @description : 개발자에 의해 핸들링되지 않은 Exception을 잡아서 오류 처리 후 응답 생성
     * @return
     */
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public AppResponse<?> handleUnhandledExceptiopn(Exception ex, WebRequest request) {
        Logger log = ContextStoreHelper.getLog();
        log.error("### Unhandled Exception ###\n@request : {}", request, ex);
        String msgCd = "MYER0007";//처리중 오류가 발생했습니다.
        return AppResponse.create((AppHeader) null, AppHeader.RT_FAIL, msgCd, messageSource.getMessage(msgCd), null);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class,
            MissingServletRequestParameterException.class, ServletRequestBindingException.class,
            ConversionNotSupportedException.class, TypeMismatchException.class, HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class, MethodArgumentNotValidException.class, NoHandlerFoundException.class,
            MissingServletRequestPartException.class, BindException.class})
    protected AppResponse<?> handleBadRequest(Exception ex, WebRequest request) {

        byte[] bytes = null;
        Logger log = ContextStoreHelper.getLog();

        if(ex instanceof HttpMessageNotReadableException) {
            try {
                var cacheReq = ((ServletWebRequest)request).getNativeRequest(ContentCachingRequestWrapper.class);
                bytes = cacheReq.getContentAsByteArray();
            } catch (RuntimeException e) {
                bytes = null; //nothing todo
            }
            if(bytes == null) bytes = "Payload_Read_Error".getBytes();
            log.error("### BadRequest Exception ###\n@request : {}\n{}", request, HexUtil.bytesToPrettyString(bytes), ex);
        }else {
            log.error("### BadRequest Exception ###\n@request : {}", request, ex);
        }

        String msgCd = "MYER0008"; //잘못된 요청입니다.
        return AppResponse.create((AppHeader) null, AppHeader.RT_FAIL, msgCd, messageSource.getMessage(msgCd), null);
    }
    
    /*
    @Around(value = "execution(* *.*Controller)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    	
    	Logger log = LoggerFactory.getLogger("INOUT_TIME");
    	
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Object result = pjp.proceed();
        
        stopWatch.stop();
        long elapsedTime = stopWatch.getTotalTimeMillis();
        
    	log.debug( "소요시간 : ", elapsedTime);
       
        return result;
    }*/
}
