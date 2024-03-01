package skcnc.stockcore.ca.service.vo;

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
public class EmpInqryOutVO {
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
}
