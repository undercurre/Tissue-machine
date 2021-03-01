<template>
  <el-dialog
    :title="dataForm.id ? '查看' : ''"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :class="dataForm.id ? 'thePageDialog':''"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="100px"
    >
      <el-form-item label="提现金额" prop="amount">
        <el-input v-model="dataForm.amount" :disabled="disabled" placeholder="提现金额"></el-input>
      </el-form-item>
      <el-form-item label="提现人：" prop="userName">
        <el-input v-model="dataForm.userName" :disabled="disabled" placeholder="提现人"></el-input>
      </el-form-item>
      <el-form-item label="提现银行:" prop="bankName">
        <el-input v-model="dataForm.bankName" :disabled="disabled" placeholder="提现银行"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号:" prop="bankNo">
        <el-input v-model="dataForm.bankNo" :disabled="disabled" placeholder="银行卡号"></el-input>
      </el-form-item>
      <el-form-item label="银行卡归属人：" prop="userName" >
        <el-input v-model="dataForm.userName" :disabled="disabled" placeholder="银行卡归属人"></el-input>
      </el-form-item>
      <el-form-item label="手机号:" prop="mobile" >
        <el-input v-model="dataForm.mobile" :disabled="disabled" placeholder="银行卡绑定手机号"></el-input>
      </el-form-item>
      <el-form-item label="提现状态：" prop="status">
        <el-tag v-if="dataForm.status == 1" class="Status" style="background-color: #1FBF84">
          提现成功
        </el-tag>
        <el-tag v-else-if="dataForm.status == 2" class="Status" style="background-color: #FF3030">
          提现失败
        </el-tag>
        <el-tag v-else class="Status" style="background-color: #FF9700">
          审核中
        </el-tag>
      </el-form-item>
      <el-form-item v-if="dataForm.status == 2" label="失败原因" prop="becauseOfFail">
        <el-input v-model="dataForm.description" :disabled="disabled" placeholder="失败原因"></el-input>
      </el-form-item>
      <el-form-item label="提现时间：" prop="createTime">
        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="提现发起时间"></el-input>
      </el-form-item>
<!--      <el-form-item label="提现成功时间" prop="finishTime">-->
<!--        <el-input v-model="dataForm.finishTime" :disabled="disabled" placeholder="提现成功时间"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="处理人" prop="handlerId">-->
<!--        <el-input v-model="dataForm.handlerId" :disabled="disabled" placeholder="处理人"></el-input>-->
<!--      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
<!--      <el-button @click="visible = false">取消</el-button>-->
<!--      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit()">确定</el-button>-->
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
          realName: '',
          bankName: '',
          cardNumber: '',
          description: '',
          handlerId: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          amount: [
            {required: true, message: '提现金额不能为空', trigger: 'blur'}
          ],
          bankNo: [
            {required: true, message: '银行卡号不能为空', trigger: 'blur'}
          ],
          bankName: [
            {required: true, message: '银行不能为空', trigger: 'blur'}
          ],
          mobile: [
            {required: true, message: '银行卡绑定手机号不能为空', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '提现状态不能为空', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '提现状态不能为空', trigger: 'blur'}
          ],
          createTime: [
            {required: true, message: '提现时间不能为空', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '提现人不能为空', trigger: 'blur'}
          ],
          realName: [
            {required: true, message: '提现人不能为空', trigger: 'blur'}
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
        this.$refs['dataForm'].validate((valid) => {
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
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              }
            })
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .thePageDialog /deep/ {
    .el-dialog {
      background-color: transparent;
      margin: 10vh auto!important;
      .el-dialog__header{
        display: flex;
        justify-content: center;
        .el-dialog__title{
          font-size: 16px;
          color: #ffffff;
        }
      }
      .el-dialog__body{
        background-color: #ffffff;
        height: calc(80vh - 120px);
        overflow-y: auto;
        overflow-x: hidden;
        .el-form {
          .el-form-item {
            margin: 0 0 15px;
            .el-form-item__label {
              font-size: 12px;
              padding: 0;
              text-align: left;
              line-height: 30px;
              height: 30px;
            }
            .el-form-item__content {
              line-height: 30px;
              margin-left: 100px!important;
              .el-input {
                font-size: 12px;
                .el-input__inner {
                  color: #333;
                  background-color: #F5F5F5;
                  border-radius: 2px;
                  height: 30px;
                  line-height: 30px;
                }
              }
            }

            .Status {
              color: #ffffff;
              height: 30px;
              width: 48%;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
          }
        }
      }
      .el-dialog__footer {
        background-color: #ffffff;

        .dialog-footer {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          .el-button {
            width: 60%;
          }
        }
      }
    }
  }
  /*::v-deep .el-dialog{*/
  /*  .el-dialog__header{*/
  /*    .dialogTitle{*/
  /*      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/dialogTitleBackGroundPic3xZpq.png");*/
  /*      width: 100%;*/
  /*      height: auto;*/
  /*      font-size: 16px;*/
  /*    }*/
  /*  }*/
  /*}*/

</style>
