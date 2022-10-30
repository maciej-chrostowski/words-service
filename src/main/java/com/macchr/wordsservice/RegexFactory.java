package com.macchr.wordsservice;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegexFactory {
    public String fromLetters(String chars) {
        return create(chars, s -> String.valueOf(s.length()));
    }

    public String containing(String chars) {
        return create(chars, RegexFactory::getLengthForNOrLess);
    }

    private String create(String chars, Function<String, String> lengthFunction) {
        var characterToCount = chars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return "^" + characterToCount.entrySet().stream()
                .map(this::mapCharacter)
                .collect(Collectors.joining("")) + "[" +
                characterToCount.keySet().stream().map(c -> c + "")
                        .collect(Collectors.joining("")) +
                "]" + "{" + lengthFunction.apply(chars) + "}" + "$";
    }

    private static String getLengthForNOrLess(String chars) {
        return "1," + chars.length();
    }

    private String mapCharacter(Map.Entry<Character, Long> entry) {
        if (entry.getKey() == 'X') {
            return "";
        }
        return negativeLookahead(entry.getKey(), entry.getValue().intValue() + 1);
    }

    private String negativeLookahead(Character character, int count) {
        return "(?!" + (".*" + character).repeat(count) + ")";
    }
}