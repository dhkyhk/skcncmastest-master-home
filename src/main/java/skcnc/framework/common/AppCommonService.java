package skcnc.framework.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skcnc.framework.database.DbioMapperbatis;
import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.utils.MapperUtil;

public abstract class AppCommonService {
    @Autowired
    protected FileMessageSource message;

    //core DB만.
    @Autowired
    protected DbioMapperbatis dbio;

	//@PersistenceContext
	//private EntityManager em;
    
	//@Autowired
	//#private EntityManagerFactory emf;

	//@Autowired
	//private JpaTransactionManager transactionManager;
	
    /**
     * @Method Name : makeException
     * @description : 실패 응답시 실패 사유에 해당하는 Exception을 만들어 반환한다.
     * @param msgCd : 응답 메시지 코드
     * @param args  : 응답 메시지 코드에 해당하는 파라미터(nullable)
     * @return
     */
    protected AppCommonException makeException(String msgCd, Object...args) {
        return AppCommonException.create(msgCd, args);
    }
    protected AppCommonException makeException(Throwable ex, String msgCd, Object...args) {
        return AppCommonException.create(ex, AppHeader.RT_FAIL, msgCd, args);
    }

    /**
     * @Method Name : makeResponse
     * @description : 응답 생성(요청의 공통 헤더를 사용하여 응답 공통 헤더 설정)
     * @param <T>     : 응답 데이터 클래스 타입
     * @param outData : 응답 데이터
     * @param msgCd   : 응답 메시지 코드
     * @param msgArgs : 응답 메시지에 설정할 파라미터(nullable)
     * @return
     */
    protected <T> AppResponse<T> makeResponse(T outData, String msgCd, Object...msgArgs) {
        return makeResponse((AppHeader)null, outData, msgCd, msgArgs);
    }

    /**
     * @Method Name : makeResponse
     * @description : 응답 생성(ch로 응답 헤더 생성)
     * @param <T>     : 응답 데이터 클래스 타입
     * @param ch      : 응답 데이터 헤더에 설정할 공통 헤더 객체
     * @param outData : 응답 데이터
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

    /**
     * @Method Name : makeResponse
     * @description : 응답 생성(inData.ch 로 응답 헤더 설정됨)
     * @param <T>     : 응답 데이터 클래스 타입
     * @param inData  : 요청시 받은 데이터(공통 헤더 설정시 사용)
     * @param outData : 응답 데이터
     * @param msgCd   : 응답 메시지 코드
     * @param msgArgs : 응답 메시지에 설정할 파라미터(nullable)
     * @return
     */
    protected <T> AppResponse<T> makeResponse(AppRequest<?> inData, T outData, String msgCd, Object...msgArgs) {
        var respCh = MapperUtil.convert(inData.getHead(), AppHeader.class);
        return makeResponse(respCh, outData, msgCd, msgArgs);
    }

    /**
     * @Method Name : getAppHeader
     * @description : 클라이언트 요청시의 AppHeader 정보를 반환한다.
     * @return
     */
    protected AppHeader getAppHeader() {
        return ContextStoreHelper.getData(AppHeader.ATTR_KEY, AppHeader.class);
    }
}
