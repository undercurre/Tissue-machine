<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="提现银行卡" prop="userBankId" label-width="100px">
        <el-select v-model="dataForm.userBankId" placeholder="请选择">
          <el-option
            v-for="item in bankOptions"
            :key="item.id"
            :label="item.bankName + ' ' +item.bankNo"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="提现金额" prop="amount" label-width="100px">
        <el-input v-model.number="dataForm.amount" :disabled="disabled" placeholder="提现金额"></el-input>
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
        bankOptions: '',
        dataForm: {
          id: 0,
          userBankId: '',
          amount: '',
          status: '',
          createTime: '',
          finishTime: '',
          handlerId: ''},
        dataRule: {
          userBankId: [
            {required: true, message: '银行卡不能为空', trigger: 'blur'}
          ],
          amount: [
            {required: true, message: '提现金额不能为空', trigger: 'blur'}
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
          this.$http({
            url: '/ad/userbank/queryAll',
            method: 'get'
          }).then(({data}) => {
            this.bankOptions = data.list
          })
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
