package skcnc.msa3.framework.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FileMessageSource {

    //정의되어 있는 메세지 코드를 사용하지 않고 입력받은 파라미터로 설정하기 위한 용도로
    // pre fix 값을 앞에 붙여서 입력 받음.
    private static final String BYPASS_PREFIX = "BYPASS:";

    @Autowired
    MessageSource message;

    @Value("${spring.profiles.active}")
    String activeProfiles;

    public String getMessage(String code) {
        return message.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object...args) {
        String msg = null;
        if(code.startsWith(BYPASS_PREFIX)) {
            return args[0].toString();
        }

        try {
            msg = message.getMessage(code, args, LocaleContextHolder.getLocale());
        }catch(NoSuchMessageException ex) {
            Logger log = ContextStoreHelper.getLog();
            log.error("### 정의되지 않은 메시지코드 입니다. => \"{}\"", code);
            //로컬 및 개발에서는 Exception을 throw 시켜서 메시지 코드 확인하도록 한다.
            if("local".equals(activeProfiles) || "dev".equals(activeProfiles)) {
                throw ex;
            }
        }
        if(msg == null) {
            return code;
        }
        return msg;
    }

    /**
     * @Method Name : parseMessageCode
     * @description : 메시지소스의 메시지 코드를 사용하지 않고 바로 리턴가능한 메시지코드를 반환한다.
     * @param code
     * @return
     */
    public String parseMessageCode(String code) {
        if(code.startsWith(BYPASS_PREFIX)) {
            String newCode = code.substring(BYPASS_PREFIX.length());
            if(StringUtils.isAlphanumeric(newCode)) {
                return newCode;
            }else {
                return "INVALID_CODE";
            }
        }else {
            return code;
        }
    }

    /**
     * @Method Name : parseMessage
     * @description : 메시지소스의 메시지코드를 사용하지 않고 바로 리턴할 데이터가 있을 때 해당 파라미터에서 실제 메시지를 뽑아온다.
     * @param args
     * @return
     */
    public String parseMessage(Object... args) {
        if(args != null && args.length > 0 && args[0] != null) {
            return args[0].toString();
        }else {
            return "";
        }
    }
}
