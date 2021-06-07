<template>
  <div>
    <div style="margin-bottom: 20px">
      <el-button type="warning" icon="el-icon-plus" @click="isAdd = true"
        >添加</el-button
      >
    </div>

    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="styles"
    >
      <el-table-column prop="typeName" label="风格名"> </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
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

    <!-- 添加 -->
    <el-dialog title="添加" :visible.sync="isAdd" width="40%">
      <el-form
        :model="newStyle"
        :rules="rules"
        ref="newStyle"
        label-width="90px"
      >
        <el-form-item label="风格名" prop="typeName">
          <el-input v-model="newStyle.typeName"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="newStyle.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('newStyle')">取 消</el-button>
        <el-button type="primary" @click="addCheck('newStyle')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
      <el-form
        :model="styleUpdate"
        :rules="rules"
        ref="styleUpdate"
        label-width="90px"
      >
        <el-form-item label="风格名" prop="typeName">
          <el-input v-model="styleUpdate.typeName"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            type="textarea"
            v-model="styleUpdate.description"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('styleUpdate')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该条风格吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  reqStyleAdd,
  reqStyleDelete,
  reqStyleList,
  reqStyleListByPage,
  reqStyleUpdate,
} from "../../../api";
export default {
  data() {
    return {
      isAdd: false,
      isUpdate: false,
      isDelete: false,

      id: "",
      pageNum: 1,

      styles: [],

      rules: {
        typeName: [
          { required: true, message: "请填写风格名", trigger: "blur" },
        ],
        description: [
          { required: true, message: "请填写描述", trigger: "blur" },
        ],
      },

      newStyle: {
        typeName: "",
        description: "",
      },

      styleUpdate: {
        typeName: "",
        description: "",
        id: "",
      },
    };
  },
  mounted() {
    this.getStyles();
  },
  methods: {
    // 查询全部风格（分页，供展示的时候）
    async getStyles() {
      let result = await reqStyleList();
      if (result.status === 200) {
        this.styles = result.data;
      }
    },

    // 添加   确认
    addCheck(formName) {
      let newStyle = this.newStyle;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqStyleAdd(newStyle.description, newStyle.typeName);
          if (res.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isAdd = false;
            this.getStyles();
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
      this.styleUpdate = row;
      console.log(row);
    },

    // 编辑   确认
    updateCheck(formName) {
      let styleUpdate = this.styleUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqStyleUpdate(
            styleUpdate.description,
            styleUpdate.id,
            styleUpdate.typeName
          );
          if (res.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getStyles();
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
      let res = await reqStyleDelete(this.id);
      if (res.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getStyles();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqStyleListByPage(this.pageNum);
      if (result.status === 200) {
        this.styles = result.data;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqDesignerListByPage(this.pageNum);
      if (result.status === 200) {
        this.styles = result.data;
      }
    },
  },
};
</script>