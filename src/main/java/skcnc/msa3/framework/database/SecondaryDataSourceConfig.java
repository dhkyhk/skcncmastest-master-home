package skcnc.msa3.framework.database;

import javax.sql.DataSource;
import java.util.HashMap;

import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import skcnc.msa3.framework.common.YamlPropertySourceFactory;

@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@Configuration //(proxyBeanMethods = false) //메소드를 호출할 때 의도적으로 매번 다른 been 객체가 생성
@EnableJpaRepositories(
        basePackages = "skcnc.msa3.domain.jap.secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryJpaTxManager"
)
public class SecondaryDataSourceConfig {
    private static final String secondaryMapperLocation = "classpath*:mapper/secondary/*Mapper.xml";

    @Autowired
    private Environment env;

    /**
     * @Method Name : secondaryDataSource
     * @description : DataSource 획득(서버 환경일 경우 jndi 를 사용하고, 로컬에서는 jdbc 직접 사용)
     * @return DataSource
     */
    @Primary
    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.secondary.datasource") 	// application.yml 에서 사용한 이름
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    // jpa
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Autowired @Qualifier("secondaryDataSource") DataSource dataSource ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource );
        em.setPackagesToScan( new String[] { "skcnc.msa3.domain.jpa.secondary" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager secondaryJpaTxManager(
            @Autowired @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory.getObject());
        return tm;
    }

    // mybatis
    /**
     * @Method Name : getMybatisConfig
     * @description : Mybatis 공통 설정 반환
     * - 기본 동작 설정
     * - 인터셉터 설정
     * - Alias 설정
     * - TypeHandler 설정
     * @return
     */
    private org.apache.ibatis.session.Configuration getMybatisConfig() {
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();

        config.setJdbcTypeForNull(JdbcType.NULL);
        config.setLocalCacheScope(LocalCacheScope.STATEMENT);
        config.setCacheEnabled(false);
        config.setLazyLoadingEnabled(false);

        //인터셉터 등록 : //등록 순서 중요 : LIFO 순으로 처리됨
        config.addInterceptor(new QueryLoggingInterceptor());
        //테이블에 기본 항목 입력용(조작자, 처리일시 등등)
        //TODO : 항목이 정해진 후 MetaCommonInterceptor 수정 후 주석 제거하자.
        //config.addInterceptor(new MetaCommonInterceptor());

        config.getTypeAliasRegistry().registerAlias(MetaHashMap.class);

        return config;
    }

    @Primary
    @Bean
    public SqlSessionFactory secondarySqlSessionFactory(
            @Autowired @Qualifier("secondaryDataSource") DataSource dataSource
            , ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //bean.setMapperLocations(applicationContext.getResources(secondaryMapperLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(secondaryMapperLocation));
        bean.setConfiguration(getMybatisConfig());
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate secondarySqlSessionTemplate(
            @Autowired @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "secondaryTxManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Autowired @Qualifier("secondaryDataSource") DataSource dataSource) {
        var txMgr = new DataSourceTransactionManager(dataSource);

        return txMgr;
    }
}
