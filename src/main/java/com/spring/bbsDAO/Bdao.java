/**
 * 
 */
package com.spring.bbsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.soring.bbsVO.Bvo;

/**
 * @CLASS Name
 *  Bdao
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-10-28
 * @PROJECT    : SpringBBS
 * @PACKAGE    : com.spring.bbsDAO
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class Bdao {

	DataSource dataSource;
	
	public Bdao() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Bvo> list() {
		// TODO : DB에 접속하여 글목록 가져오기(Command 에서 호출)
		ArrayList<Bvo> bVOs = new ArrayList<Bvo>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
							+ "from MVC_BBS "
							+ "order by bGROUP desc, bSTEP asc";
			preparedStatement = connection.prepareStatement(strQuery);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bNO_BBS = resultSet.getInt("bNO_BBS");
				String bNM_BBS = resultSet.getString("bNM_BBS");
				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
				String bSUBJECT = resultSet.getString("bSUBJECT");
				String bCONTENT = resultSet.getString("bCONTENT");
				int bHIT = resultSet.getInt("bHIT");
				int bGROUP = resultSet.getInt("bGROUP");
				int bSTEP = resultSet.getInt("bSTEP");
				int bINDENT = resultSet.getInt("bINDENT");
				
				Bvo bVO = new Bvo(bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
				
				bVOs.add(bVO);
			}//while()
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return bVOs;
	}
}
