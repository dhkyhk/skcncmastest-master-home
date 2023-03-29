package skcnc.msa3.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Schema(description = "APP 공통 헤더 정보로 요청/응답에 항상 적용되며, 일부는 요청의 정보를 그대로 회신한다.")
public class AppHeader {
    public static final String ATTR_KEY = "REQUEST_APP_HEADER";
    public static final String RT_SUCCESS = "success";
    public static final String RT_FAIL = "fail";

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "거래유형(R : Request, S : Response)", example = "R", allowableValues = "R, S")
    private String tr_type;

    @Schema(description = "스마트폰 IP", example = "127.0.0.1")
    private String ip;

    @Schema(description = "스마트폰 MAC 주소", example = "TEMP_MAC")
    private String mac;

    @Schema(description = "Global UID", example = "GUID")
    private String guid;

    @Schema(description = "화면 번호", example = "화면 ID", requiredMode = Schema.RequiredMode.AUTO, accessMode = Schema.AccessMode.READ_ONLY )
    private String scr_no;

    @Schema(description = "연속 거래 여부(B : 이전데이터, N : 다음데이터)", example="N", allowableValues = "B, N")
    private String tr_cont;

    @Schema(description = "HTS ID", example = "HTS ID")
    private String hts_id;

    @Schema(description = "스마트폰 OS 구분 코드 ID(01 Android, 02 iOS, 03 기타)", example = "01", allowableValues = "01, 02, 03")
    private String sphn_os_dvsn_cd;

    @Schema(description = "응답전용 - 반환 코드(정상응답 = " + RT_SUCCESS + ", 시스템 오류시 = " + RT_FAIL + ")", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String rt_cd;

    @Schema(description = "응답전용 - 메시지 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String msg_cd;

    @Schema(description = "응답전용 - 메시지 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String msg;

    @Schema(description = "응답전용 - 현재시간", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String sysdate;

    //내부에서 사용되는 값으로 헤더의 인증토큰에서 발췌하여 할당됨(in/out 으로 나가지 않도록 한다.)
    @JsonIgnore
    private String msa_id;
}
