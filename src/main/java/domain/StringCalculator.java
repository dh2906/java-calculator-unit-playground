package domain;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    public static final String EMPTY_STRING = "문자열이 비어있습니다.";
    public static final String CUSTOM_DELIMITER_NOT_FOUND = "커스텀 구분자를 찾을 수 없습니다.";
    public static final String NEGATIVE_NUMBER_NOT_ALLOWED = "음수는 처리할 수 없습니다.";
    public static final String INVALID_STRING = "문자열은 처리할 수 없습니다.";
    private final String delimiterRegex = "[,|:]";

    public int calculate(String str) {
        if (str == null || str.isBlank()) {
            throw new RuntimeException(EMPTY_STRING);
        }

        String customDelimiter = findCustomDelimiter(str);
        String strNumbers = extractNumber(str, customDelimiter);
        String[] tokens = splitTokens(strNumbers, customDelimiter);
        List<Integer> numbers = parseNumber(tokens);

        return add(numbers);
    }

    public String findCustomDelimiter(String str) {
        int startDelimiterIdx = str.indexOf("//");
        int endDelimiterIdx = str.indexOf("\n");

        if (startDelimiterIdx == -1 && endDelimiterIdx == -1) {
            return null;
        }

        if ((startDelimiterIdx == -1) ^ (endDelimiterIdx == -1) || (startDelimiterIdx + 2 == endDelimiterIdx)) {
            throw new RuntimeException(CUSTOM_DELIMITER_NOT_FOUND);
        }

        return str.substring(startDelimiterIdx + 2, endDelimiterIdx);
    }

    public String extractNumber(String str, String customDelimiter) {
        if (customDelimiter != null) {
            return str.substring(str.indexOf("\n") + 1);
        }

        return str;
    }

    public String[] splitTokens(String strNumbers, String customDelimiter) {
        if (customDelimiter == null) {
            return strNumbers.split(delimiterRegex);
        }

        return strNumbers.split(Pattern.quote(customDelimiter));
    }

    public List<Integer> parseNumber(String[] tokens) {
        return Arrays.stream(tokens)
                .map(this::parseTokenToInt)
                .toList();
    }

    public int parseTokenToInt(String token) {
        try {
            int number = Integer.parseInt(token.trim());

            if (number < 0) {
                throw new RuntimeException(NEGATIVE_NUMBER_NOT_ALLOWED);
            }

            return number;
        } catch (NumberFormatException ex) {
            throw new RuntimeException(INVALID_STRING);
        }
    }

    public int add(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
