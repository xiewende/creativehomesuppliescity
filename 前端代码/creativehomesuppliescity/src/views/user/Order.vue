<template>
  <div class="container">
    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="orders"
    >
      <el-table-column prop="orderNumber" label="订单编号"> </el-table-column>
      <el-table-column prop="orderNum" label="商品项数量"> </el-table-column>
      <el-table-column prop="receiveName" label="收件名"> </el-table-column>
      <el-table-column prop="createTimeStr" label="支付时间"> </el-table-column>
      <el-table-column prop="updateTimeStr" label="更新时间"> </el-table-column>
      <el-table-column prop="shipTimeStr" label="发货时间"> </el-table-column>
      <el-table-column prop="receiveTimeStr" label="收货时间">
      </el-table-column>
      <el-table-column prop="payTypeStr" label="支付方式"> </el-table-column>
      <el-table-column prop="payPrice" label="支付价格"> </el-table-column>
      <el-table-column prop="logisticsName" label="物流名称"> </el-table-column>
      <el-table-column prop="lgisticsNumble" label="物流单号">
      </el-table-column>
      <el-table-column prop="statusStr" label="状态"> </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        
         <template slot-scope="scope">
          <span v-if="scope.row.status==1">
            <el-button
            size="mini"
            type="success"
            @click="confirmReceiveGoods(scope.row)"
            >确认收货</el-button
          >
          </span>

           <span v-if="scope.row.status==0" style="margin-top:10px;     display: block;" >
              <el-button
              size="mini"
              type="success"
              >修改订单</el-button
            >
            </span>
           
        </template>

      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { reqOrderConfirmReceiveGoods, reqOrderUserSelect } from "../../api";
export default {
  data() {
    return {
      orders: [],
    };
  },
  mounted() {
    this.getOrders();
  },
  methods: {
    async getOrders() {
      let id = window.localStorage.getItem("id");
      let res = await reqOrderUserSelect(id);
      if (res.status === 200) {
        this.orders = res.data;
      }
    },

    // 确认收货
    async confirmReceiveGoods(row) {
      let res = await reqOrderConfirmReceiveGoods(row.id);
      if (res.status === 200) {
        this.getOrders();
        this.$message({
          message: "确认收货成功",
          type: "success",
        });
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