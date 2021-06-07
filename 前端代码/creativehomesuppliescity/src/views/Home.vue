<template>
  <div>
    
    <!-- 管理员 -->
    <el-container :style="{ minHeight: '100vh' }" v-if="isLogin && tag === '2'">
      <el-aside width="200px" :style="{ minHeight: '100vh' }">
        <el-menu
          default-active="1"
          background-color="#909399"
          text-color="#fff"
          active-text-color="#ffd04b"
          style="height: 100%"
          :router="true"
        >
          <el-menu-item index="1" route="/admin/user">
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="2" route="/admin/designer">
            <span>设计师管理</span>
          </el-menu-item>
          <el-menu-item index="3" route="/admin/brand">
            <span>品牌管理</span>
          </el-menu-item>
          <el-submenu index="4">
            <template slot="title">
              <span>家居用品管理</span>
            </template>
            <el-menu-item index="4-1" route="/admin/category">
              <span>分类管理</span>
            </el-menu-item>
            <el-menu-item index="4-2" route="/admin/style">
              <span>风格管理</span>
            </el-menu-item>
            <el-menu-item index="4-3" route="/admin/goods">
              <span>商品管理</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">
              <span>个人定制</span>
            </template>
            <el-menu-item index="5-1" route="/admin/designscheme">
              <span>设计方案</span>
            </el-menu-item>
            <el-menu-item index="5-2" route="/admin/success">
              <span>成功案例</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title">
              <span>订单管理</span>
            </template>
            <el-menu-item index="6-1" route="/admin/neworder">
              <span>新订单</span>
            </el-menu-item>
            <el-menu-item index="6-2" route="/admin/allorder">
              <span>全部订单</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <h3 class="title">创意家居用品城</h3>
          <div class="login" @click="logout()">退出登录</div>
        </el-header>
        <el-main style="margin: 20px">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

    <!-- 设计师 -->
    <el-container :style="{ minHeight: '100vh' }" v-if="isLogin && tag === '1'">
      <el-header class="header">
        <el-row>
          <el-col :span="8">
            <h3 class="title">创意家居用品城</h3>
          </el-col>

          <el-col :span="8">
            <el-aside width="200px" :style="{ height: '100%' }">
              <el-menu
                default-active="1"
                background-color="rgb(230, 171, 63)"
                text-color="#fff"
                active-text-color="#000"
                style="height: 100%"
                :router="true"
                mode="horizontal"
                class="menu"
              >
                <el-menu-item index="1" route="/designer/scheme">
                  <span>个人定制</span>
                </el-menu-item>
              </el-menu>
            </el-aside>
          </el-col>

          <el-col :span="8">
            <el-dropdown class="setting" @command="dropdownClick">
              <span class="el-dropdown-link">
                <el-image
                  style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-top: 10px;
                  "
                  :src="designer.image"
                ></el-image>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="updateDesignerPwd"
                  >修改设计师密码</el-dropdown-item
                >
                <el-dropdown-item command="desigerInfo"
                  >设计师个人信息</el-dropdown-item
                >
                <el-dropdown-item command="logout">注销</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
