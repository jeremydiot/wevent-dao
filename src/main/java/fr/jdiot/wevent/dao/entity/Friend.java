package fr.jdiot.wevent.dao.entity;

import java.sql.Timestamp;

public class Friend {
	
	private User user;
	private User friend;
	private Timestamp createdAt;
	
	public Friend() {}
	
	public Friend(User user, User friend, Timestamp createdAt) {
		this.user = user;
		this.friend = friend;
		this.createdAt = createdAt;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getFriend() {
		return friend;
	}
	public void setFriend(User friend) {
		this.friend = friend;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
