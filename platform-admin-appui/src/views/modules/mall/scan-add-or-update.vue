<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="广告图片" prop="imageUrl">
        <el-img v-model="dataForm.imageUrl" :disabled="disabled"></el-img>
      </el-form-item>
      <el-form-item label="展示时间" prop="duration">
        <el-input v-model="dataForm.duration" :disabled="disabled" placeholder="广告持续时间(按秒计算)"></el-input>
      </el-form-item>
      <el-form-item label="启用状态" prop="avail">
        <el-radio-group v-model="dataForm.avail" :disabled="disabled">
          <el-radio-button label="0">未启用</el-radio-button>
          <el-radio-button label="1">已启用</el-radio-button>
        </el-radio-group>
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
          imageUrl: '',
          duration: '',
          createTime: '',
          displayImg: true,
          avail: ''},
        dataRule: {
          imageUrl: [
            {required: true, message: '图片不能为空', trigger: 'blur'}
          ],
          duration: [
            {required: true, message: '广告持续时间不能为空', trigger: 'blur'}
          ],
          avail: [
            {required: true, message: '启用状态不能为空', trigger: 'blur'}
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
              url: `/ad/scan/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.scan
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
                url: `/ad/scan/${!this.dataForm.id ? 'save' : 'update'}`,
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
