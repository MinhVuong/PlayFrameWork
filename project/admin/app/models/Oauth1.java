package models;

public class Oauth1 {
	private String token;
	private String secret;
	public Oauth1(String token, String secret) {
		super();
		this.token = token;
		this.secret = secret;
	}
	public Oauth1() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}
