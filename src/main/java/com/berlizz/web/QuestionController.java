package com.berlizz.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.berlizz.domain.Question;
import com.berlizz.domain.QuestionRepository;
import com.berlizz.domain.Result;
import com.berlizz.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = new Question(sessionedUser, title, contents);
		
		questionRepository.save(question);
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String detailView(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findOne(id));
		
		return "/qna/show";
	}
	
	private Result valid(HttpSession session, Question question) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제 할 수 있습니다.");
		}
		
		return Result.ok();
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		
		if(!result.isValid()) {
			model.addAttribute("error", result.getErrorMessage());
			return "/user/login";
		}
	
		model.addAttribute("question", question);
		
		return "/qna/updateForm";		
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		
		if(!result.isValid()) {
			model.addAttribute("error", result.getErrorMessage());
			return "/user/login";
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);
		
		if(!result.isValid()) {
			model.addAttribute("error", result.getErrorMessage());
			return "/user/login";
		}
			
		questionRepository.delete(id);
		
		return "redirect:/";
	}
	
}
