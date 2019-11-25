/**
 * 
 */
package com.spring.bbsDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
//	DataSource dataSource;
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
		/*
		//[region] Connection 을 구하기 위한 부분
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		//[end]
		 */
		
		this.template = StaticTemplate.template;
	}
	
	public ArrayList<Bvo> list() {
		// DB에 접속하여 글목록 가져오기(Command 에서 호출)
		ArrayList<Bvo> bVOs = null;
		String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
						+ "from MVC_BBS "
						+ "order by bGROUP desc, bSTEP asc";

		/** JdbcTemplate에서 사용할 수 있는 메소드
		 *	- query() : select 쿼리를 실핼 할 때 사용하는 메소드
		 *		: List query(String sql, Object[] args, RowMapper rowMapper)
		 *		: List query(String sql, RowMapper rowMapper)
		 *
		 *		. BeanPropertyRowMapper<T> 클래스는 RowMapper<T> 인터페이스를 구현
		 *		. RowMapper<T> 인터페이스에서 정의하고 있는 메소드는
		 *		  : mapRow() 메소드 - ResultSet에서 읽어온 값을 이용해서 원하는 타입의 객체를 생성해서 리턴
		 *			T mapRow(ResultSet rs, int rowNum) throws SQLException;	//rowNum 행번호(0부터 시작)
		 *
		 *	- queryForObject() : 쿼리 실행 결과가 하나의 레코드(Row)를 가져오는 경우에 사용
		 *						전달되는 각 파라미터가 query() 메소드와 동일하다.
		 *						List를 반환하는 대신에 한 개의 객체를 리턴
		 *						리턴되는 행의 개수가 한개가 아닌 경우에는 IncorrectResultSizeDataAccessException 예외 발생
		 *
		 *		: public Object queryForObject(String sql, RowMapper rowMapper)
		 *		: public Object queryForObject(String sql, Object[] args, RowMapper rowMapper)
		 *
		 *		리턴 타입이 Object가 아닌 경우에는 Int, Long 타입의 결과를 구할 때 사용하는
		 *		queryForInt(), queryForLong()
		 *
		 *	- update() : insert, update, delete 쿼리를 실행할 때 사용하는 메소드
		 *				쿼리 실행결과 변경된 행의 개수를 리턴
		 *
		 *		: update(String sql)
		 *		: update(String sql, Object[] args)
		 *
		 *	- execute() : Connection을 직접 사용해야 하는 경우에 사용하는 메소드
		 *		. Connection의 생성과 종료는 JdbcTemplate에서 처리하기 때문에 Connection을 종료할 필요가 없다.
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
	
	//local에서 수정되지 않도록 하기 위해 parameter에 final을 붙여준다.
	public void write(final String bNM_BBS, final String bSUBJECT, final String bCONTENT) {
		
		/*
		//[region] 방법1: Connection 을 이용하는 방법
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
		);//[end]
		*/

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
		 + "values(seq_bbs.nextval, ?, ?, ?, 0, seq_bbs.currval, 0, 0)";

		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException{
				preparedStatement.setString(1, bNM_BBS);
				preparedStatement.setString(2, bSUBJECT);
				preparedStatement.setString(3, bCONTENT);
			}

		});//[end]
		
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

	//local에서 수정되지 않도록 하기 위해 parameter에 final을 붙여준다.
	public void modify(final String bNO_BBS, final String bNM_BBS, final String bSUBJECT, final String bCONTENT) {

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "update MVC_BBS set bNM_BBS = ?, bSUBJECT = ?, bCONTENT = ? where bNO_BBS = ?";
		
		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException{
				ps.setString(1, bNM_BBS);
				ps.setString(2, bSUBJECT);
				ps.setString(3, bCONTENT);
				ps.setInt(4, Integer.parseInt(bNO_BBS));	//Key value
			}

		});//[end]
		
