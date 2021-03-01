var util = require('../../utils/util');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //页面出货成功1 出货失败 2
    status: '',
    sn: '',
    orderType: '',
    order: '',
    number: '',
    shipmentNumber: '',
    latitude: 23.021433245663903,
    longitude: 113.12170018170164,
    machineId: 0,
    machineStatus: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var orderId = options.orderId
    util.request('order/detail', {
      orderId: orderId
    }).then(function (res) {
      if (res.code === 0) {
        that.setData({
          orderType: res.data.orderType,
          status: res.data.shipmentStatus,
          number: res.data.number,
          shipmentNumber: res.data.shipmentNumber,
          machineId: res.data.machineId
        })
      }
      util.request('tissueMachine/queryById', {
        machineId: res.data.machineId
      }).then(function (result) {
        if (result.code === 0) {
          if (result.data.status === 1) {}
          that.setData({
            machineStatus: result.data.status
          })
        }
      })
    })
    this.setData({
      sn: options.sn,
      orderId: options.orderId
    });
    wx.getLocation({
      type: "gcj02",
      success: function (res) {
        that.setData({
          latitude: res.latitude,
          longitude: res.longitude,
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

  navToBuyAgain: function () {
    let that = this;
    if (that.data.machineStatus === 1) {
      wx.navigateTo({
        url: '/pages/result/result?sn=' + that.data.sn,
      })
    }else{
      wx.redirectTo({
        url: '/pages/machineErrorStatus/machineErrorStatus?sn=' +that.data.sn
      })
    }

  },

  navToProblemFeedback: function (e) {
    var that = this
    var machineid = this.data.machineId
    wx.navigateTo({
      url: '/pages/problemFeedback/problemFeedback?machineId=' + machineid
    })
  },
  navToNearMachine: function (e) {
    var that = this
    var latitude = that.data.latitude
    var longitude = that.data.longitude
    wx.reLaunch({
      url: '/pages/nearMachine/nearMachine?latitude=' + latitude + '&longitude=' + longitude,
    })
  }
})