package interview.exercise.visitor;

import interview.exercise.strategy.ReportingStrategy;
import interview.exercise.strategy.SimpleReportingStrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Visitor that replaces regular expression in files per requirements
 * and copies the files to a processed directory
 *
 * Using Visitor pattern allows us to easily change the tasks to perform against each file
 * Uses a strategy for reporting
 */
public class RegexReplaceVisitor extends SimpleFileVisitor<Path>{
    //suffix for processed folder
    private static final String PROCESSED = "_processed";

    //assume files with Unicode charset (there are 2 files that have special characters)
    private static final Charset CHARSET = Charset.forName("UTF-8");

    //report strategy
    private ReportingStrategy reportingStrategy = new SimpleReportingStrategy();

    private String replacement;

    //reusable patterns: create once, use multiple times

    //pattern to match files
    private PathMatcher pathMatcher;

    //pattern for replacing in files
    private Pattern pattern;

    /**
     * Constructor
     * @param regexPattern String pattern to be replaced in Java supported regular expression
     * @param replacement String to be replaced with.
     * @param fileAcceptPattern File naming pattern in UNIX wild-card filename syntax
     */
    public RegexReplaceVisitor(String regexPattern, String replacement, String fileAcceptPattern) {
        this.replacement = replacement;
        pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileAcceptPattern);
        pattern = Pattern.compile(regexPattern);
    }

    public ReportingStrategy getReportingStrategy() {
        return reportingStrategy;
    }

    // replace in file and copy file under processed
    @Override
    public FileVisitResult visitFile(Path source, BasicFileAttributes attr) {
        if(!pathMatcher.matches(source)){
            return CONTINUE;
        }

        //determine target file name under "processed"
        Path target = getProcessedFileName(source);

        // assume large text files (we don't expect to replace in binary files)
        // read one line at a time instead of readAllBytes()
        // try-with-resource: will auto-close the reader and writer
        try (BufferedReader reader = Files.newBufferedReader(source, CHARSET);
             BufferedWriter writer = Files.newBufferedWriter(target, CHARSET)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //replace all occurrences of regexPattern with replacement
                line = replaceRegex(line);
                writer.write(line, 0, line.length());
                writer.write('\n');
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        //increment processed count
        reportingStrategy.addProcessedFile();

        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path source, BasicFileAttributes attrs) throws IOException {
        //create directory under "processed"
        Path target = getProcessedFileName(source);
        //does not throw exception if it already exists; create all folders on path
        Files.createDirectories(target);
        return CONTINUE;
    }

    /**
     * If there is some error accessing the file, let the user know.
     * If you don't override this method and an error occurs, an IOException is thrown.
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException ex) {
        System.err.println(ex);
        return CONTINUE;
    }

    private Path getProcessedFileName(Path source){
        String root = source.getName(0).toString();
        //replace root folder name with processed name
        String processedPath = source.toString().replaceFirst(root, root + PROCESSED);
        return Paths.get(processedPath);
    }

    /**
     * Replaces all matches of the regex pattern in a line
     *
     * One possible solution would be to pre-process the regex (just once, in RegexTextReplacementInFiles.process())
     * to include 2 capturing groups like (\\w*)lan(\\w+) and then replace it with $1<-replaced->$2
     *
     * Another approach, used below, is to use Matcher.appendReplacement() and Matcher.appendTail() as described in
     * their javadoc, so we don't have to change the regex
     *
     * @param line String containing a line of text
     * @return String with regex replaced
     */
    private String replaceRegex(String line){
        //can't use StringBuilder because of the Matcher methods interface which accept StringBuffer
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            //matcher.group(0) matches the whole word
            String word = matcher.group(0);
            matcher.appendReplacement(sb, word.replaceFirst(matcher.group(1), replacement));
            //add report data
            reportingStrategy.addWord(word);
        }
        matcher.appendTail(sb); // append the rest
        return sb.toString();
    }
}
