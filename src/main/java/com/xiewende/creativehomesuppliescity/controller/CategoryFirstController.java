package com.xiewende.creativehomesuppliescity.controller;

import com.xiewende.creativehomesuppliescity.pojo.Brand;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;
import com.xiewende.creativehomesuppliescity.service.BrandService;
import com.xiewende.creativehomesuppliescity.service.CategoryFirstService;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.vo.BrandVo;
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

import java.util.List;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "CategoryFirst",description = "商品一级分类管理接口")
@RestController
@CrossOrigin
@RequestMapping("/categoryFirst")
public class CategoryFirstController {

    @Autowired
    private CategoryFirstService categoryFirstService;

    //添加  TODO
    @ApiOperation("添加一级分类")
    @PostMapping("/insertCategoryFirst")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "一级分类的名称",
                    dataType = "string", paramType = "query")
    })
    public Result insertBrand(String categoryName){
        //1、判断是否为空
        if(categoryName==null || "".equals(categoryName))
        {
            return Result.build(400,"数据不可以为空");
        }

        CategoryFirst categoryFirst = new CategoryFirst();
        //逻辑删除
        categoryFirst.setIsdelete(0);
        categoryFirst.setCategoryName(categoryName);

        //添加
        Integer integer = categoryFirstService.insertCategoryFirst(categoryFirst);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该一级分类已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改一级分类信息")
    @PostMapping("/updateCategoryFirst")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "一级分类的名称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "一级分类的id",
                    dataType = "int", paramType = "query")
    })
    public Result updateCategoryFirst(String categoryName,Integer id){
        //1、判断是否为空
        if(categoryName==null || "".equals(categoryName)
        || id==null || "".equals(id))
        {
            return Result.build(400,"数据不可以为空");
        }
        //id和删除标记
        CategoryFirst categoryFirst = new CategoryFirst();
        categoryFirst.setIsdelete(0);
        categoryFirst.setCategoryName(categoryName);
        categoryFirst.setId(id);
        //修改
        Integer integer = categoryFirstService.updateCategoryFirst(categoryFirst);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该一级分类已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deeteCategoryFirst")
    @ApiOperation("删除某一个一级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的一级分类id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deeteCategoryFirst(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Integer integer = categoryFirstService.deeteCategoryFirst(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listAllCategoryFirst")
    @ApiOperation("查询全部一级分类")
    public Result listAllCategoryFirst(){

        //执行查询
        List<CategoryFirst> categoryFirsts = categoryFirstService.listAllCategoryFirst();
        if (categoryFirsts.size() > 0) {
            return Result.build(200, "有数据", categoryFirsts);
        }else if(categoryFirsts.size() == 0){
            return Result.build(400,"没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }

    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/findCategoryFirstById")
    @ApiOperation("根据id查询某一级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询一级分类id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result findCategoryFirstById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        CategoryFirst categoryFirst = categoryFirstService.findCategoryFirstById(id);
        if (categoryFirst != null) {
            return Result.build(200,"查到了",categoryFirst);
        }else{
            return Result.build(400, "没有此一级分类");
        }
    }


}
