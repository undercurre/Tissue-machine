<template>
  <div class="BodyBox">
    <div class="BodyTitle">
      <div class="TitlePoto">
        <img
          class="poto"
          src="http://172.19.0.177:2024/images/%E5%85%AC%E4%BC%97%E5%8F%B7%E8%81%8A%E5%A4%A9%E7%AA%97/u3.png"
        />
      </div>
      <div class="TitleTxt" v-if="isClick === 0">
        <p>工单状态：进行中</p>
      </div>
      <div class="TitleTxt" v-if="isClick === 1">
        <p>工单状态：待下一执行人开始</p>
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
          <span class="DateFlex">2020-10-11</span>

          <span class="dateSpan">至</span>

          <span class="DateFlex">2020-10-12</span>
        </div>
      </div>
      <div class="MeetingItemContent">
        <div class="ItemLeft">
          <p>
            <span>内容描述：</span>
          </p>
        </div>
        <div class="ItemRightFouth">
          <p>
            已按基本采购单的采购内容完成采购，需采购下一批材料，清单如下：12345664641321账号是阿拉克沙家
          </p>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>执行人：&emsp; </span>
          </p>
        </div>
        <div class="ItemRightFif">
          <p>
            <span>李四</span>
          </p>
        </div>
      </div>
      <div class="baseInfo" >
        <p>进度信息</p>
      </div>
      <div class="BaseLine"></div>
      <div class="Location">
        <div class="ItemLeft">
          <p>
            <span>区域定位：</span>
          </p>
        </div>
        <div class="ItemRight">
          <div class="ItemRightOne">
            <p>佛山市禅城区祖庙街道卫国路按上级考核等级咖啡和</p>
          </div>
          <div class="ItemRightTwo">
            <div class="Button">获取</div>
          </div>
        </div>
      </div>

       <div class="FinishedSituation">
        <div class="ItemLeft">
          <p>
            <span>完成情况：</span>
          </p>
        </div>
        <div style="width: 100%">
          <el-input
            type="textarea"
            autosize
            placeholder=""
            v-model="FinishedContent"
          >
          </el-input>
        </div>
      </div>

      <div class="File" >
        <div class="FileLeft">
          <p>
            <span>附件：&emsp;&emsp;</span>
          </p>
        </div>
        <div class="PotoBox">
          <ul>
            <li v-for="item in imgList" v-bind:key="item.id">
              <div class="FlexBox">
                <el-image
                  style="width: 70px; height: 70px"
                  class="Img"
                  :src="item.src1"
                  :preview-src-list="[item.src1]"
                  fit="fill"
                ></el-image>
                <div v-if="item.have2">
                  <el-image
                    style="width: 70px; height: 70px"
                    class="Img"
                    :src="item.src2"
                    :preview-src-list="[item.src2]"
                    fit="fill"
                  ></el-image>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>

       <div class="NextPerson">
        <div class="ItemLeft">
          <p>
            <span>下一执行人:</span>
          </p>
        </div>
        <div class="ItemRight">
          <div class="ItemRightOne">
            <p>{{ dataForm.members }}</p>
          </div>
          <div class="ItemRightTwo">
            <div class="Button" @click="ChoosePeople">选择</div>
          </div>
        </div>
      </div>
       <div class="FinishedButton" v-if="dataForm.members == null">
        <div class="Button">完结工单</div>
      </div>
      <div class="FinishedButton" v-else-if="isClick == 0">
        <div class="Button" @click="FinshedAndNext">完结并指派</div>
      </div>
      <div class="FlexJCCenter" v-if="isClick == 1">
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
    <memberList v-if="memberVisible" ref="memberList" @reForm="getMember" />
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
    position: fixed;
    top: 0;
    left: 0;
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
    margin-top: 50px;
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
    .CauseAbourt {
      display: flex;
      align-items: flex-start;
      margin: 15px 0 5px 0;
      .ItemLeft {
        display: flex;
        align-items: flex-start;
        height: 40px;
        margin-right: 5px;
        font-size: 14px;
        p {
          width: 70px;
        }
      }
      .ItemRight {
        width: 100%;
        border: none;
        font-weight: 400;
        font-size: 14px;
        p {
          position: relative;
          padding: 0 5px 20px;
          word-break: break-all;
          word-wrap: break-word;
        }
      }
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
      }
      .ItemRightFouth {
        width: 100%;
        border: none;
        font-weight: 400;
        font-size: 14px;
        p {
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
        width: 217px;
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
        border: none;
        width: 180px;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        /deep/.el-date-editor.el-input,
        .el-date-editor.el-input__inner {
          width: 73px;
        }
        /deep/.el-input--prefix .el-input__inner {
          padding-left: 0;
        }
        /deep/.el-input--suffix .el-input__inner {
          padding-right: 0;
        }
        /deep/.el-icon-date:before {
          content: "";
        }
        /deep/.el-icon-circle-close:before {
          content: "";
        }
        /deep/.el-input__inner {
          border: none;
          color: black;
        }
        .dateSpan {
          /* margin: 0 22px 0 0; */
          margin: 0;
        }
      }

      .ItemRightFif {
        width: 102px;
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        align-items: center;
        font-size: 14px;
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
    .Location {
      display: flex;
      align-items: center;
      margin: 0 0 5px 0;
      width: 100%;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 14px;
        /* p {
          width: 70px;
        } */
      }
      .ItemRight {
          width: calc(100% - 75px);
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        .ItemRightOne {
          width: 70%;
          height: 70%;
          border-radius: 4px;
          background-color: #ebebeb;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          p {
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
        .ItemRightTwo {
          width: 20%;
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          .Button {
            width: 80%;
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
    }
     .FinishedSituation {
      display: flex;
      align-items: flex-start;
      margin: 0 0 10px 0;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 14px;
        p {
          width: 70px;
        }
      }
      /deep/.el-textarea {
        font-size: 13px;
      }
      /deep/.el-textarea__inner {
        background-color: rgba(235, 235, 235, 1);
        color: #333333;
        border: 1px solid rgba(255, 255, 255, 1);
        padding-top: 0;
        padding-right: 0;
        padding-bottom: 30px;
        padding-left: 0;
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
      .PotoBox {
        .FlexBox {
          display: flex;
          align-items: center;
          width: 100%;
          .Img {
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
        }
      }
    }
    .NextPerson {
      display: flex;
      align-items: center;
      margin: 0 0 5px 0;
      width: 100%;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 0;
        font-size: 14px;
      }
      .ItemRight {
        width: calc(100% - 80px);
        height: 40px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        .ItemRightOne {
          width: 70%;
          height: 70%;
          border-radius: 4px;
          background-color: #ebebeb;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          p {
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
        .ItemRightTwo {
          width: 20%;
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          .Button {
            width: 80%;
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
    }
    .FinishedButton {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      .Button {
        padding: 0;
        margin: 0 0 15px;
        width: 255px;
        height: 42px;
        color: #ffffff;
        background-color: green;
        font-size: 16px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 4px;
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

  }
}
</style>

<script>
import memberList from "../ChoosePeople/ChoosePeople";
export default {
  data() {
    return {
        abourt: false,/* 点击废除按钮 */
        isClick: 0,/* 是否完结并指派 */
        dataForm: {
        members: null,
      },
      FinishedContent:"",
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      memberVisible: false,
      imgList: [
        {
          id: "1",
          src1:
            "https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backgroundZpq.png",
          src2:
            "https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/cardScan3xZpq.png",
          have2: true,
        },
        /*  {
          id: "2",
          src1:
            "https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backgroundZpq.png",
          src2:
            "https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/cardScan3xZpq.png",
          have2: false,
        }, */
        /* {
          id: "3",
          src1:
            "https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/backgroundZpq.png",
          src2: "",
          have2: false,
        }, */
      ],
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
  components: {
    memberList,
  },
  methods: {
       Abourt() {
      this.abourt = !this.abourt;
    },
    FinshedAndNext(){
      this.isClick = 1;
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
     getMember(data) {
      console.log("5最终赋值改变",data);
      this.dataForm.members = data;
    },
    ChoosePeople() {
      this.memberVisible = true;
      this.$nextTick(() => {
        this.$refs.memberList.init();
      });
      // this.$router.push({name:'ChoosePeople'});
    },
  },
};
</script>
