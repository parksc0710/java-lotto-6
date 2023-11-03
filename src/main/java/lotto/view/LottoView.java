package lotto.view;

import java.text.DecimalFormat;
import java.util.Map;

import camp.nextstep.edu.missionutils.Console;
import lotto.Application;
import lotto.domain.LottoBiz;
import lotto.model.Lotto;

public class LottoView {

	public static void start() {
		setMoney();
		setMyLotto();
		setWinnerLotto();
		setBonusNumber();
		showResult();
	}
	
	private static void setMoney() {
		boolean loopFlag = true;
		while(loopFlag) {
			try {
				setMoneyInner();
				loopFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				loopFlag = true;
			}
		}
	}
	
	private static void setMoneyInner() {
		String inputMoney = "";
		System.out.println("구입금액을 입력해 주세요.");
		inputMoney = Console.readLine();
		LottoBiz.checkInpuMoney(inputMoney);
		Application.MY_MONEY = Integer.parseInt(inputMoney);
		System.out.println("");
	}
	
	private static void setMyLotto() {
		LottoEnum lottoEnum = LottoEnum.LOTTO_PRICE;
		int lottoStock = Application.MY_MONEY / lottoEnum.getMoney();
		System.out.println(String.format("%d개를 구매했습니다.", lottoStock));
		for(int i = 0; i < lottoStock; i++) {
			System.out.println(LottoBiz.makeMyLotto());
		}
		System.out.println("");
	}
	
	private static void setWinnerLotto() {
		boolean loopFlag = true;
		while(loopFlag) {
			try {
				setWinnerLottoInner();
				loopFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				loopFlag = true;
			}
		}
	}
	
	private static void setWinnerLottoInner() {
		String inputLottoNumbers = "";
		System.out.println("당첨 번호를 입력해 주세요.");
		inputLottoNumbers = Console.readLine();
		LottoBiz.checkInpuNumberList(inputLottoNumbers);
		Lotto inputLotto = LottoBiz.setLotto(inputLottoNumbers);
		Application.WINNING_LOTTO = inputLotto;
		System.out.println("");
	}
	
	private static void setBonusNumber() {
		boolean loopFlag = true;
		while(loopFlag) {
			try {
				setBonusNumberInner();
				loopFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				loopFlag = true;
			}
		}
	}
	
	private static void setBonusNumberInner() {
		String inputBonusNumber = "";
		System.out.println("보너스 번호를 입력해 주세요.");
		inputBonusNumber = Console.readLine();
		LottoBiz.checkInpuBonusNumber(inputBonusNumber);
		Application.BONUS_NUMBER = Integer.parseInt(inputBonusNumber);
		System.out.println("");
	}
	
	private static void showResult() {
		resultHeader();
		LottoBiz.matchLotto();
		LottoBiz.makePrize();
		resultBody();
		resultFooter();
	}
	
	private static void resultHeader() {
		System.out.println("당첨 통계");
		System.out.println("---");
	}
	
	private static void resultBody() {
		for (Map.Entry<LottoEnum, Integer> entry : Application.MATCH_MAP.entrySet()) {
            LottoEnum key = entry.getKey();
            int cnt = entry.getValue();
            String resultString = resultBodyInner(key, cnt);
            System.out.println(resultString);
        }
	}
	
	private static String resultBodyInner(LottoEnum key, int cnt) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String resultString = "";
        if(key.equals(LottoEnum.MATCH_3)) 
        	resultString = String.format("3개 일치 (%s원) - %d개", decimalFormat.format(key.getMoney()), cnt);
        if(key.equals(LottoEnum.MATCH_4)) 
        	resultString = String.format("4개 일치 (%s원) - %d개", decimalFormat.format(key.getMoney()), cnt);
        if(key.equals(LottoEnum.MATCH_5)) 
        	resultString = String.format("5개 일치 (%s원) - %d개", decimalFormat.format(key.getMoney()), cnt);
        if(key.equals(LottoEnum.MATCH_5B)) 
        	resultString = String.format("5개 일치, 보너스 볼 일치 (%s원) - %d개", decimalFormat.format(key.getMoney()), cnt);
        if(key.equals(LottoEnum.MATCH_6)) 
        	resultString = String.format("6개 일치 (%s원) - %d개", decimalFormat.format(key.getMoney()), cnt);
        return resultString;
	}
	
	private static void resultFooter() {
		System.out.println(String.format("총 수익률은 %s입니다.", Application.PROFIT_RATE));
	}
}
