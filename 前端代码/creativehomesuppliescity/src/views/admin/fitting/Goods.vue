<template>
  <div>
    <div style="margin-bottom: 20px">
      <el-button type="warning" icon="el-icon-plus" @click="isAdd = true"
        >添加</el-button
      >

      <div style="margin-top: 20px">
        <span>名称</span>
        <el-input
          v-model="brandName"
          placeholder="请输入品牌名"
          style="width: 150px"
          suffix-icon="el-icon-search"
          @input="getGoods()"
        ></el-input>
        <span style="margin-left: 20px">商品名称</span>
        <el-input
          v-model="goodName"
          placeholder="请输入商品名称"
          style="width: 150px"
          suffix-icon="el-icon-search"
          @input="getGoods()"
        ></el-input>
        <span style="margin-left: 20px">风格名</span>
        <el-input
          v-model="styleName"
          placeholder="请输入风格名"
          style="width: 150px"
          suffix-icon="el-icon-search"
          @input="getGoods()"
        ></el-input>
      </div>
    </div>

    <el-table
      border
      style="width: 100%"
      :header-cell-style="{ background: '#ffd04b', color: 'white' }"
      :data="goods"
    >
      <el-table-column prop="goodName" label="名称"> </el-table-column>
      <el-table-column prop="brand.brandName" label="品牌名称">
      </el-table-column>
      <el-table-column
        prop="categorySecond.categoryName"
        label="二级分类名"
      ></el-table-column>
      <el-table-column label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.image" style="height: 100px; width: 100px; margin: 0 auto" />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="单价"> </el-table-column>
      <el-table-column prop="size" label="尺寸"> </el-table-column>
      <el-table-column prop="store" label="库存"> </el-table-column>
      <el-table-column prop="style.typeName" label="风格名称">
      </el-table-column>
      <el-table-column prop="hitNumber" label="点击量"> </el-table-column>
      <el-table-column prop="description" label="描述" width="300">
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
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
        :model="newGoods"
        :rules="rules"
        ref="newGoods"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="名称" prop="goodName">
              <el-input size="small" v-model="newGoods.goodName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌名称" prop="brandName">
              <el-select v-model="newGoods.brandName" type="small">
                <el-option
                  v-for="(brand, index) in brands"
                  :key="index"
                  :label="brand.brandName"
                  :value="brand.brandName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类名称" prop="categorySecondName">
              <el-select v-model="newGoods.categorySecondName" type="small">
                <el-option
                  v-for="(categorySecond, index) in categorySeconds"
                  :key="index"
                  :label="categorySecond.categoryName"
                  :value="categorySecond.categoryName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="图片" prop="image">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="newGoods.image" :src="newGoods.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单价" prop="price">
              <el-input
                type="number"
                size="small"
                v-model="newGoods.price"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="尺寸" prop="size">
              <el-input size="small" v-model="newGoods.size"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="newGoods.description"></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风格名" prop="styleName">
              <el-select v-model="newGoods.styleName" type="small">
                <el-option
                  v-for="(style, index) in styles"
                  :key="index"
                  :label="style.typeName"
                  :value="style.typeName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存" prop="store">
              <el-input v-model="newGoods.store"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('newGoods')">取 消</el-button>
        <el-button type="primary" @click="addCheck('newGoods')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
      <el-form
        :model="goodsUpdate"
        :rules="rules"
        ref="goodsUpdate"
        label-width="90px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="名称" prop="goodName">
              <el-input size="small" v-model="goodsUpdate.goodName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌名称" prop="brandName">
              <el-select v-model="goodsUpdate.brandName" type="small">
                <el-option
                  v-for="(brand, index) in brands"
                  :key="index"
                  :label="brand.brandName"
                  :value="brand.brandName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">

          <el-col :span="12">
            <el-form-item label="分类名称" prop="categorySecondName">
              <el-select v-model="goodsUpdate.categorySecondName" type="small">
                <el-option
                  v-for="(categorySecond, index) in categorySeconds"
                  :key="index"
                  :label="categorySecond.categoryName"
                  :value="categorySecond.categoryName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="图片" prop="image">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/creativehomesuppliescity/uploadFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccessUpdate"
                :before-upload="beforeAvatarUpload">
                <img v-if="goodsUpdate.image" :src="goodsUpdate.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单价" prop="price">
              <el-input size="small" v-model="goodsUpdate.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="尺寸" prop="size">
              <el-input size="small" v-model="goodsUpdate.size"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="描述" prop="description">
          <el-input
            type="textarea"
            v-model="goodsUpdate.description"
          ></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风格" prop="styleName">
              <el-select v-model="goodsUpdate.styleName" type="small">
                <el-option
                  v-for="(style, index) in styles"
                  :key="index"
                  :label="style.typeName"
                  :value="style.typeName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存" prop="store">
              <el-input v-model="goodsUpdate.store"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('goodsUpdate')"
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
  </div>
