<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    append-to-body
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="优惠券标题" prop="title">
        <el-input v-model="dataForm.title" :disabled="disabled" placeholder="优惠券标题"></el-input>
      </el-form-item>
      <el-form-item label="优惠券编号" prop="couponSn">
        <el-input v-model="dataForm.couponSn" :disabled="disabled" placeholder="优惠券编号"></el-input>
      </el-form-item>
<!--      <el-form-item label="最低消费金额" prop="minPrice">-->
<!--        <el-input-number :min="1" :precision="2" v-model="dataForm.minPrice" :disabled="disabled"-->
<!--                         placeholder="最低消费金额"></el-input-number>-->
<!--      </el-form-item>-->
      <el-form-item label="优惠券类型" prop="couponType">
        <el-radio-group v-model="dataForm.couponType" :disabled="disabled">
          <el-radio :label="1">代金券</el-radio>
          <el-radio :label="2">折扣</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="优惠金额" prop="subPrice" v-if="dataForm.couponType === 1">
        <el-input v-model="dataForm.subPrice" :disabled="disabled" placeholder="优惠金额">
          <template slot="append">元</template>
        </el-input>
      </el-form-item>
      <el-form-item label="折扣率" prop="discount" v-if="dataForm.couponType === 2">
        <el-input-number :max="9.9" :min="0" :step="0.01" :precision="2" v-model="dataForm.discount" :disabled="disabled" placeholder="折扣率"></el-input-number>
      </el-form-item>
      <el-form-item label="有效期开始时间" prop="beginTime">
        <el-date-picker type="datetime" v-model="dataForm.beginTime" :disabled="disabled"
                        :picker-options="datePicker"
                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="有效期开始时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="有效期结束时间" prop="endTime">
        <el-date-picker type="datetime" v-model="dataForm.endTime" :disabled="disabled"
                        :picker-options="datePicker"
                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="有效期结束时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="优惠券数量" prop="totalCount">
        <el-input-number :min="1" v-model="dataForm.totalCount" :disabled="disabled"
                         placeholder="优惠券数量"></el-input-number>
      </el-form-item>
      <el-form-item label="已发放数量" prop="sendCount">
        <el-input-number :min="1" v-model="dataForm.sendCount" :disabled="disabled"
                         placeholder="已发放数量"></el-input-number>
      </el-form-item>
      <el-form-item label="每人限领数量" prop="limitUser">
        <el-input-number :min="1" v-model="dataForm.limitUser" :disabled="disabled"
                         placeholder="每人限领数量"></el-input-number>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio :label="1">正常</el-radio>
          <el-radio :label="2">过期</el-radio>
          <el-radio :label="3">禁用</el-radio>
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
      let validateMoney = (rule, value, callback) => {
        if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(value)) {
          callback(new Error('格式有误'))
        } else {
          callback()
        }
      }
      return {
        disabled: false,
        visible: false,
        dataForm: {
          id: 0,
          title: '',
          couponSn: '',
          couponType: 1,
          minPrice: 1,
          subPrice: '',
          discount: '',
          beginTime: '',
          endTime: '',
          totalCount: 1,
          sendCount: 1,
          limitType: 0,
          limitUser: 1,
          status: 1
        },
        dataRule: {
          title: [{
            required: true,
            message: '优惠券标题不能为空',
            trigger: 'blur'
          }],
          couponSn: [{
            required: true,
            message: '优惠券编号不能为空',
            trigger: 'blur'
          }],
          subPrice: [{
            validator: validateMoney,
            trigger: 'blur'
          }],
          discount: [{
            validator: validateMoney,
            trigger: 'blur'
          }],
          minPrice: [{
            required: true,
            message: '最低消费金额不能为空',
            trigger: 'blur'
          }],
          beginTime: [{
            required: true,
            message: '有效期开始时间不能为空',
            trigger: 'change'
          }],
          endTime: [{
            required: true,
            message: '有效期结束时间不能为空',
            trigger: 'change'
          }],
          totalCount: [{
            required: true,
            message: '优惠券发放数量不能为空',
            trigger: 'blur'
          }],
          limitUser: [{
            required: true,
            message: '每人限领数量不能为空',
            trigger: 'blur'
          }]
        },
        datePicker: this.picker()
      }
    },
    methods: {
      picker () {
        return {
          // 可选时间大于等于当前时间
          disabledDate (time) {
            return time.getTime() < Date.now()
          }
        }
      },
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/coupon/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm = data.coupon
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
                url: `/ad/coupon/${!this.dataForm.id ? 'save' : 'update'}`,
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
                  this.$emit('refreshDataList')
                }
              })
            }
          })
      }
    }
  }
</script>
