<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="贴片位置类型" prop="type">
<!--        <el-input v-model="dataForm.type" :disabled="disabled" placeholder="贴片位置类型 1 机柜贴片 2下单贴片 3个人中心贴片 4 出货成功贴片"></el-input>-->
        <el-radio-group v-model="dataForm.type" :disabled="disabled">
          <el-radio-button label="1">机柜贴片</el-radio-button>
<!--          <el-radio-button label="2">下单贴片</el-radio-button>-->
          <el-radio-button label="3">个人中心贴片</el-radio-button>
          <el-radio-button label="4">出货成功贴片</el-radio-button>
          <el-radio-button label="5">首页轮播图贴片</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="图片" prop="imageUrl">
        <el-image v-if="dataForm.id && displayImg"
                  style="width: 147px; height: 147px; display: inline-block; float: left;"
                  :src="dataForm.imageUrl"></el-image>
        <el-upload
          v-if="!disabled"
          style="display: inline-block; margin-left: 3px; float: left"
          :action="filePostUrl"
          :on-success="handleSuccess"
          :on-exceed="handleExceed"
          :file-list="fileList"
          :limit=1
          list-type="picture-card">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" v-if="disabled">
        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="是否启用" prop="avail">
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
        displayImg: true,
        fileList: [],
        dataForm: {
          id: 0,
          type: '',
          imageUrl: '',
          createTime: '',
          avail: ''
        },
        dataRule: {
          type: [
            {required: true, message: '贴片类型不能为空', trigger: 'blur'}
          ],
          imageUrl: [
            {required: true, message: '图片不能为空', trigger: 'blur'}
          ],
          avail: [
            {required: true, message: '可用状态不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      this.filePostUrl = this.$http.BASE_URL + `/sys/oss/upload?token=${this.$cookie.get('token')}`
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.fileList = []
        this.displayImg = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/patch/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.patch
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
                url: `/ad/patch/${!this.dataForm.id ? 'save' : 'update'}`,
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
      },
      handleSuccess (res) {
        if (!(res.code === 500)) {
          this.dataForm.imageUrl = res.url
          this.displayImg = false
        } else {
          this.$message({
            message: '上传文件不能为空！',
            type: 'error',
            duration: 1500
          })
          this.fileList = []
        }
      },
      handleExceed () {
        this.$message({
          message: '上传数量超过上限,请先删除前面上传的图片！',
          type: 'error',
          duration: 1500
        })
      }
    }
  }
</script>
