<template>
  <div class="mod-goods">
    <el-form
      :inline="true"
      :model="searchForm"
      @keyup.enter.native="getDataList()"
      class="searchList"
    >
      <el-form-item class="longInput">
        <el-input v-model="searchForm.name" placeholder="输入商品名称" clearable>
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </el-form-item>
      <el-form-item class="searchBtn">
        <el-button @click="getDataList()" type="primary">查询</el-button>
<!--        <el-button v-if="isAuth('ad:goods:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
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
        header-align="center"
        align="center"
        label="序号">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="商品名称">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="上架状态">
        <template slot-scope="scope">
          <div style="color: #FF3030;" v-if="scope.row.status === 0">未上架</div>
          <div style="color: #1FBF84;" v-if="scope.row.status === 1">已上架</div>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="70"
        label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="isAuth('ad:goods:info')"
            type="text"
            class="blackBtn"
            size="small"
            @click="showDetails(scope.row.id)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>
    <el-pagination
      style="margin: 15px 0"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-size="pageSize"
      :total="totalPage"
      :pager-count="pagerCount"
      layout="prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <machineStock-add-or-update v-if="machineStockAddOrUpdateVisible" ref="machineStockAddOrUpdate" @refreshDataList="getDataList"></machineStock-add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './goods-add-or-update'
  import MachineStockAddOrUpdate from './machinestock-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          name: '',
          status: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        pagerCount: 5,
        dataListSelections: [],
        addOrUpdateVisible: false,
        machineStockAddOrUpdateVisible: false,
        statusOptions: [{
          label: '未上架',
          value: '0'
        }, {
          label: '已上架',
          value: '1'
        }]
      }
    },
    components: {
      AddOrUpdate,
      MachineStockAddOrUpdate
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
          url: '/ad/goods/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.searchForm.name,
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
      machineStockAddOrUpdateHandle (goodsId) {
        this.machineStockAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.machineStockAddOrUpdate.init('', false, goodsId)
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
            url: '/ad/goods/delete',
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
      changeStatus (id, status) {
        this.$http({
          url: '/ad/goods/changeStatus',
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
      }
    }
  }
</script>
<style lang="scss" scoped>
.blackBtn {
  color: #082F40;
}
.LetTableFlex{
  width: 100%;
  .el-table {
    margin: 0 auto;
  }
}
</style>
