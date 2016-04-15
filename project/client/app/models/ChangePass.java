package models;

public class ChangePass {
	private int id;		// id of customer.
	private String passOld;
	private String passNew;
	public ChangePass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChangePass(int id, String passOld, String passNew) {
		super();
		this.id = id;
		this.passOld = passOld;
		this.passNew = passNew;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassOld() {
		return passOld;
	}
	public void setPassOld(String passOld) {
		this.passOld = passOld;
	}
	public String getPassNew() {
		return passNew;
	}
	public void setPassNew(String passNew) {
		this.passNew = passNew;
	}
}
