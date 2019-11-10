/**
 * 
 */
package com.spring.bbsCommand;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.soring.bbsVO.Bvo;
import com.spring.bbsDAO.Bdao;

/**
 * @CLASS Name
 *  ListCommand
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-28
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsCommand
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class ListCommand implements Bcommand {

	@Override
	public void service(Model model) {
		// DB에 접속하여 리스트 가져오는 Command (Controller에서 호출)
		Bdao dao = new Bdao();
		ArrayList<Bvo> bVOs = dao.list();
		model.addAttribute("list", bVOs);	//bVOs를 모델에 전달
	}

}
