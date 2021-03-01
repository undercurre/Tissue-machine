<template>
  <el-dialog
    :title="isAdd ? '添加银行卡' : isUpdate ? '修改' : '查看'"
    :visible.sync="visible"
     class="thePageDialog"
  >
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
<!--      <el-form-item label="会员昵称" prop="nickname">-->
<!--        <el-input v-model="dataForm.nickname" :disabled="disabled" placeholder="会员昵称"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="收款人姓名" prop="cardName">-->
<!--        <el-input v-model="dataForm.cardName" :disabled="disabled" placeholder="收款人姓名"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="绑定银行:" prop="bankName">
        <el-input v-model="dataForm.bankName" :disabled="disabled" ></el-input>
      </el-form-item>
      <el-form-item label="银行卡号:" prop="bankNo">
        <el-input v-model="dataForm.bankNo" :disabled="disabled" ></el-input>
      </el-form-item>
      <el-form-item label="银行卡归属人:" prop="userName">
        <el-input v-model="dataForm.userName" :disabled="disabled" ></el-input>
      </el-form-item>
      <el-form-item label="银行卡绑定手机号:" prop="mobile">
        <el-input v-model="dataForm.mobile" :disabled="disabled" ></el-input>
      </el-form-item>
      <el-form-item label="银行卡类型:" prop="type" >
        <el-select v-model="dataForm.type" :disabled="disabled" placeholder=" ">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="银行名称" prop="bankName">-->
<!--        <el-input v-model="dataForm.bankName" :disabled="disabled" placeholder="银行名称"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="银行卡状态" prop="cardStatus">-->
<!--        <el-radio-group v-model="dataForm.cardStatus" :disabled="disabled">-->
<!--          <el-radio :label="1">已绑定</el-radio>-->
<!--          <el-radio :label="2">已解绑</el-radio>-->
<!--        </el-radio-group>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="绑定时间" prop="boundAt">-->
<!--        <el-input v-model="dataForm.boundAt" :disabled="disabled" placeholder="绑定时间"></el-input>-->
<!--      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
<!--      <el-button @click="visible = false">取消</el-button>-->
      <el-button v-if="!disabled && isAdd" type="primary" @click="dataFormSubmit()" >确认添加</el-button>
       <el-button v-if="!disabled && isUpdate" type="primary" @click="dataFormSubmit()" >确认修改</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        disabled: false,
        visible: false,
        isAdd: false,
        isUpdate: false,
        options: [
          {
            value: 0,
            label: '储蓄卡'
          },
          {
            value: 1,
            label: '信用卡'
          },
          {
            value: 2,
            label: '准贷记卡'
          },
          {
            value: 3,
            label: '预付费卡'
          }
        ],
        dataForm: {
          userId: '',
          type: '',
          bankName: '',
          bankNo: '',
          userName: '',
          mobile: ''
        },
        dataRule: {
          bankNo: [
            {required: true, message: '银行卡号不能为空', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '银行卡归属人不能为空', trigger: 'blur'}
          ],
          mobile: [
            {required: true, message: '银行卡手机号不能为空', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '银行卡类型不能为空', trigger: 'blur'}
          ],
          bankName: [
            {required: true, message: '绑定银行不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      // 修改查看的初始化
      init (userId, id, disabled, isAdd, isUpdate) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.dataForm.userId = userId
        this.visible = true
        this.isAdd = isAdd
        this.isUpdate = isUpdate
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
      background-color: transparent;
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
        .el-form{
          .el-form-item{
            .processing{
              background-color: red;
              color: #ffffff;
              padding: 1px;
              width: fit-content;
              border-radius: 4px;
            }
            .processed{
              background-color: red;
              color: #ffffff;
              width: fit-content;
              padding: 1px;
              border-radius: 4px;
            }
            .unfinished{
              background-color: gray;
              color: #ffffff;
              padding: 1px;
              width: fit-content;
              border-radius: 4px;
            }
            .finished{
              background-color: red;
              color: #ffffff;
              width: fit-content;
              padding: 1px;
              border-radius: 4px;
            }
          }
        }
        .confirmDialog{
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          color: #ffffff;
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
</style>
