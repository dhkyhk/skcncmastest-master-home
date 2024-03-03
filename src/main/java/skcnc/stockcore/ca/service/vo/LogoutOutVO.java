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
public class LogoutOutVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "사원번호", example = "08963")
	private String emp_no;
}
