<template>
  <div>
    <div class="container">
      <div class="login">
        <p class="title">登 录</p>
        <el-form
          :model="user"
          :rules="rules"
          ref="user"
          label-width="70px"
          label-position="left"
        >
          <el-form-item label="用户名" prop="name">
            <el-input v-model="user.name"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="tag">
            <el-radio-group v-model="user.tag">
              <el-radio label="0">普通用户</el-radio>
              <el-radio label="1">设计师</el-radio>
              <el-radio label="2">管理员</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="校验码" prop="checkCode">
            <el-input v-model="user.checkCode" style="width: 100px"></el-input>
            <canvas id="canvas" @click="check"></canvas>
          </el-form-item>
          <el-form-item>
            <el-button @click="resetForm('user')">重置</el-button>
             <el-button @click="goToHome('user')">商城首页</el-button>
            <el-button
              type="primary"
              @click="loginClick('user')"
              style="float: right"
              >登录</el-button
            >
          </el-form-item>
        </el-form>
        <router-link to="/register">
          <el-link type="warning" style="float: right">注 册</el-link>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { reqLogin } from "../api";
export default {
  data() {
    var checkCode = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入校验码"));
      } else if (value !== this.show_num.join("")) {
        callback(new Error("验证码输入错误"));
      } else {
        callback();
      }
    };
    return {
      user: {
        name: "",
        password: "",
        tag: "",
        checkCode: "",
      },
      show_num: [],
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        tag: [{ required: true, message: "请选择角色", trigger: "change" }],
        checkCode: [{ validator: checkCode, trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.check();
  },
  methods: {
    goToHome(){
        this.$router.push("/user/goods");
    },
    loginClick(formName) {
      let user = this.user;

      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqLogin(user.name, user.password, user.tag);
          if (result.status === 200) {
            if (user.tag === "0") {
              this.$router.push("/user/goods");
              window.localStorage.setItem("username", result.data.userName);
            } else if(user.tag === "1") {
              this.$router.push("/designer/scheme");
              window.localStorage.setItem(
                "designerName",
                result.data.designerName
              );
            }else{
              this.$router.push("/admin/user")
            }
            window.localStorage.setItem("tag", user.tag);
            window.localStorage.setItem("id", result.data.id);
          } else {
            this.$message.error(result.msg);
          }
        } else {
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    check() {
      var canvas_width = document.getElementById("canvas").clientWidth;
      var canvas_height = document.getElementById("canvas").clientHeight;
      var canvas = document.getElementById("canvas"); //获取到canvas的对象
      var context = canvas.getContext("2d");
      canvas.width = canvas_width;
      canvas.height = canvas_height;
      var sCode =
        "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0,q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m";
      var aCode = sCode.split(",");
      var aLength = aCode.length; //获取到数组的长度
      this.show_num = [];

      for (var i = 0; i <= 3; i++) {
        var j = Math.floor(Math.random() * aLength); //获取到随机的索引值
        var deg = (Math.random() * 30 * Math.PI) / 180; //产生0~30之间的随机弧度
        var txt = aCode[j]; //得到随机的一个内容
        this.show_num[i] = txt;
        var x = 10 + i * 20; //文字在canvas上的x坐标
        var y = 20 + Math.random() * 8; //文字在canvas上的y坐标
        context.font = "bold 23px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = this.randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
      }
      //验证码上显示线条
      for (var i = 0; i <= 5; i++) {
        context.strokeStyle = this.randomColor();
        context.beginPath();
        context.moveTo(
          Math.random() * canvas_width,
          Math.random() * canvas_height
        );
        context.lineTo(
          Math.random() * canvas_width,
          Math.random() * canvas_height
        );
        context.stroke();
      }
      //验证码上显示小点
      for (var i = 0; i <= 30; i++) {
        context.strokeStyle = this.randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
      }
    },

    randomColor() {
      //得到随机的颜色值
      var r = Math.floor(Math.random() * 256);
      var g = Math.floor(Math.random() * 256);
      var b = Math.floor(Math.random() * 256);
      return "rgb(" + r + "," + g + "," + b + ")";
    },
  },
};
</script>

<style scoped>
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

.login {
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

#canvas {
  width: 100px;
  height: 38px;
  vertical-align: middle;
  background-color: white;
  border-radius: 5px;
  margin-left: 8px;
  margin-top: -2px;
}
</style>