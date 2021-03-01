import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import VueAxios from 'vue-axios'
import NutUI from '@nutui/nutui';
import '@nutui/nutui/dist/nutui.css';
import VuePreview from 'vue-preview';
import VueCookie from 'vue-cookie'

// reset.scss
import './assets/style/global.scss'
Vue.use(VueCookie)
Vue.use(ElementUI);
Vue.use(NutUI)
axios.create({
  baseURL: 'http://172.19.0.113:8888/platform-admin/ad/workorder/'
})
Vue.use(VueAxios,axios);
Vue.use(VuePreview)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
