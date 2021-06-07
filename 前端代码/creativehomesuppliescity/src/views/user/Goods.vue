<template>
  <div>
    <div class="image">
      <img src="../../assets/bg.png" alt="" />
    </div>
    <div class="container">
      <div v-if="isDetail">
        <div style="margin-bottom: 20px">
          <div>
            <span>品牌名</span>
            <el-select
              v-model="brandName"
              placeholder="请选择品牌名"
              @change="getGoods()"
            >
              <el-option
                v-for="(brand, index) in brands"
                :key="index"
                :label="brand.brandName"
                :value="brand.brandName"
              ></el-option>
            </el-select>

          <span style="margin-left: 20px">风格名</span>
          <el-select
            v-model="styleName"
            placeholder="请选择风格名"
            @change="getGoods()"
          >
            <el-option
              v-for="(style, index) in styles"
              :key="index"
              :label="style.typeName"
              :value="style.typeName"
            ></el-option>
          </el-select>

          <span style="margin-left: 20px">一级分类</span>
          <el-select
            v-model="categoryFirstName"
            placeholder="请选择一级分类"
            @change="getGoods();getCategorySeconds()"
            
          >
            <el-option
              v-for="(categoryFirst, index) in categoryFirsts"
              :key="index"
              :label="categoryFirst.categoryName"
              :value="categoryFirst.categoryName"
            ></el-option>
          </el-select>

          <span style="margin-left: 20px">二级分类</span>
          <el-select
            v-model="categorySecondName"
            placeholder="请选择二级分类"
            @change="getGoods()"
          >
            <el-option
              v-for="(categorySecond, index) in categorySeconds"
              :key="index"
              :label="categorySecond.categoryName"
              :value="categorySecond.categoryName"
            ></el-option>
          </el-select>

          </div>
        </div>

        <div>
          <el-row :gutter="20" class="row">
            <el-col
              :span="8"
              v-for="(good, index) in goods"
              :key="index"
              class="col"
            >
              <div class="grid-content" @click="goodsDetail(good.id)" >
                <!-- <el-image
                  style="width: 356px; height: 356px"
                  :src="good.image"
                ></el-image> -->
                <img :src="good.image"  style="width: 356px; height: 356px; "></img>
                <div>{{ good.goodName }}</div>
                <div class="price">￥{{ good.price }}</div>
              </div>
            </el-col>
          </el-row>
        </div>

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

      <!-- 设计师详情 -->
      <div class="detail" v-else>
        <el-row :gutter="40">
          <el-col :span="12">
            <div class="grid-content bg-purple">
              <!-- <el-image
                style="width: 580px; height: 580px"
                :url="goodsDet.image"
              ></el-image> -->
               <img :src="goodsDet.image"    style="width: 580px; height: 580px"></img>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content bg-purple-light">
              <div class="margin-bottom title">
                <h4>{{ goodsDet.goodName }}</h4>
              </div>
              <div class="margin-bottom">
                <span>品牌名称：</span>
                <span>{{ goodsDet.brand.brandName }}</span>
              </div>
              <div class="margin-bottom">
                <span>二级分类名：</span>
                <span>{{ goodsDet.categorySecond.categoryName }}</span>
              </div>
              <div
                class="margin-bottom"
                style="font-size: 22px; color: #c10000"
              >
                <span>单价：</span>
                <span>￥{{ goodsDet.price }}</span>
              </div>
              <div class="margin-bottom">
                <span>尺寸：</span>
                <span>{{ goodsDet.size }}</span>
              </div>
              <div class="margin-bottom">
                <span>库存：</span>
                <span style="color: #c10000">{{ goodsDet.store }}</span
                >件
              </div>
              <div class="margin-bottom">
                <span>所属风格名称：</span>
                <span>{{ goodsDet.style.typeName }}</span>
              </div>
              <div class="margin-bottom">
                <span>描述：</span>
                <span>{{ goodsDet.description }}</span>
              </div>
              <div class="margin-bottom">
                <span>商品点击量：</span>
                <span>{{ goodsDet.hitNumber }}</span>
              </div>
            </div>
            <div style="margin-top: 20px">
              <el-input
                v-model="num"
                type="number"
                style="width: 80px"
              ></el-input>
              <el-button type="warning" round @click="addCart()"
                >加入购物车</el-button
              >
              <el-button
                type="primary"
                icon="el-icon-back"
                circle
                @click="isDetail = true"
              ></el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {
  reqBrandList,
  reqCategoryFirstList,
  reqCategorySecondList,
  reqGoodsDetail,
  reqGoodsListWithSomeDemand,
  reqOrderCartAdd,
  reqStyleList,
} from "../../api";

