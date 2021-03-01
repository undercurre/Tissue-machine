<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="机柜" prop="machineId">
        <template>
          <el-select v-model="dataForm.machineId" :disabled="dataForm.id ? true : false" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in machineList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="商品" prop="goodsId">
        <template>
          <el-select v-model="dataForm.goodsId" :disabled="dataForm.id ? true : false" filterable clearable placeholder="请选择">
            <el-option
              v-for="item in goodsList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input v-model="dataForm.stock" :disabled="disabled" placeholder="库存"></el-input>
      </el-form-item>
      <el-form-item label="排序等级" prop="level">
        <el-input v-model="dataForm.level" :disabled="disabled" placeholder="排序等级"></el-input>
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
        machineList: '',
        goodsList: '',
        dataForm: {
          id: 0,
          machineId: '',
          goodsId: '',
          stock: '',
          level: '',
          createTime: ''},
        dataRule: {
          machineId: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          goodsId: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          stock: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled, goodsId) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.dataForm.goodsId = goodsId
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          // 获取所有机柜信息
          this.$http({
            url: '/ad/tissuemachine/queryAll',
            method: 'get'
          }).then(({data}) => {
            this.machineList = data.list
          })
          // 获取所有已上架商品信息
          this.$http({
            url: '/ad/goods/queryAllOnSale',
            method: 'get'
          }).then(({data}) => {
            this.goodsList = data.list
          })

          if (this.dataForm.id) {
            this.$http({
              url: `/ad/machinestock/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.machinestock
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
                url: `/ad/machinestock/${!this.dataForm.id ? 'save' : 'update'}`,
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
