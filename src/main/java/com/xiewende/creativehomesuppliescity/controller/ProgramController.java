package com.xiewende.creativehomesuppliescity.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.Program;
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

@Api(tags = "program(10)",description = "个人定制管理接口")
@RestController
@CrossOrigin
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @Autowired
    private ConstantProperties properties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //用户定制
    @ApiOperation("添加个人定制")
    @PostMapping("/insertProgram")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登录的用户id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "designerId", value = "需要向某个设计师设计的设计师id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodName", value = "设计的商品名称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "设计的商品尺寸",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "otherDemand", value = "设计的商品其他要求",
                    dataType = "string", paramType = "query"),
    })
    public Result insertProgram(Integer userId,Integer designerId,String goodName,String size,String otherDemand){


        //todo  还需要判断是否已经登录的状态，这里需要redis，后续再来完善，没有登录直接返回
        String isLogin = (String) redisTemplate.opsForValue().get("user");
        if(isLogin == null) return Result.build(500,"没有登录，请先登录此系统！");

        //1、判断是否为空
        if(userId == null || designerId == null
        || goodName == null || "".equals(goodName)
        || size == null || "".equals(size)
        || otherDemand == null || "".equals(otherDemand))
        {
            return Result.build(400,"数据不可以为空");
        }
        //提交
        Integer integer = programService.insertProgram(userId, designerId, goodName, size, otherDemand);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("用户查看自己的定制")
    @PostMapping("/selectProgramByUserId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id",
                    dataType = "int", paramType = "query"),
    })
    public Result selectProgramByUserId(Integer userId){
        //1、判断是否为空
        //若没有选择个数的话，默认为原来，直接返回
        if("".equals(userId) || userId == null){
            return Result.build(400,"您不需要改变个数");
        }
        //进行查看
        List<Program> programs = programService.selectProgramByUserId(userId);

        //封装给前端
        List<ProgramVo> programVos = new ArrayList<>();
        for(Program program :programs) {
            ProgramVo programVo = new ProgramVo();
            BeanUtils.copyProperties(program,programVo); // 注意导入的包不一样，顺序不一样
            if(program.getCreateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(program.getCreateTime());
                programVo.setCreateTimeStr(dateString);
            }
            if(program.getFinishTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(program.getFinishTime());
                programVo.setFinishTimeStr(dateString);
            }

            if(program.getStatus() == 0) programVo.setStatusStr("请求提交成功");
            else if(program.getStatus() == 1) programVo.setStatusStr("完成设计");

            if(program.getIsAccept() != null){
                if(program.getIsAccept() == 0) programVo.setIsAcceptStr("用户没有接受此设计");
                else if(program.getIsAccept() == 1) programVo.setIsAcceptStr("用户已经接受此设计");
            }
            programVos.add(programVo);
        }

        if (programs.size() > 0) {
            return Result.build(200,"有数据",programVos);
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //用户接受这个定制
    @PostMapping("/receiveThisProgram")
    @ApiOperation("用户接受这个定制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要确定的个人定制id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result receiveThisProgram(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = programService.receiveThisProgram(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }


    //用户拒绝这个定制
    @PostMapping("/rejectThisProgram")
    @ApiOperation("用户拒绝这个定制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要确定的个人定制id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result rejectThisProgram(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = programService.rejectThisProgram(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //设计师查看向自己的定制内容
    @PostMapping("/selectProgramByDesignerId")
    @ApiOperation("设计师查看向自己的定制内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "designerId", value = "登录的设计师id", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isNew", value = "是否是新的定制（是 or 否）",
                    dataType = "string", paramType = "query")
    })
    public Result selectProgramByDesignerId(Integer designerId,String isNew){
        if(isNew == null || "".equals(isNew)) isNew = "否";
        //执行查询
        List<Program> programs = programService.selectProgramByDesignerId(designerId, isNew);

        //封装给前端
        List<ProgramVo> programVos = new ArrayList<>();
        for(Program program :programs) {
            ProgramVo programVo = new ProgramVo();
            BeanUtils.copyProperties(program,programVo); // 注意导入的包不一样，顺序不一样
            if(program.getCreateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(program.getCreateTime());
                programVo.setCreateTimeStr(dateString);
            }
            if(program.getFinishTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(program.getFinishTime());
                programVo.setFinishTimeStr(dateString);
            }

            if(program.getStatus() == 0) programVo.setStatusStr("请求提交成功");
            else if(program.getStatus() == 1) programVo.setStatusStr("完成设计");

            if(program.getIsAccept() != null){
                if(program.getIsAccept() == 0) programVo.setIsAcceptStr("用户没有接受此设计");
                else if(program.getIsAccept() == 1) programVo.setIsAcceptStr("用户已经接受此设计");
            }
            programVos.add(programVo);
        }

        if (programs.size() > 0) {
            return Result.build(200,"有数据",programVos);
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }


    //完成用户定制
    @ApiOperation("设计师完成设计,提交设计图等")
    @PostMapping("/finishThisProgram")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "个人定制的id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "finishIdea", value = "设计师设计的理念",
                    dataType = "string", paramType = "query"),
    })
    public Result finishThisProgram(Integer id, String finishIdea, MultipartFile file, HttpServletRequest request){

        //String finishPic

        //1、判断是否为空
        if( id== null || finishIdea == null || "".equals(finishIdea) ||  file == null || file.getSize() == 0)
        {
            return Result.build(400,"数据不可以为空");
        }

        //拿到图片
        String storePath = UploadFileUtil.upload(file, fastFileStorageClient,properties);

        //提交
        Integer integer = programService.finishThisProgram(id, storePath, finishIdea);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

}
