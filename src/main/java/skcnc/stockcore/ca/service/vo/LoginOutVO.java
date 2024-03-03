package skcnc.stockcore.ca.service.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "사원번호", example = "08963")
	private String emp_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "재직구분코드(01:재직 02:퇴직 03:휴직)", example = "01", allowableValues = "01,02,03")
	private String hofe_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "직원명", example = "홍길동")
	private String emp_nm;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이메일", example = "1111@222.com")
	private String mail;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "전화지역번호", example = "010")
	private String tel_area_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "전화상세지역번호", example = "5555")
	private String tel_ptcl_area_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "암호화전화종료번호", example = "6666")
	private String enc_tel_nd_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "부서번호", example = "1000")
	private String dept_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "토큰")
	private String token;
}
