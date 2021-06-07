// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'

import App from './App'
import router from './router'

axios.defaults.baseURL = 'http://localhost:8080/creativehomesuppliescity/'
Vue.config.productionTip = false  // 阻止启动生产消息
Vue.prototype.axios = axios

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
