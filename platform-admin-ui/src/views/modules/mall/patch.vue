<template>
  <div class="mod-patch">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
<!--      <el-form-item>-->
<!--        <el-input v-model="searchForm.name" placeholder="参数名" clearable></el-input>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-select v-model="searchForm.type" placeholder="按贴片位置查询" clearable>
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.avail" placeholder="按启用状态查询" clearable>
          <el-option
            v-for="item in availOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('ad:patch:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
<!--        <el-button v-if="isAuth('ad:patch:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        width="150"
        label="贴片位置类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1">机柜贴片</el-tag>
<!--          <el-tag v-if="scope.row.type === 2" type="success">下单贴片</el-tag>-->
          <el-tag v-if="scope.row.type === 3" type="warning">个人中心贴片</el-tag>
          <el-tag v-if="scope.row.type === 4" type="danger">出货成功贴片</el-tag>
          <el-tag v-if="scope.row.type === 5" type="info">首页轮播图贴片</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="imageUrl"
        header-align="center"
        align="center"
        width="400"
        label="图片">
        <template slot-scope="scope">
          <img
            style="width: 380px; height: 120px"
            :src="scope.row.imageUrl"
            @click="openImg(scope.row.imageUrl)"></img>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="avail"
        header-align="center"
        align="center"
        label="是否启用">
        <template slot-scope="scope">
          <el-button type="danger" v-if="scope.row.avail === 0" @click="updateAvail(scope.row.id, 1)">未启用</el-button>
          <el-button type="success" v-if="scope.row.avail === 1" @click="updateAvail(scope.row.id, 0)">已启用</el-button>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:patch:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
          <el-button v-if="isAuth('ad:patch:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="isAuth('ad:patch:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  </div>
</template>

<script>
  import AddOrUpdate from './patch-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          type: '',
          avail: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        typeOptions: [{
          value: '1',
          label: '机柜贴片'
        }, {
          value: '3',
          label: '个人中心贴片'
        }, {
          value: '4',
          label: '出货成功贴片'
        }, {
          value: '5',
          label: '首页轮播图贴片'
        }],
        availOptions: [{
          value: '1',
          label: '已启用'
        }, {
          value: '0',
          label: '未启用'
        }]
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList (index) {
        if (index !== undefined) {
          this.pageIndex = index
        }
        this.$http({
          url: '/ad/patch/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'type': this.searchForm.type,
            'avail': this.searchForm.avail
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
            url: '/ad/patch/delete',
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
      updateAvail (id, avail) {
        this.$http({
          url: '/ad/patch/updateAvail',
          method: 'post',
          data: {
            id: id,
            avail: avail
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
