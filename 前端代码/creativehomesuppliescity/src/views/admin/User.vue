<template>
  <div>
    <div style="margin-bottom: 20px">
      <span>用户名</span>
      <el-input
        v-model="userName"
        placeholder="请输入用户名"
        suffix-icon="el-icon-search"
        style="width: 200px"
        @input="getUsers()"
      ></el-input>
    </div>

    <el-table
      :data="users"
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
    >
      <el-table-column prop="userName" label="用户名"></el-table-column>
      <el-table-column prop="iphone" label="手机号"></el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          {{ scope.row.sex === 0 ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="头像">
        <template slot-scope="scope">
          <img :src="scope.row.image" style="height: 80px; width: 100px; margin: 0 auto" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
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

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
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
              <el-input
                size="small"
                type="iphone"
                v-model="user.iphone"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="user.gender">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
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

          <!-- <el-col :span="12">
            <el-form-item label="头像" prop="image">
              <el-input size="small" type="file" v-model="user.image"></el-input>
            </el-form-item>
          </el-col> -->

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
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('user')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该条用户信息吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { reqUserDelete, reqUserListOrName, reqUserUpdate } from "../../api";
export default {
  data() {
    return {
      isUpdate: false,
      isDelete: false,

      id: "",
      pageNum: 1,
      userName: "",

      users: [],
      rules: {
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
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },

      user: {
        userName: "",
        iphone: "",
        gender: "",
        email: "",
        address: "",
        image: "",
        password: "",
        id: "",
      },
    };
  },
  mounted() {
    this.getUsers();
  },
  methods: {
    // 按照名字模糊查询用户，若没有输入则查询全部
    async getUsers() {
      let result = await reqUserListOrName(this.pageNum, this.userName);
      if (result.status === 200) {
        this.users = result.data.list;
      }
    },

    // 点击编辑按钮
    updateClick(row) {
      this.isUpdate = true;
      this.user = row;
    },

    // 编辑   确认
    updateCheck(formName) {
      let user = this.user;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqUserUpdate(
            user.id,
            user.address,
            user.email,
            user.image,
            user.iphone,
            user.password,
            user.gender,
            user.userName
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getUsers();
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
      let result = await reqUserDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getUsers();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqUserListOrName(this.pageNum, this.userName);
      if (result.status === 200) {
        this.users = result.data.list;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqUserListOrName(this.pageNum, this.userName);
      if (result.status === 200) {
        this.users = result.data.list;
      }
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