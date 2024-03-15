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

@Service
@Transactional
public class DemoService1 extends AppCommonService {

	@Autowired
	ApiSendModule apiSendModule;
	
	public AppResponse<DemoVO> procTest1(@RequestBody AppRequest<DemoVO> inData){
		
		Logger log = ContextStoreHelper.getLog();
		log.debug( "*** DEMO1 START ***" );
		
		
		apiSendModule.callRestApi( "http://localhost:8081/demo2/test2", inData );
		
		DemoVO outVo = new DemoVO();
		
		return makeResponse(inData, outVo, "MYOK1001", "DEMO" );
	}
}
