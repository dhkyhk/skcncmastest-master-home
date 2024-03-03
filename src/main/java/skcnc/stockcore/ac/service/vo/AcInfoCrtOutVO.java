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
public class AcInfoCrtOutVO {
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌번호(11자리)", example = "10000000001")
	private String ac_no;

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "정상처리여부", example = "Y")
    private String norl_prcs_yn;
}
