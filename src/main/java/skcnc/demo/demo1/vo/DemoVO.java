package skcnc.demo.demo1.vo;

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
public class DemoVO {
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "작업구분(I:입력 U:변경 D:삭제)", example = "I", allowableValues = "I,U,D")
	private String wrk_dcd;
}
