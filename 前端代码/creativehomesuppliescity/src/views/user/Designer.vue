<template>
  <div>
    <div class="image">
      <img src="../../assets/bg.png" alt="" />
    </div>
    <div class="container">
      <div v-if="isDetail">
        <div style="margin-bottom: 20px">
          <span>职称</span>
          <el-input
            v-model="professionalTitle"
            placeholder="请输入职称"
            style="width: 200px"
            @input="getDesiners()"
          ></el-input>
        </div>

        <div>
          <el-row :gutter="20">
            <el-col :span="8" v-for="(designer, index) in designers" :key="index">
              <div class="grid-content" @click="designerDetail(designer.id)">
                <el-image
                  style="width: 356px; height: 356px"
                  :src="designer.image"
                ></el-image>
                <div>{{ designer.designerName }}</div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 设计师详情 -->
      <!-- <div class="detail" v-else>

        <el-row :gutter="100">
           <el-col :span="12">
            <div class="grid-content bg-purple">
               <img :src="designer.image"    style="width: 580px; height: 580px"></img>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <div class="margin-bottom">
                <span>姓名：</span>
                <span>{{ designer.designerName }}</span>
              </div>
              <div class="margin-bottom">
                <span>手机号：</span>
                <span>{{ designer.iphone }}</span>
              </div>
              <div class="margin-bottom">
                <span>邮箱：</span>
                <span>{{ designer.email }}</span>
              </div>
              <div class="margin-bottom">
                <span>性别：</span>
                <span>{{ designer.gender === 0 ? "男" : "女" }}</span>
              </div>
              <div class="margin-bottom">
                <span>职称：</span>
                <span>{{ designer.professionalTitle }}</span>
              </div>
              <div class="margin-bottom">
                <span>擅长风格：</span>
                <span>{{ designer.type }}</span>
              </div>
              <div class="margin-bottom">
                <span>设计理念：</span>
                <span>{{ designer.designConcept }}</span>
              </div>
              <div class="margin-bottom">
                <span>从业时间：</span>
                <span>{{ designer.time }}</span>
              </div>
              <div class="margin-bottom">
                <span>毕业院校：</span>
                <span>{{ designer.college }}</span>
              </div>
              <div class="margin-bottom">
                <span>人气：</span>
                <span>{{ designer.popularity }}</span>
              </div>
            </div>
            <div>
              <el-button type="warning" @click="isSchemeAdd = true"
                >找他定制</el-button
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

         <el-row :gutter="100">
          <el-col :span="16">
            <div>
              设计师的设计作品展示：
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="100">
          <el-col :span="16">
            <div>
              <el-table
                border
                style="width: 200%"
                :header-cell-style="{ background: '#ffd04b', color: 'white' }"
                :data="designer.finnishProgramList"
              >
                <el-table-column prop="user.userName" label="普通用户名"></el-table-column>
                <el-table-column prop="goodName" label="商品名"></el-table-column>
                <el-table-column prop="size" label="尺寸"> </el-table-column>
                <el-table-column prop="otherDemand" label="其他要求"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column prop="finishTime" label="完成设计时间"></el-table-column>
                <el-table-column label="图片">
                  <template slot-scope="scope">
                    <img
                      :src="scope.row.finishPic"
                      style="height: 60px; margin: 0 auto"   
                    />
                  </template>
                </el-table-column>
                <el-table-column prop="finisnIdea" label="理念讲述" width="250">
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div> -->

      <div class="detail" v-else>
        <div>
           <el-row :gutter="100">
           <el-col :span="12">
            <div class="grid-content bg-purple">
               <img :src="designer.image"    style="width: 580px; height: 580px"></img>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <div class="margin-bottom">
                <span>姓名：</span>
                <span>{{ designer.designerName }}</span>
              </div>
              <div class="margin-bottom">
                <span>手机号：</span>
                <span>{{ designer.iphone }}</span>
              </div>
              <div class="margin-bottom">
                <span>邮箱：</span>
                <span>{{ designer.email }}</span>
              </div>
              <div class="margin-bottom">
                <span>性别：</span>
                <span>{{ designer.gender === 0 ? "男" : "女" }}</span>
              </div>
              <div class="margin-bottom">
                <span>职称：</span>
                <span>{{ designer.professionalTitle }}</span>
              </div>
              <div class="margin-bottom">
                <span>擅长风格：</span>
                <span>{{ designer.type }}</span>
              </div>
              <div class="margin-bottom">
                <span>设计理念：</span>
                <span>{{ designer.designConcept }}</span>
              </div>
              <div class="margin-bottom">
                <span>从业时间：</span>
                <span>{{ designer.time }}</span>
              </div>
              <div class="margin-bottom">
                <span>毕业院校：</span>
                <span>{{ designer.college }}</span>
              </div>
              <div class="margin-bottom">
                <span>人气：</span>
                <span>{{ designer.popularity }}</span>
              </div>
            </div>
            <div>
              <el-button type="warning" @click="isSchemeAdd = true"
                >找他定制</el-button
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

      <div>
         <el-row :gutter="100">
          <el-col :span="16">
            <div>
              设计师的设计作品展示：
            </div>
          </el-col>
        </el-row>
      </div>
    
       <div>
          <el-row :gutter="100">
          <el-col :span="16">
            <div>
              <el-table
                border
                style="width: 100%"
                :header-cell-style="{ background: '#ffd04b', color: 'white' }"
                :data="designer.finnishProgramList"
              >
                <el-table-column prop="user.userName" label="普通用户名"></el-table-column>
                <el-table-column prop="goodName" label="商品名"></el-table-column>
                <el-table-column prop="size" label="尺寸"> </el-table-column>
                <el-table-column prop="otherDemand" label="其他要求"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column prop="finishTime" label="完成设计时间"></el-table-column>
                <el-table-column label="图片">
                  <template slot-scope="scope">
                    <img
                      :src="scope.row.finishPic"
                      style="height: 60px; margin: 0 auto"   
                    />
                  </template>
                </el-table-column>
                <el-table-column prop="finisnIdea" label="理念讲述" width="250">
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div>

      </div>

      

      <!-- 添加个人定制 -->
      <el-dialog title="添加" :visible.sync="isSchemeAdd" width="40%">
        <el-form
          :model="newSchemes"
          :rules="rules"
          ref="newSchemes"
          label-width="90px"
        >
          <el-form-item label="商品名" prop="goodName">
            <el-input size="small" v-model="newSchemes.goodName"></el-input>
          </el-form-item>
          <el-form-item label="尺寸" prop="size">
            <el-input size="small" v-model="newSchemes.size"></el-input>
          </el-form-item>
          <el-form-item label="其它要求" prop="otherDemand">
            <el-input size="small" v-model="newSchemes.otherDemand"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addCancel('newSchemes')">取 消</el-button>
          <el-button type="primary" @click="addCheck('newSchemes')"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  reqDesignerDetail,
  reqDesignerList,
  reqFinnishProgram,
  reqProgramAdd,
} from "../../api";
export default {
  data() {
    return {
      isDetail: true,
      isSchemeAdd: false,

      professionalTitle: "",
      type: "",
      designerName: "",
      pageNum: 1,
      userName: "",

      designers: [],
      designer: {},
      schemes: [],

      newSchemes: {
        goodName: "",
        otherDemand: "",
        size: "",
      },
      rules: {
        goodName: [
          { required: true, message: "请填写商品名", trigger: "blur" },
        ],
        otherDemand: [
          { required: true, message: "请填写其它要求", trigger: "blur" },
        ],
        size: [{ required: true, message: "请填写尺寸", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.getDesiners();
  },
  methods: {
    // 条件查询全部设计师
    async getDesiners() {
      let res = await reqDesignerList(this.professionalTitle, this.type);
      if (res.status === 200) {
        this.designers = res.data;
      }
    },

    // 查询设计师某一位设计师的详情
    async designerDetail(id) {
      let res = await reqDesignerDetail(id);
      if (res.status === 200) {
        this.designer = res.data;
        this.isDetail = false;
      }
    },

    // 添加   确认
    addCheck(formName) {
      let newSchemes = this.newSchemes;
      let designerId = this.designer.id;
      let userId = window.localStorage.getItem("id");
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let result = await reqProgramAdd(
            designerId,
            newSchemes.goodName,
            newSchemes.otherDemand,
            newSchemes.size,
            userId
          );
          if (result.status === 200) {
            this.$message({
              message: "已提交",
              type: "success",
            });
            this.$refs[formName].resetFields();
            this.isSchemeAdd = false;
          } else {
            this.$message.error(result.msg);
          }
        } else {
          this.$message.error("错误");
          return false;
        }
      });
    },

    // 添加   取消
    addCancel(formName) {
      this.$refs[formName].resetFields();
      this.isSchemeAdd = false;
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
  height: 300px;
}

.el-row {
  margin-bottom: 80px;
}

.grid-content {
  min-height: 36px;
  text-align: center;
}

.container {
  width: 1160px;
  margin: 0 auto;
  padding-top: 20px;
}

.col {
  margin-bottom: 20px;
  text-align: center;
}

.margin-bottom {
  margin-bottom: 30px;
}
</style>