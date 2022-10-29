package com.macchr.wordsservice;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegexFactoryTest {
    private final RegexFactory regexFactory = new RegexFactory();

    @Test
    public void createCorrectRegex() {
        var characters = "abcaab";
        String regex = regexFactory.fromLetters(characters);

        System.out.println(regex);
        assertThat(regex).startsWith("^");
        assertThat(regex).endsWith("$");
        String[] splitByOpenSquareBracket = regex.split("\\[");
        assertThat(splitByOpenSquareBracket).hasSize(2);
        String toOpenSquareBracket = splitByOpenSquareBracket[0];
        String fromOpenSquareBracket = splitByOpenSquareBracket[1];
        assertThat(toOpenSquareBracket).contains("(?!.*a.*a.*a.*a)");
        assertThat(toOpenSquareBracket).contains("(?!.*b.*b.*b)");
        assertThat(toOpenSquareBracket).contains("(?!.*c.*c");
        assertThat(fromOpenSquareBracket).contains("a");
        assertThat(fromOpenSquareBracket).contains("b");
        assertThat(fromOpenSquareBracket).contains("c");
        assertThat(fromOpenSquareBracket).contains("{6}");
    }

}