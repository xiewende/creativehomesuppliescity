package com.xiewende.creativehomesuppliescity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.FinnishProgram;
import com.xiewende.creativehomesuppliescity.pojo.Program;
import com.xiewende.creativehomesuppliescity.service.FinnishProgramService;
import com.xiewende.creativehomesuppliescity.service.ProgramService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.ProgramVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "finnishProgram(11)",description = "成功案例管理接口")
@RestController
@CrossOrigin
@RequestMapping("/finnishProgram")
public class FinnishProgramController {

    @Autowired
    private FinnishProgramService finnishProgramService;

    @Autowired
    private ConstantProperties properties;


    //查询全部
    //管理员查看全部成功案例
    @PostMapping("/listAllFinnishProgramWithSomeDemand")
    @ApiOperation("管理员查看全部成功案例（两个名字模糊）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "designerName", value = "设计师名字",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "查询页码", required = true,
                    dataType = "Int", paramType = "query",defaultValue = "1")
    })
    public Result listAllFinnishProgramWithSomeDemand(String userName, String designerName,Integer pageNum){
       //特殊判断
        if("".equals(userName)) userName = null;
        if("".equals(designerName)) designerName = null;

        //分页
        PageHelper.startPage(pageNum,properties.getPageSize());

        //执行查询
        List<FinnishProgram> finnishPrograms = finnishProgramService.listAllFinnishProgramWithSomeDemand(userName, designerName);
        PageInfo<FinnishProgram> finnishProgramsPageInfo = new PageInfo<>(finnishPrograms);

        if (finnishPrograms.size() > 0) {
            return Result.build(200, "有数据", finnishProgramsPageInfo);
        }else if(finnishPrograms.size() == 0){
            return Result.build(400,"没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }


    //可以选择删除某一个成功案例
    @ApiOperation("若完成后可以选择删除一个定制内容")
    @PostMapping("/deleteFinnishProgram")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成功案例id", required = true,
                    dataType = "", paramType = "query"),
    })
    public Result deleteFinnishProgram(Integer id){
        //1、判断是否为空
        if(id == null || "".equals(id)) return Result.build(400,"数据不可以为空");
        //确定删除
        Integer integer = finnishProgramService.deleteFinnishProgram(id);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }


}
