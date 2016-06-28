package PakageResult;

public class ActiveAccountPakage extends AbstractPakage{
	private User user;

	public ActiveAccountPakage(User user) {
		super();
		this.user = user;
	}

	public ActiveAccountPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActiveAccountPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
