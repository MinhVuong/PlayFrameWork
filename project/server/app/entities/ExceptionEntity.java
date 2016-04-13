package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exception")
public class ExceptionEntity {
	@Id
	@Column(name = "id_exception")
	private int id;
	@Column(name = "time")
	private String time;
	@Column(name = "method")
	private String method;
	@Column(name = "content")
	private String content;
	
	public ExceptionEntity() {
		super();
	}
	public ExceptionEntity(int id, String time, String method, String content) {
		super();
		this.id = id;
		this.time = time;
		this.method = method;
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
