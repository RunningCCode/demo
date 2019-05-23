package util;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author chenzhicong
 * @time 2019/5/23 15:29
 * @description
 */
public class FileUploadStudy {
    public void testFileUpload(HttpServletRequest request, HttpSession session){
        // 判断是普通表单，还是文件上传表单
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("不是文件上传表单！");
        }

        // 创建上传所需要的两个对象
        DiskFileItemFactory factory = new DiskFileItemFactory();  // 磁盘文件对象
        ServletFileUpload sfu = new ServletFileUpload(factory);   // 文件上传对象

        // 设置解析文件上传中的文件名的编码格式
        sfu.setHeaderEncoding("utf-8");

        // 创建 list容器用来保存 表单中的所有数据信息
        List<FileItem> items = new ArrayList<FileItem>();

        // 将表单中的所有数据信息放入 list容器中
        try {
            items = sfu.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        // 遍历 list容器，处理 每个数据项 中的信息
        for (FileItem item : items) {
            // 判断是否是普通项
            if (item.isFormField()) {
                // 处理 普通数据项 信息
                handleFormField(item);
            } else {
                // 处理 文件数据项 信息
                handleFileField(item,request);
            }
        }
    }
    /**
     * 处理 普通数据项
     * @param item
     */
    private void handleFormField(FileItem item) {
        // 获取 普通数据项中的 name值
        String fieldName = item.getFieldName();

        // 获取 普通数据项中的 value值
        String value = "";
        try {
            value = item.getString("utf-8");  // 以 utf-8的编码格式来解析 value值
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 输出到控制台
        System.out.println("fieldName:" + fieldName + "--value:" + value);
    }
    /**
     * 处理 文件数据项
     * @param item
     */
    private void handleFileField(FileItem item,HttpServletRequest req) {
        // 获取 文件数据项中的 文件名
        String fileName = item.getName();

        // 判断 此文件的文件名是否合法
        if (fileName==null || "".equals(fileName)) {
            return;
        }

        // 控制只能上传图片
        if (!item.getContentType().startsWith("image")) {
            return;
        }

        // 将文件信息 输出到控制台
        System.out.println("fileName:" + fileName);         // 文件名
        System.out.println("fileSize:" + item.getSize());   // 文件大小

        // 获取 当前项目下的 /files 目录的绝对位置
        String path = req.getServletContext().getRealPath("/files");
        File file = new File(path);   // 创建 file对象

        // 创建 /files 目录
        if (!file.exists()) {
            file.mkdir();
        }

        // 将文件保存到服务器上（UUID是通用唯一标识码，不用担心会有重复的名字出现）
        try {
            item.write(new File(file.toString(), UUID.randomUUID()+"_"+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
