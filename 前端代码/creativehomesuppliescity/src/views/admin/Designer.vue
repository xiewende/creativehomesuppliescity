<template>
  <div>
    <div style="margin-bottom: 20px">
      <el-button type="warning" icon="el-icon-plus" @click="isAdd = true"
        >添加</el-button
      >

      <span style="margin-left: 30px">职称</span>
      <el-input
        v-model="professionalTitle"
        placeholder="请输入职称"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getDesigners()"
      ></el-input>
      <span style="margin-left: 30px">风格</span>
      <el-input
        v-model="type"
        placeholder="请输入风格"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getDesigners()"
      ></el-input>
    </div>

    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="designers"
    >
      <el-table-column prop="designerName" label="姓名"> </el-table-column>
      <el-table-column prop="iphone" label="电话"> </el-table-column>
      <el-table-column prop="email" label="邮箱"> </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          {{ scope.row.sex === 0 ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column label="头像">
        <template slot-scope="scope">
          <img :src="scope.row.image" style="height: 80px; width: 100px; margin: 0 auto" />
        </template>
      </el-table-column>
      <el-table-column prop="professionalTitle" label="职称"> </el-table-column>
      <el-table-column prop="designConcept" label="设计理念" width="250">
      </el-table-column>
      <el-table-column prop="type" label="风格"> </el-table-column>
      <el-table-column prop="time" label="从业时间"> </el-table-column>
      <el-table-column prop="college" label="毕业院校"> </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="updateClick(scope.row)"
            >编辑</el-button
          >
          <el-button size="mini" type="danger" @click="deleteClick(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="float: right; margin-top: 20px">
      <el-button
        type="warning"
        size="medium"
        icon="el-icon-arrow-left"
        @click="prePage()"
      ></el-button>
      <span>{{ pageNum }}</span>
      <el-button
        type="warning"
        size="medium"
        icon="el-icon-arrow-right"
        @click="nextPage()"
      ></el-button>
    </div>

    <!-- 添加 -->
    <el-dialog title="添加" :visible.sync="isAdd" width="40%">
      <el-form
        :model="newDesigner"
        :rules="rules"
        ref="newDesigner"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="designerName">
              <el-input
                size="small"
                v-model="newDesigner.designerName"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                type="email"
                size="small"
                v-model="newDesigner.email"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="电话" prop="iphone">
              <el-input size="small" v-model="newDesigner.iphone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="newDesigner.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">

          <el-col :span="12">
            <el-form-item label="头像" prop="file">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="newDesigner.file" :src="newDesigner.file" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="职称" prop="professionalTitle">
              <el-input
                size="small"
                v-model="newDesigner.professionalTitle"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="设计理念" prop="designConcept">
          <el-input
            type="textarea"
            v-model="newDesigner.designConcept"
          ></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风格" prop="type">
              <el-select
                v-model="newDesigner.type"
                size="small"
              >
                <el-option
                  v-for="(style, index) in styles"
                  :key="index"
                  :label="style.typeName"
                  :value="style.typeName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="从业时间" prop="time">
              <el-input v-model="newDesigner.time"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="college">
              <el-input v-model="newDesigner.college"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('newDesigner')">取 消</el-button>
        <el-button type="primary" @click="addCheck('newDesigner')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
      <el-form
        :model="designerUpdate"
        :rules="rules"
        ref="designerUpdate"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="designerName">
              <el-input
                size="small"
                v-model="designerUpdate.designerName"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                type="email"
                size="small"
                v-model="designerUpdate.email"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="电话" prop="iphone">
              <el-input size="small" v-model="designerUpdate.iphone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="designerUpdate.sex">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

      
        <el-row :gutter="20">
         
          <el-col :span="12">
            <el-form-item label="头像" prop="file">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccessUpdate"
                :before-upload="beforeAvatarUpload">
                <img v-if="designerUpdate.file" :src="designerUpdate.file" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="职称" prop="professionalTitle">
              <el-input
                size="small"
                v-model="designerUpdate.professionalTitle"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="设计理念" prop="designConcept">
          <el-input
            type="textarea"
            v-model="designerUpdate.designConcept"
          ></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风格" prop="type">
              <el-select
                v-model="designerUpdate.type"
                placeholder="请选择风格"
                size="small"
              >
                <el-option
                  v-for="(style, index) in styles"
                  :key="index"
                  :label="style.typeName"
                  :value="style.typeName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="从业时间" prop="time">
              <el-input v-model="designerUpdate.time"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="college">
              <el-input v-model="designerUpdate.college"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('designerUpdate')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该条设计师信息吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  reqDesignerAdd,
  reqDesignerDelete,
  reqDesignerListByPage,
  reqDesignerUpdate,
  reqStyleList,
} from "../../api";
export default {
  data() {
    return {
      isAdd: false,
      isUpdate: false,
      isDelete: false,

      professionalTitle: "",
      type: "",

      id: "",
      pageNum: 1,
      styles: [],

      designers: [],

      rules: {
        designerName: [
          { required: true, message: "请填写姓名", trigger: "blur" },
        ],
        iphone: [{ required: true, message: "请填写电话", trigger: "blur" }],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        file: [{ required: true, message: "请上传头像", trigger: "blur" }],
        professionalTitle: [
          { required: true, message: "请填写职称", trigger: "blur" },
        ],
        designConcept: [
          { required: true, message: "请填写设计理念", trigger: "blur" },
        ],
        type: [{ required: true, message: "请选择风格", trigger: "change" }],
        time: [{ required: true, message: "请填写从业时间", trigger: "blur" }],
        college: [
          { required: true, message: "请填写毕业院校", trigger: "blur" },
        ],
        email: [{ required: true, message: "请填写邮箱", trigger: "blur" }],
      },

      newDesigner: {
        designerName: "",
        iphone: "",
        sex: "",
        professionalTitle: "",
        designConcept: "",
        type: "",
        time: "",
        college: "",
        email: "",
        file: "",
      },

      designerUpdate: {
        designerName: "",
        iphone: "",
        sex: "",
        professionalTitle: "",
        designConcept: "",
        type: "",
        time: "",
        college: "",
        email: "",
        file: "",
        id: "",
      },
    };
  },
  mounted() {
    this.getDesigners();
    this.getStyles();
  },
  methods: {
    // 全部设计师
    async getDesigners() {
      let result = await reqDesignerListByPage(
        this.pageNum,
        this.professionalTitle,
        this.type
      );
      if (result.status === 200) {
        this.designers = result.data.list;
      }
    },

    // 添加   确认
    addCheck(formName) {
      let newDesigner = this.newDesigner;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqDesignerAdd(
            newDesigner.college,
            newDesigner.designConcept,
            newDesigner.designerName,
            newDesigner.email,
            newDesigner.file,
            newDesigner.iphone,
            newDesigner.professionalTitle,
            newDesigner.sex,
            newDesigner.time,
            newDesigner.type
          );
          if (result.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isAdd = false;
            this.getDesigners();
          }

           if (result.status === 400) {
            this.$message({
              message: result.msg,
              type: "success",
            });
          }

        } else {
          this.$message.error("添加错误");
          return false;
        }
      });
    },

    // 添加   取消
    addCancel(formName) {
      this.$refs[formName].resetFields();
      this.isAdd = false;
    },

    // 点击编辑按钮
    updateClick(row) {
      this.isUpdate = true;
      this.designerUpdate = row;
      this.designerUpdate.file = row.image;
    },

    // 编辑   确认
    updateCheck(formName) {
      let designerUpdate = this.designerUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqDesignerUpdate(
            designerUpdate.college,
            designerUpdate.designConcept,
            designerUpdate.designerName,
            designerUpdate.email,
            designerUpdate.file,
            designerUpdate.id,
            designerUpdate.iphone,
            designerUpdate.professionalTitle,
            designerUpdate.sex,
            designerUpdate.time,
            designerUpdate.type
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getDesigners();
          }
        } else {
          return false;
        }
      });
    },

    // 点击删除按钮
    deleteClick(row) {
      this.id = row.id;
      this.isDelete = true;
    },

    // 删除   确认
    async deleteCheck() {
      let result = await reqDesignerDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getDesigners();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqDesignerListByPage(
        this.pageNum,
        this.professionalTitle,
        this.type
      );
      if (result.status === 200) {
        this.designers = result.data.list;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqDesignerListByPage(
        this.pageNum,
        this.professionalTitle,
        this.type
      );
      if (result.status === 200) {
        this.designers = result.data.list;
      }
    },

    // 获取风格
    async getStyles() {
      let res = await reqStyleList();
      if (res.status === 200) {
        this.styles = res.data;
      }
    },

    //添加时候的图片
    handleAvatarSuccess(res, file) {
      console.log(res)
      this.newDesigner.file =res.data;
    },
    beforeAvatarUpload(file) {
      return true;
    },

    //编辑的时候
    handleAvatarSuccessUpdate(res, file) {
      console.log(res)
      this.designerUpdate.file =res.data;
      console.log(  this.designerUpdate.image )
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
