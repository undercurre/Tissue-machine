<template>
  <div class="mod-withdrawal">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
<!--      <el-form-item>-->
<!--        <el-input v-model="searchForm.name" placeholder="参数名" clearable></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button @click="getDataList()">查询</el-button>-->
<!--      </el-form-item>-->
    </el-form>
    <div class="LetTableFlex">
    <el-table
      :data="dataList"
      border
      style="width: calc(100% - 30px);"
      stripe
      class="tablePart"
      :cell-style="tableCellStyle"
      :header-cell-style="tableHeaderColor">
      <el-table-column
        type="index"
        label="序号"
        align="center"
        width="50"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        label="提现人"
      >
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="userBankId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="提现银行卡">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="text" size="small" @click="showBankDetails(scope.row.userBankId)">{{scope.row.bankName + ' ' + scope.row.bankNo}}</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="amount"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="提现金额">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="提现状态">
        <template slot-scope="scope">
          <div v-if="scope.row.status === 0" style="color: #FF9700;font-size: 12px">审核中</div>
          <div  v-if="scope.row.status === 1" style="color: #1FBF84;font-size: 12px">提现成功</div>
          <div  v-if="scope.row.status === 2" style="color: #FF3030;font-size: 12px">提现失败</div>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="createTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="提现发起时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="finishTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="提现处理时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="handlerId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="处理人ID">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        fixed="right"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="150"-->
<!--        label="操作">-->
<!--        <template slot-scope="scope" v-if="scope.row.status === 0">-->
<!--          <el-button v-if="isAuth('ad:withdrawal:update')" type="text" size="small" @click="changeStatus(scope.row.id, 1)">审核通过</el-button>-->
<!--          <el-button v-if="isAuth('ad:withdrawal:update')" type="text" size="small" @click="changeStatus(scope.row.id, 2)">审核不通过</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:withdrawal:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
<!--          <el-button v-if="isAuth('ad:withdrawal:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:withdrawal:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>
    </div>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      style="margin: 15px 0"
      :current-page="pageIndex"
      :page-size="pageSize"
      :total="totalPage"
      :pager-count="pagerCount"
      layout="prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <bank-detail v-if="bankDetailVisible" ref="bankDetail"></bank-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './withdrawal-add-or-update'
  import BankDetail from './userbank-add-or-update'

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
        pagerCount: 5,
        dataListSelections: [],
        addOrUpdateVisible: false,
        bankDetailVisible: false
      }
    },
    components: {
      AddOrUpdate,
      BankDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/withdrawal/list',
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
      // 表格单元格样式
      tableCellStyle ({row, rowIndex}) {
        return 'height:60px'
      },
      // 设置表头行的样式
      tableHeaderColor ({row, column, rowIndex, columnIndex}) {
        return 'background-color:#FAFAFA;font-size:14px;color:#333333'
      },
      // 查看详情
      showDetails (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, true)
        })
      },
      showBankDetails (id) {
        this.bankDetailVisible = true
        this.$nextTick(() => {
          this.$refs.bankDetail.init(id, true)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
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
            url: '/ad/withdrawal/delete',
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
      },
      changeStatus (id, status) {
        this.$http({
          url: '/ad/withdrawal/changeStatus',
          method: 'post',
          params: {
            id: id,
            status: status
          }
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
      }
    }
  }
</script>
<style lang="scss" scoped>
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 20px auto 0;
      font-size: 12px;
      color: #333333;
      .el-button{
        color: #082F40;
        font-size: 12px;
      }
    }
  }
</style>
<style lang="scss" scoped>
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 20px auto 0;
      font-size: 12px;
      color: #333333;
      .el-button{
        color: #082F40;
        font-size: 12px;
      }
    }
  }
</style>
<style lang="scss" scoped>
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 20px auto 0;
      font-size: 12px;
      color: #333333;
      .el-button{
        color: #082F40;
        font-size: 12px;
      }
    }
  }
</style>
<style lang="scss" scoped>
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 20px auto 0;
      font-size: 12px;
      color: #333333;
      .el-button{
        color: #082F40;
        font-size: 12px;
      }
    }
  }
</style>
<style lang="scss" scoped>
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 20px auto 0;
      font-size: 12px;
      color: #333333;
      .el-button{
        color: #082F40;
        font-size: 12px;
      }
    }
  }
</style>
