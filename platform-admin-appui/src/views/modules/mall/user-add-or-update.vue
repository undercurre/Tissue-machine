<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看会员详情'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="dataForm.userName" :disabled="disabled" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="微信昵称" prop="nickname">
        <el-input v-model="dataForm.nickname" :disabled="disabled" placeholder="微信昵称"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-dict code="SEX" :disabled="disabled" v-model="dataForm.gender"></el-dict>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="dataForm.mobile" :disabled="disabled" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker type="datetime" v-model="dataForm.birthday" :disabled="disabled"
                        placeholder="生日"></el-date-picker>
      </el-form-item>
      <el-form-item label="用户头像" prop="headImgUrl">
        <img style="height: 30%;width: 30%" :src="dataForm.headImgUrl"/>
      </el-form-item>
      <el-form-item label="注册时间" prop="registerTime">
        <el-date-picker type="datetime" v-model="dataForm.registerTime" :disabled="disabled"
                        placeholder="注册时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="注册ip" prop="registerIp">
        <el-input v-model="dataForm.registerIp" :disabled="disabled" placeholder="注册ip"></el-input>
      </el-form-item>
      <el-form-item label="最后登录时间" prop="lastLoginTime">
        <el-date-picker type="datetime" v-model="dataForm.lastLoginTime" :disabled="disabled"
                        placeholder="最后登录时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="最后登录IP" prop="lastLoginIp">
        <el-input v-model="dataForm.lastLoginIp" :disabled="disabled" placeholder="最后登录IP"></el-input>
      </el-form-item>
      <el-form-item label="会员等级" prop="userLevelId">
        <el-select v-model="dataForm.userLevelId" :disabled="disabled" clearable filterable placeholder="请选择"
                   class="width185">
          <el-option
            v-for="level in levelList"
            :key="level.id"
            :label="level.name"
            :value="level.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户标识" prop="openId">
        <el-input v-model="dataForm.openId" :disabled="disabled" placeholder="用户的标识"></el-input>
      </el-form-item>
      <el-form-item label="公众号用户标识" prop="mpOpenId">
        <el-input v-model="dataForm.mpOpenId" :disabled="disabled" placeholder="公众号用户的标识"></el-input>
      </el-form-item>
      <el-form-item label="用户唯一标识" prop="unionId">
        <el-input v-model="dataForm.unionId" :disabled="disabled" placeholder="用户唯一标识"></el-input>
      </el-form-item>
      <el-form-item label="是否关注" prop="subscribe">
        <el-dict code="IS_NOT" :disabled="disabled" v-model="dataForm.subscribe"></el-dict>
      </el-form-item>
      <el-form-item label="关注时间" prop="subscribeTime">
        <el-date-picker type="datetime" v-model="dataForm.subscribeTime" :disabled="disabled"
                        placeholder="关注时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="总积分" prop="signAllIntegral">
        <el-input v-model="dataForm.signAllIntegral" :disabled="disabled" placeholder="签到、购物获得总积分"></el-input>
      </el-form-item>
      <el-form-item label="已使用积分" prop="signUsedIntegral">
        <el-input v-model="dataForm.signUsedIntegral" :disabled="disabled" placeholder="已兑换积分"></el-input>
      </el-form-item>
      <el-form-item label="余额" prop="balance">
        <el-input v-model="dataForm.balance" :disabled="disabled" placeholder="余额"></el-input>
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
          id: '',
          userName: '',
          password: '',
          gender: '',
          birthday: '',
          registerTime: '',
          lastLoginTime: '',
          lastLoginIp: '',
          userLevelId: '',
          nickname: '',
          mobile: '',
          registerIp: '',
          headImgUrl: '',
          openId: '',
          mpOpenId: '',
          unionId: '',
          subscribe: '',
          subscribeTime: '',
          signAllIntegral: '',
          signUsedIntegral: '',
          balance: ''
        },
        levelList: [],
        dataRule: {
          name: [
            {
              required: true,
              message: '名称不能为空',
              trigger: 'blur'
            }
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
              url: `/mall/user/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm = data.user
              }
            })
          }
          this.$http({
            url: '/mall/userlevel/queryAll',
            method: 'get'
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.levelList = data.list
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
                url: `/mall/user/${!this.dataForm.id ? 'save' : 'update'}`,
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