//[region] 기존 소스
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ modify connection 확보 ------------");
//
//			String strQuery = "update MVC_BBS set bNM_BBS = ?, bSUBJECT = ?, bCONTENT = ? where bNO_BBS = ?";
//			preparedStatement = connection.prepareStatement(strQuery);
//			
//			preparedStatement.setString(1, bNM_BBS);
//			preparedStatement.setString(2, bSUBJECT);
//			preparedStatement.setString(3, bCONTENT);
//			preparedStatement.setInt(4, Integer.parseInt(bNO_BBS));	//Key value
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
		
	}//modify()

	public void delete(final String bNO_BBS) {

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "delete from MVC_BBS where bNO_BBS = ?";

		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException{
				ps.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
			}

		});//[end]
		
//[region] 기존 소스
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ delete connection 확보 ------------");
//
//			String strQuery = "delete from MVC_BBS where bNO_BBS = ?";
//			preparedStatement = connection.prepareStatement(strQuery);
//			
//			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
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
		
	}//delete()
	
	public Bvo contentView(String bNO_BBS) {
		
		addHit(bNO_BBS);
		
		String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
				+ "from MVC_BBS "
				+ "where bNO_BBS = " + bNO_BBS;

		//SQL쿼리문을 이용해서 ResultSet으로 읽어와 Vbo 타입의 객체로 리턴하여 준다.
		RowMapper<Bvo> rm = new BeanPropertyRowMapper<Bvo>(Bvo.class);
		return this.template.queryForObject(strQuery, rm);
		
//[region] 기존 소스
//		Bvo bVO = null;
//		Connection connection = null;					//Connection 객체 생성
//		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
//		ResultSet resultSet = null;						//ResultSet 객체 생성
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ contentView connection 확보 ------------");
//
//			String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
//							+ "from MVC_BBS "
//							+ "where bNO_BBS = ? ";
//			preparedStatement = connection.prepareStatement(strQuery);
//			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));		//Key value
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				int iNO_BBS = resultSet.getInt("bNO_BBS");
//				String bNM_BBS = resultSet.getString("bNM_BBS");
//				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
//				String bSUBJECT = resultSet.getString("bSUBJECT");
//				String bCONTENT = resultSet.getString("bCONTENT");
//				int bHIT = resultSet.getInt("bHIT");
//				int bGROUP = resultSet.getInt("bGROUP");
//				int bSTEP = resultSet.getInt("bSTEP");
//				int bINDENT = resultSet.getInt("bINDENT");
//				
//				bVO = new Bvo(iNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
//			}
//			
//		} catch(SQLException e) {
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
//		
//		return bVO;
//[end]
		
	}//contentView()
	
	private void addHit(final String bNO_BBS) {

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "update MVC_BBS set bHIT = bHIT + 1 where bNO_BBS = ?";
		
		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException{
				ps.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
			}

		});//[end]
		
