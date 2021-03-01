<template>
  <div class="mod-advert">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="广告商姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.mobile" placeholder="手机号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.workunitType" clearable placeholder="单位性质">
          <el-option
            key="1"
            label="企业"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="商家"
            value="2">
          </el-option>
          <el-option
            key="3"
            label="个人"
            value="3">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.status" clearable placeholder="回访状态">
          <el-option
            key="0"
            label="待回访"
            value="0">
          </el-option>
          <el-option
            key="1"
            label="已回访"
            value="1">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
<!--        <el-button v-if="isAuth('ad:advert:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
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
        prop="name"
        header-align="center"
        align="center"
        label="姓名">
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="workunitType"
        header-align="center"
        align="center"
        label="单位性质">
        <template slot-scope="scope">
          <el-button type="primary" v-if="scope.row.workunitType === 1">企业</el-button>
          <el-button type="warning" v-if="scope.row.workunitType === 2">商家</el-button>
          <el-button type="success" v-if="scope.row.workunitType === 3">个人</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="workunitName"
        header-align="center"
        align="center"
        label="单位名称">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="回访状态">
        <template slot-scope="scope">
          <el-button type="danger" v-if="scope.row.status === 0 && isAuth('ad:advert:changeStatus')" @click="changeStatus(scope.row.id, 1)">待回访</el-button>
          <el-button type="success" v-if="scope.row.status === 1 && isAuth('ad:advert:changeStatus')" @click="changeStatus(scope.row.id, 0)">已回访</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="广告类型">
        <template slot-scope="scope">
          <el-button type="warning" @click="showAdvert(scope.row.id)">广告类型</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:advert:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
          <el-button v-if="isAuth('ad:advert:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="isAuth('ad:advert:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <advert-detail v-if="advertVisible" ref="advertDetail"></advert-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './advert-add-or-update'
  import UserDetail from './user-add-or-update'
  import AdvertDetail from './advertadverttype-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          name: '',
          mobile: '',
          workunitType: '',
          status: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        userDetailVisible: false,
        advertVisible: false
      }
    },
    components: {
      AddOrUpdate,
      UserDetail,
      AdvertDetail
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
      // 查看广告类型详情
      showAdvert (id) {
        this.advertVisible = true
        this.$nextTick(() => {
          this.$refs.advertDetail.init(id, true)
        })
      },
      // 获取数据列表
      getDataList (index) {
        if (index !== undefined) {
          this.pageIndex = index
        }
        this.$http({
          url: '/ad/advert/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.searchForm.name,
            'mobile': this.searchForm.mobile,
            'workunitType': this.searchForm.workunitType,
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
      // 更改寻访状态
      changeStatus (id, status) {
        this.$http({
          url: '/ad/advert/changeStatus',
          method: 'get',
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
            url: '/ad/advert/delete',
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
