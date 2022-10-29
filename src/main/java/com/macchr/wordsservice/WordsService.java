package com.macchr.wordsservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WordsService {
    private final WordsRegexMatcher wordsRegexMatcher;

    public List<String> getMatchingWords(String regex) {
        return wordsRegexMatcher.getMatchingWords(regex);
    }

}
