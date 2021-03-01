var util = require('../../../utils/util.js');
var app = getApp()

Page({
  data: {
    userInfo: {
      headImgUr: '',
      nicknam: '',
      userNam: '',
    },
  },

  onShow: function () {},

  onLoad: function (options) {
    var that = this
    util.request('user/userInfo').then(function (res) {
      if (res.code === 0) {
        that.setData({
          userInfo: res.data
        });
      }
    });
  },
  bindInputNickname: function(e) {
    var userInfo = this.data.userInfo
    userInfo.nickname = e.detail.value
    this.setData({
      userInfo
    })
  },
  bindInputuserName: function(e) {
    var userInfo = this.data.userInfo
    userInfo.userName = e.detail.value
    this.setData({
      userInfo
    })
  },

  modifyInfo: function (e) {
    var userInfo = this.data.userInfo

    wx.showLoading({
      title: '提交中...'
    })

    util.request('user/modifyInfo', {
        nickname: userInfo.nickname,
        userName: userInfo.userName,
      }, 'POST')
      .then(function (res) {
        wx.hideLoading();
        if (res.code == 0) {
          wx.showToast({
            title: '修改成功',
            icon: 'success'
          }, 1000)
          
          setTimeout(function () {
            wx.navigateBack()
          }, 1000)
        } else {
          util.showMsg(res.msg);
        }
      })
  }
})
