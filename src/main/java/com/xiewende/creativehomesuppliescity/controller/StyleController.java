package com.xiewende.creativehomesuppliescity.controller;

import com.xiewende.creativehomesuppliescity.pojo.Style;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.StyleService;
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
 * @create 2021-04-10  21:54
 */

@Api(tags = "Style",description = "风格管理接口")
@RestController
@CrossOrigin
@RequestMapping("/style")
public class StyleController {

    @Autowired
    private StyleService styleService;

    //添加
    @ApiOperation("添加风格")
    @PostMapping("/insertStyle")
    public Result insertStyle(Style style){
        //1、判断是否为空
        if(style.getDescription()==null || "".equals(style.getDescription())
        || style.getTypeName() == null || "".equals(style.getTypeName()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //逻辑删除
        style.setIsdelete(0);

        //添加
        Integer integer = styleService.insertStyle(style);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该风格已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改风格信息")
    @PostMapping("/updateStyle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "风格id",
                    dataType = "int", paramType = "query")
    })
    public Result updateStyle(Style style , Integer id){
        //1、判断是否为空
        if(style.getDescription()==null || "".equals(style.getDescription())
                || style.getTypeName() == null || "".equals(style.getTypeName()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //id和删除标记
        style.setId(id);
        style.setIsdelete(0);

        //修改
        Integer integer = styleService.updateStyle(style);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该风格已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deleteStyle")
    @ApiOperation("删除某一个风格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的风格id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteStyle(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = styleService.deleteStyle(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listAllStyle")
    @ApiOperation("查询全部风格")
    public Result listUserOrName(){

        //执行查询
        List<Style> styles = styleService.listAllStyle();

        if (styles.size() > 0) {
            return Result.build(200, "有数据", styles);
        }else if(styles.size() == 0){
            return Result.build(400,"=没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }

    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/selectStyleById")
    @ApiOperation("根据id查询某风格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询风格id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectStyleById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Style style = styleService.selectStyleById(id);
        if (style != null) {
            return Result.build(200,"查到了",style);
        }else{
            return Result.build(400, "没有此风格");
        }
    }


}
