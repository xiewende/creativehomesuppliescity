<template>
  <div>
    <div class="container">
      <div class="register">
        <p class="title">注 册</p>
        <el-form
          :model="newUser"
          :rules="rules"
          ref="newUser"
          label-width="80px"
          label-position="left"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="用户名" prop="userName">
                <el-input size="small" v-model="newUser.userName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号" prop="iphone">
                <el-input size="small" v-model="newUser.iphone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="密码" prop="password">
                <el-input
                  size="small"
                  type="password"
                  v-model="newUser.password"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="密码确认" prop="affirmPassword">
                <el-input
                  size="small"
                  type="password"
                  v-model="newUser.affirmPassword"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="newUser.sex">
                  <el-radio label="0">男</el-radio>
                  <el-radio label="1">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input
                  size="small"
                  type="email"
                  v-model="newUser.email"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="收货地址" prop="address">
            <el-input
              size="small"
              type="textarea"
              v-model="newUser.address"
            ></el-input>
          </el-form-item>

          <el-form-item label="头像" prop="file">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="newUser.file" :src="newUser.file" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>

          </el-form-item>

          <el-form-item>
            <el-button @click="resetForm('newUser')">重置</el-button>
            <el-button
              type="primary"
              @click="loginClick('newUser')"
              style="float: right"
              >注册</el-button
            >
          </el-form-item>
        </el-form>
        <router-link to="/login">
          <el-link type="warning" style="float: right">登 录</el-link>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { reqRegister } from "../api";
export default {
  data() {
    var passCheck = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.newUser.password) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };
    return {
      newUser: {
        userName: "",
        iphone: "",
        password: "",
        affirmPassword: "",
        sex: "",
        email: "",
        address: "",
        file: "",
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        iphone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "长度在 6 到 20 个字符",
            trigger: "blur",
          },
        ],
        affirmPassword: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { validator: passCheck, trigger: "blur" },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        file: [{ required: true, message: "请选择头像", trigger: "blur" }],
      },
    };
  },
  methods: {
    loginClick(formName) {
      let newUser = this.newUser;

      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqRegister(
            newUser.address,
            newUser.affirmPassword,
            newUser.email,
            newUser.file,
            newUser.iphone,
            newUser.password,
            newUser.sex,
            newUser.userName
          );
          if (result.status === 200) {
            this.$message({
              message: "注册成功",
              type: "success",
            });
            this.$router.push("/login");
          }

          if (result.status === 400) {
            this.$message({
              message: result.msg,
              type: "success",
            });
            
          }

        } else {
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    
    handleAvatarSuccess(res, file) {
        console.log(res)
        this.newUser.file =res.data;
         
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
.container {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url("../assets/login_bg1.jpg") no-repeat;
  background-size: cover;

  display: flex;
  justify-content: center;
  align-items: center;
}

.register {
  width: 450px;
  padding: 30px 35px 30px 30px;
  border-radius: 20px;
  margin-right: 80px;
  background-color: rgba(168, 206, 231, 0.5);
}

.title {
  text-align: center;
  font-size: 20px;
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
}
</style>