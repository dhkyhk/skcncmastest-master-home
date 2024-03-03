package skcnc.stockcore.ca.common;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import skcnc.framework.common.AppCommonException;
import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.DateUtil;
import skcnc.framework.utils.StringUtils;

@Component
public class DtMangModule extends AppCommonModule {
	
	/**
	 * 작업일자 구분에 맞게 일자 설정
	 * 작업일자구분코드(01:영업일	02:거래일	03:개장일	04:작업기준일	05:결제일)
	 * @param wrk_dt_dcd
	 */
	public void setDt( String wrk_dt_dcd )
	{
		try { 
			dbio.delete( "mapper.ca.common.cad1100mapper.deletecad1100multi", wrk_dt_dcd );
			
			String basedt = DateUtil.getCurrentDate();
			int weekIdx = 0;
			
			for( int i=0; i< 10; i++ ) {
				weekIdx = DateUtil.getWeekday(basedt);
				//04:작업 기준일만 공휴일 없이 처리하고 그외에는 공휴일 체크하자.. -
				if ( ( weekIdx == 0 || weekIdx == 6 ) && !("04").equals(wrk_dt_dcd) ) {
					basedt = DateUtil.addDay(basedt, -1);
					continue;
				}
			}
			
			String bef_4_dt = "";
			String bef_3_dt = "";
			String bef_2_dt = "";
			String bef_dt = "";
			String prs_dt = basedt;
			String aft_dt = "";
			String aft_2_dt = "";
			String aft_3_dt = "";
			String aft_4_dt = "";
			
			String tmpdt = basedt;
			for( int i=0; i<4; i++ ) 
			{
				tmpdt = DateUtil.addDay(tmpdt, -1);
				weekIdx = DateUtil.getWeekday(tmpdt);
				
				if ( ( weekIdx == 0 || weekIdx == 6 ) && !("04").equals(wrk_dt_dcd) ) {
					i--;
					continue;
				} 
				
				switch (i) {
				case 0:
					bef_dt = tmpdt;
					break;
				case 1:
					bef_2_dt = tmpdt;
					break;
				case 2:
					bef_3_dt = tmpdt;
					break;
				case 3:
					bef_4_dt = tmpdt;
					break;
				default:
					break;
				}
			}
			
			tmpdt = basedt;
			for( int i=0; i<4; i++ ) 
			{
				tmpdt = DateUtil.addDay(tmpdt, 1);
				weekIdx = DateUtil.getWeekday(tmpdt);
				
				if ( ( weekIdx == 0 || weekIdx == 6 ) && !("04").equals(wrk_dt_dcd) ) {
					i--;
					continue;
				} 
				
				switch (i) {
				case 0:
					aft_dt = tmpdt;
					break;
				case 1:
					aft_2_dt = tmpdt;
					break;
				case 2:
					aft_3_dt = tmpdt;
					break;
				case 3:
					aft_4_dt = tmpdt;
					break;
				default:
					break;
				}
			}
			
			Map<String, Object> insMap = new MetaHashMap();
			insMap.put( "wrk_dt_dcd", wrk_dt_dcd);
			insMap.put( "bef_4_dt", bef_4_dt);
			insMap.put( "bef_3_dt", bef_3_dt);
			insMap.put( "bef_2_dt", bef_2_dt);
			insMap.put( "bef_dt", bef_dt);
			insMap.put( "prs_dt", prs_dt);
			insMap.put( "aft_dt", aft_dt);
			insMap.put( "aft_2_dt", aft_2_dt);
			insMap.put( "aft_3_dt", aft_3_dt);
			insMap.put( "aft_4_dt", aft_4_dt);
			
			dbio.insert( "mapper.ca.common.cad1100mapper.insertcad1100one", insMap );
			
		} catch ( AppCommonException e ) {
			throw e;
		} catch ( Exception e ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "ERROR : ", e );
			//MYER0007=처리중 오류가 발생했습니다.
			throw makeException("MYER0007");
		}
	}
	
	public String getBsnDt( String wrk_dt_dcd )
	{
		Map<String, Object> rvMap = dbio.select( "mapper.ca.common.cad1100mapper.selectcad1100one", wrk_dt_dcd );
		
		if ( rvMap == null || StringUtils.isEmpty(rvMap.get("prs_dt").toString()) ) {
			return null;
		}
		
		return rvMap.get("prs_dt").toString();
	}
	
	public Map<String, Object> getBsnDtMap( String wrk_dt_dcd )
	{
		return dbio.select( "mapper.ca.common.cad1100mapper.selectcad1100one", wrk_dt_dcd );
	}
}
