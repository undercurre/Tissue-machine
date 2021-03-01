<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="负责人" prop="roleIdList">
        <el-select v-model="dataForm.userOpenId"  clearable filterable placeholder="请选择" class="width185" :disabled="dataForm.id? true : false">
          <el-option
            v-for="role in mpUserList"
            :key="role.openId"
            :label="role.nickname"
            :value="role.openId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分组名字" prop="groupName">
        <el-input v-model="dataForm.groupName" :disabled="dataForm.id? true : false" placeholder="分组名字"></el-input>
      </el-form-item>
      <el-form-item label="机柜列表" prop="machineIds">
        <el-select v-model="dataForm.machineIds" :disabled="disabled" multiple placeholder="请选择" width="100%">
          <el-option
            v-for="item in machineOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="创建时间" prop="createTime">-->
<!--        <el-input v-model="dataForm.createTime" :disabled="disabled" placeholder="创建时间"></el-input>-->
<!--      </el-form-item>-->
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
        machineOptions: '',
        mpUserList: [],
        dataForm: {
          id: 0,
          userOpenId: '',
          groupName: '',
          machineIds: '',
          createTime: ''},
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
              url: `/ad/group/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.group
              }
            })
          }
          this.$http({
            url: '/ad/machinegroup/queryNoGroupList',
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.machineOptions = data.list
            }
          })
          this.$http({
            url: '/ad/mpuser/queryAll',
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.mpUserList = data.list
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
                url: `/ad/group/${!this.dataForm.id ? 'save' : 'update'}`,
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
