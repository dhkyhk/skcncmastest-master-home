package skcnc.stockcore.ac.service.vo;

import java.util.List;

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
public class AcInfoCrtInVO {

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "작업구분(I:입력 U:변경 D:삭제)", example = "I", allowableValues = "I,U,D")
	private String wrk_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌번호(11자리)", example = "10000000001")
	private String ac_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "상품유형구분코드(01:종합투자,11:선물옵션)", example = "01", allowableValues = "01,11")
	private String prd_typ_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "계좌상태구분코드(01:정상,02:폐쇄)", example = "01", allowableValues = "01,02")
	private String ac_stat_dcd;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌관리부서번호", example = "1000")
	private String ac_mang_dept_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌관리직원번호", example = "08963")
	private String ac_mang_emp_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌실적부서번호", example = "1000")
	private String ac_prft_dept_no;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌개설부서번호", example = "08963")
	private String ac_opn_dept_no;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이용료과세여부", example = "Y", allowableValues = "Y,N")
	private String bill_tati_yn;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "거래세과세여부", example = "Y", allowableValues = "Y,N")
	private String trtx_tati_yn;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "배당세과세여부", example = "Y", allowableValues = "Y,N")
	private String dvtx_tati_yn;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "CMA계좌여부", example = "Y", allowableValues = "Y,N")
	private String cma_ac_yn;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "월급계좌여부", example = "Y", allowableValues = "Y,N")
	private String sly_ac_yn;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "계좌별칭명", example = "계좌별칭")
	private String ac_nknm_nm;
	
	//TODO: 거래목적코드 등록하자 01:생활비관리. 02:투자. 등등..
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "거래목적구분코드", example = "01")
	private String trd_prp_dcd;
	
	//TODO: 자금출처구분코드 등록하자 01:월급 02:
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "자금출처구분코드", example = "01")
	private String mny_sur_dcd;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "암호화비밀번호", example = "1111")
	private String enc_pwd;

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "고객식별번호(10자리)", example = "1000000001")
	private String cif;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "통보처정보")
	List<AcInfoCrtInArryVO> subvo;
}
