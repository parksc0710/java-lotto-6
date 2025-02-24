package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.domain.LottoBiz;
import lotto.model.Lotto;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void createLottoByOverSize() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createLottoByDuplicatedNumber() {
        // TODO: 이 테스트가 통과할 수 있게 구현 코드 작성
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // 아래에 추가 테스트 작성 가능
	@DisplayName("List<Integer> 오름차순 정렬 테스트")
	@Test
	void integerListOrderAsc() {
		List<Integer> before = new ArrayList<Integer>(Arrays.asList(5, 4, 3, 1, 2, 6));
		List<Integer> after = LottoBiz.sortRandomNumber(before);
		Lotto afterLotto = new Lotto(after);
		assertThat(afterLotto.getNumbersString()).isEqualTo("[1, 2, 3, 4, 5, 6]");
	}
	
	@DisplayName("당첨 번호는 1이상 45이하의 숫자이어야한다.")
    @Test
    void checkLottoNumber() {
        assertThatThrownBy(() -> LottoBiz.checkInpuNumberList("1,2,3,4,5,67"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}