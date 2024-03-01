package skcnc.stockcore.ca.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import skcnc.framework.common.AppCommonService;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.etc.DefaultMessageDto;
import skcnc.framework.etc.KakaoAuthTokenUtil;
import skcnc.framework.etc.MessageService;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.service.vo.PrcsNotfInVO;
import skcnc.stockcore.ca.service.vo.PrcsNotfOutVO;

@Service
@Transactional
public class NotfMangService extends AppCommonService {

	@Autowired
	MessageService messageService;
	
	@Autowired
	KakaoAuthTokenUtil kakaoAuthTokenUtil;
	
	public AppResponse<PrcsNotfOutVO> prcsNotf(@RequestBody AppRequest<PrcsNotfInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
		
		if ( StringUtils.isEmpty(KakaoAuthTokenUtil.authToken) ) {
			//TODO : 카카오톡을 보내기 위해서는 코드가 필요한것 같다..
			String code = "";
			if ( !kakaoAuthTokenUtil.getKakaoAuthToken( code ) ) {
				log.error( "카카오톡 토큰 발급 중 오류 발생.!!" );
				//MYER0007=처리중 오류가 발생했습니다.
				throw makeException("MYER0007");
			}
		}
		
		DefaultMessageDto myMsg = new DefaultMessageDto();
		myMsg.setBtnTitle("자세히보기");
		myMsg.setMobileUrl("");
		myMsg.setObjType("text");
		myMsg.setWebUrl("");
		myMsg.setText("메시지 테스트입니다.");

		String accessToken = KakaoAuthTokenUtil.authToken;
		
		if ( !messageService.sendMessage(accessToken, myMsg) ) {
			log.error( "카카오톡 메시지 전송중 오류 발생.!!" );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}

		//06:휴대폰으로 알람 주는걸로 설정함..
		PrcsNotfOutVO outVo = PrcsNotfOutVO.builder().notf_ctdt_dcd("06").norl_prcs_yn("Y").build();

		
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData, outVo, "MYOK1001", "카카오톡 메시지 전송이" );
	}
}
