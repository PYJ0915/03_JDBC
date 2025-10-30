package edu.kh.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 				   클래스명.메소드()가 아닌 메서드명() 만 작성하여도 호출 가능하게 한다!
// 				   => 작성법 import static 임포트 경로.*
import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) DAO (Data Access Object, 데이터 접근 객체)
// : 데이터가 저장된 곳(DB)에 접근하는 용도의 객체
// => DB에 접근하여 Java에서 원하는 결과를 얻기 위해 SQL을 수행하고 결과를 반환받는 역할
public class UserDAO {
	
	// 필드 => DB접근 관련한 JDBC 객체 참조변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	/** 1. User 등록 DAO
	 * @param user : 입력받은 ID, PW, Name 세팅된 User 객체
	 * @param conn : DB연결 정보가 담겨 있는 Connection 객체
	 * @return INSERT된 결과 행의 개수
	 */
	public int insertUser(User user, Connection conn) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? (위치홀더)에 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate()) 후 결과 반환 받기
			result = pstmt.executeUpdate();
			
		} finally {
			
			// 6. 사용한 JDBC 객체 자원 반환
			close(pstmt);
			
		}
		
		return result;
		// 결과 저장용 변수에 저장된 최종 값 반환
	}


	/** User 전체 조회 DAO
	 * @param conn
	 * @return List<User> userList
	 */
	public List<User> selectAll(Connection conn) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		List<User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE 
					FROM TB_USER
					""";
			// 3. PreapredStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? (위치홀더)에 알맞은 값 대입 (위치홀더 없으면 생략)
			
			// 5. SQL(SELECT) 수행 (executeQuery()) 후 결과 반환(Result Set) 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과(rs)를 한 행씩 접근하여 컬럼 값 얻어오기
			// cf) 몇 행이 올지 모르는 경우 => while, 무조건 한 행 => if
			
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// java.sql.Date 타입으로 값을 저장하지 않은 이유 
				// => SELECT 문에서 TO_CHAR()를 이용하여 문자열 형태로 변환해 조회해왔기 때문!
				
				// User 객체 새로 생성하여 DB에서 얻어온 컬럼 값 필드로 세팅
				userList.add(new User(userNo, userId, userPw, userName, enrollDate));
				
			}
			
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		// 조회 결과가 담긴 List 반환
		return userList;
	}


	public List<User> selectName(Connection conn, String input) throws Exception{
		
		List<User> userList = new ArrayList<User>();
		
		try {
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE 
					FROM TB_USER WHERE USER_NAME LIKE '%'||?||'%'
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				userList.add(new User(userNo, userId, userPw, userName, enrollDate));
				
			}
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
			
		}
		
		return userList;
	}


	public User selectUser(Connection conn, int input) throws Exception{
			
		User user = null;
		
		try {
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE 
					FROM TB_USER WHERE USER_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, userId, userPw, userName, enrollDate);
				
			}
				
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		return user;
	}


	public int deleteUser(Connection conn, int userNo) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					DELETE FROM TB_USER WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}
	
}
