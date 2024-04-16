package skcnc.demo.demo1;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import skcnc.demo.demo1.vo.DemoVO;
import skcnc.framework.apicall.ApiSendModule;
import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.txmang.TxDbConnect;

@Service
@Transactional
public class DemoService1 extends AppCommonService {

	@Autowired
	ApiSendModule apiSendModule;
	
	public AppResponse<DemoVO> procTest1(@RequestBody AppRequest<DemoVO> inData){
		
		Logger log = ContextStoreHelper.getLog();
		log.debug( "*** DEMO1 START ***" );
		
		initTxManager();
		
		try {
			
			//TxDbConnect clientTx = ContextStoreHelper.getData( ContextStoreHelper.TX_CLIENT, TxDbConnect.class );
			this.clientTx.callSubApi( "http://localhost:8081/" , "demo2/test2", inData);
			//apiSendModule.callRestApi( "http://localhost:8081/demo2/test2", inData );

			log.debug( "test2 호출 성공!!" );
			
			Thread.sleep( 10 * 1000 );
			
			log.debug( "*** DEMO1 END ***" );
			
			DemoVO outVo = new DemoVO();
			return makeResponse(inData, outVo, "MYOK1001", "DEMO" );
			
			//throw makeException("MYER0005", "TX테스트");
			
		} catch ( InterruptedException e ) {
			log.error( "ERROR", e );
			throw makeException("MYER0005", "TX테스트");
		} catch (RuntimeException e ){
			log.error( "ERROR", e );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException("MYER0005", "TX테스트");
		}
	}
}
