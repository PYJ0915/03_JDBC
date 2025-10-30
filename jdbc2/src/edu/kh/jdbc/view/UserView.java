package edu.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dto.User;
import edu.kh.jdbc.model.service.UserService;

// View : 사용자와 직접 상호작용하는 화면(UI)를 담당
// => 사용자에게 입력을 받고 결과를 출력하는 역할
public class UserView {

	private UserService service = new UserService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * User 관리 프로그램의 메인 메뉴 UI (View)
	 */
	public void mainMenu() {
		
		int menuNum = 0; // 메뉴선택용 변수
		
		do {
			
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				menuNum = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				switch (menuNum) {
				case 1: insertUser(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;
				case 4: selectUser(); break;
				case 5: deleteUser(); break;
				case 6: updateName(); break;
				case 7: insertUser2(); break;
				case 8: multiInsertUser(); break;
				case 0: System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				System.out.println("\n-------------------------------------\n");
				
				
			} catch (InputMismatchException e) {
				// 스캐너 사용 중 자료형이 잘못 입력된 경우
				
				System.out.println("\n*** 잘못 입력하셨습니다 ***\n");
				menuNum = -1; // 잘못 입력했을 때 while문이 멈추는 것을 방지
				sc.nextLine(); // 입력버퍼에 남아있는 잘못 입력된 문자 제거
				
				e.printStackTrace();
				
			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}
			
			
		} while (menuNum != 0);
		
	}

	/** 1. User 등록 관련 View
	 * 
	 */
	private void insertUser() throws Exception{
		
		System.out.println("\n==== 1. User 등록 ====\n");
		
		System.out.print("ID : ");
		String userId = sc.next();
		
		System.out.print("PW : ");
		String userPw = sc.next();
		
		System.out.print("Name : ");
		String userName = sc.next();
		
		// 입력받은 값 3개를 한 번에 묶어서 전달할 수 있도록 User DTO 객체 생성 후 필드에 값 세팅!
		User user = new User(); // 현재 값이 3개밖에 없기 때문에 모든 매개변수가 있는 생성자 사용 불가! => setter 사용!
		
		// setter를 이용하여 값 세팅!
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		// 서비스 호출 (INSERT) 후 결과 반환(int, 결과 행의 개수) 받기
		int result = service.insertUser(user);
		// service 객체에 있는 insertUser() 메소드 호출
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0) {
			
			System.out.println("\n" + userId + " 사용자가 등록되었습니다.\n");
			
		} else {
			
			System.out.println("\n*** 등록 실패 ***\n");
			
		}
		
	
	}

	/** 2. User 전체 조회 관련 View (SELECT)
	 * 
	 */
	private void selectAll() throws Exception{
		
		System.out.println("\n==== 2. User 전체 조회 ====\n");
		
		// 서비스 호출(SELECT) 후 결과(List<user>) 반환 받기
		List<User> userList = service.selectAll();
		
		// 조회 결과가 없을 경우
		if(userList.isEmpty()) {
			System.out.println("\n**** 조회 결과가 없습니다****\n");
			return;
		}
		
		// 조회 결과 있는 경우
		for(User u: userList) {
			
			System.out.println(u);
			
		}
		
	}

	/** 3. User 중 이름에 검색어가 포함된 회원 조회
	 * 검색어 입력 :
	 */
	private void selectName() throws Exception{
		
		System.out.println("\n==== User 중 이름에 검색어가 포함된 회원 조회 ====\n");
		
		System.out.print("검색어 입력 : ");
		String input = sc.next();
		
		List<User> userList = service.selectName(input);
		
		// 조회 결과가 없을 경우
				if(userList.isEmpty()) {
					System.out.println("\n**** 검색 결과 없음 ****\n");
					return;
				}
				
				// 조회 결과 있는 경우
				for(User u: userList) {
					
					System.out.println(u);
					
				}
		
	}

	/** 4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)
	 * * 딱 1행만 조회되거나 or 일치하는 것을 못찾았거나
	 * 
	 * -- 찾았을 때 : User 객체 출력
	 * -- 없을 때 : USER_NO가 일치하는 회원 없음
	 */
	private void selectUser() throws Exception{
		
		System.out.println("\n==== USER_NO를 입력 받아 일치하는 User 조회 ====\n");
		
		System.out.print("User 번호 입력 : ");
		int input = sc.nextInt();
		
		User user = service.selectUser(input);
		
		if(user == null) {
			System.out.println("\n**** USER_NO 일치하는 회원 없음 ****\n");
			return;
		}
		
		// 조회 결과 있는 경우
	
			System.out.println(user);
		
	}

	/** 5. USER_NO를 입력받아 일치하는 User 삭제(DELETE)
	 * * DML이다!!
	 * 
	 * -- 삭제 성공했을 때 : 삭제 성공
	 * -- 삭제 실패했을 때 : 사용자 번호가 일치하는 User 존재하지 않음
	 */
	private void deleteUser() throws Exception{
		
		System.out.println("\n==== USER_NO를 입력 받아 일치하는 User 삭제 ====\n");
		
		System.out.print("User 번호 입력 : ");
		int userNo = sc.nextInt();
		
		int result = service.deleteUSer(userNo);
		
		if(result != 0) {
			System.out.println("\n****삭제 성공****\n");
			return;
		}
		
		System.out.println("\n**** 사용자 번호가 일치하는 User가 존재하지 않음 ****\n");
		
	}

	/** ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)
	 * 1차적으로 ID, PW 확인
	 * 일치하면 수정할 이름 받고 수정
	 * 불일치 시 "아이디 및 비밀번호가 일치하지 않습니다" 출력
	 */
	private void updateName() throws Exception{
		
		System.out.println("\n==== ID, PW가 일치하는 회원이 있을 경우 이름 수정 ====\n");
		
		System.out.print("ID : ");
		String id = sc.next();
		
		System.out.print("PW : ");
		String pw = sc.next();
		
		User user = service.idpwCheck(id, pw);
		
		if(user == null) {
			
			System.out.println("\n**** 아이디 및 비밀번호가 일치하지 않습니다 ****\n");
			return;
			
		}
		
		System.out.print("수정할 이름을 입력하세요 : ");
		String name = sc.next();
		
		int result = service.updateName(name, id);
		
		if(result != 0) {
			
			System.out.println("\n**** 수정 성공 ****\n");
			return;
			
		}
		
		System.out.println("\n**** 수정 실패 ㅜㅜ ****\n");
		
	}

	private void insertUser2() {
		
		
		
	}

	private void multiInsertUser() {
		
		
		
	}
	
	
	
	

}
