package com.xiewende.creativehomesuppliescity.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @create 2021-05-09  17:11
 */
@Api(tags = "UploadFile",description = "上传图片的接口")
@RestController
@CrossOrigin
public class UploadFileController {

    @Autowired
    private ConstantProperties constantProperties;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @ApiOperation("上传图片，返回图片的路径")
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        String ansstorePath = null;
        if(file == null || file.getSize() == 0){  //默认图片
            ansstorePath = constantProperties.getDefalt_imge_path();
        }else {
            int index = file.getOriginalFilename().lastIndexOf(".");//返回指定字符在此字符串中最后一次出现处的索引
            String suffix =file.getOriginalFilename().substring(index+1); //文件后缀名
            try {
                StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
                /* 获取上传到FastDFS中图片的路径 */
                String imgUrl = storePath.getFullPath();
                String saveUrl = "http://"+constantProperties.getDefaltIp()+"/"+imgUrl;
                ansstorePath = saveUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.build(200, "上传成功！",ansstorePath);
    }
}
