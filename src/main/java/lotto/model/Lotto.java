package lotto.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lotto.view.LottoEnum;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
        if(checkDuplicate(numbers)) {
        	throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현
    private boolean checkDuplicate(List<Integer> numbers) {
    	Set<Integer> tmpSet = new HashSet<>();
        for (int number : numbers) {
            if (tmpSet.contains(number)) {
                return true;
            }
            tmpSet.add(number);
        }
        return false;
    }
    
    public void setOrderAsc() {
    	numbers.sort(null);
    }
    
    public List<Integer> getNumbers() {
    	return this.numbers;
    }
    
    public String getNumbersString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < numbers.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(numbers.get(i));
        }
        result.append("]");
        return result.toString();
    }
    
    public LottoEnum getMatch(Lotto winnerLotto, int bonusNumber) {
    	LottoEnum result = LottoEnum.LOSE;
    	int matchedNumber = 0;
        for (Integer num : numbers) {
            if (winnerLotto.getNumbers().contains(num)) {
            	matchedNumber++;
            }
        }
        result = getMatchInner(matchedNumber, bonusNumber);
    	return result;
    }
    
    private LottoEnum getMatchInner(int matchedNumber, int bonusNumber) {
    	LottoEnum result = LottoEnum.LOSE;
    	if(matchedNumber == 3) 
        	result = LottoEnum.MATCH_3;
        if(matchedNumber == 4) 
        	result = LottoEnum.MATCH_4;
        if(matchedNumber == 5 && !this.numbers.contains(bonusNumber)) 
        	result = LottoEnum.MATCH_5;
        if(matchedNumber == 5 && this.numbers.contains(bonusNumber)) 
        	result = LottoEnum.MATCH_5B;
        if(matchedNumber == 6) 
        	result = LottoEnum.MATCH_6;
        return result;
    }
}
