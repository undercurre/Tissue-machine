<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="广告商姓名" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="广告商姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model.number="dataForm.mobile" :disabled="disabled" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="单位性质" prop="workunitType">
        <el-radio-group v-model="dataForm.workunitType" :disabled="disabled">
          <el-radio :label="1">企业</el-radio>
          <el-radio :label="2">商家</el-radio>
          <el-radio :label="3">个人</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="单位名称" prop="workunitName" v-if="dataForm.workunitType != 3">
        <el-input v-model="dataForm.workunitName" :disabled="disabled" placeholder="单位名称"></el-input>
      </el-form-item>
      <el-form-item label="回访状态" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio :label="0">待回访</el-radio>
          <el-radio :label="1">已回访</el-radio>
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
        dataForm: {
          id: 0,
          name: '',
          mobile: '',
          workunitType: '',
          workunitName: '',
          advertTypeId: '',
          status: '',
          isDelete: ''
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/advert/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.advert
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
                url: `/ad/advert/${!this.dataForm.id ? 'save' : 'update'}`,
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
