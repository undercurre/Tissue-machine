<template>
  <nav class="site-navbar mobileFull" :class="'site-navbar--' + navbarLayoutType">
<!--    <div class="site-navbar__header" v-if="!sidebarFold">-->
<!--      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">-->
<!--        <a class="site-navbar__brand-lg" href="javascript:;">纸巾机管理平台</a>-->
<!--      </h1>-->
<!--    </div>-->
    <div class="site-navbar__body clearfix">
      <div class="leaderTop">菜单导航</div>
      <div class="navBar" @click="menuClick">
        <img
          class="topImg"
          src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backNavigation3xZpq.png"
          alt=""
        />
      </div>
    </div>
    <div class="mask" v-show="menuVisible">
      <el-menu
        class="el-menu-vertical-demo"
        :default-active="menuActiveName || 'home'"
        background-color="#082F40"
        text-color="#fff"
        unique-opened
        active-text-color="#ffd04b">
        <el-menu-item index="home" @click.native="jumpToMenu(1)">
          <span slot="title">首页</span>
        </el-menu-item>
        <sub-menu
          v-for="menu in menuList"
          :key="menu.menuId"
          :menu="menu"
          :dynamicMenuRoutes="dynamicMenuRoutes">
        </sub-menu>
        <el-menu-item index="5" @click.native="logoutHandle()">
          <span slot="title">退出登录</span>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 退出登录 -->
    <el-dialog
      :close-on-click-modal=false
      title="提示"
      class="logoutPart"
      :show-close="false"
      :visible.sync="logoutVisible"
      :append-to-body="true">
      <p>是否确定退出登录？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="loginClose">取消</el-button>
        <el-button type="success" @click="makesureHandle">确定</el-button>
      </span>
    </el-dialog>
  </nav>
</template>

