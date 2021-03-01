<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="dataForm.userName" placeholder="登录帐号"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-dict code="SEX" v-model="dataForm.sex"></el-dict>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model="dataForm.realName" placeholder="真实姓名"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="角色" prop="roleIdList">
        <el-select v-model="dataForm.roleIdList" multiple clearable filterable placeholder="请选择" class="width185" :disabled="disabled">
          <el-option
            v-for="role in roleList"
            :key="role.roleId"
            :label="role.roleName"
            :value="role.roleId">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item size="mini" label="PC端功能权限">-->
<!--        <el-tree-->
<!--          check-strictly-->
<!--          :data="pcMenuList"-->
<!--          :props="menuListTreeProps"-->
<!--          node-key="menuId"-->
<!--          ref="pcMenuListTree"-->
<!--          :default-expand-all="true"-->
<!--          show-checkbox>-->
<!--        </el-tree>-->
<!--      </el-form-item>-->
<!--      <el-form-item size="mini" label="移动端功能权限">-->
<!--        <el-tree-->
<!--          check-strictly-->
<!--          :data="mobileMenuList"-->
<!--          :props="menuListTreeProps"-->
<!--          node-key="menuId"-->
<!--          ref="mobileMenuListTree"-->
<!--          :default-expand-all="true"-->
<!--          show-checkbox>-->
<!--        </el-tree>-->
<!--      </el-form-item>-->
      <el-form-item label="状态" size="mini" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">正常</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        // menuList: [],
        // pcMenuList: [],
        // mobileMenuList: [],
        user: '',
        // menuListTreeProps: {
        //   label: 'name',
        //   children: 'children'
        // },
        visible: false,
        visiblePopover: false,
        roleList: [],
        dataForm: {
          id: '',
          userName: '',
          sex: '',
          realName: '',
          email: '',
          mobile: '',
          roleIdList: [],
          status: 1,
          menuIdList: ''
        },
        dataRule: {
          userName: [{
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          }],
          sex: [{
            required: true,
            message: '性别不能为空',
            trigger: 'blur'
          }],
          realName: [{
            required: true,
            message: '真实姓名不能为空',
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        // this.$http({
        //   url: '/sys/menu/queryMenuByStaff',
        //   method: 'get'
        // }).then(({ data }) => {
        //   this.pcMenuList = this.treeDataTranslate(data.pcMenuList, 'menuId')
        //   this.mobileMenuList = this.treeDataTranslate(data.mobileMenuList, 'menuId')
        // })
        this.$http({
          url: '/sys/role/select',
          method: 'get'
        }).then(({data}) => {
          this.roleList = data && data.code === 0 ? data.list : []
        }).then(() => {
          this.visible = true
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            this.$refs.pcMenuListTree.setCheckedKeys([])
            this.$refs.mobileMenuListTree.setCheckedKeys([])
          })
        }).then(() => {
          if (this.dataForm.id) {
            this.$http({
              url: `/sys/user/staffInfo/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm.userName = data.user.userName
                this.dataForm.sex = data.user.sex.toString()
                this.dataForm.realName = data.user.realName
                // this.dataForm.salt = data.user.salt
                this.dataForm.email = data.user.email
                this.dataForm.mobile = data.user.mobile
                // this.dataForm.roleIdList = data.user.roleIdList
                // this.$refs.pcMenuListTree.setCheckedKeys(data.user.pcMenuIdList)
                // this.$refs.mobileMenuListTree.setCheckedKeys(data.user.mobileMenuIdList)
                this.dataForm.status = data.user.status
                this.dataForm.roleIdList = data.user.roleIdList
              }
            })
          }
        })
      },
      // 机构树选中
      // orgListTreeCurrentChangeHandle (data) {
      //   this.dataForm.orgNo = data.orgNo
      //   this.dataForm.orgName = data.orgName
      //   this.visiblePopover = false
      // },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: `/sys/user/${!this.dataForm.id ? 'saveStaff' : 'updateStaff'}`,
              method: 'post',
              data: {
                'userId': this.dataForm.id || undefined,
                'userName': this.dataForm.userName,
                'sex': this.dataForm.sex,
                'realName': this.dataForm.realName,
                // 'salt': this.dataForm.salt,
                'email': this.dataForm.email,
                'mobile': this.dataForm.mobile,
                // 'orgNo': this.dataForm.orgNo,
                'status': this.dataForm.status,
                'roleIdList': this.dataForm.roleIdList
                // 'pcMenuIdList': [].concat(this.$refs.pcMenuListTree.getCheckedKeys(), this.$refs.pcMenuListTree.getHalfCheckedKeys()),
                // 'mobileMenuIdList': [].concat(this.$refs.mobileMenuListTree.getCheckedKeys(), this.$refs.mobileMenuListTree.getHalfCheckedKeys())
              }
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.visible = false
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500
                })
                this.$emit('refreshDataList')
              }
            })
          }
        })
      }
    }
  }
</script>

