package skcnc.framework.txmang;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skcnc.framework.apicall.ApiSendModule;

public class TxDbConnect {

	private Logger txLog = LoggerFactory.getLogger( "TxManager" );
	
	public final int TX_INIT  = 0;
	public final int TX_WAIT  = 1;
	public final int TX_USE   = 2;
	public final int TX_AFTER = 3; //다른 API 종료 대기
	public final int TX_CLOSE = 4;
	
	private volatile AtomicInteger txState = new AtomicInteger(TX_INIT);
	
	private String GUID;
	private long   STR_TIME;
	//private String UP_SERVER_IP_PORT;

	private boolean bFirst;
	private boolean bSubCall;
	
    private final SqlSessionFactory sqlSessionFactory;
    private final SqlSession        sqlSession;
    private final ApiSendModule     apiSendModule;
	
	public TxDbConnect( SqlSessionFactory sqlSessionFactory, ApiSendModule apiSendModule ) {
		this.GUID = "";
		//this.TxManager = new DataSourceTransactionManager( dataSource );
		//new SqlSessionTemplate(this.TxManager);
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSession = this.sqlSessionFactory.openSession( false );
		this.apiSendModule = apiSendModule;
		this.txState.set(TX_WAIT);
		this.bSubCall = false;
	}
	
	public void setGuid(String guid, boolean bFirst) {
		this.GUID = guid;
		this.STR_TIME = System.currentTimeMillis();
		//this.UP_SERVER_IP_PORT = upServer;
		this.txState.set(TX_USE);
		this.bFirst = bFirst;
		this.bSubCall = false;
	}
	
	public SqlSession getSession() {
		return this.sqlSession;
	}
	
	public void setSubCall( boolean subcall ) {
		this.bSubCall = subcall;
	}
	
	public boolean getSubCall() {
		return this.bSubCall;
	}
	
	public String getGuid() {
		return this.GUID;
	}
	
	public long getStrTime() {
		return this.STR_TIME;
	}
	
	/*public String getUpServer() {
		return this.UP_SERVER_IP_PORT;
	}*/
	
	public void setState( int state ) {
		this.txState.set(state);
	}
	
	public int getState() {
		return this.txState.get();
	}
	
	public boolean getFirst() {
		return this.bFirst;
	}

	private void subCall() {
		if ( this.bSubCall ) {
			//TODO : subcall 기능 추가하자..
			//apiSendModule.callRestApi( "http://localhost:8081/demo2/test2", inData );
		}
	}
	
	public void procCommit() {
		this.subCall();
		
		this.sqlSession.commit();
		this.GUID = "";
		this.STR_TIME = 0;
		this.txState.set(TX_WAIT);
		this.bSubCall = false;
				
		//this.sqlSession.clearCache();
		//this.sqlSession.close();
		//this.sqlSession = this.sqlSessionFactory.openSession( false );
	}
	
	public void procRollback() {
		this.subCall();
		
		this.sqlSession.rollback();
		this.GUID = "";
		this.STR_TIME = 0;
		this.txState.set(TX_WAIT);
		this.bSubCall = false;
		
		//this.sqlSession.clearCache();
		//this.sqlSession.close();
		//this.sqlSession = this.sqlSessionFactory.openSession( false );
	}
	
	public void close() {
		try {
			//사용중이 아닌 경우 모두 close 함.
			if ( this.txState.get() != TX_USE && this.txState.get() != TX_AFTER ) {
				this.sqlSession.close();
				this.txState.set( TX_CLOSE );
			}
		} catch ( RuntimeException e ) {
			txLog.error( "Close RuntimeException ", e );
		}
	}
}