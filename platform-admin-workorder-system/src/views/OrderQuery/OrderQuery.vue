<template>
  <div class="record_container" ref="container">
    <div class="topBar" v-if="topBar[type].length">
      <div class="bar-item" v-for="(item,index) in topBar[type]" :key="item" @click="chooseStatus(index)" :ref=index>
        <span>{{ item }}</span>
      </div>
    </div>
    <div class="search" v-if="type==0">
      <p>
        <span>搜索</span>
      </p>
      <p>
        <img src="../OrderQuery/search.png">
        <el-input
            v-model="keyword"
            placeholder="输入工单关键词"
        ></el-input>
        <el-button @click="queryClick">查询</el-button>
      </p>
    </div>
    <div  class="sort" v-if="type==0">
      <p>
        <span>筛选</span>
      </p>
      <p>
        <el-radio-group v-model="radio_power" size="mini" fill="#082F40">
          <el-radio-button label="0">我创建的</el-radio-button>
          <el-radio-button label="1">指派我的</el-radio-button>
        </el-radio-group>
      </p>
      <p>
        <span class="dateBox">
          <el-date-picker
                  v-model="firstDate"
                  :default-value="firstDate"
                  size="small"
                  value-format="yyyy-MM-dd"
              >
          </el-date-picker>
          <img src="../OrderQuery/down.png">
        </span>
        <span>
          一
        </span>
        <span class="dateBox">
          <el-date-picker
              v-model="secondDate"
              :default-value="secondDate"
              size="small"
              value-format="yyyy-MM-dd"
          >
          </el-date-picker>
          <img src="../OrderQuery/down.png">
        </span>
      </p>
      <p>
        <el-radio-group v-model="radio_status" size="mini" fill="#082F40">
          <el-radio-button label="">全部</el-radio-button>
          <el-radio-button label="0">待开始</el-radio-button>
          <el-radio-button label="1">进行中</el-radio-button>
          <el-radio-button label="2">已处理</el-radio-button>
          <el-radio-button label="3">已作废</el-radio-button>
          <el-radio-button label="4">被移除</el-radio-button>
        </el-radio-group>
      </p>
    </div>
    <div class="record">
      <nut-infiniteloading
          @loadmore="onInfinite"
          :is-show-mod="true"
          :has-more="isHasMore"
          :is-loading="isLoading"
          :threshold="200"
      >
        <ul class="list">
          <div class="item list-item" v-for="item in recordList" :key="item.id" @click="toDetail(item.id)">
            <a class="record_item">
              <div class="left" :style=statusColor[item.status]>
                {{ statusStr[item.status] }}
              </div>
              <div class="right">
                <p class="title">{{ item.name }}</p>
                <p>工单类型：{{ workTypeList.find(order => order.id == item.id).name }}</p>
                <p>指派人：{{ item.workerName }}</p>
                <p>完成时限：{{ item.startTime.substr(0,10) }}&nbsp;至&nbsp;{{ item.deadlineTime.substr(0,10) }}</p>
                <div class="line"></div>
                <p class="createTime">{{ item.createTime }}<img src="../OrderQuery/forward.png"></p>
              </div>
            </a>
          </div>
        </ul>
      </nut-infiniteloading>
    </div>
  </div>
</template>

