<template>
  <div class="mod-issue">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="searchForm.status" placeholder="按维修状态查询" clearable>
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('ad:issue:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('ad:issue:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员昵称">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showUserDetails(scope.row.userId)">{{scope.row.nickname}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="machineName"
        header-align="center"
        align="center"
        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden;"
        label="机柜信息">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showMachineDetails(scope.row.machineId)">{{scope.row.machineName}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="content"
        header-align="center"
        align="center"
        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden;"
        label="消息内容">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.content">
            <div>{{scope.row.content}}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="adIssueImageEntityList"
        header-align="center"
        align="center"
        style="overflow: scroll"
        width="320"
        label="反馈图片">
        <template slot-scope="scope">
          <el-image style="width: 100px; height: 100px; display: inline-block;" v-for="item in scope.row.adIssueImageEntityList" :key="item.id" :src="item.imageUrl" lazy></el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="处理状态">
        <template slot-scope="scope">
          <el-button type="danger" v-if="scope.row.status === 0" @click="updateStatus(scope.row.id, 1)">未处理</el-button>
          <el-button type="success" v-if="scope.row.status === 1" @click="updateStatus(scope.row.id, 0)">已处理</el-button>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:issue:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
<!--          <el-button v-if="isAuth('ad:issue:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:issue:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
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
  import AddOrUpdate from './issue-add-or-update'
  import UserDetail from './user-add-or-update'
  import MachineDetail from './tissuemachine-add-or-update.vue'
  export default {
    data () {
      return {
        searchForm: {
          status: ''
        },
        statusOptions: [{
          label: '未处理',
          value: '0'
        }, {
          label: '已处理',
          value: '1'
        }],
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
          url: '/ad/issue/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'status': this.searchForm.status
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
            url: '/ad/issue/delete',
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
      updateStatus (id, status) {
        this.$http({
          url: '/ad/issue/updateStatus',
          method: 'post',
          data: {
            id: id,
            status: status
          }
        }).then(({data}) => {
          if (data.code === 0) {
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
