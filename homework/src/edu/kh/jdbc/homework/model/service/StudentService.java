package edu.kh.jdbc.homework.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Club;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();
	
	
	/** 1. 학생 등록 서비스
	 * @param std
	 * @return
	 */
	public int insertStudent(Student std) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insertStudent(conn, std);
		
		if(result == 0) {
			
			JDBCTemplate.rollback(conn);
			
		} else {
			
			JDBCTemplate.commit(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


	/** 2. 전체 학생 조회 서비스
	 * @return
	 */
	public List<Student> selectAllStudent() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectAllStudent(conn);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}


	/** 3. 학생 정보 수정 (이름, 나이, 전공) 서비스
	 * @param std
	 * @return
	 */
	public int updateStudent(Student std) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateStudent(conn, std);
		
		if(result == 0) {
			
			JDBCTemplate.rollback(conn);
			
		} else {
			
			JDBCTemplate.commit(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


	/** 4. 학번으로 학생 삭제 서비스
	 * @param stdNo
	 * @return
	 */
	public int deleteStudent(int stdNo) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.deleteStudent(conn, stdNo);
		
		if(result == 0) {
			
			JDBCTemplate.rollback(conn);
			
		} else {
			
			JDBCTemplate.commit(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


	/** 5. 전공별 학생 조회 서비스
	 * @param input
	 * @return
	 */
	public List<Student> selectByMajor(String input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectByMajor(conn, input);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}


	/** 6. 동아리 목록 조회 서비스
	 * @return
	 */
	public List<Club> selectClub() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Club> clubList = dao.selectClub(conn);
		
		JDBCTemplate.close(conn);
		
		return clubList;
	}


	/** 7. 동아리 등록 서비스
	 * @param club
	 * @return
	 */
	public int insertClub(Club club) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insertClub(conn, club);
		
		if(result == 0) {
			
			JDBCTemplate.rollback(conn);
			
		} else {
			
			JDBCTemplate.commit(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}


	/** 8. 동아리 정보 수정 서비스
	 * @param club
	 * @return
	 */
	public int updateClub(Club club) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateClub(conn, club);
		
		if(result == 0) {
			
			JDBCTemplate.rollback(conn);
			
		} else {
			
			JDBCTemplate.commit(conn);
			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

}
