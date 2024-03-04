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
import skcnc.framework.utils.DateUtil;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ac.common.CifCtdtMangModule;
import skcnc.stockcore.ac.common.CsfInfoMangModule;
import skcnc.stockcore.ac.dao.table.Aca1000Table;
import skcnc.stockcore.ac.dao.table.Aca1100Table;
import skcnc.stockcore.ac.service.vo.CifInfoCrtInArryVO;
import skcnc.stockcore.ac.service.vo.CifInfoCrtInVO;
import skcnc.stockcore.ac.service.vo.CifInfoCrtOutVO;

@Service
@Transactional
public class CifInfoCrt extends AppCommonService 
{

	@Autowired
	CsfInfoMangModule csfInfoMangModule;
	
	@Autowired
	CifCtdtMangModule cifCtdtMangModule;
	
	public AppResponse<CifInfoCrtOutVO> procCifRegi(AppRequest<CifInfoCrtInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
		
		try {
			
			CifInfoCrtOutVO outVo = new CifInfoCrtOutVO();
			
			if ( StringUtils.isEmpty(inData.getBody().getWrk_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getCs_nm()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "고객명");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getRlnm_cnf_no_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "실명확인구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEnc_rlnm_no()) || inData.getBody().getEnc_rlnm_no().length() != 13 ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "실명확인번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getBirh_dt()) || !DateUtil.isValidDate(inData.getBody().getBirh_dt()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "생일");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getCs_typ_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "고객유형구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getNat_frgn_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "내국인외국인구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getSex_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "성별");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getCust_id()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "고객ID");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getEnc_pwd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "비밀번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getMang_dept_no()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "관리부서번호");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getInco_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "소득구분코드");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getOval_tati_typ_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "종합과세유형구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getKor_stex_invt_dec()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "한국거래소투자자구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getNana_nati_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "국적국가구분");
			}
			
			if ( StringUtils.isEmpty(inData.getBody().getResd_nati_dcd()) ) {
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "거주국가구분");
			}
			
			Aca1000Table aca1000Vo = new Aca1000Table();
			BeanUtils.copyProperties(inData.getBody(), aca1000Vo);
			String msgTxt;
			
			if ( "I".equals(inData.getBody().getWrk_dcd()) ) 
			{
				String cif = csfInfoMangModule.getCifMaxNo();
				inData.getBody().setCif(cif);
				
				aca1000Vo.setCif(cif);
				aca1000Vo.setCs_grd_cd( "4" ); //고객등급코드 4:일반
				aca1000Vo.setCrdt_por_dcd( "01" ); //신용불량구분코드 01:정상
				
				csfInfoMangModule.procCifInfoRegi( aca1000Vo );
				
				msgTxt = "고객등록이";
			}
			else if ( "U".equals(inData.getBody().getWrk_dcd()) ) 
			{
				if ( StringUtils.isEmpty(inData.getBody().getCif()) ) {
					log.error( "입력값을 확인하세요 : {}", inData );
					//MYER0003=입력값 {0}를 확인하세요.
					throw makeException("MYER0003", "고객번호");
				}
				
				csfInfoMangModule.procCifInfoChg( aca1000Vo );
				
				msgTxt = "고객정보 변경이";
			} 
			else 
			{
				log.error( "입력값을 확인하세요 : {}", inData );
				//MYER0003=입력값 {0}를 확인하세요.
				throw makeException("MYER0003", "작업구분");
			}

			if ( inData.getBody().getSub_arryvo() != null && inData.getBody().getSub_arryvo().size() >0 ){

				for (CifInfoCrtInArryVO subvo : inData.getBody().getSub_arryvo() ) {
					
					if ( StringUtils.isEmpty(subvo.getCtdt_dcd()) ) {
						log.error( "입력값을 확인하세요 : {}", subvo );
						//MYER0003=입력값 {0}를 확인하세요.
						throw makeException("MYER0003", "연락처구분코드");
					}
					
					//01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eami
					if( "01|03".contains( subvo.getCtdt_dcd() ) ) {
						if ( StringUtils.isEmpty(subvo.getPost_no_sys_dcd()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "우편번호체계구분");
						}
						
						if ( StringUtils.isEmpty(subvo.getPost_no()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "우편번호");
						}
						
						if ( StringUtils.isEmpty(subvo.getPost_no_adr()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "우편번호주소");
						}
						
						if ( StringUtils.isEmpty(subvo.getEnc_post_no_ptcl_adr()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "암호화우편번호상세주소");
						}
						
						subvo.setTel_area_no( "" );
						subvo.setTel_ptcl_area_no( "" );
						subvo.setEnc_tel_nd_no( "" );
						subvo.setMail( "" );
					}
					else if( "02|04|05|06".contains( subvo.getCtdt_dcd() ) ) {
						
						if ( StringUtils.isEmpty(subvo.getTel_area_no()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "전화지역번호");
						}
						
						if ( StringUtils.isEmpty(subvo.getTel_ptcl_area_no()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "전화국번호");
						}
						
						if ( StringUtils.isEmpty(subvo.getEnc_tel_nd_no()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "전화끝번호");
						}
						
						subvo.setPost_no_sys_dcd( "" );
						subvo.setPost_no( "" );
						subvo.setPost_no_adr( "" );
						subvo.setEnc_post_no_ptcl_adr( "" );
						subvo.setMail( "" );
					}
					else if( "07".equals(subvo.getCtdt_dcd()) ) {
						if ( StringUtils.isEmpty(subvo.getMail()) ) {
							log.error( "입력값을 확인하세요 : {}", subvo );
							//MYER0003=입력값 {0}를 확인하세요.
							throw makeException("MYER0003", "이메일");
						}
						
						subvo.setPost_no_sys_dcd( "" );
						subvo.setPost_no( "" );
						subvo.setPost_no_adr( "" );
						subvo.setEnc_post_no_ptcl_adr( "" );
						subvo.setTel_area_no( "" );
						subvo.setTel_ptcl_area_no( "" );
						subvo.setEnc_tel_nd_no( "" );
					}
					else 
					{
						log.error( "입력값을 확인하세요 : {}", subvo );
						//MYER0003=입력값 {0}를 확인하세요.
						throw makeException("MYER0003", "연락처구분");
					}
					
					
					long regi_sno = cifCtdtMangModule.getCifCtdMaxNo( inData.getBody().getCif(), subvo.getCtdt_dcd() );
					
					//기존 연락처 정보 미사용 처리
					cifCtdtMangModule.procCifCtdDelete( inData.getBody().getCif(), subvo.getCtdt_dcd() );
					
					Aca1100Table aca1100Vo = new Aca1100Table(); 
					BeanUtils.copyProperties(subvo, aca1100Vo);
					
					aca1100Vo.setCif( inData.getBody().getCif() );
					aca1100Vo.setRegi_sno( regi_sno );
					
					cifCtdtMangModule.procCifCtdRegi( aca1100Vo );
				}
			}
			
			outVo.setCif( inData.getBody().getCif() );
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
