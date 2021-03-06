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
 *  ContentCommand
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-31
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsCommand
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class ContentCommand implements Bcommand {

	@Override
	public void service(Model model) {
		
		Map<String, Object> map = model.asMap();	// model 형태를 Map형태로 바꾸어 줌
		
		//Controller 에서 추가한 request를 받아와서 HttpServletRequest으로 바꿔어줌
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bNO_BBS = request.getParameter("bNO_BBS");
		
		Bdao dao = new Bdao();
		Bvo bVO = dao.contentView(bNO_BBS);	//Key value
		
		model.addAttribute("contentView", bVO);
	}
	
}
