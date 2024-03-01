package skcnc.stockcore.ca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import skcnc.framework.common.AppCommonController;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.security.JwtTokenProvider;
import skcnc.stockcore.ca.service.DtMangService;
import skcnc.stockcore.ca.service.EmpInqryService;
import skcnc.stockcore.ca.service.EmpRegiService;
import skcnc.stockcore.ca.service.LoginMangService;
import skcnc.stockcore.ca.service.NotfMangService;
import skcnc.stockcore.ca.service.vo.DtCrtInVO;
import skcnc.stockcore.ca.service.vo.DtCrtOutVO;
import skcnc.stockcore.ca.service.vo.EmpInqryInVO;
import skcnc.stockcore.ca.service.vo.EmpInqryOutVO;
import skcnc.stockcore.ca.service.vo.EmpRegiInVO;
import skcnc.stockcore.ca.service.vo.EmpRegiOutVO;
import skcnc.stockcore.ca.service.vo.LoginInVO;
import skcnc.stockcore.ca.service.vo.LoginOutVO;
import skcnc.stockcore.ca.service.vo.LogoutInVO;
import skcnc.stockcore.ca.service.vo.LogoutOutVO;
import skcnc.stockcore.ca.service.vo.PrcsNotfInVO;
import skcnc.stockcore.ca.service.vo.PrcsNotfOutVO;

//@Api(tags = "로그인관리" )
@RestController
@RequestMapping("ca")
public class CaController extends AppCommonController{

	@Autowired
	EmpInqryService empInqry;
	
	@Autowired
	EmpRegiService empRegi;
	
	@Autowired
	DtMangService dtMangService;

	@Autowired
	LoginMangService loginMangService;

	@Autowired
	NotfMangService notfMangService;
	
	@Operation(summary = "직원조회", description = "직원정보 조회(CAA1000)")
	@PostMapping("/inqry")
	public AppResponse<EmpInqryOutVO> getEmpInfo(@RequestBody AppRequest<EmpInqryInVO> inData) 
	{
		return empInqry.getEmpInfo(inData);
	}
	
	@Operation(summary = "직원등록", description = "직원정보 등록(CAA1000)")
	@PostMapping("/regi")
	public AppResponse<EmpRegiOutVO> procEmpRegi(@RequestBody AppRequest<EmpRegiInVO> inData)
	{
		return empRegi.procEmpRegi(inData);
	}
	
	@Operation(summary = "당일등록", description = "당일기본 일자 생성(CAD1100)")
	@PostMapping("/dtcrt")
	public AppResponse<DtCrtOutVO> dtCrt(@RequestBody AppRequest<DtCrtInVO> inData)
	{
		return dtMangService.dtCrt(inData);
	}
	
	@Operation(summary = "로그인처리", description = "로그인 처리 JWT 토큰 생성 X-AUTH-TOKEN 값을 쿠키에도 저장\n AC1001U1")
	@PostMapping("/auth/login")
	public AppResponse<LoginOutVO> procLogin(@RequestBody AppRequest<LoginInVO> inData, HttpServletResponse response)
	{
		AppResponse<LoginOutVO> out = loginMangService.procLogin(inData);
		
		//swagger 에서 JWT 토큰 값을 설정해 놓기가 힘들어서 쿠키에다 강제로 설정. 사용하자..
    	Cookie cookie = new Cookie(JwtTokenProvider.TOKEN_KEY_NAME, out.getBody().getToken());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
    	response.addCookie( cookie );
		
		return out;
	}
	
	@Operation(summary = "로그아웃처리", description = "로그아웃 처리 JWT 토큰이 저장된 쿠키 삭제")
	@PostMapping("/auth/logout")
	public AppResponse<LogoutOutVO> procLogout(@RequestBody AppRequest<LogoutInVO> inData, HttpServletResponse response)
	{
		LogoutOutVO outVo = LogoutOutVO.builder().emp_no( inData.getBody().getEmp_no() ).build();
		
		//로그 아웃 처리를 위해 쿠키 클리어.
    	Cookie cookie = new Cookie(JwtTokenProvider.TOKEN_KEY_NAME, "");
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
    	response.addCookie( cookie );
    	
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData.getHead(), outVo, "MYOK1001", "로그아웃" );
	}

	@Operation(summary = "카카오로 메시지 전송", description = "카카오로 메시지 전송")
	@PostMapping("/kakaosend")
	public AppResponse<PrcsNotfOutVO> prcsNotf(@RequestBody AppRequest<PrcsNotfInVO> inData)
	{
		return notfMangService.prcsNotf(inData);
	}
}
