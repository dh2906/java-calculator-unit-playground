import domain.StringCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class StringCalculatorTest {

    private final StringCalculator stringCalculator = new StringCalculator();

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3:4",
            "1 , 2: 3,4 "
    })
    public void 기본_구분자_문자열_계산기_테스트(String str) {
        int actual = stringCalculator.calculate(str);

        assertThat(actual).isEqualTo(10);
    }

    @ParameterizedTest
    @CsvSource({
            "'//+\n', '+'",
            "'// \n', ' '",
            "'//||\n', '||'"
    })
    public void 커스텀_구분자_파싱_테스트(String str, String expected) {
        String customDelimiter = stringCalculator.findCustomDelimiter(str);

        assertThat(customDelimiter).isEqualTo(expected);
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환() {
        String[] tokens = {"1", "2", "3", "4"};

        List<Integer> actual = stringCalculator.parseNumber(tokens);

        assertThat(actual).containsExactly(1, 2, 3, 4);
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환_음수가_포함되면_예외_발생() {
        String[] tokens = {"1", "2", "-3", "4"};

        assertThatThrownBy(() -> stringCalculator.parseNumber(tokens))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(StringCalculator.NEGATIVE_NUMBER_NOT_ALLOWED);
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환_문자가_포함되면_예외_발생() {
        String[] tokens = {"a1", "b", " ", "4"};

        assertThatThrownBy(() -> stringCalculator.parseNumber(tokens))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(StringCalculator.INVALID_STRING);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//+\n1+2+3+4",
            "// \n1 2 3 4",
            "//||\n1||2||3||4"
    })
    public void 커스텀_구분자_문자열_계산기_테스트(String str) {
        int actual = stringCalculator.calculate(str);

        assertThat(actual).isEqualTo(10);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//1;2;3;4",
            "\n1;2;3;4",
            "//\n1;2;3;4"
    })
    public void 커스텀_구분자_형식에_맞지_않은_경우_예외_발생(String str) {
        assertThatThrownBy(() -> stringCalculator.calculate(str))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(StringCalculator.CUSTOM_DELIMITER_NOT_FOUND);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " "
    })
    public void 문자열이_비어있는_경우_예외_발생(String str) {
        assertThatThrownBy(() -> stringCalculator.calculate(str))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(StringCalculator.EMPTY_STRING);
    }

    @Test
    public void 구분자_없이_숫자만_입력한_경우() {
        String str = "1";
        int actual = stringCalculator.calculate(str);

        assertThat(actual).isEqualTo(1);
    }
}
