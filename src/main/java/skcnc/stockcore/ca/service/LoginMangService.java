package skcnc.stockcore.ca.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skcnc.framework.common.AppCommonException;
import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.security.JwtTokenProvider;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.common.EmpInfoMangModule;
import skcnc.stockcore.ca.common.LoginMangModule;
import skcnc.stockcore.ca.dao.table.Caa1000Table;
import skcnc.stockcore.ca.service.vo.LoginInVO;
import skcnc.stockcore.ca.service.vo.LoginOutVO;

@Service
@Transactional
public class LoginMangService extends AppCommonService {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	EmpInfoMangModule empinfoMang;
	
	@Autowired
	LoginMangModule loginMangModule;
	
	public AppResponse<LoginOutVO> procLogin(AppRequest<LoginInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
		
		try { 
			LoginOutVO outVo = new LoginOutVO();
			
			if ( StringUtils.isEmpty(inData.getBody().getEmp_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "직원번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEnc_pwd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "비밀번호");
			}
			
			Caa1000Table caa100 = empinfoMang.getEmpInfoInqry( inData.getBody().getEmp_no() );
			/*
			if ( !"01".equals( caa100.getHofe_dcd() ) ) {
				log.error( "재직 상태를 확인하세요 : {0}", caa100 );
				//MYER0004={0} 값을 확인하세요.
				throw makeException("MYER0004", "재직상태를");
			}
			
			if ( caa100.getPwd_err_cnt() >= 5 ) {
				log.error( "비밀번호 오류 횟수를 확인하세요 : {0}", caa100 );
				//MYER0004={0} 값을 확인하세요.
				throw makeException("MYER0004", "비밀번호 오류 횟수를");
			}*/
			
			boolean rv = loginMangModule.pwdCheck(inData.getBody().getEmp_no(),  inData.getBody().getEnc_pwd());
			
			if ( !rv ) {
				log.error( "비밀번호 입력 오류 : {} / {}", inData.getBody().getEnc_pwd(), caa100.getEnc_pwd() );
				//MYER2011={0}아닙니다
				throw makeException("MYER2011", "등록된 비밀번호가 ");
			}
			
			/*
			if ( !inData.getData().getEnc_pwd().equals(caa100.getEnc_pwd()) ) { 
				log.error( "비밀번호 입력 오류 : {0} / {1}", inData.getData().getEnc_pwd(), caa100.getEnc_pwd() );
				//MYER2011={0}아닙니다
				throw makeException("MYER2011", "등록된 비밀번호가 ");
			}*/
			
			BeanUtils.copyProperties(caa100, outVo);
			
			//토큰 생성
			String jwtToken = jwtTokenProvider.createToken( inData.getBody().getEmp_no(), Arrays.asList("USER"));
			outVo.setToken(jwtToken);
			
			log.error( "처리완료 되었습니다. : {}", outVo );
			
			//MYOK1001={0} 정상 처리 되었습니다 
			return makeResponse(inData, outVo, "MYOK1001", "로그인" );
			
		} catch ( AppCommonException e ) {
			throw e;
		} catch ( Exception e ) {
			log.error( "ERROR : ", e );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}
	}
}
