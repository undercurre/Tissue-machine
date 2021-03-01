<template>
  <div class="choose-person">
    <div class="header">
      <input type="text"
             class="header-input"
             placeholder="请输入用户名">
      <button class="header-btn btn">搜索</button>
    </div>
    <div class="content">
      <user-list :userList="userList"
                 :isMulti="isMulti"></user-list>
    </div>
    <div class="bottom"
         v-show="isMulti">
      <button class="btn">确定协助人</button>
    </div>
  </div>
</template>

<script>
import UserList from '../../components/UserList'
export default {
  data () {
    return {
      userList: [
        {
          name: '黄耿霖',
          phone: '123123123213',
          value: 'huang'
        },
        {
          name: '陈琪',
          phone: '123123123213',
          value: 'chen'
        },
        {
          name: '林威',
          phone: '123123123213',
          value: 'lin'
        },
        {
          name: '黄耿霖',
          phone: '123123123213',
          value: 'huang'
        },
        {
          name: '陈琪',
          phone: '123123123213',
          value: 'chen'
        },
        {
          name: '林威',
          phone: '123123123213',
          value: 'lin'
        },
        {
          name: '黄耿霖',
          phone: '123123123213',
          value: 'huang'
        },
        {
          name: '陈琪',
          phone: '123123123213',
          value: 'chen'
        },
        {
          name: '林威',
          phone: '123123123213',
          value: 'lin'
        },
      ],
      isMulti: true, // 是否可以多选
    }
  },
  components: {
    UserList
  },
  methods: {
  },
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.choose-person {
  .header {
    width: 100%;
    position: fixed;
    left: 0;
    top: 0;
    box-sizing: border-box;
    height: 6rem;
    padding: 1rem;
    display: flex;
    z-index: 5;
    background: #fff;
    .header-input {
      flex: 4;
      margin-right: 2rem;
      padding-left: 1rem;
      border: none;
      background-color: #eee;
      outline: none;
      border-radius: 0.5rem;
      color: #666;
    }
    .header-btn {
      flex: 1;
      background-color: #67c23a;
    }
  }
  .content {
    margin-top: 6rem;
    margin-bottom: 6rem;
  }
  .bottom {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    background-color: #fff;
    height: 6rem;
    text-align: center;
    button {
      width: 80%;
      margin-top: 1rem;
      height: 4rem;
      background-color: #67c23a;
      font-size: 1.4rem;
      font-weight: 700;
    }
  }
}
</style>
