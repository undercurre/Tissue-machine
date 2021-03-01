<template>
  <div class="BodyBox">
    <div class="BodyConten">
      <!-- <div class="baseInfo">
        <p>基础信息</p>
      </div>
      <div class="BaseLine"></div> -->
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>工单类型</span>
          </p>
        </div>
        <div class="ItemRightFir">
        </div>
      </div>
      <div class="MeetingItem">
        <el-radio-group v-model="radio">
          <el-radio :label="0">采购单</el-radio>
          <el-radio :label="1">维修单</el-radio>
          <el-radio :label="2">送货单</el-radio>
          <el-radio :label="3">印刷单</el-radio>
        </el-radio-group>
      </div>
      <div class="line"></div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>工单名称</span>
          </p>
        </div>
        <div style="width: 100%">
          <input
            v-model="FormName"
            class="ItemRightSec"
            type="text"
            id="formName"
            name="formT=N"
            placeholder="请输入工单名称 "
          />
        </div>
      </div>
      <div class="line"></div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>完成时限</span>
          </p>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemRightThir">
          <div class="startSpan">
            <span>开始</span>
          </div>
          <el-date-picker
            v-model="Firstday"
            placeholder="2020-10-29"
            :default-value="Firstday"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
          <div class="dateSpan">
            <img src="../CreateOrder/icon.png">
          </div>
          <div class="endSpan">
            <span>结束</span>
          </div>
          <el-date-picker
            v-model="LastDay"
            placeholder="2020-10-29"
            :default-value="LastDay"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
      </div>
      <div class="line"></div>
      <div class="MeetingItemContent">
        <div class="ItemLeft">
          <p>
            <span>内容描述</span>
          </p>
        </div>
        <div style="width: 100%">
          <el-input
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 5}"
            placeholder=""
            v-model="FormContent"
            resize="none"
          >
          </el-input>
        </div>
      </div>
      <div class="MeetingItem">
        <div class="ItemLeft">
          <p>
            <span>执行人</span>
          </p>
        </div>
        <div class="ItemRightFif">
          <div class="ItemRightFifOne">
            <el-select v-model="performer" placeholder="请选择" size="mini">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>
      </div>
      <div class="FlexJCCenter">
         <div class="CreateButton">
           <div class="Button" @click="createOrder">创 建</div>
        </div>
        <span>注：完结该工单后将推送给下一执行人确认</span>
      </div>
     <!--  <div class="FlexAround">
        <div class="abortButton">作废</div>
        <div class="confirmButton">确认修改</div>
      </div>
      <div class="FinishedButton">
        <div class="Button">已完结</div>
      </div> -->
    </div>
    <memberList v-if="memberVisible" ref="memberList" @reForm="getMember" />
  </div>
</template>

