<template>
  <div>
    <!-- 导航栏 -->
    <div class="navBar">
      <div class="navBarLeft">
        <img
          src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/logo2xZpq.png"
          alt="logo"
          class="logo"
        />
        <span class="leftTitle">财务管理</span>
      </div>
      <div class="el-icon-menu"></div>
    </div>

    <div class="frame">
      <!-- "全部"、"最近30天"、"最近90天"、账户余额按钮 -->
      <div class="buttonGroup">
        <div class="buttonLeft">
          <div>
            <div
              class="buttonA"
              v-if="status == 0"
              style="color: grey; background-color: #ffffff"
            >
              全部
            </div>
            <div v-else class="buttonA">全部</div>
          </div>
          <div class="buttonB">最近30天</div>
          <div class="buttonC">最近90天</div>
        </div>
        <div class="el-icon-s-finance"></div>
      </div>
      <!-- 订单统计 -->
      <div class="orderCensus">
        <div class="orderCensusTitle">
          <span class="el-icon-tickets"></span>
          订单统计
        </div>
        <div class="orderCensusTable">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="name"> </el-table-column>
            <el-table-column prop="sum"> </el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 会员统计 -->
      <div class="vipCensus">
        <div class="vipCensusTitle">
          <span class="el-icon-tickets"></span>
          会员统计
        </div>
        <el-table :data="tableDataA" style="width: 100%">
          <el-table-column prop="name"> </el-table-column>
          <el-table-column prop="sum"> </el-table-column>
        </el-table>
      </div>
      <!-- 跳转至第x页 -->
      <div class="jumpPage">
        <div class="jumpPageRight">
          <span class="jumpButton" v-bind="navToPage">前往</span>
          <input type="text" v-model="pageNum" class="input" />
          页
        </div>
        <div class="jumpPageLeft">
          <span class="forward" v-bind="forward"></span>
          <span class="currentPage">1</span><span>2</span>
          <span class="backward" v-bind="backward">></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      status: 1,
      tableData: [
        {
          name: "销售总额",
          sum: "￥1000000.00",
        },
        {
          name: "有效订单总数",
          sum: "10000",
        },
        {
          name: "有效订单总额",
          sum: "￥10000000.00",
        },
        {
          name: "无效订单总数",
          sum: "100",
        },
        {
          name: "无效订单总额",
          sum: "￥1000000.00",
        },
        {
          name: "已成交订单总数",
          sum: "100",
        },
        {
          name: "已成交订单总额",
          sum: "￥1000000.00",
        },
      ],
      tableDataA: [
        {
          name: "会员总数",
          sum: "100000",
        },
        {
          name: "有效会员总数",
          sum: "10000",
        },
        {
          name: "会员订单总数",
          sum: "10000",
        },
        {
          name: "会员购物总额",
          sum: "￥100000.00",
        },
        {
          name: "会员购买率",
          sum: "10%",
        },
        {
          name: "会员平均订单数",
          sum: "0.1",
        },
        {
          name: "会员购买率",
          sum: "10%",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
/* 导航栏 */
.navBar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 10vw;
  padding: 1vw 2vw;
  /* border-bottom: 0.1vw solid rgb(220,220,220); */
  box-shadow: 0.1vw 0 0.1vw 0.1vw rgb(0, 0, 0);
  .navBarLeft {
    display: flex;
    align-items: center;
    .logo {
      width: 7vw;
      height: 7vw;
      border: 0.1vw solid #000;
      border-radius: 50%;
      margin-right: 2vw;
    }
    .leftTitle {
      font-weight: bold;
      font-size: large;
    }
  }
  .el-icon-menu {
    font-weight: bold;
    color: #2b98f0;
    font-size: x-large;
  }
}
.frame {
  margin: 4vw;
  /* "全部"、"最近30天"、"最近90天"、账户余额按钮 */
  .buttonGroup {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .buttonLeft {
      display: flex;
      justify-content: space-evenly;
      .buttonA {
        padding: 2vw 6vw;
        font-size: medium;
        border: none;
        color: #ffffff;
        border-right: 0.1vw solid grey;
        background-color: rgb(26, 188, 156);
        border: 0.1vw solid grey;
        border-radius: 2.5vw 0 0 2.5vw;
      }
      .buttonB {
        padding: 2vw 3vw;
        font-size: medium;
        border: none;
        color: grey;
        border: 0.1vw solid grey;
        border-right: 0.1vw solid grey;
        background-color: #ffffff;
      }
      .buttonC {
        padding: 2vw 3vw;
        font-size: medium;
        border: none;
        color: grey;
        background-color: #ffffff;
        border: 0.1vw solid grey;
        border-radius: 0 2.5vw 2.5vw 0;
      }
    }
    .el-icon-s-finance {
      font-size: xx-large;
      color: rgb(248, 182, 45);
    }
  }
  /* 订单统计 */
  .orderCensus {
    .orderCensusTitle {
      margin: 5vw 0;
      font-size: large;
      .el-icon-tickets {
        font-weight: bold;
      }
    }
  }
  /* 会员统计 */
  .vipCensus {
    margin-top: 5vw;
    .vipCensusTitle {
      margin: 5vw 0;
      font-size: large;
      .el-icon-tickets {
        font-weight: bold;
      }
    }
  }
  /* 跳转至第x页 */
  .jumpPage {
    width: 92vw;
    position: fixed;
    bottom: 3vh;
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    font-size: larger;
    .jumpPageRight {
      display: flex;
      align-items: center;
      .jumpButton {
        color: #2b98f0;
      }
      .input {
        width: 6vw;
        height: 3vw;
      }
    }
    .jumpPageLeft {
      margin: 0 4vw;
      .currentPage {
        color: #3a9df2;
        margin-right: 4vw;
      }
    }
    .forward,
    .backward {
      margin: 0 2vw;
    }
  }
}
/deep/ .el-table_1_column_1 {
  color: rgb(153, 153, 153);
  font-weight: bold;
  background-color: rgb(249, 250, 252);
}
/deep/ .el-table_2_column_3 {
  color: rgb(153, 153, 153);
  font-weight: bold;
  background-color: rgb(249, 250, 252);
}
/deep/.el-table thead {
  overflow: hidden;
  display: none;
}
/deep/.el-table__body td {
  text-align: center;
}

/deep/.cell {
  line-height: 28px;
}
/deep/.el-table td {
  padding: 0;
}
</style>
