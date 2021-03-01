<template>
  <el-dialog
    :visible.sync="visible"
    class="dialog">
    <!--    删除时的弹窗内容-->
      <div>
        <div>
          <span>提现</span><span style="font-size: 14px">（实际可得）</span>
        </div>
        <div style="margin: 10px 0;">
        ￥{{realBalance}}
        </div>
      </div>
    <div class="line"></div>
     <div class="poundage">
       <div style="float: left">  手续费：</div>
       <div style="float: right">￥{{poundage}}</div>
     </div>
    <span slot="footer" class="dialog-footer">

      <div class="button" @click="confirm">
        确认
      </div>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        poundage: '',
        realBalance: '',
        Form: {
          amount: '',
          userBankId: ''
        },
        dataForm: {
          id: 0,
          userBankId: '',
          amount: '',
          status: '',
          createTime: '',
          finishTime: '',
          handlerId: ''
        }
      }
    },
    methods: {
      init (form) {
        let that = this
        this.Form.userBankId = form.userBankId
        this.dataForm.amount = form.amount
        this.visible = true
        this.$http({
          url: '/ad/withdrawal/commission',
          method: 'post',
          data: this.dataForm
        }).then(({data}) => {
          that.poundage = data.commission
          that.realBalance = data.sum
          that.Form.amount = data.sum
        })
      },
      confirm () {
        let that = this
        this.$http({
          url: '/ad/withdrawal/save',
          method: 'POST',
          data: this.Form
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '提现发起成功！',
              type: 'success',
              duration: '1500',
              onClose: () => {
                that.visible = false
                this.$router.push({path: 'mall-accountbalance'})
              }
            })
          }
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
 .dialog {
   /deep/ .el-dialog {
     border-radius: 3px;
     width: 80%;
     .el-dialog__header{
        padding: 0;
       margin: 0;
     }
     .el-dialog__body {
       display: flex;
       flex-direction: column;
       justify-content: center;
       padding: 5vh 15px 10px;
       text-align: center;
       font-size: 18px;
       color: #333333;
       .line{
         background-color: #ccc;
         height: 2px;
         width: 100%;
         margin: 0 auto 5px;
       }
       .poundage{
         font-size: 14px;
         margin: 5px 0;
       }
     }
     .el-dialog__footer {
       background-color: #082F40;
       padding: 0;
       margin: 0;
       color: #ffffff;
       font-size: 18px;
       .button {
         width: 100%;
         display: flex;
         justify-content: center;
         padding: 10px 0;
         border-bottom-left-radius: 3px;
         border-bottom-right-radius: 3px;
       }
     }
   }
 }
</style>
