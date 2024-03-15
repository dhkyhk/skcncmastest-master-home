package skcnc.framework.txmang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import skcnc.framework.apicall.ApiSendModule;
import skcnc.framework.utils.StringUtils;


@Component
public class TxMangMain implements Runnable {
	
	public final int TX_INIT  = 0;
	public final int TX_WAIT  = 1;
	public final int TX_USE   = 2;
	public final int TX_AFTER = 3; //다른 API 종료 대기
	public final int TX_CLOSE = 4;
	
	private Thread thread;
	private Logger txLog = LoggerFactory.getLogger( "TxManager" );
	private volatile AtomicBoolean swJobStop = new AtomicBoolean(false);
	
	private final static Map<Object, TxDbConnect> POOL_LIST = new HashMap<Object, TxDbConnect>();
	
	private final int POOL_MAX_CNT = 100;
	//private final static int POOL_STR_CNT = 5;
	private final int TIME_OUT = 3 * 60 * 1000;
	
	@Autowired
	SqlSessionFactory sessionFactory;
	
	@Autowired
	ApiSendModule apiSendModule;
	
	@PostConstruct
	public void init()  {
		txLog.debug( "*** TxMangMain Init Start *** " );
		
		try {
			
			for (int i=0; i< POOL_MAX_CNT ; i++ ) {
				TxDbConnect client = new TxDbConnect( sessionFactory, apiSendModule );
				POOL_LIST.put( i, client);
			}
			
			this.thread = new Thread( this );
			this.thread.start();
			
		} catch ( RuntimeException e ) {
			txLog.error( "RuntimeException", e  );
			throw e;
		}
		
		txLog.debug( "*** KisSocketPool Init END *** " );
	}

	@Override
	public void run() {

		boolean logView = false;
		txLog.debug( "*** KisSocketPool run Start *** " );
		
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
				
				if ( logView ) {
					txLog.debug( "*** TxMangMain : init{} wait:{} working:{} workedwait:{} close:{}", 
							statCnt[0], statCnt[1], statCnt[2], statCnt[3], statCnt[4] );
				}
				
				Thread.sleep( 100 );
				
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

		txLog.debug( "*** KisSocketPool run Start *** " );
		
		try {
			int workIdx = 0;
			for( workIdx=0; workIdx<POOL_LIST.size(); workIdx++ ) {
				TxDbConnect client = POOL_LIST.get(workIdx);
				
				if ( StringUtils.isEmpty(client.getGuid()) ) {
					return client;
				}
			}
		} catch ( RuntimeException e ) {
			txLog.error( "RuntimeException ERROR", e );
		}
		return null;
	}
}
