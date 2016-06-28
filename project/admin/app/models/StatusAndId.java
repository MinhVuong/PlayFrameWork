package models;

public class StatusAndId {
	private int key;
	private String value;
	public StatusAndId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StatusAndId(int key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
