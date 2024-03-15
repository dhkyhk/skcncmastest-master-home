package skcnc.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Schema(description = "APP 공통 헤더 정보로 요청/응답에 항상 적용되며, 일부는 요청의 정보를 그대로 회신한다.")
public class AppHeader {
    public static final String ATTR_KEY = "REQUEST_APP_HEADER";
    public static final String RT_SUCCESS = "Y";
    public static final String RT_FAIL = "N";

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "요청응답구분 (S : Request, R : Response)", example = "S", allowableValues = "R, S")
    private String dat_cd;

    @Schema(description = "스마트폰 IP", example = "127.0.0.1")
    private String ip;

    @Schema(description = "스마트폰 MAC 주소", example = "TEMP_MAC")
    private String mac;

    @Schema(description = "Global UID", example = "GUID")
    private String guid;
    
    @Schema(description = "처음 호출시 Y, 서비스간 호출시 N", example = "Y")
    private String startyn;
    
    @Schema(description = "거래 분류 Q:조회 I:입력", example = "Q", allowableValues = "Q, I" )
    private String trd_cd;

    @Schema(description = "연속 거래정보를 관리 요청시(1:처음조회 2:연속조회 3:마지막조회) 응답시(2:연속거래가능 3:연속거래없음)", example="1", allowableValues = "1, 2,3")
    private String cont_trd_cont;

    @Schema(description = "직원사번 또는 HTS ID", example = "HTS ID")
    private String opr_id;

    @Schema(description = "스마트폰 OS 구분 코드 ID(01:Android, 02:iOS, 03:기타)", example = "01", allowableValues = "01, 02, 03")
    private String sphn_os_dvsn_cd;

    @Schema(description = "응답전용 - 정상처리여부)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String norl_yn;

    @Schema(description = "응답전용 - 메시지 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String msg_cd;

    @Schema(description = "응답전용 - 메시지 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String msg_txt;

    @Schema(description = "응답전용 - 현재시간", requiredMode = Schema.RequiredMode.NOT_REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    private String sysdate;

    //내부에서 사용되는 값으로 헤더의 인증토큰에서 발췌하여 할당됨(in/out 으로 나가지 않도록 한다.)
    @JsonIgnore
    private String msa_id;
}
