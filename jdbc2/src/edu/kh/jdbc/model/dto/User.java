package edu.kh.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DTO (Data Transfer Object, 데이터 전송 객체) 
// : 값을 묶어서 전달하는 용도의 객체
// => DB에 데이터를 묶어서 전달하거나, DB에서 조회한 결과를 가져올 때 사용 (데이터 교환용 객체)
// == DB 특정 테이블의 한 행의 데이터를 저장할 수 있는 형태로 class 정의

// lombok : 자바 코드에서 반복적으로 작성해야하는 코드(보일러플레이트 코드)들을 내부적으로 자동 완성 해주는 라이브러리
// [사용법]
/* 1. 사용하고자하는 프로젝트에 라이브러리 추가
 * 2. java 코드 작성하고 있는 IDE를 설치 (tools -> lombok 파일을 이클립스 실행파일 있는 곳으로 복사 -> powershell)
 * */

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수가 있는 생성자
@ToString
public class User {
	private int userNO;
	private String userId;
	private String userPw;
	private String userName;
	private String enrollDate;
	// -> enrollDate를 왜 java.sql.Date 타입이 아니라 String으로 정했는가?
	// -> DB조회 시 날짜 데이터를 원하는 형태의 문자열로 변환하여 조회할 예정
	// -> 2025-10-28 16:18:51.000 -> 2025년 10월 28일 (SELECT문에서 TO_CHAR 사용)
	
	

}
