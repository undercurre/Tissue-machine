<template>
  <div class="detalis">
    <div class="details-wrapper">
      <div class="basic-info">
        <title-bar title="基础信息"></title-bar>
        <ul class="info-list">
          <li class="list-item">
            <span class="item-left center">设备名称:</span>
            <span class="item-right">西门水闸</span>
            <button class="btn item-btn">查看详情>></button>
          </li>
          <li class="list-item">
            <span class="item-left center">工单类型:</span>
            <span class="item-right">维修工单</span>
          </li>
          <li class="list-item">
            <span class="item-left center">工单状态:</span>
            <span class="item-right">已结单</span>
          </li>
          <li class="list-item">
            <span class="item-left center">报单时间:</span>
            <span class="item-right">2019年4月6日</span>
          </li>
          <li class="list-item">
            <span class="item-left center">工单描述:</span>
            <span class="item-right">水闸坏了，一直漏水，而且漏电，相当吓人。
            </span>
          </li>
          <li class="list-item">
            <span class="item-left center">设备位置:</span>
            <span class="item-right">佛山大学惠天楼</span>
          </li>
          <li class="list-item">
            <span class="item-left center">报单人:</span>
            <span class="item-right">曾今儒</span>
          </li>
          <li class="list-item">
            <span class="item-left center">报单人电话:</span>
            <span class="item-right ">
              <a href="tel:"
                 class="phone">15627236761</a>
            </span>
          </li>
          <li class="list-item">
            <span class="item-left center">维修班长:</span>
            <span class="item-right">郭兆星</span>
          </li>
          <li class="list-item">
            <span class="item-left center">工单附件:</span>
            <span class="item-right">
              <div class="item-img">
                <img src=""
                     alt="">
              </div>
              <div class="item-img">
                <img src=""
                     alt="">
              </div>
            </span>
          </li>
          <li class="list-item">
            <span class="item-left center">班长描述:</span>
            <div class="item-right">
              <textarea cols="30"
                        rows="5"
                        placeholder="请输入情况描述，可为空"></textarea>
            </div>

          </li>
        </ul>
      </div>
      <div class="progress-info">
        <!-- <title-bar title="进度信息"></title-bar> -->
        <div class="children">
          <router-view></router-view>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import TitleBar from '../../components/TitleBar'
export default {
  data () {
    return {
    }
  },
  components: {
    TitleBar
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.detalis {
  .details-wrapper {
    .basic-info {
      padding: 2rem;
      padding-bottom: 0;
      .info-list {
        .list-item {
          position: relative;
          margin-top: 1rem;
          line-height: 3rem;
          .item-right {
            .item-img {
              display: inline-block;
              margin-right: 1.5rem;
              width: 6rem;
              height: 6rem;
              background-color: pink;
            }
            textarea {
              width: 15rem;
              background-color: #eee;
              border: none;
              outline: none;
              padding: 1rem;
              box-sizing: border-box;
              height: 8rem;
              border-radius: 0.5rem;
              font-size: 1.2rem;
              color: #333;
            }
          }
          .center {
            text-align: center;
          }
          .item-btn {
            position: absolute;
            right: 1rem;
            top: 0.5rem;
            background-color: #409eff;
            font-size: 1.2rem;
            height: 2rem;
            line-height: 2rem;
          }
        }
      }
    }
    .progress-info {
      padding: 2rem;
      .children {
        // margin-top: 1rem;
      }
    }
  }
}
</style>
