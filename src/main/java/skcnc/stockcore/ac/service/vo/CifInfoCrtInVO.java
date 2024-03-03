package skcnc.stockcore.ac.service.vo;

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

    //작업구분.
	private String wrk_dcd;

    private String cif;                 //고객식별번호
    private String cs_nm;               //고객명
    private String cs_strt_eng_nm;      //고객시작영문명
    private String cs_nd_eng_nm;        //고객종료영문명
    private String rlnm_cnf_no_dcd;     //실명확인번호구분코드
    private String enc_rlnm_no;         //암호화실명번호
    private String rlnm_cnf_yn;         //실명확인여부
    private String rlnm_cnf_dt;         //실명확인일자
    private String birh_dt;             //생일일자
    private String cs_typ_dcd;          //고객유형구분코드
    private String nat_frgn_dcd;        //내국인외국인구분코드
    private String sex_dcd;             //성별구분코드
    private String cust_id;             //고객ID
    private String enc_pwd;             //암호화비밀번호
    private String pwd_regi_dt;         //비밀번호등록일자
    private Long   pwd_err_cnt;         //비밀번호오류횟수
    private String mang_dept_no;        //관리부서번호
    private String inco_dcd;            //소득구분코드
    private String oval_tati_typ_dcd;   //종합과세유형구분코드
    private String kor_stex_invt_dec;   //한국거래소투자자구분코드
    private String cs_rgs_dt;           //고객등록일자
    private String cs_grd_cd;           //고객등급코드
    private String crdt_por_dcd;        //신용불량구분코드
    private String acid_regi_yn;        //사고등록여부
    private String nana_nati_dcd;       //국적국가구분코드
    private String resd_nati_dcd;       //거주국가구분코드
    private String prio_tel_ctdt_dcd;   //우선전화연락처구분코드
    private String prio_post_ctdt_dcd;  //우선우편연락처구분코드
    private String sms_dnwa_dcd;        //SMS불원구분코드
    private String emal_dnwa_dcd;       //이메일불원구분코드
    private String job_dcd;             //직업구분코드

}
