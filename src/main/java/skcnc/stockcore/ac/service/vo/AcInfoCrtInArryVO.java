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
public class AcInfoCrtInArryVO {
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보작업구분코드(01:이체,02:매매,03:고객정보변경,04:계좌정보변경,05:매체변경,99:기타)", example = "01", allowableValues = "01,02,03,04,05,99")
	private String notf_wrk_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보연락처구분코드(01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eamil)", example = "01", allowableValues = "01,02,03,04,05,06,07")
	private String notf_ctdt_dcd;
}
