package com.xiewende.creativehomesuppliescity.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @create 2021-04-10  16:13
 */
public class UploadFileUtil {

    public static String upload(MultipartFile file,FastFileStorageClient fastFileStorageClient,ConstantProperties properties) {
        int index = file.getOriginalFilename().lastIndexOf(".");//返回指定字符在此字符串中最后一次出现处的索引
        String suffix =file.getOriginalFilename().substring(index+1); //文件后缀名
        //FileInputStream fileInputStream = new FileInputStream(file.getInputStream());
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
            /* 获取上传到FastDFS中图片的路径 */
            String imgUrl = storePath.getFullPath();

            String saveUrl = "http://"+properties.getDefaltIp()+"/"+imgUrl;
            System.out.println("FastDFS中图片的路径: "+saveUrl);
            return saveUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getDefalt_imge_path();
    }
}
