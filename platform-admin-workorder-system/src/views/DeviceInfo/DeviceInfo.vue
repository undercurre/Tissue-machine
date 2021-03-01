<template>
  <div class="deviceinfo">
    <div class="device-content">
      <div class="device-wrapper">
        <ul class="info-list">
          <li class="list-item">
            <span class="item-left">设备编号:</span>
            <span class="item-right">550114011</span>
          </li>
          <li class="list-item">
            <span class="item-left">设备名称:</span>
            <span class="item-right">西门水闸</span>
          </li>
          <li class="list-item">
            <span class="item-left">设备类别:</span>
            <span class="item-right">电气设备</span>
          </li>
          <li class="list-item">
            <span class="item-left">设备规格:</span>
            <span class="item-right">800L</span>
          </li>
          <li class="list-item">
            <span class="item-left">资产类型:</span>
            <span class="item-right">固定资产</span>
          </li>
          <li class="list-item">
            <span class="item-left">所属部门:</span>
            <span class="item-right">生产部</span>
          </li>
          <li class="list-item">
            <span class="item-left">当前状态:</span>
            <span class="item-right">正常运行</span>
          </li>
          <li class="list-item">
            <span class="item-left">上次维修时间:</span>
            <span class="item-right">2019年4月6日
            </span>
          </li>
          <li class="list-item">
            <span class="item-left">上次保养时间:</span>
            <span class="item-right">2019年4月24日</span>
          </li>
          <li class="list-item">
            <span class="item-left">保养负责人:</span>
            <span class="item-right">黄耿霖</span>
          </li>
          <li class="list-item">
            <span class="item-left">负责人电话:</span>
            <span class="item-right phone">
              <a href="tel:15627236761">
                15627236761
              </a>
            </span>
          </li>
          <li class="list-item">
            <span class="item-left">保养周期:</span>
            <span class="item-right">180天</span>
          </li>
          <li class="list-item">
            <span class="item-left">设备附件:</span>
            <div class="item-right appendix">
              <a href="#">
                <i class="iconfont icon-PDF"></i>
                <span>说明书</span>
              </a>
              <a href="#">
                <i class="iconfont icon-word"></i>
                <span>保修书</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
      <div class="device-bottom"
           v-show="permission">
        <div class="bottom-content">
          <router-link to=""
                       class="maintenance">保养计划</router-link>
          <router-link to=""
                       class="recording">工单记录</router-link>
          <router-link to=""
                       class="create">创建工单</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      permission: true, // 权限，控制底部是否显示
    }
  }
}
</script>

<style scoped lang="scss">
.deviceinfo {
  // margin-top: 10px;
  padding: 2rem 2rem 2rem 3rem;
  position: relative;
  .device-content {
    .device-wrapper {
      margin-bottom: 10rem;
      .info-list {
        .list-item {
          height: 4rem;
          display: flex;
          line-height: 4rem;
          font-size: 1.6rem;
          .item-left {
            flex: 2;
            // font-weight: bold;
          }
          .item-right {
            flex: 3;
          }
          .appendix {
            margin-top: 1rem;
            a {
              // display: flex;
              // flex-direction: column;
              color: #000;
              i {
                font-size: 4rem;
                fill: currentColor;
              }
              span {
                font-size: 1.4rem;
              }
            }
          }
        }
      }
    }
    .device-bottom {
      width: 100%;
      height: 6rem;
      background-color: #fff;
      position: fixed;
      bottom: 0rem;
      padding-bottom: 1rem;
      box-sizing: border-box;
      left: 0;
      .bottom-content {
        display: flex;
        padding: 0 1rem;
        a {
          flex: 1;
          display: block;
          width: 100%;
          height: 100%;
          line-height: 5rem;
          text-align: center;
          color: #fff;
          font-size: 1.6rem;
          font-weight: bold;
          border-radius: 0.5rem;
          &.maintenance {
            background: yellow;
            color: #000;
          }
          &.recording {
            background-color: rgb(36, 223, 36);
            margin: 0 2rem;
          }
          &.create {
            background-color: rgb(180, 2, 2);
          }
        }
      }
    }
  }
}
</style>
