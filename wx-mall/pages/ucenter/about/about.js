var util = require('../../../utils/util.js');

Page({
  data: {
      name: '',
      version: '',
      desc: '乐晟科技 提供技术支持'
  },
  onLoad: function (options) {
    let that = this;
    util.request('about/getAbout').then(function (res) {
      if (res.code === 0) {
        that.setData({
          name: res.data.name,
          version: res.data.ver,
        })
      }
    })
  },
  onShareAppMessage: function () {
    return {

    }
  },
  copy: function (e) {
    let text = e.currentTarget.dataset.text;
    wx.setClipboardData({
      data: text,
      success(res) {
        wx.getClipboardData({
          success(res) {
            util.showMsg('链接已复制');
          }
        })
      }
    })
  }
})