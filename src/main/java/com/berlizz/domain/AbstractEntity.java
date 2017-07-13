package com.berlizz.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // @CreatedDate @LastModifiedDate 같은 것들 리스너에서 인식해서 자동으로 입력
public class AbstractEntity {

	@Id  // primary key 지정
	@GeneratedValue  // 자동으로 1씩 증가(auto_increment)
	@JsonProperty
	private Long id;
	
	@CreatedDate    // 자동으로 시간 생성,  QnaBoardApplication 에서 @EnableJpaAuditing 필요
	private LocalDateTime createTime;
	
	@LastModifiedDate    // 가장 최근의 시간 자동으로 업데이트
	private LocalDateTime modifiedTime;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFormattedCreateTime() {
		return getFormattedTime(createTime, "yyyy.MM.dd HH:mm:ss");
	}
	
	public String getModifiedCreateTime() {
		return getFormattedTime(modifiedTime, "yyyy.MM.dd HH:mm:ss");
	}
	
	private String getFormattedTime(LocalDateTime dateTime , String format) {
		if(dateTime == null) {
			return null;
		}
		
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}
	
}
