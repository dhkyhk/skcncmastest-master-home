package skcnc.demo.demo2;

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
@RequestMapping("demo2")
public class DemoController2 extends AppCommonController{

	@Autowired
	DemoService2 demoService2;
	
	@Operation(summary = "Demo2", description = "Demo2")
	@PostMapping("/test2")
	public AppResponse<DemoVO> procTest2(@RequestBody AppRequest<DemoVO> inData)
	{
		return demoService2.procTest2(inData);
		//return makeResponse(inData.getHead(), inData.getBody(), "MYOK1001", "DEMO" );
	}
	
}
