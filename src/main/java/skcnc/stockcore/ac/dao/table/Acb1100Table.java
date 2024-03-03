package skcnc.stockcore.ac.dao.table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*계좌_통보처기본*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Acb1100Table {

	private String ac_no;
	private String notf_wrk_dcd;
	private String notf_ctdt_dcd;
	private String use_yn;
	
	private Date   opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