<style lang="scss" scoped>
.BodyBox {
  width: 100vw;
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
  input::-ms-input-placeholder{
    text-align: right;
  }
  input::-webkit-input-placeholder{
    text-align: right;
  }
  input:focus{
    outline: none;
  }
  .BodyConten {
    padding: 30px 20px 0;
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
      margin: 0 0 10px 0;
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 16px;
        p {
          width: 70px;
        }
      }
      /deep/.el-textarea {
        font-size: 14px;
        margin-top: 10px;
      }
      /deep/.el-textarea__inner {
        color: #333333;
      }
      .ItemRightFouth {
        width: 100%;

        border: none;

        font-weight: 400;

        font-size: 14px;
        color: #333333;
        background-color: rgba(235, 235, 235, 1);
        overflow: hidden;
        word-break: break-all;
      }
    }
    .line{
      border-bottom: 0.5px solid #CCCCCC;
      margin: 10px 0;

    }
    .MeetingItem {
      width: 100%;
      display: flex;
      align-items: center;
      margin: 0 0 5px 0;
      /deep/.el-radio-group{
        margin: 0 auto;
        .el-radio{
          margin-right: 10px;
          font-size: 13px;
        }
        .is-checked{
          .is-checked {
            .el-radio__inner {
              border-color: #092F40;
              background-color: #092F40;
            }
          }
        }
        .el-radio__input.is-checked+.el-radio__label{
          color: #082F40;
        }
      }
      .ItemLeft {
        display: flex;
        align-items: center;
        height: 40px;
        margin-right: 5px;
        font-size: 16px;
        p {
          width: 70px;
        }
        /* width: 20vw; */
      }
      .ItemRightFir {
        width: 100%;
        /deep/.el-icon-arrow-up:before {
          content: "\E6E1";
          color: black;
          font-weight: 700;
        }
        /deep/.el-select {
          width: 100%;
          .el-input {
            .el-input__suffix {
              display: flex;
              align-items: center;
            }
          }
          .el-input__icon {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
          }
          .el-input__inner {
            border-radius: 0;
            border: 1px solid black;
            color: black;
            height: 24px;
            line-height: 24px;
            padding-top: 0;
            padding-right: 0;
            padding-bottom: 0;
            padding-left: 10px;
          }
        }
      }
      .ItemRightSec {
        width: 100%;
        border-radius: 4px;
        height: 30px;
        border: none;
        border-radius: 4px;
        font-weight: 400;
        display: flex;
        align-items: center;
        font-size: 14px;
      }
      .ItemRightSec :hover{
        border-color: #eeeeee;
      }
      .ItemRightThir {
        height: 40px;
        width: 100%;
        font-weight: 400;
        display: flex;
        justify-content: space-around;
        align-items: center;
        font-size: 13px;
        .dateSpan {
          width: 10%;
          padding: 0 10px;
          img{
            width: 18px;
            height: 18px;
          }
        }
        .startSpan{
          width: 15%;
          span {
            display: block;
            width: 100%;
            text-align: center;
            white-space: nowrap;
            font-size: 14px;
            color: #BBBBBB;
          }
        }
        .endSpan{
          width: 15%;
          span {
            display: block;
            width: 100%;
            text-align: center;
            white-space: nowrap;
            font-size: 14px;
            color: #BBBBBB;
          }
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
          border: none;
          color: #333333;
          line-height: 24px;
          height: 24px;
          padding: 0;
          border-radius: 0;
          text-align: center;
        }
        /deep/.el-input__inner::placeholder{
          color: #333333;
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
          height: 100%;
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
     .FlexJCCenter {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 100%;
       span{
         font-size: 10px;
         margin-top: 10px;
         color: #333333;
       }
      .CreateButton {
        width: 96%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 30px;
        .Button {
          padding: 0;
          margin-top: 60px;
          width: 100%;
          height: 42px;
          color: #ffffff;
          background-color: #082F40;
          font-size: 18px;
          font-weight: bold;
          line-height: 25px;
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: 22.5px;
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
    .FlexAround {
      width: 100%;
      display: flex;
      justify-content: space-around;
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
    .FinishedButton {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 100px;
      .Button {
        padding: 0;
        margin-top: 15px;
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
import memberList from "../ChoosePeople/ChoosePeople";
let moment = require('moment');
export default {
  data() {
    return {
      //执行人
      performer: null,
      //单选按钮
      radio: 3,
      dataForm: {
        members: null,
      },
      FormType: "" /* 工单类型 */,
      FormName: "" /* 工单名称 */,
      Firstday: "" /* 开始时间 */,
      LastDay: "" /* 结束时间 */,
      FormContent: "" /* 工单内容 */,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      memberVisible: false,
      prolist: [],
      options: [],
      value1: [],
      value2: "",
      value3: "",
      permission: true, // 权限，控制底部是否显示
    };
  },
  components: {
    memberList,
  },
  created() {
    this.addInterceptors()
    this.initComCss()
  },
  mounted() {
    this.setCalendar()
    this.getOption()
  },
  methods: {
    initComCss(){
      document.body.style.backgroundColor="#FFFFFF";
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
      console.log(data);
      this.dataForm.members = data;
    },
    ChoosePeople() {
      this.memberVisible = true;
      this.$nextTick(() => {
        this.$refs.memberList.init();
      });
      // this.$router.push({name:'ChoosePeople'});
    },
    setCalendar(){
      //获取当前时间
      let date = new Date();
      //获取当前月的第一天
      let monthStart = date.setDate(1);
      //获取当前月
      let currentMonth = date.getMonth();
      //获取到下一个月，++currentMonth表示本月+1，一元运算
      let nextMonth= ++ currentMonth;
      //获取到下个月的第一天
      let nextMonthFirstDay = new Date(date.getFullYear(),nextMonth,1);
      //一天时间的毫秒数
      let oneDay=1000*60*60*24;

      //获取当前月第一天和最后一天
      let firstDay = moment(monthStart).format("YYYY-MM-DD");
      //nextMonthFirstDay-oneDay表示下个月的第一天减一天时间的毫秒数就是本月的最后一天
      let lastDay = moment(nextMonthFirstDay-oneDay).format("YYYY-MM-DD");
      //装载
      this.Firstday = firstDay;
      this.LastDay = lastDay;
    },
    getOption(){
      this.axios.get(process.env.VUE_APP_API_URL+"/sys/user/workerList").then(res => {
        if(res.data.code == 0){
          let userList = res.data.data
          userList.forEach((user) => {
            var option = {}
            option.label = user.realName
            option.value = user.userId
            this.options.push(option)
          })
          console.log("获取人员选择列表成功")
        }else{
          console.log("获取人员选择列表失败")
        }
      })
    },
    addInterceptors(){
      // 请求拦截器
      this.axios.interceptors.request.use(
          config => {
            config.headers['token'] = this.$cookie.get('token')// 请求头带上
            return config
          },
          error => {
            return Promise.reject(error)
          }
      )
    },
    createOrder(){
      this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/save",{
        name: this.FormName,
        startTime: this.Firstday+" 00:00:00",
        deadlineTime: this.LastDay+" 00:00:00",
        des: this.FormContent,
        workerId: this.performer,
        workType: this.radio,
        status: 0
      }).then(res => {
        console.log(res)
        if (res.data.code == 0) {
          this.$router.go(-1)
          console.log("工单创建上传成功")
        }else{
          console.log("工单创建上传失败")
        }
      })
    },
  },
};
</script>
