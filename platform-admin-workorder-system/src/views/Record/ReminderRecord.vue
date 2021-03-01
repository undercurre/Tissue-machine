<template>
  <div class="reminder-record"
       v-loading="loading"
       element-loading-text="拼命加载中..."
       v-loading.lock="true">
    <div v-for="(item, index) in orderData"
         :key="index">
      <order-record :recordType="1"
                    :recordData="item"></order-record>
    </div>
    <div class="no-order"
         v-if="Object.keys(orderData).length == 0">
      <span>未查询到符合条件的工单</span>
    </div>
  </div>
</template>

<script>
import OrderRecord from '../../components/OrderRecord'
export default {
  data () {
    return {
      orderData: {},
      wxtoken: '',
      loading: true
    }
  },
  components: {
    OrderRecord
  },
  methods: {
    // 获取到传过来的参数
    async getQuery () {
      let flag = ['title', 'orderId', 'deviceName', 'assetsType', 'orderType', 'createTime', 'description'], // 工单进度消息
        flag1 = ['title', 'deviceName', 'time'], // 设备保养提醒消息
        flag2 = ['title', 'orderSubmitPerson', 'orderId', 'submitTime'] // 未处理工单提醒消息

      await this.$http.httpGet('/app/apptbmsgwxlog').then(res => {
        this.loading = false
        if (res.code === 0 && res.msg === 'success') {
          let data = res.data
          this.orderData = []
          data.forEach(item => {
            let temp = JSON.parse(item.content)
            temp.pushCategoryType = item.type

            if (item.type === 0 || item.type == null) {
              this.wxLogMsgDetails(temp, flag)
            } else if (item.type === 1) {
              this.wxLogMsgDetails(temp, flag1)
            } else if (item.type === 2) {
              this.wxLogMsgDetails(temp, flag2)
            }
          })
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    wxLogMsgDetails (temp, flag) {
      let tempObject = {}
      temp.data.forEach((item2, index) => {
        tempObject[flag[index]] = item2.value
      })
      tempObject.url = temp.url
      tempObject.pushCategoryType = temp.pushCategoryType
      this.orderData.push(tempObject)
    }
  },
  mounted () {
    this.getQuery()
  }
}
</script>

<style scoped lang="scss">
.no-order {
  padding-top: 3rem;
  text-align: center;
  span {
    font-size: 1.8rem;
  }
}
</style>
