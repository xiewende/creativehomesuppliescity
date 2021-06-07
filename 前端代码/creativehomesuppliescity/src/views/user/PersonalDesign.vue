<template>
  <div class="container">
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
            style="height: 100px; margin: 0 auto"
          />
        </template>
      </el-table-column>
      <el-table-column prop="finisnIdea" label="理念讲述"> </el-table-column>
      <el-table-column prop="statusStr" label="状态"> </el-table-column>
      <el-table-column prop="createTimeStr" label="创建日期"> </el-table-column>
      <el-table-column prop="finishTimeStr" label="完成时间"> </el-table-column>
      <el-table-column prop="designer.designerName" label="设计师名">
      </el-table-column>
      <el-table-column prop="isAcceptStr" label="是否接受"> </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        
       
        <template slot-scope="scope">

           <span v-if="scope.row.status==1 && scope.row.isAcceptStr == null">
             <el-button
            size="mini"
            type="success"
            @click="schemeReceive(scope.row)"
            >接受</el-button
          >
          <el-button size="mini" type="danger" @click="schemeReject(scope.row)"
            >拒绝</el-button
          >
          </span>

           <span v-else>
            <el-button size="mini" type="danger" 
            >正在进行中</el-button>
          </span>
           

          
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {
  reqProgramReceive,
  reqProgramReject,
  reqProgramSelectByUserId,
} from "../../api";
export default {
  data() {
    return {
      schemes: [],
    };
  },
  mounted() {
    this.getschemes();
  },
  methods: {
    // 用户查看自己的定制
    async getschemes() {
      let id = window.localStorage.getItem("id");
      let res = await reqProgramSelectByUserId(id);
      if (res.status === 200) {
        this.schemes = res.data;
      }
    },

    // 接受
    async schemeReceive(row) {
      let res = await reqProgramReceive(row.id);
      if (res.status === 200) {
        this.$message({
          message: "已接受",
          type: "success",
        });
        this.getschemes();
      }
    },

    // 拒绝
    async schemeReject(row) {
      let res = await reqProgramReject(row.id);
      if (res.status === 200) {
        this.$message({
          message: "已拒绝",
          type: "success",
        });
        this.getschemes();
      }
    },
  },
};
</script>

<style scoped>
.container {
  margin: 20px;
}
</style>