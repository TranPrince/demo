package com.prince.util.algorithmImpl;

import com.prince.Constants;
import junit.framework.TestCase;
import com.prince.util.TestUtil;

import java.io.File;
import java.io.FilenameFilter;

public class FileEncodingUtilTest extends TestCase {

    public void testConvert() {
        String file = TestUtil.path + "/text/GBKTOUTF8.txt";
        FileEncodingUtil.convert(file, Constants.GBK, Constants.UTF_8);
    }

    public void testConvert1() {
        String file = TestUtil.path + "/text/GBKTOUTF8.txt";
        FileEncodingUtil.convert(file, Constants.UTF_8, Constants.GBK, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("txt");
            }
        });
    }

    public void testConvert2() {
        String file = TestUtil.path + "/text/GBKTOUTF8.txt";
        FileEncodingUtil.convert(new File(file), Constants.GBK, Constants.UTF_8);
    }
}