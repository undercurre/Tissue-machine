<template>
  <el-dialog
    title="用户账户余额变动记录"
    :visible.sync="visible">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="searchForm.type" clearable placeholder="类型"
                   class="width185">
          <el-option
            key="1"
            label="收入"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="支出"
            value="2">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="nickname"
        header-align="center"
        align="center"
        label="用户昵称">
      </el-table-column>
      <el-table-column
        width="120px"
        prop="fromType"
        header-align="center"
        align="center"
        label="下单来源">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fromType === 1" size="small" type="info">微信小程序</el-tag>
          <el-tag v-else-if="scope.row.fromType === 2" size="small" type="success">微信公众号</el-tag>
          <el-tag v-else-if="scope.row.fromType === 3" size="small" type="warning">APP</el-tag>
          <el-tag v-else-if="scope.row.fromType === 4" size="small" type="warning">H5</el-tag>
          <el-tag v-else-if="scope.row.fromType === 5" size="small" type="warning">支付宝小程序</el-tag>
          <el-tag v-else-if="scope.row.fromType === 6" size="small" type="warning">QQ小程序</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 0" size="small" type="success">收入（待支付）</el-tag>
          <el-tag v-if="scope.row.type === 1" size="small" type="success">收入</el-tag>
          <el-tag v-else-if="scope.row.type === 2" size="small" type="danger">支出</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="变动金额">
      </el-table-column>
      <el-table-column
        show-tooltip-when-overflow
        prop="logDesc"
        header-align="center"
        align="center"
        label="变动说明">
      </el-table-column>
      <el-table-column
        prop="addTime"
        header-align="center"
        align="center"
        label="记录时间">
        <template slot-scope="scope">
          <span>{{scope.row.addTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderType"
        header-align="center"
        align="center"
        label="订单类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.orderType === 0" size="small" type="success">充值</el-tag>
          <el-tag v-else-if="scope.row.orderType === 1" size="small" type="success">商城订单</el-tag>
          <el-tag v-else-if="scope.row.orderType === 2" size="small" type="success">秒杀订单</el-tag>
          <el-tag v-else-if="scope.row.orderType === 3" size="small" type="success">拼团订单</el-tag>
          <el-tag v-else-if="scope.row.orderType === 4" size="small" type="danger">商城订单退款</el-tag>
          <el-tag v-else-if="scope.row.orderType === 5" size="small" type="danger">秒杀订单退款</el-tag>
          <el-tag v-else-if="scope.row.orderType === 6" size="small" type="danger">拼团订单退款</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderSn"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
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
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        userId: '',
        searchForm: {
          type: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        visible: false
      }
    },
    methods: {
      init (userId) {
        this.userId = userId
        this.getDataList()
      },
      // 获取数据列表
      getDataList () {
        this.visible = true
        this.$http({
          url: '/mall/accountlog/list',
          method: 'get',
          params: {
            'userId': this.userId,
            'page': this.pageIndex,
            'limit': this.pageSize,
            'type': this.searchForm.type
          }
        }).then(({ data }) => {
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
      }
    }
  }
</script>
