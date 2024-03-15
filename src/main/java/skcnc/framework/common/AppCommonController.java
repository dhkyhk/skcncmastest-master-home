package skcnc.framework.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppResponse;
import skcnc.framework.txmang.TxDbConnect;
import skcnc.framework.txmang.TxMangMain;

public class AppCommonController {

    @Autowired
    private FileMessageSource message;

    @Autowired
    @Qualifier("sessionFactory")
    private SqlSessionFactory sessionFactory;
    
    @Autowired
    private TxMangMain txmang;
    
    public AppCommonController() {
    	AppHeader ch = ContextStoreHelper.getData(AppHeader.ATTR_KEY, AppHeader.class );
    	
    	TxDbConnect clientTx = txmang.getTxDb();
    	
    	boolean bStart = true;
    	if ( !"Y".equals(ch.getStartyn()) ) {
    		bStart = false;
    	}
    	clientTx.setGuid(ch.getGuid(), bStart);

    	ContextStoreHelper.setData( ContextStoreHelper.TX_CLIENT , clientTx );
    	ContextStoreHelper.setData( ContextStoreHelper.TX_SESSION, clientTx.getSession() );
    }
    
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
