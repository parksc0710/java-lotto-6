package lotto.view;

public enum LottoEnum {
	
	LOTTO_PRICE(1000),
	LOSE(0),
    MATCH_3(5000),
    MATCH_4(50000),
    MATCH_5(1500000),
    MATCH_5B(30000000),
    MATCH_6(2000000000);

    private final int money;

	LottoEnum(int i) {
        this.money = i;
    }

    public int getMoney() {
        return money;
    }
}
