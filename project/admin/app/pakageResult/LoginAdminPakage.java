package pakageResult;
import java.util.List;

import models.User;
import entities.AdminRoleEntity;

public class LoginAdminPakage extends AbstractPakage{
	List<AdminRoleEntity> rules;
	User user;
	
	public LoginAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LoginAdminPakage(List<AdminRoleEntity> rules, User user) {
		super();
		this.rules = rules;
		this.user = user;
	}

	public LoginAdminPakage(List<AdminRoleEntity> rules) {
		super();
		this.rules = rules;
	}

	public List<AdminRoleEntity> getRules() {
		return rules;
	}

	public void setRules(List<AdminRoleEntity> rules) {
		this.rules = rules;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
