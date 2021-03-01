<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="商品名字" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="商品名字"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model="dataForm.price" :disabled="disabled" placeholder="价格"></el-input>
      </el-form-item>
      <el-form-item label="代理商" prop="agentId">
        <el-select v-model="dataForm.agentId" placeholder="请选择" :disabled="disabled">
          <el-option
            v-for="item in agentOptions"
            :key="item.userId"
            :label="item.realName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品图片" prop="imageUrlList">
        <el-row>
          <el-upload
            :disabled="disabled"
            class="upload-demo"
            :action="url"
            :on-remove="handleRemove"
            :before-remove="befRemove"
            :before-upload="beforeUploadHandle"
            :on-success="successHandle"
            :on-exceed="fileSizeOverFlow"
            :file-list="dataForm.imageUrlList"
            :limit=4
            list-type="picture-card">
            <el-button v-if="!disabled" size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-row>
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input v-model.number="dataForm.stock" :disabled="disabled" placeholder="库存"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" :disabled="disabled" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item label="上架状态" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio-button label="0">未上架</el-radio-button>
          <el-radio-button label="1">已上架</el-radio-button>
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
        url: '',
        agentOptions: '',
        disabled: false,
        visible: false,
        dataForm: {
          id: 0,
          agentId: '',
          name: '',
          price: '',
          createTime: '',
          imageUrlList: '',
          updateTime: '',
          remark: '',
          status: '',
          stock: ''
        },
        dataRule: {
          agentId: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '价格不能为空', trigger: 'blur'}
          ],
          imageUrlList: [
            {required: true, message: '商品图片列表不能为空', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '上架状态不能为空', trigger: 'blur'}
          ],
          stock: [
            {required: true, message: '库存不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.url = this.$http.BASE_URL + `/sys/oss/upload?token=${this.$cookie.get('token')}`
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.dataForm.imageUrlList = []
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/goods/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.goods
              }
            })
          }

          this.$http({
            url: '/sys/user/queryAgentList',
            method: 'get',
            params: {
              'whereNeedAgentList': 2
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.agentOptions = data.data
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
                url: `/ad/goods/${!this.dataForm.id ? 'save' : 'update'}`,
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
      handleRemove (file, fileList) {
        this.dataForm.imageUrlList = fileList
      },
      // 上传之前
      beforeUploadHandle (file) {
        if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
          this.$message.error('只支持jpg、png、gif格式的图片！')
          return false
        }
      },
      // 删除之前
      befRemove (file) {
        if (file.status === 'success') {
          return this.$confirm('是否确认删除', '提示')
        }
      },
      // 上传成功
      successHandle (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.imageUrlList.push({url: response.url})
          console.log(this.dataForm.imageUrlList)
        //   for (let i in this.dataForm.imageUrlList) {
        //     this.dataForm.qualificationsPictureUrl = this.dataForm.qualificationsPictureUrl.concat(this.dataForm.qualificationList[i].url.toString()).concat(',')
        //   }
        //   console.log(this.dataForm.qualificationsPictureUrl)
        // } else {
        //   this.$message.error(response.msg)
        // }
        }
      },
      fileSizeOverFlow () {
        this.$message({
          message: '文件上传数量超过限制，最多为4个！',
          type: 'warning',
          duration: 1500
        })
      }
    }
  }
</script>
