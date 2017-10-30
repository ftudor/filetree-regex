package interview.exercise.strategy;

import java.nio.file.FileVisitor;
import java.nio.file.Path;

/**
 * Strategy to walk file trees
 */
public interface WalkingStrategy {
    void walkFileTree(Path start, FileVisitor<? super Path> visitor);
}
