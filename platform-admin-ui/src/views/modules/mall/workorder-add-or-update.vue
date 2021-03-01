<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '指派' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="指派人员" prop="workerId">
<!--          <el-input v-model="dataForm.workerId" :disabled="disabled" placeholder="指派人员" v-if="disabled"></el-input>-->
          <el-select v-model="dataForm.workerId" filterable clearable placeholder="请选择" :disabled="disabled">
            <el-option
              v-for="item in workerOptions"
              :key="item.userId"
              :label="item.realName"
              :value="item.userId">
            </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="工单类型" prop="workType">
<!--        <el-input v-model="dataForm.workType" :disabled="disabled" placeholder="工单类型 0：采购工单 1：设备维护工单 2：送货工单 3：贴印刷广告工单"></el-input>-->
<!--        <el-radio-group v-model="dataForm.workType" :disabled="disabled">-->
<!--          <el-radio-button label="0">广告采购</el-radio-button>-->
<!--          <el-radio-button label="1">设备维护</el-radio-button>-->
<!--          <el-radio-button label="2">送货</el-radio-button>-->
<!--          <el-radio-button label="3">贴印刷广告</el-radio-button>-->
<!--        </el-radio-group>-->
        <el-select v-model="dataForm.workType" filterable clearable placeholder="请选择" :disabled="disabled">
          <el-option
            v-for="item in workTypeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="工单状态" prop="status" v-if="dataForm.id">-->
<!--&lt;!&ndash;        <el-input v-model="dataForm.status" :disabled="disabled" placeholder="工单状态 0：未响应 1：待处理 2：已处理"></el-input>&ndash;&gt;-->
<!--        <el-radio-group v-model="dataForm.status" :disabled="disabled">-->
<!--          <el-radio-button label="0">待开始</el-radio-button>-->
<!--          <el-radio-button label="1">进行中</el-radio-button>-->
<!--          <el-radio-button label="2">已处理</el-radio-button>-->
<!--          <el-radio-button label="3">已作废</el-radio-button>-->
<!--          <el-radio-button label="4">被移除</el-radio-button>-->
<!--&lt;!&ndash;          <el-radio-button label="3">已完成</el-radio-button>&ndash;&gt;-->
<!--        </el-radio-group>-->
<!--      </el-form-item>-->
      <el-form-item label="对应机柜" prop="machineId">
<!--        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="工单绑定的机柜"></el-input>-->
<!--          <el-input v-model="dataForm.machineId" :disabled="disabled" placeholder="对应机柜" v-if="disabled"></el-input>-->
          <el-select v-model="dataForm.machineId" filterable clearable placeholder="请选择" :disabled="disabled">
            <el-option
              v-for="item in machineOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="工单描述" prop="des" v-if="!dataForm.parentId">
        <el-input v-model="dataForm.des" :disabled="disabled" placeholder="工单描述"></el-input>
      </el-form-item>
      <el-form-item label="工单备注" prop="remark" v-if="dataForm.parentId">
        <el-input v-model="dataForm.remark" :disabled="disabled" placeholder="工单备注"></el-input>
      </el-form-item>
      <el-form-item label="截止时间" prop="">
        <el-date-picker
          :disabled="disabled"
          v-model="timeRange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="截止日期">
        </el-date-picker>
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
        workerOptions: [],
        machineOptions: [],
        workTypeOptions: [],
        timeRange: ['', ''],
        dataForm: {
          id: 0,
          workerId: '',
          workerName: '',
          workType: '',
          status: '',
          createTime: '',
          machineId: '',
          machineName: '',
          acceptTime: '',
          finishTime: '',
          des: '',
          parentId: '',
          remark: '',
          startTime: '',
          deadlineTime: ''
        },
        dataRule: {
          workerId: [
            {required: true, message: '指派人员不能为空', trigger: 'blur'}
          ],
          workType: [
            {required: true, message: '工单类型不能为空', trigger: 'blur'}
          ],
          // machineId: [
          //   {required: true, message: '机柜不能为空', trigger: 'blur'}
          // ],
          des: [
            {required: true, message: '描述不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init: function (id, disabled, parentId) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.dataForm.parentId = parentId
        this.visible = true
        this.workerOptions = []
        this.machineOptions = []
        this.workTypeOptions = []

        // if (!disabled) {
        this.$http({
          url: '/sys/user/workerList',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.workerOptions = data.data
          }
        })

        this.$http({
          url: '/ad/tissuemachine/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.machineOptions = data.list
          }
        })

        this.$http({
          url: '/ad/worktype/queryAll',
          method: 'get'
        }).then(({data}) => {
          this.workTypeOptions = data.list
        })
        // }

        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.timeRange = ['', '']
          if (this.dataForm.id && this.disabled) {
            this.$http({
              url: `/ad/workorder/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.workorder
                this.timeRange = ['', '']
                this.timeRange[0] = new Date(data.workorder.startTime)
                this.timeRange[1] = new Date(data.workorder.deadlineTime)
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
              this.dataForm.startTime = this.timeRange[0]
              this.dataForm.deadlineTime = this.timeRange[1]
              if (this.dataForm.startTime === null || this.dataForm.startTime === '' || this.dataForm.deadlineTime === null || this.dataForm.deadlineTime === '') {
                this.$message({
                  message: '截止时间不能为空！',
                  type: 'warning',
                  duration: 1500
                })
                return
              }
              this.$http({
                url: `/ad/workorder/${!this.dataForm.id ? 'save' : 'finishWork'}`,
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