<script>
import { clearLoginInfo } from '@/utils'
import SockJS from 'sockjs-client'
import SubMenu from './main-sidebar-sub-menu'
import { isURL } from '@/utils/validate'
export default {
  data () {
    return {
      menuVisible: false,
      logoutVisible: false,
      updatePassowrdVisible: false,
      unReadMsg: '',
      websocket: null,
      wsData: '',
      kefuVisible: false,
      onLine: [],
      msg: '',
      to: [],
      dynamicMenuRoutes: []
    }
  },
  components: {
    SubMenu
  },
  created () {
    this.menuList = JSON.parse(sessionStorage.getItem('menuList') || '[]')
    this.dynamicMenuRoutes = JSON.parse(sessionStorage.getItem('dynamicMenuRoutes') || '[]')
    this.routeHandle(this.$route)
  },
  watch: {
    $route: 'routeHandle'
  },
  computed: {
    menuList: {
      get () {
        return this.$store.state.common.menuList
      },
      set (val) {
        this.$store.commit('common/updateMenuList', val)
      }
    },
    menuActiveName: {
      get () {
        return this.$store.state.common.menuActiveName
      },
      set (val) {
        this.$store.commit('common/updateMenuActiveName', val)
      }
    },
    navbarLayoutType: {
      get () {
        return this.$store.state.common.navbarLayoutType
      }
    },
    sidebarFold: {
      get () {
        return this.$store.state.common.sidebarFold
      },
      set (val) {
        this.$store.commit('common/updateSidebarFold', val)
      }
    },
    mainTabs: {
      get () {
        return this.$store.state.common.mainTabs
      },
      set (val) {
        this.$store.commit('common/updateMainTabs', val)
      }
    },
    userName: {
      get () {
        return this.$store.state.user.name
      }
    }
  },
  // mounted () {
  //   this.initWebSocket()
  // },
  beforeDestroy () {
    this.onbeforeunload()
  },
  methods: {
    // 判断跳转到哪一个页面
    jumpToMenu (type) {
      if (type === 1) {
        this.menuActiveName = 'home'
        this.$router.replace({path: 'home'})
      }
      this.menuVisible = false
    },
    // 显示隐藏菜单
    menuClick () {
      this.menuVisible = !this.menuVisible
    },
    // 單獨顯示隱藏菜單
    remenuClick () {
      this.menuVisible = !this.menuVisible
    },
    // 退出关闭
    loginClose () {
      this.logoutVisible = false
      this.menuVisible = false
    },
    // 路由操作
    routeHandle (route) {
      if (route.meta.isTab) {
        // tab选中, 不存在先添加
        let tab = this.mainTabs.filter(item => item.name === route.name)[0]
        if (!tab) {
          if (route.meta.isDynamic) {
            route = this.dynamicMenuRoutes.filter(item => item.name === route.name)[0]
            if (!route) {
              return console.error('未能找到可用标签页!')
            }
          }
          tab = {
            menuId: route.meta.menuId || route.name,
            name: route.name,
            title: route.meta.title,
            type: isURL(route.meta.iframeUrl) ? 'iframe' : 'module',
            iframeUrl: route.meta.iframeUrl || ''
          }
          this.mainTabs = this.mainTabs.concat(tab)
        }
        this.menuActiveName = tab.menuId + ''
        this.mainTabsActiveName = tab.name
      }
      this.menuVisible = false
    },
    openKefu () {
      this.kefuVisible = !this.kefuVisible
      this.unReadMsg = ''
    },
    sendAll () {
      if (!this.msg) {
        this.$message.error('发送内容不能为空')
      } else {
        let object = {
          msg: this.msg,
          type: 1
        }
        // 将object转成json字符串发送给服务端
        let json = JSON.stringify(object)
        this.websocket.send(json)
      }
    },
    sendToUser () {
      if (!this.msg) {
        this.$message.error('发送内容不能为空')
      } else {
        if (this.to.length === 0) {
          this.$message.error('请选择一个用户')
        } else {
          let object = {
            to: this.to,
            msg: this.msg,
            type: 2
          }
          // 将object转成json字符串发送给服务端
          let json = JSON.stringify(object)
          this.websocket.send(json)
        }
      }
    },
    // 退出确定
    makesureHandle () {
      this.$http({
        url: '/sys/logout',
        method: 'post'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          clearLoginInfo()
          this.$router.push({ name: 'login' })
        }
      })
    },
    // 退出
    logoutHandle () {
      this.logoutVisible = true
    },
    initWebSocket () {
      let host = this.$http.BASE_URL.replace('http', 'ws')
      // 判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
        this.websocket = new WebSocket(`${host}/webSocket`)
      } else {
        this.websocket = new SockJS(`${host}/webSocket/sockjs`)
      }

      // 连接错误
      this.websocket.onerror = this.setErrorMessage

      // 连接成功
      this.websocket.onopen = this.setOnopenMessage

      // 收到消息的回调
      this.websocket.onmessage = this.setOnmessageMessage

      // 连接关闭的回调
      this.websocket.onclose = this.setOncloseMessage

      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = this.onbeforeunload
    },
    setErrorMessage () {
      this.wsData += 'WebSocket连接发生错误' + '   状态码：' + this.websocket.readyState
    },
    setOnopenMessage () {
      console.log('WebSocket连接成功' + '   状态码：' + this.websocket.readyState)
    },
    setOnmessageMessage (event) {
      let data = JSON.parse(event.data)
      if (data.users) {
        this.onLine = data.users
      } else {
        this.$message({
          type: 'success',
          message: '您有新的消息！内容：' + data.msg,
          center: true
        })
        this.wsData += event.data
        this.unReadMsg++
      }
    },
    setOncloseMessage () {
      console.log('WebSocket连接关闭' + '   状态码：' + this.websocket.readyState)
    },
    onbeforeunload () {
      this.websocket.close()
    }
  }
}
</script>
<style lang="scss" scoped>
.mobileFull ::v-deep {
  position: relative;
  .site-navbar__body {
    margin-left: 0;
    padding-right: 0;
    .leaderTop {
      font-size: 17px;
      font-weight: 600;
      line-height: 50px;
      float: left;
      color: #333;
      padding: 0 15px;
    }
    .navBar {
      position: relative;
      width: 30px;
      margin: 10px 15px;
      float: right;
    }
  }
}



</style>
