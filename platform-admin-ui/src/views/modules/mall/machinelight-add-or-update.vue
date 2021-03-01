<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="对应机柜" prop="machineId">
        <el-select v-model="dataForm.machineSn" filterable placeholder="请选择" :disabled="disabled">
          <el-option
            v-for="item in machineOptions"
            :key="item.sn"
            :label="item.name"
            :value="item.sn">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开灯时间" prop="startTime">
        <el-time-select
          placeholder="起始时间"
          v-model="dataForm.startTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59'
          }">
        </el-time-select>
      </el-form-item>
      <el-form-item label="关灯时间" prop="endTime">
        <el-time-select
          placeholder="起始时间"
          v-model="dataForm.endTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59',
            minTime: dataForm.startTime
          }">
        </el-time-select>
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
        machineOptions: [],
        dataForm: {
          id: 0,
          machineSn: '',
          startTime: '',
          endTime: ''},
        dataRule: {
          machineSn: [
            {required: true, message: '机柜不能为空', trigger: 'blur'}
          ],
          startTime: [
            {required: true, message: '开始时间不能为空', trigger: 'blur'}
          ],
          endTime: [
            {required: true, message: '结束时间不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true

        this.$http({
          url: '/ad/tissuemachine/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.machineOptions = data.list
          }
        })

        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.dataForm.machineSn = ''
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/machinelight/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.machinelight
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
                url: `/ad/machinelight/${!this.dataForm.id ? 'save' : 'update'}`,
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
