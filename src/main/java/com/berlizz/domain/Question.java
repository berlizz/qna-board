package com.berlizz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

	@Id  // primary key 지정
	@GeneratedValue  // 자동으로 1씩 증가(auto_increment)
	private Long id;
	
	private String writer;
	private String title;
	private String contents;
	
	public Question() {
		// jpa에서 mapping 할 때 인자를 받는 생성자가 있고 이것처럼 default 생성자를 적어줘야함
	}
	
	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
