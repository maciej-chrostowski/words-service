package com.macchr.wordsservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

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
        List<Word> matchingWords = wordsService.getMatchingWords(searchedRegex.getValue())
                .stream()
                .sorted(Comparator.comparingInt(String::length))
                .map(Word::new).toList();
        model.addAttribute("matchingWords", matchingWords);
        return "index";
    }
}
