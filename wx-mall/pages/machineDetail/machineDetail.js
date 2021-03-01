// pages/result/result.js
const util = require('../../utils/util.js');
const pay = require('../../services/pay.js');
const key = 'WPABZ-DYVLX-OMV4L-7ZRYP-YUAN6-MZBAJ';
const referer = '便民纸巾设备';
const routPlan = requirePlugin('routePlan');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    machineInfo: {
    },
    patch: '',
    hasAd: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getMachineInfo(options.machineId);
     this.getMachinePatch(); 
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    // 显示顶部刷新图标
    wx.showNavigationBarLoading();
    this.onLoad();
    // 隐藏导航栏加载框
    wx.hideNavigationBarLoading();
    // 停止下拉动作
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getMachineInfo: function (machineId) {
    let that = this
    util.request("tissueMachine/queryById", {
      machineId: machineId
    }).then(function (res) {
      that.setData({
        machineInfo: res.data
      })
    })
  },
  getMachinePatch: function () {
    let that = this
    util.request("patch/queryTissueMachinePatch").then(function (res) {
      if (res.data.length <= 0) {
         that.setData({
           hasAd: false
         })
      } else {
        that.setData({
          hasAd: true
        })
      }
      that.setData({
        patch: res.data[0]
      })
    })
  },
  toFeedBack: function (e) {
    wx.navigateTo({
      url: '/pages/problemFeedback/problemFeedback?machineId=' + e.currentTarget.dataset.id
    })
  },
  toHere: function (e) {
    const machine = e.currentTarget.dataset.id;
    const endPoint = JSON.stringify({
      'name': machine.name,
      'latitude': machine.latitude,
      'longitude': machine.longitude
    })
    wx.navigateTo({
      url: 'plugin://routePlan/index?key=' + key + '&referer=' + referer + '&endPoint=' + endPoint
    })
  },
  scan: function () {
    var that = this;
    wx.scanCode({
      onlyFromCamera: true,
      success: function(res) {
        let qrUrl = decodeURIComponent(res.result)
        let sn = util.getQueryString(qrUrl, 'sn')
        if (sn != null || sn != '') {
          that.jumpToAdScan(sn)
        }
      }
    })
  },
  jumpToAdScan: function(sn) {
    wx.reLaunch({
      url: '/pages/adScan/adScan?sn='+sn,
    })
  }
})