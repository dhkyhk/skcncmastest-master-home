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
import skcnc.stockcore.ac.dao.table.Acb1100Table;

@Component
public class AcNotfMangModule extends AppCommonModule {

	public Acb1100Table getAcNotfInqry( String ac_no, String notf_wrk_dcd )
	{
		Map<String, Object> insMap = new MetaHashMap();
		insMap.put( "ac_no", ac_no);
		insMap.put( "notf_wrk_dcd", notf_wrk_dcd);
		
		Map<String,Object> acb1100 = dbio.select( "mapper.ac.acb1100mapper.selectacb1100one", insMap );
		
		if ( acb1100 == null ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw null;
		}
		
		Acb1100Table acb1100Vo = MapperUtil.convert(acb1100, Acb1100Table.class);
		return acb1100Vo;
	}
	
	public List<Acb1100Table> getAcNotfArryInqry( String ac_no )
	{
		List<Map<String,Object>> acb1100 = dbio.selectList( "mapper.ac.acb1100mapper.selectacb1100arry", ac_no );
		
		if ( acb1100 == null || acb1100.size() == 0 ) {
			//MYER0041={0}가 존재하지 않습니다.
			throw makeException( "MYER0041", "계좌 통보처 정보" );
		}
		
		List<Acb1100Table> acb1100ArryVo = new ArrayList<Acb1100Table>();
		
		for(Map<String,Object> cifctd : acb1100 ) {
			Acb1100Table acb1100vo = MapperUtil.convert(cifctd, Acb1100Table.class);
			acb1100ArryVo.add(acb1100vo);
		}
				
		return acb1100ArryVo;
	}
	
	public void procAcNotfRegi( Acb1100Table acb1100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(acb1100Vo);
		
		dbio.insert( "mapper.ac.acb1100mapper.insertacb1100one", insMap );
	}
	
	public boolean procAcNotfChg( Acb1100Table acb1100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(acb1100Vo);
		int rv = dbio.update( "mapper.ac.acb1100mapper.updateacb1100one", insMap );
		
		if ( rv != 1 ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "변경 처리중 오류 발생 {0}", rv );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException( "MYER0005", "계좌통보처 변경" );
		}
		return true;
	}
}
