package org.ogorodnik.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileAnalyzer {
    String path;
    String word;

    public FileAnalyzer(String[] arguments) {
        String[] validatedArguments = CommandLineArgumentsValidator.validateArguments(arguments);
        this.path = validatedArguments[0];
        this.word = validatedArguments[1];
    }

    void checkNUmberOfWordInput(String path, String word) {
        File file = new File(path);
        BufferedReader bufferedReader;
        int count = 0;
        List<String> list = new ArrayList<>();

        try{
            bufferedReader = new BufferedReader(new FileReader(file));

            while(bufferedReader.ready()){
                list.add(bufferedReader.readLine());
            }
            for(String paragraph: list){
                String[] sentences = paragraph.split("\\.|\\?|\\!");
                for (String sentence: sentences){
                    if(sentence.contains(word)){
                        System.out.println(sentence);
                        count++;
                    }
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static class CommandLineArgumentsValidator {
        static String[] validateArguments(String[] arguments) {
            final int MIN_NUMBER_OF_ARGUMENTS = 2;
            if (arguments.length < MIN_NUMBER_OF_ARGUMENTS) {
                throw new NullPointerException ("You have less than " + MIN_NUMBER_OF_ARGUMENTS
                        + " command line arguments. Please make sure 1st argument is file path "
                        + "and name. 2nd argument is word you need to look for in the file");
            } else if(arguments[0] == null){
                throw new NullPointerException("Path is empty. Please check your first input param");
            } else if(arguments[1] == null){
                throw new NullPointerException("Your word does not exist. Check second inputy param");
            }else{
                File file = new File(arguments[0]);
                if(!file.exists()){
                    try {
                        throw new FileNotFoundException("File does not exist or your path is not in a correct" +
                                " format. Please check first command line param: " + arguments[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            return arguments;
        }
    }
}
