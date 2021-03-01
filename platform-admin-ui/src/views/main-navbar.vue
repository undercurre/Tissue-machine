<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header" v-if="!sidebarFold">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">纸巾机管理平台</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
<!--         群发消息-->
<!--        <el-menu-item index="0" @click="openKefu">-->
<!--          <template slot="title">-->
<!--            <el-badge :value="unReadMsg">-->
<!--              <icon-svg name="wechat" class="el-icon-setting"></icon-svg>-->
<!--            </el-badge>-->
<!--          </template>-->
<!--        </el-menu-item>-->

<!--        <el-menu-item index="1">-->
<!--          <el-badge value="hot">-->
<!--            <a href="//fly2you.cn/" target="_blank">官方社区</a>-->
<!--          </el-badge>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="2" @click="$router.push({ name: 'theme' })">-->
<!--          <template slot="title">-->
<!--            <el-badge is-dot value="new">-->
<!--              <icon-svg name="xitongpeizhi" class="el-icon-setting"></icon-svg>-->
<!--            </el-badge>-->
<!--          </template>-->
<!--        </el-menu-item>-->
        <el-menu-item index="2" @click="openCharts">
          <template slot="title">
            <icon-svg name="charts" class="el-icon-setting"></icon-svg>
          </template>
        </el-menu-item>
        <el-menu-item class="site-navbar__avatar" index="3">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <img src="~@/assets/img/avatar.png" :alt="userName">{{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
    <!-- 弹窗，显示数据页面 -->
    <chart-line v-if="chartLineVisible" ref="chartLine"></chart-line>
<!--    <el-dialog-->
<!--      :close-on-click-modal=false-->
<!--      title="客服消息"-->
<!--      :visible.sync="kefuVisible"-->
<!--      :append-to-body="true">-->
<!--      <div style="height: 300px;overflow-y: auto;background-color:#EDEDED">-->
<!--        {{wsData}}-->
<!--      </div>-->
<!--      发送用户：<br>-->
<!--      <el-select v-model="to" multiple placeholder="请选择">-->
<!--        <el-option-->
<!--          v-for="item in onLine"-->
<!--          :key="item.userId"-->
<!--          :label="item.userName"-->
<!--          :value="item.userId">-->
<!--        </el-option>-->
<!--      </el-select>-->
<!--      <br>发送内容：-->
<!--      <el-input v-model="msg" placeholder="请输入内容"></el-input>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button type="success" @click="sendAll">群发</el-button>-->
<!--        <el-button type="warning" @click="sendToUser">指定用户</el-button>-->
<!--        <el-button @click="kefuVisible = false">取消</el-button>-->
<!--    </span>-->
<!--    </el-dialog>-->
  </nav>
</template>

<script>
  import UpdatePassword from './main-navbar-update-password'
  import ChartLine from './main-chartmore'
  import { clearLoginInfo } from '@/utils'
  // import SockJS from 'sockjs-client'

  export default {
    data () {
      return {
        updatePassowrdVisible: false,
        chartLineVisible: false,
        unReadMsg: '',
        websocket: null,
        wsData: '',
        kefuVisible: false,
        onLine: [],
        msg: '',
        to: []
      }
    },
    components: {
      UpdatePassword,
      ChartLine
    },
    computed: {
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
    mounted () {
      // this.initWebSocket()
    },
    beforeDestroy () {
      // this.onbeforeunload()
    },
    methods: {
      openCharts () {
        this.chartLineVisible = true
        this.$nextTick(() => {
          this.$refs.chartLine.init()
        })
      },
      // openKefu () {
      //   this.kefuVisible = !this.kefuVisible
      //   this.unReadMsg = ''
      // },
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
      // 修改密码
      updatePasswordHandle () {
        this.updatePassowrdVisible = true
        this.$nextTick(() => {
          this.$refs.updatePassowrd.init()
        })
      },
      // 退出
      logoutHandle () {
        this.$confirm(`确定进行[退出]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: '/sys/logout',
            method: 'post'
          }).then(({ data }) => {
            if (data && data.code === 0) {
              clearLoginInfo()
              this.$router.push({ name: 'login' })
            }
          })
        }).catch(() => {
        })
      }
      // initWebSocket () {
      //   let host = this.$http.BASE_URL.replace('http', 'ws')
      //   // 判断当前浏览器是否支持WebSocket
      //   if ('WebSocket' in window) {
      //     this.websocket = new WebSocket(`${host}/webSocket`)
      //   } else {
      //     this.websocket = new SockJS(`${host}/webSocket/sockjs`)
      //   }
      //
      //   // 连接错误
      //   this.websocket.onerror = this.setErrorMessage
      //
      //   // 连接成功
      //   this.websocket.onopen = this.setOnopenMessage
      //
      //   // 收到消息的回调
      //   this.websocket.onmessage = this.setOnmessageMessage
      //
      //   // 连接关闭的回调
      //   this.websocket.onclose = this.setOncloseMessage
      //
      //   // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      //   window.onbeforeunload = this.onbeforeunload
      // },
      // setErrorMessage () {
      //   this.wsData += 'WebSocket连接发生错误' + '   状态码：' + this.websocket.readyState
      // },
      // setOnopenMessage () {
      //   console.log('WebSocket连接成功' + '   状态码：' + this.websocket.readyState)
      // },
      // setOnmessageMessage (event) {
      //   let data = JSON.parse(event.data)
      //   if (data.users) {
      //     this.onLine = data.users
      //   } else {
      //     this.$message({
      //       type: 'success',
      //       message: '您有新的消息！内容：' + data.msg,
      //       center: true
      //     })
      //     this.wsData += event.data
      //     this.unReadMsg++
      //   }
      // },
      // setOncloseMessage () {
      //   console.log('WebSocket连接关闭' + '   状态码：' + this.websocket.readyState)
      // },
      // onbeforeunload () {
      //   this.websocket.close()
      // }
    }
  }
</script>
