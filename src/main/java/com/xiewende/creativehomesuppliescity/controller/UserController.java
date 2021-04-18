package com.xiewende.creativehomesuppliescity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.UserService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @create 2021-04-10  17:53
 */
@Api(tags = "User(1)",description = "用户信息管理接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConstantProperties properties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    //修改密码
    @PostMapping("/updatePassword")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被修改密码的用户id", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true,
                    dataType = "string", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true,
                    dataType = "string", paramType = "query",defaultValue = "1")
    })
    public Result updatePassword(Integer id, String oldPassword, String newPassword){

        if(id == null || oldPassword == null || "".equals(oldPassword)
        || newPassword == null || "".equals(newPassword)){
            return Result.build(400,"数据不可以为空");
        }

        Integer integer = userService.updatePassword(id, oldPassword, newPassword);

        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "原始密码填写错误");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }


    //删除用户
    @PostMapping("/deleteUser")
    @ApiOperation("删除某一个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的用户id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteUser(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = userService.deleteUser(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listUserOrName")
    @ApiOperation("按照名字模糊查询用户，若没有输入则查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "模糊查询的用户名，没有填写则查询全部",
                    dataType = "string", paramType = "query")
    })
    public Result listUserOrName(String userName){
        //特例判断
        if("".equals(userName)) userName = null;

        //执行查询
        List<User> users = userService.listUserOrName(userName);

        if (users.size() > 0) {
            return Result.build(200, "有数据", users);
        }else if(users.size() == 0){
            return Result.build(400,"=没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }


    @ApiOperation("修改用户信息")
    @PostMapping("/updateUserOther")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id",
                    dataType = "int", paramType = "query")
    })
    public Result updateUserOther(UserVo user , Integer id , MultipartFile file, HttpServletRequest request){
        //1、判断是否为空
        if(user.getUserName() == null || "".equals(user.getUserName())
                || user.getPassword() == null || "".equals(user.getPassword())
                || user.getAddress() == null || "".equals(user.getAddress())
                || user.getEmail() == null || "".equals(user.getEmail())
                || user.getSex() == null || "".equals(user.getSex())
                || user.getIphone() == null || "".equals(user.getIphone()))
        {
            return Result.build(400,"数据不可以为空");
        }

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
        User updateUser = userService.selectUserById(id);
        BeanUtils.copyProperties(user,updateUser); // 注意导入的包不一样，顺序不一样
        //性别
        if(user.getSex().equals("男"))updateUser.setGender(0);
        else updateUser.setGender(1);


        //头像部分
        if(file != null && file.getSize() != 0){  //默认头像
            //更好的做法就是抽出一个方法来，但是有点问题  只能这里很多代码了
            String storePath = UploadFileUtil.upload(file, fastFileStorageClient,properties);
            updateUser.setImage(storePath);
        }

        //修改
        Integer integer = userService.updateOther(updateUser);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该用户已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //根据id查询某一位用户
    @PostMapping("/selectUserById")
    @ApiOperation("根据id查询某一位用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询用户id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectUserById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        User user = userService.selectUserById(id);
        if (user != null) {
            return Result.build(200,"查到了",user);
        }else{
            return Result.build(400, "没有此用户");
        }
    }

}
