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
public class LoginOutVO {
	private String emp_no;
	private String hofe_dcd;
	private String emp_nm;
	private String mail;
	private String tel_area_no;
	private String tel_ptcl_area_no;
	private String enc_tel_nd_no;
	private String dept_no;
	private String token;
}
