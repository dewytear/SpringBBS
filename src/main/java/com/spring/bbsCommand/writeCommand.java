/**
 * 
 */
package com.spring.bbsCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.bbsDAO.Bdao;

/**
 * @CLASS Name
 *  writeCommand
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-29
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsCommand
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class writeCommand implements Bcommand {

	@Override
	public void service(Model model) {
		
		Map<String, Object> map = model.asMap();	// model 형태를 Map형태로 바꾸어 줌
		
		//Controller 에서 추가한 request를 받아와서 HttpServletRequest으로 바꿔어줌
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bNM_BBS = request.getParameter("bNM_BBS");
		String bSUBJECT = request.getParameter("bSUBJECT");
		String bCONTENT = request.getParameter("bCONTENT");
		
		Bdao dao = new Bdao();
		dao.write(bNM_BBS, bSUBJECT, bCONTENT);
		
	}

}
