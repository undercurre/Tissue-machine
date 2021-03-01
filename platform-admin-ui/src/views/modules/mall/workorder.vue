<template>
  <div class="mod-workorder">
<!--    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">-->
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
<!--      <el-form-item>-->
<!--        <el-button @click="getDataList()">查询</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="workerId"
        header-align="center"
        align="center"
        label="指派人员">
        <template slot-scope="scope">
          <el-tag v-if="!scope.row.workerId" type="danger">暂无指派人员</el-tag>
          <el-button type="text" size="small" @click="showUserDetails(scope.row.workerId)">{{scope.row.workerName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="workType"
        header-align="center"
        align="center"
        label="工单类型">
        <template slot-scope="scope">
<!--          <el-tag v-if="scope.row.workType === 0">广告采购</el-tag>
          <el-tag v-if="scope.row.workType === 1" type="success">设备维护</el-tag>
          <el-tag v-if="scope.row.workType === 2" type="warning">送货</el-tag>
          <el-tag v-if="scope.row.workType === 3" type="danger">贴印刷广告</el-tag>-->
          <el-tag>{{ workTypeMap[scope.row.workType] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="工单状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="danger">待开始</el-tag>
          <el-tag v-if="scope.row.status === 1" >进行中</el-tag>
          <el-tag v-if="scope.row.status === 2" type="success">已处理</el-tag>
          <el-tag v-if="scope.row.status === 3" type="danger">已作废</el-tag>
          <el-tag v-if="scope.row.status === 4" type="danger">被移除</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="isEnd"
        header-align="center"
        align="center"
        label="工单完成状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isEnd === 0" type="danger">未完结</el-tag>
          <el-tag v-if="scope.row.isEnd === 1" type="danger">待完结确认</el-tag>
          <el-tag v-if="scope.row.isEnd === 2" type="success">已完结</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="工单创建时间">
      </el-table-column>
      <el-table-column
        prop="machineId"
        header-align="center"
        align="center"
        label="对应机柜">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showMachineDetails(scope.row.machineId)">{{scope.row.machineName}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="acceptTime"
        header-align="center"
        align="center"
        label="运维人员接单时间">
      </el-table-column>
      <el-table-column
        prop="finishTime"
        header-align="center"
        align="center"
        label="工单完成时间">
      </el-table-column>
      <el-table-column
        prop="des"
        header-align="center"
        align="center"
        label="工单描述">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:workorder:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
<!--          <el-button v-if="isAuth('ad:workorder:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:workorder:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="领取工单">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status != 0" type="danger">此工单已被领取</el-tag>
          <el-button v-if="isAuth('ad:workorder:update') && scope.row.status === 0" type="primary" size="small" @click="getWorkOrder(scope.row.id)">领取</el-button>
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
  import AddOrUpdate from './workorder-add-or-update'
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
        workTypeMap: {},
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        userDetailVisible: false,
        machineDetailVisible: false,
        workTypeOptions: [{
          label: '广告采购',
          value: 0
        }, {
          label: '设备维护',
          value: 1
        }, {
          label: '送货',
          value: 2
        }, {
          label: '贴印刷广告',
          value: 3
        }],
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
      UserDetail
    },
    created () {
      this.$http({
        url: '/ad/worktype/queryAll',
        method: 'get'
      }).then(({data}) => {
        let workTypeList = data.list
        for (let i = 0; i < workTypeList.length; i++) {
          this.workTypeMap[workTypeList[i].id] = workTypeList[i].name
        }
      })
    },
    activated () {
      this.getDataList()
    },
    methods: {
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
