<template>
  <!-- 用户列表组件 -->
  <div class="user-list">
    <ul>
      <li class="list-item"
          v-for="(item, index) in userList"
          :key="index">
        <div class="left">
          <div class="avatar">
            <img src="../assets/1.jpg"
                 alt="">
          </div>
          <div class="info">
            <span class="name">{{item.name}}</span>
            <span class="phone">{{item.phone}}</span>
          </div>
        </div>
        <!-- 后面的checkbox可以用watch来进行获取值，需要绑定一个数组 -->
        <div class="right"
             v-show="isMulti">
          <input type="checkbox"
                 name="user"
                 v-model="checkList"
                 :value="item.value">
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    userList: {
      type: Array
    },
    isMulti: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      checkList: [],
    }
  },
  watch: {
    checkList (newVal, oldVal) {
      console.log(newVal, oldVal)
    }
  },
}
</script>

<style scoped lang="scss">
.user-list {
  ul {
    .list-item {
      margin-bottom: 1rem;
      padding: 1rem 0;
      padding-left: 2rem;
      border-top: 0.1rem solid #eee;
      border-bottom: 0.1rem solid #eee;
      box-shadow: 0 0.3rem 0.5rem #eee;
      position: relative;
      .left {
        display: flex;
        flex-direction: row;
        .avatar {
          img {
            border-radius: 50%;
            width: 5rem;
            height: 5rem;
          }
        }
        .info {
          font-size: 1.4rem;
          padding: 1rem 0 0 1rem;
          display: flex;
          flex-direction: column;
          .name {
            color: #333;
          }
          .phone {
            margin-top: 0.5rem;
            font-size: 1.2rem;
            color: #999;
          }
        }
      }
      .right {
        position: absolute;
        right: 1rem;
        top: 2rem;
      }
    }
  }
}
</style>
