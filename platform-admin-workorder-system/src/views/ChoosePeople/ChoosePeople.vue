<template>
  <el-dialog
    title="提示"
    class="overDialog"
    :visible.sync="visible"
    append-to-body
    :fullscreen="visible"
  >
  <div class="choose-person">
    <div class="header">
      <input type="text"
             class="header-input"
             placeholder="请输入用户名">
      <button class="header-btn btn">搜索</button>
    </div>
    <div class="content">
      <user-list :userList="userList"
        v-on:pick-radio="PickRadio"></user-list>
    </div>
    <div class="bottom"
        >
      <button class="btn" @click="Confirm">确定协助人</button>
    </div>
  </div>

  </el-dialog>

</template>

<script>
import UserList from '../../components/UserListDemo2'
export default {
  data () {
    return {
        pickValue:'',
      userList: [
        {
          name: '黄耿霖1',
          phone: '123123123213',
          value: 'huang'
        },
        {
          name: '陈琪1',
          phone: '123123123213',
          value: 'chen'
        },
        {
          name: '林威1',
          phone: '123123123213',
          value: 'lin'
        },
        {
          name: '黄耿霖2',
          phone: '123123123213',
          value: 'huang2'
        },
        {
          name: '陈琪2',
          phone: '123123123213',
          value: 'chen2'
        },
        {
          name: '林威2',
          phone: '123123123213',
          value: 'lin2'
        },
        {
          name: '黄耿霖3',
          phone: '123123123213',
          value: 'huang3'
        },
        {
          name: '陈琪3',
          phone: '123123123213',
          value: 'chen3'
        },
        {
          name: '林威3',
          phone: '123123123213',
          value: 'lin3'
        },
      ],
      visible: false,
      /* isMulti: true, */ // 是否可以多选
    }
  },
  components: {
    UserList
  },
  methods: {
    init() {
      this.visible = true;
    },
    Confirm() {
      this.visible = false;
      console.log("4确认再发送from ChoosePeople",this.pickValue)
      this.$emit("reForm",this.pickValue);
    },
      PickRadio:function(val){
        console.log("3接收from UserlistDemo",val)
           this.pickValue = val
      },

      // Confirm:function(){
      //    this.$router.push({name:'demoZpq3', query:{pickPerson:this.pickValue}});
      // },
  },
}
</script>

<style scoped lang="scss">
@import "@/assets/style/global.scss";
.overDialog ::v-deep {
  .el-dialog {
    width: 100%;
    margin: 0 !important;
    .el-dialog__header {
      display: none;
    }
  }
}



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
