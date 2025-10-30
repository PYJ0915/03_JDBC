package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.UserDAO;
import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) Service : 비즈니스로직을 처리하는 계층
//  => 데이터를 가공하고 트랜잭션(Commit, Rollback) 관리 수행
public class UserService {
	
	// 필드
	private UserDAO dao = new UserDAO();

	/** 1. User 등록 서비스
	 * @param user : 입력받은 ID, PW, Name 세팅된 객체
	 * @return INSERT된 결과 행의 개수
	 */
	public int insertUser(User user) throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공 (필요하지 않다면 생략!)
		// EX) 이름 + "님" 
		
		// 3. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.insertUser(user, conn);
		
		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어
		if(result > 0) { // INSERT 성공!
			
			JDBCTemplate.commit(conn);
			
		} else { // INSERT 실패!
			
			JDBCTemplate.rollback(conn);
			
		}
		
		// 5. Connection 반환
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 2. User 전체 조회 서비스
	 * @return 조회된 User들이 담긴 List
	 */
	public List<User> selectAll() throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. DAO 메서드 호출(SELECT) 후 결과 반환(List<User>) 받기
		List<User> userList = dao.selectAll(conn);
		
		// 3. 커넥션 반환
		JDBCTemplate.close(conn);
		
		// 4. 결과 반환
		return userList;
	}

	public List<User> selectName(String input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<User> userList = dao.selectName(conn, input);
		
		JDBCTemplate.close(conn);
		
		return userList;
	}

	public User selectUser(int input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		User user = dao.selectUser(conn, input);
		
		JDBCTemplate.close(conn);
		
		return user;
	}

	public int deleteUSer(int userNo) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.deleteUser(conn, userNo);
		
		if(result != 0) {
			
			JDBCTemplate.commit(conn);
			
		} else {
			
			JDBCTemplate.rollback(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
}
