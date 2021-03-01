<template>
  <div class="Content">
    <!-- 机柜管理导航栏 -->
    <div class="top">
      <div class="FontStyle">菜单导航</div>
      <div class="imgBox">
        <img
          class="topImg"
          src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backNavigation3xZpq.png"
          alt=""
        />
      </div>
    </div>
    <!-- 导航栏下边框虚线 -->
    <div class="bottomLine"></div>

    <div class="frame">
      <!-- 工单类型、查询 -->
      <div class="orderTypeAndqueryButton">
        <select v-model="selected" class="orderType">
          <option disabled value="">工单类型</option>
          <option>采购单</option>
          <option>印刷单</option>
          <option>送货单</option>
          <option>维修单</option>
        </select>
        <span class="queryButton" v-bind="query">查询</span>
      </div>
      <!-- 工单表格 -->
      <el-table :data="tableData" border="1" class="el-table；">
        <el-table-column
          prop="orderNum"
          label="序号"
          class="orderNum"
          width="49vw"
        >
        </el-table-column>
        <el-table-column prop="type" label="工单类型" width="80vw">
        </el-table-column>
        <el-table-column prop="statusId" label="工单状态" width="80vw">
          <template slot-scope="{ row }">
            <span
              style="
                background-color: #f0f9eb;
                color: #5e962d;
                border-radius: 30%;
                border: solid 0.2vw #d9d9d9;
                padding: 2vw;
              "
              v-if="row.statusId == 0"
              >已处理</span
            >
            <span
              v-else
              style="
                background-color: #f56c6c;
                color: #fff;
                border-radius: 30%;
                padding: 2vw;
              "
              >处理中</span
            >
          </template>
        </el-table-column>
        <el-table-column prop="operationId" label="操作" class="operationTitle">
          <template slot-scope="{ row }">
            <span
              v-if="row.operationId == 0"
              class="operation"
              style="font-size: larger"
              >查看</span
            >
            <div style="display: flex" v-else>
              <div
                class="twoButton"
                style="
                  display: flex;
                  justify-content: space-between;
                  width: 100%;
                "
              >
                <el-button
                  type="text"
                  @click="dialogFormVisibleCheck = true"
                  style="font-size: large"
                  >指派</el-button
                >
                <el-button
                  type="text"
                  @click="dialogFormVisibleModify = true"
                  style="font-size: large"
                  >查看</el-button
                >
                <el-button
                  type="text"
                  @click="open"
                  style="color: #f96464; font-size: large"
                  >确认</el-button
                >
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 跳转至第x页 -->
      <div class="Jump">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          page-size="8"
          layout="prev, pager, next, jumper"
          :total="40"
          pager-count="3"
        >
        </el-pagination>
        <div class="ConfirmButton">
          <span>确认</span>
        </div>
      </div>
    </div>
    <!-- 指派对话框 -->
    <!--  <el-dialog
      :visible.sync="dialog"
      append-to-body
      title="新增"
      top="30vh"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item label="指派人员" prop="people">
          <el-select v-model="ruleForm.people" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工单类型" prop="type">
          <el-radio-group v-model="ruleForm.type">
            <div class="RadioGroup">
              <el-radio :label="1">广告采购</el-radio>
              <el-radio :label="2">设备维护</el-radio>
            </div>
            <div class="RadioGroup">
              <el-radio :label="3">送货</el-radio>
              <el-radio :label="4">贴印刷广告</el-radio>
            </div>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="对应机柜" prop="machine">
          <el-select v-model="ruleForm.machine" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容描述" prop="desc">
          <el-input
            type="textarea"
            autosize
            rows="5"
            v-model="ruleForm.desc"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >确 定</el-button
        >
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageNum: 1,
      selected: "",
      tableData: [
        {
          orderNum: "1",
          type: "采购单",
          statusId: "1",
          operationId: "0",
        },
        {
          orderNum: "2",
          type: "维修单",
          statusId: "0",
          operationId: "1",
        },
        {
          orderNum: "3",
          type: "送货单",
          statusId: "1",
          operationId: "1",
        },
        {
          orderNum: "4",
          type: "印刷单",
          statusId: "0",
          operationId: "0",
        },
        {
          orderNum: "5",
          type: "印刷单",
          statusId: "1",
          operationId: "0",
        },
        {
          orderNum: "6",
          type: "维修单",
          statusId: "0",
          operationId: "0",
        },
        {
          orderNum: "7",
          type: "印刷单",
          statusId: "1",
          operationId: "1",
        },
        {
          orderNum: "8",
          type: "维修单",
          statusId: "1",
          operationId: "0",
        } /* {
          orderNum: "9",
          type: "送货单",
          statusId: "0",
          operationId: "1",
        }, */,
      ],
    };
  },
};
</script>
<style lang="scss" scoped>
/* 机柜管理导航栏 */
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
  .bottomLine {
    height: 4px;
    width: 100vw;
    background-color: grey;
  }
  .frame {
    /* margin: 4vw; */
    /* 工单类型、查询 */
    .orderTypeAndqueryButton {
      display: flex;
      align-items: center;
      margin: 5vw 0;
      .orderType {
        margin-right: 2vw;
        width: 32vw;
        height: 8vw;
      }
      .queryButton {
        color: #3a9df2;
        border: #3a9df2 0.1vw solid;
        border-radius: 10%;
        padding: 2vw;
      }
    }
    /* 工单表格 */
    .el-table {
      width: 100vw;
    }
    /deep/.el-table th {
      font-weight: bold;
      color: #ffffff;
      background-color: #ffba77;
      text-align: center;
    }
    /deep/.el-table_1_column_4 {
      color: #3a9df2;
      display: flex;
      justify-content: center;
    }
    /deep/ .el-table .cell {
      text-align: center;
    }
    /* 跳转至第x页 */
    .Jump {
      display: flex;
      align-items: center;
      justify-content: space-between;
      width: 100%;
      /deep/.el-pagination {
        width: 70%;
        .el-pagination__editor.el-input {
          width: 32px;
        }
        .el-pagination__jump {
          margin-left: 0;
        }
      }
      .ConfirmButton {
        width: 20%;
      }
    }
  }
}
</style>
