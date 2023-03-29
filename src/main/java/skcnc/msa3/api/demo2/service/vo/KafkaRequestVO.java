package skcnc.msa3.api.demo2.service.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KafkaRequestVO {
    @Schema(description = "Event Type", defaultValue = "eventType")
    private String eventType;
}
