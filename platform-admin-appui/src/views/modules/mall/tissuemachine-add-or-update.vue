<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改机柜信息' : '查看机柜信息'"
    :close-on-click-modal="false"
    class="thePageDialog"
    append-to-body
    :visible.sync="visible"
    v-loading.fullscreen.lock="fullscreenLoading"
    element-loading-text="重启设备中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      class="thePageForm"
      label-width="90px">
      <el-form-item label="终端唯一码：" prop="sn">
        <el-input v-model="dataForm.sn" :disabled="disabled" placeholder="终端唯一码"></el-input>
      </el-form-item>
      <el-form-item label="机柜ID：" prop="id">
        <el-input v-model="dataForm.id" :disabled="disabled" placeholder="机柜ID"></el-input>
      </el-form-item>
      <!--      <el-form-item label="协议版本号" prop="vsn">-->
      <!--        <el-input v-model="dataForm.vsn" :disabled="disabled" placeholder="协议版本号"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="机柜软件版本号" prop="softVersion">-->
      <!--        <el-input v-model="dataForm.softVersion" :disabled="disabled" placeholder="机柜软件版本号"></el-input>-->
      <!--      </el-form-item>-->
      <el-form-item label="设备名称：" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="机柜名称"></el-input>
      </el-form-item>
      <!--      <el-form-item label="设备状态" prop="status">-->
      <!--        <el-radio-group v-model="dataForm.status"  :disabled="disabled">-->
      <!--          <el-radio-button label="1">设备正常</el-radio-button>-->
      <!--          <el-radio-button label="2">设备维修中</el-radio-button>-->
      <!--        </el-radio-group>-->
      <!--      </el-form-item>-->
      <el-form-item label="设备状态：" prop="status">
        <el-tag type="success" v-if="dataForm.status === 1" class="inMaintenance">设备正常</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 2" class="machineNomal">设备维修中</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 3" class="machineNomal">出纸异常</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 4" class="machineNomal">位置异常</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 5" class="machineNomal">纸巾不足</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 6" class="machineNomal">电量不足</el-tag>
        <el-tag type="danger" v-else-if="dataForm.status === 7" class="machineNomal">语音修改异常</el-tag>
        <el-tag type="info" v-else-if="dataForm.status === 8" class="machineNomal">离线</el-tag>
      </el-form-item>
      <!--      <el-form-item label="经度" prop="longitude">-->
      <!--        <el-input v-model="dataForm.longitude" :disabled="disabled" placeholder="经度"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="纬度" prop="latitude">-->
      <!--        <el-input v-model="dataForm.latitude" :disabled="disabled" placeholder="纬度"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="机器图片" prop="listPicUrl">-->
      <!--        <el-img v-model="dataForm.machineImage" :disabled="disabled">-->
      <!--        </el-img>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="机器Logo图片" prop="listPicUrl">-->
      <!--        <el-upload-->
      <!--          v-if="!disabled"-->
      <!--          style="display: inline-block; margin-left: 3px; float: left"-->
      <!--          :action="url"-->
      <!--          :on-success="handleSuccess"-->
      <!--          :on-exceed="handleExceed"-->
      <!--          :file-list="fileList"-->
      <!--          :limit=1-->
      <!--          list-type="picture-card">-->
      <!--          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
      <!--        </el-upload>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="是否显示Logo" prop="isShowLogo">-->
      <!--        <el-radio-group v-model="dataForm.isShowLogo" :disabled="disabled">-->
      <!--          <el-radio-button label="0">不显示</el-radio-button>-->
      <!--          <el-radio-button label="1">显示</el-radio-button>-->
      <!--        </el-radio-group>-->
      <!--      </el-form-item>-->
      <el-form-item label="是否启用：" prop="isShowLogo" >
        <div>
          <el-tag v-if="dataForm.status === 2 || dataForm.isDelete === 1" class="stopUse">停用中</el-tag>
          <el-tag v-else class="keepUsing">启用中</el-tag>
          <el-button size="mini" @click="Restart(dataForm.id)" v-if="!disabled" class="restartMachine">重启设备</el-button>
        </div>
      </el-form-item>

      <el-form-item label="所在位置：" prop="address">
        <el-input v-model="dataForm.address" :disabled="disabled" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="工作时间：" prop="workTime">
        <el-input v-model="dataForm.workTime" :disabled="disabled" placeholder="工作时间 如：周一至周五 9:00-22:00"></el-input>
      </el-form-item>
      <el-form-item label="纸巾库存：" prop="tissueNumber">
        <el-input v-model="dataForm.tissueNumber" :disabled="disabled" placeholder="剩余纸巾数量"></el-input>
      </el-form-item>
      <el-form-item label="已使用流量：">
        <div style="font-size: 12px;">{{ dataForm.usedFlow }}&nbsp;MB</div>
      </el-form-item>
      <el-form-item label="设备版本号：">
        <div>
          {{ dataForm.machineVersion }}
        </div>
      </el-form-item>
      <el-form-item label="设备位置：">
        <el-tag v-if="dataForm.status === 3" class="machineLocateError">偏移</el-tag>
        <el-tag class="machineLocateNomal" v-else>正常</el-tag>
      </el-form-item>
      <!--      <el-form-item label="备注" prop="remark">-->
      <!--        <el-input v-model="dataForm.remark" :disabled="disabled" placeholder="备注"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="是否删除" prop="isDelete">-->
      <!--        <el-radio-group v-model="dataForm.isDelete" :disabled="disabled">-->
      <!--          <el-radio-button label="0">已启用</el-radio-button>-->
      <!--          <el-radio-button label="1">未启用</el-radio-button>-->
      <!--        </el-radio-group>-->
      <!--      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
