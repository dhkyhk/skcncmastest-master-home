package skcnc.framework.security;

import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import skcnc.framework.common.AppCommonRequestControllerAdvice;
import skcnc.framework.common.AppCommonResponseControllerAdvice;
import skcnc.framework.common.FileMessageSource;
import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;

@Component
public class NoauthAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Qualifier("appObjectMapper")
	@Autowired
	private ObjectMapper objectMapper; 
	
	@Autowired
	private FileMessageSource message;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ae) throws IOException, ServletException {

		String url = request.getRequestURI();

		AppRequest<Object> appReq = null;
		AppResponse<Object> appResp = null;
		
		//요청 전문 읽어서 json 획득
		String bodyJson = request.getReader().lines().collect(Collectors.joining());
		if(!StringUtils.isBlank(bodyJson)) {
			appReq = objectMapper.readValue(bodyJson, new TypeReference<AppRequest<Object>>() {} );
			
			//요청 전처리 진행 : 인증 필터는 DispatcherServlet 이전에 실행되므로 선행작업 진행시킴(응답 생성시 필요)
			AppCommonRequestControllerAdvice.beforeProcess(appReq);
		}
		
		//응답생성
		String msgCd = "MYER9001"; //MYER9001=인증되지 않은 요청입니다.URL={0}
		String msg = message.getMessage(msgCd, url);
		appResp = AppResponse.create(appReq == null ? null : appReq.getHead(),
				AppHeader.RT_FAIL,
				msgCd, 
				msg, 
				null);
		
		//응답 후처리 진행
		AppCommonResponseControllerAdvice.afterProcess(appResp);
		
		//응답으로 인증실패 json 기록
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(appResp));
	}
}
