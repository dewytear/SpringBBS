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
			System.out.println("log: ------------ list connection 확보 ------------");
			
			// Group은 원글의 번호이며 큰숫자가 최근글이 된다.
			// Step으로 정렬을 하는 이유는 댓글에 대한 최신순으로 정렬하기위한 순차적 번호이다.(작은수가 최근 댓글)
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
	}//list()
	
	public void write(String bNM_BBS, String bSUBJECT, String bCONTENT) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ write connection 확보 ------------");
			
			String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
					 + "values(seq_bbs.nextval, ?, ?, ?, 0, seq_bbs.currval, 0, 0)";
			preparedStatement = connection.prepareStatement(strQuery);
			
			preparedStatement.setString(1, bNM_BBS);
			preparedStatement.setString(2, bSUBJECT);
			preparedStatement.setString(3, bCONTENT);
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}//write()

	public void modify(String bNO_BBS, String bNM_BBS, String bSUBJECT, String bCONTENT) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ modify connection 확보 ------------");

			String strQuery = "update MVC_BBS set bNM_BBS = ?, bSUBJECT = ?, bCONTENT = ? where bNO_BBS = ?";
			preparedStatement = connection.prepareStatement(strQuery);
			
			preparedStatement.setString(1, bNM_BBS);
			preparedStatement.setString(2, bSUBJECT);
			preparedStatement.setString(3, bCONTENT);
			preparedStatement.setInt(4, Integer.parseInt(bNO_BBS));	//Key value
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}//modify()

	public void delete(String bNO_BBS) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ delete connection 확보 ------------");

			String strQuery = "delete from MVC_BBS where bNO_BBS = ?";
			preparedStatement = connection.prepareStatement(strQuery);
			
			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}//delete()
	
	public Bvo contentView(String bNO_BBS) {
		
		addHit(bNO_BBS);
		
		Bvo bVO = null;
		Connection connection = null;					//Connection 객체 생성
		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
		ResultSet resultSet = null;						//ResultSet 객체 생성
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ contentView connection 확보 ------------");

			String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
							+ "from MVC_BBS "
							+ "where bNO_BBS = ? ";
			preparedStatement = connection.prepareStatement(strQuery);
			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));		//Key value
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int iNO_BBS = resultSet.getInt("bNO_BBS");
				String bNM_BBS = resultSet.getString("bNM_BBS");
				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
				String bSUBJECT = resultSet.getString("bSUBJECT");
				String bCONTENT = resultSet.getString("bCONTENT");
				int bHIT = resultSet.getInt("bHIT");
				int bGROUP = resultSet.getInt("bGROUP");
				int bSTEP = resultSet.getInt("bSTEP");
				int bINDENT = resultSet.getInt("bINDENT");
				
				bVO = new Bvo(iNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
			}
			
		} catch(SQLException e) {
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
		
		return bVO;
		
	}//contentView()
	
	private void addHit(String bNO_BBS) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ addHit connection 확보 ------------");
			
			String strQuery = "update MVC_BBS set bHIT = bHIT + 1 where bNO_BBS = ?";
			preparedStatement = connection.prepareStatement(strQuery);

			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}//addHit()

	public Bvo replyForm(String bNO_BBS) {

		Bvo bVO = null;
		Connection connection = null;					//Connection 객체 생성
		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
		ResultSet resultSet = null;						//ResultSet 객체 생성
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ replyForm connection 확보 ------------");

			//String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
			//				+ "from MVC_BBS "
			//				+ "where bNO_BBS = ? ";
			String strQuery = "select * from MVC_BBS where bNO_BBS = ? ";
			preparedStatement = connection.prepareStatement(strQuery);
			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));		//Key value
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int iNO_BBS = resultSet.getInt("bNO_BBS");
				String bNM_BBS = resultSet.getString("bNM_BBS");
				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
				String bSUBJECT = resultSet.getString("bSUBJECT");
				String bCONTENT = resultSet.getString("bCONTENT");
				int bHIT = resultSet.getInt("bHIT");
				int bGROUP = resultSet.getInt("bGROUP");
				int bSTEP = resultSet.getInt("bSTEP");
				int bINDENT = resultSet.getInt("bINDENT");
				
				bVO = new Bvo(iNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
			}//if()
			
		} catch(SQLException e) {
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
		
		return bVO;
		
	}//replyForm()
	

	public void replyOk(String bNO_BBS, String bNM_BBS, String bSUBJECT, String bCONTENT, String bGROUP, String bSTEP, String bINDENT) {

		replySet(bGROUP, bSTEP);
		
		Connection connection = null;					//Connection 객체 생성
		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
		
		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ replyOk connection 확보 ------------");

			String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
					 + "values(seq_bbs.nextval, ?, ?, ?, 0, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(strQuery);
			
			preparedStatement.setString(1, bNM_BBS);
			preparedStatement.setString(2, bSUBJECT);
			preparedStatement.setString(3, bCONTENT);
			
			preparedStatement.setInt(4, Integer.parseInt(bGROUP));
			preparedStatement.setInt(5, Integer.parseInt(bSTEP)+1);
			preparedStatement.setInt(6, Integer.parseInt(bINDENT)+1);
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}//replyOk()
	
	public void replySet(String group, String step) {
		
		Connection connection = null;					//Connection 객체 생성
		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성

		try {
			connection = dataSource.getConnection();
			System.out.println("log: ------------ replySet connection 확보 ------------");

			String strQuery = "update MVC_BBS set bSTEP = bSTEP + 1 where bGROUP = ? and bSTEP > ?";
			
			preparedStatement = connection.prepareStatement(strQuery);
			
			preparedStatement.setInt(1, Integer.parseInt(group));
			preparedStatement.setInt(2, Integer.parseInt(step));
			
			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
