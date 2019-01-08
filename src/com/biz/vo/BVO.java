package com.biz.vo;

// 카드의 문양과 숫자를 멤버변수로 선언하기위해 BVO라는 클래스 생성
public class BVO {
	
	// 카드의 문양을 저장할 String형 멤버변수 선언
	private String pattern ;
	// 카드의 숫자를 저장할 String형 멤버변수 선언
	private String number ;

	// 각각의 멤버변수를 사용할수있게 getter와 setter를 생성
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "BVO [pattern=" + pattern + ", number=" + number + "]";
	}
	

}
