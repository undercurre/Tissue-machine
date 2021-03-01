<template>
  <el-dialog
    title="消息记录"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-table
      :data="messageList"
      :show-header="false"
      style="width: 100%"
      height="400">
      <el-table-column>
        <template slot-scope="scope">
          <template v-if="scope.row.operCode === 2002">
            <span style="color: blue">{{worker}}  {{scope.row.createdTime}}</span> </br>{{scope.row.text}}
          </template>
          <template v-if="scope.row.operCode === 2003">
            <span style="color: green"> {{user}}  {{scope.row.createdTime}}</span></br>{{scope.row.text}}
          </template>
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        messageList: '',
        user: '',
        worker: ''
      }
    },
    methods: {
      init (openId, workerId, userName, workerName) {
        this.visible = true
        this.user = userName
        this.worker = workerName
        this.$http({
          url: '/ad/chatrecord/getMessageList',
          method: 'get',
          params: {
            openId: openId,
            workerId: workerId
          }
        }).then(({ data }) => {
          this.messageList = data.list
        })
      }
    }
  }
</script>
