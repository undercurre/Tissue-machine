<template>
  <div class="remind-record">
    <order-record :recordType="1"
                  :recordData="mockRemindData"></order-record>
    <order-record :recordType="1"
                  :recordData="mockRemindData"></order-record>
    <order-record :recordType="1"
                  :recordData="mockRemindData"></order-record>
    <order-record :recordType="2"
                  :recordData="mockOrderData"></order-record>
    <order-record :recordType="2"
                  :recordData="mockOrderData"></order-record>
  </div>
</template>

<script>
import OrderRecord from '../../components/OrderRecord'
export default {
  data () {
    return {
      mockOrderData: {
        title: '西门水厂',
        orderType: '维修单',
        orderStatus: '待维修',
        describe: '水闸坏了，一直漏水，而且漏电，相当吓人。',
        worker: '黄耿林',
        time: '2019年7月31日15:02:46'
      },
      mockRemindData: {
        title: '新工单需要指派',
        degree: '优先',
        number: '5501556688',
        name: '西闸水长',
        describe: '水闸坏了，一直漏水，而且漏电，相当吓人。',
        time: '2019年7月31日15:02:46'
      },
    }
  },
  components: {
    OrderRecord
  }
}
</script>

<style scoped lang="scss">
.remind-record {
  margin-bottom: 5rem;
}
</style>
