package edu.kh.jdbc.homework.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	private static Connection conn = null;
	

	/** 호출 시 커넥션 객체 생성하여 호출한 곳으로 반환하는 메서드
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			
			if(conn != null && !conn.isClosed()) return conn;
			
			Properties prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("driver.xml"));
			
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("userName"), prop.getProperty("password"));
			
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			
			System.out.println("커넥션 생성 중 예외 발생! JDBCTemplate의 getConnection() 참조");
			e.printStackTrace();
			
		}
		
		return conn;
		
	}
	
	/** 전달받은 커넥션에서 수행한 SQL을 커밋하는 메서드
	 * 
	 */
	public static void commit(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.commit();
			
		} catch (Exception e) {
			
			System.out.println("커밋 중 예외 발생 JDBCTemplate의 commit() 참조");
			e.printStackTrace();
			
		}
		
	}
	
	
	/** 전달받은 커넥션에서 수행한 SQL을 롤백하는 메서드
	 * 
	 */
	public static void rollback(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.rollback();
			
		} catch (Exception e) {
			
			System.out.println("롤백 중 예외 발생 JDBCTemplate의 rollback() 참조");
			e.printStackTrace();
			
		}
		
		
	}
	
	/** 전달받은 커넥션을 close()하는 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch (Exception e) {
			
			System.out.println("Connection close() 중 예외 발생 JDBCTemplate의 close() 참조");
			e.printStackTrace();
			
		}
		
		
	}
	
	/** 전달받은 Statement를 close()하는 메서드
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		} catch (Exception e) {
			
			System.out.println("Statement close() 중 예외 발생 JDBCTemplate의 close() 참조");
			e.printStackTrace();
			
		}
		
		
	}
	
	/** 전달받은 ResultSet을 close()하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
		try {
			
			if(rs != null && !rs.isClosed()) rs.close();
			
		} catch (Exception e) {
			
			System.out.println("ResultSet close() 중 예외 발생 JDBCTemplate의 close() 참조");
			e.printStackTrace();
			
		}
		
		
	}

}
