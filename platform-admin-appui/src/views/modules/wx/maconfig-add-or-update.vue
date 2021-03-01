<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="appId" prop="appId">
        <el-input v-model="dataForm.appId" :disabled="disabled" placeholder="微信小程序appId"></el-input>
      </el-form-item>
      <el-form-item label="secret" prop="secret">
        <el-input v-model="dataForm.secret" :disabled="disabled" placeholder="微信小程序secret"></el-input>
      </el-form-item>
      <el-form-item label="token" prop="token">
        <el-input v-model="dataForm.token" :disabled="disabled" placeholder="小程序token"></el-input>
      </el-form-item>
      <el-form-item label="AESKey" prop="aesKey">
        <el-input v-model="dataForm.aesKey" :disabled="disabled" placeholder="微信小程序EncodingAESKey"></el-input>
      </el-form-item>
      <el-form-item label="消息格式" prop="msgDataFormat">
        <el-input v-model="dataForm.msgDataFormat" :disabled="disabled" placeholder="消息格式，XML或者JSON"></el-input>
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
          appId: '',
          secret: '',
          token: '',
          aesKey: '',
          msgDataFormat: ''
        },
        dataRule: {
          name: [
            {
              required: true,
              message: '名称不能为空',
              trigger: 'blur'
            }
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
              url: `/wx/maconfig/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm = data.maconfig
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
                url: `/wx/maconfig/${!this.dataForm.id ? 'save' : 'update'}`,
                method: 'post',
                data: this.dataForm
              }).then(({ data }) => {
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
