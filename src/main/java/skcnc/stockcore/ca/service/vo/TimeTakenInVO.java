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
public class TimeTakenInVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "계좌번호(11자리)", example = "10000000001")
	private String ac_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "주문수량", example = "100")
	private Long ord_qnt;
}
