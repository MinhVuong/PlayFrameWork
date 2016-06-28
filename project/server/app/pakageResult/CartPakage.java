package pakageResult;

public class CartPakage extends AbstractPakage{
	private int idC;		//id luu danh sach san pham trong database cart
	private String message;

	public CartPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	
	public CartPakage(int idC, String message) {
		super();
		this.idC = idC;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}
	
	
}
