package skcnc.stockcore.ca.service;

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
import skcnc.framework.utils.DateUtil;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.common.DtMangModule;
import skcnc.stockcore.ca.common.EmpInfoMangModule;
import skcnc.stockcore.ca.dao.table.Caa1000Table;
import skcnc.stockcore.ca.service.vo.EmpRegiInVO;
import skcnc.stockcore.ca.service.vo.EmpRegiOutVO;

@Service
@Transactional
public class EmpRegiService extends AppCommonService {

	@Autowired
	EmpInfoMangModule empinfoMang;
	
	//@Autowired
	//PasswordEncoder passwordEncoder;
	
	@Autowired
	DtMangModule dtMang;
	
	public AppResponse<EmpRegiOutVO> procEmpRegi(AppRequest<EmpRegiInVO> inData) 
	{
		Logger log = ContextStoreHelper.getLog();
		
		try  {
			
			if ( StringUtils.isEmpty(inData.getBody().getWrk_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEmp_nm()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "직원명");
			}
			if ( StringUtils.isEmpty(inData.getBody().getMail()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "이메일");
			}
			if ( StringUtils.isEmpty(inData.getBody().getTel_area_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "전화지역번호");
			}
			if ( StringUtils.isEmpty(inData.getBody().getTel_ptcl_area_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "전화국번호");
			}
			if ( StringUtils.isEmpty(inData.getBody().getEnc_tel_nd_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "전화일련번호");
			}
			if ( StringUtils.isEmpty(inData.getBody().getDept_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "부서번호");
			}
			
			//TODO : 나중에 확인이 필요한 부분이다..
			/*if ( inData.getData().getEnc_pwd().length() < 10 ) {
				String pwd = passwordEncoder.encode( inData.getData().getEnc_pwd() );
				inData.getData().setEnc_pwd(pwd);
			}*/
			
			EmpRegiOutVO outVo = new EmpRegiOutVO();
			outVo.setEmp_no(inData.getBody().getEmp_no());
			
			if ( "I".equals(inData.getBody().getWrk_dcd()) ) 
			{
				Caa1000Table caa100Vo = new Caa1000Table(); 
				BeanUtils.copyProperties(inData.getBody(), caa100Vo);
				
				String emp_no = inData.getBody().getEmp_nm();
				if ( StringUtils.isEmpty(emp_no) )  {
					emp_no = empinfoMang.getEmpMaxNo();
					caa100Vo.setEmp_no(emp_no);
				}
				
				caa100Vo.setHofe_dcd( "01" ); //재직
				String dt = dtMang.getBsnDt("01");
				caa100Vo.setJoco_dt( dt );
				caa100Vo.setRetco_dt( "" );
				caa100Vo.setPwd_regi_dt( dt );
				
				outVo.setEmp_no(emp_no);
				
				empinfoMang.procEmpInfoRegi( caa100Vo );
			}
			else if ( "U".equals(inData.getBody().getWrk_dcd()) ) 
			{
				if ( StringUtils.isEmpty(inData.getBody().getEmp_no()) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "직원번호");
				}
				
				if ( StringUtils.isEmpty(inData.getBody().getHofe_dcd()) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "재직구분코드");
				}
				
				if ( StringUtils.isEmpty(inData.getBody().getJoco_dt()) || DateUtil.isValidDate( inData.getBody().getJoco_dt() ) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "입사일자");
				}
				
				if ( !StringUtils.isEmpty(inData.getBody().getRetco_dt()) && DateUtil.isValidDate( inData.getBody().getRetco_dt() ) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "퇴사일자");
				}
				
				if ( !StringUtils.isEmpty(inData.getBody().getRetco_dt()) ) {
					if ( !"02".equals(inData.getBody().getHofe_dcd()) ) {
						log.error( "입력값을 확인하세요 : {}", inData );
						//MYER0003=입력값 {0}를 확인하세요.
						throw makeException("MYER0003", "재직구분");
					}
				}
				
				Caa1000Table caa100Vo = new Caa1000Table(); 
				BeanUtils.copyProperties(inData.getBody(), caa100Vo);
				
				empinfoMang.procEmpInfoChg( caa100Vo );
			} 
			else 
			{
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}
			
			
			Caa1000Table caa100 = empinfoMang.getEmpInfoInqry( inData.getBody().getEmp_no() );
			
			if ( caa100 == null || StringUtils.isEmpty(caa100.getEmp_no()) ) {
				log.error( "해당 직원정보가 존재하지 않습니다. : {}", inData );
				//MYER0041={0}가 존재하지 않습니다.
				throw makeException("MYER0041", "직원정보");
			}
			
			outVo.setNorl_prcs_yn("Y");
			log.error( "처리완료 되었습니다. : {}", outVo );
			
			//MYOK1001={0} 정상 처리 되었습니다 
			return makeResponse(inData, outVo, "MYOK1001", "직원정보" );
		} catch ( AppCommonException e ) {
			throw e;
		} catch ( Exception e ) {
			log.error( "ERROR : ", e );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}
	}
}
