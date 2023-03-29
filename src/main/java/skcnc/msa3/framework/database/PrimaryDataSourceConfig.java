package skcnc.msa3.framework.database;

import javax.sql.DataSource;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.session.LocalCacheScope;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;

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
    basePackages = "skcnc.msa3.domain.jpa.primary",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryJpaTxManager"
)
public class PrimaryDataSourceConfig {

    private static final String primaryMapperLocation = "classpath*:mapper/primary/*Mapper.xml";

    @Autowired
    private Environment env;

    /**
     * @Method Name : primaryDataSource
     * @description : DataSource 획득(서버 환경일 경우 jndi 를 사용하고, 로컬에서는 jdbc 직접 사용)
     * @return DataSource
     */
    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.primary.datasource") 	// application.properties에서 사용한 이름
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    // jpa
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            @Autowired @Qualifier("primaryDataSource") DataSource dataSource ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource );
        em.setPackagesToScan( new String[] { "skcnc.msa3.domain.jpa.primary" });

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
    public PlatformTransactionManager primaryJpaTxManager(
            @Autowired @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
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
    public SqlSessionFactory primarySqlSessionFactory(
            @Autowired @Qualifier("primaryDataSource") DataSource dataSource
            , ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //bean.setMapperLocations(applicationContext.getResources(primaryMapperLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(primaryMapperLocation));
        bean.setConfiguration(getMybatisConfig());
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Autowired @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "primaryTxManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Autowired @Qualifier("primaryDataSource") DataSource dataSource) {
        var txMgr = new DataSourceTransactionManager(dataSource);

        return txMgr;
    }

    // Transaction 이 JPA 와 mybatis 각각 2개 모두 4개임.
    // 그래서 Service 에 아래와 같이 사용할 Transaction 을 지정 해야 함
    // @Transactional(value = "primaryJpaTxManager", rollbackFor = {Throwable.class})
    // 동시에 2곳의 DB에 처리 해야 하는 경우 기존에는 ChainedTransactionManager 를 통해
    // 여러개의 Transaction 을 묶어서 사용하는게 가능
    // 현재 ChainedTransactionManager deprecated 됨.
    // 사용을 할수는 있지만 문제가 있음.
    // 결국 cud 하는 경우 2곳의 DB에 동시 처리는 불가할것 같음.
}