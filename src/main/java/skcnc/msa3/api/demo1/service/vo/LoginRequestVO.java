package skcnc.msa3.api.demo1.service.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class LoginRequestVO {

    @Schema(description = "아이디", defaultValue = "testId", required=true)
    public String id;
    @Schema(description = "비밀번호", defaultValue = "testPassword", required=true)
    public String password;

}
