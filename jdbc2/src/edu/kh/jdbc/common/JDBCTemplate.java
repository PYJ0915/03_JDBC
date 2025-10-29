package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/* JDBCTemplate
 * : JDBC 관련 작업을 위한 코드를 미리 작성해서 제공하는 클래스
 * 
 * - Connection 생성
 * - AutoCommit false
 * - Commit / Rollback
 * - 각종 자원 반환 close()
 * 
 * ***** 중요 *****
 * 어디서든지 JDBCTemplate 클래스를 객체로 만들지 않고도
 * 메서드를 사용할 수 있도록 하기 위해 모든 메서드를 public static 으로 선언
 * 
 * */

public class JDBCTemplate {

	// 필드
	private static Connection conn = null;
	
	// 메서드
	/** 호출 시 Connection 객체를 생성하여 호출한 곳으로 반환하는 메서드 + AutoCommit 해제
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			// 1. 이전에 Connection 객체가 만들어져있고 close() 되지않았다면 새로 만들지 않고 기존 커넥션 반환!
			if(conn != null && !conn.isClosed()) return conn;
				
			// 2. Properties 객체 생성
			Properties prop = new Properties();
			
			// 3. Properties가 제공하는 메서드를 이용해서 driver.xml 읽어오기
			prop.loadFromXML(new FileInputStream("driver.xml"));
			
			// 4. prop에 저장된 값을 이용해서 Connection 객체 생성
			Class.forName(prop.getProperty("driver"));
			
		    conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("userName"), prop.getProperty("password"));
			
		    // 5. 만들어진 Connection 객체에서 AutoCommit 해제
		    conn.setAutoCommit(false);
		    
		} catch (Exception e) {
			System.out.println("커넥션 생성 중 예외 발생해따! (JDBCTemplate의 getConnection() 참조)");
			e.printStackTrace();
		}
		
		// 6. 만들어진 Connection 객체 반환
	    return conn;
		
	}
	
	/** 전달받은 커넥션에서 수행한 SQL을 commit 하는 메서드
	 * 
	 */
	public static void commit(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.commit();
			
		} catch (Exception e) {
			
			System.out.println("커밋 중 예외 발생해따 (JDBCTemplate의 commit() 참조)");
			e.printStackTrace();
			
		}
		
		
		
	}
	
	public static void rollback(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.rollback();
			
		} catch (Exception e) {
			System.out.println("롤백 중 예외 발생해따 (JDBCTemplate의 rollback() 참조)");
			e.printStackTrace();
		}
		
	}
	
	// ---------------------------------------------------------
	
	// Connection, Statement(PreparedStatement), ResultSet
	
	/** 전달받은 컬렉션을 close() 하는 메서드
	 * 
	 */
	public static void close(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch (Exception e) {
			System.out.println("Connection 닫는 중에 예외 발생해따 (JDBCTemplate의 close() 참조)");
			e.printStackTrace();
		}
		
	}
	
	/** 전달받은 Statement or PreparedStatement 둘 다 close() 할 수 있는 메서드
	 * + 다형성의 업캐스팅 적용 (PreparedStatement는 Statement의 자식)
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		} catch (Exception e) {
			System.out.println("Statement 닫는 중에 예외 발생해따 (JDBCTemplate의 close() 참조)");
			e.printStackTrace();
		}
		
	}
	
	/** 전달 받은 ResultSet을 close() 하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
		try {
			
			if(rs != null && !rs.isClosed()) rs.close();
			
		} catch (Exception e) {
			System.out.println("ResultSet 닫는 중에 예외 발생해따 (JDBCTemplate의 close() 참조)");
			e.printStackTrace();
		}
		
	}
}
