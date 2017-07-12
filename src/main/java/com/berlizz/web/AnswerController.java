package com.berlizz.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.berlizz.domain.Answer;
import com.berlizz.domain.AnswerRepository;
import com.berlizz.domain.Question;
import com.berlizz.domain.QuestionRepository;
import com.berlizz.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers") // answer는 question에 항상 종속적
                                                   // 종속적인 관계일 시 이런식의 설계가 좋음
public class AnswerController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		
		return String.format("redirect:/questions/%d", questionId);
	}
}
