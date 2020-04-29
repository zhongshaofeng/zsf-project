package com.zsf.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @Author zhongshao
 * @Description 上传文件到阿里云的工具类
 * @Date 2020/3/15 20:23
 */
public class OssApiUtil {

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    private static String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "LTAI4FdYYbKBQUobUhcpiiy4";
    private static String accessKeySecret = "bWPePVIOonJSMIPhhxYRBgSWZyXkZx";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    private static String bucketName = "zsf-oss";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。

    private static OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    /**
     * @descripttion 上传图片到阿里云服务器 --简单上传
     * @Author zhongshao
     * @Date 2020/3/15 20:28
     */
    public static String upload(MultipartFile file, String firstKey){

        //创建上传文件源信息
        ObjectMetadata meta = new ObjectMetadata();

        //设置Http请求头的参数类型，避免打开链接直接弹下载
        meta.setContentDisposition("image/jpg");
        meta.setContentType("image/jpg");
        try{
            InputStream inputStream1=file.getInputStream();
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            byte[] b=new byte[1024];
            int t=0;
            while((t=inputStream1.read(b))!=-1){
                output.write(b,0,t);
            }
            byte[] by=output.toByteArray();
            output.close();
            inputStream1.close();
            InputStream is = new ByteArrayInputStream(by);
            ossClient.putObject(bucketName, firstKey, is,meta);
            System.out.println("图片已保存至阿里云");
            System.out.println("https://zsf-oss.oss-cn-hangzhou.aliyuncs.com/" + firstKey);
            return firstKey;
        }catch (Exception e){
            e.printStackTrace();
            return "error";

        }

    }


    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

}
