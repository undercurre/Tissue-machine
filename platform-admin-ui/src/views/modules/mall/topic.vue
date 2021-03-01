<template>
  <div class="mod-topic">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.title" placeholder="活动主题" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.subtitle" placeholder="子标题" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('mall:topic:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('mall:topic:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
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
        prop="title"
        header-align="center"
        align="center"
        label="活动主题">
      </el-table-column>
      <el-table-column
        prop="avatar"
        header-align="center"
        align="center"
        label="化名">
        <template slot-scope="scope">
          <img style="height: 30%;width: 30%" v-if="scope.row.avatar" @click="openImg(scope.row.avatar)"
               :src="scope.row.avatar"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="itemPicUrl"
        header-align="center"
        align="center"
        label="活动条例图片">
        <template slot-scope="scope">
          <img style="height: 30%;width: 30%" v-if="scope.row.itemPicUrl" @click="openImg(scope.row.itemPicUrl)"
               :src="scope.row.itemPicUrl"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="subtitle"
        header-align="center"
        align="center"
        show-tooltip-when-overflow
        label="子标题">
      </el-table-column>
      <el-table-column
        prop="topicCategoryId"
        header-align="center"
        align="center"
        label="活动类别">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showCategoryDetail(scope.row.topicCategoryId)">
            {{scope.row.categoryTitle}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="priceInfo"
        header-align="center"
        align="center"
        label="活动价格">
      </el-table-column>
      <el-table-column
        prop="readCount"
        header-align="center"
        align="center"
        label="阅读数">
      </el-table-column>
      <el-table-column
        prop="scenePicUrl"
        header-align="center"
        align="center"
        label="场景图片链接">
        <template slot-scope="scope">
          <img style="height: 30%;width: 30%" v-if="scope.row.scenePicUrl" @click="openImg(scope.row.scenePicUrl)"
               :src="scope.row.scenePicUrl"/>
        </template>
      </el-table-column>
      <!--      <el-table-column-->
      <!--        prop="topicTemplateId"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="活动模板Id">-->
      <!--      </el-table-column>-->
      <!--      <el-table-column-->
      <!--        prop="topicTagId"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="活动标签Id">-->
      <!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:topic:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看
          </el-button>
          <el-button v-if="isAuth('mall:topic:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <el-button v-if="isAuth('mall:topic:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">
            删除
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
    <CategoryDetail v-if="categoryDetailVisible" ref="categoryDetail"></CategoryDetail>
  </div>
</template>

<script>
  import AddOrUpdate from './topic-add-or-update'
  import CategoryDetail from './topiccategory-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          title: '',
          subtitle: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false,
        categoryDetailVisible: false
      }
    },
    components: {
      AddOrUpdate,
      CategoryDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 查看会员详情
      showCategoryDetail (id) {
        this.categoryDetailVisible = true
        this.$nextTick(() => {
          this.$refs.categoryDetail.init(id, true)
        })
      },
      // 获取数据列表
      getDataList (index) {
        if (index !== undefined) {
          this.pageIndex = index
        }
        this.$http({
          url: '/mall/topic/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'title': this.searchForm.title,
            'subtitle': this.searchForm.subtitle
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
            url: '/mall/topic/delete',
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
