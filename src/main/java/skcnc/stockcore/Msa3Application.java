package skcnc.stockcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@EnableWebMvc
/**
 * 할일을 여기에 정리하자 
 * 1. jwt 추가
 * 2. 카프카
 * 3. 다른 서비스와 호출.
 */
@SpringBootApplication
@EnableFeignClients
public class Msa3Application{
	public static void main(String[] args) {
		SpringApplication.run(Msa3Application.class, args);
	}
}
