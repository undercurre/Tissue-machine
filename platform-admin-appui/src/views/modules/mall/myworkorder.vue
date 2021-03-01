<template>
  <div class="mod-workorder">
    <el-form :inline="true"
             :model="searchForm"
             @keyup.enter.native="getDataList()"
             class="Top">
<!--      <el-form-item>-->
<!--        <el-select v-model="searchForm.workType" clearable placeholder="工单类型">-->
<!--          <el-option-->
<!--            v-for="item in workTypeOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-select v-model="searchForm.status" clearable placeholder="工单类型">-->
<!--          <el-option-->
<!--            v-for="item in statusOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item class="orderType">
        <el-select
          v-model="searchForm.workType"
          clearable
          placeholder="工单类型">
          <el-option
            v-for="item in workTypeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="btnGroup">
        <el-button @click="getDataList()" class="searchBtn">查询</el-button>
        <el-button @click="addNew()" class="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <div class="LetTableFlex">
    <el-table
      :data="dataList"
      border
      stripe
      height="calc(100vh - 171px)"
      class="tablePart"
      :header-cell-style="tableHeaderColor"
      style="width: calc(100% - 30px);">
      <el-table-column
        type="index"
        width="50"
        align="center"
        label="序号">
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="machineId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="对应机柜">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="text" size="small" @click="showMachineDetails(scope.row.machineId)">{{scope.row.machineName}}-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column
        prop="workType"
        header-align="center"
        align="center"
        label="工单类型">
        <template slot-scope="scope">
<!--          <el-tag v-if="scope.row.workType === 0">采购单</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 1" type="success">维护单</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 2" type="warning">送货单</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 3" type="danger">印刷单</el-tag>-->
          <div style="font-size: 12px;color:#333333;">{{GetWorkType(scope.row.workType)}}</div>
<!--          <div v-if="scope.row.workType === 1" style="font-size: 12px;color:#333333;">维护单</div>-->
<!--          <div v-if="scope.row.workType === 2" style="font-size: 12px;color:#333333;">送货单</div>-->
<!--          <div v-if="scope.row.workType === 3" style="font-size: 12px;color:#333333;">印刷单</div>-->
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="des"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单描述">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="remark"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单备注">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="工单状态">
        <template slot-scope="scope">
<!--          <el-button v-if="scope.row.status === 1" type="danger" @click="finish(scope.row.id)">处理中</el-button>-->
<!--          <el-tag v-if="scope.row.status === 2" type="success">已处理</el-tag>-->
          <div v-if="scope.row.status === 0" style="font-size: 12px;color: #424242">待领取</div>
          <div v-if="scope.row.status === 1" style="font-size: 12px;color: #424242">进行中</div>
          <div v-if="scope.row.status === 2" style="font-size: 12px;color: #DF4357">已处理</div>
          <div v-if="scope.row.status === 3" style="font-size: 12px;color: #424242">已作废</div>
          <div v-if="scope.row.status === 4" style="font-size: 12px;color: #424242">被移除</div>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="isEnd"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单完结状态">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag v-if="scope.row.isEnd === 0" type="danger">未完结</el-tag>-->
<!--          <el-tag v-if="scope.row.isEnd === 1" type="success">已完结</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="createTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单创建时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="acceptTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="接单时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="finishTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单完成时间">-->
<!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
<!--          <el-button v-if="isAuth('ad:workorder:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>-->
<!--          <el-button v-if="scope.row.isEnd === 0" type="primary" size="small" @click="addOrUpdateHandle(scope.row.id,scope.row.parentId)">指派</el-button>-->
<!--          <el-button v-if="isAuth('ad:workorder:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>-->
<!--          <el-button v-if="scope.row.isEnd === 0 && isAuth('ad:workorder:finish')" type="danger" size="small" @click="end(scope.row.id)">确认</el-button>-->
          <el-button v-if="isAuth('ad:workorder:info')"
                     type="text" size="small"
                     @click="showDetails(scope.row.id)"
                     style="color: #082F40;font-size: 12px"
          >查看</el-button>
<!--          指派按钮只有非源头才有-->
          <el-button v-if="scope.row.isEnd === 0 && scope.row.status === 1 && isAuth('ad:workorder:appoint')"
                     type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.id, scope.row.parentId)"
                     style="color: #082F40;font-size: 12px"
          >指派</el-button>
          <el-button v-if="scope.row.isEnd === 2 && isAuth('ad:workorder:rollback')"
                     type="text" size="small"
                     @click="rollback(scope.row.id)"
                     style="color: #082F40;font-size: 12px"
          >打回</el-button>
