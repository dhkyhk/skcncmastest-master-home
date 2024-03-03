package skcnc.stockcore.ac.dao.table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* 계좌_고객기본 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Aca1000Table {
	private String cif;
	private String cs_nm;
	private String cs_strt_eng_nm;
	private String cs_nd_eng_nm;
	private String rlnm_cnf_no_dcd;
	private String enc_rlnm_no;
	private String rlnm_cnf_yn;
	private String rlnm_cnf_dt;
	private String birh_dt;
	private String cs_typ_dcd;
	private String nat_frgn_dcd;
	private String sex_dcd;
	private String cust_id;
	private String enc_pwd;
	private String pwd_regi_dt;
	private Long   pwd_err_cnt;
	private String mang_dept_no;
	private String inco_dcd;
	private String oval_tati_typ_dcd;
	private String kor_stex_invt_dec;
	private String cs_rgs_dt;
	private String cs_grd_cd;
	private String crdt_por_dcd;
	private String acid_regi_yn;
	private String nana_nati_dcd;
	private String resd_nati_dcd;
	private String prio_tel_ctdt_dcd;
	private String prio_post_ctdt_dcd;
	private String sms_dnwa_dcd;
	private String emal_dnwa_dcd;
	private String job_dcd;

	private Date opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
