<template>
  <el-dialog
    :title="!disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="thePageDialog">
    <!--    修改或查看时才有表单出现-->
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="指派人员：" prop="workerId">
        <template>
          <el-input v-model="dataForm.workerName" :disabled="disabled" placeholder="指派人员" v-if="disabled"></el-input>
          <el-select v-model="dataForm.workerId" filterable clearable placeholder="请选择" v-if="!disabled">
            <el-option
              v-for="item in workOptions"
              :key="item.userId"
              :label="item.realName"
              :value="item.userId">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="工单类型：" prop="workType" >
        <!--        <el-input v-model="dataForm.workType" :disabled="disabled" placeholder="工单类型 0：采购工单 1：设备维护工单 2：送货工单 3：贴印刷广告工单"></el-input>-->
<!--        <el-radio-group v-model="dataForm.workType" :disabled="disabled">-->
<!--          <div style="display: flex">-->
<!--            <el-radio :label="0" border>广告采购</el-radio>-->
<!--            <el-radio :label="1" border>设备维护</el-radio>-->
<!--          </div>-->
<!--          <div style="display: flex">-->
<!--            <el-radio :label="2" border>送货</el-radio>-->
<!--            <el-radio :label="3" border>贴印刷广告</el-radio>-->
<!--          </div>-->
<!--        </el-radio-group>-->
        <el-radio-group v-model="dataForm.workType" :disabled="disabled" size="mini">
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
      <!--      <el-form-item v-if="!dataForm.id" label="对应机柜" prop="machineId">-->
      <!--        &lt;!&ndash;        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="工单绑定的机柜"></el-input>&ndash;&gt;-->
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
      <el-form-item label="工单状态：" prop="status" >
        <!--        <el-input v-model="dataForm.status" :disabled="disabled" placeholder="工单状态 0：未响应 1：待处理 2：已处理"></el-input>-->
        <!--        <el-radio-group v-model="dataForm.status" :disabled="disabled">-->
        <!--          <el-radio-button label="0">未响应</el-radio-button>-->
        <!--          <el-radio-button label="1">待处理</el-radio-button>-->
        <!--          <el-radio-button label="2">已处理</el-radio-button>-->
        <!--          <el-radio-button label="3">已完成</el-radio-button>-->
        <!--        </el-radio-group>-->
        <el-radio-group v-model="dataForm.status" v-if="!disabled">
          <el-radio :label="0" border>未响应</el-radio>
          <el-radio :label="1" border>处理中</el-radio>
          <el-radio :label="2" border>已处理</el-radio>
          <el-radio :label="3" border>已作废</el-radio>
          <el-radio :label="4" border>被移除</el-radio>
        </el-radio-group>
<!--        <el-select v-model="dataForm.status" filterable clearable placeholder="可选填" v-if="!disabled">-->
<!--          <el-option-->
<!--            v-for="item in orderStatus"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
        <div v-else>
          <el-tag v-if="dataForm.status === 0" class="deal" style="background-color: #FF3030;">待开始</el-tag>
          <el-tag v-if="dataForm.status === 1" class="deal" style="background-color: #FF3030;">处理中</el-tag>
          <el-tag v-if="dataForm.status === 2" class="deal" style="background-color: #FF3030;">已处理</el-tag>
          <el-tag v-if="dataForm.status === 3" class="deal" style="background-color: #FF3030;">已作废</el-tag>
          <el-tag v-if="dataForm.status === 4" class="deal" style="background-color: #FF3030;">被移除</el-tag>
        </div>
      </el-form-item>
      <el-form-item label="工单完结状态：" prop="isEnd" v-if="disabled">
        <el-tag v-if="dataForm.isEnd === 3" class="isFinished" style="background-color: #8894A8;">已完结</el-tag>
        <el-tag v-if="dataForm.isEnd === 1 || dataForm.isEnd === 2" class="isFinished" style="background-color: #8894A8;">待确认完结</el-tag>
        <el-tag v-if="dataForm.isEnd === 0" class="isFinished" style="background-color: #8894A8;">未完结</el-tag>
      </el-form-item>
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

      <el-form-item label="对应机柜：" prop="machineId" v-if="!disabled">
        <el-select v-model="dataForm.machineId" filterable clearable placeholder="可选填" v-if="!disabled">
          <el-option
            v-for="item in machineOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工单描述：" prop="des">
        <el-input v-model="dataForm.des" type="textarea" rows="4" :disabled="disabled" placeholder="工单描述"></el-input>
      </el-form-item>
      <el-form-item label="工单备注：" prop="remark" v-if="disabled && (dataForm.remark !== null && dataForm.remark !== '')">
        <el-input v-model="dataForm.remark" type="textarea" rows="4" :disabled="disabled" placeholder="工单备注"></el-input>
      </el-form-item>
