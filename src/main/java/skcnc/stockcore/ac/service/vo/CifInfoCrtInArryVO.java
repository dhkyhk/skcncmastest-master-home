package skcnc.stockcore.ac.service.vo;

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
public class CifInfoCrtInArryVO {

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "연락처구분코드(01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eamil)", example = "01", allowableValues = "01,02,03,04,05,06,07")
	private String ctdt_dcd;                 //연락처구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "우편번호체계구분코드(01:거리주소,02:도로주소)", example = "01", allowableValues = "01,02")
	private String post_no_sys_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "우편번호", example = "111222")
	private String post_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "우편번호주소", example = "여의도동")
	private String post_no_adr;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "암호화우편번호상세주소", example = "전경련센터 13층")
	private String enc_post_no_ptcl_adr;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "전화지역번호", example = "010")
	private String tel_area_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "전화상세지역번호", example = "5555")
	private String tel_ptcl_area_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "암호화전화종료번호", example = "6666")
	private String enc_tel_nd_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "이메일", example = "111@skc.com")
	private String mail;
}
