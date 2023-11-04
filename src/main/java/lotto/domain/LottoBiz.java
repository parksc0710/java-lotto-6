package lotto.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.Application;
import lotto.model.Lotto;
import lotto.view.LottoEnum;

public class LottoBiz {

	public static Lotto setLotto(String inputLottoNumbers) {
		List<Integer> numList = Arrays.stream(inputLottoNumbers.split(","))
				.map(String::trim) 
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		return new Lotto(numList);
	}

	public static String makeMyLotto() {
		List<Integer> randomNums = Randoms.pickUniqueNumbersInRange(1, 45, 6);
		randomNums = sortRandomNumber(randomNums);
		Lotto myLotto = new Lotto(randomNums);
		Application.MY_LOTTO.add(myLotto);
		return myLotto.getNumbersString();
	}

	public static List<Integer> sortRandomNumber(List<Integer> randomNums) {
		return randomNums.stream()
            .sorted()
            .collect(Collectors.toList());
	}

	public static void checkInpuMoney(String inputMoney) {
		LottoEnum lottoEnum = LottoEnum.LOTTO_PRICE;
		int lottoPrice = lottoEnum.getMoney();
		if (!checkNumber(inputMoney)) {
			throw new IllegalArgumentException("[ERROR] 구매금액은 숫자여야 합니다.");
		}
		if (Integer.parseInt(inputMoney) % lottoPrice != 0) {
			throw new IllegalArgumentException(String.format("[ERROR] 구매급액은 %d원 단위여야 합니다.", lottoPrice));
		}
	}

	public static void checkInpuNumberList(String inputLottoNumbers) {
		String[] numberArray = inputLottoNumbers.split(",");
		for (String num : numberArray) {
			if (!checkNumber(num.trim())) {
				throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자여야 합니다.");
			}
			if (!(Integer.parseInt(num.trim()) >= 1 && Integer.parseInt(num.trim()) <= 45)) {
				throw new IllegalArgumentException("[ERROR] 당첨 번호는 1이상 45이하여야 합니다.");
			}
		}
	}

	public static void checkInpuBonusNumber(String inputBonusNumber) {
		if (!checkNumber(inputBonusNumber)) {
			throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자여야 합니다.");
		}
		if (!(Integer.parseInt(inputBonusNumber) >= 1 && Integer.parseInt(inputBonusNumber) <= 45)) {
			throw new IllegalArgumentException("[ERROR] 보너스 번호는 1이상 45이하여야 합니다.");
		}
		if (Application.WINNING_LOTTO.getNumbers().contains(Integer.parseInt(inputBonusNumber))) {
			throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
		}
	}

	private static boolean checkNumber(String input) {
		return input.matches("\\d+");
	}

	public static void matchLotto() {
		initMatchMap();
		for (Lotto tmp : Application.MY_LOTTO) {
			matchLottoInner(tmp);
		}
	}

	private static void initMatchMap() {
		Application.MATCH_MAP.put(LottoEnum.MATCH_3, 0);
		Application.MATCH_MAP.put(LottoEnum.MATCH_4, 0);
		Application.MATCH_MAP.put(LottoEnum.MATCH_5, 0);
		Application.MATCH_MAP.put(LottoEnum.MATCH_5B, 0);
		Application.MATCH_MAP.put(LottoEnum.MATCH_6, 0);
	}

	private static void matchLottoInner(Lotto myLotto) {
		LottoEnum result = myLotto.getMatch(Application.WINNING_LOTTO, Application.BONUS_NUMBER);
		if(result != LottoEnum.LOSE) {
			int matchCnt = Application.MATCH_MAP.get(result);
			Application.MATCH_MAP.put(result, matchCnt + 1);
		}
	}

	public static void makePrize() {
		for (Map.Entry<LottoEnum, Integer> entry : Application.MATCH_MAP.entrySet()) {
			LottoEnum winnerType = entry.getKey();
			int winnerCount = entry.getValue();
			Application.TOTAL_PRIZE += winnerType.getMoney() * winnerCount;
		}
		Application.PROFIT_RATE = String.format("%,.1f%%", (double)Application.TOTAL_PRIZE / Application.MY_MONEY * 100);
	}
}
