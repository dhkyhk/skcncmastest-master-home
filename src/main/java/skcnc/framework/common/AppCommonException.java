package skcnc.framework.common;

import org.springframework.context.ApplicationContext;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import skcnc.framework.model.AppHeader;

@Builder
@Data
@ToString
public class AppCommonException extends RuntimeException {
    private static final long serialVersionUID = -4982348093873946553L;
    private static FileMessageSource messageSource = null;
    private static Object messageSourceLock = new Object();

    private String rtCd;
    private String msgCd;
    private String msg;
    private Throwable cause;

    @Override
    public String getMessage() {
        return "[" + msgCd + "]" + msg + (cause != null ? ", cause = [" + cause.getMessage() + "]" : "");
    }

    /**
     * @Method Name : create
     * @description : AppCommonException 생성자 helper
     * @param msgCd
     * @param msgArgs
     * @return
     */
    public static AppCommonException create(String msgCd, Object...msgArgs) {
        return create((Throwable)null, AppHeader.RT_FAIL, msgCd, msgArgs);
    }

    public static AppCommonException create(Throwable cause, String rtCd, String msgCd, Object...msgArgs) {

        if(messageSource == null) {
            synchronized (messageSourceLock) {
                if(messageSource == null) {
                    ApplicationContext context = ApplicationContextProvider.getApplicationContext();
                    messageSource = context.getBean(FileMessageSource.class);
                }
            }
        }
        String newMsgCd = messageSource.parseMessageCode(msgCd);
        String msg = "";
        if(newMsgCd.equals(msgCd)) {
            msg = messageSource.getMessage(msgCd, msgArgs);
        }else {
            msg = messageSource.parseMessage(msgArgs);
        }

        return AppCommonException.builder()
                .rtCd(rtCd)
                .msgCd(newMsgCd)
                .msg(msg)
                .cause(cause)
                .build();
    }
}
