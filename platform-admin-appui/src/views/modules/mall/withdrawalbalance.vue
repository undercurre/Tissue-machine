<template>
  <div class="Content">
    <!-- 到账银行 -->
    <div class="Top">
      <div class="Left">
          到账银行
      </div>
      <div class="Right" @click="GotoBindCard">
        <div class="WhichBank" v-if="isBind">
          <div class="bankFont">
            {{ dataForm.bankName }} ({{ dataForm.bankNo.toString().substring(12) }})
          </div>
          <div class="time">
            (2小时内到账)
          </div>
        </div>
        <div class="WhichBank" v-else>
          <div class="bankFont">
            去绑定银行卡
          </div>
          <div class="time">
            （暂无银行卡）
          </div>
        </div>
        <img src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/GotoBank3xZpq.png" alt="" class="Img">
      </div>
    </div>
    <!-- 提现金额 -->
    <div class="Center">
      <div class="Left">
        <div class="cashTil">
          提现金额
        </div>
        <div class="inputMoney">
          <span>&yen;</span><el-input v-model="Form.amount"></el-input>
        </div>
        <div class="cashPrice">可提现金额&nbsp;&yen;{{ balance }}</div>
      </div>
      <div class="Right">
        <el-button type="text" @click="Form.amount = balance">全部提现</el-button>
      </div>
    </div>
    <div class="button">
      <el-button
        type="primary"
        round
        :disabled="Form.userBankId === '' || Form.amount === ''"
        @click="toConfirmWithdrawal">提现</el-button>
    </div>
    <userbank-list
      ref="userList"
      v-if="userVisible"
      @getChoose="theChoosePoint"
    />
    <balance-poundage ref="balancePoundage" v-if="poundageVisible"></balance-poundage>
  </div>
</template>

<script>
import userbankList from './userbankList'
import BalancePoundage from './accountbalance-poundage'
export default {
  data () {
    return {
      isBind: false,
      userVisible: false,
      poundageVisible: false,
      balance: '',
      bindCardId: '',
      lastUseCardId: '',
      searchForm: {
        name: ''
      },
      pageIndex: 1,
      pageSize: 10,
      Form: {
        amount: '',
        userBankId: ''
      },
      dataForm: {
        bankName: null,
        bankNo: null,
        id: null,
        mobile: null,
        type: null,
        userId: null,
        userName: null
      }
    }
  },
  components: {
    userbankList,
    BalancePoundage
  },
  activated () {
    this.isBind = false
    let that = this
    for (let key of Object.keys(this.dataForm)) {
      this.dataForm[key] = null
    }
    this.getDataList()
    this.$http({
      url: '/sys/user/info',
      method: 'get'
    }).then(({data}) => {
      console.log('userInfo', data)
      that.balance = data.user.balance
    })
  },
  methods: {
    // 获取最近提现用的银行卡信息
    getDataList () {
      let that = this
      this.Form.amount = ''
      // 从提现列表中查询最近的银行卡号
      this.$http({
        url: '/ad/withdrawal/getLastestCard',
        methods: 'get'
      }).then(({data}) => {
        if (data && data.code === 0 && data.data !== null) {
          let lastUseCard = data.data
          // 获取用户的所有银行卡信息
          this.$http({
            url: '/ad/userbank/list',
            method: 'get',
            params: {
              'page': this.pageIndex,
              'limit': this.pageSize,
              'name': this.searchForm.name
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              let userCards = []
              userCards = data.page.records
              // 判断最近提现使用的银行卡是否还在用户所有的银行卡中，因为有可能最近使用的银行卡被用户删了
              for (let j = 0, len = userCards.length; j < len; j++) {
                // 如果最近使用的银行卡还在，则根据银行卡号获取具体的银行卡信息
                if (userCards[j].id === lastUseCard) {
                  that.lastUseCardId = lastUseCard
                  that.$http({
                    url: `/ad/userbank/info/${that.lastUseCardId}`,
                    method: 'get'
                  }).then(({data}) => {
                    if (data && data.code === 0) {
                      that.dataForm = data.userbank
                      that.isBind = true
                      that.Form.userBankId = data.userbank.id
                    }
                  })
                }
              }
            }
          })
        }
      })
    },
    theChoosePoint (data) {
      if (data) {
        this.isBind = true
        this.dataForm = data
        this.Form.userBankId = data.id
      }
    },
    sendWithdrawal () {
      this.$http({
        url: '/ad/withdrawal/save',
        method: 'POST',
        data: this.Form
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: '提现发起成功！',
            type: 'success',
            duration: '1500',
            onClose: () => {
              this.$router.push({path: 'mall-accountbalance'})
            }
          })
        }
      })
    },
    toConfirmWithdrawal () {
      this.poundageVisible = true
      this.$nextTick(() => {
        this.$refs.balancePoundage.init(this.Form)
      })
    },
    GotoBindCard () {
      this.userVisible = true
      this.$nextTick(() => {
        this.$refs.userList.getDataList()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .Content{
    padding: 20px 15px;
    /* 上中下部分（以UI中的横线划分）的上部分 */
    .Top{
      width: 100%;
      padding-bottom: 20px;
      border-bottom: 1px solid #ccc;
      display: flex;
      align-items: flex-start;
      justify-content: space-between;
      position: relative;
      .Left{
        position: absolute;
        top: 0;
        left: 0;
        font-size: 10px;
        color: #082F40;
        font-family: "PingFang SC";
      }
      .Right{
        padding: 0 0 0 50px;
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: relative;
        .WhichBank{
          display: flex;
          flex-direction: column;
          justify-content: flex-start;
          align-items: center;
          margin: 0 auto;
          .bankFont{
            font-size: 17px;
            color: #333;
            margin-bottom: 20px;
            font-weight: 600;
            font-family: "PingFang SC";
          }
          .time{
            font-size: 10px;
            font-family: "PingFang SC";
            color: #999;
          }
        }
        .Img{
          width: 28px;
        }
      }
    }
    .Center{
      position: relative;
      overflow: hidden;
      width: 100%;
      padding: 20px 0;
      border-bottom: 1px solid #ccc;
      .Left{
        position: relative;
        padding: 0 80px 0 0;
        .cashTil {
          font-size: 17px;
          color: #333;
          display: block;
          padding: 0 0 10px;
        }
        .inputMoney{
          font-size: 17px;
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 0 0 15px;
          span {
            font-size: 30px;
            font-weight: 600;
            line-height: 36px;
            color: #333;
          }
          /deep/ .el-input__inner{
            font-size: 30px;
            color: #333;
            border: none;
            padding: 0 15px 0 5px;
          }
        }
        .cashPrice {
          font-size: 17px;
          color: #333;
        }
      }
      .Right {
        position: absolute;
        right: 0;
        top: 0;
        z-index: 99;
        height: 100%;
        display: flex;
        /deep/ .el-button--text {
          font-size: 17px;
          color: #FF5757;
          font-family: "PingFang SC";
        }

      }
    }
    .button{
      width: 100%;
      margin-top: 30px;
      display: flex;
      justify-content: center;
      /deep/ .el-button{
        width: 100%;
        padding: 12px 0;
        background-color: #082F40;
        border-color: #082F40;
      }
    }
  }

</style>