<script>
let moment = require('moment');
export default {
  data () {
    return {
      workTypeList: null,
      isCreator: null,
      isWorker: null,
      radio_power: null,
      radio_status: null,
      type: this.$route.query.type,//0工单查询1历史工单2分配的工单3创建的工单
      recordList: [],
      firstDate: null,
      secondDate: null,
      keyword: null,
      isHasMore: true,
      isLoading: false,
      timer: null,
      page: 1,
      totalPage: null,
      statusColor:{
        0: 'background-color:#FF9700',
        1: 'background-color:#0091FF',
        2: 'background-color:#1FBF84',
        3: 'background-color:#FF3030'
      },
      statusStr: {
        0: "待开始",
        1: "进行中",
        2: "已处理",
        3: "已作废",
        4: "被移除"
      },
      topBar:[
        [],
        ["全部","已处理","已作废","被移除"],
        ["全部","待领取","进行中"],
        ["全部","待开始","进行中"]
      ],
    }
  },
  mounted() {
    this.setCalendar()
    if (this.type != 0){
      this.queryAction()
    }
    this.initComCss()
  },
  created() {
    this.addInterceptors()
  },
  destroyed() {
    clearTimeout(this.timer);
  },
  methods: {
    initComCss(){
      document.body.style.backgroundColor="#F5F5F5";
      if (this.type!=0) {
        this.$refs.container.style.marginTop = 55 + 'px';
        this.$refs[0][0].style.borderBottom = "3px solid #082F40"
      }
      this.axios.get(process.env.VUE_APP_API_URL+"/ad/worktype/queryAll").then( () => {
        this.workTypeList = res.data.list
      })
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
      this.firstDate = firstDay;
      this.secondDate = lastDay;
    },
    onInfinite () {
      this.isLoading = true;
      this.timer = setTimeout(() => {
        if (this.page <= this.totalPage) {
          this.page = this.page + 1;
          this.queryAction()
        } else {
          this.isHasMore = false;
        }
        this.isLoading = false;
      }, 100);
    },
    queryClick(){
      this.page = 1
      this.recordList = []
      this.queryAction()
    },
    queryAction(){
      if (this.radio_power == 0) {
        this.isCreator = 1
        this.isWorker = 0
      }
      else {
        this.isWorker = 1
        this.isCreator = 0
      }
      if (this.type == 2){
        this.isWorker = 1
        this.isCreator = 0
      }
      if (this.type == 3){
        this.isCreator = 1
        this.isWorker = 0
      }
      if (this.type == 0) {
        this.axios.get(process.env.VUE_APP_API_URL+"/ad/workorder/appList", {
          params: {
            limit: 4,
            page: this.page,
            deadlineTime: this.secondDate + " 00:00:00",
            startTime: this.firstDate + " 00:00:00",
            name: this.keyword,
            isWorker: this.isWorker,
            isCreator: this.isCreator,
            status: this.radio_status,
          }
        }).then(res => {
          console.log(res)
          if (res.data.code == 0) {
            this.totalPage = res.data.page.pages
            this.recordList = this.recordList.concat(res.data.page.records)
            console.log("工单查询成功")
          } else {
            console.log("工单查询失败")
          }
        })
      }else{
        if (this.type == 1) {
          this.axios.get(process.env.VUE_APP_API_URL+"/ad/workorder/appList", {
            params: {
              limit: 4,
              page: this.page,
              status: this.radio_status,
            }
          }).then(res => {
            console.log(res)
            if (res.data.code == 0) {
              this.totalPage = res.data.page.pages
              this.recordList = this.recordList.concat(res.data.page.records)
              console.log("工单查询成功")
            } else {
              console.log("工单查询失败")
            }
          })
        }else{
          this.axios.get(process.env.VUE_APP_API_URL+"/ad/workorder/appList", {
            params: {
              limit: 4,
              page: this.page,
              status: this.radio_status,
              isWorker: this.isWorker,
              isCreator: this.isCreator,
            }
          }).then(res => {
            console.log(res)
            if (res.data.code == 0) {
              this.totalPage = res.data.page.pages
              this.recordList = this.recordList.concat(res.data.page.records)
              console.log("工单查询成功")
            } else {
              console.log("工单查询失败")
            }
          })
        }
      }
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
    chooseStatus(index){
      for (var j=(this.topBar[this.type].length-1); j>=0; j--)
        this.$refs[j][0].style.borderBottom = ""
      this.$refs[index][0].style.borderBottom = "3px solid #082F40"
      if (index == 0)
        this.radio_status = ''
      else {
        if (this.type == 1)
          this.radio_status = index+1
        else
          this.radio_status = index-1
      }
      this.page = 1
      this.recordList = []
      this.queryAction()
    },
    toDetail(itemId){
      this.$router.push({ name: 'orderDetail', query: { id: itemId,fromRecType: this.type }})
    }
  }
}
</script>

<style  lang="scss" scoped>
.record_container {
  margin: 20px;
  .topBar{
    width: 100%;
    display: flex;
    justify-content: space-around;
    background-color: #FFFFFF;
    height: 35px;
    position: fixed;
    top: 0;
    margin-left: -20px;
    z-index: 9999;
    .bar-item{
      padding: 0 6px;
      line-height: 35px;
      font-size: 14px;
      color: #999999;
    }
  }
  p{
    margin-top: 10px;
    margin-bottom: 20px;
  }
  .search, .sort, .item {
    width: 100%;
    background-color: #ffffff;
    margin-bottom: 20px;
    border-radius: 5px;
    padding: 10px;
    font-size: 16px;
    box-sizing: border-box;
    font-weight: 550;
  }
  .search {
    position: relative;
    img{
      position: absolute;
      left: 5%;
      top: 67px;
      width: 18px;
      z-index: 100;
    }
    .el-input {
      width: 70%;
      /deep/.el-input__inner{
        padding: 0 25px;
      }
      font-size: 14px;
    }
    .el-button {
      width: 24%;
      float: right;
      background-color: #082F40;
      color: #ffffff;
      border-radius: 3px;
      font-size: 14px;
    }
  }
  .sort {
    .dateBox{
      position: relative;
      img{
        position: absolute;
        right: 4px;
        top: 2px;
        margin-left: 6px;
      }
    }
    :nth-child(2n) {
      /deep/.el-radio-group {
        width: 100%;
        .el-radio-button {
          width: 30%;
          text-align: center;
        }
      }
      /deep/.el-radio-button__inner {
        width: 80%;
        border-radius: 12px;
        background-color: #F5F5F5;
        margin: 6px;
      }
    }
    :nth-child(3) {
      /deep/.el-date-editor {
        width: 34%;
        .el-input__inner{
          padding: 0 15px 0 0;
          text-align: center;
          background-color: #F5F5F5;
          border: none;
          border-radius: 12px;
          color: #082F40;
          font-weight: bold;
        }
      }
      /deep/.el-input__icon{
        display: none;
      }
      span{
        margin: 2%;
      }
    }
  }
  .record{
    .item{
      padding: 0;
    }
    a:link,
    a:visited,
    a:hover,
    a:active{
      color: #333333;
      text-decoration: none;
    }
    .record_item{
      border-radius: 5px;
      display: flex;
      justify-content: space-between;
      .left{
        padding: 10px;
        writing-mode: vertical-lr;
        text-align: center;
        background-color: #FF9700;
        border-radius: 5px 0 0 5px;
        color: #FFFFFF;
      }
      .right{
        width: 90%;
        padding: 10px;
        p{
          font-size: 13px;
          font-weight: normal;
        }
        .title{
          font-size: 16px;
          font-weight: bold;
        }
        .createTime{
          font-size: 12px;
          color: #999999;
          margin-bottom: 0px;
          position: relative;
        }
        img{
          width: 15px;
          height: 15px;
          position: absolute;
          right: 0;
        }
      }
    }
    .record_item::after{
      width: 0;
      content: '';
    }
    .line{
      border-bottom: 0.5px solid #CCCCCC;
      margin: 10px 0;
    }
  }
}
</style>
