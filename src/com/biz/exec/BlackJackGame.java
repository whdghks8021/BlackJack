package com.biz.exec;

import com.biz.service.BService;

// 블랙잭 게임이 실행되는 클래스
public class BlackJackGame {
	
	// 이 프로젝트를 실행하면 가장먼저 실행되는 main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// BService를 사용하기위해 선언과 초기화
		BService bs = new BService();
		// BService안의 cardSet() method를 실행
		bs.cardSet();
		// BService안의 addCard() method를 실행
		bs.addCard();
	}

}
