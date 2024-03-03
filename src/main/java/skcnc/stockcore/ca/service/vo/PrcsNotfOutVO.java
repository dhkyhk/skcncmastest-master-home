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
public class PrcsNotfOutVO {

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보연락처구분코드(01:이체,02:매매,03:고객정보변경,04:계좌정보변경,05:매체변경,99:기타)", example = "01", allowableValues = "01,02,03,04,05,99")
	private String notf_ctdt_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "정상처리여부", example = "Y")
	private String norl_prcs_yn;
}
