package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
//@RequiredArgsConstructor : @NonNull, final이 붙은 변수에 대한 생성자 만들기

@Component
@ToString
@Getter
@RequiredArgsConstructor
public class SampleHotel3 {
//Spring 4.3 이후 자동으로 @AutoWired 주입
	
	@NonNull
	private Chef chef;
	
//	public SampleHotel3(Chef chef) { 
//		 this.chef = chef; 
//	}
	 
}
