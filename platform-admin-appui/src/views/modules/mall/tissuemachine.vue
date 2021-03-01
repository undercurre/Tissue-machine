<template xmlns="http://www.w3.org/1999/html">
  <div class="mod-tissuemachine">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()" class="searchList">
      <el-form-item class="longInput">
        <el-input v-model="searchForm.name" placeholder="请输入机柜名称">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </el-form-item>
<!--      <el-form-item>-->
<!--        <el-select v-model="searchForm.status" placeholder="按维修状态查询" clearable>-->
<!--          <el-option-->
<!--            v-for="item in statusOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-select v-model="searchForm.isDelete" placeholder="按启用状态查询" clearable>-->
<!--          <el-option-->
<!--            v-for="item in isDeleteOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item class="searchBtn">
        <el-button @click="searchByIdOrName()" type="primary">查询</el-button>
<!--        <el-button v-if="isAuth('ad:tissuemachine:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
<!--        <el-button v-if="isAuth('ad:tissuemachine:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <div class="LetTableFlex">
    <el-table
      :data="dataList"
      border
      style="width: calc(100% - 30px);"
      stripe
      height="calc(100vh - 171px)"
      class="tablePart"
      :cell-style="tableCellStyle"
      :header-cell-style="tableHeaderColor"
      >
      <el-table-column
        prop="sn"
        header-align="center"
        align="center"
        label="sn码"
      >
      </el-table-column>
<!--      <el-table-column-->
<!--        type="index"-->
<!--        width="50"-->
<!--        align="center"-->
<!--        label="序号">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="机箱ID"
        >
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="vsn"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="协议版本号">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="softVersion"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="机柜软件版本号">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
        label="机箱名称"
        >
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.name" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.name }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="机箱状态">
        <template slot-scope="scope">
          <div  v-if="scope.row.status === 1" style="color: blue">设备正常</div>
          <div  v-if="scope.row.status === 2" style="color: blue">设备维修中</div>
          <div  v-if="scope.row.status === 3" style="color: red">出纸异常</div>
          <div  v-if="scope.row.status === 4" style="color: red">位置异常</div>
          <div  v-if="scope.row.status === 5" style="color: red">纸巾不足</div>
          <div  v-if="scope.row.status === 6" style="color: red">电量不足</div>
          <div  v-if="scope.row.status === 7" style="color: red">语音修改异常</div>
          <div  v-if="scope.row.status === 8" style="color: red">离线</div>
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="longitude"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="100"-->
<!--        label="经度">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="latitude"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="90"-->
<!--        label="纬度">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="machineImage"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="120"-->
<!--        label="机柜图片">-->
<!--        <template slot-scope="scope">-->
<!--          <el-image-->
<!--            style="width: 100px; height: 100px"-->
<!--            :src="scope.row.machineImage"></el-image>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="machineLogo"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="120"-->
<!--        label="机柜Logo">-->
<!--        <template slot-scope="scope">-->
<!--          <el-image-->
<!--            style="width: 100px; height: 100px"-->
<!--            :src="scope.row.machineLogo"></el-image>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="isShowLogo"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="100"-->
<!--        label="是否显示Logo">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="success" v-if="scope.row.isShowLogo === 1" @click="updateIsShowLogo(scope.row.id, 0)">显示</el-button>-->
<!--          <el-button type="danger" v-if="scope.row.isShowLogo === 0" @click="updateIsShowLogo(scope.row.id, 1)">不显示</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="address"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; "-->
<!--        label="地址">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tooltip :content="scope.row.address" placement="top">-->
<!--            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.address }}</div>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="workTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; "-->
<!--        label="工作时间">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tooltip :content="scope.row.workTime" placement="top">-->
<!--            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.workTime }}</div>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="tissueNumber"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="纸巾库存">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="createTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"-->
<!--        label="创建时间">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tooltip :content="scope.row.createTime" placement="top">-->
<!--            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.createTime }}</div>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="updateTime"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"-->
<!--        label="修改设备时间">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tooltip :content="scope.row.updateTime" placement="top">-->
<!--            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.updateTime }}</div>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="remark"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"-->
<!--        label="备注">-->
<!--        <template slot-scope="scope">-->
<!--          <el-tooltip :content="scope.row.remark" placement="top">-->
<!--            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.remark }}</div>-->
<!--          </el-tooltip>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--        prop="isDelete"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="100"-->
<!--        label="是否启用">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="success" v-if="scope.row.isDelete === 0" @click="updateIsDelete(scope.row.id, 1)">启用中</el-button>-->
<!--          <el-button type="danger" v-if="scope.row.isDelete === 1" @click="updateIsDelete(scope.row.id, 0)">未启用</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="100%"
        label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="isAuth('ad:tissuemachine:info')"
            type="text" size="small"
            @click="showDetails(scope.row.id)"
            class="blackBtn">查看</el-button>
          <el-button
            v-if="isAuth('ad:tissuemachine:update')"
            type="text" size="small"
            @click="UpdateHandle(scope.row.id)"
            class="blackBtn">修改</el-button>
<!--          <el-button v-if="isAuth('ad:tissuemachine:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
<!--          <el-button v-if="isAuth('ad:tissuemachine:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
        </template>
      </el-table-column>
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

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './tissuemachine-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          name: '',
          status: '',
          isDelete: ''
        },
        statusOptions: [{
          value: '1',
          label: '设备正常'
        }, {
          value: '2',
          label: '设备维修中'
        }],
        isDeleteOptions: [{
          value: '0',
          label: '设备已启用'
        }, {
          value: '1',
          label: '设备未启用'
        }],
        dataList: [],
        url: 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/20200925/ede674d1f2b146d080da06cb0acc2775.jpg',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        pagerCount: 5,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
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
      // 根据名称和id查询机柜
      searchByIdOrName () {
        if (/.*[\u4e00-\u9fa5]+.*$/.test(this.searchForm.name)) {
          this.getDataList()
        } else {
          if (this.searchForm.name === '' || this.searchForm.name === null) {
            this.getDataList()
          }
        }
      },
      // 获取数据列表
      getDataList () {
        this.$http({
          url: '/ad/tissuemachine/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.searchForm.name
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
      // addOrUpdateHandle (id) {
      //   this.addOrUpdateVisible = true
      //   this.$nextTick(() => {
      //     this.$refs.addOrUpdate.init(id)
      //   })
      // },
      // 修改
      UpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, false)
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
            url: '/ad/tissuemachine/delete',
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
          url: '/ad/tissuemachine/updateStatus',
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
      },
      updateIsDelete (id, isDelete) {
        this.$http({
          url: '/ad/tissuemachine/updateIsDelete',
          method: 'post',
          data: {
            id: id,
            isDelete: isDelete
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
      },
      updateIsShowLogo (id, isShowLogo) {
        this.$http({
          url: '/ad/tissuemachine/updateIsShowLogo',
          method: 'post',
          data: {
            id: id,
            isShowLogo: isShowLogo
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
<style lang="scss" scoped>
  .blackBtn {
    color: #082F40;
  }
  .LetTableFlex{
    width: 100%;
    .el-table {
      margin: 0 auto;
    .el-table__body td {
      padding: 0;
      height: 135px;
    }
  }
  }
</style>
