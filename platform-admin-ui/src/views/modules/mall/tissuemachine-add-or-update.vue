<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="代理商" prop="agentId" v-if="isAuth('ad:tissuemachine:save')">
        <el-select v-model="dataForm.agentId" placeholder="请选择" :disabled="disabled">
          <el-option
            v-for="item in agentOptions"
            :key="item.userId"
            :label="item.realName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="终端唯一码" prop="sn">
        <el-input v-model="dataForm.sn" :disabled="disabled" placeholder="终端唯一码"></el-input>
      </el-form-item>
      <el-form-item label="机柜名称" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="机柜名称"></el-input>
      </el-form-item>
      <el-form-item label="设备定位" prop="status">
        <el-radio-group v-model="dataForm.isOpenLocate"  :disabled="disabled">
          <el-radio-button label="1">开启</el-radio-button>
          <el-radio-button label="0">关闭</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="机柜状态" prop="status">
        <el-radio-group v-model="dataForm.status"  :disabled="disabled">
          <el-radio-button label="1">设备正常</el-radio-button>
          <el-radio-button label="2">设备维修中</el-radio-button>
          <el-radio-button label="3" v-if="dataForm.id">出纸异常</el-radio-button>
          <el-radio-button label="4" v-if="dataForm.id">位置异常</el-radio-button>
          <el-radio-button label="5" v-if="dataForm.id">纸巾不足</el-radio-button>
          <el-radio-button label="6" v-if="dataForm.id">电量不足</el-radio-button>
          <el-radio-button label="7" v-if="dataForm.id">语音异常</el-radio-button>
          <el-radio-button label="8" v-if="dataForm.id">设备离线</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="地图显示经纬度" prop="latitude" :disabled="disabled">
        <span v-if="dataForm.latitude !== ''">{{dataForm.longitude}},{{dataForm.latitude}}</span>
        <iframe :disabled="disabled" id="mapPage" width="100%" height="500px" frameborder=0
                :src="mapUrl">
        </iframe>
      </el-form-item>

      <el-form-item label="机器图片" prop="listPicUrl">
        <el-img v-model="dataForm.machineImage" :disabled="disabled">
        </el-img>
      </el-form-item>
      <el-form-item label="机器Logo图片" prop="listPicUrl">
        <el-upload
          v-if="!disabled"
          style="display: inline-block; margin-left: 3px; float: left"
          :action="url"
          :on-success="handleSuccess"
          :on-exceed="handleExceed"
          :file-list="fileList"
          :limit=1
          list-type="picture-card">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="是否显示Logo" prop="isShowLogo">
        <el-radio-group v-model="dataForm.isShowLogo" :disabled="disabled">
          <el-radio-button label="0">不显示</el-radio-button>
          <el-radio-button label="1">显示</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="dataForm.address" :disabled="disabled" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="工作时间" prop="workTime">
        <el-input v-model="dataForm.workTime" :disabled="disabled" placeholder="工作时间 如：周一至周五 9:00-22:00"></el-input>
      </el-form-item>
      <el-form-item label="纸巾库存" prop="tissueNumber">
        <el-input v-model="dataForm.tissueNumber" :disabled="disabled" placeholder="剩余纸巾数量"></el-input>
      </el-form-item>
      <el-form-item label="设备版本号" prop="machineVersion">
        <el-input v-model="dataForm.machineVersion" :disabled="disabled" placeholder="设备版本信息"></el-input>
      </el-form-item>
      <el-form-item label="剩余流量" prop="machineVersion">
        <el-input v-model="dataForm.simCcid" :disabled="disabled" placeholder="设备版本信息"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" :disabled="disabled" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.reportLatitude !== '' && dataForm.reportLatitude !== null" label="机柜上报经纬度" prop="reportLatitude" >
                  <a  :href="linkUrl" target="_blank">地图查询</a>
      </el-form-item>
      <el-form-item label="是否启用" prop="isDelete">
        <el-radio-group v-model="dataForm.isDelete" :disabled="disabled">
          <el-radio-button label="0">已启用</el-radio-button>
          <el-radio-button label="1">未启用</el-radio-button>
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
        displayLogo: true,
        fileList: [],
        agentOptions: [],
        linkUrl: '',
        mapUrl: 'https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=KOZBZ-2YRE6-KRBS2-ERK6Z-623D6-2TB5F&referer=纸巾机-后台管理端',
        dataForm: {
          agentId: '',
          id: 0,
          sn: '',
          machineVersion: '',
          name: '',
          isOpenLocate: '',
          simCcid: '',
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
          reportLatitude: '',
          reportLongitude: '',
          isDelete: ''},
        dataRule: {
          agentId: [
            {required: true, message: '代理商不能为空', trigger: 'blur'}
          ],
          sn: [
            {required: true, message: '终端唯一码不能为空', trigger: 'blur'}
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
          ],
          isOpenLocate: [
            {required: true, message: '设备安装室内外位置不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      var that = this
      this.url = this.$http.BASE_URL + `/sys/oss/uploadCircularImg?token=${this.$cookie.get('token')}`
      window.addEventListener('message', function (event) {
        // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
        if (!that.disabled || !that.dataForm.id) {
          var loc = event.data
          if (loc && loc.module === 'locationPicker') { // 防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
            console.log('location', loc)
            that.dataForm.latitude = loc.latlng.lat
            that.dataForm.longitude = loc.latlng.lng
          }
        }
      }, false)
      console.log(that.dataForm.reportLatitude)
      console.log(that.dataForm.reportLongitude)
    },
    methods: {
      init (id, disabled) {
        this.url = this.$http.BASE_URL + `/sys/oss/uploadCircularImg?token=${this.$cookie.get('token')}`
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.fileList = []
        this.dataForm.reportLatitude = ''
        this.dataForm.reportLongitude = ''
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/tissuemachine/getById/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.tissuemachine
                this.mapUrl = 'https://apis.map.qq.com/tools/locpicker?search=1&type=1&coord=' + data.tissuemachine.latitude + ',' + data.tissuemachine.longitude + '&key=KOZBZ-2YRE6-KRBS2-ERK6Z-623D6-2TB5F&referer=纸巾机-后台管理端'
                // this.linkUrl = 'https://apis.map.qq.com/tools/poimarker?type=1&keyword=' + '' +  '&center=' + data.tissuemachine.reportLatitude + ',' + data.tissuemachine.reportLongitude + '&radius=1000&key=KOZBZ-2YRE6-KRBS2-ERK6Z-623D6-2TB5F&referer=纸巾机-后台管理端'
                this.linkUrl = 'https://apis.map.qq.com/tools/poimarker?type=0&marker=coord:' + data.tissuemachine.latitude + ',' + data.tissuemachine.longitude + ';title:' + data.tissuemachine.name + ';addr:' + data.tissuemachine.address + '&key=KOZBZ-2YRE6-KRBS2-ERK6Z-623D6-2TB5F&referer=纸巾机-后台管理端'
                console.log(this.linkUrl)
                console.log(this.mapUrl)
                this.fileList.push({
                  url: data.tissuemachine.machineLogo
                })
              }
            })
          }

          this.$http({
            url: '/sys/user/queryAgentList',
            method: 'get',
            params: {
              'whereNeedAgentList': 1
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.agentOptions = data.data
            }
            console.log(this.agentOptions)
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (valid) {
              this.$http({
                url: `/ad/tissuemachine/${!this.dataForm.id ? 'save' : 'update'}`,
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
