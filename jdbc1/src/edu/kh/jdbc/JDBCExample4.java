package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class JDBCExample4 {

	public static void main(String[] args) {
		// 부서명을 입력 받아 해당 부서에 근무하는 사원의 사번, 이름, 부서명, 직급명을 직급코드 오름차순 조회
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String dbInfo = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_pyj";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(dbInfo, userName, password);
			
			sc = new Scanner(System.in);
			
			System.out.print("부서명 입력 : ");
			String input = sc.next();
			
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME FROM EMPLOYEE JOIN JOB USING(JOB_CODE) LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID) "
					+ "WHERE DEPT_TITLE = '" + input + "'" + " ORDER BY JOB_CODE";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			/* flag 이용법
			boolean flag = true; */
			
			// return 사용법
			if(!rs.next()) {
				
				System.out.println("일치하는 부서가 없습니다");
				return;
				
			}
			
			// 왜 do-while문?
			// 위 if문 조건에서 이미 첫 번째 행 커서가 소비됨.
			// 보통 while문 사용 시 next()를 바로 만나면서 2행부터 접근
			// do-while문을 사용하여 next()하지 않아도 1번째 행부터 접근할 수 있도록 함!
			
				do {
				
				String empId = rs.getString("EMP_ID");
				String empNmae = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("사번 : %s / 이름 : %s / 부서명 : %s / 직급명 : %s \n", empId, empNmae, deptTitle, jobName);
				 /*flag = false; */
				
			} while(rs.next());
			
		 /*	if(flag) {
				
				System.out.println("일치하는 부서가 없습니다");
				
			} */
			
			
		} catch (Exception e) {
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			
		}
				
		

	}

}
