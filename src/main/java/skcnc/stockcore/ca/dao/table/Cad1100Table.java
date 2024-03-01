package skcnc.stockcore.ca.dao.table;

import java.util.Date;

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
public class Cad1100Table {
	private String wrk_dt_dcd;
	private String bef_4_dt;
	private String bef_3_dt;
	private String bef_2_dt;
	private String bef_dt;
	private String prs_dt;
	private String aft_dt;
	private String aft_2_dt;
	private String aft_3_dt;
	private String aft_4_dt;

	private Date opr_dttm;
	private String opr_id;
	private String opr_trd_id;
	private String opr_tmn_id;
}
