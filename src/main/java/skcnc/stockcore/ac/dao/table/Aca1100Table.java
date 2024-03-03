package skcnc.stockcore.ac.dao.table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* 계좌_고객연락처기본 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Aca1100Table {
	private String cif;
	private String ctdt_dcd;
	private Long   regi_sno;
	private String post_no_sys_dcd;
	private String post_no;
	private String post_no_adr;
	private String enc_post_no_ptcl_adr;
	private String tel_area_no;
	private String tel_ptcl_area_no;
	private String enc_tel_nd_no;
	private String mail;
	private String use_yn;

	private Date opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
