<template>

  <div>
  <!-- 订单 -->
  <div v-show="pid === 0">
    <div style="margin-bottom: 20px">
      <span>状态名</span>
      <el-input
        v-model="statusName"
        placeholder="请输入状态名"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getAllOrders()"
      ></el-input>
      <span style="margin-left: 30px">下单人名字</span>
      <el-input
        v-model="userName"
        placeholder="请输入下单人名字"
        style="width: 200px"
        suffix-icon="el-icon-search"
        @input="getAllOrders()"
      ></el-input>  
    </div>

    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="allOrders"
    >
      <el-table-column prop="orderNumber" label="订单编号"> </el-table-column>
      <el-table-column prop="orderNum" label="数量"> </el-table-column>
      <el-table-column prop="userName" label="用户名"> </el-table-column>
      <el-table-column prop="address" label="地址"> </el-table-column>
      <el-table-column prop="iphone" label="手机号"> </el-table-column>
      <el-table-column prop="receiveName" label="收件人"> </el-table-column>
      <el-table-column prop="createTimeStr" label="支付时间"> </el-table-column>
      <el-table-column prop="updateTimeStr" label="更新时间"> </el-table-column>
      <el-table-column prop="shipTimeStr" label="发货时间"> </el-table-column>
      <el-table-column prop="receiveTimeStr" label="收货时间"> </el-table-column>
      <el-table-column prop="payTypeStr" label="支付方式"> </el-table-column>
      <el-table-column prop="payPrice" label="支付价格"> </el-table-column>
      <el-table-column prop="logisticsName" label="物流名称"> </el-table-column>
      <el-table-column prop="lgisticsNumble" label="物流单号">
      </el-table-column>
      <el-table-column prop="statusStr" label="状态"> </el-table-column>
       <el-table-column label="操作" width="150" align="center">

        
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="detailClick(scope.row)"
              >详情</el-button
            >
            
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
  </div>

  <!-- 订单详情 -->
  <div  v-show="pid!== 0">
    <div style="margin-bottom: 20px">
      <el-button
        type="primary"
        icon="el-icon-back"
        @click="getAllOrders()"
        >返回</el-button
      >
    </div>

    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="orderDetails"
    >
      <el-table-column prop="goodName" label="家居用品名"> </el-table-column>

      <el-table-column label="图片"  prop="goodsPic">
        <template slot-scope="scope">
         
        <img :src="scope.row.goodsPic" style="height: 80px; width: 100px; margin: 0 auto" >
        </template> 
      </el-table-column>

      <el-table-column prop="goodsPrice" label="商品单价"> </el-table-column>
      <el-table-column prop="goodsNum" label="商品数量"> </el-table-column>
      <el-table-column prop="totalPrice" label="商品总价"> </el-table-column>
      
    </el-table>
  
  </div>

  </div>
</template>

<script>
import {
    reqOrderSelectWithDemand,
    reqOrderDetail,
} from "../../../api";
export default {
  data() {
    return {
      allOrders: [],
      pageNum: 1,
      statusName: "",
      userName: "",

      pid: 0,

      orderDetails: [],

    };
  },
  mounted() {
    this.getAllOrders();
  },
  methods: {
    // 全部订单
    async getAllOrders() {
      this.pid = 0;
      let result = await reqOrderSelectWithDemand(
        this.pageNum,
        this.statusName,
        this.userName
      );
      if (result.status === 200) {
        this.allOrders = result.data.list;
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqOrderSelectWithDemand(
        this.pageNum,
        this.statusName,
        this.userName
      );
      if (result.status === 200) {
        this.allOrders = result.data.list;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqOrderSelectWithDemand(
        this.pageNum,
        this.statusName,
        this.userName
      );
      if (result.status === 200) {
        this.allOrders = result.data.list;
      }
    },

    // 点击订单详情
    detailClick(row) {
      this.pid = row.id;
      this.getOrderDetails();
    },

     // 获取全部订单详情
    async getOrderDetails() {
      let result = await reqOrderDetail(this.pid);
      if (result.status === 200) {
        this.orderDetails = result.data.orderGoodsList;
        console.log(  this.orderDetails)
      }else{
        this.orderDetails = [];
      }
    },

  },
};
</script>