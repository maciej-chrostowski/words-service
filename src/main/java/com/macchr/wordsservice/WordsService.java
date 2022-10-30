package com.macchr.wordsservice;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordsService {
    private final WordsRegexMatcher wordsRegexMatcher;
    private final RegexFactory regexFactory = new RegexFactory();

    public List<String> getMatchingWordsByRegex(String regex) {
        return wordsRegexMatcher.getMatchingWords(regex)
                .stream().distinct().toList();
    }

    public List<String> getMatchingWordsByLetters(String letters) {
        return getMatchingWordsByRegex(regexFactory.fromLetters(letters));
    }

    public List<String> getMatchingWordsByContaining(String containing) {
        return getMatchingWordsByRegex(regexFactory.containing(containing));
    }
}
