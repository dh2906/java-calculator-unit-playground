package domain;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    public static final String EMPTY_STRING = "문자열이 비어있습니다.";
    public static final String CUSTOM_DELIMITER_NOT_FOUND = "커스텀 구분자를 찾을 수 없습니다.";
    public static final String NEGATIVE_NUMBER_NOT_ALLOWED = "음수는 처리할 수 없습니다.";
    public static final String INVALID_STRING = "문자열은 처리할 수 없습니다.";
    private final String delimeterRegex = "[,|:]";

    public int calculate(String str) {
        if (str == null || str.isBlank()) {
            throw new RuntimeException(EMPTY_STRING);
        }

        String customDelimeter = findCustomDelimeter(str);
        String strNumbers = extractNumber(str, customDelimeter);
        String[] tokens = splitTokens(strNumbers, customDelimeter);
        List<Integer> numbers = parseNumber(tokens);

        return add(numbers);
    }

    public String findCustomDelimeter(String str) {
        int startDelimeterIdx = str.indexOf("//");
        int endDelimeterIdx = str.indexOf("\n");

        if (startDelimeterIdx == -1 && endDelimeterIdx == -1) {
            return null;
        }

        if ((startDelimeterIdx == -1) ^ (endDelimeterIdx == -1) || (startDelimeterIdx + 2 == endDelimeterIdx)) {
            throw new RuntimeException(CUSTOM_DELIMITER_NOT_FOUND);
        }

        return str.substring(startDelimeterIdx + 2, endDelimeterIdx);
    }

    public String extractNumber(String str, String customDelimeter) {
        if (customDelimeter != null) {
            return str.substring(str.indexOf("\n") + 1);
        }

        return str;
    }

    public String[] splitTokens(String strNumbers, String customDelimeter) {
        if (customDelimeter == null) {
            return strNumbers.split(delimeterRegex);
        }

        return strNumbers.split(Pattern.quote(customDelimeter));
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
