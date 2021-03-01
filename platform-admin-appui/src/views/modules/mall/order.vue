<template>
  <div class="mod-order">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.nickname" placeholder="会员昵称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.machineName" placeholder="机柜店名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.orderType" clearable placeholder="订单类型">
          <el-option
            key="1"
            label="普通订单"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="免费领取"
            value="2">
          </el-option>
<!--          <el-option-->
<!--            key="3"-->
<!--            label="活动订单"-->
<!--            value="4">-->
<!--          </el-option>-->
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.orderStatus" clearable placeholder="支付状态">
          <el-option
            key="0"
            label="未支付"
            value="0">
          </el-option>
          <el-option
            key="1"
            label="已支付"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="退款中"
            value="2">
          </el-option>
          <el-option
            key="3"
            label="已退款"
            value="3">
          </el-option>
          <el-option
            key="4"
            label="已取消"
            value="4">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
<!--        <el-button v-if="isAuth('ad:order:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
<!--        <el-button v-if="isAuth('ad:order:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="fromType"
        header-align="center"
        align="center"
        label="下单类型">
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
        prop="orderSn"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员昵称">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showUserDetails(scope.row.userId)">{{scope.row.nickname}}</el-button>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="activityId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="活动ID">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="orderType"
        header-align="center"
        align="center"
        label="订单类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.orderType === 1" size="small" type="info">普通订单</el-tag>
          <el-tag v-else-if="scope.row.orderType === 2" size="small" type="warning">免费领取</el-tag>
          <el-tag v-else-if="scope.row.活动订单 === 3" size="small" type="success">活动订单</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderStatus"
        header-align="center"
        align="center"
        label="订单状态">
       <template slot-scope="scope">
        <el-tag v-if="scope.row.orderStatus === 0" size="small" type="info">未支付</el-tag>
        <el-tag v-else-if="scope.row.orderStatus === 1" size="small" type="warning">已支付</el-tag>
        <el-tag v-else-if="scope.row.orderStatus === 2" size="small" type="success">退款中</el-tag>
        <el-tag v-else-if="scope.row.orderStatus === 3" size="small" type="danger">已失效</el-tag>
       </template>
      </el-table-column>

      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="实际消费金额">
      </el-table-column>
      <el-table-column
        prop="comsumePrice"
        header-align="center"
        align="center"
        label="消费金">
      </el-table-column>
      <el-table-column
        prop="keyValue"
        header-align="center"
        align="center"
        label="购买商品信息">
      </el-table-column>
      <el-table-column
        prop="machineName"
        header-align="center"
        align="center"
        label="机柜信息">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showMachineDetails(scope.row.machineId)">{{scope.row.machineName}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="订单创建时间">
      </el-table-column>
      <el-table-column
        prop="payTime"
        header-align="center"
        align="center"
        label="支付时间">
      </el-table-column>
      <el-table-column
        prop="expireTime"
        header-align="center"
        align="center"
        label="订单过期时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:order:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
<!--          <el-button v-if="isAuth('ad:order:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:order:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
        </template>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <user-detail v-if="userDetailVisible" ref="userDetail"></user-detail>
    <machine-detail v-if="machineDetailVisible" ref="machineDetail"></machine-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './order-add-or-update'
  import UserDetail from './user-add-or-update'
  import MachineDetail from './tissuemachine-add-or-update.vue'
  export default {
    data () {
      return {
        searchForm: {
          nickname: '',
          orderType: '',
          orderStatus: '',
          machineName: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        userDetailVisible: false,
        machineDetailVisible: false
      }
    },
    components: {
      AddOrUpdate,
      UserDetail,
      MachineDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 查看会员详情
      showUserDetails (id) {
        this.userDetailVisible = true
        this.$nextTick(() => {
          this.$refs.userDetail.init(id, true)
        })
      },
      // 查看机柜详情
      showMachineDetails (id) {
        this.machineDetailVisible = true
        this.$nextTick(() => {
          this.$refs.machineDetail.init(id, true)
        })
      },
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/order/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'nickname': this.searchForm.nickname,
            'orderType': this.searchForm.orderType,
            'orderStatus': this.searchForm.orderStatus,
            'machineName': this.searchForm.machineName
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
            url: '/ad/order/delete',
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
