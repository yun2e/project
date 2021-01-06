package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Component
@ToString
@Getter
@AllArgsConstructor
public class SampleHotel2 {
//Spring 4.3 이후 자동으로 @AutoWired 주입
	private Chef chef;

	//@AllArgsConstructor : 생성자 파라미터 생략
	/*
	 * public SampleHotel2(Chef chef) { this.chef = chef; }
	 */
}
