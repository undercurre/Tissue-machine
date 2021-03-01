<template>
  <div class="Content">
<!--    <div class="top">-->
<!--      <div class="FontStyle">菜单导航</div>-->
<!--      <div class="imgBox">-->
<!--        <img-->
<!--          class="topImg"-->
<!--          src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backNavigation3xZpq.png"-->
<!--          alt=""-->
<!--        />-->
<!--      </div>-->
<!--    </div>-->
    <div class="MiddlePic">
      <img
        class="BigPic"
        src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backbanner3xZpq.png"
        alt=""
      />
    </div>
    <div class="Bottom">
      <!--机柜管理-->
      <div class="indBlockList"  @click="GotoMachineManagement" v-if="isInMenuList('机柜管理')">
        <div class="blokckListLeft">
          <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backCabinetLogo3xZpq.png" alt="">
        </div>
        <div class="blokckListCenter">
          <h5>机柜管理</h5>
          <p>Cabinet management</p>
        </div>
        <div class="blokckListRight">
          <img class="Goto" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Goto3xZpq.png">
        </div>
      </div>
      <!--商品管理-->
      <div class="indBlockList goodsManagement"  @click="GotoGoodsManagement" v-if="isInMenuList('商品管理')">
        <div class="blokckListLeft">
          <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/goodsManageLogo3xZpq.png" alt="">
        </div>
        <div class="blokckListCenter">
          <h5>商品管理</h5>
          <p>Commodity management</p>
        </div>
        <div class="blokckListRight">
          <img class="Goto" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Goto3xZpq.png">
        </div>
      </div>
      <!-- 财务管理 -->
      <div class="indBlockList financialManagement"  @click="GotoFinancialManagement" v-if="isInMenuList('财务管理')">
        <div class="blokckListLeft">
          <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/financialManageLogo3xZpq.png" alt="">
        </div>
        <div class="blokckListCenter">
          <h5>财务管理</h5>
          <p>Financial management</p>
        </div>
        <div class="blokckListRight">
          <img class="Goto" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Goto3xZpq.png">
        </div>
      </div>
      <!--工单管理-->
      <div class="indBlockList orderManagement"  @click="GotoOrderManagement" v-if="isInMenuList('工单管理')">
        <div class="blokckListLeft">
          <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/orderManageLogo3xZpq.png" alt="">
        </div>
        <div class="blokckListCenter">
          <h5>工单管理</h5>
          <p>Work order management</p>
        </div>
        <div class="blokckListRight">
          <img class="Goto" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Goto3xZpq.png">
        </div>
      </div>

      <div class="indBlockList aboutus"  @click="GotoAboutUs">
        <div class="blokckListLeft">
          <img class="Logo" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/about3x.png" alt="">
        </div>
        <div class="blokckListCenter">
          <h5>关于我们</h5>
          <p>About Us</p>
        </div>
        <div class="blokckListRight">
          <img class="Goto" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Goto3xZpq.png">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        txt: '',
        menuList: []
      }
    },
    activated () {
      this.menuList = JSON.parse(sessionStorage.getItem('menuList') || '[]')
    },
    methods: {
      isInMenuList (menuName) {
        for (let i in this.menuList) {
          if (menuName === this.menuList[i].name) {
            return true
          }
        }
        return false
      },
      GotoMachineManagement () {
        this.$router.replace({path: 'mall-tissuemachine'})
      },
      GotoGoodsManagement () {
        this.$router.push({path: 'mall-goods'})
      },
      GotoFinancialManagement () {
        this.$router.push({path: 'mall-financialmanage'})
      },
      GotoOrderManagement () {
        this.$router.push({path: 'mall-myworkorder'})
      },
      GotoAboutUs () {
        this.$router.push({path: 'mall-aboutus'})
      }
    }
  }
</script>

<style lang="scss" scoped>
.Content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;
  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 90%;
    padding: 15px 0;
    .FontStyle {
      font-size: 17px;
      color: #333333;
    }
    .imgBox {
      display: flex;
      justify-content: center;
      align-items: center;
      .topImg {
        width: 26px;
        height: 18px;
      }
    }
  }
  .MiddlePic {
    .BigPic {
      width: 100%;
    }
  }
  .Bottom {
    width: 100%;
    padding: 15px 15px 0;
    .indBlockList {
      height: 80px;
      position: relative;
      overflow: hidden;
      margin: 0 0 15px;
      border-radius: 3px;
      -webkit-border-radius: 3px;
      -moz-border-radius: 3px;
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Cabinet%20management3xZpq.png");
      background-size: auto 100%;
      background-position: right;
      background-repeat: no-repeat;
      display: flex;
      .blokckListLeft {
        float: left;
        padding: 0 10px;
        height: 80px;
        img {
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 15px 0;
          width: 34px;
        }
      }
      .blokckListCenter {
        position: relative;
        flex: 1;
        justify-content: center;
        align-items: center;
        width: calc(100% - 99px);
        overflow: hidden;
        padding: 14px 0;
        h5,p {
          margin: 0 0 15px;
          padding: 0 0 0 6vw;
          font-size: 20px;
          color: #fff;
          font-family: "PingFang SC";
        }
        p {
          font-size: 12px;
          margin: 0;
        }
      }
      .blokckListRight {
        float: right;
        padding: 0 20px 0 10px;
        img {
          display: block;
          margin: 28px 0;
          height: 24px;
        }
      }
    }
    .goodsManagement {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/goodsManage3xZpq.png");
      .blokckListLeft {
        img {
          margin: 22px 0 21px;
        }
      }
    }
    .financialManagement {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/financialManage3xZpq.png");
      .blokckListLeft {
        img {
          margin: 23px 0;
        }
      }
    }
    /*工单管理*/
    .orderManagement {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/orderManage3xZpq.png");
      .blokckListLeft {
        img {
          margin: 23px 0;
        }
      }
    }
    .aboutus {
      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/aboutusbackground3x.png");
      .blokckListLeft {
        img {
          margin: 23px 0;
        }
      }
    }
  }
}
</style>
