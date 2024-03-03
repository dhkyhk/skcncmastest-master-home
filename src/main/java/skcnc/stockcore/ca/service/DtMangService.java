package skcnc.stockcore.ca.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skcnc.framework.common.AppCommonException;
import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.common.DtMangModule;
import skcnc.stockcore.ca.service.vo.DtCrtInVO;
import skcnc.stockcore.ca.service.vo.DtCrtOutVO;

@Service
@Transactional
public class DtMangService extends AppCommonService {

	@Autowired
	DtMangModule dtMang;
	
	public AppResponse<DtCrtOutVO> dtCrt(AppRequest<DtCrtInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
		
		try {
			String prs_dt;
			
			if ( StringUtils.isEmpty(inData.getBody().getWrk_dt_dcd()) ) {
				dtMang.setDt( "01" );
				dtMang.setDt( "02" );
				dtMang.setDt( "03" );
				dtMang.setDt( "04" );
				dtMang.setDt( "05" );
				
				prs_dt = dtMang.getBsnDt( "01" );
			} else if ( inData.getBody().getWrk_dt_dcd().contains("01|02|03|04|05") ) {
				dtMang.setDt( inData.getBody().getWrk_dt_dcd() );
				
				prs_dt = dtMang.getBsnDt( inData.getBody().getWrk_dt_dcd() );
			} else {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업일구분");
			}
			
			DtCrtOutVO outVo = new DtCrtOutVO(); 
			outVo.setNorl_prcs_yn("Y");
			outVo.setPrs_dt( prs_dt );
			
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
