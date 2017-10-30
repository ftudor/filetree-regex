package interview.exercise.strategy;

import java.util.Map;
import java.util.TreeMap;

/**
 * Simple implementation of ReportingStrategy
 */
public class SimpleReportingStrategy implements ReportingStrategy {
    //store report for replacements; sort keys in alphabetical order
    private Map<String, Integer> report = new TreeMap<>();

    private int filesProcessed = 0;

    @Override
    public void addWord(String word) {
        //increment value if present
        if(report.containsKey(word)){
            report.put(word, report.get(word) + 1);
        }
        else {
            report.put(word, 1);
        }
    }

    @Override
    public void printReport(String replacement) {
        //processed files no
        System.out.format("Processed %d files\n", filesProcessed);

        //replacements
        System.out.format("Replaced to %s:\n", replacement);
        for(String word: report.keySet()){
            System.out.format("* %s : %d occurrences\n", word, report.get(word));
        }
    }


    @Override
    public void addProcessedFile() {
        filesProcessed++;
    }

    @Override
    public int getProcessedFilesNo() {
        return filesProcessed;
    }
}
