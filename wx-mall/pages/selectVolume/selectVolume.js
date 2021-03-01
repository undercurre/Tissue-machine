// pages/selectVolume/selectVolume.js
const util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    select: 0,
    tabList:[],
    couponList: [],
    volumId:0,
    openWin:false,
    nowNumber:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCouponNumber()
    this.getCouponList(0)
    this.setData({
      nowNumber:options.number
    })
  },
  getCouponList (status) {
    var that = this
    util.request("coupon/list", {
      "status": status
    }).then(function (res) {
      that.setData({
        couponList: res.data
      })
    })
  },

  getCouponNumber () {
    var that = this
    util.request("coupon/getCouponNumber").then(function (res) {
      that.setData({
        tabList: res.data
      })
    })
  },
  choose (event) {
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];  //上一个页面
    prevPage.setData({
      couponId: event.currentTarget.dataset.volumid,
      number: this.data.nowNumber,
      popShow: true,
      couponType: event.currentTarget.dataset.coupontype,
      hasSelect:1,
      discount: event.currentTarget.dataset.discount,
      subprice: event.currentTarget.dataset.subprice
    })
    wx.navigateBack({
      delta: 1// 返回上一级页面。
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

  }
})