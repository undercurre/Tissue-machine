<template>
    <div style="width: 100%;height: 100%">
     <el-dialog
     title="警告"
     :close-on-click-modal='canClose'
     :show-close='isShowClose'
     :visible='isShow'
     custom-class="dialog"
     >
     <span style="font-size: 12px;">您没有查看的权限！</span>
     </el-dialog>
    </div>
</template>

<script>
export default {
    data () {
        return {
            canClose: false,
            isShowClose: false,
            isShow: true
        }
    },
}
</script>

<style lang="scss" scoped>
/deep/ .el-dialog {
   width: 85%;
   .el-dialog_header{
       .el-dialog__title{
           font-size: 14px;
       }
   }
   .el-dialog__body{
       padding: 14px 25px;
   }
}
</style>