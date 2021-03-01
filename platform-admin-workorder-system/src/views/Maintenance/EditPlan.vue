<template>
  <div class="edit-plan">
    <div class="edit-wrapper">
      <!-- 中间内容 -->
      <div class="edit-content">
        <el-form label-width="14rem"
                 label-position="left"
                 :model="formLabelAlign">
          <el-form-item label="设备名称：">
            <span class="item-info">西门水闸</span>
            <button class="btn item-btn">查看详情>></button>
          </el-form-item>
          <el-form-item label="活动区域：">
            <el-input v-model="formLabelAlign.region"
                      style="width: 8rem;"></el-input>
            <span class="day">天</span>
          </el-form-item>
          <el-form-item label="上次保养时间："
                        v-show="this.editType === '2'">
            <span class="item-info">2019年4月24日</span>
          </el-form-item>
          <el-form-item label="保养负责人：">
            <span class="item-info">黄耿霖</span>
            <button class="btn item-btn">选择负责人</button>
          </el-form-item>
          <el-form-item label="负责人电话：">
            <span class="item-info phone">15627236761</span>
          </el-form-item>
          <el-form-item label="开始时间：">
            <el-date-picker v-model="formLabelAlign.startTime"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            style="width:15rem;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间：">
            <el-date-picker v-model="formLabelAlign.endTime"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            style="width:15rem;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="状态：">
            <el-select v-model="formLabelAlign.status"
                       style="width:15rem;">
              <el-option label="启用"
                         value="启用"></el-option>
              <el-option label="停用"
                         value="停用"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <!-- 底部 -->
      <div class="edit-bottom">
        <div class="bottom-btn">
          <button class="btn">{{this.editType === '2' ? '确认修改' : '确认添加'}}</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      editType: '1', // 编辑类型，1为新增 2为编辑
      formLabelAlign: {
        name: '',
        region: '',
        type: '',
        startTime: '',
        endTime: '',
        status: '启用'
      },
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now();
        },
      }
    }
  },
  computed: {
  },
  methods: {
    // 下拉菜单
    handleCommand (command) {
      if (command === '1') {
        this.formLabelAlign.status = '启用'
      } else {
        this.formLabelAlign.status = '停用'
      }
    }
  }
}
</script>

<style  lang="scss">
@import "@/assets/style/global.scss";
.edit-plan {
  padding: 2rem 3rem 0 3rem;
  .edit-wrapper {
    .edit-content {
      .el-form {
        .el-form-item {
          margin-top: 1rem;
          margin-bottom: 1.5rem;
          .el-form-item__label {
            color: #000;
            font-weight: 700;
            font-size: 1.6rem;
          }
          .item-info {
            font-size: 1.6rem;
          }
          .day {
            margin-left: 1rem;
          }
          .el-input__inner {
            font-size: 1.6rem;
          }
          .item-btn {
            margin-left: 2rem;
            color: #fff;
            background-color: #409eff;
            height: 2.5rem;
            font-size: 1.2rem;
          }
        }
      }
      .el-picker-panel,
      .el-date-picker,
      .el-popper {
        width: 20rem;
        height: 20rem;
      }
    }
    .edit-bottom {
      margin-top: 5rem;
      left: 3rem;
      width: 100%;
      .bottom-btn {
        button {
          width: 100%;
          height: 4rem;
          background-color: rgb(10, 211, 10);
          font-size: 1.6rem;
          font-weight: 700;
        }
      }
    }
  }
}
</style>
