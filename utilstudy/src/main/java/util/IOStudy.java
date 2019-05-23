package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.comparator.PathFileComparator;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author chenzhicong
 * @time 2019/5/23 13:47
 * @description
 */
public class IOStudy {
    private String basePath = null;
    @Before
    public void setUp() {
        basePath = System.getProperty("user.dir") + "\\file\\";
    }
    @After
    public void tearDown() throws Exception {
    }
    /**
     * 拷贝文件
     * @throws IOException
     */
    @Test
    public void testCopy() throws IOException {
        System.out.println(basePath);
        File srcFile = new File(basePath + "a.txt");
        File destFile = new File(basePath + "b.txt");
        FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * 删除文件
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException{
        File delFile = new File(basePath + "b.txt");
        FileUtils.forceDelete(delFile);
        //FileUtils.forceMkdir(delFile);
    }
    /**
     * 比较文件内容
     * @throws IOException
     */
    @Test
    public void testCompareFile() throws IOException{
        File srcFile = new File(basePath + "a.txt");
        File destFile = new File(basePath + "b.txt");
        boolean result = FileUtils.contentEquals(srcFile, destFile);
        System.out.println(result);
    }

    /**
     * 移动文件
     * @throws IOException
     */
    @Test
    public void testMoveFile() throws IOException{
        File srcFile = new File(basePath + "b.txt");
        File destDir = new File(basePath + "move");
        FileUtils.moveToDirectory(srcFile, destDir, true);
    }

    /**
     * 读取文件内容
     * @throws IOException
     */
    @Test
    public void testRead() throws IOException{
        File srcFile = new File(basePath + "a.txt");
        String content = FileUtils.readFileToString(srcFile, Charset.defaultCharset());
        List<String> contents = FileUtils.readLines(srcFile,Charset.defaultCharset());
        System.out.println(content);
        System.out.println("******************");
        for (String string : contents) {
            System.out.println(string);
        }
    }

    /**
     * 写入文件内容
     * @throws IOException
     */
    @Test
    public void testWrite() throws IOException{
        File srcFile = new File(basePath + "a.txt");
        FileUtils.writeStringToFile(srcFile, "\nyes文件", Charset.defaultCharset(), true);
    }


    /**
     * 测试行迭代器
     * @throws IOException
     */
    @Test
    public void testIterator() throws IOException{
        File file = new File(basePath + "a.txt");
        LineIterator li = FileUtils.lineIterator(file);
        while(li.hasNext()){
            System.out.println(li.nextLine());
        }
        LineIterator.closeQuietly(li);
    }

    /**
     * 空内容文件过滤器
     * @throws IOException
     */
    @Test
    public void testEmptyFileFilter() throws IOException{
        File dir = new File(basePath);
        String[] files = dir.list(EmptyFileFilter.NOT_EMPTY);
        for (String file : files) {
            System.out.println(file);
        }
    }
    /**
     * 文件名称后缀过滤器
     * @throws IOException
     */
    @Test
    public void testSuffixFileFilter() throws IOException{
        File dir = new File(basePath);
        String[] files = dir.list(new SuffixFileFilter(".txt"));
        for (String file : files) {
            System.out.println(file);
        }
    }
    /**
     * 文件名称比较器
     * @throws IOException
     */
    @Test
    public void testNameFileComparator() throws IOException {
        File f1 = new File(basePath + "a.txt");
        File f2 = new File(basePath + "c.txt");
        int result = NameFileComparator.NAME_COMPARATOR.compare(f1, f2);
        System.out.println(result);
    }
    /**
     * 文件路径比较器
     * @throws IOException
     */
    @Test
    public void testPathFileComparator() throws IOException {
        File f1 = new File(basePath + "a.txt");
        File f2 = new File(basePath + "c.txt");
        int result = PathFileComparator.PATH_COMPARATOR.compare(f1, f2);
        System.out.println(result);
    }
    /**
     * 组合比较器
     * @throws IOException
     */
    @Test
    public void testCompositeFileComparator() throws IOException {
        File dir = new File(basePath);
        File [] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
        CompositeFileComparator cfc = new CompositeFileComparator(
                DirectoryFileComparator.DIRECTORY_COMPARATOR,
                NameFileComparator.NAME_COMPARATOR);
        File f1 = new File(basePath + "a.txt");
        File f2 = new File(basePath + "c.txt");
        System.out.println(cfc.compare(f1,f2));
        cfc.sort(files);
        System.out.println("*****after sort*****");
        for (File file : files) {
            System.out.println(file.getName());
        }
    }



}
