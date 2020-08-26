import java.util.Date;

public class TableEntry {

	private int id;
	private int round;

	private String action;
	private String orgasm;

	private Date date;
	private Date total;
	private long jerked;
	
	private String orgasmType = "";

	private TableEntry() {
	}

	public TableEntry(int id, Date date, int round, String action, Date total, long jerked, String orgasm) {
		this.id = id;
		this.round = round;
		this.action = action;
		this.orgasm = orgasm;
		this.date = date;
		this.total = total;
		this.jerked = jerked;
	}
	
	public int getId() {
		return id;
	}

	public int getRound() {
		return round;
	}

	public String getAction() {
		return action;
	}

	public String getOrgasm() {
		return orgasm;
	}

	public Date getDate() {
		return date;
	}

	public Date getTotal() {
		return total;
	}

	public long getJerked() {
		return jerked;
	}
	
	public String getOrgasmType() {
		return orgasmType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setOrgasm(String orgasm) {
		this.orgasm = orgasm;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTotal(Date total) {
		this.total = total;
	}

	public void setJerked(Long jerked) {
		this.jerked = jerked;
	}

	public void setOrgasmType(String orgasmType) {
		this.orgasmType = orgasmType;
	}

}
