<template>
  <div>
    <!-- 导航栏 -->
    <div class="navBar">
      <div class="navBarLeft">
        <div>
          <img
            src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/logo2xZpq.png"
            alt="logo"
            class="logo"
          />
        </div>
        <div class="navBarTitle">代接工单</div>
      </div>
      <div class="el-icon-menu"></div>
    </div>

    <div class="frame">
      <!-- 查询、新增、批量删除 -->
      <div class="functionBar">
        <div class="select">
          <select v-model="selected" class="orderType">
            <option disabled value="" style="display: none">工单类型</option>
            <option value="0">采购单</option>
            <option value="1">维修单</option>
            <option value="2">送货单</option>
            <option value="3">印刷单</option>
          </select>
        </div>
        <div class="query">查询</div>
        <div class="add" @click="ShowDialog">新增</div>
        <div class="batchDelete">批量删除</div>
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
        <el-table-column prop="name" label="指派人员" width="80vw">
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
          <span>查看</span>
          <span>修改</span>
          <el-button type="text" @click="open">删除</el-button>
        </el-table-column>
      </el-table>

      <!-- 跳转至第x页 -->
      <div class="jumpPage">
        <div class="jumpPageRight">
          <span class="jumpButton" v-bind="navToPage">前往</span>
          <input name="text" v-model="pageNum" class="input" />
          页
        </div>
        <div class="jumpPageLeft">
          <span class="forward" v-bind="forward"></span>
          <span class="currentPage">1</span><span>2</span>
          <span class="backward" v-bind="backward">></span>
        </div>
      </div>
    </div>
    <!-- 新增对话框 -->

    <el-dialog
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
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageNum: 1,
      selected: "",
      dialog: false /* 对话框显示与否 */,
      FormType: "",
      ruleForm: {
        people: "" /* 指派人 */,
        type: "" /* 工单类型 */,
        machine: "" /* 对应机柜 */,
        desc: "",
      },
      rules: {
        people: [
          { required: true, message: "指派人不能为空", trigger: "change" },
        ],
        type: [
          { required: true, message: "工单类型不能为空", trigger: "change" },
        ],
        machine: [
          { required: true, message: "机柜不能为空", trigger: "change" },
        ],
        desc: [{ required: true, message: "内容不能为空", trigger: "blur" }],
      },
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      tableData: [
        {
          orderNum: "1",
          name: "李华",
          statusId: "1",
        },
        {
          orderNum: "2",
          name: "张三",
          statusId: "0",
        },
        {
          orderNum: "3",
          name: "李四",
          statusId: "1",
        },
        {
          orderNum: "4",
          name: "陈建国",
          statusId: "0",
        },
        {
          orderNum: "5",
          name: "赵卫国",
          statusId: "1",
        },
        {
          orderNum: "6",
          name: "张爱国",
          statusId: "0",
        },
        {
          orderNum: "7",
          name: "刘保家",
          statusId: "1",
        },
        {
          orderNum: "8",
          name: "朱爱国",
          statusId: "1",
        },
        {
          orderNum: "9",
          name: "郑卫国",
          statusId: "0",
        },
      ],
    };
  },
  methods: {
    submitForm(formName) {
      var that=this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("submit===",valid);
        that.$refs[formName].resetFields();
        that.dialog=false;
        } else {
          console.log("error submit!!",valid);
          return false;
        }
      });
    },
    cancel(){
      this.dialog=false;
    },

    open() {
      this.$confirm("是否确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    ShowDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style lang="scss" scoped>
/* 导航栏 */
.navBar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1vw 4vw;
  border-bottom: 1px solid rgba(0, 0, 0, 0.466);
  .navBarLeft {
    display: flex;
    align-items: center;
    .logo {
      width: 8vw;
      height: 8vw;
      border-radius: 50%;
      margin-right: 1vw;
      border: rgba(0, 0, 0, 0.582) 0.1vw solid;
    }
    .navBarTitle {
      font-size: large;
      font-weight: bold;
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
  /* 查询、新增、批量删除 */
  .functionBar {
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    margin: 2vw 0;
    font-size: larger;
    .select {
      flex: 3;
      .orderType {
        width: 100%;
        padding: 1.2vw 0;
      }
    }
    .query {
      color: #3a9df2;
      border: #3a9df2 0.1vw solid;
      border-radius: 1vw;
      flex: 1;
      margin: 0 2vw;
      padding: 2vw 3vw;
      text-align: center;
    }
    .add {
      flex: 1;
      background-color: rgb(94, 190, 45);
      color: #ffffff;
      margin-right: 2vw;
      padding: 2vw 3vw;
      text-align: center;
      border-radius: 1vw;
    }
    .batchDelete {
      flex: 2;
      background-color: rgb(249, 100, 100);
      color: #ffffff;
      text-align: center;
      padding: 2vw 3vw;
      border-radius: 1vw;
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
  .jumpPage {
    width: 92vw;
    margin-top: 5vw;
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    font-size: larger;
    .jumpPageRight {
      display: flex;
      align-items: center;
      .jumpButton {
        color: #3a9df2;
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
/deep/ .el-table_1_column_4 .cell {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  width: 100vw;
}
/deep/.el-popup-parent--hidden {
  .el-message-box__wrapper {
    .el-message-box {
      width: 80vw;
    }
  }
}
@media screen and (max-width: 300px){
    >>> .el-dialog{
      width: 90%!important;
    }
  }

  @media screen and (min-width: 320px){
    >>> .el-dialog{
      width: 80%!important;
    }
  }
::v-deep .el-dialog {

  .el-dialog__header {
    padding: 10px;
  }
  .el-dialog__body {
    padding: 0 0 0 10px;
    .el-form-item {
      margin-bottom: 15px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .el-form-item__label {
        line-height: 1;
        padding: 0;
      }
      .el-form-item__content {
        width: 60%;
        line-height: 1;
        margin-left: 0;
        .el-form-item__error {
          padding-top: 1px;
        }
        /* 指派人 */
        .el-select {
          width: 100%;
          .el-input {
            width: 100%;
            .el-input__inner {
              height: 20px;
            }
            .el-select__caret {
              display: flex;
              align-items: center;
            }
          }
        }
        /* 工单类型 */
        .el-radio-group {
          width: 100%;
          .RadioGroup {
            margin-bottom: 5px;
            display: flex;
            justify-content: space-between;
            .el-radio {
              border: 1px solid #dcdfe6;
              border-radius: 4px;
              margin-right: 0;
              display: flex;
              justify-content: center;
              align-items: center;
              padding: 2px;
              .el-radio__label {
                padding-left: 0;
              }
            }
          }
        }
        /* 内容描述 */
        .el-textarea__inner {
          padding: 0 0 50px;
        }
      }
    }
  }
  .el-dialog__footer {
    padding: 0px 20px 20px;
    .el-button {
      padding: 5px 10px;
    }
  }
}
</style>
