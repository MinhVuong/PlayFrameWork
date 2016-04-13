package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {
	@Id
	@Column(name = "id_customer")
	private int id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "gender")
	private int gender;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "group_id")
	private int groupId;
	@Column(name = "score")
	private int score;
	@Column(name = "is_active")
	private int isActive;
	@Column(name = "create_at")
	private String createAt;
	@Column(name = "update_at")
	private String updateAt;
	@Column(name = "date_log")
	private String dateLog;
	@Column(name = "number_log")
	private int numberLog;
	
	public int getId() {
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	public String getDateLog() {
		return dateLog;
	}
	public void setDateLog(String dateLog) {
		this.dateLog = dateLog;
	}
	public int getNumberLog() {
		return numberLog;
	}
	public void setNumberLog(int numberLog) {
		this.numberLog = numberLog;
	}
	
	

}
