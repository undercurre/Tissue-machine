<template>
  <div class="mod-withdrawal">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('ad:withdrawal:save')" type="primary" @click="addOrUpdateHandle()">申请提现</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="userBankId"
        header-align="center"
        align="center"
        label="提现银行卡">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showBankDetails(scope.row.userBankId)">{{scope.row.bankName + ' ' + scope.row.bankNo}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="提现金额">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="提现状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0">待审核</el-tag>
          <el-tag type="success" v-if="scope.row.status === 1">提现成功</el-tag>
          <el-tag type="danger" v-if="scope.row.status === 2">提现失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="提现发起时间">
      </el-table-column>
      <el-table-column
        prop="finishTime"
        header-align="center"
        align="center"
        label="提现处理时间">
      </el-table-column>
      <el-table-column
        prop="handlerId"
        header-align="center"
        align="center"
        label="处理人ID">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.handlerId === ''">暂未审核，请耐心等待！</el-tag>
          <el-button type="text" size="small" @click="showUserDetails(scope.row.handlerId)">{{scope.row.realName}}</el-button>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        fixed="right"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="150"-->
<!--        label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button v-if="isAuth('ad:withdrawal:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>-->
<!--          <el-button v-if="isAuth('ad:withdrawal:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:withdrawal:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <bank-detail v-if="bankDetailVisible" ref="bankDetail"></bank-detail>
    <user-detail v-if="userDetailVisible" ref="userDetail"></user-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './userwithdrawal-add-or-update'
  import BankDetail from './userbank-add-or-update'
  import UserDetail from '../sys/user-add-or-update'

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
        bankDetailVisible: false,
        userDetailVisible: false
      }
    },
    components: {
      AddOrUpdate,
      BankDetail,
      UserDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/withdrawal/listByUser',
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
      showUserDetails (id) {
        this.userDetailVisible = true
        this.$nextTick(() => {
          this.$refs.userDetail.init(id, true)
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
      }
    }
  }
</script>
