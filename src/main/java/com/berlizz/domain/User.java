package com.berlizz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {

	@Column(nullable=false, length=20, unique=true) // unique=true는 중복 불가 선언
	@JsonProperty
	private String userId;
	
	@JsonIgnore    // json으로 변환 시 제외시킴
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;

	@Override
	public String toString() {
		return "Users [" + super.toString() + ", password=" + password + ", name=" + name + ", email=" + email + "]";
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
	
	public boolean matchPassword(String newPassword) {
		if(newPassword == null) {
			return false;
		}
		
		return newPassword.equals(password);
	}
	
	public boolean matchId(Long newId) {
		if(newId == null) {
			return false;
		}
		
		return newId.equals(getId());
	}

}