<!-- 用户 -->
    <el-container
      :style="{ minHeight: '100vh' }"
      v-if="!isLogin || tag === '0'"
      class="main-box"
    >
      <el-header class="header">
        <el-row>
          <el-col :span="8">
            <h3 class="title">创意家居用品城</h3>
          </el-col>

          <el-col :span="8">
            <el-menu
              default-active="1"
              background-color="rgb(230, 171, 63)"
              text-color="#fff"
              active-text-color="#000"
              style="height: 100%"
              :router="true"
              mode="horizontal"
              class="menu"
            >
              <el-menu-item index="1" route="/user/goods">
                <span>商品</span>
              </el-menu-item>
              <el-menu-item index="2" route="/user/designer">
                <span>设计师</span>
              </el-menu-item>
              <el-menu-item index="3" route="/user/cart">
                <span>购物车</span>
              </el-menu-item>
              <el-menu-item index="4" route="/user/order">
                <span>订单</span>
              </el-menu-item>
              <el-menu-item index="5" route="/user/design">
                <span>个人定制</span>
              </el-menu-item>
            </el-menu>
          </el-col>

          <el-col :span="8">
            <div v-if="!isLogin" class="login" @click="toLogin()">
              登录/注册
            </div>
            <el-dropdown class="setting" @command="dropdownClick" v-else>
              <span class="el-dropdown-link">
                <el-image
                  style="
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                    margin-top: 10px;
                  "
                  :src="user.image"
                ></el-image>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="updatePwd"
                  >修改密码</el-dropdown-item
                >
                <el-dropdown-item command="userInfo"
                  >用户个人信息</el-dropdown-item
                >
                <el-dropdown-item command="logout">注销</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <!-- 修改密码 -->
    <el-dialog title="修改密码" :visible.sync="isUpdatePwd" width="40%">
      <el-form
        :model="password"
        :rules="rules"
        ref="password"
        label-width="90px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input type="password" v-model="password.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" v-model="password.newPassword"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updatePwdCancel('password')">取 消</el-button>
        <el-button type="primary" @click="updatePwdCheck('password')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改用户信息 -->
    <el-dialog title="修改个人信息" :visible.sync="isUpdateUser" width="40%">
      <el-form
        :model="user"
        :rules="rules"
        ref="user"
        label-width="80px"
        label-position="left"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input size="small" v-model="user.userName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="iphone">
              <el-input size="small" v-model="user.iphone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input
                size="small"
                type="password"
                v-model="user.password"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="头像" prop="file">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccessUpdateUser"
                :before-upload="beforeAvatarUpload">
                <img v-if="user.image" :src="user.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>

          <!-- <el-col :span="12">
            <el-form-item label="头像" prop="image">
              <el-input
                size="small"
                type="file"
                v-model="user.image"
              ></el-input>
            </el-form-item>
          </el-col> -->

        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="user.gender">
                <el-radio :label="0" :value="0">男</el-radio>
                <el-radio :label="1" :value="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                size="small"
                type="email"
                v-model="user.email"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="收货地址" prop="address">
          <el-input
            size="small"
            type="textarea"
            v-model="user.address"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdateUser = false">取 消</el-button>
        <el-button type="primary" @click="updateUserCheck('user')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改设计师密码 -->
    <el-dialog
      title="修改设计师密码"
      :visible.sync="isUpdateDesignerPwd"
      width="40%"
    >
      <el-form
        :model="designerPwd"
        :rules="rules"
        ref="designerPwd"
        label-width="90px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input
            type="password"
            v-model="designerPwd.oldPassword"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            type="password"
            v-model="designerPwd.newPassword"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDesignerPwdCancel('designerPwd')"
          >取 消</el-button
        >
        <el-button type="primary" @click="updateDesignerPwdCheck('designerPwd')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改设计师信息 -->
    <el-dialog
      title="修改设计师信息"
      :visible.sync="isUpdateDesigner"
      width="40%"
    >
      <el-form
        :model="designer"
        :rules="designerRules"
        ref="designer"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="designerName">
              <el-input size="small" v-model="designer.designerName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                type="email"
                size="small"
                v-model="designer.email"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="电话" prop="iphone">
              <el-input size="small" v-model="designer.iphone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="designer.gender">
                <el-radio :label="0" :value="0">男</el-radio>
                <el-radio :label="1" :value="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">

          <!-- <el-col :span="12">
            <el-form-item label="头像" prop="image">
              <el-input
                size="small"
                type="file"
                v-model="designer.image"
              ></el-input>
            </el-form-item>
          </el-col> -->

           <el-col :span="12">
            <el-form-item label="头像" prop="file">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccessUpdate"
                :before-upload="beforeAvatarUpload">
                <img v-if="designer.image" :src="designer.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="职称" prop="professionalTitle">
              <el-input
                size="small"
                v-model="designer.professionalTitle"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="设计理念" prop="designConcept">
          <el-input type="textarea" v-model="designer.designConcept"></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风格" prop="type">
              <el-input size="small" v-model="designer.type"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="从业时间" prop="time">
              <el-input v-model="designer.time"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="college">
              <el-input v-model="designer.college"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdateDesigner = false">取 消</el-button>
        <el-button type="primary" @click="updateDesignerCheck('designer')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  reqDesignerDetail,
  reqDesignerPasswordUpdate,
  reqDesignerUpdate,
  reqLoginOut,
  reqUserPasswordUpdate,
  reqUserSelectById,
  reqUserUpdate,
} from "../api";
export default {
  data() {
    return {
      isUpdatePwd: false,
      isUpdateUser: false,
      isLogin: false,
      tag: "",

      avatarUrl:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      rules: {
        oldPassword: [
          { required: true, message: "请填写原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请填写新密码", trigger: "blur" },
        ],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        iphone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        image: [{ required: true, message: "请选择头像", trigger: "blur" }],
      },
      password: {
        oldPassword: "",
        newPassword: "",
      },
      user: {
        id: "",
        address: "",
        email: "",
        image: "",
        iphone: "",
        password: "",
        gender: "",
        userName: "",
      },

      isUpdateDesignerPwd: false,
      isUpdateDesigner: false,
      designerPwd: {
        newPassword: "",
        oldPassword: "",
      },
      designer: {
        college: "",
        designConcept: "",
        designerName: "",
        email: "",
        image: "",
        id: "",
        iphone: "",
        professionalTitle: "",
        gender: "",
        time: "",
        type: "",
      },
      designerRules: {
        designerName: [
          { required: true, message: "请填写姓名", trigger: "blur" },
        ],
        iphone: [{ required: true, message: "请填写电话", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        image: [{ required: true, message: "请上传头像", trigger: "blur" }],
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
    };
  },
  created() {
    let id = window.localStorage.getItem("id");
    this.tag = window.localStorage.getItem("tag");
    if (id) {
      this.isLogin = true;
      if (this.tag === "0") {
        this.getOneUser();
      } else if (this.tag === "1") {
        this.getOneDesigner();
      }
    } else {
      this.isLogin = false;
    }
  },
  methods: {
    dropdownClick(command) {
      switch (command) {
        case "updatePwd":
          this.isUpdatePwd = true;
          break;
        case "userInfo":
          this.getOneUser();
          this.isUpdateUser = true;
          break;
        case "logout":
          this.logout();
          break;
        case "updateDesignerPwd":
          this.isUpdateDesignerPwd = true;
          break;
        case "desigerInfo":
          this.getOneDesigner();
          this.isUpdateDesigner = true;
          break;
      }
    },

    // 修改密码   确认
    updatePwdCheck(formName) {
      let id = window.localStorage.getItem("id");
      let password = this.password;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqUserPasswordUpdate(
            id,
            password.newPassword,
            password.oldPassword
          );
          if (res.status === 200) {
            this.$message({
              message: "修改密码成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdatePwd = false;
          }
          /*************新改******************/
          if (res.status === 400) {
            this.$message({
              message: "原始密码填写错误！",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdatePwd = true;
          }
        } else {
          return false;
        }
      });
    },

    // 修改密码   取消
    updatePwdCancel(formName) {
      this.$refs[formName].resetFields();
      this.isUpdatePwd = false;
    },

    // 获取用户信息
    async getOneUser() {
      let id = window.localStorage.getItem("id");
      let res = await reqUserSelectById(id);
      if (res.status == 200) {
        this.user = res.data;
      }
    },

    // 修改用户信息 确认
    updateUserCheck(formName) {
      let user = this.user;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqUserUpdate(
            user.id,
            user.address,
            user.email,
            user.image,
            user.iphone,
            user.password,
            user.gender,
            user.userName
          );
          if (res.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdateUser = false;
          }
        } else {
          return false;
        }
      });
    },

    /*******************************/
    /**************设计师***********/
    /*******************************/

    // 修改设计师密码   确认
    updateDesignerPwdCheck(formName) {
      let id = window.localStorage.getItem("id");
      let designerPwd = this.designerPwd;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqDesignerPasswordUpdate(
            id,
            designerPwd.newPassword,
            designerPwd.oldPassword
          );
          if (res.status === 200) {
            this.$message({
              message: "修改密码成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdateDesignerPwd = false;
          }
          /*************新改******************/
          if (res.status === 400) {
            this.$message({
              message: "原始密码填写错误！",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdateDesignerPwd = true;
          }
        } else {
          return false;
        }
      });
    },

    // 修改设计师密码   取消
    updateDesignerPwdCancel(formName) {
      this.$refs[formName].resetFields();
      this.isUpdateDesignerPwd = false;
    },

    // 修改设计师信息 点击
    async getOneDesigner() {
      let id = window.localStorage.getItem("id");
      let res = await reqDesignerDetail(id);
      if (res.status == 200) {
        this.designer = res.data;
      }
    },

    // 修改用户信息 确认
    updateDesignerCheck(formName) {
      let designer = this.designer;

      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqDesignerUpdate(
            designer.college,
            designer.designConcept,
            designer.designerName,
            designer.email,
            designer.image,
            designer.id,
            designer.iphone,
            designer.professionalTitle,
            designer.gender,
            designer.time,
            designer.type
          );
          if (res.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isUpdateDesigner = false;
          }
        } else {
          return false;
        }
      });
    },

    // 退出登录
    async logout() {
      let res = await reqLoginOut();
      if (res.status === 200) {

         this.$message({
          message: "退出成功",
        });

        window.localStorage.removeItem("username");
        window.localStorage.removeItem("tag");
        window.localStorage.removeItem("id");
        window.localStorage.removeItem("designerName");
        

        this.isLogin = false;
        //console.log(111, this.isLogin);
        // window.localStorage.clear();
        //  this.$router.replace("/home");
        this.$nextTick(() => {
          this.$router.push({
            path: "/user/goods",
          });
        });
      } else {
        this.$message({
          message: "退出失败",
        });
      }
    },

    // 去登录
    toLogin() {
      this.$router.replace("/login");
    },


    //设计师编辑的时候
    handleAvatarSuccessUpdate(res, file) {
      console.log(res)
      this.designer.image =res.data;
      console.log(  this.designerUpdate.image )
    },
    beforeAvatarUpload(file) {
      return true;
    },

    //用户编辑的时候
    handleAvatarSuccessUpdateUser(res, file) {
      console.log(res)
      this.user.image =res.data;
      console.log(  this.user.image )
    },
    beforeAvatarUpload(file) {
      return true;
    }


  },
};
</script>


<style scoped>

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


.header {
  background: rgb(230, 171, 63);
}

.header .title {
  float: left;
}

.header .setting {
  float: right;
  height: 40px;
}

.login {
  float: right;
  height: 60px;
  line-height: 60px;
  cursor: pointer;
}

.el-main {
  padding: 0 0 20px;
}

/* .menu {
  width: 960px;
  margin: 0 auto;
} */

.main-box{
  flex-direction: column;
} 
</style>