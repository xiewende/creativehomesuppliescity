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
      :data="brands"
    >
      <el-table-column prop="brandName" label="品牌名"> </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column prop="designer.designerName" label="设计师名">
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
        :model="newBrand"
        :rules="rules"
        ref="newBrand"
        label-width="90px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="品牌名" prop="brandName">
              <el-input size="small" v-model="newBrand.brandName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设计师名" prop="designerName">
              <el-select
                v-model="newBrand.designerName"
                placeholder="请选择设计师名"
                size="small"
              >
                <el-option
                  v-for="(designer, index) in designers"
                  :key="index"
                  :label="designer.designerName"
                  :value="designer.designerName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="描述" prop="description">
          <el-input
            size="small"
            type="textarea"
            v-model="newBrand.description"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('newBrand')">取 消</el-button>
        <el-button type="primary" @click="addCheck('newBrand')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="isUpdate" width="40%">
      <el-form
        :model="brandUpdate"
        :rules="rules"
        ref="brandUpdate"
        label-width="90px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="brandName">
              <el-input size="small" v-model="brandUpdate.brandName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设计师名" prop="designerName">
              <el-select
                v-model="brandUpdate.designerName"
                size="small"
                @change="getDesignerNameChange"
              >
                <el-option
                  v-for="(designer, index) in designers"
                  :key="index"
                  :label="designer.designerName"
                  :value="designer.designerName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="描述" prop="description">
          <el-input
            size="small"
            type="textarea"
            v-model="brandUpdate.description"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isUpdate = false">取 消</el-button>
        <el-button type="primary" @click="updateCheck('brandUpdate')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog title="删除" :visible.sync="isDelete" width="40%">
      <span>确定删除该品牌信息吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCheck()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  reqBrandDelete,
  reqBrandAdd,
  reqBrandListByPage,
  reqBrandUpdate,
  reqDesignerList,
} from "../../api";
export default {
  data() {
    return {
      isAdd: false,
      isUpdate: false,
      isDelete: false,

      id: "",
      pageNum: 1,
      designers: [],

      brands: [],

      rules: {
        brandName: [
          { required: true, message: "请填写品牌名", trigger: "blur" },
        ],
        description: [
          { required: true, message: "请填写描述", trigger: "blur" },
        ],
        designerName: [
          { required: true, message: "请选择设计师名", trigger: "change" },
        ],
      },

      newBrand: {
        brandName: "",
        description: "",
        designerName: "",
      },

      brandUpdate: {
        brandName: "",
        description: "",
        designerName: "",
        id: "",
      },
    };
  },
  mounted() {
    this.getBrands();
    this.getDesigners();
  },
  methods: {
    // 查询全部品牌（分页，供展示的时候）
    async getBrands() {
      let result = await reqBrandListByPage(this.pageNum);
      if (result.status === 200) {
        this.brands = result.data.list;
      }
    },

    // 添加   确认
    addCheck(formName) {
      let newBrand = this.newBrand;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqBrandAdd(
            newBrand.brandName,
            newBrand.description,
            newBrand.designerName
          );
          if (result.status === 200) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.getBrands();
            this.$refs[formName].resetFields();
            this.isAdd = false;
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
      this.brandUpdate = row;
      this.brandUpdate.designerName = row.designer.designerName
    },

    getDesignerNameChange(val){
      this.brandUpdate.designerName = val
    },

    // 编辑   确认
    updateCheck(formName) {
      let brandUpdate = this.brandUpdate;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqBrandUpdate(
            brandUpdate.brandName,
            brandUpdate.description,
            brandUpdate.designerName,
            brandUpdate.id
          );
          if (result.status === 200) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
            this.isUpdate = false;
            this.getBrands();
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
      let result = await reqBrandDelete(this.id);
      if (result.status === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.isDelete = false;
        this.getBrands();
      }
    },

    // 上一页
    async prePage() {
      this.pageNum -= 1;
      let result = await reqBrandListByPage(this.pageNum);
      if (result.status === 200) {
        this.brands = result.data.list;
      }
    },

    // 下一页
    async nextPage() {
      this.pageNum += 1;
      let result = await reqBrandListByPage(this.pageNum);
      if (result.status === 200) {
        this.brands = result.data.list;
      }
    },

    // 获取设计师
    async getDesigners() {
      let res = await reqDesignerList("", "");
      if (res.status === 200) {
        this.designers = res.data;
      }
    },
  },
};
</script>
