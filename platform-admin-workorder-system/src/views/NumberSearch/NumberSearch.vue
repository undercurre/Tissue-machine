<template>
  <div class="number-search">
    <div class="search-wrapper">
      <div class="wrapper-input">
        <input type="text"
               placeholder="请输入设备编号"
               v-model="deviceNumber">
      </div>
      <div class="wrapper-icon">
        <a href="#">
          <i class="iconfont icon-iconfontscan"></i>
        </a>
      </div>
    </div>
    <div class="search-btn">
      <div class="btn-left">
        <button class="btn">清除内容</button>
      </div>
      <div class="btn-right">
        <button class="btn">查看设备</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      deviceNumber: '', // 设备编号
    }
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.number-search {
  margin-top: 2rem;
  padding: 2rem;
  .search-wrapper {
    position: relative;
    .wrapper-input {
      width: 100%;
      height: 5rem;
      line-height: 5rem;
      border-radius: 0.5rem;
      background-color: #eee;
      input {
        height: 4rem;
        width: 80%;
        border: none;
        padding: 0 2rem;
        font-size: 1.6rem;
        outline-style: none;
        color: #999;
        background-color: #eee;
      }
    }
    .wrapper-icon {
      position: absolute;
      right: 0;
      top: 0;
      background-color: rgba(245, 209, 7, 0.801);
      width: 5rem;
      height: 5rem;
      line-height: 5rem;
      text-align: center;
      border-radius: 0.5rem;
      a {
        color: #333;
        i {
          font-size: 3rem;
          font-weight: bold;
        }
      }
    }
  }
  .search-btn {
    margin-top: 3rem;
    display: flex;
    flex-direction: row;
    button {
      width: 12rem;
      height: 4rem;
      font-weight: 700;
    }
    .btn-left {
      flex: 1;
      @include center();
      width: 40%;
      button {
        background-color: rgb(207, 0, 0);
      }
    }
    .btn-right {
      flex: 1;
      width: 40%;
      @include center();
      button {
        background-color: rgb(6, 175, 6);
      }
    }
  }
}
</style>
