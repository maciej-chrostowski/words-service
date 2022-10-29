package com.macchr.wordsservice;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
public class IndexController {
    private final WordsService wordsService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("searchedRegex", new SearchedRegex());
        model.addAttribute("testAttr", "value from index");
        return "index";
    }

    @PostMapping(params = "searchWords")
    public String searchWords(Model model, @ModelAttribute("searchedRegex") SearchedRegex searchedRegex) {
        List<Word> matchingWords;
        if (StringUtils.isNotEmpty(searchedRegex.getRegex())) {
            matchingWords = wordsService.getMatchingWordsByRegex(searchedRegex.getRegex())
                    .stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .map(Word::new).toList();
            model.addAttribute("explanation", "Searched by " + searchedRegex.getRegex());
        } else if (StringUtils.isNotEmpty(searchedRegex.getLetters())) {
            matchingWords = wordsService.getMatchingWordsByLetters(searchedRegex.getLetters())
                    .stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .map(Word::new).toList();
            model.addAttribute("explanation", "Searched by " + searchedRegex.getLetters());
        } else {
            return "index";
        }
        model.addAttribute("explanation", "Searched by "
                + Stream.of(searchedRegex.getRegex(), searchedRegex.getLetters())
                .filter(StringUtils::isNotEmpty).findFirst().get());
        model.addAttribute("matchingWords", matchingWords);

        return "index";
    }
}
