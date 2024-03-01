package skcnc.stockcore.ca.common;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import skcnc.framework.common.AppCommonModule;
import skcnc.framework.common.ContextStoreHelper;
import skcnc.framework.database.MetaHashMap;
import skcnc.framework.utils.StringUtils;
import skcnc.stockcore.ca.dao.table.Caa1000Table;

@Component
public class LoginMangModule extends AppCommonModule{

	//@Autowired
	//PasswordEncoder passwordEncoder;
	
	@Autowired
	EmpInfoMangModule empInfoMangModule;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean pwdCheck( String emp_no, String pwd )
	{
		Logger log = ContextStoreHelper.getLog();
		
		if ( StringUtils.isEmpty(emp_no) ) {
			log.error( "입력값을 확인하세요 : {0}", emp_no );
			//MYER0003=입력값 {0}를 확인하세요.
			throw makeException("MYER0003", "직원번호");
		}
		
		if ( StringUtils.isEmpty(pwd) ) {
			log.error( "입력값을 확인하세요 : {0}", pwd );
			//MYER0003=입력값 {0}를 확인하세요.
			throw makeException("MYER0003", "비밀번호");
		}
		
		Caa1000Table caa100Vo = empInfoMangModule.getEmpInfoInqry( emp_no );
		
		if ( caa100Vo == null || StringUtils.isEmpty(caa100Vo.getEmp_no()) ) {
			log.error( "입력값을 확인하세요 : {0}", emp_no );
			//MYER2011={0}아닙니다
			throw makeException("MYER2011", "등록된 직원번호가 ");
		}
		
		if ( !"01".equals( caa100Vo.getHofe_dcd() ) ) {
			log.error( "재직 상태를 확인하세요 : {0}", caa100Vo );
			//MYER0004={0} 값을 확인하세요.
			throw makeException("MYER0004", "재직상태를");
		}
		
		if ( caa100Vo.getPwd_err_cnt() >= 5 ) {
			log.error( "비밀번호 오류 횟수를 확인하세요 : {0}", caa100Vo );
			//MYER0004={0} 값을 확인하세요.
			throw makeException("MYER0004", "비밀번호 오류 횟수를");
		}
		
		Map<String, Object> insMap = new MetaHashMap();
		insMap.put( "emp_no", emp_no );
		
		//String enc_pwd = passwordEncoder.encode( pwd );
		
		if ( caa100Vo.getEnc_pwd().equals( pwd ) ) {
		//if ( passwordEncoder.matches(enc_pwd, caa100Vo.getEnc_pwd()) ) {
			if ( caa100Vo.getPwd_err_cnt() > 0 ) {
				insMap.put( "pwd_err_cnt", 0 );
				dbio.update( "mapper.ca.common.cmempinfomangmapper.updatecaa1000errcnt", insMap );
			}
			return true;
		}
		
		insMap.put( "emp_no", emp_no );
		insMap.put( "pwd_err_cnt", caa100Vo.getPwd_err_cnt() + 1 );
		dbio.update( "mapper.ca.common.cmempinfomangmapper.updatecaa1000errcnt", insMap );
		
		return false;
	}
}
