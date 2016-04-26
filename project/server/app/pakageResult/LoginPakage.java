package pakageResult;

public class LoginPakage extends AbstractPakage{
	private User user;
	private IndexFullPakage indexFull;
	public LoginPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	

	public LoginPakage(User user, IndexFullPakage indexFull) {
		super();
		this.user = user;
		this.indexFull = indexFull;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IndexFullPakage getIndexFull() {
		return indexFull;
	}

	public void setIndexFull(IndexFullPakage indexFull) {
		this.indexFull = indexFull;
	}
	
	
}
