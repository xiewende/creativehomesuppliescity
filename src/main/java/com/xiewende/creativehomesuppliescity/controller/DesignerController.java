package com.xiewende.creativehomesuppliescity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.Style;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.DesignerService;
import com.xiewende.creativehomesuppliescity.service.StyleService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.utils.UploadFileUtil;
import com.xiewende.creativehomesuppliescity.vo.DesignerVo;
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

@Api(tags = "Designer(2)",description = "设计师管理接口")
@RestController
@CrossOrigin
@RequestMapping("/designer")
public class DesignerController {

    @Autowired
    private DesignerService designerService;

    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    //添加
    @ApiOperation("添加设计师")
    @PostMapping("/InsertDesigner")
    public Result InsertDesigner(DesignerVo designerVo){
        //1、判断是否为空
        if(designerVo.getCollege() == null || "".equals(designerVo.getCollege())
        || designerVo.getDesignConcept() == null || "".equals(designerVo.getDesignConcept())
        || designerVo.getDesignerName() == null || "".equals(designerVo.getDesignerName())
        || designerVo.getEmail() == null || "".equals(designerVo.getEmail())
        || designerVo.getIphone() == null || "".equals(designerVo.getIphone())
        || designerVo.getProfessionalTitle()== null || "".equals(designerVo.getProfessionalTitle())
        || designerVo.getSex() == null || "".equals(designerVo.getSex())
        || designerVo.getTime() == null || "".equals(designerVo.getTime())
        || designerVo.getType() == null || "".equals(designerVo.getType()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //对电话号码的校验
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(String.valueOf(designerVo.getIphone()));
        if(!m.matches())return Result.build(400,"电话号码格式错误！！！");

        //对邮箱进行检验
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(designerVo.getEmail());
        if(!m1.matches()) return Result.build(400,"邮箱格式错误！！！");

        //复制给dao层bean
        Designer insertdesigner = new Designer();
        BeanUtils.copyProperties(designerVo,insertdesigner); // 注意导入的包不一样，顺序不一样

        //性别
        if(designerVo.getSex().equals("男"))insertdesigner.setGender(0);
        else insertdesigner.setGender(1);

        //逻辑删除
        insertdesigner.setIsdelete(0);

        //默认密码
        insertdesigner.setPassword(MD5Utils.getMD5passwprd(constantProperties.getDefaltPassword()));

        //首次人气
        insertdesigner.setPopularity(0);

        //头像
        insertdesigner.setImage(designerVo.getFile());

        //添加
        Integer integer = designerService.InsertDesigner(insertdesigner);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该设计师已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改设计师")
    @PostMapping("/updateDesigner")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设计师id",
                    dataType = "int", paramType = "query")
    })
    public Result updateDesigner(DesignerVo designerVo,Integer id,MultipartFile file, HttpServletRequest request){
        //1、判断是否为空
        if(designerVo.getCollege() == null || "".equals(designerVo.getCollege())
                || designerVo.getDesignConcept() == null || "".equals(designerVo.getDesignConcept())
                || designerVo.getDesignerName() == null || "".equals(designerVo.getDesignerName())
                || designerVo.getEmail() == null || "".equals(designerVo.getEmail())
                || designerVo.getIphone() == null || "".equals(designerVo.getIphone())
                || designerVo.getProfessionalTitle()== null || "".equals(designerVo.getProfessionalTitle())
                || designerVo.getSex() == null || "".equals(designerVo.getSex())
                || designerVo.getTime() == null || "".equals(designerVo.getTime())
                || designerVo.getType() == null || "".equals(designerVo.getType()))
        {
            return Result.build(400,"数据不可以为空");
        }

        //对电话号码的校验
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(String.valueOf(designerVo.getIphone()));
        if(!m.matches())return Result.build(400,"电话号码格式错误！！！");

        //对邮箱进行检验
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(designerVo.getEmail());
        if(!m1.matches()) return Result.build(400,"邮箱格式错误！！！");

        //复制给dao层bean
        Designer insertdesigner = designerService.selectDesignerById(id);
        BeanUtils.copyProperties(designerVo,insertdesigner); // 注意导入的包不一样，顺序不一样

        //性别
        if(designerVo.getSex().equals("男"))insertdesigner.setGender(0);
        else insertdesigner.setGender(1);

        //头像
        if(file != null && file.getSize() != 0){  //默认头像
            //更好的做法就是抽出一个方法来，但是有点问题  只能这里很多代码了
            String storePath = UploadFileUtil.upload(file, fastFileStorageClient,constantProperties);
            insertdesigner.setImage(storePath);
        }

        //添加
        Integer integer = designerService.updateDesigner(insertdesigner);
        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "该设计师已经存在！！！");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deteleDesigner")
    @ApiOperation("删除某一个设计师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的设计师id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deteleDesigner(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = designerService.deteleDesigner(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listDesigner")
    @ApiOperation("条件查询全部设计师（不带分页，供其他选择设计师使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "professionalTitle", value = "设计师职称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "设计师风格",
                    dataType = "string", paramType = "query")
    })
    public Result listDesigner(String professionalTitle, String type){

        if("".equals(professionalTitle))professionalTitle = null;
        if("".equals(type)) type = null;

        //执行查询
        List<Designer> designers = designerService.listDesigner(professionalTitle, type);

        if (designers.size() > 0) {
            return Result.build(200, "有数据", designers);
        }else if(designers.size() == 0){
            return Result.build(400,"=没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }


    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listDesignerByPage")
    @ApiOperation("条件查询全部设计师（带分页，查询全部展示使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "professionalTitle", value = "设计师职称",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "设计师风格",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "查询页码", required = true,
                    dataType = "Int", paramType = "query",defaultValue = "1")
    })
    public Result listDesignerByPage(String professionalTitle, String type,Integer pageNum){

        if("".equals(professionalTitle))professionalTitle = null;
        if("".equals(type)) type = null;

        //分页
        PageHelper.startPage(pageNum,constantProperties.getPageSize());
        //执行查询
        List<Designer> designers = designerService.listDesigner(professionalTitle, type);
        PageInfo<Designer> designerPageInfo = new PageInfo<>(designers);
        if (designers.size() > 0) {
            return Result.build(200, "有数据", designerPageInfo);
        }else if(designers.size() == 0){
            return Result.build(400,"没有数据");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }

    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/selectDesignerById")
    @ApiOperation("根据id查询某设计师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询设计师的id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectDesignerById(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Designer designer = designerService.selectDesignerById(id);
        if (designer != null) {
            return Result.build(200,"查到了",designer);
        }else{
            return Result.build(400, "没有此风格");
        }
    }

    //修改密码
    @PostMapping("/updateDesignerPassword")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被修改密码的设计师id", required = true,
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

        Integer integer = designerService.updatePassword(id, oldPassword, newPassword);

        if (integer > 0) {
            return Result.ok();
        } else if (integer == 0) {
            return Result.build(400, "原始密码填写错误");
        } else {
            return Result.build(500, "系统错误！！！");
        }
    }


    @PostMapping("/selectDesignerDeatils")
    @ApiOperation("查询设计师某一位设计师的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询设计师的id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectDesignerDeatils(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");
        Designer designer = designerService.selectDesignerDeatils(id);
        if (designer != null) {
            return Result.build(200,"查到了",designer);
        }else{
            return Result.build(400, "没有此风格");
        }
    }


}
