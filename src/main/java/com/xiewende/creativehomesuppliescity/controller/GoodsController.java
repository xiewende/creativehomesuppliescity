package com.xiewende.creativehomesuppliescity.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.Goods;
import com.xiewende.creativehomesuppliescity.service.GoodsService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.GoodsVo;
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
import java.util.Date;
import java.util.List;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "Goods",description = "家居用品管理接口")
@RestController
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ConstantProperties properties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    //添加
    @ApiOperation("添加家居用品")
    @PostMapping("/insertGoods")
    public Result insertGoods(GoodsVo goodsVo, MultipartFile file, HttpServletRequest request){
        //1、判断是否为空
        if(goodsVo.getBrandName() == null || "".equals(goodsVo.getBrandName())
        || goodsVo.getCategorySecondName() == null || "".equals(goodsVo.getCategorySecondName())
        || goodsVo.getDescription() == null || "".equals(goodsVo.getDescription())
        || goodsVo.getGoodName() == null || "".equals(goodsVo.getGoodName())
        || goodsVo.getPrice() == null || "".equals(goodsVo.getPrice())
        || goodsVo.getSize() == null || "".equals(goodsVo.getPrice())
        || goodsVo.getStore() == null || "".equals(goodsVo.getStore())
        || goodsVo.getStyleName() == null || "".equals(goodsVo.getStyleName())
        || file == null || file.getSize() == 0)
        {
            return Result.build(400,"数据不可以为空");
        }

        //复制
        Goods insertGoods = new Goods();
        BeanUtils.copyProperties(goodsVo,insertGoods); // 注意导入的包不一样，顺序不一样

        //逻辑删除
        insertGoods.setIsdelete(0);

        //两个时间
        insertGoods.setCreateTime(new Date());
        insertGoods.setUpdateTime(new Date());

        //点击量
        insertGoods.setHitNumber(0);

        //图片
        String storePath = UploadFileUtil.upload(file, fastFileStorageClient,properties);
        insertGoods.setImage(storePath);

        //添加
        Integer integer = goodsService.insertGoods(insertGoods,goodsVo.getCategorySecondName(),goodsVo.getStyleName(),goodsVo.getBrandName());
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改家居用品信息")
    @PostMapping("/updateGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "家居用品id",
                    dataType = "int", paramType = "query")
    })
    public Result updateGoods(GoodsVo goodsVo , Integer id, MultipartFile file, HttpServletRequest request){

        //1、判断是否为空
        if(goodsVo.getBrandName() == null || "".equals(goodsVo.getBrandName())
        || goodsVo.getCategorySecondName() == null || "".equals(goodsVo.getCategorySecondName())
        || goodsVo.getDescription() == null || "".equals(goodsVo.getDescription())
        || goodsVo.getGoodName() == null || "".equals(goodsVo.getGoodName())
        || goodsVo.getPrice() == null || "".equals(goodsVo.getPrice())
        || goodsVo.getSize() == null || "".equals(goodsVo.getPrice())
        || goodsVo.getStore() == null || "".equals(goodsVo.getStore())
        || goodsVo.getStyleName() == null || "".equals(goodsVo.getStyleName())
        || file == null || file.getSize() == 0)
        {
            return Result.build(400,"数据不可以为空");
        }

        Goods updateGoods = goodsService.findGoodById(id);

        //复制
        BeanUtils.copyProperties(goodsVo,updateGoods); // 注意导入的包不一样，顺序不一样

        //逻辑删除
        updateGoods.setIsdelete(0);

        //修改时间
        updateGoods.setUpdateTime(new Date());

        //图片
        String storePath = UploadFileUtil.upload(file, fastFileStorageClient,properties);
        updateGoods.setImage(storePath);

        //修改
        Integer integer = goodsService.updateGoods(updateGoods,goodsVo.getCategorySecondName(),goodsVo.getStyleName(),goodsVo.getBrandName());
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deleteGoods")
    @ApiOperation("删除某一个家居用品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的家居用品id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteGoods(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = goodsService.deleteGoods(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listGoodsWithSomeDemand")
    @ApiOperation("查询全部家居用品，全部输入参数都是可选参数可以为空")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryFirstName", value = "需要查询家居用品商一级分类",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "categorySecondName", value = "需要查询家居用品二级分类",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stypeName", value = "需要查询家居用品风格名称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "需要查询家居用品品牌名称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "goodName", value = "需要查询家居用品商品名称",
                    dataType = "string", paramType = "query")
    })
    public Result listGoodsWithSomeDemand(String categoryFirstName,String categorySecondName, String stypeName, String brandName, String goodName){

        if("".equals(categorySecondName)) categorySecondName = null;
        if("".equals(categoryFirstName)) categoryFirstName = null;
        if("".equals(stypeName)) stypeName = null;
        if("".equals(brandName)) brandName = null;
        if("".equals(goodName)) goodName = null;


        //执行查询
        List<Goods> goods = goodsService.listGoodsWithSomeDemand(categoryFirstName,categorySecondName, stypeName, brandName, goodName);
        if (goods.size() > 0) {
            return Result.build(200, "有数据", goods);
        }else if(goods.size() == 0){
            return Result.build(400,"没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }

    }

    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/selectGoodsDetails")
    @ApiOperation("根据id查询某家居用品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询家居用品id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectGoodsDetails(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Goods goods = goodsService.selectGoodsDetails(id);
        if (goods != null) {
            return Result.build(200,"查到了",goods);
        }else{
            return Result.build(400, "没有此风格");
        }
    }


}
