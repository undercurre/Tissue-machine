<template>
  <div class="mod-workorder">
    <el-form :inline="true"
             :model="searchForm"
             @keyup.enter.native="getDataList()"
             class="searchList">
      <el-form-item class="longInput">
        <el-select v-model="searchForm.workType" clearable placeholder="工单类型">
          <el-option
            v-for="item in workTypeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
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
<!--      <el-form-item>-->
<!--        <el-select v-model="searchForm.isEnd" clearable placeholder="工单类型">-->
<!--          <el-option-->
<!--            v-for="item in isEndOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item class="searchBtn">
        <el-button @click="getDataList()" type="primary">查询</el-button>
<!--        <el-button v-if="isAuth('ad:workorder:save')"-->
<!--                   type="primary" @click="addNew()"-->
<!--                   class="addBtn"-->
<!--        >新增</el-button>-->
<!--        <el-button v-if="isAuth('ad:workorder:delete')"-->
<!--                   type="danger"-->
<!--                   @click="deleteMoreItems()"-->
<!--                   :disabled="dataListSelections.length <= 0"-->
<!--                   class="deleteMoreBtn"-->
<!--        >批量删除</el-button>-->
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
      style="width: calc(100% - 30px);"
    >
      <el-table-column
        type="index"
        label="序号"
        align="center"
        width="50"
      >
      </el-table-column>
      <el-table-column
        prop="workerId"
        header-align="center"
        align="center"
        label="指派人员">
        <template slot-scope="scope">
          <el-tag v-if="!scope.row.workerId" type="danger">暂无指派人员</el-tag>
<!--          <el-button type="text" size="small" @click="showUserDetails(scope.row.workerId)">{{scope.row.workerName}}</el-button>-->
          <div style="font-size: 12px;color: #333333">{{ scope.row.workerName }}</div>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="workType"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单类型">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag v-if="scope.row.workType === 0">广告采购</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 1" type="success">设备维护</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 2" type="warning">送货</el-tag>-->
<!--          <el-tag v-if="scope.row.workType === 3" type="danger">贴印刷广告</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="工单状态">
        <template slot-scope="scope">
          <div v-if="scope.row.status === 0" style="color: red;font-size: 12px;">待领取</div>
          <div v-if="scope.row.status === 1" style="color: #FF3131;font-size: 12px;">处理中</div>
          <div v-if="scope.row.status === 2" style="color: #424242;font-size: 12px;">已处理</div>
          <div v-if="scope.row.status === 3" style="color: #000000;font-size: 12px;">已作废</div>
          <div v-if="scope.row.status === 4" style="color: red;font-size: 12px;">被移除</div>
<!--          <el-tag v-if="scope.row.status == 3" type="success">已完成</el-tag>-->
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="isEnd"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单完成状态">-->
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
<!--        prop="machineId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="对应机柜">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="text" size="small" @click="showMachineDetails(scope.row.machineId)">{{scope.row.machineName}}-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="acceptTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="运维人员接单时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="finishTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单完成时间">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="des"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="工单描述">-->
<!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
<!--          <el-button v-if="isAuth('ad:workorder:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>-->
          <el-button
            v-if="isAuth('ad:workorder:info')"
            type="text" size="small"
            @click="showDetails(scope.row.id)"
            style="color: #082F40"
          >查看</el-button>
          <el-button
            v-if="isAuth('ad:workorder:update')"
            type="text" size="small"
            @click="getOrder(scope.row.id)"
            style="color: #082F40"
          >领取</el-button>

<!--          <el-button-->
<!--            v-if="isAuth('ad:workorder:delete')"-->
<!--            type="text" size="small"-->
<!--            @click="deleteOneOrder(scope.row.id)"-->
<!--            style="color: #082F40"-->
<!--          >删除</el-button>-->
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="status"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="领取工单">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag v-if="scope.row.status != 0" type="danger">此工单已被领取</el-tag>-->
<!--          <el-button v-if="isAuth('ad:workorder:update') && scope.row.status === 0" type="primary" size="small" @click="getWorkOrder(scope.row.id)">领取</el-button>-->
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
    <!-- 弹窗, 查看、 修改、-->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
<!--    新增-->
    <add-new v-if="addNewVisible" ref="addNew" @refreshDataList="getDataList"></add-new>
    <user-detail v-if="userDetailVisible" ref="userDetail"></user-detail>
    <machine-detail v-if="machineDetailVisible" ref="machineDetail"></machine-detail>
<!--    批量删除-->
    <delete-more v-if="deleteMoreVisible" ref="deleteMore" @refreshDataList="getDataList"></delete-more>
<!--    领取工单-->
    <get-order v-if="getOrderVisible" ref="getOrder" @gotoMyOrder="jumpToMyOrder"></get-order>
  </div>
</template>

