<template>
  <div class="device-object">
    <div class="object-wrapper">
      <div class="btn-up">
        <button class="btn">固定资产设备</button>
      </div>
      <div class="btn-down">
        <button class="btn">非固定资产设备</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.device-object {
  height: 100%;
  padding: 3rem 2rem 0 2rem;
  .object-wrapper {
    text-align: center;
    display: flex;
    flex-direction: column;
    button {
      width: 80%;
      height: 7rem;
      background-color: rgb(4, 206, 4);
      font-size: 1.8rem;
    }
    .btn-down {
      margin-top: 3rem;
      button {
        background-color: orange;
      }
    }
  }
}
</style>
