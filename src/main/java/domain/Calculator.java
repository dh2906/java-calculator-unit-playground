package domain;

public class Calculator {
    int add(int num1, int num2) {
        return num1 + num2;
    }

    int sub(int num1, int num2) {
        return num1 - num2;
    }

    int mul(int num1, int num2) {
        return num1 * num2;
    }

    int div(int num1, int num2) {
        if (num2 == 0)
            throw new RuntimeException("0으로 나눌 수 없습니다.");

        return num1 / num2;
    }
}
