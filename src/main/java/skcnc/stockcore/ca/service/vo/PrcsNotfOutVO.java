package skcnc.stockcore.ca.service.vo;

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
public class PrcsNotfOutVO {
	//통보연락처구분코드
	private String notf_ctdt_dcd; 
	private String norl_prcs_yn;
}
