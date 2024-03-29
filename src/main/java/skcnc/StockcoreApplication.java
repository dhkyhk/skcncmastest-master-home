package skcnc;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import jakarta.annotation.PostConstruct;


//@EnableWebMvc
/**
 * 할일을 여기에 정리하자 
 * 1. 카카오톡 
 * 2. 카프카
 * 3. 다른 서비스와 호출.
 */
@SpringBootApplication
@EnableFeignClients
public class StockcoreApplication{
	public static void main(String[] args) {
		SpringApplication.run(StockcoreApplication.class, args);
	}
	
	@PostConstruct
	void started(){
	    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
