<template>
  <div class="BodyBox">
    <div class="BodyTitle">
      <div class="TitlePoto">
        <img
          class="poto"
          src="http://172.19.0.177:2024/images/%E5%85%AC%E4%BC%97%E5%8F%B7%E8%81%8A%E5%A4%A9%E7%AA%97/u3.png"
        />
      </div>
      <div class="TitleTxt">
        <p>工单状态：待开始</p>
      </div>
    </div>
    <div class="BodyConten">
      <div class="baseInfo">
        <p>基础信息</p>
      </div>
      <div class="BaseLine"></div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>工单类型：</span>
          </p>
        </div>
        <div class="ItemRightFir">
          <p>
            <span>采购单</span>
          </p>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>工单名称：</span>
          </p>
        </div>
        <div class="ItemRightSec">
          <p>
            <span>广告材料采购工单</span>
          </p>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>完成时限：</span>
          </p>
        </div>
        <div class="ItemRightThir">
          <el-date-picker v-model="value2" type="date" placeholder="2020-10-29">
          </el-date-picker>
          <span class="dateSpan">至</span>
          <el-date-picker v-model="value3" type="date" placeholder="2020-10-30">
          </el-date-picker>
          <!--   <nut-datepicker
            :is-visible="isVisible"
            type="date"
            title="请选择日期"
            :is-show-chinese="false"
            @close="switchPicker('isVisible')"
            @choose="setChooseValue"
          ><p>选择时间</p>
          </nut-datepicker> -->
        </div>
      </div>
      <div class="MeetingItemContent">
        <div class="ItemLeft">
          <p>
            <span>内容描述：</span>
          </p>
        </div>
        <div class="ItemRightFouth">
          <div>
            已按基本采购单的采购内容完成采购，需采购下一批材料，清单如下：12345664641321账号是阿拉克沙家
          </div>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>执行人：&emsp; </span>
          </p>
        </div>
        <div class="ItemRightFif">
          <div class="ItemRightFifOne">
            <p>
              <span>李四</span>
            </p>
          </div>
          <div class="ItemRightFifTwo">
            <div class="PersonButton">选择</div>
          </div>
        </div>
      </div>
      <div class="FlexJCCenter">
        <div class="FlexAround">
          <div class="abortButton" @click="Abourt">作废</div>
          <div class="confirmButton">确认修改</div>
        </div>
        <div class="Abourt" v-if="abourt">
          <el-input
            type="textarea"
            autosize
            placeholder="点击填写作废理由"
            v-model="FormContent"
          >
          </el-input>
          <div class="Confirm">
            <div class="Button">确认</div>
          </div>
        </div>
        <div class="FinishedButton">
          <div class="Button">待开始</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.BodyBox {
  width: 100vw;
  /*   height: 935px; */
  position: relative;
  font-family: "PingFang-SC-Bold", "PingFang SC Bold", "PingFang SC";
  font-weight: 700;
  font-style: normal;
  font-size: 14px;
  color: #333333;
  .BodyTitle {
    width: 100vw;
    height: 50px;
    background-color: rgba(255, 255, 255, 1);
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    box-shadow: 0px 3px 5px rgba(201, 201, 201, 0.349019607843137);
    .TitlePoto {
      margin-left: 2.5vw;
      margin-right: 1.2vw;
      display: flex;
      align-items: center;
      justify-content: center;
      .poto {
        width: 4.5vw;
        height: 4.5vw;
      }
    }
    .TitleTxt {
      height: 4.5vw;
      font-family: "PingFang-SC-Bold", "PingFang SC Bold", "PingFang SC";
      font-weight: 700;
      font-style: normal;
      font-size: 18px;
      color: #000000;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
  .BodyConten {
    padding: 30px 10px 0;
    .baseInfo {
      width: 65px;
      height: 25px;
      background-color: rgba(242, 242, 242, 1);
      border: none;
      border-top-left-radius: 4px;
      border-top-right-radius: 4px;
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      padding: 2px 15px 0;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
    }
    .BaseLine {
      background-color: rgba(242, 242, 242, 1);
      border: none;
      border-top-left-radius: 0;
      border-top-right-radius: 0;
      border-bottom-left-radius: 4px;
      border-bottom-right-radius: 0;
      height: 4px;
    }
    .MeetingItemContent {
      display: flex;
      align-items: flex-start;
      margin: 0 0 5px 0;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 14px;
        p {
          width: 70px;
        }
        /* width: 70px; */
      }
      .ItemRightFouth {
        width: 100%;
        background-color: rgba(235, 235, 235, 1);
        /* line-height: 20px; */
        border: none;
        /* border-radius: 4px; */
        font-weight: 400;
        /* display: flex;
        align-items: flex-start; */
        font-size: 14px;
        /* white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
            word-break: break-all; */
        div {
          position: relative;
          padding: 5px 5px 20px;
          word-break: break-all;
          word-wrap: break-word;
        }
      }
    }
    .MeetingItem {
      display: flex;
      align-items: center;
      margin: 0 0 5px 0;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 14px;
        p {
          width: 70px;
        }
        /* width: 20vw; */
      }
      .ItemRightFir {
        width: 217px;
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        font-size: 14px;
      }
      .ItemRightSec {
        background-color: rgba(235, 235, 235, 1);
        width: 100%;
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        align-items: center;
        font-size: 14px;
      }
      .ItemRightThir {
        height: 40px;
        width: 100%;
        font-weight: 400;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 13px;
        .dateSpan {
          width: 25px;
          padding: 0 10px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        /deep/.el-date-editor.el-input,
        .el-date-editor.el-input__inner {
          width: calc((100%-45px) / 2);
          padding: 3px 2px;
        }
        /deep/.el-input--prefix .el-input__inner {
          padding-left: 0;
        }
        /deep/.el-input--suffix .el-input__inner {
          padding-right: 0;
        }
        /deep/.el-icon-circle-close:before {
          content: ""; /*自带的圆形的删除图标 */
        }
        /deep/.el-input__icon {
          width: 0; /* 自带删除图标大小 */
        }
        /deep/.el-input__prefix,
        .el-input__suffix {
          width: 0;
          height: 0; /* 自带的日历图标所占大小 */
        }
        /deep/.el-icon-date:before {
          content: ""; /* 自带的日历图标 */
        }
        /deep/.el-input__inner {
          border: 1px solid black;
          color: black;
          line-height: 24px;
          height: 24px;
          padding: 0;
          border-radius: 0;
          text-align: center;
        }
      }

      .ItemRightFif {
        width: 100%;
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        .ItemRightFifOne {
          width: 70%;
          padding: 10px 0;
          background-color: #ebebeb;
          display: flex;
          justify-content: flex-start;
          align-items: center;
        }
        .ItemRightFifTwo {
          width: 20%;
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          .PersonButton {
            width: 51px;
            height: 20px;
            background-color: rgba(0, 153, 255, 1);
            color: #ffffff;
            font-size: 12px;
            font-weight: 400;
            border-radius: 4px;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }
      .ItemRightSix {
        border-radius: 4px;
        font-weight: 400;
        font-size: 14px;
        /deep/.el-select {
          display: inline-block;
          position: relative;
          background-color: rgba(228, 228, 228, 1);
          .el-tag--info {
            color: #333333;
            background-color: rgba(228, 228, 228, 1);
          }
          .el-tag {
            font-size: 14px;
            font-weight: 400;
          }
          .el-input__inner {
            background-color: rgba(228, 228, 228, 1);
          }
          .el-icon-arrow-up:before {
            content: "\E790";
          }
          .el-select__caret {
            color: black;
          }
          .el-input__icon {
            font-size: 14px;
          }
        }
      }
    }
    .TableDemo {
      display: flex;
      width: 370px;
      /deep/.el-table {
        td,
        th {
          padding: 0;
        }
        .cell {
          line-height: 1;
          width: auto;
        }
      }
    }
    .PMargin {
      margin: 15px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      .spanLeft {
        margin-right: 5px;
        font-size: 14px;
      }
      .spanRight {
        width: 120px;
        font-weight: 400;
        font-size: 13px;
      }
    }
    .File {
      display: flex;
      align-items: flex-start;
      margin: 15px 0;
      .FileLeft {
        display: flex;
        align-items: center;
        margin-right: 5px;
        font-size: 14px;
      }
      .PotoBox > div {
        display: flex;
      }
      /deep/.el-upload--picture-card {
        width: 70px;
        height: 70px;
        border-style: solid;
        border-color: #c0ccda;
        border-radius: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f2f2f2;
        margin-right: 20px;
      }
      /deep/.el-upload-list--picture-card .el-upload-list__item {
        width: 70px;
        height: 70px;
        border-radius: 0;
      }
    }
    .FlexJCCenter {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 100%;
      .FlexAround {
        width: 70%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 30px;
        .abortButton {
          width: 97px;
          height: 33px;
          background-color: #cc0033;
          color: #ffffff;
          font-size: 14px;
          font-weight: 700;
          border-radius: 4px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .confirmButton {
          width: 97px;
          height: 33px;
          background-color: #00cc33;
          color: #ffffff;
          font-size: 14px;
          font-weight: 700;
          border-radius: 4px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
      .Abourt {
          margin-top: 10px;
        width: 70%;
        /deep/.el-textarea__inner {
          background-color: rgba(235, 235, 235, 1);
          color: #333333;
          border: 1px solid rgba(255, 255, 255, 1);
          padding-top: 0;
          padding-right: 0;
          padding-bottom: 60px;
          padding-left: 0;
          border-top-left-radius: 4px;
          border-top-right-radius: 4px;
          border-bottom-left-radius: 0;
          border-bottom-right-radius: 0;
        }

        .Confirm {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;

          .Button {
          border-top-left-radius: 0;
          border-top-right-radius: 0;
          border-bottom-left-radius: 4px;
          border-bottom-right-radius: 4px;
            padding: 0;
            width: 100%;
            height: 42px;
            color: #ffffff;
            background-color: red;
            font-size: 16px;
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 4px;
          }
        }
      }

      .FinishedButton {
        width: 70%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 30px;
        .Button {
          padding: 0;
          margin-top: 15px;
          width: 100%;
          height: 42px;
          color: #ffffff;
          background-color: #9c9c9c;
          font-size: 16px;
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: 4px;
        }
      }
    }
    .finishedItem {
      display: flex;
      align-items: center;
      margin: 15px 0;
      .ItemLeft {
        display: flex;
        align-items: center;
        /* height: 40px; */
        margin-right: 5px;
        font-size: 14px;
      }
      .ItemRight {
        width: 217px;
        /*  height: 40px; */
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        font-size: 13px;
      }
    }
  }
}
</style>

<script>
export default {
  data() {
    return {
      abourt: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      prolist: [
        {
          id: "B",
          statu: "已按采购单的采购内容完成采购。",
          location: "佛山市禅城区祖庙街道卫国路",
        },
        {
          id: "C",
          statu: "已按采购单的采购内容完成采购。",
          location: "佛山市禅城区祖庙街道卫国路床上看的卡拉大量看到啦",
        },
      ],
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
      value1: [],
      value2: "",
      value3: "",
      isVisible: true,
      permission: true, // 权限，控制底部是否显示
    };
  },
  methods: {
    Abourt() {
      this.abourt = !this.abourt;
    },
    switchPicker(param) {
      this[`${param}`] = !this[`${param}`];
    },
    setChooseValue(param) {
      this.date = param[3];
    },
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
    },
  },
};
</script>
