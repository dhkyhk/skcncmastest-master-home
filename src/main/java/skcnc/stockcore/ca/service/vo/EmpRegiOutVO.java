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
public class EmpRegiOutVO {
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "사원번호", example = "08963")
	private String emp_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "정상처리여부", example = "Y")
	private String norl_prcs_yn;
}
