// pages/nearMachine/nearMachine.js
var util = require('../../utils/util');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    TissueMachineList: [],
    latitude: '',
    longitude: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    util.request("tissueMachine/list").then(function (res) {
      if (res.code === 0) {
        var userLatitude = options.latitude
        var userLongitude = options.longitude
        for (let i in res.data) {
          var distance=parseFloat(util.getDistance(res.data[i].latitude,res.data[i].longitude,userLatitude,userLongitude))*1000
          res.data[i].distance=distance
        }
        res.data.sort(that.compare("distance"))
        that.setData({
          TissueMachineList: res.data,
        })
      }
    })
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
  compare: function (property) {
    return function (a, b) {
      var value1 = a[property];
      var value2 = b[property];
      return value1 - value2;
    }
  },
  goMachineDetail: function(e) {
    wx.navigateTo({
      url: '/pages/machineDetail/machineDetail?machineId=' + e.currentTarget.dataset.id
    })
  }
})