package skcnc.msa3.framework.common;

import org.springframework.beans.factory.annotation.Autowired;
import skcnc.msa3.framework.model.AppHeader;
import skcnc.msa3.framework.model.AppResponse;

public class AppCommonController {

    @Autowired
    private FileMessageSource message;

    /**
     * @Method Name : makeResponse
     * @description : 응답 생성(ch로 응답 헤더 생성)
     * @param <T>     : 응답 데이터 본문
     * @param ch      : 응답 데이터 헤더에 설정할 공통 헤더 객체
     * @param outData : 응답 데이터 본문
     * @param msgCd   : 응답 메시지 코드
     * @param msgArgs : 응답 메시지에 설정할 파라미터(nullable)
     * @return
     */
    protected <T> AppResponse<T> makeResponse(AppHeader ch, T outData, String msgCd, Object...msgArgs) {
        String newMsgCd = message.parseMessageCode(msgCd);
        String msg = "";
        if(newMsgCd.equals(msgCd)) {
            msg = message.getMessage(msgCd, msgArgs);
        }else {
            msg = message.parseMessage(msgArgs);
        }

        return AppResponse.create(ch,
                AppHeader.RT_SUCCESS,
                newMsgCd,
                msg,
                outData);
    }
}
