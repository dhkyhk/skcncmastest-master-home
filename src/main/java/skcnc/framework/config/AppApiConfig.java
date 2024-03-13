package skcnc.framework.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppApiConfig {
	
	//HTTP client의 연결을 유지할 시간(keep-alive 전략 및 idle 컨넥션 해제 타임아웃으로 사용)
	private static final int HTTP_CONNECT_KEEP_TIMEOUT = 5; //second;
	
	
	/**
	 * @Method Name : localeChangeInterceptor
	 * @description : 다국어 처리를 위한 현재 사용자의 로케일을 처리하는 인터셉터
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
	
	@Bean(name = "dataApiObjectMapper")
	ObjectMapper dataApiObjectMapper() {
		ObjectMapper mapper = new ObjectMapper()
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				//표준API규격(v202107-1)에 의해 null,빈값은 요청/응답에서 제외
				.setSerializationInclusion(Include.NON_EMPTY)
				.setDefaultPropertyInclusion(Include.NON_EMPTY)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				;
		

		SimpleModule module = new SimpleModule("ToStringSerializerModule");
		ToStringSerializer toStringSerializer = ToStringSerializer.instance;
		module.addSerializer(long.class, toStringSerializer);
		module.addSerializer(int.class, toStringSerializer);
		module.addSerializer(float.class, toStringSerializer);
		module.addSerializer(double.class, toStringSerializer);
		module.addSerializer(Number.class, toStringSerializer);
		module.addSerializer(boolean.class, toStringSerializer);
		module.addSerializer(Boolean.class, toStringSerializer);
		
		mapper.registerModule(module);
		
		return mapper;
	}
	
	@Bean("dataApiRestTemplate")
	RestTemplate dataApiRestTemplate(
			@Autowired 
			@Qualifier("dataApiObjectMapper") 
			ObjectMapper objectMapper,
			@Autowired ClientHttpRequestFactory clientHttpRequestFactory
			) {
		
		return innerRestTemplate(objectMapper, clientHttpRequestFactory );
	}
	
	private RestTemplate innerRestTemplate(
			ObjectMapper objectMapper,
			ClientHttpRequestFactory clientHttpRequestFactory
			) {
		
		RestTemplate template = new RestTemplate();
		
		template.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));
		List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);

		//xml 메시지컨버터는 제거한다.(표준 api 스펙 및 기타 rest 통신시 xml 사용하는 곳이 없으므로 제거함. 필요시 json 이 선순위가 되도록 조치해야 함)
		messageConverters.removeIf(p -> AbstractXmlHttpMessageConverter.class.isAssignableFrom(p.getClass()));
		messageConverters.removeIf(p -> MappingJackson2XmlHttpMessageConverter.class.isAssignableFrom(p.getClass()));
		

		messageConverters.removeIf(p -> MappingJackson2HttpMessageConverter.class.isAssignableFrom(p.getClass()));
		messageConverters.add(jsonConverter);
		
		
		//application/x-www-form-urlencoded
		FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
		messageConverters.add(formConverter);
		
		if(log.isInfoEnabled()) {
			String logMsg = messageConverters.stream()
					.map(m -> {
						return m.toString();
					}).collect(Collectors.joining(","));
			log.info("RestTemplate => {}, MessageConverters => {}", template, logMsg);
		}

		return template;
	}
	
	@Bean
	ClientHttpRequestFactory clientHttpRequestFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		//SSL 인증서 무시 설정
		TrustStrategy acceptingTrustStrategy = (cert, authTyp) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		
		//host이름 검증 무시
		//SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		
/*
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory())
				.build();
*/
		
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(100);
		 
		RequestConfig requestConfig = RequestConfig
				.custom()
				.setConnectTimeout( Timeout.ofMilliseconds(HTTP_CONNECT_KEEP_TIMEOUT * 1000 ) )
				.setConnectionRequestTimeout( Timeout.ofMilliseconds(HTTP_CONNECT_KEEP_TIMEOUT * 1000 ) )
				.build();

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig)
				.setKeepAliveStrategy(connectionKeepAliveStrategy())
				//.setSSLSocketFactory(sslsf)
				.disableRedirectHandling() //disable redirect
				.evictExpiredConnections()
				.evictIdleConnections( Timeout.ofMilliseconds(HTTP_CONNECT_KEEP_TIMEOUT * 1000 ) )
				.build();

		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return factory;
	}
	
	@Bean
	ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return (httpResponse, httpContext) -> {
			Iterator<Header> it = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
			//HeaderElementIterator eleIt = new BasicHeaderElementIterator(it);
			
			while( it.hasNext() ) {
				Header header = it.next();
				
				String param = header.getName();
				String value = header.getValue();
				if(value != null && param.equalsIgnoreCase("timeout")) {
					//return (Long.parseLong(value) * 1000) - 500; //convert to ms
					return Timeout.ofMilliseconds( Long.parseLong(value) * 1000  - 500 );
				}
			}

			return Timeout.ofMilliseconds(HTTP_CONNECT_KEEP_TIMEOUT * 1000 );
		};
	}
}
