package PakageResult;

public class User {
	private Integer id;		// id customer
	private String firstName;	
	private String email;
	private String token;
	public User() {
		super();
		id=0;
		// TODO Auto-generated constructor stub
	}
	public User(int id, String firstName, String email, String token) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.token = token;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
