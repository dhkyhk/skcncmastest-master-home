package skcnc.demo.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import skcnc.demo.demo1.vo.DemoVO;
import skcnc.framework.common.AppCommonController;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;

@Api(tags = "Demo Controller" )
@RestController
@RequestMapping("demo1")
public class DemoController1 extends AppCommonController{

	@Autowired
	DemoService1 demoService1;
	
	@Operation(summary = "Demo1", description = "Demo1")
	@PostMapping("/test1")
	public AppResponse<DemoVO> procTest1(@RequestBody AppRequest<DemoVO> inData)
	{
		//initTxManager();
		return demoService1.procTest1(inData);
		//return makeResponse(inData.getHead(), inData.getBody(), "MYOK1001", "DEMO" );
	}
	
}
