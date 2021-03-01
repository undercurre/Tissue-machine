var util = require('../../utils/util.js');

Page({
  data: {
    select: 0,
    tabList:[],
    couponList: []
  },

  onLoad: function () {
    this.getCouponNumber()
    this.getCouponList(0)
  },

  activeTab (e) {
    var index = e.currentTarget.dataset.index
    this.setData({
      select: index
    })
    this.getCouponList(index)
  },

  toIndex () {
    wx.reLaunch({
      url: '/pages/index/index',
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
  }
})
