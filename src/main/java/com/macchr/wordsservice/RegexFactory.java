package com.macchr.wordsservice;

import java.util.function.Function;
import java.util.stream.Collectors;

public class RegexFactory {
    public String fromLetters(String chars) {
        var characterToCount = chars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return "^" + characterToCount.entrySet().stream()
                .map(entry -> negativeLookahead(entry.getKey(), entry.getValue().intValue() + 1))
                .collect(Collectors.joining("")) + "[" +
                characterToCount.keySet().stream().map(c -> c + "")
                        .collect(Collectors.joining("")) +
                "]" + "{" + chars.length() + "}" + "$";
    }

    private String negativeLookahead(Character character, int count) {
        return "(?!" + (".*" + character).repeat(count) + ")";
    }
}