<template>
  <div class="mod-machinevoice">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
          <el-input v-model="searchForm.content" placeholder="语音内容" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('ad:machinevoice:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('ad:machinevoice:save')" type="primary" @click="addProvisionalVoice()">临时语音</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      @selection-change="selectionChangeHandle"
      border>
      <el-table-column
        prop="content"
        header-align="center"
        align="center"
        label="语音信息">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="showMachineList(scope.row.id)">{{scope.row.content}}</el-button>
          </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
        <template slot-scope="scope" >
          {{scope.row.createTime}}
        </template>
      </el-table-column>
      <el-table-column
        prop="validTime"
        header-align="center"
        align="center"
        label="有效时间">
        <template slot-scope="scope" >
          {{scope.row.validTime}}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('ad:machinevoice:info') && (scope.row.superiorid != null)" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
          <el-button v-if="isAuth('ad:machinevoice:update') && (scope.row.superiorid ===null)" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">追加</el-button>
          <el-button v-if="isAuth('ad:machinevoice:delete') && scope.row.superiorid != null" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <machine-detail v-if="machineDetailVisible" ref="machineDetail"></machine-detail>
    <add-provisional-voice v-if="addProvisionalVoiceVisible" ref="addProvisionalVoice"></add-provisional-voice>
    <el-dialog
      title="机柜列表"
      :visible.sync="machineListVisible"
      width="60%">
      <el-form :inline="true" :model="machineSearchForm" @keyup.enter.native="showMachineList(parentId)">
        <el-form-item>
          <el-input v-model="machineSearchForm.name" placeholder="机柜名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="showMachineList(parentId)">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="machineList"
        style="width: 100%">
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
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
          header-align="center"
          align="center"
          label="机柜状态">
          <template slot-scope="scope">
            <el-tag type="primary" v-if="scope.row.status === 1">设备正常</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 2">设备维修中</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 3">出纸异常</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 4">位置异常</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 5">纸巾不足预警</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 6">电量不足预警</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 7">语音修改异常</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 8">设备离线</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button v-if="isAuth('ad:machinevoice:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除语音</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="machineSizeChangeHandle"
        @current-change="machineCurrentChangeHandle"
        :current-page="machinePageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="machinePageSize"
        :total="machineTotalPage"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
      <span slot="footer" class="dialog-footer">
        <el-button @click="machineListVisible = false">关闭</el-button>
        <el-button type="primary" @click="machineListVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import AddOrUpdate from './machinevoice-add-or-update'
  import AddProvisionalVoice from './machineprovisionalvoice-add-or-update'
  import MachineDetail from './tissuemachine-add-or-update'
  import TableTreeColumn from '@/components/table-tree-column'
  export default {
    data () {
      return {
        searchForm: {
          name: ''
        },
        machineSearchForm: {
          name: ''
        },
        parentId: '',
        dataList: [],
        machineList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        machinePageIndex: 1,
        machinePageSize: 10,
        machineTotalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        addProvisionalVoiceVisible: false,
        machineDetailVisible: false,
        machineListVisible: false
      }
    },
    components: {
      AddOrUpdate,
      MachineDetail,
      TableTreeColumn,
      AddProvisionalVoice
    },
    activated (id) {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList (index) {
        if (index !== undefined) {
          this.pageIndex = index
        }
        this.$http({
          url: '/ad/machinevoice/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'content': this.searchForm.content
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = this.treeDataTranslate(data.page.records, 'id', 'superiorid', 'childrens')
            this.totalPage = data.page.total
          } else {
            this.dataList = []
            this.totalPage = 0
          }
        })
      },
      getMachineList (id) {
        this.$http({
          url: '/ad/tissuemachine/queryMachineListBySupId',
          method: 'get',
          params: {
            page: this.machinePageIndex,
            limit: this.machinePageSize,
            id: id,
            name: this.machineSearchForm.name
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.machineList = data.page.records
          } else {
            this.machineList = []
            this.machineTotalPage = 0
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
      // 每页数
      machineSizeChangeHandle (val) {
        this.machinePageSize = val
        this.machinePageIndex = 1
        this.getMachineList()
      },
      // 当前页
      machineCurrentChangeHandle (val) {
        this.machinePageIndex = val
        this.getMachineList()
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
      showMachineDetail (id) {
        this.machineDetailVisible = true
        this.$nextTick(() => {
          this.$refs.machineDetail.init(id, true)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 临时语音
      addProvisionalVoice (id) {
        this.addProvisionalVoiceVisible = true
        this.$nextTick(() => {
          this.$refs.addProvisionalVoice.init(id)
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
            url: '/ad/machinevoice/delete',
            method: 'post',
            data: ids
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.showMachineList(this.parentId)
            }
          })
        }).catch(() => {
        })
      },
      showMachineList (id) {
        this.machineListVisible = true
        this.parentId = id
        this.machineList = []
        this.getMachineList(id)
      }
    }
  }
</script>
