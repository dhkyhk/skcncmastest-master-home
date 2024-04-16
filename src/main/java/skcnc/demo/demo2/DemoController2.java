package skcnc.demo.demo2;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import skcnc.demo.demo1.vo.DemoVO;
import skcnc.framework.common.AppCommonController;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.model.AppRequest;
import skcnc.framework.model.AppResponse;
import skcnc.framework.txmang.TxDbConnect;
import skcnc.framework.txmang.TxMangMain;
import skcnc.stockcore.ca.controller.vo.TxInVO;
import skcnc.stockcore.ca.controller.vo.TxOutVO;

@Api(tags = "Demo Controller" )
@RestController
@RequestMapping("demo2")
public class DemoController2 extends AppCommonController{

	@Autowired
	DemoService2 demoService2;
	
    @Autowired
    private TxMangMain txmang;
	
	@Operation(summary = "Demo2", description = "Demo2")
	@PostMapping("/test2")
	public AppResponse<DemoVO> procTest2(@RequestBody AppRequest<DemoVO> inData)
	{
		//initTxManager();
		return demoService2.procTest2(inData);
		//return makeResponse(inData.getHead(), inData.getBody(), "MYOK1001", "DEMO" );
	}

	
	@Operation(summary = "commit", description = "API 동시 commit/rollback")
	@PostMapping("txproc")
	public AppResponse<TxOutVO> apicommit(@RequestBody AppRequest<TxInVO> inData)
	{
		Logger log = ContextStoreHelper.getLog();
		String norl_prcs_yn = "N";
		try { 
			
			//TxDbConnect clientTx = ContextStoreHelper.getData( ContextStoreHelper.TX_CLIENT, TxDbConnect.class );
			
			
			/*
			TxDbConnect clientTx = txmang.getTxGuidDB( inData.getHead().getGuid() );
			
			if ( clientTx != null ) {
				if ( "C".equals(inData.getBody().getTx_prcs_dcd()) ) {
					clientTx.procCommit();
				} else {
					clientTx.procRollback();
				}
			}*/
			if ( txmang.getTxAftProc( inData.getHead().getGuid(), inData.getBody().getTx_prcs_dcd() ) );
				norl_prcs_yn = "Y";
		} catch ( RuntimeException e ) {
			TxDbConnect clientTx = ContextStoreHelper.getData( ContextStoreHelper.TX_CLIENT, TxDbConnect.class );
			clientTx.procRollback();
			log.error( "ERROR", e );
		}
		
		TxOutVO outVo = TxOutVO.builder().norl_prcs_yn( norl_prcs_yn ).build();
		//MYOK1001={0} 정상 처리 되었습니다 
		return makeResponse(inData.getHead(), outVo, "MYOK1001", "Test가" );
	}
}
