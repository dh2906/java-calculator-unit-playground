import domain.StringCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {
    private final StringCalculator stringCalculator = new StringCalculator();

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3:4", "1 , 2: 3,4 "})
    public void 기본_구분자_문자열_계산기_테스트(String str) {
        int actual = stringCalculator.calculate(str);

        assertEquals(10, actual);
    }

    @ParameterizedTest
    @CsvSource({
            " '//+\n', '+' ",
            " '// \n', ' ' ",
            " '//||\n', '||' "
    })
    public void 커스텀_구분자_파싱_테스트(String str, String expected) {
        String customDelimeter = stringCalculator.findCustomDelimeter(str);

        assertEquals(expected, customDelimeter);
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환() {
        String[] tokens = {"1", "2", "3", "4"};

        List<Integer> actual = stringCalculator.parseNumber(tokens);

        assertEquals(List.of(1, 2, 3, 4), actual);
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환_음수가_포함되면_예외_발생() {
        String[] tokens = {"1", "2", "-3", "4"};

        assertThrows(RuntimeException.class, () -> stringCalculator.parseNumber(tokens));
    }

    @Test
    public void 문자열_리스트_정수_리스트로_변환_문자가_포함되면_예외_발생() {
        String[] tokens = {"a1", "b", " ", "4"};

        assertThrows(RuntimeException.class, () -> stringCalculator.parseNumber(tokens));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//+\n1+2+3+4",
            "// \n1 2 3 4",
            "//||\n1||2||3||4"
    })
    public void 커스텀_구분자_문자열_계산기_테스트(String str) {
        int actual = stringCalculator.calculate(str);

        assertEquals(10, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//1;2;3;4",
            "\n1;2;3;4",
            "//\n1;2;3;4"
    })
    public void 커스텀_구분자_형식에_맞지_않은_경우_예외_발생(String str) {
        assertThrows(RuntimeException.class, () -> stringCalculator.calculate(str));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " "
    })
    public void 문자열이_비어있는_경우_예외_발생(String str) {
        assertThrows(RuntimeException.class, () -> stringCalculator.calculate(str));
    }

    @Test
    public void 구분자_없이_숫자만_입력한_경우() {
        String str = "1";
        int actual = stringCalculator.calculate(str);

        assertEquals(1, actual);
    }
}
