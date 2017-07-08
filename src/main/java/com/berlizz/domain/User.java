package com.berlizz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id  // primary key 지정
	@GeneratedValue  // 자동으로 1씩 증가
	private Long id;
	
	@Column(nullable=false, length=20)
	private String userId;
	private String password;
	private String name;
	private String email;

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void update(User updateUser) {
		this.userId = updateUser.getUserId();
		this.password = updateUser.getPassword();
		this.name = updateUser.getName();
		this.email = updateUser.getEmail();
	}

}
