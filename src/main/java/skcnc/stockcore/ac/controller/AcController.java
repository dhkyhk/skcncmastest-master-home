package skcnc.stockcore.ac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import skcnc.framework.common.AppCommonController;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.stockcore.ac.service.AcInfoCrt;
import skcnc.stockcore.ac.service.CifInfoCrt;
import skcnc.stockcore.ac.service.vo.AcInfoCrtInVO;
import skcnc.stockcore.ac.service.vo.AcInfoCrtOutVO;
import skcnc.stockcore.ac.service.vo.CifInfoCrtInVO;
import skcnc.stockcore.ac.service.vo.CifInfoCrtOutVO;

@Api(tags = "계좌업무 Controller" )
@RestController
@RequestMapping("ac")
public class AcController extends AppCommonController{

	@Autowired
	CifInfoCrt cifInfoCrt;
	
	@Autowired
	AcInfoCrt ccInfoCrt;
	
	@Operation(summary = "고객등록", description = "고객정보등록(ACA1000)")
	@PostMapping("/cif_regi")
	public AppResponse<CifInfoCrtOutVO> procCifRegi(@RequestBody AppRequest<CifInfoCrtInVO> inData)
	{
		return cifInfoCrt.procCifRegi(inData);
	}
	
	@Operation(summary = "계좌개설", description = "계좌개설(ACB1000)")
	@PostMapping("/acno_regi")
	public AppResponse<AcInfoCrtOutVO> procAcRegi(@RequestBody AppRequest<AcInfoCrtInVO> inData)
	{
		return ccInfoCrt.procAcRegi(inData);
	}
}
