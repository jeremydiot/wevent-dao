package fr.jdiot.wevent.dao.entity;

import java.sql.Timestamp;

public class User {

	private String id;
	private String username;
	private String password;
	private String email;
	private Timestamp connectedAt;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	public User() {}
	
	public User(String id, String username, String password, String email, Timestamp connectedAt, Timestamp createdAt, Timestamp modifiedAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.connectedAt = connectedAt;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String uuid) {
		this.id = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getConnectedAt() {
		return connectedAt;
	}
	public void setConnectedAt(Timestamp connectedAt) {
		this.connectedAt = connectedAt;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
}

