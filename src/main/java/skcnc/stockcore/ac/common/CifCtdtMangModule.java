package skcnc.stockcore.ac.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.MapperUtil;
import skcnc.stockcore.ac.dao.table.Aca1100Table;

@Component
public class CifCtdtMangModule extends AppCommonModule {
	
	public Aca1100Table getCifCtdInqry( String cif, String ctdt_dcd )
	{
		Map<String, Object> insMap = new MetaHashMap();
		insMap.put( "cif", cif);
		insMap.put( "ctdt_dcd", ctdt_dcd);
		
		Map<String,Object> aca1100 = dbio.select( "mapper.ac.aca1100mapper.selectaca1100one", insMap );
		
		if ( aca1100 == null ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw makeException( "MYER0041", "고객연락처 정보" );
		}
		
		Aca1100Table aca1100Vo = MapperUtil.convert(aca1100, Aca1100Table.class);
		return aca1100Vo;
	}
	
	public List<Aca1100Table> getCifCtdArryInqry( String cif )
	{
		Map<String, Object> insMap = new MetaHashMap();
		insMap.put( "cif", cif);
		
		List<Map<String,Object>> aca1100 = dbio.selectList( "mapper.ac.aca1100mapper.selectaca1100arry", insMap );
		
		if ( aca1100 == null || aca1100.size() == 0 ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw makeException( "MYER0041", "고객연락처 정보" );
		}
		
		//Caa1000Table caa100Vo = new Caa1000Table();
		List<Aca1100Table> aca1100ArryVo = new ArrayList<Aca1100Table>();
		
		for(Map<String,Object> cifctd : aca1100 ) {
			Aca1100Table aca1100vo = MapperUtil.convert(cifctd, Aca1100Table.class);
			aca1100ArryVo.add(aca1100vo);
		}
				
		return aca1100ArryVo;
	}
	
	public String getCifCtdMaxNo( String cif, String ctdt_dcd )
	{
		Map<String, Object> insMap = new MetaHashMap();
		insMap.put( "cif", cif);
		insMap.put( "ctdt_dcd", ctdt_dcd);
		return dbio.select( "mapper.ac.aca1100mapper.selectaca1100maxone", insMap );
	}
	
	public void procCifCtdRegi( Aca1100Table aca1100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(aca1100Vo);
		
		dbio.insert( "mapper.ac.aca1100mapper.insertaca1100one", insMap );
	}
	
	public boolean procCifCtdChg( Aca1100Table aca1100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(aca1100Vo);
		int rv = dbio.update( "mapper.ac.aca1100mapper.updateaca1100one", insMap );
		
		if ( rv != 1 ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "변경 처리중 오류 발생 {0}", rv );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException( "MYER0005", "고객연락처 변경" );
		}
		return true;
	}

}
