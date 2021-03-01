<template>
  <el-dialog
    :show-close="false"
    :visible.sync="visible"
    class="dialog">
    <!--    删除时的弹窗内容-->
      <div class="deleteMassage">
        是否确认删除此工单？
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
        id: '',
        admin: ''
      }
    },
    methods: {
      init (id, isAdmin) {
        this.id = id
        this.admin = isAdmin
        this.visible = true
      },
      confirm () {
        // 非管理员完结工单
        if (this.admin === 0) {
          this.$http({
            url: '/ad/workorder/softDelete',
            method: 'post',
            params: {
              id: this.id
            }
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
          })
        }
        if (this.admin === 1) {
          this.$http({
            url: '/ad/workorder/delete',
            method: 'post',
            data: [this.id]
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
          })
        }
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
       justify-content: center;
       padding: 10vh 15px;
       text-align: center;
         font-size: 18px;
         color: #333333;

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
