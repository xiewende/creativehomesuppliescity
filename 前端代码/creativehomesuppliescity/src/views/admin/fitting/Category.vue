<template>
  <div>
    <!-- 一级分类 -->
    <div v-if="pid === 0">
      <div style="margin-bottom: 20px">
        <el-button type="warning" icon="el-icon-plus" @click="isFirstAdd = true"
          >添加</el-button
        >
      </div>
      <el-table
        border
        style="width: 100%"
        :header-cell-style="{ background: '#ffd04b', color: 'white' }"
        :data="firstCategories"
      >
        <el-table-column prop="categoryName" label="分类名"> </el-table-column>
        <el-table-column label="操作" width="250" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="updateFirstClick(scope.row)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="deleteFirstClick(scope.row)"
              >删除</el-button
            >
            <el-button
              size="mini"
              type="success"
              @click="detailClick(scope.row)"
              >详情</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加 -->
      <el-dialog title="添加" :visible.sync="isFirstAdd" width="40%">
        <el-form
          :model="newFirstCategory"
          :rules="firstRules"
          ref="newFirstCategory"
          label-width="90px"
        >
          <el-form-item label="分类名" prop="categoryName">
            <el-input
              size="small"
              v-model="newFirstCategory.categoryName"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addFirstCancel('newFirstCategory')"
            >取 消</el-button
          >
          <el-button type="primary" @click="addFirstCheck('newFirstCategory')"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <!-- 编辑 -->
      <el-dialog title="编辑" :visible.sync="isFirstUpdate" width="40%">
        <el-form
          :model="firstCategoryUpdate"
          :rules="firstRules"
          ref="firstCategoryUpdate"
          label-width="90px"
        >
          <el-form-item label="分类名" prop="categoryName">
            <el-input
              size="small"
              v-model="firstCategoryUpdate.categoryName"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="isFirstUpdate = false">取 消</el-button>
          <el-button
            type="primary"
            @click="updateFirstCheck('firstCategoryUpdate')"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <!-- 删除 -->
      <el-dialog title="删除" :visible.sync="isFirstDelete" width="40%">
        <span>确定删除该分类吗</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="isFirstDelete = false">取 消</el-button>
          <el-button type="primary" @click="deleteFirstCheck()"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>

    <!-- 二级分类 -->
    <div v-else>
      <div style="margin-bottom: 20px">
        <el-button
          type="primary"
          icon="el-icon-back"
          @click="getFirstCategories()"
          >返回</el-button
        >
        <el-button type="warning" icon="el-icon-plus" @click="isAdd = true"
          >添加</el-button
        >
      </div>

      <el-table
        border
        style="width: 100%"
        :header-cell-style="{ background: '#ffd04b', color: 'white' }"
        :data="catogories"
      >
        <el-table-column prop="categoryName" label="分类名"> </el-table-column>
        <el-table-column label="操作" width="250" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="updateClick(scope.row)"
              >编辑</el-button
            >
            <el-button size="mini" type="danger" @click="deleteClick(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加 -->
      <el-dialog title="添加" :visible.sync="isAdd" width="40%">
        <el-form
          :model="newCategory"
          :rules="rules"
          ref="newCategory"
          label-width="90px"
        >
          <el-form-item label="分类名" prop="categoryName">
            <el-input
              size="small"
              v-model="newCategory.categoryName"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addCancel('newCategory')">取 消</el-button>
          <el-button type="primary" @click="addCheck('newCategory')"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <!-- 编辑 -->
      <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
        <el-form
          :model="categoryUpdate"
          :rules="rules"
          ref="categoryUpdate"
          label-width="90px"
        >
          <el-form-item label="分类名" prop="categoryName">
            <el-input
              size="small"
              v-model="categoryUpdate.categoryName"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="isUpdate = false">取 消</el-button>
          <el-button type="primary" @click="updateCheck('categoryUpdate')"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <!-- 删除 -->
      <el-dialog title="删除" :visible.sync="isDelete" width="40%">
        <span>确定删除该条分类吗</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="isDelete = false">取 消</el-button>
          <el-button type="primary" @click="deleteCheck()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  reqCategoryFirstAdd,
  reqCategoryFirstDelete,
  reqCategoryFirstFindById,
  reqCategoryFirstList,
  reqCategoryFirstUpdate,
  reqCategorySecondAdd,
  reqCategorySecondDelete,
  reqCategorySecondFindById,
  reqCategorySecondList,
  reqCategorySecondListById,
  reqCategorySecondUpdate,
} from "../../../api";
export default {
  data() {
    return {
      //   一级分类
      isFirstAdd: false,
      isFirstUpdate: false,
      isFirstDelete: false,

      pid: 0,
      firstId: "",

      firstCategories: [],
      firstRules: {
        categoryName: [
          { required: true, message: "请填写分类名", trigger: "blur" },
        ],
      },
      newFirstCategory: {
        categoryName: "",
      },
      firstCategoryUpdate: {
        id: "",
        categoryName: "",
      },

      //   二级分类
      isAdd: false,
      isUpdate: false,
      isDelete: false,

      categoryFirstName: "",
      id: "",

      catogories: [],
      rules: {
        categoryName: [
          { required: true, message: "请填写商品名", trigger: "blur" },
        ],
      },
      newCategory: {
        categoryName: "",
      },
      categoryUpdate: {
        id: "",
        categoryName: "",
      },
    };
  },
  mounted() {
    this.getFirstCategories();
  },
  methods: {
    ///////////////////////
    /**     一级分类     */
    ///////////////////////
    // 获取全部一级分类
    async getFirstCategories() {
      this.pid = 0;
      let result = await reqCategoryFirstList();
      if (result.status === 200) {
        this.firstCategories = result.data;
      }
    },

    // 添加   确认
    addFirstCheck(formName) {
      let newFirstCategory = this.newFirstCategory;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqCategoryFirstAdd(newFirstCategory.categoryName);
          if (result.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isFirstAdd = false;
            this.getFirstCategories();
          }
        } else {
          this.$message.error("添加错误");
          return false;
        }
      });
    },

    // 添加   取消
    addFirstCancel(formName) {
      this.$refs[formName].resetFields();
      this.isFirstAdd = false;
    },

    // 点击编辑按钮
    updateFirstClick(row) {
      this.isFirstUpdate = true;
      this.firstCategoryUpdate = row;
    },

    // 编辑   确认
    updateFirstCheck(formName) {
      let firstCategoryUpdate = this.firstCategoryUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqCategoryFirstUpdate(
            firstCategoryUpdate.id,
            firstCategoryUpdate.categoryName
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isFirstUpdate = false;
            this.getFirstCategories();
          }
        } else {
          return false;
        }
      });
    },

    // 点击删除按钮
    deleteFirstClick(row) {
      this.firstId = row.id;
      this.isFirstDelete = true;
    },

    // 删除   确认
    async deleteFirstCheck() {
      let result = await reqCategoryFirstDelete(this.firstId);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isFirstDelete = false;
        this.getFirstCategories();
      }
    },

    // 点击查看
    detailClick(row) {
      this.categoryFirstName = row.categoryName;
      this.pid = row.id;
      this.getCategories();
    },

    ///////////////////////
    /**     二级分类     */
    ///////////////////////
    // 获取全部二级分类
    async getCategories() {
      let result = await reqCategorySecondList(this.pid);
      if (result.status === 200) {
        this.catogories = result.data;
      }else{
        this.catogories = [];
      }
    },

    // 添加   确认
    addCheck(formName) {
      let newCategory = this.newCategory;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqCategorySecondAdd(
            this.categoryFirstName,
            newCategory.categoryName
          );
          if (result.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isAdd = false;
            this.getCategories();
          } else {
            this.$message.error(result.msg);
          }
        } else {
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
      this.categoryUpdate = row;
    },

    // 编辑   确认
    updateCheck(formName) {
      let categoryUpdate = this.categoryUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqCategorySecondUpdate(
            this.categoryFirstName,
            categoryUpdate.categoryName,
            categoryUpdate.id
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getCategories();
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
      let result = await reqCategorySecondDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getCategories();
      }
    },
  },
};
</script>