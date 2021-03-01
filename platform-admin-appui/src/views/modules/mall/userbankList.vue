<template>
  <div>
  <el-dialog
    title="选择银行卡"
    :close-on-click-modal="false"
    class="thePageDialog"
    :visible.sync="visible"
  >
    <div class="mod-userbank">
      <div
        class="bankList"
        v-for="(item, index) in dataList"
        :key="index"
        v-if="hasCard"
      >
        <div class="bankBlock" @click="choiceToBind(item)">
          <h4>{{ item.bankName }}</h4>
          <h5>{{ item.bankNo.toString().substring(0, 4) }}**** ****{{ item.bankNo.toString().substring(12) }}</h5>
        </div>
      </div>
      <div class="addBlock" v-if="isAuth('ad:userbank:save') && dataList.length === 0" @click="addOrUpdateHandle()">
        <p>添加银行卡 <i class="el-icon-plus"></i></p>
      </div>
    </div>
  </el-dialog>
  <!-- 弹窗, 新增 / 修改 -->
  <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './userbank-add-or-update'
export default {
  data () {
    return {
      hasCard: false,
      visible: false,
      searchForm: {
        name: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      addOrUpdateVisible: false,
      DeleteVisible: false
    }
  },
  activated () {
    // this.getDataList()
    this.hasCard = false
  },
  components: {
    AddOrUpdate
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.visible = true
      this.$http({
        url: '/ad/userbank/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'name': this.searchForm.name
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          if (data.page.records.length > 0) {
            this.hasCard = true
          }
          this.dataList = data.page.records
          this.totalPage = data.page.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
      })
    },
    // 选择要绑定的银行卡
    choiceToBind (data) {
      this.visible = false
      this.$emit('getChoose', data)
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      // this.visible = false
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, false)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.thePageDialog /deep/ {
  .el-dialog {
    margin: 10vh auto !important;
    background-color: transparent;
    .el-dialog__header{
      display: flex;
      justify-content: center;
      .el-dialog__title{
        font-size: 16px;
        color: #fff;
      }
    }
    .el-dialog__body{
      background-color: #fff;
      height: calc(80vh - 121px);
      overflow: auto;
      position: relative;
      padding: 15px 0;
      border-bottom-left-radius: 5px;
      border-bottom-right-radius: 5px;
      .mod-userbank {
        position: relative;
        .bankList{
          width: 90%;
          margin: 20px auto;
          display: flex;
          flex-direction: column;
          align-items: center;
          box-shadow: 0 5px 5px #7F7F7F;
          border-radius: 5px;
          .bankBlock{
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            background-color: #4761FF;
            padding: 10px;
            border-radius: 5px;
            h4{
              font-size: 16px;
              padding: 10px 0;
              color: #ffffff;
              margin: 0;
            }
            h5{
              font-size: 14px;
              padding: 10px 0;
              color: #ffffff;
              margin: 0;
            }

          }
          .bankBtnGroup{
            width: 100%;
            display: flex;
            justify-content: space-around;
            background-color: #ffffff;
            border-bottom-left-radius: 5px;
            border-bottom-right-radius: 5px;
            padding: 4% 0;
            .viewBtn{
              color: #333333;
              font-size: 14px;
            }
          }
        }
        .addBlock {
          background-color: transparent;
          padding: 20px 0;
          margin: 15px 0 0;
          p {
            font-size: 16px;
            color: #333333;
            text-align: center;
          }
        }
        .addBlock {
          background-color: transparent;
          padding: 10px 0;
          margin: 0;
          p {
            font-size: 16px;
            color: #333333;
            text-align: center;
          }
        }
      }
    }
  }
}


</style>
