<template>
  <div class="container">
    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="carts"
      @selection-change="handleSelectionChange"
      ref="multipleTable"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="goods.goodName" label="名称"> </el-table-column>
      <el-table-column label="图片">
        <template slot-scope="scope">
          <img
            :src="scope.row.goods.image"
            style="height: 100px; width: 100px; margin: 0 auto"
          />
        </template>
      </el-table-column>
      <el-table-column prop="goods.price" label="单价"> </el-table-column>
      <el-table-column prop="num" label="数量"> </el-table-column>
      <el-table-column prop="goods.size" label="尺寸"> </el-table-column>
      <el-table-column prop="goods.description" label="描述" width="250">
      </el-table-column>
      <el-table-column prop="totalPrice" label="总价"> </el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="updateClick(scope.row)"
            >修改数量</el-button
          >
          <el-button size="mini" type="danger" @click="deleteClick(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-button type="primary" @click="orderAddClick()">下单</el-button>
      <!-- 分页 -->
      <div style="float: right">
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

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
      <el-form
        :model="cartUpdate"
        :rules="rules"
        ref="cartUpdate"
        label-width="90px"
      >
        <el-form-item label="数量" prop="num">
          <el-input
            size="small"
            type="number"
            v-model="cartUpdate.num"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('cartUpdate')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该条信息吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 下单 -->
    <el-dialog title="下单" :visible.sync="isOrder" width="40%">
      <el-form
        :model="orderAdd"
        :rules="rules"
        ref="orderAdd"
        label-width="90px"
      >
        <el-form-item label="地址" prop="address">
          <el-input
            size="small"
            type="textarea"
            v-model="orderAdd.address"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="iphone">
          <el-input size="small" v-model="orderAdd.iphone"></el-input>
        </el-form-item>
        <el-form-item label="支付方式" prop="payType">
          <el-input size="small" v-model="orderAdd.payType"></el-input>
        </el-form-item>
        <el-form-item label="收件人名" prop="userName">
          <el-input size="small" v-model="orderAdd.userName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isOrder = false">取 消</el-button>
        <el-button type="primary" @click="orderAddCheck('orderAdd')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  reqOrderAdd,
  reqOrderCartDelete,
  reqOrderCartList,
  reqOrderCartNumUpdate,
  reqOrderDelete,
  reqUserSelectById,
} from "../../api";
export default {
  data() {
    return {
      isUpdate: false,
      isDelete: false,
      isOrder: false,

      id: "",
      pageNum: 1,

      carts: [],
      rules: {
        num: [{ required: true, message: "请填写商品的数量", trigger: "blur" }],
        address: [{ required: true, message: "请填写地址", trigger: "blur" }],
        iphone: [{ required: true, message: "请填写手机号", trigger: "blur" }],
        payType: [
          { required: true, message: "请填写支付方式", trigger: "blur" },
        ],
        userName: [
          { required: true, message: "请填写收件人名", trigger: "blur" },
        ],
      },
      cartUpdate: {
        num: "",
        id: "",
      },

      multipleSelection: [],
      orderAdd: {
        address: "",
        iphone: "",
        payType: "",
        userName: "",
      },
    };
  },
  created() {
    this.getCarts();
  },
  methods: {
    // 查询全部购物车
    async getCarts() {
      let userId = window.localStorage.getItem("id");
      let res = await reqOrderCartList(this.pageNum, userId);
      if (res.status === 200) {
        this.carts = res.data.list;
      }
    },

    // 点击编辑按钮
    updateClick(row) {
      this.isUpdate = true;
      this.cartUpdate = row;
    },

    // 编辑   确认
    updateCheck(formName) {
      let cartUpdate = this.cartUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqOrderCartNumUpdate(
            cartUpdate.id,
            cartUpdate.num
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getCarts();
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
      let result = await reqOrderCartDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getCarts();
      }
    },

    // 选中
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 点击 下单
    orderAddClick() {
      this.isOrder = true;
      this.getOneUser()
    },

    // 下单 确认
    async orderAddCheck(formName) {
      let idList = [];
      let len = this.multipleSelection.length;
      for (let i = 0; i < len; i++) {
        idList.push(this.multipleSelection[i].id);
      }
      
      let idListString = idList.join(",")
      let orderAdd = this.orderAdd;
      let userId = window.localStorage.getItem("id");
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let res = await reqOrderAdd(
            orderAdd.address,
            idListString,
            orderAdd.iphone,
            orderAdd.payType,
            orderAdd.userName,
            userId
          );
          if (res.status === 200) {
            this.getCarts();
            this.isOrder = false;
            this.$message({
              message: "下单成功",
              type: "success",
            });
          }else{
            this.$message.error(res.msg)
          }
        } else {
          return false;
        }
      });
    },

    // 上一页
    prePage() {
      this.pageNum -= 1;
      this.getCarts();
    },

    // 下一页
    nextPage() {
      this.pageNum += 1;
      this.getCarts();
    },

    // 根据id查询某一位用户
    async getOneUser() {
      let id = window.localStorage.getItem("id");
      let res = await reqUserSelectById(id);
      if (res.status === 200) {
        this.orderAdd = res.data;
      }
    },
  },
};
</script>

<style scroped>
.container {
  margin: 20px;
}
</style>