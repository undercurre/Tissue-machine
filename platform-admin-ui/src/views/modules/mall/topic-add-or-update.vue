<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="活动主题" prop="title">
        <el-input v-model="dataForm.title" :disabled="disabled" placeholder="活动主题"></el-input>
      </el-form-item>
      <el-form-item label="活动内容" prop="content">
        <ueditor v-model="dataForm.content" :disabled="disabled" placeholder="活动内容"></ueditor>
      </el-form-item>
      <el-form-item label="化名" prop="avatar">
        <el-img v-model="dataForm.avatar" :disabled="disabled">
        </el-img>
      </el-form-item>
      <el-form-item label="活动条例图片" prop="itemPicUrl">
        <el-img v-model="dataForm.itemPicUrl" :disabled="disabled">
        </el-img>
      </el-form-item>
      <el-form-item label="子标题" prop="subtitle">
        <el-input v-model="dataForm.subtitle" :disabled="disabled" placeholder="子标题"></el-input>
      </el-form-item>
      <el-form-item label="活动类别" prop="topicCategoryId">
        <el-select v-model="dataForm.topicCategoryId" :disabled="disabled" clearable filterable placeholder="请选择"
                   class="width185">
          <el-option
            v-for="topicCategory in topicCategorys"
            :key="topicCategory.id"
            :label="topicCategory.title"
            :value="topicCategory.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动价格" prop="priceInfo">
        <el-input-number v-model="dataForm.priceInfo" :min="0" label="活动价格"></el-input-number>
      </el-form-item>
      <el-form-item label="阅读数" prop="readCount">
        <el-input v-model="dataForm.readCount" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="场景图片链接" prop="scenePicUrl">
        <el-img v-model="dataForm.scenePicUrl" :disabled="disabled">
        </el-img>
      </el-form-item>
      <!--      <el-form-item label="活动模板Id" prop="topicTemplateId">-->
      <!--        <el-input v-model="dataForm.topicTemplateId" :disabled="disabled" placeholder="活动模板Id"></el-input>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="活动标签Id" prop="topicTagId">-->
      <!--        <el-input v-model="dataForm.topicTagId" :disabled="disabled" placeholder="活动标签Id"></el-input>-->
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
        dataForm: {
          id: 0,
          title: '',
          content: '',
          avatar: '',
          itemPicUrl: '',
          subtitle: '',
          topicCategoryId: '',
          priceInfo: '',
          readCount: '',
          scenePicUrl: '',
          topicTemplateId: '',
          topicTagId: ''
        },
        dataRule: {
          name: [
            {
              required: true,
              message: '名称不能为空',
              trigger: 'blur'
            }
          ]
        },
        topicCategorys: []
      }
    },
    created () {
      this.$http({
        url: `/mall/topiccategory/queryAll`,
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.topicCategorys = data.list
        }
      })
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
              url: `/mall/topic/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm = data.topic
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
                url: `/mall/topic/${!this.dataForm.id ? 'save' : 'update'}`,
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
