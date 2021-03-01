<template>
  <div
    class="site-wrapper"
    :class="{ 'site-sidebar--fold': sidebarFold }">
    <template>
      <main-navbar/>
<!--      <main-sidebar/>-->
      <div class="site-content__wrapper mobileFullPage" :style="{ 'min-height': documentClientHeight + 'px' }">
        <main-content/>
      </div>
    </template>
  </div>
</template>

<script>
  import MainNavbar from './main-navbar'
  import MainSidebar from './main-sidebar'
  import MainContent from './main-content'

  export default {
    data () {
      return {}
    },
    components: {
      MainNavbar,
      MainSidebar,
      MainContent
    },
    computed: {
      documentClientHeight: {
        get () {
          return this.$store.state.common.documentClientHeight
        },
        set (val) {
          this.$store.commit('common/updateDocumentClientHeight', val)
        }
      },
      sidebarFold: {
        get () {
          return this.$store.state.common.sidebarFold
        }
      },
      userId: {
        get () {
          return this.$store.state.user.id
        },
        set (val) {
          this.$store.commit('user/updateId', val)
        }
      },
      userName: {
        get () {
          return this.$store.state.user.name
        },
        set (val) {
          this.$store.commit('user/updateName', val)
        }
      }
    },
    created () {
      this.getUserInfo()
    },
    mounted () {
      this.resetDocumentClientHeight()
    },
    methods: {
      // 重置窗口可视高度
      resetDocumentClientHeight () {
        this.documentClientHeight = document.documentElement['clientHeight']
        window.onresize = () => {
          this.documentClientHeight = document.documentElement['clientHeight']
        }
      },
      // 获取当前管理员信息
      getUserInfo () {
        this.$http({
          url: '/sys/user/info',
          method: 'get'
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.userId = data.user.userId
            this.userName = data.user.userName
          }
        })
      }
    }
  }
</script>
<style lang="scss">
.site-wrapper {
  position: relative;
  min-width: auto;
  .mobileFull {
    /*position: relative;*/
    .site-navbar__body {
      margin-left: 0;
      padding-right: 0;
      z-index: 9999;
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
        height: 20px;
        margin: 15px;
        float: right;
        img {
          height: 100%;
        }
      }
    }
  }
  .mobileFullPage {
    position: relative;
    margin-left: 0;
    padding-top: 0;
    .allPageTop {
      padding: 50px 0 0;
      min-height: calc(100vh - 50px);
      >.el-card {
        width: 100%;
        min-height: calc(100vh - 50px);
        >.el-card__body {
          padding: 0;
        }
      }
    }
  }
}

.mask {
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0, 0.2);
  position: fixed;
  z-index: 99;
  .el-menu-vertical-demo {
    width: 50vw;
    float: right;
    position: relative;
    margin: 50px 0 0;
    .el-submenu {
      position: relative;
      .el-submenu__title {
        font-size: 12px;
        height: 45px;
        line-height: 45px;
      }
      &.is-active,
      &.is-opened {
        .el-submenu__title {
          background-color: #05232F !important;
        }
      }
    }
    .el-menu-item {
      font-size: 12px;
      height: 45px;
      line-height: 45px;
    }
  }
}

/* radio按钮和钱包图标 */
.RadioGroupAndLogo {
  width: 100%;
  position: relative;
  overflow: hidden;
  .theRadio {
    position: relative;
    float: left;
    padding: 9px 0 0;
    .el-radio-group {
      .el-radio-button {
        .el-radio-button__orig-radio:checked {
          &+.el-radio-button__inner {
            background-color: #082F40;
            border-color: #082F40;
          }
        }
        .el-radio-button__inner {
          padding: 15px 0 !important;
          width: 75px;
        }
      }
    }
  }
  .logoFlex {
    float: right;
    position: relative;
    overflow: hidden;
    .logoSize{
      width: 45px;
      display: block;
      margin: 0 auto;
    }
    .fontSize{
      font-size: 10px;
      text-align: center;
      color: #333;
      font-family: "PingFang SC";
      z-index: 9;
      margin: -5px 0 0;
    }
  }
}

// 搜索栏样式
.searchList {
  position: relative;
  padding: 10px 15px 0;
  .el-form-item {
    margin: 0 0 10px;
  }
  .longInput {
    width: calc(100% - 60px);
    .el-form-item__content {
      width: 100%;
      .el-input {
        font-size: 12px;
      }
      .el-select {
        width: 95%;
        font-size: 12px;
      }
    }
  }
  .searchBtn {
    .el-form-item__content {
      .el-button {
        padding: 10px 15px;
        font-size: 12px;
        background-color: #082F40;
        border-color: #082F40;
      }
    }
  }
}

.tablePart {
  position: relative;
  .el-table__header {
    tr {
      th {
        font-size: 14px;
        color: #333;
        font-family: "PingFang SC";
      }
    }
  }
  .el-table__body-wrapper {
    .el-table__body {
      .el-table__row {
        td {
          .cell {
            font-size: 12px;
            color: #333;
            font-family: "PingFang SC";
          }
        }
      }
    }
  }
}

// 退出弹层窗口
.logoutPart {
  .el-dialog {
    position: relative;
    width: calc(100vw - 30px);
    max-width: 480px;
    .el-dialog__header {
      padding: 5px 15px;
      border-bottom: 1px #efefef solid;
      .el-dialog__title {
        font-size: 12px;
      }
    }
    .el-dialog__body {
      padding: 5px 15px;
      font-size: 12px;
    }
    .el-dialog__footer {
      padding: 0;
      border-top: 1px #efefef solid;
      .dialog-footer {
        width: 100%;
        position: relative;
        display: block;
        overflow: hidden;
        .el-button {
          width: 50%;
          float: left;
          padding: 20px 0;
          font-size: 12px;
          border-radius: 0 0 0 2px;
          &+.el-button {
            margin: 0;
            border-radius: 0 0 2px 0;
          }
        }
      }
    }
  }
}


.thePageDialog {
  /*position: relative;*/
  .el-dialog {
    position: relative;
    margin: 10vh auto;
    width: calc(100% - 30px);
    max-height: 80vh;
    background-color: transparent;
    .el-dialog__header {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/dialogTitleBackGroundPic3xZpq.png");
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;
      border-radius: 4px 4px 0 0;
      .el-dialog__headerbtn{
        right: 10px;
        .el-dialog__close{
          color: #fff;
          font-size: 22px;
        }
      }
    }
    .el-dialog__body {
      background-color: #fff;
    }
  }
}
.point {
  /*position: relative;*/
  .el-dialog {
    position: relative;
    margin: 10vh auto;
    width: calc(100% - 30px);
    max-height: 80vh;
    background-color: transparent;
    .el-dialog__header {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/dialogTitleBackGroundPic3xZpq.png");
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;
      border-radius: 4px 4px 0 0;
    }
    .el-dialog__body {
      background-color: #fff;
    }
  }
}

.el-select-dropdown {
  position: relative;
  max-width: 90%;
}
</style>
