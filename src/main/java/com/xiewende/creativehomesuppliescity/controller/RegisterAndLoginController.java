package com.xiewende.creativehomesuppliescity.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.UserService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.UserVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @create 2021-04-10  12:03
 */
@Api(tags = "registerAndlogin",description = "用户注册登录接口")
@RestController
@CrossOrigin
public class RegisterAndLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConstantProperties properties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @ApiOperation("hello")
    @PostMapping("/hello")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldName", value = "场地名称", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "you", value = "场地地点", required = true,
                    dataType = "int", paramType = "query")
    })
    public String hello(String fieldName,Integer you){
        return fieldName+you;
    }

    //用户注册
    @ApiOperation("用户注册")
    @PostMapping("/register")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "affirmPassword", value = "确认密码",
                    dataType = "string", paramType = "query")
    })
    public Result registerUser(UserVo user ,String affirmPassword, MultipartFile file, HttpServletRequest request){
        //1、判断是否为空
        if(user.getUserName() == null || "".equals(user.getUserName())
        || user.getPassword() == null || "".equals(user.getPassword())
        || user.getAddress() == null || "".equals(user.getAddress())
        || user.getEmail() == null || "".equals(user.getEmail())
        || user.getSex() == null || "".equals(user.getSex())
        || user.getIphone() == null || "".equals(user.getIphone())
        || affirmPassword == null || "".equals(affirmPassword))
        {
            return Result.build(400,"数据不可以为空");
        }

        //2次密码是否相同的检验
        if(!user.getPassword().equals(affirmPassword))  return Result.build(400,"两次填入的密码不一致！");

        //对电话号码的校验
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(String.valueOf(user.getIphone()));
        if(!m.matches())return Result.build(400,"电话号码格式错误！！！");

        //对邮箱进行检验
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(user.getEmail());
        if(!m1.matches()) return Result.build(400,"邮箱格式错误！！！");

        //复制给 dao层用的bean
        User insertUser = new User();
        BeanUtils.copyProperties(user,insertUser); // 注意导入的包不一样，顺序不一样
        //性别
        if(user.getSex().equals("男"))insertUser.setGender(0);
        else insertUser.setGender(1);
        //加密密码
        insertUser.setPassword(MD5Utils.getMD5passwprd(user.getPassword()));

        //头像部分
        if(file == null || file.getSize() == 0){  //默认头像
            insertUser.setImage(properties.getDefalt_imge_path());
        }else{
            //更好的做法就是抽出一个方法来，但是有点问题  只能这里很多代码了
            String storePath = UploadFileUtil.upload(file, fastFileStorageClient,properties);
            insertUser.setImage(storePath);

//            int index = file.getOriginalFilename().lastIndexOf(".");//返回指定字符在此字符串中最后一次出现处的索引
//            String suffix =file.getOriginalFilename().substring(index+1); //文件后缀名
//            try {
//                StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
//                //获取上传到FastDFS中图片的路径
//                String imgUrl = storePath.getFullPath();
//                String saveUrl = "http://"+properties.getDefaltIp()+"/"+imgUrl;
//                System.out.println("FastDFS中图片的路径: "+saveUrl);
//                insertUser.setImage(saveUrl);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        
        //添加
        Integer integer = userService.insertUser(insertUser);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该用户已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

}
