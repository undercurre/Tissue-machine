// pages/myOrder/myOrder.js
var util = require('../../utils/util');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderList: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that =this
    util.request('/order/list').then(function(res){
      if(res.code===0){
        that.setData({
          orderList:res.data
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
  deleteOrder: function(e){
    var that = this
    var orderId = e.currentTarget.dataset.orderid
    wx.showModal({
      title: '提示',
      content: '确定删除该订单吗',
      success: function(res) {
        if (res.confirm) {
          util.request('/order/deleteById',{orderId:orderId},'POST').then(function(res){
            that.onLoad()
          })
        }
         else if (res.cancel) {
          return;
        }
      }
    })
  }
})