package edu.kh.jdbc.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dto.Club;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**  1. 학생 등록 DAO
	 * @param conn
	 * @param std
	 * @return
	 */
	public int insertStudent(Connection conn, Student std) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					INSERT INTO KH_STUDENT VALUES(SEQ_STUDENT_NO.NEXTVAL, ?, ?, ?, DEFAULT, NULL)
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, std.getStdName());
			pstmt.setInt(2, std.getStdAge());
			pstmt.setString(3, std.getMajor());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	/** 2. 전체 학생 조회 DAO
	 * @param conn
	 * @return
	 */
	public List<Student> selectAllStudent(Connection conn) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') AS "ENT_DATE", CLUB_NO, NVL(CLUB_NAME, '없음') AS "CLUB_NAME"
					FROM KH_STUDENT 
					LEFT JOIN KH_CLUB USING (CLUB_NO)
					ORDER BY STD_NO
					""";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				int clubNo = rs.getInt("CLUB_NO");
				String clubName = rs.getString("CLUB_NAME");
				
				stdList.add(new Student(stdNo, stdName, stdAge, major, entDate, clubNo, clubName));
				
			}
			
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
			
		}
		
		return stdList;
	}

	/** 3. 학생 정보 수정 (이름, 나이, 전공) DAO
	 * @param conn
	 * @param std
	 * @return
	 */
	public int updateStudent(Connection conn, Student std) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT 
					SET STD_NAME = ?, STD_AGE = ?, MAJOR = ? 
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, std.getStdName());
			pstmt.setInt(2, std.getStdAge());
			pstmt.setString(3, std.getMajor());
			pstmt.setInt(4, std.getStdNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	/** 4. 학번으로 학생 삭제 DAO
	 * @param conn
	 * @param stdNo
	 * @return
	 */
	public int deleteStudent(Connection conn, int stdNo) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	/** 5. 전공별 학생 조회 DAO
	 * @param conn
	 * @param input
	 * @return
	 */
	public List<Student> selectByMajor(Connection conn, String input) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') AS "ENT_DATE", CLUB_NO, NVL(CLUB_NAME, '없음') AS "CLUB_NAME"
					FROM KH_STUDENT 
					LEFT JOIN KH_CLUB USING (CLUB_NO)
					WHERE MAJOR = ?
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");
				int clubNo = rs.getInt("CLUB_NO");
				String clubName = rs.getString("CLUB_NAME");
				
				stdList.add(new Student(stdNo, stdName, stdAge, major, entDate, clubNo, clubName));
				
			}
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		return stdList;
	}

	/** 6. 동아리 목록 조회 DAO
	 * @param conn
	 * @return
	 */
	public List<Club> selectClub(Connection conn) throws Exception{
		
		List<Club> clubList = new ArrayList<Club>();
		
		try {
			
			String sql = """
					SELECT CLUB_NO, CLUB_NAME, CAPACITY, EC_YN, ACTIVITY_TYPE
					FROM KH_CLUB
					""";
					
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int clubNo = rs.getInt(1);
				String clubName = rs.getString(2);
				int capacity = rs.getInt(3);
				String ECYN = rs.getString(4);
				String activityType = rs.getString(5);
				
				clubList.add(new Club(clubNo, clubName, capacity, ECYN, activityType));
				
			}
			
		} finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
			
		}
		
		
		return clubList;
	}
 
	/** 7. 동아리 등록 DAO
	 * @param conn
	 * @param club
	 * @return
	 */
	public int insertClub(Connection conn, Club club) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					INSERT INTO KH_CLUB VALUES (SEQ_CLUB_NO.NEXTVAL, ?, ?, ?, ?)
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, club.getClubName());
			pstmt.setInt(2, club.getCapacity());
			pstmt.setString(3, club.getECYN());
			pstmt.setString(4, club.getActivityType());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
		
		}
		
		return result;
	}

	/** 8. 동아리 정보 수정 DAO
	 * @param conn
	 * @param club
	 * @return
	 */
	public int updateClub(Connection conn, Club club) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_CLUB 
					SET CLUB_NAME = ?, CAPACITY = ?, EC_YN =?, ACTIVITY_TYPE = ?
					WHERE CLUB_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, club.getClubName());
			pstmt.setInt(2, club.getCapacity());
			pstmt.setString(3, club.getECYN());
			pstmt.setString(4, club.getActivityType());
			pstmt.setInt(5, club.getClubNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(conn);
			
		}
		
		return result;
	}

	
}
