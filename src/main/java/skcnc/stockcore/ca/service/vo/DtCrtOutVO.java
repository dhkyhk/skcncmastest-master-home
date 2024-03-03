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
public class DtCrtOutVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "현재일자", example = "20240101")
	private String prs_dt;
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "정상처리여부", example = "Y", allowableValues = "Y,N")
	private String norl_prcs_yn;
}
