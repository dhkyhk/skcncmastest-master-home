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
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.common.EmpInfoMangModule;
import skcnc.stockcore.ca.dao.table.Caa1000Table;
import skcnc.stockcore.ca.service.vo.EmpInqryInVO;
import skcnc.stockcore.ca.service.vo.EmpInqryOutVO;

@Service
@Transactional
public class EmpInqryService extends AppCommonService {

	@Autowired
	EmpInfoMangModule empinfoMang;
	
	public AppResponse<EmpInqryOutVO> getEmpInfo(AppRequest<EmpInqryInVO> inData) 
	{
		Logger log = ContextStoreHelper.getLog();
		
		try  {
			if ( StringUtils.isEmpty(inData.getBody().getEmp_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "직원번호");
			}
			
			Caa1000Table caa100 = empinfoMang.getEmpInfoInqry( inData.getBody().getEmp_no() );
			
			if ( caa100 == null || StringUtils.isEmpty(caa100.getEmp_no()) ) {
				log.error( "해당 직원정보가 존재하지 않습니다. : {}", inData );
				//MYER0041={0}가 존재하지 않습니다.
				throw makeException("MYER0041", "직원정보");
			}
			
			EmpInqryOutVO outVo = new EmpInqryOutVO();
			BeanUtils.copyProperties(caa100, outVo);
			
			log.error( "조회완료 되었습니다. : {}", outVo );
			
			//MYOK1003=조회가 완료 되었습니다.
			return makeResponse(inData, outVo, "MYOK1003" );
		} catch ( AppCommonException e ) {
			throw e;
		} catch ( Exception e ) {
			log.error( "ERROR : ", e );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}
	}
}
