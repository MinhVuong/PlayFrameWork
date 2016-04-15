package pakageResult;

public class LoginPakage extends AbstractPakage{
	private User user;

	public LoginPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public LoginPakage(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
