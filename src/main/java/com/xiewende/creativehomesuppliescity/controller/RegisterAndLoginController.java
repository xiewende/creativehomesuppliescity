package com.xiewende.creativehomesuppliescity.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.LoginService;
import com.xiewende.creativehomesuppliescity.service.UserService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.UserVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LoginService loginService;


    //用户注册
    @ApiOperation("用户注册")
    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "affirmPassword", value = "确认密码",
                    dataType = "string", paramType = "query")
    })
    public Result registerUser(UserVo user ,String affirmPassword){
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

        //默认头像部分
        if(user.getImage() == null || "".equals(user.getImage())) user.setImage(properties.getDefalt_imge_path());

        //复制给 dao层用的bean
        User insertUser = new User();
        BeanUtils.copyProperties(user,insertUser); // 注意导入的包不一样，顺序不一样
        //性别
        if(user.getSex().equals("男"))insertUser.setGender(0);
        else insertUser.setGender(1);
        //加密密码
        insertUser.setPassword(MD5Utils.getMD5passwprd(user.getPassword()));

        //逻辑删除
        insertUser.setIsdelete(0);
        
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

    @PostMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag", value = "哪位登录（用户：0，设计师：1，管理员：2）", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true,
                    dataType = "string", paramType = "query")
    })
    public Result login(Integer tag,String name, String password){
        if(tag == null ||
                name == null || "".equals(name)
                || password == null || "".equals(password)) {
            return Result.build(400,"数据不可以为空");
        }
        //用户
        if(tag == 0){
            User user = loginService.userLogin(name, password);
            if(user == null) return Result.build(400, "用户账户或者密码错误！！！");
            //存到redis
            redisTemplate.opsForValue().set("user", "user", 60 * 60 * 24, TimeUnit.SECONDS);
            return Result.build(200, "登陆成功！！！", user);
        }
        //设计师
        else if(tag == 1){
            Designer designer = loginService.designerLogin(name, password);
            if(designer == null) return Result.build(400, "用户账户或者密码错误！！！");
            redisTemplate.opsForValue().set("user", "user", 30 * 60, TimeUnit.SECONDS);
            return Result.build(200, "登陆成功！！！", designer);
        }
        //管理员
        else{
            Integer integer = loginService.adminLogin(name, password);
            if(integer == 0) return Result.build(400, "用户账户或者密码错误！！！");
            redisTemplate.opsForValue().set("user", "user", 30 * 60, TimeUnit.SECONDS);
            return Result.build(200, "登陆成功！！！", "admin");
        }
    }

    @PostMapping("/loginout")
    @ApiOperation("退出登录")
    public Result loginout(){
        Boolean loginUser = redisTemplate.delete("user");
        if (loginUser) {
            return Result.build(200, "退出成功！！！");
        } else {
            return Result.build(500, "系统出现故障");
        }
    }

}
