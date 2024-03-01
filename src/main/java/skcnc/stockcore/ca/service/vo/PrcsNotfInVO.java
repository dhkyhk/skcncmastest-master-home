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
public class PrcsNotfInVO {
	private String cif;
	private String ac_no;
	private String notf_ctdt_dcd;
	private String notf_txt;
}
