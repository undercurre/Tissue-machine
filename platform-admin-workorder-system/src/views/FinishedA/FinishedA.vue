<template>
  <div class="BodyBox">
    <div class="BodyTitle">
      <div class="TitlePoto">
        <img
          class="poto"
          src="http://172.19.0.177:2024/images/%E5%85%AC%E4%BC%97%E5%8F%B7%E8%81%8A%E5%A4%A9%E7%AA%97/u3.png"
        />
      </div>
      <div class="TitleTxt" v-if="FinishedStatus === 0">
        <p>工单状态：已作废</p>
      </div>
      <div class="TitleTxt" v-if="FinishedStatus === 1">
        <p>工单状态：已完结</p>
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
      <div class="baseInfo" v-if="FinishedStatus === 0">
        <p>进度信息</p>
      </div>
      <div class="baseInfo" v-if="FinishedStatus === 1">
        <p>完结情况</p>
      </div>
      <div class="BaseLine"></div>
      <div class="CauseAbourt" v-if="FinishedStatus === 0">
        <div class="ItemLeft">
          <p>
            <span>作废理由：</span>
          </p>
        </div>
        <div class="ItemRight">
          <p>
          采购内容有所变化，未确定新采购内容，故作废此工单。清单如下：12345664641321账号是阿拉克沙家
        </p>
        </div>
      </div>

      <div v-if="FinishedStatus === 1">
        <ul>
          <li v-for="item in prolist" v-bind:key="item.id">
            <p class="PMargin">
              <span class="spanLeft">
                &emsp;&emsp;&emsp;&nbsp;{{ item.id }}：</span
              >
              <span class="spanRight">{{ item.statu }}</span>
            </p>
            <p class="PMargin">
              <span class="spanLeft">区域定位：</span>
              <span class="spanRight">{{ item.location }}</span>
            </p>
          </li>
        </ul>
      </div>
      <div class="File" v-if="FinishedStatus === 1">
        <div class="FileLeft">
          <p>
            <span>&emsp;&emsp;附件：</span>
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
                  :fit="fit"
                ></el-image>
                <div v-if="item.have2">
                  <el-image
                    style="width: 70px; height: 70px"
                    class="Img"
                    :src="item.src2"
                    :preview-src-list="[item.src2]"
                    :fit="fit"
                  ></el-image>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div class="AbourtButton" v-if="FinishedStatus === 0">
        <div class="Button">已作废</div>
      </div>
      <div class="FinishedButton" v-if="FinishedStatus === 1">
        <div class="Button">已完结</div>
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
        p{
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
        background-color: #9c9c9c;
        font-size: 16px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 4px;
      }
    }
    @media screen and (max-height:570px){
      .AbourtButton{
        margin-top: 2vh!important;
      }
    }
    .AbourtButton {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 15vh;
      .Button {
        padding: 0;
        margin: 0 0 15px;
        width: 255px;
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
</style>

<script>
export default {
  data() {
    return {
      FinishedStatus: 0 ,/* 完结 */
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
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
  methods: {
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
