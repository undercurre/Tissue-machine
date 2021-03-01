<template>
  <div class="Content">
    <div class="PicAndTxt">
      <img src="https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/Backlogo3xZpq.png"
           alt="" class="Pic">
      <div class="name">便民公益纸巾</div>
      <div class="version">{{ version }}</div>
    </div>
    <div class="bottomTxt">
      <div class="phone">客服电话：0757-28900001</div>
      <div class="technology">乐晟科技  提供技术支持</div>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        version: '1.0'
      }
    }
  }
</script>

<style lang="scss" scoped>
.Content {
  width: 100%;
  height: calc(100vh - 52px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  .PicAndTxt {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 10vh 0 0;
    .Pic{
      max-width: 300px;
      width: 30vw;
    }
    .name {
      font-size: 14px;
      color: #333;
      line-height: 1.8;
      margin: 20px 0 15px;
      font-family: "PingFang SC";
      font-weight: 600;
    }
    .version {
      color: #999;
      font-size: 12px;
      font-family: "PingFang SC";
    }
  }
  .bottomTxt{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 20px;
    left: 0;
    width: 100%;
    .phone,.technology {
      color: #333;
      font-size: 12px;
      font-weight: 600;
      font-family: "PingFang SC";
      margin: 0 0 30px;
    }
    .technology {
      margin: 0;
    }
  }
}
</style>
