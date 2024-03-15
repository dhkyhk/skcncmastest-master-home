package skcnc.framework.database;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {
		"skcnc.stockcore.**.dao" }, entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
@MapperScan(sqlSessionFactoryRef = "sessionFactory", value = { "skcnc.stockcore.**.dao.mapper", "classpath*:/skcnc/stockcore/**/dao/mapper/*Mapper.xml" })
public class DataBaseConfig {
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	DataSource datasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	LocalContainerEntityManagerFactoryBean entityManager() {
		LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		managerFactoryBean.setDataSource(datasource());
		managerFactoryBean.setPersistenceUnitName("persistence");
		managerFactoryBean.setPackagesToScan(
				new String[] { "skcnc.stockcore.**.dao" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		managerFactoryBean.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.format_sql", true);
		prop.put("hibernate.hbm2ddl.auto", "none");
		managerFactoryBean.setJpaPropertyMap(prop);

		return managerFactoryBean;
	}

	@Bean
	@Primary
	PlatformTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManager().getObject());
		return jpaTransactionManager;
	}

	@Bean(name = "sessionFactory")
	@Primary
	SqlSessionFactory sessionFactory(ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(datasource());
		sqlSessionFactoryBean.setTypeAliasesPackage("skcnc.stockcore");
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/skcnc/stockcore/**/dao/mapper/*Mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
    @Primary
    @Bean(name = "sessionTemplate")
    SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
    	return new SqlSessionTemplate(sqlSessionFactory);
    }
}