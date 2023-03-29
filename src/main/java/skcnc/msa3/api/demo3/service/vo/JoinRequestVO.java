package skcnc.msa3.api.demo3.service.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class JoinRequestVO {

    @Schema(description = "Event Type", defaultValue = "eventType")
    private String eventType;

    @Schema(description = "아이디", defaultValue = "testId")
    public String id;
    @Schema(description = "비밀번호", defaultValue = "testPassword")
    public String password;
}
