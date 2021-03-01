<template>
  <div class="menu_container">
    <img class="bg" src="../OrderMenu/bg.png">
    <div class="header bgw">
        <img class="avatar" src="../OrderMenu/avatar.png">
        <p>{{ name }}的菜单管理系统</p>
    </div>
    <div class="myOrder bgw">
      <div class="title">
        <span>我的工单</span>
      </div>
      <div class="content">
        <div @click="toQuery(3)">
          <img src="../OrderMenu/setup.png">
          <p>创建的工单</p>
        </div>
        <div @click="toQuery(2)">
          <img src="../OrderMenu/send.png">
          <p>分配的工单</p>
        </div>
      </div>
    </div>
    <div class="manager bgw">
      <div class="title">
        <span>工单管理</span>
      </div>
      <div class="content">
        <div @click="toCreate">
          <img src="../OrderMenu/new.png">
          <p>新建工单</p>
        </div>
        <div @click="toQuery(1)">
          <img src="../OrderMenu/his.png">
          <p>历史工单</p>
        </div>
        <div @click="toQuery(0)">
          <img src="../OrderMenu/query.png">
          <p>查询工单</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $http from "../../utils/http";


export default {
  data() {
    return {
      name: ''
    }
  },
  created() {
    console.log(this.$route.query.token)
    this.$cookie.set('token',this.$route.query.token)
    this.addInterceptors()
    this.getName()
  },
  mounted() {
    this.initComCss()
  },
  methods:{
    initComCss(){
      document.body.style.backgroundColor="#F5F5F5";
    },
    toQuery(type){
      this.$router.push({ name: 'orderQuery',query:{ type: type}})
    },
    toCreate(){
      this.$router.push({ name: 'CreateOrder'})
    },
    addInterceptors() {
      // 请求拦截器
      this.axios.interceptors.request.use(
          config => {
            config.headers['token'] = this.$cookie.get('token')// 请求头带上
            return config
          },
          error => {
            return Promise.reject(error)
          }
      )
    },
    getName(){
      this.axios.get( process.env.VUE_APP_API_URL+"/sys/user/info").then( res => {
        if (res.data.code === 0)
        {
          this.name = res.data.user.realName
          this.$cookie.set('userId',res.data.user.userId)
        }else{
          console.log("用户数据获取失败")
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.menu_container{
  color: #333333;
  .bg{
    width: 100vw;
    position: absolute;
    top: 0;
    z-index: -99999;
  }
  .bgw{
    background-color: #ffffff;
  }
  .header,.myOrder,.manager{
    margin: 6vw;
    border-radius: 5px;
  }
  .title{
    border-bottom: 1px solid #CCCCCC;
    font-size: 15px;
    font-weight: bold;
    padding: 7px 10px;
  }
  .content{
    display: flex;
    justify-content: space-around;
    div{
      display: flex;
      flex-direction: column;
      img{
        width: 30px;
        height: 36px;
        margin: 10px auto;
      }
      p{
        text-align: center;
        margin-bottom: 10px;
      }
    }
  }
  .header{
    display: flex;
    flex-direction: column;
    margin-top: 16vh;
    .avatar{
      width: 76px;
      position: absolute;
      left: 50%;
      transform: translateX(-38px) translateY(-38px);
    }
    p{
      text-align: center;
      margin-top: 58px;
      margin-bottom: 20px;
      font-size: 20px;
      font-weight: bolder;
    }
  }
}
</style>
