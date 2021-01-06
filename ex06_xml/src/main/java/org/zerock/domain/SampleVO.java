package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter, setter 대신
@AllArgsConstructor //모든 생성자 가능
@NoArgsConstructor //기본생성자 없음
public class SampleVO {
	private Integer mno;
	private String firstName;
	private String lastName;
}
