package skcnc.framework.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
	private JwtTokenProvider jwtTokenProvider;
	
	//Jwt Provider 주입
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	
	/**
	 * Request로 들어오는 Jwt Token의 유효성 검증 및 유효한 경우 갱신된 token 을 쿠키 및 응답헤더로 전달
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);
		boolean isValid = false;
		if(!StringUtils.isBlank(token)) {
			if(jwtTokenProvider.validateToken(token)) {
				isValid = true;
			}
		}else {
			log.debug("Token is empty");
		}
		
		if(isValid) {
			Authentication auth = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);

			//토큰 갱신처리
			jwtTokenProvider.updateAndFlushToken((HttpServletRequest)request, (HttpServletResponse)response, token);
		}
		filterChain.doFilter(request, response);
	}
}
