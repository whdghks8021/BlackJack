package com.biz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.vo.BVO;

// BService 클래스 선언
public class BService {

	// 카드를 담을 cardList 선언
	List<BVO> cardList;
	// 딜러에게 주어진 카드를 담을 list 선언
	List<BVO> dealer;
	// 게이머에게 주어진 카드를 담을 list 선언
	List<BVO> gamer;
	// 스캐너 사용을 위해 scan을 선언
	Scanner scan;

	// BService의 생성자, main 클래스에서 호출
	public BService() {
		// 선언한 cardList와 딜러, 게이머 list를 ArrayList로 초기화
		cardList = new ArrayList();
		dealer = new ArrayList();
		gamer = new ArrayList();
		// 키보드입력을 위해 스캐너에 System.in을 이용하여 초기화
		scan = new Scanner(System.in);
	}

	// 카드를 세팅하는 method 총 52장의카드이며 4개의 패턴무늬가 있다.
	// main 클래스에서 사용하기때문에 public으로 선언
	public void cardSet() {
		// String형 배열에 4개의 패턴을 넣어준다.
		String[] pattern = { "♣", "◆", "♥", "♠" };
		// 카드는 A ~ K까지 총 13단계이기때문에 intNum변수에 13을 기입.
		int intNum = 13;

		// 이제 각각의 패턴에 13장의 카드를 넣어주기위해 반복문을 사용
		for (String p : pattern) {
			// 한개의 패턴에 1 ~ 13까지의 숫자를 기입해준다.
			for (int i = 1; i <= intNum; i++) {
				// 생성된 카드를 담을 vo를 선언해준다.
				BVO vo = new BVO();
				// 카드의 숫자를 String형 num변수에 담아주는데,
				// 1 = A , 11 ~ 13 = J Q K로 변환하여 담기위하여 check method를 이용한다.
				String num = check(i);
				// 카드의 패턴과 변환된 숫자를 vo에 세팅한다.
				vo.setNumber(num);
				vo.setPattern(p);
				// 세팅한 vo를 카드list에 추가시켜준다.
				cardList.add(vo);
			}
		}
		// 모든카드가 준비되면 console창에 알려준다.
		System.out.println("카드 준비 완료.");
		System.out.println("게임을 시작합니다.");

		// 카드가 준비되었으므로, 딜러와 게이머에게 2장씩 드로우하는 method를 실행한다.
		distribute();

	}

	// 이 method는 게이머와 딜러에게 카드를 1장씩 드로우하는 역할을 한다.
	// 이 method도 main클래스에서 사용하기 때문에 public으로 선언
	public void addCard() {
		// 키보드입력을 위해 스캐너에 System.in을 이용하여 초기화
		scan = new Scanner(System.in);
		// 무한반복문을 실행하여 게임 종료조건이 될때까지 카드 드로우를 선택한다.
		while (true) {
			// 카드를 드로우 하겠냐고 console창에 표시한다.
			System.out.println("카드를 드로우 하시겠습니까? (YES/NO)");
			System.out.print(">>");
			// scan을 이용하여 키보드입력을 받은뒤 그 값을 sc변수에 저장한다.
			String sc = scan.nextLine();
			// 만약 입력받은 값이 'YES' 이면 아래 method들이 실행된다.
			if (sc.equalsIgnoreCase("YES")) {
				// 게이머는 드로우에 조건이 없기때문에 카드를 한장 뽑는다.
				gamerDraw();
				// 그리고 현재 가지고 있는 카드를 console에 보여준다.
				showGamerCard();
			}
			// 딜러의경우 드로우에 조건이 있다.
			// 카드의 합이 17이하이면 드로우를 해야하기 때문에
			// 게이머가 'YES' 와 'NO' 어떤것을 선택하던지 딜러는 카드의합이 17이하면 드로우해야한다.
			dealerDraw();
			// 이제 각각 한턴씩 지나게되면 게임종료 조건을 검사하게된다.
			// condition method에 게이머의 드로우 선택과 다양한 조건을 검사하여
			// 조건이 성립되면 '0'이라는 값을 받아 게임이 종료된다.
			if (condition(sc) == 0)
				return;

		}
	}

	// int 매개변수를 받아 값을 변환하여 String형으로 리턴하는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private String check(int i) {