<!--      <el-form-item label="工单创建时间：" prop="createTime" v-if="disabled">-->
<!--        <div>{{ dataForm.createTime }}</div>-->
<!--      </el-form-item>-->
      <el-form-item label="接单时间：" prop="acceptTime" v-if="disabled">
        <div>{{ dataForm.acceptTime }}</div>
      </el-form-item>
      <el-form-item label="工单完成时间：" prop="finishTime" v-if="disabled">
        <div>{{ dataForm.finishTime }}</div>
      </el-form-item>
      <el-form-item label="工单开始时间：" prop="showStartTime" v-if="disabled">
        <div style="color: #333333;font-size: 12px">{{ dataForm.startTime }}</div>
      </el-form-item>
      <el-form-item label="工单截止时间：" prop="showFinishTime" v-if="disabled">
        <div style="color: #333333;font-size: 12px">{{ dataForm.finishTime }}</div>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
<!--      <el-button @click="visible = false">取消</el-button>-->
      <el-button v-if="!disabled && !isDel" type="primary" @click="dataFormSubmit()">确认修改</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        isDel: false,
        disabled: false,
        visible: false,
        workOptions: [],
        machineOptions: [],
        radioList: [],
        choiceOfFinished: '',
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
          isEnd: '',
          showStartTime: '',
          showFinishTime: ''
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
          showStartTime: [
            {required: true, message: '工单开始时间不能为空', trigger: 'blur'}
          ],
          showFinishTime: [
            {required: true, message: '工单截止时间不能为空', trigger: 'blur'}
          ],
          workerId: [
            {required: true, message: '指派人员不能为空', trigger: 'blur'}
          ],
          isEnd: [
            {required: true, message: '工单完结状态不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.workOptions = []
        this.machineOptions = []
        if (!disabled) {
          this.$http({
            url: '/sys/user/workerList',
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.workOptions = data.data
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
        }

        this.$http({
          url: '/ad/worktype/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.radioList = data.list
          }
        })

        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/workorder/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.workorder
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
                url: `/ad/workorder/update`,
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
        background-color: #ffffff;
        height: calc(80vh - 120px);
        overflow-x: hidden;
        overflow-y: auto;
        padding: 15px;
        .el-form{
          .el-form-item{
            margin: 0 0 10px;
            .el-form-item__label {
              font-size: 12px;
              padding: 0 8px 0 0;
              text-align: left;
              width: 105px!important;
              line-height: 30px;
              height: 30px;
            }
            .el-form-item__content {
              margin-left: 105px !important;
              line-height: 30px;
              .el-select {
                width: 100%;
                .el-input {
                  font-size: 12px;
                  .el-input__inner {
                    border-radius: 2px;
                  }
                }
              }
              .el-radio-group {
                position: relative;
                width: 100%;
                .el-radio {
                  width: 48%;
                  float: left;
                  margin: 0 2% 10px 0;
                  padding: 0;
                  height: 30px;
                  line-height: 30px;
                  border-radius: 2px;
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
                    font-size: 12px;
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
              /*工单状态*/
              .deal{
                color: #ffffff;
                height: 30px;
                width: 48%;
                /*Line-height影响文字的居中效果*/
                line-height: 30px;
                font-size: 12px;
                border-radius: 2px;
                vertical-align: middle;
                text-align: center;
              }
              /*工单完结状态*/
              .isFinished{
                color: #ffffff;
                height: 30px;
                width: 48%;
                /*Line-height影响文字的居中效果*/
                line-height: 30px;
                font-size: 12px;
                border-radius: 2px;
                vertical-align: middle;
                text-align: center;
              }

              /*查看时的输入框*/
              .el-input.is-disabled .el-input__inner {
                color: #333333;
                background-color: #F5F5F5;
                border-radius: 2px;
                height: 30px;
                line-height: 30px;
              }
              /*修改时的输入框*/
              .el-input--medium .el-input__inner {
                height: 30px;
                line-height: 30px;
              }
              /*查看时的文本框*/
              .el-textarea.is-disabled .el-textarea__inner {
                color: #333333;
              }
            }
          }
        }
        .confirmDialog{
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          color: #ffffff;
        }
      }

      .el-dialog__footer {
        background-color: #ffffff;

        .dialog-footer {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          .el-button {
            width: 60%;
          }
        }
      }
    }
  }
</style>
