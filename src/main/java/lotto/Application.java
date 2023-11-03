package lotto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lotto.model.Lotto;
import lotto.view.LottoEnum;
import lotto.view.LottoView;

public class Application {
	
	public static int MY_MONEY = 0;
	public static List<Lotto> MY_LOTTO = new ArrayList<Lotto>(); 
	public static Lotto WINNING_LOTTO;
	public static int BONUS_NUMBER = 0;
	public static Map<LottoEnum, Integer> MATCH_MAP = new TreeMap<LottoEnum, Integer>(); 
	public static int TOTAL_PRIZE = 0;
	public static String PROFIT_RATE = ""; 
			
    public static void main(String[] args) {
    	LottoView.start();
    }
}
