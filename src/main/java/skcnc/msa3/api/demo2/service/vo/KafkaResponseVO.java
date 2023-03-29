package skcnc.msa3.api.demo2.service.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KafkaResponseVO {
    @Schema(description = "성공여부")
    private String succYn;

    @Schema(description = "오류시 에러 내용")
    private String errMsg;
}
