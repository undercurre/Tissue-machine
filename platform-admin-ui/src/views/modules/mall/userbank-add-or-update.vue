<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="银行名称" prop="bankName" label-width="100px">
        <el-input v-model="dataForm.bankName" :disabled="disabled" placeholder="银行名称"></el-input>
      </el-form-item>
      <el-form-item label="卡号" prop="bankNo" label-width="100px">
        <el-input v-model="dataForm.bankNo" :disabled="disabled" placeholder="银行卡卡号"></el-input>
      </el-form-item>
      <el-form-item label="持有人姓名" prop="userName" label-width="100px">
        <el-input v-model="dataForm.userName" :disabled="disabled" placeholder="持有人姓名"></el-input>
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
          bankName: '',
          bankNo: '',
          userId: '',
          userName: ''},
        dataRule: {
          bankName: [
            {required: true, message: '银行名称不能为空', trigger: 'change'}
          ],
          bankNo: [
            {required: true, message: '银行卡号不能为空', trigger: 'change'}
          ],
          userName: [
            {required: true, message: '持有人姓名不能为空', trigger: 'change'}
          ]
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
              url: `/ad/userbank/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.userbank
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (this.dataForm.bankNo.length !== this.dataForm.bankNo.replace(/[^\d]/g, '').length) {
              this.$message({
                message: '请再次查看银行卡是否输入正确!',
                type: 'warning',
                duration: 1500
              })
              return
            }
            // this.dataForm.bankNo = this.value.replace(/[^\d]/g,'');
            if (valid) {
              this.$http({
                url: `/ad/userbank/${!this.dataForm.id ? 'save' : 'update'}`,
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
