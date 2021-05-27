package fr.jdiot.wevent.dao.entity;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User {

	private String id;
	private String username;
	private String password;
	private String email;
	private Timestamp connectedAt;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public User() {}
	
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
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

