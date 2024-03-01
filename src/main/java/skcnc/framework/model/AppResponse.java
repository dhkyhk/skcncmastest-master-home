package skcnc.framework.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import skcnc.framework.utils.DateUtil;
import skcnc.framework.utils.MapperUtil;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Schema(description = "모든 응답의 기본 구조 데이터(ch : 공통헤더, data : 각 API 별 응답 VO)")
public class AppResponse<T> implements Serializable {
    private static final long serialVersionUID = -5235244182348661151L;

    @Schema(description = "공통 헤더 값", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppHeader head;

    @Schema(description = "응답 데이터 body", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private T body;

    static final String FILE_INOUT = "FILE_INOUT";

    /**
     * @Method Name : create
     * @description : 응답 객체 생성
     * @param <T>    : 응답 데이터 본문
     * @param inData : 요청시 데이터
     * @param rtCd   : 응답 코드
     * @param msgCd  : 응답 메시지 코드
     * @param msg    : 응답 메시지
     * @param body   : 응답 데이터 본문 객체
     * @return
     */
    public static <T> AppResponse<T> create(AppRequest<?> inData, String rtCd, String msgCd, String msg, T body) {
        AppHeader ch = null;
        if(inData != null) {
            ch = MapperUtil.convert(inData.getHead(), AppHeader.class);
        }
        return create(ch, rtCd, msgCd, msg, body);

    }

    /**
     * @Method Name : create
     * @description : 응답 객체 생성
     * @param <T>   : 응답 데이터 본문
     * @param ch    : 응답에 실어 보낼 공통 헤더 객체
     * @param rtCd   : 응답 코드
     * @param msgCd  : 응답 메시지 코드
     * @param msg    : 응답 메시지
     * @param body   : 응답 데이터 본문 객체
     * @return
     */
    public static <T> AppResponse<T> create(AppHeader ch, String rtCd, String msgCd, String msg, T body) {

        //Logger log = ContextStoreHelper.getLog();
        //log.debug( "AppResponse - create START " );

        if(ch== null) {
        	ch = AppHeader.builder().build();
        }
        ch.setDat_cd("R");//S:Request R:Response, 
        ch.setNorl_yn(rtCd);
        
        ch.setMsg_cd(msgCd);
        ch.setMsg_txt(msg);
        ch.setSysdate( DateUtil.getCurrentDatetimemilli() );

        return AppResponse.<T>builder()
                .head(ch)
                .body(body)
                .build();
    }
}