<!--      <el-button @click="visible = false">取消</el-button>-->
      <el-button v-if="!disabled"  @click="dataFormSubmit()" class="confirmFeat">确认修改</el-button>
    </span>
    <!--    重启的遮罩层-->
<!--    <div class="coverAll" v-if="coverVisible" @click="coverVisible = !coverVisible">-->
<!--      <img id="restart" src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/ajax-loader3xZpq.png" alt="" class="restart">-->
<!--      <div style="color: whitesmoke">重启设备中</div>-->
<!--    </div>-->
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        isWrong: '0',
        getStatus: 0,
        getMachineId: null,
        errorText: false,
        fullscreenLoading: false,
        timer: null,
        disabled: false,
        visible: false,
        displayImg: true,
        displayLogo: true,
        finishedRestart: false,
        fileList: [],
        dataForm: {
          id: 0,
          sn: '',
          ip: '',
          vsn: '',
          softVersion: '',
          name: '',
          status: '',
          longitude: '',
          latitude: '',
          machineImage: '',
          machineLogo: '',
          isShowLogo: '',
          address: '',
          workTime: '',
          tissueNumber: '',
          createTime: '',
          updateTime: '',
          remark: '',
          filePostUrl: '',
          usedFlow: 0,
          isDelete: ''},
        dataRule: {
          machineLocation: [
            {required: true, message: '设备位置', trigger: 'blur'}
          ],
          remainingTraffic: [
            {required: true, message: '剩余流量', trigger: 'blur'}
          ],
          sn: [
            {required: true, message: '终端唯一码不能为空', trigger: 'blur'}
          ],
          id: [
            {required: true, message: 'ID不能为空', trigger: 'blur'}
          ],
          isShowLogo: [
            {required: true, message: '状态不为空', trigger: 'blur'}
          ],
          vsn: [
            {required: true, message: '协议版本号不能为空', trigger: 'blur'}
          ],
          softVersion: [
            {required: true, message: '软件版本号不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '机柜名称不能为空', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '机柜状态不能为空', trigger: 'blur'}
          ],
          longitude: [
            {required: true, message: '经度不能为空', trigger: 'blur'}
          ],
          latitude: [
            {required: true, message: '纬度不能为空', trigger: 'blur'}
          ],
          machineImage: [
            {required: true, message: '机柜图片不能为空', trigger: 'blur'}
          ],
          address: [
            {required: true, message: '机柜地址不能为空', trigger: 'blur'}
          ],
          workTime: [
            {required: true, message: '机柜工作时间不能为空', trigger: 'blur'}
          ],
          tissueNumber: [
            {required: true, message: '剩余纸巾数量不能为空', trigger: 'blur'}
          ],
          isDelete: [
            {required: true, message: '机柜启用状态不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      this.url = this.$http.BASE_URL + `/sys/oss/uploadCircularImg?token=${this.$cookie.get('token')}`
    },
    watch: {
      getStatus: {
        deep: true,
        handler (val) {
          if (val === 3) {
            this.fullscreenLoading = false
            this.finishedRestart = false
            clearInterval(this.timer)
          }
        }
      },
      errorText: {
        deep: true,
        handler (val) {
          if (val) {
            this.fullscreenLoading = false
            this.finishedRestart = false
            clearInterval(this.timer)
          }
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.url = this.$http.BASE_URL + `/sys/oss/uploadCircularImg?token=${this.$cookie.get('token')}`
        this.disabled = disabled
        this.dataForm.id = id
        this.visible = true
        this.fileList = []
        let that = this
        this.$nextTick(() => {
          // that.$refs['dataForm'].resetFields()
          if (that.dataForm.id) {
            that.$http({
              url: `/ad/tissuemachine/getById/${that.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                console.log('next,=====dataForm', data.tissuemachine)
                that.dataForm = data.tissuemachine
                that.fileList.push({
                  url: data.tissuemachine.machineLogo
                })
              }
            })
          }
        })
      },
      // 重启
      Restart (myid) {
        this.fullscreenLoading = true
        this.getStatus = null
        let that = this
        this.$http({
          url: `/ad/tissuemachine/restart`,
          method: 'get',
          params: {
            id: myid
          }
        }).then(({data}) => {
          console.log('重启', data)
          that.getMachineId = data.data.id
          that.timer = setInterval(function () {
            that.checkRestart(that.getMachineId)
          }, 2000)
        }).catch(error => {
          console.log('1', error)
        })
      },
      //
      async checkRestart (id) {
        this.$http({
          url: `/ad/machineoperate/info/${id}`,
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            if (data.machineoperate && data.machineoperate.status) {
              this.getStatus = data.machineoperate.status
            } else {
              this.errorText = true
              this.$message.error('请刷新尝试')
            }
          }
        }).catch(error => {
          console.log('2', error)
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (valid) {
              this.$http({
                url: `/ad/tissuemachine/update`,
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
      handleSuccess (res, file, fileList) {
        this.fileList = fileList
        if (!(res.code === 500)) {
          console.log(res)
          this.dataForm.machineLogo = res.url
          this.displayImg = false
        } else {
          this.$message({
            message: '上传文件不能为空！',
            type: 'error',
            duration: 1500
          })
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
<style lang="scss" scoped>
.thePageDialog /deep/ {
  .el-dialog {
    background-color: transparent;
    margin: 10vh auto !important;
    .el-dialog__header{
      display: flex;
      justify-content: center;
      .el-dialog__title{
        font-size: 16px;
        color: #ffffff;
      }
    }
    .el-dialog__body{
      background-color: #fff;
      height: calc(80vh - 110px);
      padding: 15px;
      overflow: auto;
      .thePageForm {
        position: relative;
        .el-form-item {
          margin: 0 0 15px;
          .el-form-item__label {
            font-size: 12px;
            padding: 0 8px 0 0;
            text-align: left;
            line-height: 30px;
          }
          .el-form-item__content {
            .el-form-item__error{
              padding-top: 2px;
            }
            line-height: 30px;
            .el-input {
              font-size: 12px;
              .el-input__inner {
                color: #333;
                background-color: #fff;
                border-radius: 2px;
                height: 30px;
                line-height: 30px;
              }
            }
            .el-input.is-disabled{
              font-size: 12px;
              .el-input__inner {
                color: #333;
                background-color: #F5F5F5;
                border-radius: 2px;
                height: 30px;
                line-height: 30px;
              }
            }
            /*机柜状态:设备维修中*/
            .inMaintenance {
              background-color: #FF3030;
              color: #ffffff;
              height: 30px;
              width: 48%;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*机柜状态：设备正常*/
            .machineNomal{
              height: 30px;
              width: 48%;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*是否启用：停用中*/
            .stopUse{
              background-color: #1FBF84;
              color: #ffffff;
              height: 30px;
              width: 48%;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*是否启用：使用中*/
            .keepUsing{
              background-color: #1FBF84;
              color: #fff;
              height: 30px;
              width: 48%;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*重启设备按钮*/
            .restartMachine{
              background-color: #0A84FF;
              color: #fff;
              width: 48%;
              height: 30px;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*设备位置：偏移*/
            .machineLocateError{
              background-color: #FF9700;
              color: #fff;
              width: 48%;
              height: 30px;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*设备位置：正常*/
            .machineLocateNomal{
              background-color: #1FBF84;
              color: #fff;
              width: 48%;
              height: 30px;
              line-height: 30px;
              font-size: 12px;
              padding: 0;
              border-radius: 2px;
              vertical-align: middle;
              text-align: center;
            }
            /*查看时的文本框*/
            .el-textarea.is-disabled .el-textarea__inner {
              background-color: #fff;
            }

          }
        }
      }

      .coverAll{
        width: 100vw;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        background-color: #000;
        opacity: 0.5;
        z-index: 50;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        .restart{
          width: 40px;
          height: 40px;
          object-fit: fill;
          margin-bottom: 15px;
        }
      }
    }
    .el-dialog__footer {
      background-color: #ffffff;
      .dialog-footer {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        display: flex;
        justify-content: center;
        align-content: center;
        /*确认修改按钮*/
        .confirmFeat{
          margin: 0;
          background-color: #082F40;
          color: #fff;
          font-size: 12px;
          border-radius: 4px;
          padding: 10px;
          width: 70%;
        }
      }
    }
  }
}
  /*::v-deep .el-dialog{*/
  /*  .el-dialog__header{*/
  /*    .dialogTitle{*/
  /*      background-image: url("https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/dialogTitleBackGroundPic3xZpq.png");*/
  /*      width: 100%;*/
  /*      height: auto;*/
  /*      font-size: 16px;*/
  /*    }*/
  /*  }*/
  /*}*/

</style>
