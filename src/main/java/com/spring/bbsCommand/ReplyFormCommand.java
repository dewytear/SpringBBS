/**
 * 
 */
package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.soring.bbsVO.Bvo;
import com.spring.bbsDAO.Bdao;

/**
 * @CLASS Name
 *  ReplyFormCommand
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-11-01
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsCommand
 * @Description: 글에 대한 내용을 확인하고 
 * 				답글을 달기위해 원문의 내용을 가져온다.
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class ReplyFormCommand implements Bcommand {

	@Override
	public void service(Model model) {
		Map<String, Object> map = model.asMap();	// model 형태를 Map형태로 바꾸어 줌

		//Controller 에서 추가한 request를 받아와서 HttpServletRequest으로 바꿔어줌
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bNO_BBS = request.getParameter("bNO_BBS");	//Key value
		
		Bdao dao = new Bdao();
		Bvo bVO = dao.replyForm(bNO_BBS);
		
		model.addAttribute("replyForm", bVO);
	}

}
