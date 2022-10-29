package com.macchr.wordsservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class WordsRegexMatcher {
    private final List<String> words = new LinkedList<>();

    public WordsRegexMatcher() throws IOException {
        long startTime = System.currentTimeMillis();
//        Pattern pattern = Pattern.compile("^[aąbcćdeęfghijklłmnńoóprsśtuwyzźż]*$");
        Resource resource = new ClassPathResource("odm.txt");
        InputStream wordsFileStream = resource.getInputStream();
        byte[] bytes = readAllBytes(wordsFileStream);
        String wordsString = new String(bytes);
        String[] splittedWords = wordsString.split("(, )|([\r\n]+)");
        for (int i = 0; i < splittedWords.length; i++) {
            String trimmed = splittedWords[i].trim();
            if (/*pattern.matcher(trimmed).find() && */trimmed.length() > 1) {
            } else {
                splittedWords[i] = null;
            }
        }
        for (String splittedWord : splittedWords) {
            if (splittedWord != null) {
                words.add(splittedWord.trim());
            }
        }

        System.out.println("Words initialized in " + (System.currentTimeMillis() - startTime)/1000 + " seconds");
    }

    public List<String> getMatchingWords(String regex) {
        Pattern searchedPattern = Pattern.compile(regex);
        Pattern polishLetternsPattern = Pattern.compile("^[aąbcćdeęfghijklłmnńoóprsśtuwyzźż]*$");
        List<String> matchingWords = new ArrayList<>();
        for (String word : words) {
            if (searchedPattern.matcher(word).find() && polishLetternsPattern.matcher(word).find()) {
                matchingWords.add(word);
            }
        }
        return matchingWords;
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 1024;
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                outputStream.write(buf, 0, readLen);

            return outputStream.toByteArray();
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }
}