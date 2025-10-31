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

	/** 3. 검색어를 입력 받아 이름에 검색어가 포함되는 USER 조회 서비스
	 * @param input
	 * @return
	 */
	public List<User> selectName(String input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<User> userList = dao.selectName(conn, input);
		
		JDBCTemplate.close(conn);
		
		return userList;
	}

	/** 4. USER_NO를 입력 받아 일치하는 USER 조회 서비스
	 * @param input
	 * @return
	 */
	public User selectUser(int input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		User user = dao.selectUser(conn, input);
		
		JDBCTemplate.close(conn);
		
		return user;
	}

	/** 5. USER_NO를 입력 받아 일치하는 USER 삭제 서비스
	 * @param userNo
	 * @return
	 */
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

	/** 6-1. ID, PW를 입력 받아 일치하는 USER 반환 서비스 (풀이 1.)
	 * @param id
	 * @param pw
	 * @return
	 */
 /* public User idpwCheck(String id, String pw) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		User user = dao.idpwCheck(conn, id, pw);
		
		JDBCTemplate.close(conn);
		
		return user;
	} */
	
	/** 6-1.ID, PW를 입력 받아 일치하는 USER 반환 서비스 (풀이 2.)
	 * @param id
	 * @param pw
	 * @return
	 */
	public int idpwCheck(String id, String pw) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int userNo = dao.idpwCheck(conn, id, pw);
		
		JDBCTemplate.close(conn);
		
		return userNo;
	}

	/** 6-2. ID, PW 일치하는 USER 존재하는 경우 이름 수정 서비스 (풀이 1.)
	 * @param name
	 * @param id
	 * @return
	 */
	public int updateName(String name, String id) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateName(conn, name, id);
		
		if(result != 0) {
			
			JDBCTemplate.commit(conn);
			
		} else {
			
			JDBCTemplate.rollback(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 6-2 ID, PW 일치하는 USER 존재하는 경우 이름 수정 서비스 (풀이 2.)
	 * @param name
	 * @param userNo
	 * @return
	 */
	public int updateName(String name, int userNo) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateName(conn, name, userNo);
		
		if(result != 0) {
			
			JDBCTemplate.commit(conn);
			
		} else {
			
			JDBCTemplate.rollback(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
		
	}

	/** 7. ID 중복 검사 서비스
	 * @param userId
	 * @return
	 */
	public int idCheck(String userId) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		JDBCTemplate.close(conn);
		
		return count;
	}

	/** userList에 담겨있는 모든 User 객체를 INSERT하는 서비스
	 * @param userList
	 * @return
	 */
	public int multiInsertUser(List<User> userList) throws Exception{
		
		// 다중 INSERT 방법
		// 1) SQL을 이용한 다중 INSERT
		// 2) Java 반복문을 이용한 다중 INSERT < 
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = 0; // 삽입 성공한 행의 개수
		
		for(User user : userList) {
			int result = dao.insertUser(user, conn);
			count += result; // 삽입 성공한 행의 개수를 count 누적
		}
		
		// 전체 삽입 성공 시 commit / 아니면 rollback (일부 삽입, 전체 실패)
		
		if(count == userList.size()) { 
			
			JDBCTemplate.commit(conn);
			
		} else {
			
			JDBCTemplate.rollback(conn);
			
		}
	
		JDBCTemplate.close(conn);
		
		return count;
	}

	
}
