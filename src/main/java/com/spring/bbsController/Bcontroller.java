/**
 * 
 */
package com.spring.bbsController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.bbsCommand.Bcommand;
import com.spring.bbsCommand.ListCommand;

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
		cmd = new ListCommand();
		cmd.service(model);	//model 은 view에 데이터를 전달
		return "list";
	}
	
}
