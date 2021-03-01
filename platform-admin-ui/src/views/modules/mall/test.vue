<template>
    <div class="content">
      <div class="top">
        <span>设备id：</span>
        <input v-model="id" placeholder="请输入设备id" style="width: 60%"></input>
        <el-button @click="start">开始测试</el-button>
      </div>
      <h4>上报记录：</h4>
      <div class="center">
        <el-table
          :data="messageList"
          :show-header="false"
          style="width: 100%"
          height="400">
          <el-table-column>
            <template slot-scope="scope">
              <span style="color: red"> {{scope.row.machineSn}}  ({{scope.row.reportTime}})</span></br>{{scope.row.content}}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="bottom">
        <span>发送指令：</span>
        <input v-model="instruct" placeholder="请输入完整指令" style="width: 60%"></input>
        <el-button @click="sendData">确认发送</el-button>
      </div>
    </div>
</template>

<script>
  import moment from 'moment'
  export default {
    data () {
      return {
        timer: null,
        id: '',
        instruct: '',
        message: '',
        messageList: []
      }
    },
    methods: {
      getData (date) {
        this.$http({
          url: '/ad/machinereportrecord/queryAll',
          method: 'get',
          params: {
            'sn': this.id,
            'time': date
          }
        }).then(({data}) => {
          this.messageList = data.list
        })
      },
      // 获取数据列
      start () {
        this.$http({
          url: `/ad/machinereportrecord/machineUpdate/${this.id}`,
          method: 'get'
        })
        let that = this
        clearInterval(that.timer)
        let date = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        that.timer = setInterval(function () {
          that.getData(date)
        }, 6000)
      },
      sendData () {
        console.log(this.instruct)
        this.$http({
          url: '/ad/machinereportrecord/receiveData',
          method: 'post',
          data: JSON.parse(this.instruct)
        }).then(() => {
          this.$message({
            message: '发送成功，请查看上方记录，确认设备是否返回执行结果，并查看设备是否执行相关操作！',
            type: 'success',
            duration: 10000
          })
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
.content{
  .top{
    margin-bottom: 10px;
  }
  .center{
    margin: 10px 0;
    .area{
      width: 80%;
    }
  }
}
</style>
