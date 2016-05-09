package pakageResult;

public class CartPakage extends AbstractPakage{
	private String message;

	public CartPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public CartPakage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
