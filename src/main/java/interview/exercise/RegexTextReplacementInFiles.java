package interview.exercise;

import interview.exercise.strategy.DFSWalkingStrategy;
import interview.exercise.strategy.ReportingStrategy;
import interview.exercise.strategy.WalkingStrategy;
import interview.exercise.visitor.PrintFilesVisitor;
import interview.exercise.visitor.RegexReplaceVisitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Main class to do processing of a file hierarchy and replace via regex
 * using the file features in Java NIO (1.7+) for best performance
 *
 * Notes for all classes involved:
 * 1) in Production a Logging library will be used (Log4J, Logback, etc),
 *    but here for simplicity using System.out or System.err
 * 2) For errors I chose to return error code instead of throwing exceptions
 * 3) No synchronization was taken into account, assumed single thread operation
 */
public class RegexTextReplacementInFiles {
    private static final String DEFAULT_FILE_PATTERN = "*.*";
    //we could use different error codes for different cases, but using one for simplicity
    private static final int ERROR_CODE = -1;
    private static final int MIN_ARGS = 3;

    /**
     * Method to do required processing on each file in a file tree
     * Uses a strategy for walking a file tree
     *
     * @param startingPath Starting directory path. This will always be a directory and not a file.
     * @param regexPattern String pattern to be replaced in Java supported regular expression
     * @param replacement String to be replaced with.
     * @param fileAcceptPattern (Optional) File naming pattern in UNIX wild-card filename syntax
     *
     * @return number of processed files or -1 if theer were errors
     */
    //
    static int process(String startingPath, String regexPattern, String replacement, String fileAcceptPattern) {

        //check if startingPath is not directory
        Path startingDir = Paths.get(startingPath);
        if(!Files.isDirectory(startingDir)){
            System.err.println("Starting directory is invalid");
            return ERROR_CODE;
        }

        //check if regex is valid
        if(!isValidRegex(regexPattern)){
            return ERROR_CODE;
        }

        if(fileAcceptPattern == null){
            fileAcceptPattern = DEFAULT_FILE_PATTERN;
        }

        RegexReplaceVisitor visitor = new RegexReplaceVisitor(regexPattern, replacement, fileAcceptPattern);

        //use a strategy to select desired tree walking method
        WalkingStrategy strategy = new DFSWalkingStrategy();
        strategy.walkFileTree(startingDir, visitor);


        //print report
        ReportingStrategy repStrategy = visitor.getReportingStrategy();
        visitor.getReportingStrategy().printReport(replacement);

        return repStrategy.getProcessedFilesNo();
    }

    /**
     * Utility method to print a file hierarchy
     *
     * @param startingPath Starting directory path. This will always be a directory and not a file.
     * @param fileAcceptPattern (Optional) File naming pattern in UNIX wild-card filename syntax
     *
     * @return number of processed files number of processed files or -1 if there were errors
     */

    static int print(String startingPath, String fileAcceptPattern) {

        Path startingDir = Paths.get(startingPath);
        if(!Files.isDirectory(startingDir)){
            System.err.println("Starting directory is invalid");
            return ERROR_CODE;
        }

        PrintFilesVisitor visitor;
        if(fileAcceptPattern == null){
            fileAcceptPattern = DEFAULT_FILE_PATTERN;
        }
        visitor = new PrintFilesVisitor(fileAcceptPattern);

        //use a strategy to select desired tree walking method
        WalkingStrategy strategy = new DFSWalkingStrategy();
        strategy.walkFileTree(startingDir, visitor);

        return visitor.getFilesProcessed();
    }

    /**
     * Check if the regex has a single match group
     *
     * @return True if it has one group, false otherwise
     */
    static boolean isValidRegex(String regex){
        Pattern p = Pattern.compile(regex);
        //create dummy matcher to count the groups
        int groups = p.matcher("").groupCount();
        if(groups != 1){
            System.err.println("Regex is invalid - does not have one capture group");
        }
        return groups == 1;
    }

    public static void main(String[] args) {
        String startingDir = null, regexPattern = null, replacement = null, fileAcceptPattern = null;
        if (args.length >= MIN_ARGS) {
            startingDir = args[0];
            regexPattern = args[1];
            replacement = args[2];
        }
        else{
            System.err.println("Expected at least 3 parameters but got " + args.length);
            return;
        }

        if (args.length > MIN_ARGS) {
            fileAcceptPattern = args[MIN_ARGS];
        }

        if (startingDir != null) {
            process(startingDir, regexPattern, replacement, fileAcceptPattern);
        } else {
            System.err.println("startingDir is null");
        }
    }
}
