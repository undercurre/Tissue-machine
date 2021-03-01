var util = require('../../utils/util.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    pageUrl: '', // 要跳转的页面
    count: 0,
    imageUrl: '', // 广告图片
    isJump: false, // 是否显示跳转按钮
    aJump: false, // 是否已经跳转
    left: '',
    isShow: 0,
    btnleft: 0,
    btnHeight: 0,
    btnWidth: 0
  },
  onLoad: function(options) {
    var that = this
    var pageUrl = '/pages/result/result?sn=' + options.sn
    console.log(wx.getStorageSync('menuButtonCss').left)
    // var left = wx.getStorageSync('menuButtonCss').left
    var buttonbound = wx.getMenuButtonBoundingClientRect()
    console.log('胶囊',buttonbound)
    that.setData({
      pageUrl: pageUrl,
      btnleft: buttonbound.left,
      btnHeight:  buttonbound.height,
      btnWidth: buttonbound.width
    })
    // 需要传入跳转页面，url
  },
  onReady: function () {
    
  },
  onShow: function() {
    var that = this
    // 需要接入接口，获取广告url、时间等信息
    util.request('scan/getScan').then(function(res) {
      if(res.code == 0) {
        that.setData({
          isShow: 1,
          count: res.data.duration,
          imageUrl: res.data.imageUrl
        })
        // 进行倒计时
        if (!that.data.aJump) {
          that.countdown()
        }
      }
    })
  },
  countdown: function () {
    let that = this
    var count = that.data.count
    var pastCount = 0
    that.data.timer = setInterval(() =>{
      count -= 1
      pastCount += 1
      that.setData({
        count: count
      });
      if (pastCount >= 3) {
        that.setData({
          isJump: true
        })
      }
      if (that.data.count == 0) {
        clearInterval(that.data.timer)
        setTimeout(function() {
          that.jump()
        }, 1000)
      }
    }, 1000)
  },
  jump:function() {
    var that = this
    clearInterval(that.data.timer)
    that.back()
  },
  back: function() {
    var that = this
    that.setData({
      aJump: true
    })
    wx.redirectTo({
      url: that.data.pageUrl,
    })
  }
})