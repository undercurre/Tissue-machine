<template>
  <!-- 用户列表组件 -->
  <el-radio-group v-model="pick" @change="Change">
    <ul class="UiWidth">
      <li class="list-item" v-for="(item, index) in UserList" :key="index">
        <el-radio :label="item.name">
          <div class="Item">
            <div class="left">
              <div class="avatar">
                <img src="../assets/1.jpg" alt="" />
              </div>
              <div class="info">
                <span class="name">{{ item.name }}</span>
                <span class="phone">{{ item.phone }}</span>
              </div>
            </div>
          </div>
        </el-radio>
      </li>
    </ul>
  </el-radio-group>
</template>

<script>
export default {
  name: "user-list",
  props: {
    userList: {
      type: Array,
    },
  },
  data() {
    return {
      UserList: this.userList,
      pick: "",
      radio: "",
    };
  },
  watch: {
    pick: function (val) {
      console.log("1变化watch", val);
    },
  },
  methods: {
    Change(){
      console.log("2传值",this.pick)
      this.$emit('pick-radio',this.pick);
    }
  },
};
</script>

<style scoped lang="scss">
::v-deep.el-radio-group {
  width: 100%;
  .UiWidth {
    width: 100%;
    .list-item {
      margin-bottom: 1rem;
      padding: 1rem 0;
      padding-left: 2rem;
      border-top: 0.1rem solid #eee;
      border-bottom: 0.1rem solid #eee;
      box-shadow: 0 0.3rem 0.5rem #eee;
      .el-radio {
        display: flex;
        flex-direction: row-reverse;
        justify-content: space-between;
        .el-radio__input {
          display: flex;
          align-items: center;
        }
        .el-radio__label {
          display: block;
          .Item {
            .left {
              display: flex;
              .avatar {
                img {
                  border-radius: 50%;
                  width: 5rem;
                  height: 5rem;
                }
              }
              .info {
                display: flex;
                flex-direction: column;
                justify-content: center;
              }
            }
          }
        }
      }
    }
  }
}
</style>
