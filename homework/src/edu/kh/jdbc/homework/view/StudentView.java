package edu.kh.jdbc.homework.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Club;
import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;

public class StudentView {

	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);

	public void mainMenu() {

		int menuNum = 0;

		do {

			try {

				System.out.println("\n==== 학생 관리 프로그램 ====\n");

				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 삭제");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 동아리 목록 조회");
				System.out.println("7. 동아리 등록");
				System.out.println("8. 동아리 정보 수정");
				System.out.println("9. 동아리 삭제");
				System.out.println("10. 동아리원 등록 및 삭제");
				System.out.println("11. 동아리 정원 현황");
				System.out.println("12. 동아리 추천");
				System.out.println("\n0. 프로그램 종료");

				System.out.print("\n메뉴 선택 : ");
				menuNum = sc.nextInt();

				switch (menuNum) {
				case 1:
					insertStudent();
					break;
				case 2:
					selectAllStudent();
					break;
				case 3:
					updateStudent();
					break;
				case 4:
					deleteStudent();
					break;
				case 5:
					selectByMajor();
					break;
				case 6:
					selectClub();
					break;
				case 7:
					insertClub();
					break;
				case 8:
					updateClub();
					break;
				case 9:
					deleteClub();
					break;
				case 10:
					manageClubMember();
					break;
				case 11:
					selectCapacity();
					break;
				case 12:
					recommendClub();
					break;
				case 0:
					System.out.println("\n[프로그램을 종료합니다...]\n");
					break;
				default:
					System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}

				System.out.println("\n-------------------------------------\n");

			} catch (InputMismatchException e) {

				System.out.println("\n*** 입력 형식에 맞게 입력해주세요. ***\n");
				sc.nextLine();
				menuNum = -1;

			} catch (Exception e) {

				System.out.println("\n*** 예기지 못한 오류가 발생했습니다. ***\n");
				e.printStackTrace();

			}

		} while (menuNum != 0);

	}

	/**
	 * 1. 학생 등록
	 * 
	 */
	private void insertStudent() throws Exception {

		System.out.println("\n==== 학생 등록 ====\n");

		System.out.print("등록할 학생의 이름 입력 : ");
		String name = sc.next();

		System.out.print("등록할 학생의 나이 입력 : ");
		int age = sc.nextInt();

		System.out.print("등록할 학생의 전공 입력 : ");
		String major = sc.next();

		Student std = new Student();

		std.setStdName(name);
		std.setStdAge(age);
		std.setMajor(major);

		int result = service.insertStudent(std);

		if (result == 0) {

			System.out.println("\n*** 등록 실패 ㅠㅠ ***\n");
			return;
		}

		System.out.println("\n***" + name + " 학생 등록 완료! ***\n");

	}

	/**
	 * 2. 전체 학생 조회
	 * 
	 */
	private void selectAllStudent() throws Exception {

		System.out.println("\n==== 전체 학생 조회 ====\n");

		List<Student> stdList = service.selectAllStudent();

		if (stdList.isEmpty()) {

			System.out.println("\n*** 조회 결과가 없습니다 ***\n");
			return;

		}

		for (Student std : stdList) {

			System.out.println(std);

		}

	}

	/**
	 * 3. 학생 정보 수정 (이름, 나이, 전공)
	 * 
	 */
	private void updateStudent() throws Exception {

		System.out.println("\n==== 학생 정보 수정 ====\n");

		System.out.print("수정할 학생의 학번 입력 : ");
		int stdNo = sc.nextInt();

		System.out.print("수정할 이름 : ");
		String stdName = sc.next();

		System.out.print("수정할 나이 : ");
		int stdAge = sc.nextInt();

		System.out.print("수정할 전공 : ");
		String major = sc.next();

		Student std = new Student();

		std.setStdNo(stdNo);
		std.setStdName(stdName);
		std.setStdAge(stdAge);
		std.setMajor(major);

		int result = service.updateStudent(std);

		if (result == 0) {

			System.out.println("\n*** 등록되지 않은 학번입니다! ***\n");
			return;
		}

		System.out.println("\n*** 수정 완료! ***\n");

	}

	/**
	 * 4. 학번으로 학생 삭제
	 * 
	 */
	private void deleteStudent() throws Exception {

		System.out.println("\n==== 학생 삭제 ====\n");

		System.out.print("삭제할 학생의 학번 입력 : ");
		int stdNo = sc.nextInt();

		int result = service.deleteStudent(stdNo);

		if (result == 0) {

			System.out.println("\n*** 등록되지 않은 학번입니다! ***\n");
			return;
		}

		System.out.println("\n*** 삭제 완료 ***\n");

	}

	/**
	 * 5. 전공별 학생 조회
	 * 
	 */
	private void selectByMajor() throws Exception {

		System.out.println("\n==== 전공별 학생 조회 ====\n");

		System.out.print("조회할 전공 입력 : ");
		String input = sc.next();

		List<Student> stdList = service.selectByMajor(input);

		if (stdList.isEmpty()) {

			System.out.println("\n*** 조회 결과가 없습니다 ***\n");
			return;

		}

		for (Student std : stdList) {

			System.out.println(std);

		}

	}

	/**
	 * 6. 동아리 목록 조회
	 * 
	 */
	private void selectClub() throws Exception {

		System.out.println("\n==== 동아리 목록 조회 ====\n");

		List<Club> clubList = service.selectClub();

		if (clubList.isEmpty()) {

			System.out.println("\n*** 조회 결과가 없습니다 ***\n");
			return;

		}

		for (Club club : clubList) {

			System.out.println(club);

		}

	}

	/**
	 * 7. 동아리 등록
	 * 
	 */
	private void insertClub() throws Exception {

		System.out.println("\n==== 동아리 등록 ====\n");

		sc.nextLine();

		System.out.print("동아리 이름 : ");
		String clubName = sc.nextLine();

		System.out.print("동아리 정원 : ");
		int capacity = sc.nextInt();

		System.out.print("대외 활동 여부 (Y/N) : ");
		String ECYN = sc.next().toUpperCase();
		sc.nextLine();

		System.out.print("활동 성격 : ");
		String activityType = sc.nextLine();

		Club club = new Club();

		club.setClubName(clubName);
		club.setCapacity(capacity);
		club.setECYN(ECYN);
		club.setActivityType(activityType);

		int result = service.insertClub(club);

		if (result == 0) {

			System.out.println("\n*** 등록 실패 ***\n");
			return;

		}

		System.out.println("\n*** " + clubName + " 동아리 등록 완료! ***\n");

	}

	/**
	 * 8. 동아리 정보 수정
	 * 
	 */
	private void updateClub() throws Exception{

		System.out.println("\n==== 동아리 정보 수정 ====\n");

		System.out.print("수정할 동아리의 동아리 번호 입력 : ");
		int clubNo = sc.nextInt();
		sc.nextLine();

		System.out.print("수정할 이름 입력 : ");
		String clubName = sc.nextLine();

		System.out.print("수정할 정원 입력 : ");
		int capacity = sc.nextInt();

		System.out.print("수정할 대외 활동 여부 입력 (Y/N) : ");
		String ECYN = sc.next().toUpperCase();
		sc.nextLine();

		System.out.print("수정할 활동 성격 입력 : ");
		String activityType = sc.nextLine();

		Club club = new Club(clubNo, clubName, capacity, ECYN, activityType);

		int result = service.updateClub(club);

		if (result == 0) {

			System.out.println("\n*** 등록되지 않은 동아리 번호입니다! ***\n");
			return;
		}

		System.out.println("\n*** 수정 완료! ***\n");

	}

	/**
	 * 9. 동아리 삭제
	 * 
	 */
	private void deleteClub() {

	}

	/**
	 * 10. 동아리원 등록 및 삭제
	 * 
	 */
	private void manageClubMember() {

	}

	/**
	 * 11. 동아리 정원 현황 조회 메서드
	 * 
	 */
	private void selectCapacity() {

	}

	/**
	 * 12. 동아리가 존재하지 않는 학생을 랜덤으로 뽑아 동아리를 추천하여 가입여부를 묻고 원하면 가입!
	 * 
	 */
	private void recommendClub() {

	}

}
