<template>
  <el-dialog
    title="新增"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="thePageDialog">
    <!--    新增时才有表单出现-->
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="指派人员：" prop="workerId" >
        <el-select v-model="dataForm.workerId" filterable clearable placeholder="请选择" >
          <el-option
            v-for="item in workOptions"
            :key="item.userId"
            :label="item.realName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工单类型：" prop="workType" >
        <!--        <el-input v-model="dataForm.workType" :disabled="disabled" placeholder="工单类型 0：采购工单 1：设备维护工单 2：送货工单 3：贴印刷广告工单"></el-input>-->
<!--        <el-radio-group v-model="dataForm.workType" size="mini">-->
<!--          <el-radio :label="0" border>广告采购</el-radio>-->
<!--          <el-radio :label="1" border>设备维护</el-radio>-->
<!--          <el-radio :label="2" border>送货</el-radio>-->
<!--          <el-radio :label="3" border>贴印刷广告</el-radio>-->
<!--        </el-radio-group>-->
        <el-radio-group v-model="dataForm.workType" size="mini">
          <el-radio
            v-for="item in radioList"
            :key="item.id"
            :label="item.id"
            :value="item.id"
            border
          >{{item.name}}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="!dataForm.id" label="对应机柜：" >
        <!--        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="工单绑定的机柜"></el-input>-->
        <template>
          <el-select v-model="dataForm.machineId" filterable placeholder="请选择">
            <el-option
              v-for="item in machineOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <!--      <el-form-item label="工单状态" prop="status" v-if="disabled && dataForm.id">-->
      <!--&lt;!&ndash;        <el-input v-model="dataForm.status" :disabled="disabled" placeholder="工单状态 0：未响应 1：待处理 2：已处理"></el-input>&ndash;&gt;-->
      <!--&lt;!&ndash;        <el-radio-group v-model="dataForm.status" :disabled="disabled">&ndash;&gt;-->
      <!--&lt;!&ndash;          <el-radio-button label="0">未响应</el-radio-button>&ndash;&gt;-->
      <!--&lt;!&ndash;          <el-radio-button label="1">待处理</el-radio-button>&ndash;&gt;-->
      <!--&lt;!&ndash;          <el-radio-button label="2">已处理</el-radio-button>&ndash;&gt;-->
      <!--&lt;!&ndash;          <el-radio-button label="3">已完成</el-radio-button>&ndash;&gt;-->
      <!--&lt;!&ndash;        </el-radio-group>&ndash;&gt;-->
      <!--        <div v-if="dataForm.status == '1' || dataForm.status == '0'" class="processing">-->
      <!--          处理中-->
      <!--        </div>-->
      <!--        <div v-else class="processed">-->
      <!--          已处理-->
      <!--        </div>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="工单完结状态" prop="isEnd" v-if="disabled && dataForm.id">-->
      <!--        <div v-if="dataForm.isEnd == '0'" class="unfinished">-->
      <!--          未完结-->
      <!--        </div>-->
      <!--        <div v-else class="finished">-->
      <!--          已完结-->
      <!--        </div>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="对应机柜" prop="machineId">-->
      <!--        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="工单绑定的机柜"></el-input>-->
      <!--        <template>-->
      <!--          <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="对应机柜" v-if="disabled"></el-input>-->
      <!--          <el-select v-model="dataForm.machineId" filterable placeholder="请选择" v-if="!disabled">-->
      <!--            <el-option-->
      <!--              v-for="item in machineOptions"-->
      <!--              :key="item.value"-->
      <!--              :label="item.label"-->
      <!--              :value="item.value">-->
      <!--            </el-option>-->
      <!--          </el-select>-->
      <!--        </template>-->
      <!--      </el-form-item>-->
      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker
          v-model="dataForm.startTime"
          type="date"
          placeholder="开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="截止时间：" prop="deadlineTime">
        <el-date-picker
          v-model="dataForm.deadlineTime"
          type="date"
          placeholder="截至时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工单描述：" prop="des" >
        <el-input v-model="dataForm.des" type="textarea" rows="3"  placeholder="工单描述"></el-input>
      </el-form-item>
      <!--      <el-form-item label="工单备注" prop="remark" v-if="!dataForm.id || (dataForm.id && disabled)">-->
      <!--        <el-input v-model="dataForm.remark" type="textarea" rows="4" :disabled="disabled" placeholder="工单备注"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="工单创建时间" prop="createTime" v-if="disabled && dataForm.id">-->
      <!--        <div>{{ dataForm.createTime }}</div>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="接单时间" prop="acceptTime" v-if="disabled && dataForm.id">-->
      <!--        <div>{{ dataForm.acceptTime }}</div>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="工单完成时间" prop="finishTime" v-if="disabled && dataForm.id">-->
      <!--        <div>{{ dataForm.finishTime }}</div>-->
      <!--      </el-form-item>-->

    </el-form>
    <!--    确认时的弹窗内容-->
    <!--    <div class="confirmDialog" v-if="dataForm.id && !disabled">-->
    <!--      <div>-->
    <!--        是否确认完成此工单？-->
    <!--      </div>-->

    <!--    </div>-->
    <span slot="footer" class="dialog-footer" >
