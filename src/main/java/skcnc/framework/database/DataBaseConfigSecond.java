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
@EnableJpaRepositories(
    basePackages = {"skcnc.sup.**.dao"},
    entityManagerFactoryRef = "secondEntityManager",
    transactionManagerRef = "secondTransactionManager"
)
@MapperScan(
        sqlSessionFactoryRef = "secondSessionFactory",
        value = { "skcnc.sup.**.dao.mapper", "classpath*:/skcnc/sup/**/dao/mapper/*Mapper.xml" }
)
public class DataBaseConfigSecond {
	@Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    DataSource secondDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean secondEntityManager() {
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(secondDatasource());
        managerFactoryBean.setPersistenceUnitName("secondPersistence");
        managerFactoryBean.setPackagesToScan(new String[] {
                "skcnc.sup.**.dao"
        });

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
    PlatformTransactionManager secondTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondEntityManager().getObject());
        return transactionManager;
    }

    @Bean(name = "secondSessionFactory")
    SqlSessionFactory secondSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(secondDatasource());
        sqlSessionFactoryBean.setTypeAliasesPackage("skcnc.sup");
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/skcnc/sup/**/dao/mapper/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Primary
    @Bean(name = "secondSessionTemplate")
    SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("secondSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
