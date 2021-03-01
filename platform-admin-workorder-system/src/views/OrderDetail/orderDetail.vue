<template>
  <div class="detail_container">
    <div class="titleBar">
      <span class="status_box" ref="statusBox"></span>
      <span>基础信息（{{ statusStr[status][roleId] }}）</span>
    </div>
    <div class="line"></div>
    <p>
      <span class="title">工单类型：</span>{{ orderTypeStr[orderType] }}
    </p>
    <p>
      <span class="title">工单名称：</span>
      <el-input v-if="(roleId==0&&status==0)" size="mini" v-model="orderName"></el-input>
      <span v-if="(status!=0&&roleId==0)||roleId==1">广告材料采购工单</span>
    </p>
    <p>
      <span class="title">完成时限：</span>
      <el-date-picker
          v-if="roleId==0&&status==0"
          v-model="firstDate"
          placeholder="2020-10-29"
          :default-value="firstDate"
          size="mini"
      >
      </el-date-picker>
      <span v-if="(status!=0&&roleId==0)||roleId==1">{{ firstDate.substr(0,10) }}</span>
      <span class="between">至</span>
      <el-date-picker
          v-if="roleId==0&&status==0"
          v-model="secondDate"
          placeholder="2020-10-29"
          :default-value="secondDate"
          size="mini"
      >
      </el-date-picker>
      <span v-if="(status!=0&&roleId==0)||roleId==1">{{ secondDate.substr(0,10) }}</span>
    </p>
    <p>
      <span class="title">内容描述：</span>
      <el-input v-if="roleId==0&&status==0" type="textarea" size="mini" resize="none" :autosize="{ minRows: 3, maxRows: 5}" v-model="formContent"></el-input>
      <span v-if="(status!=0&&roleId==0)||roleId==1" class="content">{{ formContent }}</span>
    </p>
    <p>
      <span class="title">执行人：</span>
      <span v-if="(status!=0&&roleId==0)||roleId==1">{{ workerName }}</span>
      <el-select v-if="roleId==0&&status==0" v-model="performerId" placeholder="请选择" size="mini">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </p>
    <div class="line" v-if="(status==1&&roleId==1)||status==2"></div>
    <div class="titleBar ing" v-if="status==3||((status==1||(status==2&&sonStatus==0))&&roleId==1)">
      <span>进度信息</span>
      <img v-if="status==3" src="../OrderDetail/invalid.png" class="invalid_icon">
      <img v-if="status==4" src="../OrderDetail/remove.png" class="invalid_icon">
    </div>
    <div class="titleBar" v-if="status==2&&sonStatus!=0">
      <span>完结信息</span>
    </div>
    <div class="line" v-if="status==3||(status==1&&roleId==1)||status==2"></div>
    <p v-if="((status==1||(status==2&&sonStatus==0))&&roleId==1)">
      <span class="title">区域定位：</span>
      <el-input class="w60" size="mini" disabled v-model="address"></el-input>
      <img class="place_icon" src="../OrderDetail/place.png" @click="getLocation">
    </p>
    <p v-if="roleId==1&&(status==1||(status==2&&sonStatus==0))">
      <span class="title">完成情况：</span>
      <el-input v-model="finishInfo" type="textarea" size="mini" resize="none" :autosize="{ minRows: 3, maxRows: 5}" class="w60"></el-input>
    </p>
    <div class="finish" v-if="status==2&&sonStatus!=0">
      <p v-if="roleId==0">
        <span class="title">{{ workerName }}：</span>{{ remark }}
      </p>
      <p>
        <span class="title">区域定位：</span>{{ address }}
      </p>
      <p v-if="roleId==1">
        <span class="title">完成情况：</span>{{ remark }}
      </p>
    </div>
    <div v-if="status==2&&roleId==0">
      <div class="finish" v-for="item in sonList" :key="item.id">
        <p>
          <span class="title">{{ item.workerName }}：</span>{{ item.remark }}
        </p>
        <p>
          <span class="title">区域定位：</span>{{ item.address }}
        </p>
      </div>
    </div>
    <p v-if="status==2&&sonStatus!=0">
      <span class="title">附件：</span>
      <vue-preview :slides="thumbsList" class="prew"></vue-preview>
    </p>
    <p class="imgList" v-if="roleId==1&&(status==1||(status==2&&sonStatus==0))">
      <span class="title">附件：</span>
      <vue-preview :slides="thumbsList" class="prew"></vue-preview>
      <nut-imagepicker
          :longTapTime="longTapTime"
          v-loading.fullscreen.lock="fullscreenLoading"
          @imgMsg="imgMsg"
          :width="70"
          :height="70"
          :margin="10"
          :max="3"
          :imgList.sync="imgList"
          delMode="longtap"
      >
      </nut-imagepicker>
    </p>
    <p v-if="roleId==1&&(status==1||(status==2&&sonStatus==0))">
      <span class="title w40">下一个执行人：</span>
      <el-select v-model="next_performer" placeholder="请选择" size="mini" class="w60">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </p>
    <p v-if="status==3">
      <span class="title">作废理由：</span>
      <span v-if="roleId==1||status==3" class="reason">{{ invalidReason }}</span>
    </p>
    <div class="button_bar" v-if="(roleId==0&&status==0)||(sonStatus==0&&roleId==1&&status==2)">
      <el-button size="small" class="left" @click="invalidDialogVisible = true">作废</el-button>
      <el-button size="small" class="right" @click="confirmDialogVisible = true">确认修改</el-button>
    </div>
    <div>
      <el-button class="center" v-if="(status==1||status==0)&&roleId==1" @click="nextStatus">{{ buttonStr[status][roleId] }}</el-button>
    </div>
    <p class="remind" v-if="roleId==1&&status==1">注：完结该工单后将推送给下一执行人确认</p>
    <el-dialog
        :visible.sync="invalidDialogVisible"
        width="80%"
        center
        top="30vh">
      <el-input type="textarea" resize="none" :autosize="{ minRows: 4, maxRows: 6}" v-model="invalidReason" placeholder="填写作废理由！"></el-input>
      <span slot="footer" class="dialog-footer">
          <button @click="invalidConfirm" class="gray" ref="invalidBtn">确定作废</button>
      </span>
    </el-dialog>
    <el-dialog
        :visible.sync="confirmDialogVisible"
        width="80%"
        center
        top="30vh">
        <span>是否确认修改此工单？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="fixConfirm">确定修改</el-button>
        </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      formRecType: null,
      itemId: null,
      sonOrderId: null,
      status: null,
      roleId: null,
      sonLock: false,
      orderName: null,
      firstDate: null,
      secondDate: null,
      formContent: null,
      performerId: null,
      workerName: null,
      invalidReason: null,
      sonStatus: null,
      invalidLock: false,
      address: '中国',
      options: [],
      next_performer: null,
      orderType: null,
      sonList: [],
      thumbsList: [],
      lat: null,
      lng: null,
      finishInfo: null,
      orderTypeStr: {
        0: '采购单',
        1: '维修单',
        2: '送货单',
        3: '印刷单'
      },
      statusStr: {
        0: ['工单待领取', '工单待领取'],
        1: ["进行中", "进行中"],
        2: ["已完结", "已完结"],
        3: ["已作废", "已作废"]
      },
      buttonStr: {
        0: ["","确认领取"],
        1: ["","完成"]
      },
      statusBox: {
        0: '#FF9700',
        1: '#0091FF',
        2: '#1FBF84',
        3: '#FF3030'
      },
      imgList: [],
      imageList: [],
      longTapTime: 1000,
      invalidDialogVisible: false,
      confirmDialogVisible: false,
      fullscreenLoading: false,
    }
  },
  methods: {
    init() {
      this.itemId = this.$route.query.id
      this.formRecType = this.$route.query.formRecType
    },
    getLocation(){
      // var geolocation = new qq.maps.Geolocation("65SBZ-LZKE4-U3VUG-DHX4S-5FJNK-XGFP5", "myapp");
      // geolocation.getLocation(res => {
      //   this.lat = res.lat
      //   this.lng = res.lng
      //   this.axios.get('/api/ad/workorder/getAddress',{
      //     params:{
      //       longitude: this.lng,
      //       latitude: this.lat,
      //       get_poi: "1"
      //     }
      //   }).then(res => {
      //     console.log(res)
      //     this.address = res.data.address.result.address
      //   })
      //   console.log(res)
      // })
      console.log('11')
    },
    getDetail() {
      this.axios.get(process.env.VUE_APP_API_URL+"api/ad/workorder/info/" + this.itemId).then(res => {
        if (res.data.code == 0) {
          var order = res.data.workorder
          this.status = order.status
          if (order.creatorId == this.$cookie.get("userId"))
            this.roleId = 0
          else
            this.roleId = 1
          this.orderType = order.workType
          this.orderName = order.name
          this.firstDate = order.startTime
          this.secondDate = order.deadlineTime
          this.formContent = order.des
          this.performerId = order.workerId
          this.workerName = order.workerName
          this.invalidReason = order.reason
          this.remark = order.remark
          this.address = order.address
          this.sonList = order.sonWorkOrderList
          try {
            this.sonStatus = order.sonWorkOrderList[0].status
            this.next_performer = order.sonWorkOrderList[0].workerId
            this.finishInfo = order.sonWorkOrderList[0].des
            this.sonOrderId = order.sonWorkOrderList[0].id
          }catch (e) {
            console.log("没有子单")
          }
          order.imageList.forEach((item, index) => {
            item.w = 600;   //设置以大图浏览时的宽度
            item.h = 400;     //设置以大图浏览时的高度
            item.src = item.imageUrl;  //大图
            item.msrc = item.imageUrl;  //小图
            item.alt = 'picture' + (index + 1);
            item.title = '附件' + (index + 1);
          })
          this.thumbsList = order.imageList
          for (let item of this.sonList){
            if (item.status == 0)
              this.sonLock = 1
          }
          console.log("工单详情获取成功")
        } else {
          console.log("工单详情获取失败")
        }
      })
    },
    invalidConfirm() {
      if (!this.invalidLock) {
        return 0
      } else {
        if (this.sonStatus == 0 && this.roleId == 1 && this.status == 2) {
          this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/update", {
            id: this.sonOrderId,
            reason: this.invalidReason,
            status: 3
          }).then(res => {
            if (res.data.code == 0) {
              console.log("工单修改成功")
            } else {
              console.log("工单修改失败")
            }
          })
          this.invalidDialogVisible = false
        } else {
          this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/update", {
            id: this.itemId,
            reason: this.invalidReason,
            status: 3
          }).then(res => {
            if (res.data.code == 0) {
              console.log("工单修改成功")
            } else {
              console.log("工单修改失败")
            }
          })
          this.invalidDialogVisible = false
        }
      }
    },
    fixConfirm() {
      if (this.sonStatus == 0 && this.roleId == 1 && this.status == 2) {
        var image;
        this.thumbsList.forEach(item => {
          image.imageUrl = item.src
          image.workOrderId = this.sonOrderId
          this.imageList.push(image)
        })
        this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/update", {
          id: this.sonOrderId,
          remark: this.finishInfo,
          imageList: this.imageList,
          workerId: this.next_performer,
          address: this.address,
          latitude: this.lat,
          longitude: this.lng
        }).then(res => {
          if (res.data.code == 0) {
            console.log("工单修改成功")
          } else {
            console.log("工单修改失败")
          }
        })
        this.confirmDialogVisible = false
      } else {
        this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/update", {
          id: this.itemId,
          name: this.orderName,
          startTime: this.firstDate + " 00:00:00",
          deadlineTime: this.secondDate + " 00:00:00",
          des: this.formContent,
          workerId: this.performerId,
        }).then(res => {
          if (res.data.code == 0) {
            console.log("工单修改成功")
          } else {
            console.log("工单修改失败")
          }
        })
        this.confirmDialogVisible = false
      }
    },
    nextStatus(){
      if (this.status==0){
        this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/update", {
          id: this.itemId,
          status: this.status+1
        }).then(res => {
          if (res.data.code == 0) {
            this.getDetail()
            console.log("工单修改成功")
          } else {
            console.log("工单修改失败")
          }
        })
      }else {
        this.axios.post(process.env.VUE_APP_API_URL+"/ad/workorder/finishWork", {
          id: this.itemId,
          remark: this.finishInfo,
          imageList: this.imageList,
          workerId: this.next_performer,
          address: this.address,
          latitude: this.lat,
          longitude: this.lng
        }).then(res => {
          if (res.data.code == 0) {
            this.getDetail()
            console.log("工单修改成功")
          } else {
            console.log("工单修改失败")
          }
        })
      }
    },
    addInterceptors() {
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
    getOption() {
      this.axios.get(process.env.VUE_APP_API_URL+"/sys/user/workerList").then(res => {
        if (res.data.code == 0) {
          let userList = res.data.data
          userList.forEach((user) => {
            var option = {}
            option.label = user.realName
            option.value = user.userId
            this.options.push(option)
          })
          console.log("获取人员选择列表成功")
        } else {
          console.log("获取人员选择列表失败")
        }
      })
    },
    initComCss() {
        this.$refs.statusBox.style.backgroundColor = this.statusBox[this.status]
        document.body.style.backgroundColor="#FFFFFF";
    },
    imgMsg(data) {
      if (data.code == 2) {
        let imgFormData = new FormData()
        imgFormData.append('file',data.msg[0])
        this.openFullScreen()
        this.axios.post(process.env.VUE_APP_API_URL+"/sys/oss/uploadCircularImg", imgFormData , {timeout: 1000000}).then(result => {
          if (result.data.code == 0) {
            let image = {}
            image.imageUrl = result.data.url
            image.workOrderId = this.itemId
            this.imageList.push(image)
            console.log(result)
            console.log("附件数据上传成功")
            this.closeFullScreen()
          } else {
            console.log("附件数据上传失败")
          }
        })
      }
    },
    //加载中
    openFullScreen() {
      this.fullscreenLoading = true;
    },
    closeFullScreen() {
      this.fullscreenLoading = false
    },
  },
  created() {
    this.init()
    this.addInterceptors()
    this.getOption()
    this.getDetail()
  },
  mounted() {
  },
  destroyed() {
  },
  watch: {
    status() {
      this.$nextTick(() => {
        this.initComCss()
      })
    },
    sonStatus(){
      if (this.sonStatus == 0 && this.roleId == 1 && this.status == 2){
        this.statusBox[2] = this.statusBox[0]
        this.statusStr[2][0] = '待下一个执行人开始'
        this.statusStr[2][1] = '待下一个执行人开始'
      }
    },
    next_performer(val){
      if (val != null)
        this.buttonStr[this.status][this.roleId] = '完成并指派'
    },
    invalidReason() {
      if (this.invalidReason.length != 0) {
        this.$refs.invalidBtn.style.backgroundColor = '#082F40'
        this.invalidLock = true
      } else {
        this.$refs.invalidBtn.style.backgroundColor = '#666666'
        this.invalidLock = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.detail_container{
  .titleBar{
    margin: 30px;
    font-size: 16px;
    display: flex;
    .status_box{
      display: inline-block;
      width: 4px;
      height: 16px;
      border-radius: 2.5px;
      margin-right: 10px;
    }
    span{
      vertical-align: text-top;
      font-weight: bold;
    }
    .invalid_icon{
      width: 100px;
      height: 100px;
    }
  }
  .ing{
    justify-content: space-between;
    align-items: flex-end;
    span{
      margin-left: 14px;
    }
  }
  .line{
    border-bottom: 0.5px solid #CCCCCC;
    margin: 10px 0;
  }
  p{
    margin: 30px 34px;
    font-size: 14px;
    span{
      vertical-align: middle;
    }
    .prew {
      display: inline-block;
      vertical-align: top;
      /deep/ .my-gallery {
        img {
          width: 20vw;
          height: 20vw;
          margin-right: 10px;
        }
      }
    }
    .content{
      width: 70%;
      display: inline-block;
      line-height: 1.5;
      vertical-align: text-top;
    }
    .title{
      display: inline-block;
      font-weight: bold;
      width: 28%;
    }
    .w40{
      width: 40%;
    }
    .el-input{
      width: 70%;
    }
    .el-date-editor{
      width: 30%;
      /deep/.el-input__inner{
        padding: 0;
        text-align: center;
      }
      /deep/.el-input__inner::placeholder{
        color: #333333;
      }
      /deep/.el-input__prefix {
        .el-input__icon {
          display: none;
        }
      }
    }
    .between{
      margin: 2.5vw;
      color: #666666;
      font-weight: bold;
    }
    .el-textarea{
      width: 70%;
      vertical-align: text-top;
    }
    .w60{
      width: 52%;
    }
    .place_icon{
      background-color: #082F40;
      width: 5vw;
      height: 5vw;
      margin: 0 auto;
      margin-left: 10px;
      vertical-align: middle;
      border-radius: 2px;
      padding: 0 10px;
    }
  }
  .imgList{
    display: flex;
    justify-content: flex-start;
    /deep/.img-item a img{
      border-radius: 0;
    }
    /deep/.nut-imagepicker .img-list .add-icon i{
      color: #000000;
    }
    /deep/.nut-imagepicker .img-list .add-icon{
      border: 1px solid #CCCCCC;
      border-radius: 0;
    }
  }
  .el-select{
    width: 70%;
  }
  .reason{
    display: inline-block;
    width: 70%;
    vertical-align: text-top;
    line-height: 1.5;
  }
  .button_bar{
    margin-top: 100px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-around;
    .el-button{
      width: 35%;
      border-radius: 5px;
      color: #ffffff;
      font-size: 15px;
    }
    .left{
      background-color: #FF3030;
    }
    .right {
      background-color: #082F40;
    }
  }
  .remind{
    text-align: center;
    color: #333333;
    font-size: 10px;
    margin: 6px;
  }
  .center{
    background-color: #082F40;
    width: 90%;
    display: block;
    border-radius: 22.5px;
    margin: 0 auto;
    color: #FFFFFF;
    font-size: 15px;
  }
  /deep/.el-dialog__body{
    text-align: center;
    font-size: 18px;
    color: #424242;
    padding-left: 20px;
    padding-right: 20px;
    .el-textarea__inner{
      border-radius: 0;
    }
  }
  /deep/.el-dialog__footer{
    padding: 0;
    button{
      padding: 12px;
      background-color: #082F40;
      width: 100%;
      font-size: 18px;
      color: #ffffff;
      border: 0;
      border-radius: 0;
    }
    .gray{
      background-color: #666666;
    }
  }
}

</style>
