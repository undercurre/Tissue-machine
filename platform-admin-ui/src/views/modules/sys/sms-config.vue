<template>
  <el-dialog
    title="短信配置"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="短信类型" size="mini" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">腾讯云SMS</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="appid" prop="appid">
        <el-input v-model="dataForm.appid" placeholder="appid"></el-input>
      </el-form-item>
      <el-form-item label="appKey" prop="appkey">
        <el-input v-model="dataForm.appkey" placeholder="appkey"></el-input>
      </el-form-item>
      <el-form-item label="签名" prop="sign">
        <el-input v-model="dataForm.sign" placeholder="【公司简称】"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          type: 1,
          appid: '',
          appkey: '',
          sign: ''
        },
        dataRule: {
          appid: [
            {
              required: true,
              message: 'appid不能为空',
              trigger: 'blur'
            }
          ],
          appkey: [
            {
              required: true,
              message: 'appkey不能为空',
              trigger: 'blur'
            }
          ]
        }
      }
    },
    methods: {
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$http({
            url: `/sys/smslog/config`,
            method: 'get'
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm = data.config
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
                url: `/sys/smslog/saveConfig`,
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
                }
              })
            }
          })
      }
    }
  }
</script>
