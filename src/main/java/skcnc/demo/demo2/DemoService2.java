package skcnc.demo.demo2;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import skcnc.demo.demo1.vo.DemoVO;
import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;

@Service
@Transactional
public class DemoService2 extends AppCommonService {

	public AppResponse<DemoVO> procTest2(@RequestBody AppRequest<DemoVO> inData){
		
		Logger log = ContextStoreHelper.getLog();
		log.debug( "*** DEMO2 START ***" );
		
		DemoVO outVo = new DemoVO();
		
		return makeResponse(inData, outVo, "MYOK1001", "DEMO" );
	}
}
