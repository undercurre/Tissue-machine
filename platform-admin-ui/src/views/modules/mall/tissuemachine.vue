<template>
  <div class="mod-tissuemachine">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="按机柜名称查找"></el-input>
      </el-form-item>
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
        <el-select v-model="searchForm.isDelete" placeholder="按启用状态查询" clearable>
          <el-option
            v-for="item in isDeleteOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.isCheckFlow" placeholder="按流量状态查询" clearable>
          <el-option
            v-for="item in isCheckFlowStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('ad:tissuemachine:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
<!--        <el-button v-if="isAuth('ad:tissuemachine:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="sn"
        header-align="center"
        align="center"
        label="终端唯一码">
      </el-table-column>

      <el-table-column
        prop="isOpenLocate"
        header-align="center"
        align="center"
        label="设备定位状态">
        <template slot-scope="scope">
          <el-button type="danger" v-if="scope.row.isOpenLocate === 0" @click="updateIsOpenLocate(scope.row.id, 1)">关闭</el-button>
          <el-button type="success" v-if="scope.row.isOpenLocate === 1" @click="updateIsOpenLocate(scope.row.id, 0)">开启</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        width="100"
        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
        label="机柜名称">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.name" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.name }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        width="150"
        header-align="center"
        align="center"
        label="机柜状态">
        <template slot-scope="scope">
          <el-button type="primary" v-if="scope.row.status === 1" @click="updateStatus(scope.row.id, 2)">设备正常</el-button>
          <el-button type="danger" v-if="scope.row.status === 2" @click="updateStatus(scope.row.id, 1)">设备维修中</el-button>
          <el-button type="danger" v-if="scope.row.status === 3" @click="updateStatus(scope.row.id, 1)">出纸异常</el-button>
          <el-button type="danger" v-if="scope.row.status === 4" @click="updateStatus(scope.row.id, 1)">位置异常</el-button>
          <el-button type="danger" v-if="scope.row.status === 5" @click="updateStatus(scope.row.id, 1)">纸巾不足</el-button>
          <el-button type="danger" v-if="scope.row.status === 6" @click="updateStatus(scope.row.id, 1)">电量不足</el-button>
          <el-button type="danger" v-if="scope.row.status === 7" @click="updateStatus(scope.row.id, 1)">语音修改异常</el-button>
          <el-button type="danger" v-if="scope.row.status === 8" @click="updateStatus(scope.row.id, 1)">设备离线</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="longitude"
        header-align="center"
        align="center"
        width="100"
        label="经度">
      </el-table-column>
      <el-table-column
        prop="latitude"
        header-align="center"
        align="center"
        width="90"
        label="纬度">
      </el-table-column>
      <el-table-column
        prop="machineImage"
        header-align="center"
        align="center"
        width="120"
        label="机柜图片">
        <template slot-scope="scope">
          <img v-if="scope.row.machineImage !== '' && scope.row.machineImage !== null"
            style="width: 100px; height: 100px"
            :src="scope.row.machineImage" @click="openImg(scope.row.machineImage)" ></img>
        </template>
      </el-table-column>
      <el-table-column
        prop="machineLogo"
        header-align="center"
        align="center"
        width="120"
        label="机柜Logo">
        <template slot-scope="scope">
          <img v-if="scope.row.machineLogo !== '' && scope.row.machineLogo !== null"
            style="width: 100px; height: 100px"
            :src="scope.row.machineLogo"
            @click="openImg(scope.row.machineLogo)"></img>
        </template>
      </el-table-column>
      <el-table-column
        prop="isShowLogo"
        header-align="center"
        align="center"
        width="100"
        label="是否显示Logo">
        <template slot-scope="scope">
          <el-button type="success" v-if="scope.row.isShowLogo === 1" @click="updateIsShowLogo(scope.row.id, 0)">显示</el-button>
          <el-button type="danger" v-if="scope.row.isShowLogo === 0" @click="updateIsShowLogo(scope.row.id, 1)">不显示</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="address"
        header-align="center"
        align="center"
        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; "
        label="地址">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.address" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.address }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="tissueNumber"
        header-align="center"
        align="center"
        label="纸巾库存">
      </el-table-column>
      <el-table-column
        prop="usedFlow"
        header-align="center"
        align="center"
        width="90"
        label="已使用流量">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.usedFlow" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.usedFlow }}MB</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="isDelete"
        header-align="center"
        align="center"
        width="100"
        label="是否启用">
        <template slot-scope="scope">
          <el-button type="success" v-if="scope.row.isDelete === 0" @click="updateIsDelete(scope.row.id, 1)">启用中</el-button>
          <el-button type="danger" v-if="scope.row.isDelete === 1" @click="updateIsDelete(scope.row.id, 0)">未启用</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="workTime"
        header-align="center"
        align="center"
        style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; "
        label="工作时间">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.workTime" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.workTime }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
        label="创建时间">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.createTime" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.createTime }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="updateTime"
        header-align="center"
        align="center"
        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
        label="修改设备时间">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.updateTime" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.updateTime }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        prop="machineVersion"
        header-align="center"
        align="center"
        label="设备版本信息">
      </el-table-column>
      <el-table-column
        prop="simCcid"
        header-align="center"
        align="center"
        label="CCID">
      </el-table-column>
      <el-table-column
        prop="serverUrl"
        header-align="center"
        align="center"
        label="服务器URL">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
        label="备注">
        <template slot-scope="scope">
          <el-tooltip :content="scope.row.remark" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{ scope.row.remark }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:tissuemachine:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
          <el-button v-if="isAuth('ad:tissuemachine:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="isAuth('ad:tissuemachine:restart')" type="text" size="small" @click="restart(scope.row.id)">重启</el-button>
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
  import AddOrUpdate from './tissuemachine-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          name: '',
          status: '',
          isDelete: '',
          // 是否查询流量超过预警
          isCheckFlow: ''
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
        isCheckFlowStatus: [{
          value: '0',
          label: '未超预警值'
        }, {
          value: '1',
          label: '超过预警值'
        }],
        dataList: [],
        url: 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/20200925/ede674d1f2b146d080da06cb0acc2775.jpg',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
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
      // 获取数据列表
      getDataList (index) {
        if (index !== undefined) {
          this.pageIndex = index
        }
        this.$http({
          url: '/ad/tissuemachine/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.searchForm.name,
            'status': this.searchForm.status,
            'isDelete': this.searchForm.isDelete,
            'isCheckFlow': this.searchForm.isCheckFlow
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
      updateIsOpenLocate (id, isOpenLocate) {
        this.$http({
          url: '/ad/tissuemachine/updateIsOpenLocate',
          method: 'post',
          data: {
            id: id,
            isOpenLocate: isOpenLocate
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
      },
      restart (id) {
        this.$confirm(`确定进行重启?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: '/ad/tissuemachine/restart',
            method: 'get',
            params: {
              id: id
            }
          }).then(({data}) => {
            if (data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
            }
          })
        })
      }
    }
  }
</script>
