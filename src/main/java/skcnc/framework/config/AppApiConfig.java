package skcnc.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppApiConfig {
	//HTTP client의 연결을 유지할 시간(keep-alive 전략 및 idle 컨넥션 해제 타임아웃으로 사용)
	//private static final int HTTP_CONNECT_KEEP_TIMEOUT = 5; //second;
	
	
	/**
	 * @Method Name : localeChangeInterceptor
	 * @description : 다국어 처리를 위한 현재 사용자의 로케일을 처리하는 인터셉터
	 * @date        : 2021. 2. 24.
	 * @author      : P21055
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		var lci = new LocaleChangeInterceptor();
		return lci;
	}
	

	/**
	 * @Method Name : objectMapper
	 * @description : APP 서버용 JSON 직렬화 객체 
	 * @date        : 2021. 2. 24.
	 * @author      : P21055
	 * @return
	 */
	@Primary
	@Bean(name = "appObjectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper()
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.setSerializationInclusion(Include.ALWAYS)
				.setDefaultPropertyInclusion(Include.ALWAYS)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true) //key 대소문자 구분 안함
				.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) //떠블쿼데이션 없는 필드 허용
				;
		return mapper;
	}
}
