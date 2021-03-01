<template>
  <div class="upload-image">
    <el-upload class="upload-demo"
               ref="upload"
               action="string"
               :on-remove="handleRemove"
               :limit="3"
               :on-exceed="handleExceed"
               :disabled="isDisabled"
               list-type="picture"
               :accept="'image/*'"
               :before-upload="onBeforeUploadImage"
               :http-request="UploadImage"
               :on-change="fileChange"
               :file-list="fileList">
      <el-button size="small"
                 :loading="!isPicSuccess"
                 type="primary"
                 :disabled="isDisabled">
        <span v-if="isPicSuccess">上传附件</span>
        <span v-else>图片上传中</span>
      </el-button>
      <div slot="tip"
           class="el-upload__tip">只能上传jpg/jpeg/png文件，且不超过30m</div>
    </el-upload>
    <el-progress :percentage="process"
                 v-show="process"
                 color="#409eff"></el-progress>
  </div>
</template>

<script>
import axios from 'axios'
import { dataUrlToBlob, blobToFile, compress } from '../utils/common'
export default {
  data () {
    return {
      fileList: [], // 上传的文件列表
      file: null,
      fileIds: [], // 文件id
      process: 0, // 进度
      isPicSuccess: true
    }
  },
  props: {
    isDisabled: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    onBeforeUploadImage (file) {
      this.isPicSuccess = false
      this.$emit('changeIsPicSuccess', false)
      const isIMAGE = file.type === 'image/jpeg' || 'image/jpg' || 'image/png'
      const isLt30M = file.size / 1024 / 1024 < 30
      if (!isIMAGE) {
        this.$message.error('上传文件只能是图片格式!')
      }
      if (!isLt30M) {
        this.$message.error('上传文件大小不能超过 30MB!')
      }
      return isIMAGE && isLt30M
    },
    UploadImage () {
      let baseURL, base64Data, blobData, file
      let formData = new FormData()
      let image = new Image()
      let name = this.file.name
      image.src = URL.createObjectURL(this.file)
      // 图片加载完之后才做后面的事情
      image.onload = function () {
        base64Data = compress(image) // 压缩并获取到base64数据
        blobData = dataUrlToBlob(base64Data)
        file = blobToFile(blobData, name)
      }
      let timer
      timer = setInterval(() => {
        if (image.complete) {
          clearInterval(timer)
          if (process.env.NODE_ENV === 'development') {
            baseURL = '/api'
          } else if (process.env.NODE_ENV === 'production') {
            baseURL = 'http://shuichang.xinglian.info/waterOperation'
          }
          formData.append('file', file)
          let config = {
            headers: {
              token: sessionStorage.getItem('wxtoken')
            },
            onUploadProgress: (progressEvent) => {
              // 使用本地 progress 事件
              if (progressEvent.lengthComputable) {
                let num = Math.floor((progressEvent.loaded / progressEvent.total) * 100)
                this.process = num // 使用某种 UI 进度条组件会用到的百分比
              }
            }
          }
          axios.post(baseURL + '/app/apptbfileinfo/save?uploadFileType=1', formData, config).then(res => {
            if (res.data.code === 0 && res.data.msg === 'success') {
              this.fileIds.push(res.data.data.id)
              this.$message.success('图片' + res.data.data.originalFilename + '上传完毕')
              this.process = 0
              this.$emit('getFileIds', this.fileIds)
            } else {
              this.$message.error(res.data.msg)
            }
            this.isPicSuccess = true
            this.$emit('changeIsPicSuccess', true)
          })
        }
      }, 100)
    },
    fileChange (file) {
      this.file = file.raw // 取出上传文件的对象，在其它地方也可以使用
      this.fileList.push(file) // 重新手动赋值filstList， 免得自定义上传成功了, 而fileList并没有动态改变， 这样每次都是上传一个对象
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    handleRemove (file) {
      for (let i = 0; i < this.fileList.length; i++) {
        if (this.fileList[i].name == file.name) {
          this.fileList.splice(i, 1)
          this.fileIds.splice(i, 1)
        }
      }
    },
    clearFile () {
      this.fileList.splice(0, 3)
      this.fileIds = []
    }
  },
}
</script>

<style scoped lang="scss">
</style>
