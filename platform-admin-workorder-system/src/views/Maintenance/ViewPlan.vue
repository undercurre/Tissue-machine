<template>
  <div class="maintenance-plan">
    <div class="plan-wrapper">
      <div class="plan-content">
        <ul class="info-list">
          <li class="list-item"
              v-for="(item, index) in dataList"
              :key="index">
            <span class="item-left">{{item.deviceInfo}}:</span>
            <span class="item-right"
                  :class="item.Corresponding === 'phone' ? 'phone' : ''">{{item.val}}</span>
            <!-- 第一个显示按钮 -->
            <button class="btn item-btn"
                    v-show="index === 0">查看详情>></button>
          </li>
        </ul>
      </div>
      <div class="plan-bottom">
        <!-- 查看的btn -->
        <div class="view">
          <div class="btn-up"
               v-if="hasPlan">
            <button class="btn create">创建计划</button>
          </div>
          <div class="btn-down"
               v-else>
            <button class="btn trans">修改计划</button>
            <button class="btn start">启用计划</button>
            <button class="btn stop">停用计划</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      hasPlan: true, // 是否有计划,
      dataList: [
        {
          deviceInfo: '设备名称', // 左侧显示的设备信息
          Corresponding: 'name', // 对应的数据库获取的数据的键值
          val: '' //上面的键值对应的数据库获取的数据信息的值
        },
        {
          deviceInfo: '保养周期',
          Corresponding: 'cycle',
          val: ''
        },
        {
          deviceInfo: '上次保养时间',
          Corresponding: 'lastTime',
          val: ''
        },
        {
          deviceInfo: '保养负责人',
          Corresponding: 'head',
          val: ''
        },
        {
          deviceInfo: '负责人电话',
          Corresponding: 'phone',
          val: ''
        },
        {
          deviceInfo: '设备投入时间',
          Corresponding: 'startTime',
          val: ''
        },
        {
          deviceInfo: '计划状态',
          Corresponding: 'status',
          val: ''
        },
      ],
      // mock的数据
      mockData: {
        name: '西门水闸',
        cycle: '180天',
        lastTime: '2019年4月24日',
        head: '黄耿霖',
        phone: '15627236761',
        startTime: '2009年4月24日',
        status: '进行中'
      }
    }
  },
  methods: {
    initData () {
      // 将数据赋给dataList
      this.dataList.forEach(item => {
        if (Object.keys(this.mockData).includes(item.Corresponding)) {
          let temp = item.Corresponding
          item.val = this.mockData[temp]
          // console.log(item)
        }
      })
    }
  },
  mounted () {
    this.initData()
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.maintenance-plan {
  padding: 2rem 3rem 0 3rem;
  .plan-wrapper {
    .plan-content {
      .info-list {
        .list-item {
          height: 4rem;
          margin-top: 1rem;
          position: relative;
          .item-btn {
            position: absolute;
            top: 0.5rem;
            right: 0;
            width: 8rem;
            height: 3rem;
            font-size: 1.2rem;
            color: #000;
            background-color: rgb(36, 231, 245);
          }
          .phone {
            color: rgb(11, 144, 252);
          }
        }
      }
    }
    .plan-bottom {
      margin-top: 3rem;
      box-sizing: border-box;
      width: 100%;
      padding: 0 2rem;
      height: 16rem;
      .view {
        .btn-up {
          line-height: 8rem;
          height: 6rem;
          .create {
            font-weight: 700;
            width: 100%;
            font-size: 1.4rem;
            height: 4rem;
            background-color: rgb(5, 168, 5);
          }
        }
        .btn-down {
          display: flex;
          justify-content: center;
          align-items: center;
          height: 8rem;
          line-height: 8rem;
          button {
            height: 4rem;
            font-weight: 700;
          }
          .stop {
            flex: 1;
            background: rgb(184, 6, 6);
          }
          .start {
            flex: 1;
            margin: 0 2rem;
            background: rgb(230, 230, 32);
            color: #000;
          }
          .trans {
            flex: 1;
            background-color: rgb(5, 168, 5);
          }
        }
      }
    }
  }
}
</style>