</template>

<script>
import {
  reqBrandList,
  reqCategorySecondList,
  reqGoodsAdd,
  reqGoodsDelete,
  reqGoodsListWithSomeDemand,
  reqGoodsUpdate,
  reqStyleList,
} from "../../../api";
export default {
  data() {
    return {
      isAdd: false,
      isUpdate: false,
      isDelete: false,

      id: "",

      goods: [],
      brands: [],
      categorySeconds: [],
      styles: [],

      pageNum: 1,
      brandName: "",
      categoryFirstName: "",
      categorySecondName: "",
      goodName: "",
      styleName: "",

      rules: {
        goodName: [{ required: true, message: "请填写名称", trigger: "blur" }],
        brandName: [
          { required: true, message: "请填写品牌名称", trigger: "change" },
        ],
        categorySecondName: [
          {
            required: true,
            message: "请填写所属二级分类名称",
            trigger: "change",
          },
        ],
        image: [{ required: true, message: "请上传图片", trigger: "change" }],
        price: [{ required: true, message: "请填写单价", trigger: "blur" }],
        size: [{ required: true, message: "请填写尺寸", trigger: "blur" }],
        store: [{ required: true, message: "请填写库存", trigger: "blur" }],
        styleName: [
          { required: true, message: "请所属风格名称", trigger: "change" },
        ],
        description: [
          { required: true, message: "请填写描述", trigger: "blur" },
        ],
      },

      newGoods: {
        goodName: "",
        brandName: "",
        categorySecondName: "",
        image: "",
        price: "",
        size: "",
        store: "",
        styleName: "",
        description: "",
      },

      goodsUpdate: {
        goodName: "",
        brandName: "",
        categorySecondName: "",
        image: "",
        price: "",
        size: "",
        store: "",
        styleName: "",
        description: "",
        id: "",
      },
    };
  },
  created() {
    this.getGoods();
    this.getBrands();
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
    },

    // 添加   确认
    addCheck(formName) {
      let newGoods = this.newGoods;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqGoodsAdd(
            newGoods.brandName,
            newGoods.categorySecondName,
            newGoods.description,
            newGoods.image,
            newGoods.goodName,
            newGoods.price,
            newGoods.size,
            newGoods.store,
            newGoods.styleName
          );
          if (result.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isAdd = false;
            this.getGoods();
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
      this.goodsUpdate = row;
      this.goodsUpdate.brandName = row.brand.brandName;
      this.goodsUpdate.styleName = row.style.typeName;
      this.goodsUpdate.categorySecondName = row.categorySecond.categoryName;
    },

    // 编辑   确认
    updateCheck(formName) {
      let goodsUpdate = this.goodsUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqGoodsUpdate(
            goodsUpdate.brandName,
            goodsUpdate.categorySecondName,
            goodsUpdate.description,
            goodsUpdate.image,
            goodsUpdate.goodName,
            goodsUpdate.id,
            goodsUpdate.price,
            goodsUpdate.size,
            goodsUpdate.store,
            goodsUpdate.styleName
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getGoods();
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
      let result = await reqGoodsDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getGoods();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
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
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
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
    },

    // 获取品牌
    async getBrands() {
      let res = await reqBrandList();
      if (res.status === 200) {
        this.brands = res.data;
      }
    },

    // 获取二级分类
    async getCategorySeconds() {
      let res = await reqCategorySecondList();
      if (res.status === 200) {
        this.categorySeconds = res.data;
      }
    },

    // 获取风格
    async getStyles() {
      let res = await reqStyleList();
      if (res.status === 200) {
        this.styles = res.data;
      }
    },

    //上传图片  添加的时候
    handleAvatarSuccess(res, file) {
        console.log(res)
        this.newGoods.image =res.data;
        console.log(  this.newGoods.image )
      },
    beforeAvatarUpload(file) {
      return true;
    },

    //上传图片  添加的时候编辑
    handleAvatarSuccessUpdate(res, file) {
        console.log(res)
        this.goodsUpdate.image =res.data;
        console.log(  this.goodsUpdate.image )
      },
    beforeAvatarUpload(file) {
      return true;
    }




  },
};
</script>
<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>