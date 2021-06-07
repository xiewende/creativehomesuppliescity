<template>
  <div style="margin:20px">
    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="schemes"
    >
      <el-table-column prop="goodName" label="商品名"> </el-table-column>
      <el-table-column prop="size" label="尺寸"> </el-table-column>
      <el-table-column prop="otherDemand" label="其他要求"> </el-table-column>
      <el-table-column label="图片">
        <template slot-scope="scope">
          <img
            :src="scope.row.finishPic"
            style="height: 50px; margin: 0 auto"
          />
        </template>
      </el-table-column>
      <el-table-column prop="finisnIdea" label="理念讲述"> </el-table-column>
      <el-table-column prop="statusStr" label="状态"> </el-table-column>
      <el-table-column prop="createTimeStr" label="创建日期"> </el-table-column>
      <el-table-column prop="finishTimeStr" label="完成时间"> </el-table-column>
      <el-table-column prop="isAcceptStr" label="是否接受"> </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.status==0"><el-button size="mini" type="danger" @click="finishClick(scope.row)"
            >完成定制</el-button></span>
          <span v-else>
            <el-button size="mini" type="danger" 
            >等待用户反馈</el-button>
          </span>
           
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="定制" :visible="isFinish" width="40%">
    
     
      <el-form
        :model="newSchemes"
        :rules="rules"
        ref="newSchemes"
        label-width="90px"
      >
        
  <el-form-item label="图片" prop="file" v-if="isFinish" >
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccessUpdate"
                :before-upload="beforeAvatarUpload">
                <img v-if="newSchemes.file" :src="newSchemes.file" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>

        <el-form-item label="设计理念" prop="finishIdea">
          <el-input
            size="small"
            type="textarea"
            v-model="newSchemes.finishIdea"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('newSchemes')">取 消</el-button>
        <el-button type="primary" @click="finishDesign('newSchemes')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { reqProgramFinish, reqProgramSelectByDesignerId } from "../../api";
export default {
  data() {
    return {
      isFinish: false,

      schemes: [],
      isNew: "",

      newSchemes: {
        file: "",
        finishIdea: "",
        id: "",
      },
      rules: {
        file: [{ required: true, message: "请上传图片", trigger: "blur" }],
        finishIdea: [
          { required: true, message: "请填写设计理念", trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.getSchemes();
  },
  methods: {
    async getSchemes() {
      let designerId = window.localStorage.getItem("id");
      let res = await reqProgramSelectByDesignerId(designerId, this.isNew);
      if (res.status === 200) {
        this.schemes = res.data;
      }
    },

    // 点击 完成定制
    finishClick(row) {
      this.isFinish = true;

     this.newSchemes.id = row.id;
    },

    // 设计师完成设计,提交设计图等
    finishDesign(formName) {
      let newSchemes = this.newSchemes;
      console.log(newSchemes);
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqProgramFinish(
            newSchemes.file,
            newSchemes.finishIdea,
            newSchemes.id
            //window.localStorage.getItem("id")
          );
          if (result.status === 200) {
            this.$message({
              message: "提交成功",
              type: "success",
            });
            this.isFinish = false;
            this.getSchemes();
          }else{
            this.$message.error(result.msg)
          }
        } else {
          return false;
        }
      });
    },

    // 取消 定制
    addCancel(formName) {
      this.$refs[formName].resetFields();
      this.isFinish = false;
    },

     //上传图片  添加的时候编辑
    handleAvatarSuccessUpdate(res, file) {
        console.log(res)
        this.$nextTick(()=>{
             this.newSchemes.file =res.data;
 console.log( "2222", this.newSchemes.file)
        },1000)
       
        console.log(  this.newSchemes.file)
      },
    beforeAvatarUpload(file) {
      return true;
    }

  },
};
</script>


<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>