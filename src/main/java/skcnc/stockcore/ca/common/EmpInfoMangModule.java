package skcnc.stockcore.ca.common;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.MapperUtil;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.dao.table.Caa1000Table;

@Component
public class EmpInfoMangModule extends AppCommonModule 
{
	
	public Caa1000Table getEmpInfoInqry( String emp_no )
	{
		Map<String,Object> caa100 = dbio.select( "mapper.ca.common.cmempinfomangmapper.selectcaa1000one", emp_no );
		
		if ( caa100 == null ) {
			//MYER0051=사용자ID가 존재하지 않습니다.
			throw makeException( "MYER0051" );
		}
		
		//Caa1000Table caa100Vo = new Caa1000Table();
		Caa1000Table caa100Vo = MapperUtil.convert(caa100, Caa1000Table.class);
		return caa100Vo;
	}
	
	public String getEmpMaxNo()
	{
		return dbio.select( "mapper.ca.common.cmempinfomangmapper.selectcaa1000Maxone" );
	}
	
	public boolean procEmpInfoChg( Caa1000Table caa100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(caa100Vo);
		int rv = dbio.update( "mapper.ca.common.cmempinfomangmapper.updatecaa1000one", insMap );
		
		if ( rv != 1 ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "변경 처리중 오류 발생 {0}", rv );
			//MYER0005={0} 처리중 오류가 발생했습니다.
			throw makeException( "MYER0005", "사용자 정보변경" );
		}
		return true;
	}
	
	public void procEmpInfoRegi( Caa1000Table caa100Vo )
	{
		Map<String, Object> insMap = new MetaHashMap(); 
		insMap = MapperUtil.toMap(caa100Vo);
		
		dbio.insert( "mapper.ca.common.cmempinfomangmapper.insertcaa1000one", insMap );
	}
	
	public boolean getPwdCheck( String emp_no, String pwd )
	{
		Caa1000Table caa100Vo = this.getEmpInfoInqry( emp_no );
		
		if ( caa100Vo.getEnc_pwd().equals( pwd ) )
			return true;
		return false;
	}
	

}
