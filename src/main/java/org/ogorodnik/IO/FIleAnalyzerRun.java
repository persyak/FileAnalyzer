package org.ogorodnik.IO;

class FIleAnalyzerRun {
    public static void main(String[] args) {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(args);
        fileAnalyzer.calculateAndPrintWordOccurence(fileAnalyzer.inputArguments);
    }
}
