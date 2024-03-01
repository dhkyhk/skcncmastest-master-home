package skcnc.framework.security;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skcnc.framework.security.service.AuthUserDetailVO;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	public static String TOKEN_KEY_NAME = "X-AUTH-TOKEN";
	//public static String TOKEN_KEY_NAME = "Authorization";

	//@Autowired
	//MydataProperties mydataProp;
	
    @Value("${stockcore.auth.token.secret}")
    private String AUTH_TOKEN_KEY;

    @Value("${stockcore.auth.token.valid-minute:30}")
    private long authTokenValidMinute;

    private SecretKey secretKey = null;
    private long tokenValidMillisecond;

	@PostConstruct
	public void init() {
		String secretValue = this.AUTH_TOKEN_KEY; 
		//getAuthTokenSecret() 으로 획득시 이미 base64 디코딩된 문자열이 반환되어 secretValue 에는 실제 키 문자열이 들어가 있음.
		
//		log.warn("Token Secret : {}", secretValue); //중요정보이므로 로그에 기록하지 않는다.
//		log.warn("Token Secret bytes \n{}", secretValue.getBytes()); //중요정보이므로 로그에 기록하지 않는다.
		try {
			secretKey = Keys.hmacShaKeyFor(secretValue.getBytes());
		}catch(IllegalArgumentException ex) {
			throw new RuntimeException("AuthToken Secret 문자열을 Base64 인코딩되어 있어야 합니다.");
		}
		
		tokenValidMillisecond = this.authTokenValidMinute * 60 * 1000L;
		log.debug("Auth Token Expire Time : {} minute", this.authTokenValidMinute );
	}
	
	//Jwt 토큰 생성
	/**
	 * @Method Name : createToken
	 * @description : userPk와 roles로 토큰 생성
	 * @date        : 2021. 4. 13.
	 * @author      : P21055
	 * @param userPk : 사용자를 식별하는 유일한 값으로 mydata_id 를 넘긴다.
	 * @param roles  : 부여된 권한(USER 만 허용)
	 * @return
	 */
	public String createToken(String userPk, List<String> roles) {
		return createToken(userPk, roles, null);
	}
	
	public String createToken(String userPk, List<String> roles, Date issuedAt) {
		Claims claims = Jwts.claims()
				.setSubject(userPk)
				;
		claims.put("roles", roles);
		Date now = new Date();
		if(issuedAt == null) {
			issuedAt = now;
		}
		Date expireTime = new Date(now.getTime() + tokenValidMillisecond);
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(issuedAt)
				.setExpiration(expireTime)
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
		
		return token;
	}
	
	/**
	 * @Method Name : getMsaCustId
	 * @description : 현재 요청의 인증된 사용자 정보를 토큰으로부터 획득하여 반환
	 * @date        : 2024. 02. 28.
	 * @author      : 
	 * @return : null 반환시 토큰이 없거나 유효하지 않은 경우임.
	 */
	public String getMsaCustId() {
		var attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		var req = attr.getRequest();
		
		String token = resolveToken(req);
		if(StringUtils.isBlank(token)) return null;
		try {
			//jwt 토큰으로만 사용자정보 조회
			Jws<Claims> claims = Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					;
			
			return claims.getBody().getSubject();
		}catch(RuntimeException ex) {
			return null;
		}
	}
	
	/**
	 * @Method Name : updateToken
	 * @description : 현재의 토큰 유효기간을 갱신처리한다.
	 * @date        : 2024. 2. 29.
	 * @author      : 
	 * @param jwtToken
	 * @return
	 */
	public String updateToken(String jwtToken) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(jwtToken)
				.getBody()
				;
		String userPk = claims.getSubject();
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) claims.get("roles");
		Date issuedAt = claims.getIssuedAt();
		String token = createToken(userPk, roles, issuedAt);
		
		return token;
	}
	
	//Jwt 토큰으로 인증 정보를 반환
	public Authentication getAuthentication(String jwtToken) {
		//jwt 토큰으로만 사용자정보 조회
		Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(jwtToken)
				;
			
		boolean isValid = !claims.getBody().getExpiration().before(new Date());
		if(isValid) {
			@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) claims.getBody().get("roles");
			UserDetails userDetails = AuthUserDetailVO.builder()
					.msa_id(claims.getBody().getSubject())
					.roles(roles)
					.tokenIssuedAt(claims.getBody().getIssuedAt())
					.build();
			Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
			
			log.debug("Authentication created : {}", auth);
			return auth;
		}else {
			throw new RuntimeException("인증정보 확인필요");
		}
	}
	
	// Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰값"
	public String resolveToken(HttpServletRequest req) {
		String token = req.getHeader(TOKEN_KEY_NAME);
		if(StringUtils.isBlank(token)) {
			//쿠키에서 추출
			token = getCookieValue(req.getCookies(), TOKEN_KEY_NAME);
		}
		return token;
	}
	
	public void updateAndFlushToken(HttpServletRequest req, HttpServletResponse res, String oldToken) {
		String headerExists = req.getHeader(TOKEN_KEY_NAME);
		String newToken = updateToken(oldToken);
		if(headerExists != null) {
			res.setHeader(TOKEN_KEY_NAME, newToken);
		}
		
		Cookie cookie = new Cookie(TOKEN_KEY_NAME, newToken);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		res.addCookie(cookie);
		
		log.debug("Token updated and flushed: {}", newToken);
	}

	//Jwt 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(jwtToken)
					;
			boolean isValid = !claims.getBody().getExpiration().before(new Date());
			return isValid;
		}catch(ExpiredJwtException ex) {
			log.debug("Token Expired : {}", jwtToken);
			return false;
		}catch(RuntimeException ex) {
			log.error("Token Invalid : {}", jwtToken, ex);
			return false;
		}
	}
	
	private String getCookieValue(Cookie[] cookies, String key) {
		if(cookies == null) return null;
		for(Cookie cookie : cookies) {
			if(key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
