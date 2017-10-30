Regex Text Replacement in Files
This package contains the skeleton structure for this exercise. Use only built-in libraries included in
Oracle's JDK 6/7/8 to implement the process() method in RegexTextReplacementInFiles.java to perform
string replacement for each of the qualified files under a starting directory. The program takes 3 required
parameters and one optional parameter:
1. Starting directory path. This will always be a directory and not a file.
2. String pattern to be replaced in Java supported regular expression (see note #1).
3. String to be replaced with.
4. (Optional) File naming pattern in UNIX wild-card filename syntax (see note #2).
The program should create a directory that matches the starting directory’s name appended with a
“_processed” suffix in the same parent path as the starting directory. Any file that is processed (according
to the file naming pattern) under the starting directory, and its sub-directories, should be copied with the
same exact name over to its relative location under the “_processed” directory, but with its content changed
based on the matching group and replacement string. If the file naming pattern parameter is not given, then
process every file under the starting directory. Before the program terminates, display a summary report of
the strings and occurrences count that were replaced. For clarification, see the output example below.
Please assume the file size can be arbitrarily large. The file repository can also be arbitrarily large, meaning
it may contain large number of sub-directories and files. It might be crucial to process them as efficient as
possible. You can make any additional assumptions you need for your implementation. Make notes of these
assumptions with Javadoc in your code for our understanding.
To demonstrate the level of your OOP understanding and design, please structure your implementation to
allow easy customization in the future for: a) directory walking strategies (e.g. depth first or breadth first),
b) tasks to be performed against each file, and c) stats to be collected and reports to be generated.
Notes:
1. The string pattern is a JDK supported regular expression with exactly 1 match group. Find all
texts which match the pattern, and replace ONLY the match group 1 with the replacement. If the
match group in the given pattern doesn't equal to one, the program can exit immediately by
throwing and/or printing error.
2. The file naming pattern will be the generally accepted file naming pattern in UNIX where '*'
matches any number of characters and '?' matches one character.
Execution and Output
Note: This execution example is for a UNIX OS. Please adjust for your OS if necessary.
$ ./scripts/regexTextReplacementInFiles sample_dir '\w*(lan)\w+' '<-­-replaced-­->' *.txt
Processed 7 files
Replaced to '<-­-replaced-­->':
* Planitia : 2 occurrence
* lander : 2 occurrence
* landing : 10 occurrences
* lands : 2 occurrence
* plans : 2 occurrence
* plants : 2 occurrence
$ ls -­-R sample_dir*
sample_dir:
abcde.txt dummy.doc folder1
sample_dir/folder1:
folder1-­-1 folder1-­-sample1.txt folder1-­-sample2.txt
sample_dir/folder1/folder1-­-1:
folder1-­-1-­-sample1.txt folder1-­-1-­-sample2.txt folder1-­-1-­-sample3.txt
folder1-­-1-­-sample4.txt
sample_dir_processed:
abcde.txt folder1
sample_dir_processed/folder1:
folder1-­-1 folder1-­-sample1.txt folder1-­-sample2.txt
sample_dir_processed/folder1/folder1-­-1:
folder1-­-1-­-sample1.txt folder1-­-1-­-sample2.txt folder1-­-1-­-sample3.txt
folder1-­-1-­-sample4.txt
$ cat sample_dir_processed/folder1/folder1-­-sample2.txt
…
Watney p<-­--­--replaced-­--­-->s to drive 3,200 kilometres (2,000 mi) to Schiaparelli crater when the
Ares 4 mission <-­--­--replaced-­--­-->ds there in four years. He begins modifying one of Ares 3's
rovers for the journey, adding solar cells and an additional battery. He makes a long test drive
to recover the unmanned Pathfinder <-­--­--replaced-­--­-->der and Sojourner rover and brings them back
…
Acceptance Criteria
• Use only core JDK library.
• The program should not exit due to errors that were not handled.
• The processing report is printed in the format of the example output.
• All strings in the files that matches the string pattern are replaced with the string replacement
string input.
• Only process the files that match the file-naming pattern. If the file-naming pattern was not given,
apply the replacement to all files. In all cases, files can be in different sub-directories within the
given starting directory.
What We Look For
• Code quality
o Following the OOP principals
o Best programming practices
o Readability with/without comments
• Correctness
• Completeness
What we are not looking for in this exercise (i.e. don’t spend too much time):
• Fancy output of messages
• Print out Usage/Help for command line
Submission
• When finished, please zip up your code in the directory structure of the project and send it in as a
zip file. Please make sure the zip file includes all your files
• It is ok not to finish all the functionalities, but please keep it compile-able and runnable for your
submission