<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '追加' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="语音内容" prop="content">
        <el-input v-model="dataForm.content" :disabled="dataForm.id? true : false" placeholder="语音内容"></el-input>
      </el-form-item>
      <el-form-item label="机柜列表" prop="machineIds">
        <el-select v-model="dataForm.machineIds" :disabled="disabled" multiple placeholder="请选择" width="100%">
          <el-option
            v-for="item in machineOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item   label="有效时间" prop="validTime" v-if="!dataForm.id">
        <el-date-picker type="datetime" v-model="dataForm.validTime" :disabled="disabled"
                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="有效期开始时间"></el-date-picker>
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
        machineOptions: '',
        dataForm: {
          id: 0,
          content: '',
          machineIds: '',
          validTime: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
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
              url: `/ad/machinevoice/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.machinevoice
              }
            })
          }

          this.$http({
            url: '/ad/tissuemachine/queryNoVoiceList',
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.machineOptions = data.list
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (valid) {
              this.$http({
                url: `/ad/machinevoice/${!this.dataForm.id ? 'save' : 'update'}`,
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
