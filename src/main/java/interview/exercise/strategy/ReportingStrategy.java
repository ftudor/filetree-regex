package interview.exercise.strategy;

/**
 * Strategy for reporting
 */
public interface ReportingStrategy {
    void addWord(String word);
    void printReport(String replacement);
    void addProcessedFile();
    int getProcessedFilesNo();

}