<!--      <el-button @click="visible = false">取消</el-button>-->
      <el-button v-if="!dataForm.id" type="primary" @click="dataFormSubmit()">确认</el-button>
      <!--      <el-button type="primary" v-if="dataForm.id && !disabled">确认</el-button>-->
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        workOptions: [],
        machineOptions: [],
        radioList: [],
        dataForm: {
          id: '',
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
          isEnd: '',
          startTime: '',
          deadlineTime: ''
        },
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          machineId: [
            {required: true, message: '对应机柜不能为空', trigger: 'blur'}
          ],
          workType: [
            {required: true, message: '工单类型不能为空', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '工单状态不能为空', trigger: 'blur'}
          ],
          des: [
            {required: true, message: '工单描述不能为空', trigger: 'blur'}
          ],
          remark: [
            {required: true, message: '工单备注不能为空', trigger: 'blur'}
          ],
          acceptTime: [
            {required: true, message: '接单时间不能为空', trigger: 'blur'}
          ],
          createTime: [
            {required: true, message: '工单创建时间不能为空', trigger: 'blur'}
          ],
          finishTime: [
            {required: true, message: '工单完成时间不能为空', trigger: 'blur'}
          ],
          workerId: [
            {required: true, message: '指派人员不能为空', trigger: 'blur'}
          ],
          startTime: [
            {required: true, message: '起始时间不能为空', trigger: 'blur'}
          ],
          deadlineTime: [
            {required: true, message: '结束时间不能为空', trigger: 'blur'}
          ],
          isEnd: [
            {required: true, message: '工单完结状态不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.dataForm.machineId = ''
          this.dataForm.workerId = ''
          this.dataForm.workType = ''
          this.dataForm.remark = ''
          this.dataForm.des = ''
        })
        this.$http({
          url: '/sys/user/workerList',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.workOptions = data.data
          }
        })

        this.$http({
          url: '/ad/worktype/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.radioList = data.list
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
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: `/ad/workorder/save`,
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
<style lang="scss" scoped>
.thePageDialog /deep/ {
  .el-dialog {
    background-color: transparent;
    margin: 10vh auto!important;
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
      height: calc(80vh - 120px);
      overflow-x: hidden;
      overflow-y: auto;
      padding: 15px;
      .el-form{
        .el-form-item{
          margin: 0 0 15px;
          .el-form-item__label {
            font-size: 12px;
            padding: 0 8px 0 0;
            text-align: right;
            line-height: 30px;
            height: 30px;
          }
          .el-form-item__content {
            line-height: 30px;
            /*提示信息*/
            .el-form-item__error{
              padding-top: 2px !important;
            }
            /*指派人员，对应机柜*/
            .el-select {
              width: 100%;
              .el-input {
                font-size: 12px;
                line-height: 30px;
                .el-input__inner {
                  border-radius: 2px;
                  height: 30px;
                  line-height: 30px;
                }
              }
            }
            /*修改时的输入框*/
            .el-input {
              .el-input__inner {
                height: 30px;
                line-height: 30px;
              }
            }

            /*日期选择器*/
            .el-date-editor{
              width: 100%;
              font-size: 12px;
              .el-input__inner{
                padding-right: 0;
                height: 30px;
                line-height: 30px;
              }
            }

            /*文本框*/
            .el-textarea{
              font-size: 12px;
            }
            .el-radio-group {
              position: relative;
              .el-radio {
                width: 48%;
                float: left;
                margin: 0 2% 10px 0;
                height: 30px;
                line-height: 30px;
                padding: 0;
                &:nth-of-type(2n) {
                  margin: 0 0 10px 2%;
                }
                .el-radio__input {
                  opacity: 0;
                  display: none;
                }
                .el-radio__label {
                  display: block;
                  text-align: center;
                  line-height: 30px;
                  height: 30px;
                  padding: 0;
                }
              }
              .is-checked {
                background-color: #082F40;
                border-color: #082F40;
                height: 30px;
                line-height: 30px;
                .el-radio__label {
                  color: #fff;
                }
              }
            }

          }


        }
      }

    }

    .el-dialog__footer {
      background-color: #fff;
      padding: 15px;
      .dialog-footer {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        .el-button {
          width: 60%;
          background-color: #082F40;
          border-color: #082F40;
        }
      }
    }
  }
}
</style>
