package skcnc.framework.etc;

import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import skcnc.framework.common.ContextStoreHelper;

@Component
public class KakaoAuthTokenUtil {
	private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
	public static String authToken = null;

	public boolean getKakaoAuthToken(String code)  {
		
		Logger log = ContextStoreHelper.getLog();
		
		try { 
			HttpHeaders header = new HttpHeaders();
			String accessToken = "";
			String refrashToken = "";
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

			header.set("Content-Type", HttpCallService.APP_TYPE_URL_ENCODED);

			parameters.add("code", code);
			parameters.add("grant_type", "authorization_code");
			parameters.add("client_id", "your client id");
			parameters.add("redirect_url", "http://localhost:88");
			parameters.add("client_secret", "your client secret");

			HttpEntity<?> requestEntity = HttpCallService.httpClientEntity(header, parameters);

			ResponseEntity<String> response = HttpCallService.httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
			JSONObject jsonData = new JSONObject(response.getBody());
			accessToken = jsonData.get("access_token").toString();
			refrashToken = jsonData.get("refresh_token").toString();
			if(accessToken.isEmpty() || refrashToken.isEmpty()) {
				log.debug("토큰발급에 실패했습니다.");
				return false;
			}else {
			    authToken = accessToken;
			    return true;
			}
		} catch( JSONException e ) {
			log.error( "ERROR", e );
			throw new RuntimeException( "JSONException", e );
		} catch( RuntimeException e ) {
			log.error( "ERROR", e );
			throw e;
		}
	}
}
