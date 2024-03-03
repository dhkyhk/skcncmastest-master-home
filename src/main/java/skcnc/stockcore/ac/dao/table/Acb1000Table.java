package skcnc.stockcore.ac.dao.table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*계좌_계좌기본*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Acb1000Table {

	private String ac_no;
	private String prd_typ_dcd;
	private String ac_stat_dcd;
	private String ac_opn_dt;
	private String ac_mang_dept_no;
	private String ac_mang_emp_no;
	private String ac_prft_dept_no;
	private String ac_opn_dept_no;
	private String bill_tati_yn;
	private String trtx_tati_yn;
	private String dvtx_tati_yn;
	private String cma_ac_yn;
	private String sly_ac_yn;
	private String ac_nknm_nm;
	private String trd_prp_dcd;
	private String mny_sur_dcd;
	private String enc_pwd;
	private String pwd_regi_dt;
	private Long   pwd_err_cnt;
	private String cif;
	
	private Date opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
