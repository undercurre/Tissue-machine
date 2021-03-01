<template>
  <div class="mod-user">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.userName" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-dict code="SEX" v-model="searchForm.gender"></el-dict>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.mobile" placeholder="手机号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
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
        prop="userName"
        header-align="center"
        align="center"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="nickname"
        header-align="center"
        align="center"
        label="微信昵称">
      </el-table-column>
      <el-table-column
        prop="gender"
        header-align="center"
        align="center"
        label="性别">
        <template slot-scope="scope">
          <span>{{transDict('SEX',scope.row.gender)}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="birthday"
        header-align="center"
        align="center"
        label="生日">
        <template slot-scope="scope">
          <span>{{scope.row.birthday}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="headImgUrl"
        header-align="center"
        align="center"
        label="用户头像">
        <template slot-scope="scope">
          <img style="height: 50%;width: 50%" @click="openImg(scope.row.headImgUrl)" :src="scope.row.headImgUrl"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="registerTime"
        header-align="center"
        align="center"
        label="注册时间">
        <template slot-scope="scope">
          <span>{{scope.row.registerTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastLoginTime"
        header-align="center"
        align="center"
        label="最后登录时间">
        <template slot-scope="scope">
          <span>{{scope.row.lastLoginTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="userLevelName"
        header-align="center"
        align="center"
        label="会员等级">
      </el-table-column>
      <el-table-column
        prop="registerIp"
        header-align="center"
        align="center"
        label="注册ip">
      </el-table-column>
      <el-table-column
        prop="subscribe"
        header-align="center"
        align="center"
        label="是否关注">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.subscribe === 0" size="small" type="danger">否</el-tag>
          <el-tag v-else-if="scope.row.subscribe === 1" size="small" type="success">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="subscribeTime"
        header-align="center"
        align="center"
        label="关注时间">
        <template slot-scope="scope">
          <span>{{scope.row.subscribeTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="signAllIntegral"
        header-align="center"
        align="center"
        label="总积分">
      </el-table-column>
      <el-table-column
        prop="signUsedIntegral"
        header-align="center"
        align="center"
        label="已使用积分">
      </el-table-column>
      <el-table-column
        prop="balance"
        header-align="center"
        align="center"
        label="消费金">
      </el-table-column>
      <el-table-column
        prop="tissueCount"
        header-align="center"
        align="center"
        label="剩余可领取纸巾">
<!--        <template slot-scope="scope">-->
<!--          <el-button type="text" size="small" @click="showAccountLog(scope.row.id)">{{scope.row.tissueCount}}-->
<!--          </el-button>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:user:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看
          </el-button>
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
    <accountlog v-if="accountlogVisible" ref="accountlog"></accountlog>
  </div>
</template>

<script>
  import Accountlog from './accountlog'
  import AddOrUpdate from './user-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          userName: '',
          gender: '',
          mobile: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        accountlogVisible: false
      }
    },
    components: {
      Accountlog,
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      showAccountLog (userId) {
        this.accountlogVisible = true
        this.$nextTick(() => {
          this.$refs.accountlog.init(userId)
        })
      },
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/mall/user/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userName': this.searchForm.userName,
            'gender': this.searchForm.gender,
            'mobile': this.searchForm.mobile
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
            url: '/mall/user/delete',
            method: 'post',
            data: ids
          }).then(({ data }) => {
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
