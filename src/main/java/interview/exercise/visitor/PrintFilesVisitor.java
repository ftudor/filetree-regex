package interview.exercise.visitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Visitor that prints file name information, extends SimpleFileVisitor
 * Using Visitor pattern allows us to easily change the tasks to perform against each file
 */
public class PrintFilesVisitor extends SimpleFileVisitor<Path> {
    private int filesProcessed = 0;
    private PathMatcher matcher;

    /**
     * Constructor
     * @param fileAcceptPattern File naming pattern in UNIX wild-card filename syntax
     */
    public PrintFilesVisitor(String fileAcceptPattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileAcceptPattern);
    }

    public int getFilesProcessed() {
        return filesProcessed;
    }

    // Print information about each type of file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if(!matcher.matches(file)){
            return CONTINUE;
        }

        if (attr.isSymbolicLink()) {
            System.out.format("Symbolic link: %s ", file);
        } else if (attr.isRegularFile()) {
            System.out.format("Regular file: %s ", file);
        } else {
            System.out.format("Other: %s ", file);
        }
        System.out.println("(" + attr.size() + " bytes)");

        //increment processed count
        filesProcessed++;

        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.format("Directory: %s%n", dir);
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
}