export default {
  data() {
    return {
      isDetail: true,

      goods: [],
      brands: [],
      categoryFirsts: [],
      categorySeconds: [],
      styles: [],

      pageNum: 1,
      brandName: "",
      categoryFirstName: "",
      categorySecondName: "",
      goodName: "",
      styleName: "",

     

      num: 0,

      goodsDet: {
        id: "",
        goodName: "",
        brandName: "",
        categorySecondName: "",
        file: "",
        price: "",
        size: "",
        store: "",
        styleName: "",
        description: "",
      },
    };
  },
  created() {
    this.getGoods();
    this.getBrands();
    this.getCategoryFirsts();
    this.getCategorySeconds();
    this.getStyles();
  },
  methods: {
    // 全部家居用品
    async getGoods() {
      let result = await reqGoodsListWithSomeDemand(
        this.brandName,
        this.categoryFirstName,
        this.categorySecondName,
        this.goodName,
        this.pageNum,
        this.styleName
      );
      if (result.status === 200) {
        this.goods = result.data.list;
      }
      if (result.status === 400) {
        this.goods = [];
      }
    },

    // 商品详情
    async goodsDetail(id) {
      let result = await reqGoodsDetail(id);
      if (result.status === 200) {
        this.goodsDet = result.data;
      }
      this.isDetail = false;
    },

    // 添加购物车
    async addCart() {
      let userId = window.localStorage.getItem("id");
      let res = await reqOrderCartAdd(this.goodsDet.id, this.num, userId);
      if (res.status === 200) {
        this.$message({
          message: "已添加",
          type: "success",
        });
      } else {
        this.$message.error(res.msg);
      }
    },

    // 上一页
    prePage() {
      this.pageNum -= 1;
      this.getGoods();
    },

    // 下一页
    nextPage() {
      this.pageNum += 1;
      this.getGoods();
    },

    // 获取品牌
    async getBrands() {
      let res = await reqBrandList();
      if (res.status === 200) {
        this.brands = res.data;
      }
    },

     // 获取风格
    async getStyles() {
      let res = await reqStyleList();
      if (res.status === 200) {
        this.styles = res.data;
        
      }
    },

    // 获取一级分类
    async getCategoryFirsts() {
      let res = await reqCategoryFirstList();
      if (res.status === 200) {
        this.categoryFirsts = res.data;
        console.info(this.categoryFirsts);
      }
    },

    // 获取二级分类
    async getCategorySeconds() {
      let res = await reqCategorySecondList(this.categoryFirstName);
      if (res.status === 200) {
        this.categorySeconds = res.data;
      }
      if (res.status === 400) {
        this.categorySeconds = [];
      }
    },
  },
};
</script>

<style scoped>
.image {
  margin: 0;
  padding: 0;
  border: 0;
  width: 100%;
  height: 250px;
}

.image img {
  width: 100%;
  height: 230px;
}

.grid-content {
  min-height: 36px;
 
}

.container {
  width: 1160px;
  margin: 0 auto;
}

.col {
  margin-bottom: 20px;
  text-align: center;
}

.col .price {
  padding: 5px;
  color: brown;
  font-weight: bolder;
}

.detail {
  width: 1160px;
  margin: 0 auto;
  margin-top: 20px;
}

.detail .title h4 {
  height: 50px;
  line-height: 50px;
  font-weight: normal;
  font-size: 22px;
}

.margin-bottom {
  margin-bottom: 30px;
}
</style>