<script>
  import AddOrUpdate from './workorder-add-or-update'
  import DeleteMore from './workorder-delete-more'
  import GetOrder from './workorder-get'
  import AddNew from './myworkorder-addnew'
  import UserDetail from '../sys/user-add-or-update'
  import MachineDetail from './tissuemachine-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          workType: '',
          status: '',
          isEnd: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        pagerCount: 5,
        dataListSelections: [],
        addOrUpdateVisible: false,
        userDetailVisible: false,
        machineDetailVisible: false,
        addNewVisible: false,
        deleteMoreVisible: false,
        getOrderVisible: false,
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
      AddNew,
      DeleteMore,
      GetOrder
    },
    activated () {
      this.getDataList()
    },
    methods: {
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
          url: '/ad/workorder/list',
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
        console.log('wdt', val)
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
      // 领取
      getOrder (id) {
        this.getOrderVisible = true
        this.$nextTick(() => {
          this.$refs.getOrder.init(id)
        })
      },
      // 点击领取后，跳转到我的工单
      jumpToMyOrder () {
        this.$router.push({path: 'mall-myworkorder'})
      },
      //  修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, false)
        })
      },
      // 新增
      addNew () {
        this.addNewVisible = true
        this.$nextTick(() => {
          this.$refs.addNew.init()
        })
      },
      // 删除
      deleteHandle (id) {
        let ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, false, true)
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
      // 批量删除
      deleteMoreItems () {
        this.deleteMoreVisible = true
        let that = this
        this.$nextTick(() => {
          this.$refs.deleteMore.init(that.dataListSelections)
        })
      },
      getWorkOrder (id) {
        this.$http({
          url: '/ad/workorder/getWorkOrder',
          method: 'get',
          params: {
            workId: id
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.getDataList()
          }
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
  /*表格居中*/
  .LetTableFlex {
    width: 100%;
    .el-table {
      margin: 0 auto;
    }
  }
/*搜索栏的样式*/
/*.Top{*/
/*  position: relative;*/
/*  padding: 10px 15px 0;*/
/*  .el-form-item {*/
/*    margin: 0 0 10px;*/
/*  }*/
  /*选择工单类型*/
  /*.orderType{*/
  /*  width: 30%;*/
  /*  .el-form-item__content {*/
  /*    width: 100%;*/
  /*    .el-input {*/
  /*      font-size: 12px;*/
  /*    }*/
  /*    .el-select {*/
  /*      width: 100%;*/
  /*      font-size: 12px;*/
  /*    }*/
  /*  }*/
  /*}*/
  /*按钮组合*/
  /*.btnGroup{*/
  /*  float: right;*/
  /*  .searchBtn{*/
  /*    padding: 10px 15px;*/
  /*    font-size: 12px;*/
  /*    background-color: #082F40;*/
  /*    border-color: #082F40;*/
  /*    color: #ffffff;*/
  /*  }*/
  /*  .addBtn{*/
  /*    padding: 10px 15px;*/
  /*    font-size: 12px;*/
  /*    border: 1px solid #0A84FF;*/
  /*    color: #0A84FF;*/
  /*    background-color: #ffffff;*/
  /*  }*/
  /*  .deleteMoreBtn{*/
  /*    padding: 10px 15px;*/
  /*    font-size: 12px;*/
  /*    border: 1px solid #053D53;*/
  /*    color: #053D53;*/
  /*    background-color: #ffffff;*/
  /*  }*/
  /*}*/
/*}*/

/* 当屏幕with<360px时，对顶部选择器和按钮进行兼容处理*/
@media screen and (max-width: 360px) {
  .Top{
    /*选择工单类型*/
    .orderType{
      width: 33%;
    }
    /*按钮组合*/
    .btnGroup{
      float: right;
      .searchBtn{
        padding: 10px 15px;
      }
      .addBtn{
        padding: 10px 15px;
      }
      .deleteMoreBtn{
        padding: 10px;

      }
    }
  }
}

/* 当屏幕with<320px时，对顶部选择器和按钮进行兼容处理*/
@media screen and (max-width: 320px) {
  .Top{
    /*选择工单类型*/
    .orderType{
      width: 36%;
    }
    /*按钮组合*/
    .btnGroup{
      width: 60%;
      float: right;
      .searchBtn{
        padding: 10px;
      }
      .addBtn{
        padding: 10px;
        margin-left: 0;
      }
      .deleteMoreBtn{
        padding: 10px;
        margin-left: 0;
      }
    }
  }
}

/* 当屏幕with<300px时，对顶部选择器和按钮进行兼容处理*/
@media screen and (max-width: 280px) {
  .Top{
    /*选择工单类型*/
    .orderType{
      width: 43%;
    }
    /*按钮组合*/
    .btnGroup{
      width: 55%;
      float: right;
      .searchBtn{
        padding: 10px 5px;
      }
      .addBtn{
        padding: 10px 5px;
        margin-left: 0;
      }
      .deleteMoreBtn{
        padding: 10px 2px;
        margin-left: 0;
      }
    }
  }
}


  /*隐藏表格第一行第一列的复选框*/
  /*.el-table /deep/{*/
  /*  .el-table__header-wrapper {*/
  /*    .el-table__header {*/
  /*      thead {*/
  /*        tr :first-child {*/
  /*          .cell {*/
  /*            visibility: hidden;*/
  /*          }*/
  /*        }*/
  /*      }*/
  /*    }*/
  /*  }*/
  /*}*/



</style>
