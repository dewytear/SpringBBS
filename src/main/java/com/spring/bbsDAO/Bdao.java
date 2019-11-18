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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.soring.bbsVO.Bvo;
import com.spring.template.StaticTemplate;

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

	JdbcTemplate template;
	DataSource dataSource;
	/** JBBC를 이용한 간소화
	 * - 아래의 공통부분을 JBBC를 이용하여 간소하게 처리 할 수 있다.
	 * 1. 드라이버로딩
	 * 2. Conneection
	 * 3. Statement
	 * 4. 자원해제
	 * 
	 * JDBC외에도 하이버네이트, MuBatis를 이용해서 간소화 할 수 있다.
	 **/
	public Bdao() {
		//[region] Connection 을 구하기 위한 부분
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		//[end]
		
		this.template = StaticTemplate.template;
	}
	
	public ArrayList<Bvo> list() {
		// DB에 접속하여 글목록 가져오기(Command 에서 호출)
		ArrayList<Bvo> bVOs = null;
		String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
						+ "from MVC_BBS "
						+ "order by bGROUP desc, bSTEP asc";

		/** JdbcTemplate에서 사용할 수 있는 메소드
		 *	- query() : select 쿼리를 실핼 할 때 사용
		 *
		 *		. BeanPropertyRowMapper<T> 클래스는 RowMapper<T> 인터페이스를 구현
		 *		. RowMapper<T> 인터페이스에서 정의하고 있는 메소드는
		 *		  : mapRow() 메소드 - ResultSet에서 읽어온 값을 이용해서 원하는 타입의 객체를 생성해서 리턴
		 *			T mapRow(ResultSet rs, int rowNum) throws SQLException;	//rowNum 행번호(0부터 시작)
		 *
		 *	- queryForObject()
		 *	- update()
		 *	- execute()
		 **/
		//SQL쿼리문을 이용해서 ResultSet으로 읽어와 Vbo 타입의 객체로 리턴하여 준다.
		RowMapper<Bvo> rm = new BeanPropertyRowMapper<Bvo>(Bvo.class);
		bVOs = (ArrayList<Bvo>)template.query(strQuery, rm);
		
//[region] 기존 소스
//		ArrayList<Bvo> bVOs = new ArrayList<Bvo>();
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ list connection 확보 ------------");
//			
//			// Group은 원글의 번호이며 큰숫자가 최근글이 된다.
//			// Step으로 정렬을 하는 이유는 댓글에 대한 최신순으로 정렬하기위한 순차적 번호이다.(작은수가 최근 댓글)
//			String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
//							+ "from MVC_BBS "
//							+ "order by bGROUP desc, bSTEP asc";
//			preparedStatement = connection.prepareStatement(strQuery);
//			resultSet = preparedStatement.executeQuery();
//			
//			while(resultSet.next()) {
//				int bNO_BBS = resultSet.getInt("bNO_BBS");
//				String bNM_BBS = resultSet.getString("bNM_BBS");
//				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
//				String bSUBJECT = resultSet.getString("bSUBJECT");
//				String bCONTENT = resultSet.getString("bCONTENT");
//				int bHIT = resultSet.getInt("bHIT");
//				int bGROUP = resultSet.getInt("bGROUP");
//				int bSTEP = resultSet.getInt("bSTEP");
//				int bINDENT = resultSet.getInt("bINDENT");
//				
//				Bvo bVO = new Bvo(bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
//				
//				bVOs.add(bVO);
//			}//while()
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch(Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//[end]
		
		return bVOs;
	}//list()
	
	public void write(final String bNM_BBS, final String bSUBJECT, final String bCONTENT) {
		
			this.template.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
	
					String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
					 + "values(seq_bbs.nextval, ?, ?, ?, 0, seq_bbs.currval, 0, 0)";
					
					PreparedStatement preparedStatement = connection.prepareStatement(strQuery);
					preparedStatement.setString(1, bNM_BBS);
					preparedStatement.setString(2, bSUBJECT);
					preparedStatement.setString(3, bCONTENT);
					return preparedStatement;
				}
			}
		);
		
//[region] 기존 소스
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ write connection 확보 ------------");
//			
//			String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
//					 + "values(seq_bbs.nextval, ?, ?, ?, 0, seq_bbs.currval, 0, 0)";
//			preparedStatement = connection.prepareStatement(strQuery);
//			
//			preparedStatement.setString(1, bNM_BBS);
//			preparedStatement.setString(2, bSUBJECT);
//			preparedStatement.setString(3, bCONTENT);
//			
//			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
//			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
//			
//		} catch (SQLException e){
//			e.printStackTrace();
//		}finally {
//			try {
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close();
//			}catch(Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//[end]
		
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
	}//replySet()
}
