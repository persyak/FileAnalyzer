package org.ogorodnik.IO;

class FIleAnalyzerRun {
    public static void main(String[] args) {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(args);
        fileAnalyzer.checkNUmberOfWordInput(fileAnalyzer.path, fileAnalyzer.word);
    }
}
