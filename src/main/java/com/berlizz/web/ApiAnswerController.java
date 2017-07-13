package com.berlizz.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berlizz.domain.Answer;
import com.berlizz.domain.AnswerRepository;
import com.berlizz.domain.Question;
import com.berlizz.domain.QuestionRepository;
import com.berlizz.domain.Result;
import com.berlizz.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers") // answer는 question에 항상 종속적
                                                   // 종속적인 관계일 시 이런식의 설계가 좋음
public class ApiAnswerController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		
		Answer answer = new Answer(loginUser, question, contents);
		
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("자신이 작성한 글만 삭제할 수 있습니다.");
		}
		
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!answer.isSameWriter(loginUser)) {
			return Result.fail("자신이 작성한 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(id);
		
		return Result.ok();
	}
}
