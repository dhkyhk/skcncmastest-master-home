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
public class PrcsNotfInVO {
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "고객식별번호", example = "1000000001")
	private String cif;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌번호", example = "10000000000")
	private String ac_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보연락처구분코드(01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eamil)", example = "01", allowableValues = "01,02,03,04,05,06,07")
	private String notf_ctdt_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보내용", example = "통보내용!!")
	private String notf_txt;
}
