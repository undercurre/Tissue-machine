<template>
  <div class="Content">
    <ul>
    <li v-for="item in dataList" v-bind:key="item.id">
    <div class="Card">
      <div :class="item.bankName === '中国银行' ? 'TopZG' : item.bankName === '中国农业银行' ? 'TopZGny' : 'TopZGgs'"
           @click="choiceToBind(item.id)">
        <div class="bankType">
          {{ item.bankName }}
        </div>
        <div class="cardNumber">
          {{ item.bankNo.toString().substring(0, 4) }}**** ****{{ item.bankNo.toString().substring(12) }}
        </div>
      </div>
      <div class="Bottom">
     <el-button type="text" @click="checkCard(item.id)">查看</el-button>
        <el-button type="text" @click="updateCard(item.id)">修改</el-button>
        <el-button type="text" @click="deleteCard(item.id)">删除</el-button>
      </div>
    </div>
    </li>
    </ul>

    <el-button type="text" @click="addCard(userId)">添加银行卡 +</el-button>
<!--    添加银行卡,修改，查看-->
    <add-or-update v-if="AddOrUpdateVisible" ref="AddOrUpdate" @refreshDataList="getDataList"></add-or-update>
<!--    删除-->
<delete-card v-if="DeleteVisible" ref="DeleteCard" @refreshDataList="getDataList"></delete-card>
  </div>
</template>

<script>
  import AddOrUpdate from './bankcardmanagement-add-or-update'
  import DeleteCard from './bankcardmanagement-delete'
  export default {
    data () {
      return {
        searchForm: {
          nickname: '',
          cardName: '',
          cardNumber: '',
          bankName: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        AddOrUpdateVisible: false,
        DeleteVisible: false,
        userId: ''
      }
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 添加银行卡
      addCard (userId) {
        this.AddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.AddOrUpdate.init(userId, null, false, true, false)
        })
      },
      // 修改
      updateCard (id) {
        this.AddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.AddOrUpdate.init(null, id, false, false, true)
        })
      },
      // 查看
      checkCard (id) {
        this.AddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.AddOrUpdate.init(null, id, true, false, false)
        })
      },
      // 删除
      deleteCard (id) {
        this.DeleteVisible = true
        this.$nextTick(() => {
          this.$refs.DeleteCard.init(id)
        })
      },
      // 选中要绑定的银行卡
      choiceToBind (id) {
        this.$router.push({path: 'mall-withdrawalbalance', query: {fromBankcard: '1', bankcardId: id}})
      },
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/userbank/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
            this.userId = data.list[0].userId
          } else {
            this.dataList = []
          }
        })
      }
    },
    components: {
      AddOrUpdate,
      DeleteCard
    }
  }
</script>

<style lang="scss" scoped>
  .Content{
    padding: 10px 2vw;
    display: flex;
    flex-direction: column;
   align-items: center;
    ul{
      width: 100%;
      margin: 0;
      padding: 0;
    }
    li{
      list-style: none;
    }
    .Card{
      margin-bottom: 20px;
      width: 100%;
      border-radius: 4px;
      display: flex;
      flex-direction: column;
      align-items: center;
      border:1px solid black;
      .TopZG{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        background-color: teal;
        padding: 10px;
        .bankType{
          font-size: 18px;
          padding: 10px 0;
          color: #ffffff;
        }
        .cardNumber{
          font-size: 10px;
          padding: 10px 0;
          color: #ffffff;
        }
      }
      .TopZGny{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        background-color: green;
        padding: 10px;
        .bankType{
          font-size: 18px;
          padding: 10px 0;
          color: #ffffff;
        }
        .cardNumber{
          font-size: 10px;
          padding: 10px 0;
          color: #ffffff;
        }
      }
      .TopZGgs{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        background-color: blue;
        padding: 10px;
        .bankType{
          font-size: 18px;
          padding: 10px 0;
          color: #ffffff;
        }
        .cardNumber{
          font-size: 10px;
          padding: 10px 0;
          color: #ffffff;
        }
      }
      .Bottom{
        width: 100%;
        display: flex;
        justify-content: space-around;
        background-color: #ffffff;

      }
    }
  }
</style>
