package skcnc.framework.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class ContextStoreHelper {

    public static final String API_LOG_NAME = "api_log";
    public static final String API_URL_KEY = "api_url";

    private static final ThreadLocal<Map<String,Object>> store = new NamedInheritableThreadLocal<>("ContextStore");
    private static final ThreadLocal<Logger> log = new ThreadLocal<>();

    public static void initLog() {
        Logger logger = LoggerFactory.getLogger( API_LOG_NAME );
        log.set (logger);
    }

    public static Logger getLog() {
        if ( log.get() == null ){

            String url = ContextStoreHelper.getData( API_URL_KEY, String.class );

            if ( url == null || url.isEmpty() ){
                var attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if ( attr != null ) {
                    HttpServletRequest request = attr.getRequest();
                    url = request.getRequestURI().replaceAll("/", ".");
                    ContextStoreHelper.setData( ContextStoreHelper.API_URL_KEY, url );
                }
            }

            Logger logger = LoggerFactory.getLogger( API_LOG_NAME );
            log.set (logger);
        }
        return log.get();
    }

    /**
     * 공유데이터 설정
     * @param key 입력할 key 값
     * @param value 입력할 데이타 object
     */
    public static void setData(String key, Object value) {
        var map = store.get();
        if(map == null) {
            map = new HashMap<String,Object>();
            store.set(map);
        }
        map.put(key, value);
    }

    /**
     * 공유데이터 획득
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T getData(String key, Class<T> clazz) {
        return getData(key, clazz, null);
    }

    /**
     * @Method Name : getData
     * @description : 공유데이터 획득
     * - 스토어 저장된 key의 값을 반환, key 데이터가 없는 경우 defaultValue 반환
     * @param <T>
     * @param key
     * @param clazz
     * @param defaultValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getData(String key, Class<T> clazz, Object defaultValue) {
        var map = store.get();
        if(map == null) return (T) defaultValue;
        Object value = map.get(key);
        if(value == null) return (T) null;
        return (T) value;
    }

    /**
     * 공유데이터 참조를 모두 복사한다(얕은 복사)
     * @return
     */
    public static Map<String,Object> copyAllData() {
        return new HashMap<>(store.get());
    }

    /**
     * 공유데이터 파괴(참조만 제거함)
     */
    public static void clear() {
        store.remove();
        log.remove();
    }
}
