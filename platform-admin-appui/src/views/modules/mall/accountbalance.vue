<template>
  <div class="Content">
    <div class="topPhoto">
       <div class="allTxtBox">
         <div class="topTxt">
           总资产
         </div>
         <div class="bottomTxt">
           <div class="bottomLeft">
             ¥
           </div>
           <div class="bottomRight">
             {{ money }}
           </div>
         </div>

       </div>
    </div>
    <el-button type="primary" :round="isRound" class="withdrawalButton" @click="GotoWithdrawalBalance">
      提现
    </el-button>
    <el-button v-if="isAuth('ad:withdrawal:list')" type="text" class="withdrawalDetail" @click="GotoUserWithdrawal">
      提现明细
    </el-button>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        money: '',
        isRound: true
      }
    },
    activated () {
      let that = this
      this.$http({
        url: '/sys/user/info',
        method: 'get'
      }).then(({data}) => {
        console.log('userInfo', data)
        that.money = data.user.balance
      })
    },
    methods: {
      GotoWithdrawalBalance () {
        this.$router.push({path: 'mall-withdrawalbalance', query: {fromBankcard: '0'}})
      },
      GotoUserWithdrawal () {
        this.$router.push({path: 'mall-withdrawal'})
      }
    }
  }
</script>

<style lang="scss" scoped>
 .Content{
   padding: 0;
   height: calc(100vh - 52px);
   display: flex;
   flex-direction: column;
   align-items: center;
   .topPhoto{
     margin: 15px 0;
     background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/totalAssets3xZoqVersion2.png");
     width: 100%;
     height: 50vh;
     background-repeat: no-repeat;
     background-size: 100% auto;
     position: relative;
     .allTxtBox{
       position: absolute;
       top: 60px;
       left: 40px;
       display: flex;
       flex-direction: column;
       justify-content: space-between;
       color: #ffffff;
       .topTxt{
         font-size: 12px;
         margin-bottom: 15px;
       }
       .bottomTxt{
         display: flex;
         align-items: baseline;
         .bottomLeft{
           font-size: 18px;
         }
         .bottomRight{
           font-size: 30px;
         }
       }
     }
   }
   .withdrawalButton{
     width: calc(100% - 30px);
     background-color: #082F40;
     color: #fff;
     padding: 12px 20px;
     font-size: 14px;
     font-family: "PingFang SC";
   }
   .withdrawalDetail{
     margin: 20px auto 0;
     color: #082F40;
     display: block;
   }
 }

</style>
