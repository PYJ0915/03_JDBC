package edu.kh.jdbc.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	
	private int stdNo;
	private String stdName;
	private int stdAge;
	private String major;
	private String entDate;
	private int clubNo;
	private String clubName;
	
	@Override
	public String toString() {
		return "학번 : " + stdNo + " / 이름 : " + stdName + " / 나이 : " + stdAge + " / 전공 : " + major
				+ " / 입학일 : " + entDate + " / 소속 동아리 : " + clubName;
	}
	
	

}
