package skcnc.stockcore.ca.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import skcnc.framework.common.AppCommonController;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.txmang.TxDbConnect;
import skcnc.stockcore.ca.controller.vo.TxInVO;
import skcnc.stockcore.ca.controller.vo.TxOutVO;
import skcnc.stockcore.ca.service.TimeTaken;
import skcnc.stockcore.ca.service.vo.TimeTakenInVO;
import skcnc.stockcore.ca.service.vo.TimeTakenOutVO;

@Api(tags = "업무공통 Controller" )
@RestController
@RequestMapping("test")
public class TestController extends AppCommonController{

	@Autowired
	TimeTaken timeTaken;
	
	@Operation(summary = "Test 서비스", description = "성능 테스트용 테스트 서비스")
	@PostMapping("/timetaken")
	public AppResponse<TimeTakenOutVO> prcsTrade(@RequestBody AppRequest<TimeTakenInVO> inData)
	{
		return timeTaken.prcsTrade(inData);
	}
	
	@Operation(summary = "sendtest", description = "API call 용 테스트1")
	@PostMapping("/sendtest")
	public AppResponse<TimeTakenOutVO> sendTest(@RequestBody AppRequest<TimeTakenInVO> inData)
	{
		TimeTakenOutVO outVo = TimeTakenOutVO.builder().norl_prcs_yn("Y").build();
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData.getHead(), outVo, "MYOK1001", "Test가" );
	}
	
	@Operation(summary = "recvtest", description = "API call 용 테스트2")
	@PostMapping("/recvtest")
	public AppResponse<TimeTakenOutVO> recvTest(@RequestBody AppRequest<TimeTakenInVO> inData)
	{
		TimeTakenOutVO outVo = TimeTakenOutVO.builder().norl_prcs_yn("Y").build();
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData.getHead(), outVo, "MYOK1001", "Test가" );
	}
}
