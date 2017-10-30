package interview.exercise.strategy;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Strategy wo walk a file tree using Depth First Search
 * We can use Java Library (1.7+) Files.walkFileTree()
 * Uses NIO and also should have the best performance compared to a manually implemented DFS strategy
 */
public class DFSWalkingStrategy implements WalkingStrategy{
    @Override
    public void walkFileTree(Path start, FileVisitor<? super Path> visitor) {
        try {
            Files.walkFileTree(start, visitor);
        }
        catch (IOException e){
            System.out.println("Exception when walking hierarchy: " + e.getMessage());
        }
    }
}
