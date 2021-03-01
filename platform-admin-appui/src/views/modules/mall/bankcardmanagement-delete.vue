<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="dialog">
    <!--    删除时的弹窗内容-->
    <div class="confirmDialog" >
      <div class="">
        是否删除此银行卡？
      </div>

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
        isDel: false,
        disabled: false,
        visible: false,
        choiceOfFinished: '',
        machineStatus: '',
        isFinish: [
          {
            value: 1,
            label: '已处理'
          },
          {
            value: 0,
            label: '未处理'
          }
        ],
        dataForm: []
      }
    },
    methods: {
      init (id, disabled, isDel, parentId) {
        this.disabled = disabled
        this.dataForm.push(id)
        this.isDel = isDel
        this.visible = true
      },
      confirm () {
        this.$http({
          url: '/ad/userbank/delete',
          method: 'post',
          data: this.dataForm
        }).then(({data}) => {
          console.log('=====', data)
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
            this.visible = false
            this.$emit('refreshDataList')
          }
        }).catch(error => {
          console.log(error)
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
 .dialog {
   /deep/ .el-dialog {
     border-radius: 4px;
     .el-dialog__header{

     }
     .el-dialog__body {
       display: flex;
       justify-content: center;

     }
     .el-dialog__footer {
       background-color: blue;
       padding: 0;
       margin: 0;
       color: #ffffff;
       .button {
         width: 100%;
         display: flex;
         justify-content: center;
         border-bottom-left-radius: 4px;
         border-bottom-right-radius: 4px;
       }
     }
   }
 }
</style>
