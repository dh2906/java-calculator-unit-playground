import domain.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    public void 덧셈_테스트() {
        final int num1 = 1;
        final int num2 = 2;
        final int actual = calculator.add(num1, num2);

        assertEquals(num1 + num2, actual);
    }

    @Test
    public void 뺄셈_테스트() {
        final int num1 = 3;
        final int num2 = 2;
        final int actual = calculator.sub(num1, num2);

        assertEquals(num1 - num2, actual);
    }

    @Test
    public void 곱셈_테스트() {
        final int num1 = 3;
        final int num2 = 2;
        final int actual = calculator.mul(num1, num2);

        assertEquals(num1 * num2, actual);
    }

    @Test
    public void 나눗셈_테스트() {
        final int num1 = 4;
        final int num2 = 2;
        final int actual = calculator.div(num1, num2);

        assertEquals(num1 / num2, actual);
    }
}
