package adminEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_roles")
public class AdminRoleEntity {
	@Id
	@Column(name = "id_admin_role")
	private int id;
	@Column(name = "group_id")
	private int groud;
	@Column(name = "rule_id")
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
