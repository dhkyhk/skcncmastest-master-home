package skcnc.framework.txmang;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import skcnc.framework.apicall.ApiSendModule;

@Component
//@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TxMangMain implements Runnable {
	
	public static final int TX_INIT  = 0;
	public static final int TX_WAIT  = 1;
	public static final int TX_USE   = 2;
	public static final int TX_AFTER = 3; //다른 API 종료 대기
	public static final int TX_CLOSE = 4;
	
	private Thread thread;
	private Logger txLog = LoggerFactory.getLogger( "TX_MANAGER" );
	private volatile AtomicBoolean swJobStop = new AtomicBoolean(false);
	
	private final static Map<Object, TxDbConnect> POOL_LIST = new HashMap<Object, TxDbConnect>();
	
	private final int POOL_MAX_CNT = 10;
	//private final static int POOL_STR_CNT = 5;
	private final int TIME_OUT = 3 * 60 * 1000;
	
	//@Autowired
	//@Qualifier("sessionFactory")
	
	
	//TODO : 직접 JDBC로 DB 연결해서 사용해 보자..
	//private Connection con;
	private SimpleDriverDataSource dataSource;
	private SqlSessionFactory      session;
	
	@Autowired
	ApiSendModule apiSendModule;
	
	@PostConstruct
	public void init()  {
		txLog.debug( "*** TxMangMain Init Start *** " );
		
		try {
		
			//con = DriverManager.getConnection("jdbc:postgresql://db-postgres-secmsa-core.cfgq2mm0w8uf.ap-northeast-2.rds.amazonaws.com:5432/postgres", "postgres", "asdfg12345");

			//Configuration clsConfig = new Configuration();
			//SqlSessionFactory clsFactory = new SqlSessionFactoryBuilder().build( clsConfig );
			//SqlSession clsSession = clsFactory.openSession( con );
			
			this.dataSource = new SimpleDriverDataSource();
	        //dataSource.setDriverClass( org.postgresql.Driver.class );
			this.dataSource.setDriverClass( org.postgresql.Driver.class );
			this.dataSource.setUrl("jdbc:postgresql://db-postgres-secmsa-core.cfgq2mm0w8uf.ap-northeast-2.rds.amazonaws.com:5432/postgres");
			this.dataSource.setUsername("postgres");
			this.dataSource.setPassword("asdfg12345");
	        
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean(); 
			sessionFactory.setDataSource(this.dataSource);
			
			Resource[] res = new PathMatchingResourcePatternResolver().getResources( "classpath*:/skcnc/stockcore/**/dao/mapper/*Mapper.xml" );
			sessionFactory.setMapperLocations(res);
			this.session = sessionFactory.getObject(); 
			
			for (int i=0; i< POOL_MAX_CNT ; i++ ) {
				TxDbConnect client = new TxDbConnect(txLog, this.session, apiSendModule );
				POOL_LIST.put( i, client);
			}
			
			this.thread = new Thread( this );
			this.thread.start();
		} catch ( SQLException e ) {
			txLog.error( "RuntimeException", e  );
			throw new RuntimeException( "SQLException", e );
		} catch ( IOException e ) {
			txLog.error( "RuntimeException", e  );
			throw new RuntimeException( "IOException", e );
		} catch ( RuntimeException e ) {
			txLog.error( "RuntimeException", e  );
			throw e;
		} catch ( Exception e ) {
			txLog.error( "RuntimeException", e  );
			throw new RuntimeException( "RuntimeException", e );
		}
		
		txLog.debug( "*** KisSocketPool Init END *** " );
	}

	@Override
	public void run() {

		boolean logView = false;
		txLog.debug( "*** KisSocketPool run Start *** " );
		int loopCnt = 0;
		
		while ( !Thread.interrupted() && !swJobStop.get() ) {
			
			int workIdx = 0;
			int[] statCnt = new int[5];
			logView = true;
			
			try { 
				
				long curTime = System.currentTimeMillis();
				
				for( workIdx=0; workIdx<POOL_LIST.size(); workIdx++ ) {
					TxDbConnect client = POOL_LIST.get(workIdx);

					switch (client.getState()) {
					case TX_INIT:
						statCnt[0]++;
						break;
					case TX_WAIT:
						statCnt[1]++;
						break;
					case TX_USE:
						statCnt[2]++;
						break;
					case TX_AFTER:
						statCnt[3]++;
						
						if ( curTime - client.getStrTime() > this.TIME_OUT ) {
							//대기시간 초과 일단 Rollback 처리하자..
							txLog.error( "*** Error idx : {} Time out Guid :{} Rollback!! ", workIdx, client.getGuid() );
							client.procRollback();
						}
						
						break;
					case TX_CLOSE:
						statCnt[4]++;
						break;
					default:
						break;
					}
				}
				
				if ( logView && (loopCnt % 10 == 0) ) {
					loopCnt = 0;
					txLog.debug( "*** TxMangMain : init{} wait:{} working:{} workedwait:{} close:{}", 
							statCnt[0], statCnt[1], statCnt[2], statCnt[3], statCnt[4] );
				}
				
				Thread.sleep( 500 );
				loopCnt++;
				
			} catch ( InterruptedException e ) {
				txLog.error( "** Socket Pool InterruptedException ERROR ", e );
			} catch ( RuntimeException e ) {
				txLog.error( "** Socket Pool RuntimeException ,ERROR ", e );
			}
		}
		
		txLog.debug( "*** KisSocketPool run END *** " );
	}
	
	/**
	 * @Method Name : destroy
	 * @description : Socket 종료
	 * @date        : 2021. 9. 13.
	 * @author      : P21024
	 * @throws Exception
	 */
	@PreDestroy
	private void destroy() throws Exception {
		swJobStop.set(true);
		int doingDbTx = 0 ;
		
		
		for ( int j=0; j<10; j++ ) {
			
			for ( int i=0; i<POOL_LIST.size(); i++  ) {
				
				TxDbConnect client = POOL_LIST.get(i);
				
				if ( client.getState() == TX_USE || client.getState() == TX_AFTER ) {
					doingDbTx++;
				} else {
					client.close();
				}
			}
			
			if ( doingDbTx == 0 ) {
				break;
			}
		}
	
	}
	
	public synchronized TxDbConnect getTxDb() {

		txLog.debug( "*** TxDbConnect Start *** " );
		
		try {
			int workIdx = 0;
			for( workIdx=0; workIdx<POOL_LIST.size(); workIdx++ ) {
				TxDbConnect client = POOL_LIST.get(workIdx);
				
				if ( client.getState() == TX_WAIT ) {
					client.setState( TX_USE );
					txLog.debug( "{} tx client use return", workIdx );
					return client;
				}
			}
		} catch ( RuntimeException e ) {
			txLog.error( "TxDbConnect RuntimeException ERROR", e );
		}
		return null;
	}
	
	public synchronized TxDbConnect getTxDb( String guid ) {
		txLog.debug( "*** TxDbConnect Guid Start *** {} ", guid );
		
		try {
			int workIdx = 0;
			for( workIdx=0; workIdx<POOL_LIST.size(); workIdx++ ) {
				TxDbConnect client = POOL_LIST.get(workIdx);
				
				if ( client.getState() == TX_AFTER ) {
					if ( guid.equals(client.getGuid()) ) {
						return client;
					}
				}
			}
			
			return this.getTxDb();
		} catch ( RuntimeException e ) {
			txLog.error( "TxDbConnect Guid RuntimeException ERROR", e );
		}
		return null;
	}
	
	
	public synchronized boolean getTxAftProc( String guid, String succYn ) {
		txLog.debug( "*** getTxGuidDB Guid Start *** " );
		
		try {
			int workIdx = 0;
			for( workIdx=0; workIdx<POOL_LIST.size(); workIdx++ ) {
				TxDbConnect client = POOL_LIST.get(workIdx);
				
				if ( client.getState() == TX_AFTER ) {
					if ( guid.equals(client.getGuid()) ) {
						if ( "C".equals(succYn) ) {
							client.procCommit();
						} else {
							client.procRollback();
						}
					}
				}
			}
			
		} catch ( RuntimeException e ) {
			txLog.error( "getTxGuidDB Guid RuntimeException ERROR", e );
			return false;
		}
		return true;
	}
}
