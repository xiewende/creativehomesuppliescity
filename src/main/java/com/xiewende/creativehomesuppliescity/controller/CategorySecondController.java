package com.xiewende.creativehomesuppliescity.controller;

import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import com.xiewende.creativehomesuppliescity.service.CategoryFirstService;
import com.xiewende.creativehomesuppliescity.service.CategorySecondService;
import com.xiewende.creativehomesuppliescity.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "CategorySecond(4)",description = "商品二级分类管理接口")
@RestController
@CrossOrigin
@RequestMapping("/ategocrySecond")
public class CategorySecondController {

    @Autowired
    private CategorySecondService categorySecondService;
    //添加  TODO
    @ApiOperation("添加二级分类")
    @PostMapping("/insertCategorySecond")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryFirstName", value = "一级分类的名称,查询一级进行选择",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "categorySecondName", value = "二级分类的名称",
                    dataType = "string", paramType = "query")
    })
    public Result insertCategorySecond(String categoryFirstName,String categorySecondName){
        //1、判断是否为空
        if(categoryFirstName==null || "".equals(categoryFirstName) || categorySecondName==null || "".equals(categorySecondName))
        {
            return Result.build(400,"数据不可以为空");
        }

        CategorySecond categorySecond = new CategorySecond();
        //逻辑删除
        categorySecond.setIsdelete(0);
        categorySecond.setCategoryName(categorySecondName);

        //添加
        Integer integer = categorySecondService.insertCategorySecond(categorySecond,categoryFirstName);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该二级分类已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改二级分类信息")
    @PostMapping("/updateCategorySecond")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categorySecondName", value = "二级分类的名称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "categoryFirstName", value = "一级分类的名称，查询一级进行选择",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "二级分类的id",
                    dataType = "int", paramType = "query")
    })
    public Result updateCategorySecond(String categorySecondName,Integer id,String categoryFirstName){
        //1、判断是否为空
        if(categoryFirstName==null || "".equals(categoryFirstName) || categorySecondName==null || "".equals(categorySecondName) || id == null)
        {
            return Result.build(400,"数据不可以为空");
        }
        //id和删除标
        CategorySecond categorySecond = new CategorySecond();
        categorySecond.setIsdelete(0);
        categorySecond.setCategoryName(categorySecondName);
        categorySecond.setId(id);
        //修改
        Integer integer = categorySecondService.updateCategorySecond(categorySecond,categoryFirstName);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该二级分类已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deleteCategorySecond")
    @ApiOperation("删除某一个二级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的二级分类id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteCategorySecond(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Integer integer = categorySecondService.deleteCategorySecond(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    @PostMapping("/listAllCategorySecond")
    @ApiOperation("查询全部二级分类")
    public Result listAllCategorySecond(){

        //执行查询
        List<CategorySecond> categorySeconds = categorySecondService.listAllCategorySecond();
        if (categorySeconds.size() > 0) {
            return Result.build(200, "有数据", categorySeconds);
        }else if(categorySeconds.size() == 0){
            return Result.build(400,"没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }

    //根据id查询二个
    //根据id查询某二位用户
    @PostMapping("/findCategorySecondById")
    @ApiOperation("根据id查询某二级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询二级分类id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result findCategorySecondById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        CategorySecond categorySecond = categorySecondService.findCategorySecondById(id);
        if (categorySecond != null) {
            return Result.build(200,"查到了",categorySecond);
        }else{
            return Result.build(400, "没有此二级分类");
        }
    }


}
