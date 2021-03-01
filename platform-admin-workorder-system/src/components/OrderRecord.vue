<template>
  <!-- 传入不同的type和data来使用 -->
  <div class="order-record">
    <router-link to="/deviceinfo?id=1"
                 class="record-wrapper">
      <div class="record-header">
        <span class="header">{{list.title}}</span>
        <span class="time">{{list.time}}</span>
      </div>

      <!-- 提醒记录 -->
      <div class="record-content"
           v-if="this.recordType === 1">
        <div class="content-list">
          <ul class="list">
            <li class="list-item">
              <span>紧急程度:</span>
              <span>{{list.degree}}</span>
            </li>
            <li class="list-item">
              <span>设备编号:</span>
              <span>{{list.number}}</span>
            </li>
            <li class="list-item">
              <span>设备名称:</span>
              <span>{{list.name}}</span>
            </li>
            <li class="list-item">
              <span>工单编号:</span>
              <span>{{list.describe}}</span>
            </li>
            <li class="list-item">
              <span>报单时间:</span>
              <span>{{list.time}}</span>
            </li>
          </ul>
        </div>
        <div class="details">
          <a href="#">详情</a>
        </div>
      </div>

      <!-- 工单记录 -->
      <div class="record-content"
           v-else>
        <div class="content-list">
          <ul class="list">
            <li class="list-item">
              <span>工单状态:</span>
              <span>{{list.orderType}}</span>
            </li>
            <li class="list-item">
              <span>工单类型:</span>
              <span>{{list.orderStatus}}</span>
            </li>
            <li class="list-item">
              <span>工单描述:</span>
              <span>{{list.describe}}</span>
            </li>
            <li class="list-item">
              <span>被指派人:</span>
              <span>{{list.worker}}</span>
            </li>
          </ul>
        </div>
        <div class="details">
          <a href="#">详情</a>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  data () {
    return {
      list: []
    }
  },
  props: {
    // 记录的类型 1为提醒记录 2为工单记录
    recordType: {
      type: Number,
      default: 2
    },
    recordData: {
      type: Object
    }
  },
  methods: {
    initRecordData () {
      this.list = this.recordData
      // console.log(this.list)
    }
  },
  mounted () {
    this.initRecordData()
  }
}
</script>

<style scoped lang="scss">
.order-record {
  padding: 0 2rem;
  margin-top: 2rem;
  .record-wrapper {
    display: block;
    padding: 0 2rem 0.2rem 2rem;
    border-radius: 0.5rem;
    color: #000;
    background-color: #eee;
    .record-header {
      box-sizing: border-box;
      height: 5rem;
      display: flex;
      flex-direction: column;
      padding-top: 1rem;
      .header {
        font-weight: 700;
        font-size: 1.6rem;
      }
      .time {
        font-size: 1.2rem;
        color: #aaa;
        margin-top: 0.5rem;
      }
    }
    .record-content {
      .content-list {
        .list {
          .list-item {
            height: 2.5rem;
            font-size: 1.4rem;
            line-height: 2.5rem;
            color: #333;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            span {
              &:last-child {
                margin-left: 0.5rem;
              }
            }
          }
        }
      }
      .details {
        margin: 1rem 0;
        border-top: 0.1rem solid #ccc;
        a {
          display: block;
          box-sizing: border-box;
          height: 2rem;
          font-size: 1.4rem;
          color: rgb(110, 124, 250);
          margin-top: 0.5rem;
        }
      }
    }
  }
}
</style>
