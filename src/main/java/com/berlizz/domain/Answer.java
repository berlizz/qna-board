package com.berlizz.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_answer_writer"))
	@JsonProperty
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_answer_to_question"))
	@JsonProperty
	private Question question;
	
	@Lob
	@JsonProperty
	private String contents;
	
	public Answer() {
		// jpa에서 mapping 할 때 인자를 받는 생성자가 있고 이것처럼 default 생성자를 적어줘야함
	}
	
	public Answer(User writer, Question question, String contents) {
		super();
		this.writer = writer;
		this.question = question;
		this.contents = contents;
	}
	
	public boolean isSameWriter(User loginUser) {
		return writer.equals(loginUser);
	}

	@Override
	public String toString() {
		return "Answer [" + super.toString() + "writer=" + writer + ", question=" + question + ", contents=" + contents + "]";
	}

}
