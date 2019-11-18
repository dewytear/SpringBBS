/**
 * 
 */
package com.spring.bbsController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soring.bbsVO.Bvo;
import com.spring.bbsCommand.*;
import com.spring.template.StaticTemplate;

/**
 * @CLASS Name
 *  Bcontroller
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-28
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsController
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
@Controller
public class Bcontroller {

	//Dispatcher Servlet을 통해 요청을 받아온다.
	Bcommand cmd = null;
	
	private JdbcTemplate template;
	
	@Autowired
	// Servlet-context.xml 에 생성한 Jdbc bean에 연결 후 공유하기 위해 Static으로 메모리에 공유
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		StaticTemplate.template = this.template;
	}
	
	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("log: ------------ list() 호출 ------------");
		
		cmd = new ListCommand();
		cmd.service(model);	//model 은 view에 데이터를 전달
		
		return "list";
	}
	
	@RequestMapping("/writeForm")
	public String writeFrom(Model model) {
		System.out.println("log: ------------ writeFrom() 호출 ------------");
		
		return "writeForm";
	}

	@RequestMapping("/writeOk")
	public String writeOk(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ writeOk() 호출 ------------");
		
		model.addAttribute("request", request);
		cmd = new writeCommand();
		cmd.service(model);
		
		return "writeForm";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ contentView() 호출 ------------");
		
		model.addAttribute("request", request);
		cmd = new ContentCommand();
		cmd.service(model);
		
		return "contentView";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ modify() 호출 ------------");
		
		model.addAttribute("request", request);
		cmd = new ModifyCommand();
		cmd.service(model);
		
		return "redirect:list";	//수정 후 목록보기로 돌아오도록 설정
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ delete() 호출 ------------");
		
		model.addAttribute("request", request);
		cmd = new DeleteCommand();
		cmd.service(model);
		
		return "redirect:list";	//삭제 후 목록보기로 돌아오도록 설정
	}
	
	@RequestMapping("/replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ replyForm() 호출 ------------");

		model.addAttribute("request", request);
		cmd = new ReplyFormCommand();
		cmd.service(model);
		
		return "replyForm";
	}
	
	@RequestMapping("/replyOk")
	public String replayOk(HttpServletRequest request, Model model) {
		System.out.println("log: ------------ replayOk() 호출 ------------");
		
		model.addAttribute("request", request);
		cmd = new ReplyCommand();
		cmd.service(model);
		
		return "redirect:list";	//댓글 후 목록보기로 돌아오도록 설정
	}
	
	@ModelAttribute("Bvo")
	public Bvo fromBacking() {
		return new Bvo();
	}
}
