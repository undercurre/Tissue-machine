<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看商品信息'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="thePageDialog"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      class="thePageForm"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="90px"
    >
      <el-form-item label="商品名字：" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="商品名字"></el-input>
      </el-form-item>
      <el-form-item label="价格：" prop="price">
        <el-input v-model="dataForm.price" :disabled="disabled" placeholder="价格"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="remark">
        <el-input type="textarea" rows="4" v-model="dataForm.remark" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="是否上架：" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio-button label="0">未上架</el-radio-button>
          <el-radio-button label="1">已上架</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="采购总数：" prop="totalPurchase">
        <el-input v-model="totalPurchase" :disabled="disabled" placeholder="采购总数"></el-input>
      </el-form-item>
      <el-form-item label="已投放：" prop="putOn">
        <el-input v-model="putOn" :disabled="disabled" placeholder="已投放"></el-input>
      </el-form-item>
      <el-form-item label="库存：" prop="stock">
        <el-input v-model="dataForm.stock" :disabled="disabled" placeholder="库存"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
<!--      <el-button @click="visible = false">取消</el-button>-->
      <!--      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit()">确定</el-button>-->
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        url: '',
        disabled: false,
        visible: false,
        totalPurchase: 10000,
        putOn: 100,
        repertory: 100000,
        dataForm: {
          id: 0,
          name: '',
          price: '',
          createTime: '',
          imageUrlList: '',
          updateTime: '',
          remark: '',
          status: '',
          stock: ''
        },
        dataRule: {
          remark: [
            {required: true, message: '备注不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '价格不能为空', trigger: 'blur'}
          ],
          imageUrlList: [
            {required: true, message: '商品图片列表不能为空', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '上架状态不能为空', trigger: 'blur'}
          ],
          totalPurchase: [
            {required: true, message: '采购总数不能为空', trigger: 'blur'}
          ],
          putOn: [
            {required: true, message: '已投放不能为空', trigger: 'blur'}
          ],
          stock: [
            {required: true, message: '库存不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.url = this.$http.BASE_URL + `/sys/oss/upload?token=${this.$cookie.get('token')}`
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.dataForm.imageUrlList = []
          if (this.dataForm.id) {
            this.$http({
              url: `/ad/goods/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.goods
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
    margin: 10vh auto !important;
    background-color: transparent;
    .el-dialog__header{
      display: flex;
      justify-content: center;
      .el-dialog__title{
        font-size: 16px;
        color: #fff;
      }
    }
    .el-dialog__body{
      background-color: #fff;
      height: calc(80vh - 53px);
      overflow: auto;
      position: relative;
      padding: 15px;
      .thePageForm {
        position: relative;
        .el-form-item {
          margin: 0 0 15px;
          .el-form-item__label {
            font-size: 12px;
            padding: 0 8px 0 0;
            text-align: left;
            line-height: 30px;
          }
          .el-form-item__content {
            line-height: 30px;
            .el-input {
              font-size: 12px;
              .el-input__inner {
                color: #333;
                background-color: #F5F5F5;
                border-radius: 2px;
                height: 30px;
                line-height: 30px;
              }
            }
            .el-textarea.is-disabled .el-textarea__inner {
              background-color: #fff;
            }
            .el-radio-group {
              .el-radio-button {
                .el-radio-button__inner {
                  font-size: 12px;
                  padding: 8px 20px;
                }
                & + .is-disabled {
                  .el-radio-button__inner {
                    background-color: #1FBF84;
                  }
                }
              }
            }
          }
        }
      }


      .machineLocate{
        font-size: 12px;
        background-color: #1FBF84;
        color: #ffffff;
        padding: 1px;
        border-radius: 4px;
        width: 92px;
        height: 21px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
    .el-dialog__footer {
      background-color: #ffffff;
      padding: 0;
    }
  }
}
</style>
