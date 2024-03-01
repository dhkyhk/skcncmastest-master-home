package skcnc.stockcore.ca.dao.table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Caa1000Table {
	private String emp_no;
	private String hofe_dcd;
	private String joco_dt;
	private String retco_dt;

	private String emp_nm;
	private String mail;
	private String tel_area_no;
	private String tel_ptcl_area_no;
	private String enc_tel_nd_no;
	
	private String enc_pwd;
	private String pwd_regi_dt;
	private Long pwd_err_cnt;
	private String dept_no;
	
	private Date opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
