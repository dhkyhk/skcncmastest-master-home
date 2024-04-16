package skcnc.framework.txmang;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import skcnc.framework.apicall.ApiSendModule;
import skcnc.framework.model.AppHeader;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.stockcore.ca.controller.vo.TxInVO;
import skcnc.stockcore.ca.controller.vo.TxOutVO;

public class TxDbConnect {

	private Logger txLog;
	
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
    
    private ArrayList<String> subCallList = new ArrayList<String>(); 
	
	public TxDbConnect(Logger txLog, SqlSessionFactory sqlSessionFactory, ApiSendModule apiSendModule ) {
		this.txLog = txLog;
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
		
		txLog.debug( "*** START setGuid guid:{}, First:{}", guid, bFirst );
		
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
		
		if ( state == this.TX_USE )
			this.subCallList.clear();
	}
	
	public int getState() {
		return this.txState.get();
	}
	
	public boolean getFirst() {
		return this.bFirst;
	}

	public boolean procCommit() {
		
		txLog.debug( "*** TX Manager Commit!!" );
		
		boolean bSuccYn = this.callCommitApi( true );
		
		if ( bSuccYn ) {
			this.sqlSession.commit();
		} else {
			this.sqlSession.rollback();
		}
		
		this.GUID = "";
		this.STR_TIME = 0;
		this.txState.set(TX_WAIT);
		this.bSubCall = false;
		this.subCallList.clear();
		
		return bSuccYn; 
		//this.sqlSession.clearCache();
		//this.sqlSession.close();
		//this.sqlSession = this.sqlSessionFactory.openSession( false );
	}
	
	public boolean procRollback() {
		
		txLog.debug( "*** TX Manager Rollback!!" );
		
		boolean bSuccYn = this.callCommitApi( false );
		
		this.sqlSession.rollback();
		this.GUID = "";
		this.STR_TIME = 0;
		this.txState.set(TX_WAIT);
		this.bSubCall = false;
		
		this.subCallList.clear();
		
		return bSuccYn;
		
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
	
	public Object callSubApi(String host,  String url, AppRequest sendData) {
		txLog.debug( "*** START callSubApi guid:{}, hurl:{}", this.GUID, host + url );
		sendData.getHead().setStartyn("N");
		this.subCallList.add( host );
		return apiSendModule.callRestApi(host + url, sendData);
	}
	
	private boolean callCommitApi( boolean commit ) {
		
		if ( this.subCallList == null ) {
			txLog.debug( "*** START callCommitApi subCallList is null!!" );
			return true;
		} else {
			txLog.debug( "*** START callCommitApi subCallList size:{}", this.subCallList.size() );
		}

		AppHeader head = AppHeader.builder().dat_cd("S").ip("127.0.0.1").mac("").guid( this.GUID ).startyn( "N" ).trd_cd("I").cont_trd_cont("1").opr_id("SYSTEM").sphn_os_dvsn_cd("03").build();
		//body 
		String subcall = "demo2/txproc";
		
		TxInVO inVo = new TxInVO();
		inVo.setTx_prcs_dcd( "R" );
		if ( commit ) {
			inVo.setTx_prcs_dcd( "C" );
		}
		
		AppRequest comtReq = AppRequest.builder().head( head ).body( inVo ).build();
		boolean bSuccYn = true;
		for(int i=0; i<this.subCallList.size(); i++ ) {
			String subHost = this.subCallList.get(i);
			AppResponse<TxOutVO> outvo = (AppResponse<TxOutVO>)apiSendModule.callRestApi(subHost + subcall, comtReq);
			
			//if ( !"Y".equals(outvo.getBody().getNorl_prcs_yn()) ) {
			if ( !"Y".equals(outvo.getHead().getNorl_yn()) ) {
				bSuccYn = false;
			}
		}
		
		return bSuccYn;
	}

	
    public int insert(String queryId, Object parameterObject) {
        return this.sqlSession.insert(queryId, parameterObject);
    }
	//apiSendModule.callRestApi( "http://localhost:8081/demo2/test2", inData );
}