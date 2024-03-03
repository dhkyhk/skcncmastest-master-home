package skcnc.stockcore.ac.common;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.MapperUtil;
import skcnc.stockcore.ac.dao.table.Aca1000Table;

@Component
public class CsfInfoMangModule extends AppCommonModule {

	public Aca1000Table getCifInfoInqry( String cif )
	{
		Map<String,Object> aca1000 = dbio.select( "mapper.ac.aca1000mapper.selectcaa1000one", cif );
		
		if ( aca1000 == null ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw makeException( "MYER0041", "고객정보" );
		}
		
		Aca1000Table aca1000Vo = MapperUtil.convert(aca1000, Aca1000Table.class);
		return aca1000Vo;
	}
	
	public String getCifMaxNo()
	{
		return dbio.select( "mapper.ac.aca1000mapper.selectaca1000maxone" );
	}
	
	public void procCifInfoRegi( Aca1000Table aca1000Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(aca1000Vo);
		
		dbio.insert( "mapper.ac.aca1000mapper.insertaca1000one", insMap );
	}
	
	public void procCifInfoChg( Aca1000Table aca1000Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(aca1000Vo);
		int rv = dbio.update( "mapper.ac.aca1000mapper.updateaca1000one", insMap );
		
		if ( rv != 1 ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "변경 처리중 오류 발생 {0}", rv );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException( "MYER0005", "고객정보 변경" );
		}
	}
	
	public boolean getPwdCheck( String cif, String pwd )
	{
		Aca1000Table aca1000Vo = this.getCifInfoInqry( cif );
		
		if ( aca1000Vo.getEnc_pwd().equals( pwd ) )
			return true;
		return false;
	}
}
