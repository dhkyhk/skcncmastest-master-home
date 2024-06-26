package skcnc.framework.apicall;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;

@Component
//@Service
//@Transactional(rollbackFor = {Throwable.class})
public class ApiSendModule {

	@Qualifier("dataApiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;
	
	@Qualifier("appObjectMapper")
	@Autowired
	private ObjectMapper objectMapper;
	
	public AppResponse callRestApi(String url, AppRequest sendData) {

		Logger log = ContextStoreHelper.getLog();
		
		log.debug( "***  호출 url : {}", url );
		
		StringBuffer uriBuilder = new StringBuffer(url );
		
		HttpEntity<AppRequest<Object>> request = new HttpEntity<>(sendData);

		//requestEntity = new HttpEntity<Map<String, Object>>(apiTlgTextVO.getReqt_param_map(), httpHeaders); 
		/*
		if(log.isDebugEnabled() == true) {
			log.debug("*** callRestApi  - uriBuilder : {}", uriBuilder.toString());
			log.debug("*** callRestApi  - method : {}", method);
			log.debug("*** callRestApi  - requestEntity : {}", requestEntity);
		}*/

		ResponseEntity<Object> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.POST, request, Object.class);
		
		return objectMapper.convertValue(response.getBody(), AppResponse.class );
	}
}
