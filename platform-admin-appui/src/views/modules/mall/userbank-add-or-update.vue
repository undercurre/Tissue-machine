<template>
  <el-dialog
    :title="!dataForm.id ? '新增银行卡' : !isDisabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    class="thePageDialog"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      class="thePageForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="100px"
    >
      <el-form-item label="绑定银行:" prop="bankName">
        <el-input v-model="dataForm.bankName" :disabled="isDisabled" placeholder="银行名称"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号:" prop="bankNo">
        <el-input v-model="dataForm.bankNo" :disabled="isDisabled" placeholder="银行卡卡号"></el-input>
      </el-form-item>
      <el-form-item label="银行卡归属人:" prop="userName">
        <el-input v-model="dataForm.userName" :disabled="isDisabled" placeholder="持有人姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号:" prop="mobile">
        <el-input v-model="dataForm.mobile" :disabled="isDisabled" placeholder="银行卡绑定手机号"></el-input>
      </el-form-item>
      <el-form-item label="银行卡类型:" prop="type">
        <el-select v-model="dataForm.type" :disabled="isDisabled" placeholder="银行卡类型">
          <el-option
            v-for="item in typeOptions"
            :label="item.name"
            :value="item.id"
            :key="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" v-if="!isDisabled">
      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit()">{{ theComfirmText }}</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        isDisabled: false,
        visible: false,
        theComfirmText: '确定增加',
        dataForm: {
          id: 0,
          bankName: '',
          bankNo: '',
          userId: '',
          type: null,
          mobile: '',
          userName: ''
        },
        typeOptions: [
          {
            id: 0,
            name: '储蓄卡'
          },
          {
            id: 1,
            name: '信用卡'
          },
          {
            id: 2,
            name: '准贷记卡'
          },
          {
            id: 3,
            name: '预付费卡'
          }
        ],
        dataRule: {
          bankName: [
            {required: true, message: '银行名称不能为空', trigger: 'blur'}
          ],
          bankNo: [
            {required: true, message: '银行卡号不能为空', trigger: 'blur'}
          ],
          mobile: [
            {required: true, message: '银行卡绑定手机号不能为空', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '银行卡类型不能为空', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '持有人姓名不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.isDisabled = disabled
        this.dataForm.id = id || ''
        if (id) {
          this.theComfirmText = '确定修改'
        } else {
          this.theComfirmText = '确定添加'
        }
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
<style lang="scss" scoped>
  .thePageDialog /deep/ {
    .el-dialog {
      margin: 10vh auto !important;
      background-color: transparent;
      .el-dialog__header{
        display: flex;
        justify-content: center;
        .el-dialog__title{
          font-size: 16px;
          color: #fff;
        }
      }
      .el-dialog__body{
        background-color: #fff;
        height: calc(80vh - 121px);
        overflow: auto;
        position: relative;
        padding: 15px;
        .thePageForm {
          position: relative;
          .el-form-item {
            margin: 0 0 15px;
            .el-form-item__label {
              font-size: 12px;
              padding: 0 8px 0 0;
              text-align: left;
              line-height: 30px;
            }
            .el-form-item__content {
              .el-form-item__error{
                padding-top: 2px;
              }
              line-height: 30px;
              .el-input {
                font-size: 12px;
                .el-input__inner {
                  color: #333;
                  background-color: #fff;
                  border-radius: 2px;
                  height: 30px;
                  line-height: 30px;
                }
              }
              .el-select {
                width: 100%;
                .el-input {
                  font-size: 12px;
                  .el-input__inner {
                    color: #333;
                    background-color: #fff;
                    border-radius: 2px;
                    height: 30px;
                    line-height: 30px;
                  }
                }
              }
              .el-textarea.is-disabled .el-textarea__inner {
                background-color: #fff;
              }
              .el-radio-group {
                .el-radio-button {
                  .el-radio-button__inner {
                    font-size: 12px;
                  }
                  & + .is-disabled {
                    .el-radio-button__inner {
                      background-color: #1FBF84;
                    }
                  }
                }
              }
            }
          }
        }


        .machineLocate{
          font-size: 12px;
          background-color: #1FBF84;
          color: #ffffff;
          padding: 1px;
          border-radius: 4px;
          width: 92px;
          height: 21px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
      .el-dialog__footer {
        background-color: #fff;
        padding: 10px 15px;
        border-top: 1px #efefef solid;
        .dialog-footer {
          width: 100%;
          display: block;
          position: relative;
          .el-button {
            font-size: 14px;
            background-color: #082F40;
            border-color: #082F40;
            display: block;
            width: 100%;
            padding: 15px 20px;
            border-radius: 3px;
          }
        }
      }
    }
  }
</style>
