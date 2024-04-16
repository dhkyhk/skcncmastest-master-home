package skcnc.demo.demo2;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import skcnc.demo.demo1.vo.DemoVO;
import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;

@Service
@Transactional
public class DemoService2 extends AppCommonService {

	public AppResponse<DemoVO> procTest2(@RequestBody AppRequest<DemoVO> inData){
		
		Logger log = ContextStoreHelper.getLog();
		log.debug( "*** DEMO2 START ***" );
		
		initTxManager();
		
		try {
			Map<String, Object> insMap = new MetaHashMap(); 
			insMap.put( "cif", "1000000001"); 
			
			//dbio.insert( "mapper.demo2.cad4000mapper.insertcad4000one", insMap );
			//dbio.insert( "mapper.ca.timetakenmapper.insertcad4000", insMap );
			
			clientTx.insert( "mapper.ca.timetakenmapper.insertcad4000", insMap );
			
			Thread.sleep( 10 * 1000 );
			
			//DemoVO outVo = new DemoVO();
			//return makeResponse(inData, outVo, "MYOK1001", "DEMO" );
			
			throw makeException("MYER0005", "TX테스트");
			
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
