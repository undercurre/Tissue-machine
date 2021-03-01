<template>
  <div class="create-order">
    <div class="form-wrapper">
      <el-form label-width="10rem"
               label-position="left"
               :model="formLabelAlign">
        <el-form-item label="设备名称:">
          <!-- 固定资产时候直接获取 -->
          <div class="fixed"
               v-if="isFixedAssets">
            <input type="text"
                   class="fixed-device-name"
                   v-model="formLabelAlign.name"
                   disabled>
            <button class="fixed-btn btn">查看详情>></button>
          </div>
          <!-- 非固定资产手填 -->
          <div class="notfixed"
               v-else>
            <el-input v-model="formLabelAlign.name"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="工单类型:"
                      v-show="isFixedAssets">
          <el-radio-group v-model="formLabelAlign.radio">
            <el-radio :label="3">维修</el-radio>
            <el-radio :label="6">保养</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工单描述:">
          <el-input v-model="formLabelAlign.type"
                    type="textarea"
                    rows="5"
                    maxlength="500"
                    placeholder="工单描述，500字以内"></el-input>
        </el-form-item>
        <el-form-item label="设备位置:">
          <el-input v-model="formLabelAlign.type"
                    type="textarea"
                    rows="3"
                    maxlength="100"
                    placeholder="设备位置，100字以内"></el-input>
        </el-form-item>
        <el-form-item label="附件:">
          <div class="upload">
            <el-upload class="upload-demo"
                       action="https://jsonplaceholder.typicode.com/posts/"
                       :on-preview="handlePreview"
                       :on-remove="handleRemove"
                       multiple
                       :limit="3"
                       :on-exceed="handleExceed"
                       :file-list="fileList">
              <el-button size="small"
                         type="primary">点击上传</el-button>
              <div slot="tip"
                   class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </div>
        </el-form-item>
        <div class="form-submit">
          <button class="btn">创建工单</button>
          <div class="tips">注：工单将推送给对应部门的设备负责人</div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isFixedAssets: true, // 是否是固定资产，默认为是
      formLabelAlign: {
        name: '西门水闸',
        region: '',
        type: '',
        radio: ''
      },
      fileList: [], // 上传的文件列表
    }
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file, fileList);
    },
    handlePreview (file) {
      console.log(file);
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
  }
}
</script>

<style lang="scss">
@import "@/assets/style/global.scss";
.create-order {
  padding: 3rem 2rem 0 2rem;
  .form-wrapper {
    .el-form {
      .el-form-item__label {
        // font-weight: 700;
        font-size: 1.6rem;
        color: #000;
      }
      .el-form-item {
        .fixed {
          position: relative;
          input {
            border: none;
            outline: none;
            font-size: 1.6rem;
            background-color: inherit;
          }
          .fixed-btn {
            position: absolute;
            right: 0;
            top: 0.5rem;
            width: 10rem;
            height: 3rem;
            font-size: 1.2rem;
            color: #000;
            background: rgb(173, 232, 255);
          }
        }
      }
      .form-submit {
        text-align: center;
        button {
          width: 80%;
          height: 5rem;
          background-color: rgb(11, 197, 11);
          font-size: 1.6rem;
          font-weight: bold;
        }
        .tips {
          font-size: 1.4rem;
          color: #ccc;
          margin-top: 1rem;
        }
      }
    }
  }
}
</style>
