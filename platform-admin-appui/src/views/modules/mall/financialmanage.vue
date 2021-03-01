<template>
<div class="Content">
  <!-- radio按钮和钱包图标 -->
  <div class="RadioGroupAndLogo">
    <div class="theRadio">
      <el-radio-group v-model="radioOfRecord" size="mini">
        <el-radio-button label=365>全部</el-radio-button>
        <el-radio-button label=30>最近30天</el-radio-button>
        <el-radio-button label=90>最近90天</el-radio-button>
      </el-radio-group>
    </div>
    <!-- 钱包图标 -->
    <div class="logoFlex" @click="GotoAccountBalance">
      <img src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/wallet3xZpq.png" alt="" class="logoSize">
      <p class="fontSize">钱包</p>
    </div>
  </div>
  <!-- 订单统计 -->
  <div class="tablePart">
    <h5>订单统计</h5>
    <!-- 表格 -->
    <div class="tableList">
      <div class="tableBlock">
        <div class="halfLeft">销售总额</div>
        <div class="halfRight">&yen;{{ orderStatic.saleSum }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">有效订单总数</div>
        <div class="halfRight">{{ orderStatic.validOrderNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">有效订单总额</div>
        <div class="halfRight">&yen;{{ orderStatic.validOrderSum }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">无效订单总数(关闭或取消)</div>
        <div class="halfRight">{{ orderStatic.invalidOrderNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">无效订单总额</div>
        <div class="halfRight">&yen;{{ orderStatic.invalidOrderSum }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">已成功订单总数</div>
        <div class="halfRight">{{ orderStatic.finishOrderNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">已成功订单总额</div>
        <div class="halfRight">&yen;{{ orderStatic.finishOrderSum }}</div>
      </div>
    </div>
  </div>

  <!-- 会员统计 -->
  <div class="tablePart">
    <!-- 标题 -->
    <h5>会员统计</h5>
    <!-- 表格 -->
    <div class="tableList">
      <div class="tableBlock">
        <div class="halfLeft">会员总数</div>
        <div class="halfRight">{{ memberstatistics.registerUserNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">有订单会员数</div>
        <div class="halfRight">{{ memberstatistics.haveOrderUserNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">会员订单总数</div>
        <div class="halfRight">{{ memberstatistics.orderNumber }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">会员购物总额</div>
        <div class="halfRight">&yen;{{ memberstatistics.orderAmount }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">会员购买率</div>
        <div class="halfRight">{{ memberstatistics.userBuyRate }}%</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">每会员订单数</div>
        <div class="halfRight">{{ memberstatistics.orderNumberPerPerson }}</div>
      </div>
      <div class="tableBlock">
        <div class="halfLeft">每会员购物额</div>
        <div class="halfRight">&yen;{{ memberstatistics.orderAmountPerPerson }}</div>
      </div>
    </div>
 </div>

</div>

</template>

<script>
export default {
  data () {
    return {
      orderStatic: {
        saleSum: null,
        validOrderNumber: null,
        validOrderSum: null,
        invalidOrderNumber: null,
        invalidOrderSum: null,
        finishOrderNumber: null,
        finishOrderSum: null
      },
      memberstatistics: {
        haveOrderUserNumber: null,
        orderAmount: null,
        orderAmountPerPerson: null,
        orderNumber: null,
        orderNumberPerPerson: null,
        registerUserNumber: null,
        userBuyRate: null
      },
      status: 1,
      radioOfRecord: 365,
      stripeValue: true
    }
  },
  activated () {
    this.getDataList()
  },
  watch: {
    radioOfRecord: function (val) {
      if (val === 365) {
        this.getDataList()
      } else {
        this.getDataList(val)
      }
    }

  },
  methods: {
    getDataList (days) {
      this.$http('/ad/order/orderAccount', {params: {day: days}})
        .then(({data}) => {
          this.orderStatic = data.data
        })
      .catch(error => {
        this.$message.error(error.msg)
      })
      this.$http('/ad/order/userCount', {params: {day: days}})
        .then(({data}) => {
          this.memberstatistics = data.data
        })
        .catch(error => {
          this.$message.error(error.msg)
        })
    },
    GotoAccountBalance () {
      this.$router.push({path: 'mall-accountbalance'})
    }
  }
}
</script>

<style lang="scss" scoped>
.Content {
  padding: 10px 15px;
  position: relative;
  /* 表格 */
  .tablePart {
    padding: 25px 0 0;
    h5 {
      font-size: 14px;
      color: #333;
      font-family: "PingFang SC";
      margin: 0;
      padding: 0;
      line-height: 19px;
      font-weight: 400;
      &:before {
        width: 0;
        border-left: 3px #0A84FF solid;
        border-right: 3px #0A84FF solid;
        border-radius: 2px;
        height: 100%;
        content: "";
        margin: 0 5px 0 0;
      }
    }
    .tableList {
      position: relative;
      overflow: hidden;
      margin: 15px 0 0;
      border: 1px #efefef solid;
      .tableBlock {
        border-bottom: 1px #efefef solid;
        position: relative;
        overflow: hidden;
        .halfLeft,.halfRight {
          font-size: 12px;
          color: #333;
          text-align: center;
          height: 30px;
          line-height: 30px;
          border-right: 1px #efefef solid;
          width: 50%;
          float: left;
          display: block;
        }
        &:nth-of-type(odd) {
          .halfLeft {
            background-color: #eee;
          }
        }
        &:last-child {
          border-bottom: none;
        }
      }
    }
  }
}
</style>