<!--          (非源头)的完成按钮,，发送参数admin=0表示是非源头触发的-->
          <el-button v-if="scope.row.isEnd === 0 && scope.row.status === 1 && isAuth('ad:workorder:finish') && scope.row.workerId === user.userId"
                     type="text" size="small"
                     @click="endHandel(scope.row.id, 0)"
                     style="color: #082F40;font-size: 12px"
          >完成</el-button>
<!--          （源头）的完成按钮，当isEnd=1时且拥有源头权限时，才显示按钮,发送参数admin=1表示是源头触发的-->
          <el-button v-if="scope.row.isEnd === 2 && isAuth('ad:workorder:commit')"
                     type="text" size="small"
                     @click="endHandel(scope.row.id, 1)"
                     style="color: #082F40;font-size: 12px"
          >确认完成</el-button>
<!--          非源头的删除按钮，当源头点击确认完成后，即isEnd=2时(已完结)显示,既有源头又有非源头权限时不显示，发送参数admin=0表示是非源头触发的-->
          <el-button v-if="scope.row.isEnd === 3 &&
          isAuth('ad:workorder:softdelete') && !(isAuth('ad:workorder:softdelete') && isAuth('ad:workorder:delete'))"
                     type="text" size="small"
                     @click="deleteOrder(scope.row.id, 0)"
                     style="color: #082F40;font-size: 12px"
          >删除</el-button>
          <!--          源头的删除按钮，当源头点击确认完成后，即isEnd=2时(已完结)显示,发送参数admin=1表示是非源头触发的-->
          <el-button v-if="scope.row.isEnd === 3 && isAuth('ad:workorder:delete')"
                     type="text" size="small"
                     @click="deleteOrder(scope.row.id, 1)"
                     style="color: #082F40;font-size: 12px"
          >删除</el-button>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="status"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="领取工单">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag v-if="scope.row.status != 0" type="danger">此工单已被领取</el-tag>-->
<!--          <el-button v-if="isAuth('ad:workorder:update') && scope.row.status === 0" type="primary" size="small">领取</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>
    </div>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-size="pageSize"
      :total="totalPage"
      :pager-count="pagerCount"
      layout="prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 指派 / 查看 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <user-detail v-if="userDetailVisible" ref="userDetail"></user-detail>
    <machine-detail v-if="machineDetailVisible" ref="machineDetail"></machine-detail>
    <!--    新增-->
    <add-new v-if="addNewVisible" ref="addNew" @refreshDataList="getDataList"></add-new>
<!--    确认完成弹窗-->
    <finish-order v-if="finishOrderVisible" ref="finishOrder" @refreshDataList="getDataList"></finish-order>
<!--    打回弹窗-->
    <roll-back v-if="rollbackVisible" ref="rollbackOrder" @refreshDataList="getDataList"></roll-back>
<!--    删除弹窗-->
    <delete-order v-if="deleteVisible" ref="deleteOrder" @refreshDataList="getDataList"></delete-order>
  </div>
</template>

