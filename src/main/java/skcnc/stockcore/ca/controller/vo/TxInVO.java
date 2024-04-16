package skcnc.stockcore.ca.controller.vo;

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
public class TxInVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "처리구분 C:commit R:rollback", example = "c")
	private String tx_prcs_dcd;
}
