package com.xiewende.creativehomesuppliescity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiewende.creativehomesuppliescity.pojo.Brand;
import com.xiewende.creativehomesuppliescity.pojo.Style;
import com.xiewende.creativehomesuppliescity.service.BrandService;
import com.xiewende.creativehomesuppliescity.service.StyleService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
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

@Api(tags = "Brand(6)",description = "品牌管理接口")
@RestController
@CrossOrigin
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ConstantProperties constantProperties;

    //添加
    @ApiOperation("添加品牌")
    @PostMapping("/insertBrand")
    public Result insertBrand(BrandVo brandVo){
        //1、判断是否为空
        if(brandVo.getBrandName()==null || "".equals(brandVo.getBrandName())
        || brandVo.getDescription() == null || "".equals(brandVo.getDescription())
        || brandVo.getDesignerName() == null || "".equals(brandVo.getDesignerName()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //复制
        Brand insertBrand = new Brand();
        BeanUtils.copyProperties(brandVo,insertBrand); // 注意导入的包不一样，顺序不一样

        //逻辑删除
        insertBrand.setIsdelete(0);

        //添加
        Integer integer = brandService.insertBrand(insertBrand,brandVo.getDesignerName());
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该品牌已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改品牌信息")
    @PostMapping("/updateBrand")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id",
                    dataType = "int", paramType = "query")
    })
    public Result updateBrand(BrandVo brandVo , Integer id){
        //1、判断是否为空
        if(brandVo.getBrandName()==null || "".equals(brandVo.getBrandName())
                || brandVo.getDescription() == null || "".equals(brandVo.getDescription())
                || brandVo.getDesignerName() == null || "".equals(brandVo.getDesignerName()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //复制
        Brand updateBrand = new Brand();
        BeanUtils.copyProperties(brandVo,updateBrand); // 注意导入的包不一样，顺序不一样

        //id和删除标记
        updateBrand.setId(id);
        updateBrand.setIsdelete(0);

        //修改
        Integer integer = brandService.updateBrand(updateBrand,brandVo.getDesignerName());
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该品牌已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deleteBrand")
    @ApiOperation("删除某一个品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的品牌id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteBrand(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = brandService.deleteBrand(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listAllBrand")
    @ApiOperation("查询全部品牌（不带分页，供其他选择品牌使用）")
    public Result listAllBrand(){

        //执行查询
        List<Brand> brands = brandService.listAllBrand();
        if (brands.size() > 0) {
            return Result.build(200, "有数据", brands);
        }else if(brands.size() == 0){
            return Result.build(400,"=没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }


    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listAllBrandByPage")
    @ApiOperation("查询全部品牌（分页，供展示的时候）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询页码", required = true,
                    dataType = "Int", paramType = "query",defaultValue = "1")
    })
    public Result listAllBrandByPage(Integer pageNum){

        //分页
        PageHelper.startPage(pageNum,constantProperties.getPageSize());
        //执行查询
        List<Brand> brands = brandService.listAllBrand();
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        if (brands.size() > 0) {
            return Result.build(200, "有数据", brandPageInfo);
        }else if(brands.size() == 0){
            return Result.build(400,"=没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }


    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/selectBrandById")
    @ApiOperation("根据id查询某品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询品牌id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectBrandById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Brand brand = brandService.selectBrandById(id);
        if (brand != null) {
            return Result.build(200,"查到了",brand);
        }else{
            return Result.build(400, "没有此风格");
        }
    }


}
