package org.ogorodnik.IO;

import java.io.*;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.List;


class FileAnalyzer {
    String[] inputArguments;

    public FileAnalyzer(String[] args) {
        this.inputArguments = CommandLineArgumentsValidator.validateArguments(args);
    }

    ArrayList<String> findSentensesWithWordInput(String path, String word) {
        File file = new File(path);
        if(!file.canRead()){
            try {
                throw new AccessException("File can't be read. Please check permissions");
            } catch(AccessException e){
                e.printStackTrace();
            }
        }
        List<String> list = new ArrayList<>();
        ArrayList<String> processedList = new ArrayList<>();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while(bufferedReader.ready()){
                list.add(bufferedReader.readLine());
            }
            for(String paragraph: list){
                String[] sentences = paragraph.split("\\.|\\?|\\!");
                for (String sentence: sentences){
                    if(sentence.toLowerCase().contains(word.toLowerCase())){
                        processedList.add(sentence);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processedList;
    }

    private void printSentencesWithReqestedWordAndWordOccurence(ArrayList<String> arrayList, String word){
        if(arrayList.size() == 0){
            System.out.println("\"" + word + "\" is not present in a file you provided");
        } else {
            System.out.println("\"" + word + "\" occurs " + arrayList.size() + " times");
            System.out.println("please see sentences where the word occurs:");
            for(String element:arrayList){
                System.out.println(element);
            }
        }
    }

    void calculateAndPrintWordOccurence (String[] arguments){
        String path = inputArguments[0];
        String word = inputArguments[1];
        printSentencesWithReqestedWordAndWordOccurence(
                findSentensesWithWordInput(path, word), word);
    }


    private static class CommandLineArgumentsValidator {
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
