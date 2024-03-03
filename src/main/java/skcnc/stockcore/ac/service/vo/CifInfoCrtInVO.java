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
public class CifInfoCrtInVO {

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "작업구분(I:입력 U:변경 D:삭제)", example = "I", allowableValues = "I,U,D")
	private String wrk_dcd;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "고객식별번호 10자리", example = "1000000001")
    private String cif;
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "고객명", example = "홍길동")
    private String cs_nm;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED , description = "영문 고객 이름", example = "gildong")
    private String cs_strt_eng_nm;
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "영문 고객 성", example = "hong")
    private String cs_nd_eng_nm;        //고객종료영문명
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "실명확인번호구분코드(01:주민등록증,02:운전면허증,03:외국인투자등록증,04:사업자등록번호,05:법인등록번호)", example = "01", allowableValues = "01,02,03,04,05")
	private String rlnm_cnf_no_dcd;     //실명확인번호구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "암호화실명번호", example = "9001012020011")
    private String enc_rlnm_no;         //암호화실명번호
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "실명확인여부", example = "20240301")
    private String rlnm_cnf_yn;         //실명확인여부
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "실명확인일자", example = "20240301")
    private String rlnm_cnf_dt;         //실명확인일자
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "생일일자", example = "20240301")
    private String birh_dt;             //생일일자
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "고객유형구분코드(01:개인,02:법인)", example = "01", allowableValues = "01,02")
    private String cs_typ_dcd;          //고객유형구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "내국인외국인구분코드(01:내국인,02:외국인)", example = "01", allowableValues = "01,02")
    private String nat_frgn_dcd;        //내국인외국인구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "성별구분코드(01:남자,02:여자)", example = "01", allowableValues = "01,02")
    private String sex_dcd;             //성별구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "고객ID", example = "HTS ID")
    private String cust_id;             //고객ID
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "암호화비밀번호", example = "1111")
    private String enc_pwd;             //암호화비밀번호
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "관리부서번호", example = "1000")
    private String mang_dept_no;        //관리부서번호
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "소득구분코드", example = "1000")
    private String inco_dcd;            //소득구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "종합과세유형구분코드", example = "1000")
    private String oval_tati_typ_dcd;   //종합과세유형구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "한국거래소투자자구분코드", example = "1000")
    private String kor_stex_invt_dec;   //한국거래소투자자구분코드

	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "국적국가구분코드", example = "410")
    private String nana_nati_dcd;       //국적국가구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "거주국가구분코드", example = "410")
    private String resd_nati_dcd;       //거주국가구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "우선전화연락처구분코드(01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eamil)", example = "01", allowableValues = "01,02,03,04,05,06,07")
    private String prio_tel_ctdt_dcd;   //우선전화연락처구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "우선우편연락처구분코드(01:집주소,02:집전화,03:회사주소,04:회사전화,05:FAX,06:휴대폰,07:eamil)", example = "01", allowableValues = "01,02,03,04,05,06,07")
    private String prio_post_ctdt_dcd;  //우선우편연락처구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "SMS불원구분코드(01:희망 02:불원)", example = "01", allowableValues = "01,02")
    private String sms_dnwa_dcd;        //SMS불원구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이메일불원구분코드(01:희망 02:불원)", example = "01", allowableValues = "01,02")
    private String emal_dnwa_dcd;       //이메일불원구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "직업구분코드", example = "10000")
    private String job_dcd;             //직업구분코드
	
	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "고객연락처")
	List<CifInfoCrtInArryVO> sub_arryvo;

}
