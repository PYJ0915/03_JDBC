package edu.kh.jdbc.homework.view;

import java.util.Scanner;

import edu.kh.jdbc.homework.model.service.StudentService;

public class StudentView {

	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		
		int menuNum = 0;
		
		do {
			try {
				
				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 삭제");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 동아리 목록 조회");
				System.out.println("7. 동아리 추가");
				System.out.println("8. 동아리 정보 수정");
				System.out.println("9. 동아리 삭제");
				System.out.println("10. 동아리원 등록 및 삭제");
				
				
				
			} catch (Exception e) {
				
				
				
				
			}
			
			
		} while (menuNum != 0);
		
	}
	
}
