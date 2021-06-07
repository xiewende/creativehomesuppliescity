<template>
  <div>
    <div style="margin-bottom: 20px">
      <span>设计师名</span>
      <el-input
        v-model="designerName"
        placeholder="请输入设计师名"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getSchemes()"
      ></el-input>
      <span style="margin-left: 30px">用户名</span>
      <el-input
        v-model="userName"
        placeholder="请输入用户名"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getSchemes()"
      ></el-input>
    </div>

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
            style="height: 100px; width: 100px; margin: 0 auto"
          />
        </template>
      </el-table-column>
      <el-table-column prop="finisnIdea" label="理念讲述" width="300"> </el-table-column>
      <el-table-column prop="user.userName" label="用户名"> </el-table-column>
      <el-table-column prop="designer.designerName" label="设计师名"> </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
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

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该条信息吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { reqFinnishProgramDelete, reqFinnishProgram } from "../../../api";
export default {
  data() {
    return {
      isDelete: false,

      designerName: "",
      userName: "",

      id: "",
      pageNum: 1,

      schemes: [],
    };
  },
  mounted() {
    this.getSchemes();
  },
  methods: {
    // 全部成功案例
    async getSchemes() {
      let result = await reqFinnishProgram(
        this.designerName,
        this.pageNum,
        this.userName
      );
      if (result.status === 200) {
        this.schemes = result.data.list;
      }
    },

    // 点击删除按钮
    deleteClick(row) {
      this.id = row.id;
      this.isDelete = true;
    },

    // 删除   确认
    async deleteCheck() {
      let result = await reqFinnishProgramDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getSchemes();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqFinnishProgram(
        this.designerName,
        this.pageNum,
        this.userName
      );
      if (result.status === 200) {
        this.schemes = result.data.list;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqFinnishProgram(
        this.designerName,
        this.pageNum,
        this.userName
      );
      if (result.status === 200) {
        this.schemes = result.data.list;
      }
    },
  },
};
</script>
