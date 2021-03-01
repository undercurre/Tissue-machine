<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabledShow ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="会员昵称" prop="nickname">
        <el-input v-model="dataForm.nickname" :disabled="disabledShow" placeholder="会员昵称"></el-input>
      </el-form-item>
      <el-form-item label="收款人姓名" prop="cardName">
        <el-input v-model="dataForm.cardName" :disabled="disabledShow" placeholder="收款人姓名"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号" prop="cardNumber">
        <el-input v-model="dataForm.cardNumber" :disabled="disabledShow" placeholder="银行卡号"></el-input>
      </el-form-item>
      <el-form-item label="银行卡类型" prop="cardType">
        <el-input v-model="dataForm.cardType" :disabled="disabledShow" placeholder="银行卡类型"></el-input>
      </el-form-item>
      <el-form-item label="银行名称" prop="bankName">
        <el-input v-model="dataForm.bankName" :disabled="disabledShow" placeholder="银行名称"></el-input>
      </el-form-item>
      <el-form-item label="银行卡状态" prop="cardStatus">
        <el-radio-group v-model="dataForm.cardStatus" :disabled="disabledShow">
          <el-radio :label="1">已绑定</el-radio>
          <el-radio :label="2">已解绑</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="绑定时间" prop="boundAt">
        <el-input v-model="dataForm.boundAt" :disabled="disabledShow" placeholder="绑定时间"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="!disabledShow" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        disabledShow: false,
        visible: false,
        dataForm: {
          id: 0,
          userId: '',
          nickname: '',
          cardName: '',
          cardNumber: '',
          cardType: '',
          bankTypeId: '',
          bankName: '',
          cardStatus: '',
          boundAt: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabledShow = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/mall/userbankcard/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.userbankcard
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
                url: `/mall/userbankcard/${!this.dataForm.id ? 'save' : 'update'}`,
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