//[region] 기존 소스
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ addHit connection 확보 ------------");
//			
//			String strQuery = "update MVC_BBS set bHIT = bHIT + 1 where bNO_BBS = ?";
//			preparedStatement = connection.prepareStatement(strQuery);
//
//			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));	//Key value
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
		
	}//addHit()

	public Bvo replyForm(String bNO_BBS) {

		String strQuery = "select * from MVC_BBS where bNO_BBS = " + bNO_BBS;

		//SQL쿼리문을 이용해서 ResultSet으로 읽어와 Vbo 타입의 객체로 리턴하여 준다.
		//RowMapper<Bvo> rm = new BeanPropertyRowMapper<Bvo>(Bvo.class);
		return this.template.queryForObject(strQuery, new BeanPropertyRowMapper<Bvo>(Bvo.class));
		
//[region] 기존 소스
//		Bvo bVO = null;
//		Connection connection = null;					//Connection 객체 생성
//		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
//		ResultSet resultSet = null;						//ResultSet 객체 생성
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ replyForm connection 확보 ------------");
//
//			//String strQuery = "select bNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT "
//			//				+ "from MVC_BBS "
//			//				+ "where bNO_BBS = ? ";
//			String strQuery = "select * from MVC_BBS where bNO_BBS = ? ";
//			preparedStatement = connection.prepareStatement(strQuery);
//			preparedStatement.setInt(1, Integer.parseInt(bNO_BBS));		//Key value
//			resultSet = preparedStatement.executeQuery();
//			
//			if(resultSet.next()) {
//				int iNO_BBS = resultSet.getInt("bNO_BBS");
//				String bNM_BBS = resultSet.getString("bNM_BBS");
//				Timestamp bDT_BBS = resultSet.getTimestamp("bDT_BBS");
//				String bSUBJECT = resultSet.getString("bSUBJECT");
//				String bCONTENT = resultSet.getString("bCONTENT");
//				int bHIT = resultSet.getInt("bHIT");
//				int bGROUP = resultSet.getInt("bGROUP");
//				int bSTEP = resultSet.getInt("bSTEP");
//				int bINDENT = resultSet.getInt("bINDENT");
//				
//				bVO = new Bvo(iNO_BBS, bNM_BBS, bDT_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT);
//			}//if()
//			
//		} catch(SQLException e) {
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
//		
//		return bVO;
//[end]
		
	}//replyForm()
	
	//local에서 수정되지 않도록 하기 위해 parameter에 final을 붙여준다.
	public void replyOk(String bNO_BBS, final String bNM_BBS, final String bSUBJECT, final String bCONTENT, final String bGROUP, final String bSTEP, final String bINDENT) {

		replySet(bGROUP, bSTEP);

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
				 + "values(seq_bbs.nextval, ?, ?, ?, 0, ?, ?, ?)";

		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException{
				ps.setString(1, bNM_BBS);
				ps.setString(2, bSUBJECT);
				ps.setString(3, bCONTENT);
				ps.setInt(4, Integer.parseInt(bGROUP));
				ps.setInt(5, Integer.parseInt(bSTEP) + 1);
				ps.setInt(6, Integer.parseInt(bINDENT) + 1);
			}

		});//[end]
		
//[region] 기존 소스
//		Connection connection = null;					//Connection 객체 생성
//		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
//		
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ replyOk connection 확보 ------------");
//
//			String strQuery = "insert into MVC_BBS(bNO_BBS, bNM_BBS, bSUBJECT, bCONTENT, bHIT, bGROUP, bSTEP, bINDENT)\r\n"
//					 + "values(seq_bbs.nextval, ?, ?, ?, 0, ?, ?, ?)";
//			
//			preparedStatement = connection.prepareStatement(strQuery);
//			
//			preparedStatement.setString(1, bNM_BBS);
//			preparedStatement.setString(2, bSUBJECT);
//			preparedStatement.setString(3, bCONTENT);
//			
//			preparedStatement.setInt(4, Integer.parseInt(bGROUP));
//			preparedStatement.setInt(5, Integer.parseInt(bSTEP)+1);
//			preparedStatement.setInt(6, Integer.parseInt(bINDENT)+1);
//			
//			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
//			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
//			
//		} catch(SQLException e) {
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
		
	}//replyOk()

	//local에서 수정되지 않도록 하기 위해 parameter에 final을 붙여준다.
	public void replySet(final String group, final String step) {

		//[region] 방법2: PreparedStatementSetter 를 이용하는 방법
		String strQuery = "update MVC_BBS set bSTEP = bSTEP + 1 where bGROUP = ? and bSTEP > ?";

		this.template.update(strQuery, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException{
				ps.setString(1, group);
				ps.setString(2, step);
			}
				
		});//[end]
		
//[region] 기존 소스
//		Connection connection = null;					//Connection 객체 생성
//		PreparedStatement preparedStatement = null;		//PreparedStatement 객체 생성
//
//		try {
//			connection = dataSource.getConnection();
//			System.out.println("log: ------------ replySet connection 확보 ------------");
//
//			String strQuery = "update MVC_BBS set bSTEP = bSTEP + 1 where bGROUP = ? and bSTEP > ?";
//			
//			preparedStatement = connection.prepareStatement(strQuery);
//			
//			preparedStatement.setInt(1, Integer.parseInt(group));
//			preparedStatement.setInt(2, Integer.parseInt(step));
//			
//			int n = preparedStatement.executeUpdate();	//실행 결과값이 integer로 나온다.
//			//성공, 실패에 따른 로직 없음(n값 사용하지않음)
//			
//		} catch(SQLException e) {
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
		
	}//replySet()
}
