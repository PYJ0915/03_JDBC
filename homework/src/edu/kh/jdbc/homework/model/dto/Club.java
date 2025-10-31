package edu.kh.jdbc.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Club {

	private int clubNo;
	private String clubName;
	private int capacity;
	private String ECYN;
	private String activityType;
	
	@Override
	public String toString() {
		return "동아리 번호 : " + clubNo + " / 동아리 이름 : " + clubName + " / 정원 : " + capacity + " / 대외 활동 여부 : " + ECYN
				+ " / 활동 성격: " + activityType;
	}
	
	
}
