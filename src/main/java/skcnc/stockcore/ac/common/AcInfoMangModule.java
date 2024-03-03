package skcnc.stockcore.ac.common;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.MapperUtil;
import skcnc.stockcore.ac.dao.table.Acb1000Table;

@Component
public class AcInfoMangModule extends AppCommonModule {

	public Acb1000Table getAcInfoInqry( String ac_no )
	{
		Map<String,Object> acb1000 = dbio.select( "mapper.ac.acb1000mapper.selectacb1000one", ac_no );
		
		if ( acb1000 == null ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw makeException( "MYER0041", "계좌정보" );
		}
		
		Acb1000Table acb1000Vo = MapperUtil.convert(acb1000, Acb1000Table.class);
		return acb1000Vo;
	}
	
	public String getAcMaxNo()
	{
		return dbio.select( "mapper.ac.acb1000mapper.selectacb1000maxone" );
	}
	
	public void procAcInfoRegi( Acb1000Table acb1000Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(acb1000Vo);
		
		dbio.insert( "mapper.ac.acb1000mapper.insertacb1000one", insMap );
	}
	
	public boolean procAcInfoChg( Acb1000Table acb1000Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(acb1000Vo);
		int rv = dbio.update( "mapper.ac.acb1000mapper.updateacb1000one", insMap );
		
		if ( rv != 1 ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "변경 처리중 오류 발생 {0}", rv );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException( "MYER0005", "계좌정보 변경" );
		}
		return true;
	}
	
	public boolean getAcPwdCheck( String ac_no, String pwd )
	{
		Acb1000Table acb1000Vo = this.getAcInfoInqry( ac_no );
		
		if ( acb1000Vo.getEnc_pwd().equals( pwd ) )
			return true;
		return false;
	}
}