<script>
  import AddOrUpdate from './myworkorder-add-or-update'
  import AddNew from './myworkorder-addnew'
  import RollBack from './myworkorder-rollback'
  import FinishOrder from './myworkorder-finish'
  import UserDetail from '../sys/user-add-or-update'
  import MachineDetail from './tissuemachine-add-or-update'
  import DeleteOrder from './myworkorder-delete'

  export default {
    data () {
      return {
        searchForm: {
          workType: '',
          status: '',
          isEnd: ''
        },
        dataList: [],
        user: '',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        pagerCount: 5,
        dataListSelections: [],
        addOrUpdateVisible: false,
        userDetailVisible: false,
        machineDetailVisible: false,
        finishOrderVisible: false,
        addNewVisible: false,
        rollbackVisible: false,
        deleteVisible: false,
        workTypeOptions: [],
        statusOptions: [{
          label: '未响应',
          value: 0
        }, {
          label: '处理中',
          value: 1
        }, {
          label: '已处理',
          value: 2
        }],
        isEndOptions: [{
          label: '未完结',
          value: 0
        }, {
          label: '已完结',
          value: 1
        }]
      }
    },
    components: {
      AddOrUpdate,
      MachineDetail,
      UserDetail,
      FinishOrder,
      AddNew,
      RollBack,
      DeleteOrder
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取工单类型
      GetWorkType (Type) {
        for (let i = 0; i < this.workTypeOptions.length; i++) {
          if (this.workTypeOptions[i].id === Type) {
            return this.workTypeOptions[i].name
          }
        }
      },
      // 表格单元格样式
      tableCellStyle ({row, rowIndex}) {
        return 'height:135px'
      },
      // 设置表头行的样式
      tableHeaderColor ({row, column, rowIndex, columnIndex}) {
        return 'background-color:#FAFAFA;'
      },
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/workorder/myworkorder',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'workType': this.searchForm.workType,
            'status': this.searchForm.status,
            'isEnd': this.searchForm.isEnd
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.user = data.user
            this.dataList = data.page.records
            this.totalPage = data.page.total
          } else {
            this.dataList = []
            this.totalPage = 0
          }
        })

        this.$http({
          url: '/ad/worktype/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.workTypeOptions = data.list
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
      showMachineDetails (id) {
        this.machineDetailVisible = true
        this.$nextTick(() => {
          this.$refs.machineDetail.init(id, true)
        })
      },
      showUserDetails (id) {
        this.userDetailVisible = true
        this.$nextTick(() => {
          this.$refs.userDetail.init(id, true)
        })
      },
      // 指派
      addOrUpdateHandle (id, parentId) {
        console.log('======' + id + '+' + parentId)
        this.addOrUpdateVisible = true
        if (parentId == null || parentId === '') {
          parentId = id
        }
        console.log('======' + id + '+' + parentId)
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, false, parentId)
        })
      },
      // 打回
      rollback (id) {
        this.rollbackVisible = true
        this.$nextTick(() => {
          this.$refs.rollbackOrder.init(id)
        })
      },
      // 新增
      addNew () {
        this.addNewVisible = true
        this.$nextTick(() => {
          this.$refs.addNew.init()
        })
      },
      // 确认完成工单
      endHandel (id, admin) {
        this.finishOrderVisible = true
        if (admin === 0) {
          this.$nextTick(() => {
            this.$refs.finishOrder.init(id, 0)
          })
        } else {
          this.$nextTick(() => {
            this.$refs.finishOrder.init(id, 1)
          })
        }
      },
      // 人工编写的删除方法
      deleteOrder (id, admin) {
        this.deleteVisible = true
        if (admin === 0) {
          this.$nextTick(() => {
            this.$refs.deleteOrder.init(id, 0)
          })
        } else {
          this.$nextTick(() => {
            this.$refs.deleteOrder.init(id, 1)
          })
        }
      },
      // 系统生成的删除方法
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
            url: '/ad/workorder/delete',
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
      end (id) {
        this.$http({
          url: '/ad/workorder/endWork',
          method: 'get',
          params: {
            workId: id
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            // this.$message({
            //   message: '操作成功',
            //   type: 'success',
            //   duration: 1500
            // })
            this.addOrUpdateVisible = true
            this.$nextTick(() => {
              this.$refs.addOrUpdate.init(id, false)
            })
            this.getDataList()
          }
        })
      },
      finish (id) {
        this.$http({
          url: '/ad/workorder/finishWork',
          method: 'get',
          params: {
            workId: id
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
      margin: 0 auto;
    }
  }
  /*搜索栏的样式*/
  .Top{
    position: relative;
    padding: 10px 15px 0;
    .el-form-item {
      margin: 0 0 10px;
    }
  /*选择工单类型*/
  .orderType{
    width: 45%;
    .el-form-item__content {
      width: 100%;
      .el-input {
        font-size: 12px;
      }
      .el-select {
        width: 100%;
        font-size: 12px;
      }
    }
  }
  /*按钮组合*/
  .btnGroup{
    margin-left: 10px;
    .searchBtn{
      padding: 10px 15px;
      font-size: 12px;
      background-color: #082F40;
      border-color: #082F40;
      color: #ffffff;
    }
    .addBtn{
      padding: 10px 15px;
      font-size: 12px;
      border: 1px solid #0A84FF;
      color: #0A84FF;
      background-color: #ffffff;
      margin-left: 5px;
    }
  }
  }

</style>
