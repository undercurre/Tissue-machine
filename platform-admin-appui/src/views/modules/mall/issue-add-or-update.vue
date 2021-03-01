<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="会员昵称" prop="nickname">
        <el-input v-model="dataForm.nickname" :disabled="disabled" placeholder="会员昵称"></el-input>
      </el-form-item>
      <el-form-item label="机柜名称" prop="nickname">
        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="机柜名称"></el-input>
      </el-form-item>
      <el-form-item label="消息内容" prop="content">
        <el-input v-model="dataForm.content" :disabled="disabled" placeholder="消息内容"></el-input>
      </el-form-item>
      <el-form-item prop="adIssueImageEntityList" label="反馈图片">
          <el-image
            style="width: 100px; height: 100px; display: inline-block;"
            v-for="item in dataForm.adIssueImageEntityList"
            :key="item" :src="item.imageUrl"
            :preview-src-list="srcList"
            lazy></el-image>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="处理状态" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio-button label="0">未处理</el-radio-button>
          <el-radio-button label="1">已处理</el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        disabled: false,
        visible: false,
        srcList: [],
        dataForm: {
          id: 0,
          userId: '',
          content: '',
          createTime: '',
          isDelete: '',
          status: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.srcList = []
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/issue/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.issue
                var that = this
                data.issue.adIssueImageEntityList.forEach(function (e) {
                  that.srcList.push(e.imageUrl)
                })
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (valid) {
              this.$http({
                url: `/ad/issue/${!this.dataForm.id ? 'save' : 'update'}`,
                method: 'post',
                data: this.dataForm
              }).then(({data}) => {
                if (data && data.code === 0) {
                  this.$message({
                    message: '操作成功',
                    type: 'success',
                    duration: 1500
                  })
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            }
          })
      }
    }
  }
</script>
