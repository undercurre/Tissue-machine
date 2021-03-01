<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="订单编号" prop="orderSn">
        <el-input v-model="dataForm.orderSn" :disabled="disabled" placeholder="订单编号"></el-input>
      </el-form-item>
      <el-form-item label="会员ID" prop="userId">
        <el-input v-model="dataForm.nickname" :disabled="disabled" placeholder="会员ID"></el-input>
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType">
        <el-radio-group v-model="dataForm.orderType" disabled>
          <el-radio-button :label="1">普通订单</el-radio-button>
          <el-radio-button :label="2">免费领取</el-radio-button>
          <el-radio-button :label="3">活动订单</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-radio-group v-model="dataForm.orderStatus" disabled>
          <el-radio-button :label="0">未支付</el-radio-button>
          <el-radio-button :label="1">已支付</el-radio-button>
          <el-radio-button :label="2">退款中</el-radio-button>
          <el-radio-button :label="3">已退款</el-radio-button>
          <el-radio-button :label="4">已取消</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="实际消费金额" prop="price">
        <el-input v-model="dataForm.price" :disabled="disabled" placeholder="实际消费金额"></el-input>
      </el-form-item>
      <el-form-item label="消费金" prop="comsumePrice">
        <el-input v-model="dataForm.comsumePrice" :disabled="disabled" placeholder="消费金"></el-input>
      </el-form-item>
      <el-form-item label="机柜信息" prop="machineName">
        <el-input v-model="dataForm.machineName" :disabled="disabled" placeholder="纸巾机ID"></el-input>
      </el-form-item>
      <el-form-item label="订单创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="订单创建时间"></el-input>
      </el-form-item>
      <el-form-item label="支付时间" prop="payTime">
        <el-input v-model="dataForm.payTime" :disabled="disabled" placeholder="支付时间"></el-input>
      </el-form-item>
      <el-form-item label="订单过期时间" prop="expireTime">
        <el-input v-model="dataForm.expireTime" :disabled="disabled" placeholder="订单过期时间"></el-input>
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
        dataForm: {
          id: 0,
          fromType: '',
          orderSn: '',
          userId: '',
          activityId: '',
          orderType: '',
          orderStatus: '',
          price: '',
          comsumePrice: '',
          machineId: '',
          createTime: '',
          payTime: '',
          nickname: '',
          machineName: '',
          isDelete: '',
          expireTime: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/order/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.order
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
                url: `/ad/order/${!this.dataForm.id ? 'save' : 'update'}`,
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
