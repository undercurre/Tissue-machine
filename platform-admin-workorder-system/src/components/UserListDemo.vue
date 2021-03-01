<template>
  <!-- 用户列表组件 -->
  <div class="user-list">
    <ul>
      <li class="list-item"
          v-for="(item, index) in UserList"
          :key="index"  @click="OnClickItem(item.name)">
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

        <div class="right"
             >
          <input type="radio"
                 name="user"
                 v-model="pick"
                 :value=item.name
                >
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
  },
  data () {
    return {
        UserList: this.userList,
      pick:'',
    }
  },
  watch:{
      pick:function(val){
          console.log("1变化watch",val);
          console.log("2变化后传值",val);
        this.$emit("pick-radio",val);
      }
  },
  methods: {
      OnClickItem(val){
        this.pick = val;

        console.log("点击选项pick",this.pick);
      },
      Change(val){
         console.log("2变化后传值",val);
        this.$emit("pick-radio",val);
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
