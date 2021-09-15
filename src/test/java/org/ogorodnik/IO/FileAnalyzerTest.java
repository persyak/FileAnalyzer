package org.ogorodnik.IO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerTest {

    @BeforeEach
    public void before() throws IOException {
        new File("Inputs").mkdir();
        new File("Inputs/test.txt").createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Inputs/test.txt"));
        bufferedWriter.write("This is a test file. This file is made to test fileAnalyzer! Do you think we can test this file?");
        bufferedWriter.newLine();
        bufferedWriter.write("It's another paragraph where we have a Test word, so it should be 4th sentence where it occurs.");
        bufferedWriter.newLine();
        bufferedWriter.write("А сейчас добавим предложение на русском языке со словом test.");
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    @Test
    public void testReadFileAndWordFromCommandLine() {
        String[] arguments = new String[2];
        arguments[0] = ".//Inputs//test.txt";
        arguments[1] = "test";
        FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        assertEquals(".//Inputs//test.txt",
                fileAnalyzer.inputArguments[0]);
        assertEquals("test", fileAnalyzer.inputArguments[1]);
    }

    @Test
    public void testArgumentsLessThanTwo() {
        String[] arguments = new String[]{"d:\\Learning\\JAVA\\AT\\FileAnalyzer\\Inputs\\test.txt"};
        assertThrows(NullPointerException.class, () -> {
            FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        });
    }

    @Test
    public void testAnyOfArgumentsIsNull(){
        String[] arguments = new String[]{"d:\\Learning\\JAVA\\AT\\FileAnalyzer\\Inputs\\test.txt", null};
        assertThrows(NullPointerException.class, () -> {
            new FileAnalyzer(arguments);
        });
        arguments[0] = null;
        arguments[1] = "word";
        assertThrows(NullPointerException.class, () -> new FileAnalyzer(arguments));
    }

    @Test
    public void testFileWithRequiredWord(){
        String[] arguments = new String[]{".//Inputs//test.txt", "test"};
        FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        String path = fileAnalyzer.inputArguments[0];
        String word = fileAnalyzer.inputArguments[1];
        assertEquals(5, fileAnalyzer.findSentensesWithWordInput(path, word).size());
        assertEquals(1, fileAnalyzer.findSentensesWithWordInput(path, "сейчас").size());
    }

    @AfterEach
    public void after(){
        new File("Inputs/test.txt").delete();
        new File("Inputs").delete();
    }

}
