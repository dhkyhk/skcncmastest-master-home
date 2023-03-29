package skcnc.msa3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableWebMvc
@SpringBootApplication
@EnableFeignClients
public class Msa3Application{
	public static void main(String[] args) {
		SpringApplication.run(Msa3Application.class, args);
	}
}
