package interview.exercise.strategy;

import java.nio.file.FileVisitor;
import java.nio.file.Path;

/**
 * Strategy wo walk a file tree using Breadth First Search
 * Can be swapped for a DFSWalkingStrategy with a simple change in the caller
 * TODO implement if needed
 */
public class BFSWalkingStrategy implements WalkingStrategy{
    @Override
    public void walkFileTree(Path start, FileVisitor<? super Path> visitor) {
    }
}
