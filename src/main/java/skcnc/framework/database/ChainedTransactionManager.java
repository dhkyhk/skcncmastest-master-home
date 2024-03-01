package skcnc.framework.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

/*
 * 만들기는 했는데 사용은 안하는게 좋을것 같다..
 * */
@Configuration
public class ChainedTransactionManager {
/*
	@Bean
	@Primary
	PlatformTransactionManager chainTransactionManager(
			PlatformTransactionManager transactionManager
			, PlatformTransactionManager secondTransactionManager) {
		return new org.springframework.data.transaction.ChainedTransactionManager(transactionManager, secondTransactionManager);
	}*/
}
