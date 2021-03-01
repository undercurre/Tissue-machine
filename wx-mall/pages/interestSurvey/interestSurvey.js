var util = require('../../utils/util.js');

Page({
  /**
   * 页面的初始数据
   */
  data: {
    interestSurvey: "",
    userLevelId: "",
    form:{
      price: 10,
      orderType: 3,
      fromType: 1
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let data = JSON.parse(options.userInfo);
    this.setData({
      interestSurvey: data.interestSurvey,
      userLevelId: data.userLevelId
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

  /* 刷新页面 */
  refresh: function (e) {
    this.onLoad()
  },

  submitOrder: function (e) {
    let that = this;
    util.request("order/submitActivityOrder",{
      price: that.data.form.price,
      orderType: that.data.form.orderType,
      fromType: that.data.form.fromType
    },"post").then(function (res) {
      if(res.code === 0){
        wx.reLaunch({
          url: '/pages/pay/pay?orderId=' + res.data.id + "&actualPrice=" + res.data.price
        })
      }
    })
  },
})