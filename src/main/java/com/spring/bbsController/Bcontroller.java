/**
 * 
 */
package com.spring.bbsController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soring.bbsVO.Bvo;
import com.spring.bbsCommand.Bcommand;
import com.spring.bbsCommand.ContentCommand;
import com.spring.bbsCommand.ListCommand;
import com.spring.bbsCommand.ModifyCommand;
import com.spring.bbsCommand.writeCommand;

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
		
		return "redirect:list";	//수정 뒤에 목록보기로 돌아오도록 설정
	}
	
	@ModelAttribute("Bvo")
	public Bvo fromBacking() {
		return new Bvo();
	}
}
