import Vue from 'vue'
import Router from 'vue-router'
import {Message} from "element-ui";
let Base64 = require('js-base64').Base64

Vue.use(Router)
console.log(process.env.BASE_URL)
const router = new Router({
    base: process.env.BASE_URL,
    mode: 'history',
    routes: [{
        path: '/deviceinfo',
        name: 'deviceinfo',
        component: () => import('./views/DeviceInfo/DeviceInfo.vue')
    },
        {
            path: '/orderDetail',
            name: 'orderDetail',
            meta: {
                // keepAlive: true,
                title: '工单详情'
            },
            component: () => import('./views/OrderDetail/orderDetail.vue')
        },
        {
            path: '/orderQuery',
            name: 'orderQuery',
            meta: {
                // keepAlive: true,
                title: '工单列表'
            },
            component: () => import('./views/OrderQuery/OrderQuery.vue')
        },
        {
            path: '/',
            name: 'orderMenu',
            meta: {
                // keepAlive: true,
                title: '工单管理系统菜单'
            },
            component: () => import('./views/OrderMenu/orderMenu.vue')
        },
        {
            path: '/numbersearch',
            name: 'numbersearch',
            component: () => import('./views/NumberSearch/NumberSearch.vue')
        },

      {
        path: '/FirstPage',
        name: 'FirstPage',
        component: () => import('./views/FirstPage/FirstPage.vue')
    },
    {
      path: '/MyOrder',
      name: 'MyOrder',
      component: () => import('./views/MyOrder/MyOrder.vue')
  },
  {
    path: '/MoneyManage',
    name: 'MoneyManage',
    component: () => import('./views/MoneyManage/MoneyManage.vue')
},
{
  path: '/WaitingOrder',
  name: 'WaitingOrder',
  component: () => import('./views/WaitingOrder/WaitingOrder.vue')
},
      {
        path: '/WillStartA',
        name: 'WillStartA',//待开始
        component: () => import('./views/WillStartA/WillStartA.vue')
    },
    {
      path: '/DoingA',
      name: 'DoingA',//进行中
      component: () => import('./views/DoingA/DoingA.vue')
  },
  {
    path: '/RemovedB',//被移除
    name: 'RemovedB',
    component: () => import('./views/RemovedB/RemovedB.vue')
},
  {
    path: '/DoingB',
    name: 'DoingB',
    component: () => import('./views/DoingB/DoingB.vue')
},
  {
    path: '/FinishedA',//已完结
    name: 'FinishedA',
    component: () => import('./views/FinishedA/FinishedA.vue')
},
      {
        path: '/CreateOrder',//创建工单
        name: 'CreateOrder',
        component: () => import('./views/CreateOrder/CreateOrder.vue')
    },

  {
    path: '/WaitTakeB',//待领取
    name: 'WaitTakeB',
    component: () => import('./views/WaitTakeB/WaitTakeB.vue')
},

  {
    path: '/ChoosePeople',
    name: 'ChoosePeople',
    component: () => import('./views/ChoosePeople/ChoosePeople.vue')
},
{
  path: '/ChoosePerson',
  name: 'ChoosePerson',
  component: () => import('./views/ChoosePerson/ChoosePerson.vue')
},
    {
      path: '/Details',
      name: 'Details',
      component: () => import('./views/Details/Details.vue')
  },
  {
    path: '/Details/Children',
    name: 'Edit',
    component: () => import('./views/Details/Children/Edit.vue')
},
{
  path: '/Details/Children',
  name: 'UnEdit',
  component: () => import('./views/Details/Children/UnEdit.vue')
},
{
  path: '/errorMessage',
  name: 'errorMessage',
  component: () => import('./views/errorMessage/errorMessage.vue')
},
    ]
})

// 路由导航守卫
// router.beforeEach((to, from, next) => {
//   if (to.meta.title) {
//     document.title = to.meta.title
//   }
//   // 如果session和query都没有token，需要去授权
//   let token = sessionStorage.getItem('wxtoken') || ''
//   // 通过url的token来注册
//   if (token === '' && !to.query.token) {
//     // 去授权
//     let url =
//         process.env.PROXY_API_URL +
//         escape('http://172.19.0.160:8888/platform-admin/' + to.fullPath)
//       //http://bysystem2.xinglian.info
//     // window.location.href = process.env.VUE_APP_AUTHORIZEURL + uri
//     window.location.replace(url)
//   } else {
//     if (token) {
//       // 说明已经有了token，可以直接过去了
//       if ((to.name === 'information' || to.name === 'appointmentVerification'|| to.name === 'appointmentDetails') && !to.query.token) {
//         window.location.href =
//             process.env.PROXY_API_URL + to.fullPath + '&token=' + token
//       } else {
//         next()
//       }
//     } else {
//       // 说明第一次进来，session中没有token
//       token = to.query.token
//       if (!token || !/\S/.test(token)) {
//         Message({
//           message: '用户未注册或未激活（没有token）',
//           type: 'error'
//         })
//       } else {
//         // 第一次拿着token进来要做的事情
//         let userIdToken = token.slice(
//             token.indexOf('.') + 1,
//             token.lastIndexOf('.')
//         )
//         //拿到userId和token
//         let data = JSON.parse(Base64.decode(userIdToken))
//         sessionStorage.setItem('wxtoken', token, 30 * 60);
//         sessionStorage.setItem('systemUserId', data.systemUserId);
//         next()
//       }
//     }
//   }
// })
export default router
