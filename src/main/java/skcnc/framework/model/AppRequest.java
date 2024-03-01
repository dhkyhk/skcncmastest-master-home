package skcnc.framework.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Schema(description = "모든 요청의 기본 구조 데이터(ch : 공통헤더, data : 각 API 별 요청 VO)")
public class AppRequest<T> {
    @Schema(description = "공통 헤더 값", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppHeader head;

    @Schema(description = "요청 데이터 body 부로 각 API 명세에 따른 VO 형식", requiredMode = Schema.RequiredMode.REQUIRED)
    private T body;
}