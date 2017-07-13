package com.berlizz.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_question_writer"))
	@JsonProperty
	private User writer;
	
	@JsonProperty
	private String title;
	
	@OneToMany(mappedBy="question")
	@OrderBy("id ASC")
	private List<Answer> answers;  // 답변의 목록을 question 이 직접 가지고 있을 수 있음
	
	@Lob  // 255자 제한을 없애버림
	@JsonProperty
	private String contents;
	
	public Question() {
		// jpa에서 mapping 할 때 인자를 받는 생성자가 있고 이것처럼 default 생성자를 적어줘야함
	}
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	public int getCountedAnswers() {
		return answers.size();
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {	
		return this.writer.equals(loginUser);
	}

	@Override
	public String toString() {
		return "Question [" + super.toString() + ", writer=" + writer + ", title=" + title + ", answers=" + answers + ", contents=" + contents
				+ "]";
	}

}
