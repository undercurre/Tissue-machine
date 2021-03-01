<template>
  <div class="title-bar">
    <div class="title">
      {{this.title}}
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String
    }
  },
  mounted () {
    // console.log(this.title)
  },
}
</script>

<style scoped lang="scss">
.title-bar {
  .title {
    width: 8rem;
    height: 2.5rem;
    background-color: #eee;
    font-size: 1.3rem;
    line-height: 3rem;
    border-top-left-radius: 0.5rem;
    border-top-right-radius: 0.5rem;
    text-align: center;
  }
  .bottom {
    border: 0.2rem solid #eee;
  }
}
</style>
