<template>
  <div class="mod-userbank">
    <div
      class="bankList"
      v-for="(item, index) in dataList"
      :key="index"
    >
      <div class="bankBlock">
<!--      <div class="bankBlock" @click="choiceToBind(item.id)">-->
        <h4>{{ item.bankName }}</h4>
        <h5>{{ item.bankNo.toString().substring(0, 4) }}**** ****{{ item.bankNo.toString().substring(12) }}</h5>
      </div>
      <div class="bankBtnGroup">
        <div class="viewBtn" v-if="isAuth('ad:userbank:info')" @click="showDetails(item.id)">查看</div>
        <div class="viewBtn" v-if="isAuth('ad:userbank:update')" @click="addOrUpdateHandle(item.id)">修改</div>
        <div class="viewBtn" v-if="isAuth('ad:userbank:delete')" @click="deleteCard(item.id)">删除</div>
      </div>
    </div>
    <div class="addBlock" v-if="isAuth('ad:userbank:save')" @click="addOrUpdateHandle()">
      <p>添加银行卡 <i class="el-icon-plus"></i></p>
    </div>

<!--    <el-table-->
<!--      :data="dataList"-->
<!--      border-->
<!--      style="width: 100%;">-->
<!--      <el-table-column-->
<!--        prop="bankName"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="银行名称">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="bankNo"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="银行卡卡号">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="userName"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="银行卡持有人姓名">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        fixed="right"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="150"-->
<!--        label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button v-if="isAuth('ad:userbank:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>-->
<!--          <el-button v-if="isAuth('ad:userbank:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:userbank:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->
<!--    <el-pagination-->
<!--      @size-change="sizeChangeHandle"-->
<!--      @current-change="currentChangeHandle"-->
<!--      :current-page="pageIndex"-->
<!--      :page-sizes="[10, 20, 50, 100]"-->
<!--      :page-size="pageSize"-->
<!--      :total="totalPage"-->
<!--      layout="total, sizes, prev, pager, next, jumper">-->
<!--    </el-pagination>-->
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <!--    删除-->
    <delete-card v-if="DeleteVisible" ref="DeleteCard" @refreshDataList="getDataList"></delete-card>
  </div>
</template>

<script>
import AddOrUpdate from './userbank-add-or-update'
import DeleteCard from './userbank-delete'
export default {
  data () {
    return {
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
  components: {
    AddOrUpdate,
    DeleteCard
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
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
          this.dataList = data.page.records
          this.totalPage = data.page.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 查看详情
    showDetails (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, true)
      })
    },
    // 选择要绑定的银行卡
    choiceToBind (id) {
      this.$router.push({path: 'mall-withdrawalbalance', query: {fromBankcard: '1', bankcardId: id}})
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, false)
      })
    },
    // zpq-删除
    deleteCard (id) {
      this.DeleteVisible = true
      this.$nextTick(() => {
        this.$refs.DeleteCard.init(id)
      })
    },
    // 删除
    deleteHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[删除]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/ad/userbank/delete',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
            this.getDataList()
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.mod-userbank {
  position: relative;
  .bankList{
    width: 90%;
    margin: 20px auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 9px 11px 0 #7F7F7F;
    border-radius: 5px;
    .bankBlock{
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      background-color: #4761FF;
      padding: 10px;
      border-top-left-radius: 5px;
      border-top-right-radius: 5px;
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
 }

</style>
