package entities;

public class AdminRoleEntity {
	private int id;
	private int groud;
	private int rule;
	public AdminRoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminRoleEntity(int id, int groud, int rule) {
		super();
		this.id = id;
		this.groud = groud;
		this.rule = rule;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroud() {
		return groud;
	}
	public void setGroud(int groud) {
		this.groud = groud;
	}
	public int getRule() {
		return rule;
	}
	public void setRule(int rule) {
		this.rule = rule;
	}
}
