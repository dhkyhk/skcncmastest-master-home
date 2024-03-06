package skcnc.stockcore.ca.service;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.stockcore.ca.service.vo.TimeTakenInVO;
import skcnc.stockcore.ca.service.vo.TimeTakenOutVO;

@Service
@Transactional
public class TimeTaken extends AppCommonService {

	public AppResponse<TimeTakenOutVO> prcsTrade(@RequestBody AppRequest<TimeTakenInVO> inData)
	{
		//TODO : 
		//1. 10번 쿼리 날리자...
		//2. Update 1번.
		Logger log = ContextStoreHelper.getLog();
		
		log.debug( "*** Test 거래 시작 ***" );
		
		String mapperNm = "mapper.ca.timetakenmapper.";
		Map<String, Object> insMap = new MetaHashMap();
		Map<String,Object> qrv = null;
		
		//일자정보 조회
		qrv = dbio.select( mapperNm + "selectcad1100one", "01" );
		qrv = dbio.select( mapperNm + "selectcad1100one", "02" );
		qrv = dbio.select( mapperNm + "selectcad1100one", "03" );
		qrv = dbio.select( mapperNm + "selectcad1100one", "04" );
		
		//직원정보 조회
		qrv = dbio.select( mapperNm + "selectcaa1000one", "08963" );
		
		//고객기본
		qrv = dbio.select( mapperNm + "selectaca1000one", "0000000001" );
		
		insMap.put( "cif", "0000000001");
		insMap.put( "ctdt_dcd", "01");
		
		//고객연락처 기본
		qrv = dbio.select( mapperNm + "selectaca1100one", insMap );
		
		// 계좌기본
		qrv = dbio.select( mapperNm + "selectacb1000one", "00000000001" );
		
		insMap.clear();
		insMap.put( "ac_no", "00000000001");
		insMap.put( "notf_wrk_dcd", "01");
		
		// 계좌기본
		qrv = dbio.select( mapperNm + "selectacb1100one", insMap );
		
		insMap.clear();
		insMap.put( "cif", "0000000001");
		insMap.put( "ac_no", "00000000001");
		insMap.put( "notf_ctdt_dcd", "01");
		insMap.put( "post_no_sys_dcd", "01");
		insMap.put( "post_no", "01");
		insMap.put( "post_no_adr", "서울 영등포구 여의대로 24");
		insMap.put( "enc_post_no_ptcl_adr", "13층");
		insMap.put( "tel_area_no", "010");
		insMap.put( "tel_ptcl_area_no", "5555");
		insMap.put( "enc_tel_nd_no", "6666");
		insMap.put( "mail", "111@222.com");
		insMap.put( "notf_txt", "처리가 완료 되었습니다.");
		
		dbio.insert(  mapperNm + "insertcad4000", insMap );
		
		TimeTakenOutVO outVo = TimeTakenOutVO.builder().norl_prcs_yn("Y").build();

		log.debug( "*** Test 거래 종료 ***" );
		
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData, outVo, "MYOK1001", "Test가" );
	}
	
}
