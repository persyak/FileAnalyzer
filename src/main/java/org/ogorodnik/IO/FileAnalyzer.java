package org.ogorodnik.IO;

import java.io.*;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.List;


class FileAnalyzer {
    String[] inputArguments;

    public FileAnalyzer(String[] args) {
        this.inputArguments = validateArguments(args);
    }

    private String readContent(String path) throws IOException {
        return readContent(new FileInputStream(path));
    }

    private String readContent(InputStream inputStream) throws IOException {
        byte[] byteContent = inputStream.readAllBytes();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte element : byteContent) {
            if (element != '\n') {
                stringBuilder.append((char) element);
            }
        }
        return stringBuilder.toString();
    }

    private List<String> splitToSentences(String content, String sentenceSeparator) {
        return List.of(content.split(sentenceSeparator));
    }

    private List<String> filterSentences(List<String> sentences, String word) {
        List<String> processedList = new ArrayList<>();
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(word.toLowerCase())) {
                processedList.add(sentence);
            }
        }
        return processedList;
    }

    private Result getResult(List<String> list) {
        Result result = new Result();
        if (list.size() == 0) {
            result.count = 0;
            result.sentences = null;
        } else {
            result.count = list.size();
            result.sentences = list;
        }
        return result;
    }

    Result findSentensesWithWordInput(String path, String word) throws IOException {
        String content = readContent(path);
        List<String> sentences = splitToSentences(content, "\\.|\\?|\\!");
        List<String> filteredSentences = filterSentences(sentences, word);
        return getResult(filteredSentences);
    }

    void printSentencesWithReqestedWordAndWordOccurence(Result result, String word) {
        if (result.count == 0) {
            System.out.println("\"" + word + "\" is not present in a file you provided");
        } else {
            System.out.println("\"" + word + "\" occurs " + result.count + " times");
            System.out.println("please see sentences where the word occurs:");
            for (String element : result.sentences) {
                System.out.println(element);
            }
        }
    }

    private String[] validateArguments(String[] arguments) {
        final int MIN_NUMBER_OF_ARGUMENTS = 2;
        if (arguments.length < MIN_NUMBER_OF_ARGUMENTS) {
            throw new NullPointerException("You have less than " + MIN_NUMBER_OF_ARGUMENTS
                    + " command line arguments. Please make sure 1st argument is file path "
                    + "and name. 2nd argument is word you need to look for in the file");
        } else if (arguments[0] == null) {
            throw new NullPointerException("Path is empty. Please check your first input param");
        } else if (arguments[1] == null) {
            throw new NullPointerException("Your word does not exist. Check second inputy param");
        } else {
            File file = new File(arguments[0]);
            if (!file.exists()) {
                throw new RuntimeException(
                        new FileNotFoundException("File does not exist or your path is not in a correct" +
                                " format. Please check first command line param: " + arguments[0]));
            }
        }
        return arguments;
    }

    static class Result {
        List<String> sentences;
        int count;
    }
}