		if (i == 1) // 1을 A로 리턴
			return "A";
		if (i == 11) // 11을 J로 리턴
			return "J";
		if (i == 12) // 12를 Q로 리턴
			return "Q";
		if (i == 13) // 13을 K로 리턴
			return "K";
		return "" + i; // 위 4가지 값이 아닌경우, 문자열로 변환만 하여 리턴
	}

	// 카드덱이 완성되고, 게임이 시작하면 딜러와 게이머에게 2장씩 카드를 드로우하는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void distribute() {
		// 2장의 카드를 뽑기위해 2번 반복한다.
		for (int i = 0; i < 2; i++) {
			// 카드 1장을 드로우하는 draw() method를 이용해서
			// 게이머list와 딜러list에게 한장씩 추가해준다.
			gamer.add(draw());
			dealer.add(draw());
		}
		// 카드를 2장씩 받은후, 게이머는 자신의 카드를 console에서 확인할수있도록 하는
		// method를 실행한다.
		showGamerCard();
		System.out.println("딜러와 게이머 2장씩 지급 완료");
	}

	// 카드를 드로우하는 method 이며, 카드1장을 리턴하기위해 BVO를 객체로 선언
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private BVO draw() {
		// 카드를 담을 vo를 선언하고 초기화한다.
		BVO vo = new BVO();
		// 랜덤의 카드를 뽑기위해서, 총 카드의 개수 안에서 랜덤값이 형성된다.
		int rnd = (int) (Math.random() * cardList.size());
		// 만들어진 랜덤값을 이용해서 cardList에있는 한장을 뽑아서 저장한다.
		vo = cardList.get(rnd);
		// 뽑은 카드는 중복으로 뽑히지 않아야하기 때문에, cardList에서 삭제해준다.
		cardList.remove(rnd);
		// 뽑은 카드(vo)를 리턴해준다.
		return vo;
	}
	// 딜러가 카드를 드로우하는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void dealerDraw() {
		// 딜러는 카드를 드로우하는데 조건이있기때문에, 조건문을 사용한다.
		// dealerSum() method를 이용해서, 카드의합이 17미만이면 드로우 한다.
		// 여기서 21이 넘어가면 0이라는 값을 리턴하게 되어있으므로, 17미만이지만 0이아닐경우에만
		// 드로우 하도록 한다.
		if (dealerSum() < 17 && dealerSum() != 0) {
			// draw() method로 드로우한 카드를 딜러list에 추가한다.
			dealer.add(draw());
			System.out.println("딜러가 카드한장을 드로우 했습니다.");
		} else {
			// 만약 딜러의 카드합이 17이상이거나, 21을초과한경우 드로우하지 않았다는 메시지를 보여준다.
			System.out.println("딜러는 카드를 드로우 하지 않았습니다.");
		}

	}
	// 게이머가 카드를 드로우하는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void gamerDraw() {
		// 게이머는 카드를 드로우하는데 딱히 조건이 없으므로
		// draw() method로 드로우한 카드를 게이머list에 추가한다.
		gamer.add(draw());
		System.out.println("게이머가 카드한장을 드로우 했습니다.");
	}
	// 게임종료조건을 검사하는 method
	// String형 매개변수를 이용하여, 게이머의 드로우 여부를 참고한다.
	// 종료조건이 성립되면 '0'을 리턴하여 알려준다.
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private int condition(String sc) {
		// 먼저 딜러의 합계가 17이넘어서 드로우 하지않고, 게이머또한 드로우를 선택하지 않았을때
		if (dealerSum() >= 17 && sc.equalsIgnoreCase("NO")) {
			// 승자를 결정하는 winner() method 실행 후 게임종료신호인 '0'을 리턴
			winner();
			return 0;
		}
		// 게이머나 딜러 둘중 하나의 카드합이 21(블랙잭)이 된 경우
		if (gamerSum() == 21 || dealerSum() == 21) {
			// 승자를 결정하는 winner() method 실행 후 게임종료신호인 '0'을 리턴
			winner();
			return 0;
		}
		// 게이머나 딜러 둘중 하나의 카드합이 21을 초과해버린 경우
		if (gamerSum() == 0 || dealerSum() == 0) {
			// 승자를 결정하는 winner() method 실행 후 게임종료신호인 '0'을 리턴
			winner();
			return 0;
		}
		// 위 조건에 성립되지 않는경우 '1'의 값을 리턴하여
		// 게임이 계속 진행되도록 한다.
		return 1;
	}

	// 승자를 가리는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void winner() {
		System.out.println("승자를 가립니다.");
		// 딜러의 카드합이 게이머의 합보다 높은경우 딜러의 승리
		if (dealerSum() > gamerSum())
			System.out.println("★☆★☆ 딜러 승리 ★☆★☆");
		// 게이머의 카드합이 딜러의 합보다 높은경우 게이머의 승리
		if (dealerSum() < gamerSum())
			System.out.println("★☆★☆ 게이머 승리 ★☆★☆");
		// 딜러와 게이머의 카드합이 같은경우 무승부
		if (dealerSum() == gamerSum())
			System.out.println("★☆★☆ 무승부 ★☆★☆");
		// 게임이 종료되면 서로의 카드를 보여주는 method 실행
		showAllCard();
	}
	// 딜러와 게이머의 카드를 보여주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void showAllCard() {
		showDealerCard(); // 딜러의 카드를 보여주는 method
		showGamerCard(); // 게이머의 카드를 보여주는 method
	}
	// 딜러의 카드를 보여주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void showDealerCard() {
		System.out.println(" < 딜러 덱 >");
		// 딜러list에 있는 카드를 한장씩 vo에 저장하여 console창에 보여준다.
		// 딜러list가 더이상 없으면 반복문이 종료된다.
		for (BVO vo : dealer) {
			System.out.print(vo.getPattern() + vo.getNumber() + " / ");
		}
		// 딜러의 카드와 합계를 표시해준다.
		System.out.println("합계 : " + dealerSum());
	}
	// 게이머의 카드를 보여주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private void showGamerCard() {
		System.out.println("< 게이머 덱 >");
		// 게이머list에 있는 카드를 한장씩 vo에 저장하여 console창에 보여준다.
		// 게이머list가 더이상 없으면 반복문이 종료된다.
		for (BVO vo : gamer) {
			System.out.print(vo.getPattern() + vo.getNumber() + " / ");
		}
		// 게이머의 카드와 합계를 표시해준다.
		System.out.println("합계 : " + gamerSum());
	}
	// 딜러의 카드합계를 계산하여 값을 리턴해주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private int dealerSum() {
		// 합계를 담을 int형 변수 선언
		int sum = 0;
		// 딜러list에 있는 카드를 한장씩 vo에 저장한다.
		// 딜러list가 더이상 없으면 반복문이 종료된다. 
		for (BVO vo : dealer) {
			// vo에 저장된 값을 change() method를 이용하여 숫자로 리턴받는다
			// A = 1 , J Q K = 10 을 리턴해주고, 나머지 숫자는 그대로 받게된다.
			// 받은값을 sum변수에 누적시켜준다.
			sum += change(vo);
		}
		// 만약 카드의 합이 21을 초과하게 된다면 '0'이라는 값을 리턴해준다.
		if (sum > 21)
			return 0;
		// 그렇지 않은경우 그 값을 리턴해준다.
		return sum;
	}
	// 게이머의 카드합계를 계산하여 값을 리턴해주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private int gamerSum() {
		// 합계를 담을 int형 변수 선언
		int sum = 0;
		// 게이머list에 있는 카드를 한장씩 vo에 저장한다.
		// 게이머list가 더이상 없으면 반복문이 종료된다. 
		for (BVO vo : gamer) {
			// vo에 저장된 값을 change() method를 이용하여 숫자로 리턴받는다
			// A = 1 , J Q K = 10 을 리턴해주고, 나머지 숫자는 그대로 받게된다.
			// 받은값을 sum변수에 누적시켜준다.
			sum += change(vo);
		}
		// 만약 카드의 합이 21을 초과하게 된다면 '0'이라는 값을 리턴해준다.
		if (sum > 21)
			return 0;
		// 그렇지 않은경우 그 값을 리턴해준다.
		return sum;
	}
	// 카드의 값를 매개변수로 받아 변환하여 리턴해주는 method
	// BService 안에서만 사용되는 method이기 때문에 private으로 선언
	private int change(BVO vo) {
		// 받은 카드의 숫자영역을 String형 변수에 저장한다.
		String strNum = vo.getNumber();
		// 받은 카드가 'A'인 경우 1을 리턴
		if (strNum.equals("A"))
			return 1;
		// 받은 카드가 'J'인 경우 10을 리턴
		if (strNum.equals("J"))
			return 10;
		// 받은 카드가 'Q'인 경우 10을 리턴
		if (strNum.equals("Q"))
			return 10;
		// 받은 카드가 'K'인 경우 10을 리턴
		if (strNum.equals("K"))
			return 10;
		// 위 4가지 조건의 카드가 아닌경우 그 값을 int형으로 변환하여 리턴
		int intNum = Integer.valueOf(strNum);

		return intNum;

	}
}
