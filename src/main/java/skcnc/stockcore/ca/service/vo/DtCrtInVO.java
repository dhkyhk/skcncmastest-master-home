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
public class DtCrtInVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "작업일자구분코드(01:영업일	02:거래일	03:개장일	04:작업기준일	05:결제일)", example = "01", allowableValues = "01,02,03,04,05")
	private String wrk_dt_dcd;
}
