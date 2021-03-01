<template>
  <div class="edit">
    <title-bar title="进度信息"></title-bar>
    <el-form ref="form"
             :model="sizeForm"
             label-width="10rem"
             size="mini">
      <!-- 都有的通用item -->
      <el-form-item label="被指派人:">
        <el-input v-model="sizeForm.name"
                  style="width:12rem;"
                  disabled></el-input>
        <el-button style="margin-left: 2rem;"
                   type="primary"
                   v-show="this.type === 'daizhipai' ? true : false">选择指派人</el-button>
      </el-form-item>
      <el-form-item label="指派人电话:">
        <a href="tel:15627236761"
           class="phone">15627236761</a>
      </el-form-item>
      <el-form-item label="工单协助人:">
        <el-input v-model="sizeForm.name"
                  style="width:12rem;"
                  type="textarea"
                  disabled></el-input>
        <el-button style="margin-left: 2rem; margin-bottom: 2rem;"
                   type="primary"
                   v-show="this.type === 'daizhipai' ? true : false">选择协助人</el-button>
      </el-form-item>
      <el-form-item label="紧急程度:">
        <el-radio-group v-model="sizeForm.radio">
          <el-radio :label="1"
                    :disabled="this.type === 'daizhipai' ? false : true">普通</el-radio>
          <el-radio :label="2"
                    :disabled="this.type === 'daizhipai' ? false : true">优先</el-radio>
          <el-radio :label="3"
                    :disabled="this.type === 'daizhipai' ? false : true">紧急</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 待返工 -->
      <div class="wating-rework"
           v-show="(this.type === 'daifangong' || this.type === 'chulizhong' || this.type === 'daijiedan') ? true : false">
        <el-form-item label="工单检查人:">
          <el-input v-model="sizeForm.name"
                    style="width:12rem;"
                    disabled></el-input>
          <el-button style="margin-left: 2rem;"
                     type="primary"
                     v-show="this.type === 'chulizhong'">选择检查人</el-button>
        </el-form-item>
        <el-form-item label="维修人描述:">
          <el-input v-model="sizeForm.name"
                    style="width:20rem;"
                    type="textarea"
                    disabled></el-input>
        </el-form-item>
        <el-form-item label="检查人描述:"
                      v-show="this.type === 'chulizhong' ? false : true">
          <el-input v-model="sizeForm.name"
                    style="width:20rem;"
                    type="textarea"
                    disabled></el-input>
        </el-form-item>
        <el-form-item label="保养情况:"
                      v-show="this.type === 'chulizhong' ? true : false">
          <el-checkbox v-model="sizeForm.checked">顺便做了保养</el-checkbox>
        </el-form-item>
      </div>
      <!-- 底下按钮 -->
      <div class="form-btn"
           style="text-align: center;">
        <!-- 指派的按钮 -->
        <div class="daizhipai"
             v-show="this.type === 'daizhipai' ? true : false">
          <el-button type="danger"
                     size="medium"
                     style="width:10rem;">作废</el-button>
          <el-button type="success"
                     size="medium"
                     style="width:10rem;">确认指派</el-button>
        </div>
        <!-- 待处理 的按钮 -->
        <div class="daichuli daifangong daipeijian"
             v-show="this.type === 'daichuli' || this.type === 'daipeijian' || this.type === 'daifangong'">
          <el-button type="success"
                     style="width: 20rem;">{{greenbtn}}</el-button>
        </div>
        <!-- 处理中的按钮 -->
        <div class="chulizhong"
             v-show="this.type === 'chulizhong'">
          <el-button type="warning"
                     size="small">等待配件</el-button>
          <el-button type="success"
                     size="small">完成修复</el-button>
          <el-button type="danger"
                     size="small">停用并完成修复</el-button>
        </div>
        <!-- 待结单的按钮 -->
        <div class="daijiedan"
             v-show="this.type === 'daijiedan'">
          <el-button type="danger"
                     size="small">检查不通过</el-button>
          <el-button type="success"
                     size="small">通过检查并结单</el-button>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import TitleBar from '../../../components/TitleBar'
export default {
  data () {
    return {
      type: 'daizhipai', // 七种状态 待指派 待处理 等待配件 待返工 处理中 待结单 已作废
      sizeForm: {
        name: '黄耿霖',
        radio: 1,
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        checked: ''
      },
    }
  },
  components: {
    TitleBar

  },
  watch: {

  },
  computed: {
    greenbtn: function () {
      let val
      if (this.type === 'daichuli') {
        val = '开始处理'
      } else if (this.type === 'daipeijian' || this.type === 'daifangong') {
        val = '继续处理'
      }
      return val
    }
  },
  methods: {
    onSubmit () {
      console.log('submit!');
    }
  }
}
</script>

<style lang="scss">
@import "@/assets/style/global.scss";
.edit {
  .el-form {
    .el-form-item__label {
      font-size: 1.6rem;
    }
    .el-input__inner {
      font-size: 1.4rem;
    }
    .el-textarea__inner {
      font-size: 1.4rem;
    }
    margin-top: 1.5rem;
  }
}
</style>
