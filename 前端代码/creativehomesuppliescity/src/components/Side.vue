<template>
  <div>
    <!-- 普通用户 -->
    <el-aside width="200px" :style="{ height: '100%' }">
      <el-menu
        default-active="1"
        background-color="#909399"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="height: 100%"
        :router="true"
        mode="horizontal"
      >
        <el-menu-item index="1" route="/user/goods">
          <span>商品</span>
        </el-menu-item>
        <el-menu-item index="2" route="/user/designer">
          <span>设计师</span>
        </el-menu-item>
        <el-menu-item index="3" route="/user/cart">
          <span>购物车</span>
        </el-menu-item>
        <el-menu-item index="4" route="/user/order">
          <span>订单</span>
        </el-menu-item>
        <el-menu-item index="4" route="/user/design">
          <span>个人定制</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 设计师 -->
    <!-- <el-aside width="200px" :style="{ height: '100%' }">
      <el-menu
        default-active="1"
        background-color="#909399"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="height: 100%"
        :router="true"
      >
        <el-menu-item index="1" route="/designer/scheme">
          <span>个人定制</span>
        </el-menu-item>
      </el-menu>
    </el-aside> -->

    <!-- 管理员 -->
    <!-- <el-aside width="200px" :style="{ height: '100%' }">
      <el-menu
        default-active="1"
        background-color="#909399"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="height: 100%"
        :router="true"
      >
        <el-menu-item index="1" route="/admin/user">
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="2" route="/admin/designer">
          <span>设计师管理</span>
        </el-menu-item>
        <el-menu-item index="3" route="/admin/brand">
          <span>品牌管理</span>
        </el-menu-item>
        <el-submenu index="4">
          <template slot="title">
            <span>家居用品管理</span>
          </template>
          <el-menu-item index="4-1" route="/admin/category">
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="4-2" route="/admin/style">
            <span>风格管理</span>
          </el-menu-item>
          <el-menu-item index="4-3" route="/admin/goods">
            <span>商品管理</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="5">
          <template slot="title">
            <span>个人定制</span>
          </template>
          <el-menu-item index="5-1" route="/admin/designscheme">
            <span>设计方案</span>
          </el-menu-item>
          <el-menu-item index="5-1" route="/admin/success">
            <span>成功案例</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="6">
          <template slot="title">
            <span>订单管理</span>
          </template>
          <el-menu-item index="6-1" route="/admin/neworder">
            <span>新订单</span>
          </el-menu-item>
          <el-menu-item index="6-2" route="/admin/allorder">
            <span>全部订单</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside> -->
  </div>
</template>

<script>
export default {};
</script>