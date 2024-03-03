package skcnc.stockcore.ac.service;

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
import skcnc.stockcore.ac.common.AcInfoMangModule;
import skcnc.stockcore.ac.common.AcNotfMangModule;
import skcnc.stockcore.ac.dao.table.Aca1100Table;
import skcnc.stockcore.ac.dao.table.Acb1000Table;
import skcnc.stockcore.ac.dao.table.Acb1100Table;
import skcnc.stockcore.ac.service.vo.AcInfoCrtInArryVO;
import skcnc.stockcore.ac.service.vo.AcInfoCrtInVO;
import skcnc.stockcore.ac.service.vo.AcInfoCrtOutVO;

@Service
@Transactional
public class AcInfoCrt extends AppCommonService {

	@Autowired
	AcInfoMangModule acInfoMangModule;
	
	@Autowired
	AcNotfMangModule acNotfMangModule;
	
	public AppResponse<AcInfoCrtOutVO> procAcRegi(AppRequest<AcInfoCrtInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
	
		try {
			String msgTxt="";
			
			AcInfoCrtOutVO outVo = new AcInfoCrtOutVO(); 
			
			if ( StringUtils.isEmpty(inData.getBody().getWrk_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getPrd_typ_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "상품유형구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getAc_stat_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "계좌상태구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getBill_tati_yn()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "이용료과세여부");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getTrtx_tati_yn()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "거래세과세여부");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getDvtx_tati_yn()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "배당세과세여부");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getCma_ac_yn()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "CMA계좌여부");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getSly_ac_yn()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "월급계좌여부");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEnc_pwd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "비밀번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEnc_pwd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "비밀번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getCif()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "고객식별번호");
			}
			
			Acb1000Table acb1000Vo = new Acb1000Table();
			BeanUtils.copyProperties(inData.getBody(), acb1000Vo);
			
			if ( "I".equals(inData.getBody().getWrk_dcd()) ) 
			{
				String ac_no = acInfoMangModule.getAcMaxNo();
				inData.getBody().setAc_no( ac_no );
				
				acb1000Vo.setAc_no(ac_no);
				
				acInfoMangModule.procAcInfoRegi(acb1000Vo);
			}
			else if ( "U".equals(inData.getBody().getWrk_dcd()) ) 
			{
				if ( StringUtils.isEmpty(inData.getBody().getAc_no()) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "계좌번호");
				}
				
				acInfoMangModule.procAcInfoChg(acb1000Vo);
			}
			else 
			{
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}
			
			
			if ( inData.getBody().getSub_arryvo() != null && inData.getBody().getSub_arryvo().size() >0 ){
				
				for (AcInfoCrtInArryVO subvo : inData.getBody().getSub_arryvo() ) {

					if ( StringUtils.isEmpty(subvo.getNotf_wrk_dcd()) ) {
						log.error( "입력값을 확인하세요 : {}", inData );
						//MYER0003=입력값 {0}를 확인하세요.
						throw makeException("MYER0003", "통보작업구분");
					}
					
					if ( StringUtils.isEmpty(subvo.getNotf_ctdt_dcd()) ) {
						log.error( "입력값을 확인하세요 : {}", inData );
						//MYER0003=입력값 {0}를 확인하세요.
						throw makeException("MYER0003", "통보연락처구분");
					}
					
					//기존정보 조회
					Acb1100Table acb1100 = acNotfMangModule.getAcNotfInqry( inData.getBody().getAc_no(), subvo.getNotf_wrk_dcd() );
					
					if ( acb1100 !=null ) {
						
						acb1100.setNotf_ctdt_dcd( subvo.getNotf_ctdt_dcd() );
						acb1100.setUse_yn( "Y" );
						
						acNotfMangModule.procAcNotfChg( acb1100 );
						
					} else {
						Acb1100Table acb1100Vo = new Acb1100Table(); 
						BeanUtils.copyProperties(subvo, acb1100Vo);
						
						acb1100Vo.setAc_no( inData.getBody().getAc_no() );
						
						acNotfMangModule.procAcNotfRegi( acb1100Vo );
					}
				}
			}
			
			outVo.setAc_no( inData.getBody().getAc_no() );
			outVo.setNorl_prcs_yn( "Y" );
			
			//MYOK1001={0} 정상 처리 되었습니다 
			return makeResponse(inData, outVo, "MYOK1001", msgTxt );
			
		} catch ( AppCommonException e ) {
			throw e;
		} catch ( Exception e ) {
			log.error( "ERROR : ", e );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}
	}
}
