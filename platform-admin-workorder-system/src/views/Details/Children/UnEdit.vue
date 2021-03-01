<template>
  <div class="un-edit"
       v-show="this.type === 'daizhipai' ? false : true">
    <title-bar title="进度信息"></title-bar>
    <div class="un-wrapper">
      <ul class="info-list">
        <!-- 一定有的信息 -->
        <div class="common">
          <li class="list-item">
            <span class="item-left">被指派人:</span>
            <span class="item-right">黄耿霖</span>
          </li>
          <li class="list-item">
            <span class="item-left">指派人电话:</span>
            <span class="item-right">
              <a href="tel:"
                 class="phone">15627236761</a>
            </span>
          </li>
          <li class="list-item">
            <span class="item-left">工单协助人:</span>
            <span class="item-right">刘建委，梁文浩，梁盛达</span>
          </li>
        </div>
        <li class="list-item"
            v-show="this.type === 'yijiedan'">
          <span class="item-left">结单时间:</span>
          <span class="item-right">2019年4月24日</span>
        </li>
        <li class="list-item"
            v-show="this.type !== 'yizuofei'">
          <span class="item-left">紧急程度:</span>
          <span class="item-right">普通</span>
        </li>
        <!-- 已结单、待返工、待结单、处理中、等待配件 -->
        <div class="other"
             v-show="this.type !== 'daichuli' && this.type !== 'yizuofei'">
          <li class="list-item"
              v-show="this.type !== 'chulizhong' && this.type !== 'daipeijian'">
            <span class="item-left">工单检查人:</span>
            <span class="item-right">任老师</span>
          </li>
          <li class="list-item">
            <span class="item-left">维修人描述:</span>
            <span class="item-right">确实坏了，问题已经解决，请放心使用。</span>
          </li>
          <li class="list-item"
              v-show="(this.type !== 'daijiedan') && (this.type !== 'chulizhong') && this.type !== 'daipeijian'">
            <span class="item-left">检查人描述:</span>
            <span class="item-right">检查过了，黄工已经修复。可以结单。</span>
          </li>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import TitleBar from '../../../components/TitleBar'
export default {
  data () {
    return {
      type: 'daizhipai', // 工单状态 已结单 待返工 待结单 处理中 等待配件 待处理 待指派 已作废
    }
  },
  components: {
    TitleBar
  }
}
</script>

<style scoped lang="scss">
.un-edit {
  .un-wrapper {
    margin-top: 1.5rem;
    .info-list {
      .list-item {
        line-height: 3rem;
        .item-left {
          text-align: center;
        }
      }
      .other {
        margin-top: 1rem;
      }
    }
  }
}
</style>
