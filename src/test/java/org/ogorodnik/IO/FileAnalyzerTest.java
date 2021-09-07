package org.ogorodnik.IO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerTest {

    @Test
    public void testReadFileAndWordFromCommandLine() {
        String[] arguments = new String[2];
        arguments[0] = "d:\\Learning\\JAVA\\AT\\FileAnalyzer\\Inputs\\test.txt";
        arguments[1] = "word";
        FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        assertEquals("d:\\Learning\\JAVA\\AT\\FileAnalyzer\\Inputs\\test.txt",
                fileAnalyzer.inputArguments[0]);
        assertEquals("word", fileAnalyzer.inputArguments[1]);
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
            FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        });
        arguments[0] = null;
        arguments[1] = "word";
        assertThrows(NullPointerException.class, () -> {
            FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        });
    }

    @Test
    public void testFileWithFourSentencesWithRequiredWord(){
        String[] arguments = new String[]{"d:\\Learning\\JAVA\\AT\\FileAnalyzer\\Inputs\\test.txt", "test"};
        FileAnalyzer fileAnalyzer = new FileAnalyzer(arguments);
        String path = fileAnalyzer.inputArguments[0];
        String word = fileAnalyzer.inputArguments[1];
        assertEquals(4, fileAnalyzer.findSentensesWithWordInput(path, word).size());
    }

}
