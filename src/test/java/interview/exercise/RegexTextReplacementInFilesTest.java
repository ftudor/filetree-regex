package interview.exercise;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RegexTextReplacementInFilesTest {
    private static final String ROOT = "sample_dir";
    private static final String INVALID_ROOT = "aaa";
    private static final String TO_REPLACE = "\\w*(lan)\\w+";
    private static final String TO_REPLACE_INVALID = "\\w*lan\\w+";
    private static final String REPLACED = "<-replaced‐>";
    private static final String TXT_PATTERN = "*.txt";
    private static final int COUNT_ALL = 8;
    private static final int COUNT_TXT = 7;
    private static final int COUNT_ERR = -1;

    @Test
    public void testSanity() {
       System.out.println("JUnit test works");
    }

    //tests for process()

    @Test
    public void testProcessNoFilePattern(){
        int count = RegexTextReplacementInFiles.process(ROOT, TO_REPLACE, REPLACED, null);
        assertEquals(count, COUNT_ALL);
    }

    @Test
    public void testProcessFilePattern(){
        int count = RegexTextReplacementInFiles.process(ROOT,  TO_REPLACE, REPLACED, TXT_PATTERN);
        assertEquals(count, COUNT_TXT);
    }

    @Test
    public void testProcessInvalidRoot(){
        int count = RegexTextReplacementInFiles.process(INVALID_ROOT,  TO_REPLACE, REPLACED, TXT_PATTERN);
        assertEquals(count, COUNT_ERR);
    }

    @Test
    public void testProcessInvalidRegex(){
        int count = RegexTextReplacementInFiles.process(ROOT, TO_REPLACE_INVALID, REPLACED, null);
        assertEquals(count, COUNT_ERR);
    }

    //tests for isValidRegex()

    @Test
    public void testValidRegex(){
        boolean res = RegexTextReplacementInFiles.isValidRegex(TO_REPLACE);
        assertTrue(res);
    }

    @Test
    public void testInvalidRegex(){
        boolean res = RegexTextReplacementInFiles.isValidRegex(TO_REPLACE_INVALID);
        assertFalse(res);
    }

    //additional tests for print()

    //@Test
    public void testPrintNoFilePattern(){
        System.out.println("Print all files:");
        int count = RegexTextReplacementInFiles.print(ROOT, null);
        assertEquals(count, COUNT_ALL);
    }

    //@Test
    public void testPrintFilePattern(){
        System.out.println("Print text files:");
        int count = RegexTextReplacementInFiles.print(ROOT, TXT_PATTERN);
        assertEquals(count, COUNT_TXT);
    }

    //@Test
    public void testPrintInvalidRoot(){
        int count = RegexTextReplacementInFiles.print(INVALID_ROOT, null);
        assertEquals(count, COUNT_ERR);
    }

}
