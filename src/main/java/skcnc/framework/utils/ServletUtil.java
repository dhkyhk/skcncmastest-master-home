package skcnc.framework.utils;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class ServletUtil {
	
	/**
     * @Method Name : getUserIp
     * @description : 서버에 접근한 사용자 IP 획득(현재 쓰레드의 요청 헤더에서 ip 추출)
     * @return
     */
    public static String getUserIp() {
        var attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attr == null) return null;
        return getUserIp(attr.getRequest());
    }


    /**
     * @Method Name : getUserIp
     * @description : 서버에 접근한 사용자 IP 획득(req 헤더에서 ip 추출)
     * @param req
     * @return
     */
    public static String getUserIp(HttpServletRequest req) {
        if(req == null) return null;
        String ip = null;
        
        ip = req.getHeader("X-Forwarded-For");
        if(ip != null) return ip;

        ip = req.getHeader("Proxy-Client-IP");
        if(ip != null) return ip;

        ip = req.getHeader("WL-Proxy-Client-IP"); //웹로직
        if(ip != null) return ip;

        ip = req.getHeader("HTTP_CLIENT_IP");
        if(ip != null) return ip;

        ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        if(ip != null) return ip;

        return req.getRemoteAddr();
    }
}
