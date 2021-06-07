import Vue from 'vue'
import Router from 'vue-router'

import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'

// 普通用户
import Designer from '../views/user/Designer.vue'
import Goods from '../views/user/Goods.vue'
import Cart from '../views/user/Cart.vue'
import Order from '../views/user/Order.vue'
import PersonalDesign from '../views/user/PersonalDesign.vue'

// 设计师
import AllScheme from '../views/designer/AllScheme.vue'

// 管理员
import UserManage from '../views/admin/User.vue'
import DesignerManage from '../views/admin/Designer.vue'
import BrandManage from '../views/admin/Brand.vue'
import CategoryManage from '../views/admin/fitting/Category.vue'
import StyleManage from '../views/admin/fitting/Style.vue'
import GoodsManage from '../views/admin/fitting/Goods.vue'
import NewOrder from '../views/admin/order/NewOrder.vue'
import AllOrders from '../views/admin/order/AllOrders.vue'
import DesignScheme from '../views/admin/personal/DesignScheme.vue'
import SuccessScheme from '../views/admin/personal/SuccessScheme.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/home',
      component: Home,
      children: [
        {
          path: "/",
          redirect: '/user/goods'
        },
        {
          path: '/user/designer',
          component: Designer
        },
        {
          path: '/user/goods',
          component: Goods
        },
        {
          path: '/user/cart',
          component: Cart
        },
        {
          path: '/user/order',
          component: Order
        },
        {
          path: '/user/design',
          component: PersonalDesign
        },
        {
          path: '/designer/scheme',
          component: AllScheme
        },
        {
          path: '/admin/user',
          component: UserManage
        },
        {
          path: '/admin/designer',
          component: DesignerManage
        },
        {
          path: '/admin/brand',
          component: BrandManage
        },
        {
          path: '/admin/category',
          component: CategoryManage
        },
        {
          path: '/admin/style',
          component: StyleManage
        },
        {
          path: '/admin/goods',
          component: GoodsManage
        },
        {
          path: '/admin/neworder',
          component: NewOrder
        },
        {
          path: '/admin/allorder',
          component: AllOrders
        },
        {
          path: '/admin/designscheme',
          component: DesignScheme
        },
        {
          path: '/admin/success',
          component: SuccessScheme
        },
      ]
    }
  ]
})
