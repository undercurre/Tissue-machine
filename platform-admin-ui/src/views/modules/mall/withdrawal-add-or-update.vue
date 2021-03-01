<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="提现金额" prop="amount">
        <el-input v-model="dataForm.amount" :disabled="disabled" placeholder="提现金额"></el-input>
      </el-form-item>
      <el-form-item label="提现状态" prop="status">
        <el-input v-model="dataForm.status" :disabled="disabled" placeholder="提现状态"></el-input>
      </el-form-item>
      <el-form-item label="提现发起时间" prop="createTime">
        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="提现发起时间"></el-input>
      </el-form-item>
      <el-form-item label="提现成功时间" prop="finishTime">
        <el-input v-model="dataForm.finishTime" :disabled="disabled" placeholder="提现成功时间"></el-input>
      </el-form-item>
      <el-form-item label="处理人" prop="handlerId">
        <el-input v-model="dataForm.handlerId" :disabled="disabled" placeholder="处理人"></el-input>
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
          userBankId: '',
          amount: '',
          status: '',
          createTime: '',
          finishTime: '',
          handlerId: ''},
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
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/withdrawal/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.withdrawal
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
                url: `/ad/withdrawal/${!this.dataForm.id ? 'save' : 'update'}`,
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
