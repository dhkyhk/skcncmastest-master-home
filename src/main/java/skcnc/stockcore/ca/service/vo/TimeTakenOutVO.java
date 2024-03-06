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
public class TimeTakenOutVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "정상처리여부", example = "Y")
    private String norl_prcs_yn;
}
