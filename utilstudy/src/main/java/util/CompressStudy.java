package util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author chenzhicong
 * @time 2019/4/30 13:40
 * @description
 */
public class CompressStudy {


    /**
     * 压缩(ZIP)给定目录文件或者里面的文件
     *
     * @param inFile
     *            给定的目录或文件
     * @param extensions
     *            需要压缩的文件扩展名 ，不指定为null
     * @param recursive
     *            是否遍历给定目录
     * @param out
     *            输出流
     * @throws Exception
     */
    public static void zipFiles(String inFile, String[] extensions, boolean recursive, OutputStream out) {
        Collection<File> filesToArchive = null;
        File file = Paths.get(inFile).toFile();
        if (file.isFile()) {
            filesToArchive = Arrays.asList(file);
        } else {
            filesToArchive = FileUtils.listFiles(file, extensions, recursive);
        }
        // 此处是避免压缩文件里面出现文件目录前面有系统分割符号
        String rootPath = file.getParent();
        if (!rootPath.endsWith(File.separator)) {
            rootPath += File.separator;
        }
        try {
            zip(out, filesToArchive, rootPath, null);
        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
        }
    }
    private static void zip(OutputStream out, Collection<File> filesToArchive, String rootPath, String comment) throws FileNotFoundException, IOException, ArchiveException {
        try (
                ZipArchiveOutputStream o = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, out)) {
            // TODO 使用 new
            // java.util.zip.ZipFile("c://ExamExam.zip").getComment();
            // 获取注释,直接o.setComment设置注释，默认是UTF-8编码的。但是window上打开是乱码，或许可以设置
            // 为GBK，打开才是正常的
            if (null!=comment && comment.equals("")) {
                // o.setEncoding(System.getProperty("sun.jnu.encoding"));
                o.setEncoding("GBK");
                o.setComment(comment);

            }

            for (File f : filesToArchive) {
                // 获取每个文件相对路径,作为在ZIP中路径
                ArchiveEntry entry = o.createArchiveEntry(f, f.getCanonicalPath().substring(rootPath.length()));
                o.putArchiveEntry(entry);
                if (f.isFile()) {
                    try (InputStream i = Files.newInputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
                o.closeArchiveEntry();
            }

            o.finish();
        }
        IOUtils.closeQuietly(out);
    }

    /**
     * 解压Zip文件
     *
     * @param zipFile
     *            需要解压的zip文件位置
     * @param destDir
     *            解压的目标位置
     */
    public static void unzip(String zipFile, String destDir) {
        File f;
        try (
                ArchiveInputStream i = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, Files.newInputStream(Paths.get(zipFile)))) {
            ArchiveEntry entry = null;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    continue;
                }
                f = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
            }
        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
        }

    }







}
