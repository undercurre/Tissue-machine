<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="dataForm.roleName" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item label="上级角色" prop="superiorRoleId">
        <el-select v-model="dataForm.superiorRoleId" placeholder="请选择">
          <el-option
            v-for="item in roleOptions"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item size="mini" label="PC端功能权限">
        <el-tree
          check-strictly
          :data="pcMenuList"
          :props="menuListTreeProps"
          node-key="menuId"
          ref="pcMenuListTree"
          :default-expand-all="true"
          show-checkbox>
        </el-tree>
      </el-form-item>
      <el-form-item size="mini" label="移动端功能权限">
        <el-tree
          check-strictly
          :data="mobileMenuList"
          :props="menuListTreeProps"
          node-key="menuId"
          ref="mobileMenuListTree"
          :default-expand-all="true"
          show-checkbox>
        </el-tree>
      </el-form-item>
<!--      <el-form-item size="mini" label="数据权限">-->
<!--        <el-tree-->
<!--          check-strictly-->
<!--          :data="orgList"-->
<!--          :props="orgListTreeProps"-->
<!--          node-key="orgNo"-->
<!--          ref="orgListTree"-->
<!--          :default-expand-all="true"-->
<!--          show-checkbox>-->
<!--        </el-tree>-->
<!--      </el-form-item>-->
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
        visible: false,
        user: '',
        roleOptions: '',
        pcMenuList: [],
        mobileMenuList: [],
        menuList: [],
        // orgList: this.treeDataTranslate(JSON.parse(sessionStorage.getItem('orgList') || '[]'), 'orgNo', 'parentNo'),
        menuListTreeProps: {
          label: 'name',
          children: 'children'
        },
        // orgListTreeProps: {
        //   label: 'orgName',
        //   children: 'children'
        // },
        dataForm: {
          id: '',
          roleName: '',
          remark: '',
          superiorRoleId: ''
        },
        dataRule: {
          roleName: [
            {
              required: true,
              message: '角色名称不能为空',
              trigger: 'blur'
            }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.$http({
          url: '/sys/menu/list',
          method: 'get'
        }).then(({ data }) => {
          this.pcMenuList = this.treeDataTranslate(data.pcMenuList, 'menuId')
          this.mobileMenuList = this.treeDataTranslate(data.mobileMenuList, 'menuId')
        })
        this.$http({
          url: '/sys/user/info',
          method: 'get'
        }).then(({ data }) => {
          this.roleOptions = data.user.roleList
        })

        this.$http({
          url: '/sys/menu/queryMenuByStaff',
          method: 'get'
        }).then(({ data }) => {
          this.pcMenuList = this.treeDataTranslate(data.pcMenuList, 'menuId')
          this.mobileMenuList = this.treeDataTranslate(data.mobileMenuList, 'menuId')
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
              url: `/sys/role/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm.roleName = data.role.roleName
                this.dataForm.remark = data.role.remark
                this.dataForm.superiorRoleId = data.role.createRoleId
                this.$refs.pcMenuListTree.setCheckedKeys(data.role.pcMenuIdList)
                this.$refs.mobileMenuListTree.setCheckedKeys(data.role.mobileMenuIdList)
                // this.$refs.orgListTree.setCheckedKeys(data.role.orgNoList)
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: `/sys/role/${!this.dataForm.id ? 'save' : 'update'}`,
              method: 'post',
              data: {
                'roleId': this.dataForm.id || undefined,
                'roleName': this.dataForm.roleName,
                'remark': this.dataForm.remark,
                'superiorRoleId': this.dataForm.superiorRoleId,
                // 'orgNoList': [].concat(this.$refs.orgListTree.getCheckedKeys(), this.$refs.orgListTree.getHalfCheckedKeys()),
                'pcMenuIdList': [].concat(this.$refs.pcMenuListTree.getCheckedKeys(), this.$refs.pcMenuListTree.getHalfCheckedKeys()),
                'mobileMenuIdList': [].concat(this.$refs.mobileMenuListTree.getCheckedKeys(), this.$refs.mobileMenuListTree.getHalfCheckedKeys())
              }
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
