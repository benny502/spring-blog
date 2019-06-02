import Vue from 'vue'
import View from './View.vue'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import './styles.scss'
import router from './router'

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(View)
}).$mount('#app')
