// pages/ucenter/ucenter.js

var util = require("../../utils/util")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    messageNumber: 0,
    userInfo: null,
    userTissueNumber: 365,
    join: '',
    image: '',
    userlevel:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    wx.setStorageSync('navUrl', '/pages/personalCenter/personalCenter');
    this.getUserInfo();
    this.getMessageNumber();
    this.getPatchImage();
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

  navToMyOrder: function (e) {
    if (!wx.getStorageSync('token')) {
      wx.showModal({
        title: '温馨提示',
        content: '您还没有登录，是否去登录',
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/auth/btnAuth/btnAuth',
            })
          } else if (res.cancel) {
            let page = getCurrentPages()
            let curr = page[page.length - 2]
            if(curr.route === "pages/index/index"){
              return
            } else {
              wx.reLaunch({
              url: '/pages/index/index',
            })
            }
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/myOrder/myOrder',
    })
  },

  navTomembershipCard: function (e){
    if (!wx.getStorageSync('token')) {
      wx.showModal({
        title: '温馨提示',
        content: '您还没有登录，是否去登录',
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/auth/btnAuth/btnAuth',
            })
          } else if (res.cancel) {
            let page = getCurrentPages()
            let curr = page[page.length - 2]
            if(curr.route === "pages/index/index"){
              return
            } else {
              wx.reLaunch({
              url: '/pages/index/index',
            })
            }
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/userCard/userCard?' + 'from=' + '1',
    })
  },
  navToMyWallet: function () {
    if (!wx.getStorageSync('token')) {
      wx.showModal({
        title: '温馨提示',
        content: '您还没有登录，是否去登录',
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/auth/btnAuth/btnAuth',
            })
          } else if (res.cancel) {
            let page = getCurrentPages()
            let curr = page[page.length - 2]
            if(curr.route === "pages/index/index"){
              return
            } else {
              wx.reLaunch({
              url: '/pages/index/index',
            })
            }
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/myWallet/myWallet',
    })
  },
  navToMessage: function (e) {
    if (!wx.getStorageSync('token')) {
      wx.showModal({
        title: '温馨提示',
        content: '您还没有登录，是否去登录',
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/auth/btnAuth/btnAuth',
            })
          } else if (res.cancel) {
            let page = getCurrentPages()
            let curr = page[page.length - 2]
            if(curr.route === "pages/index/index"){
              return
            } else {
              wx.reLaunch({
              url: '/pages/index/index',
            })
            }
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/ucenter/message/message',
    })
  },

  navToAbout: function (e) {
    wx.navigateTo({
      url: '/pages/ucenter/about/about',
    })
  },

  getMessageNumber: function () {
    var that = this;
    util.request("message/getMessageNumber").then(function (res) {
      that.setData({
        messageNumber: res.data
      })
    })
  },

  getUserInfo: function () {
    var that = this;
    util.request("user/userInfo").then(function (res) {
      that.setData({
        userInfo: res.data,
        userTissueNumber: res.data.tissueCount,
        userlevel:parseInt(res.data.userLevelId)
      })
    })
  },

  getPatchImage: function () {
    var that = this
    util.request("patch/getPersonCenterPatch").then(function (res) {
      that.setData({
        image: res.data[0].imageUrl
      })
    })
  },

  setting: function() {
    if (!wx.getStorageSync('token')) {
      wx.showModal({
        title: '温馨提示',
        content: '您还没有登录，是否去登录',
        success(res) {
          if (res.confirm) {
            wx.redirectTo({
              url: '/pages/auth/btnAuth/btnAuth',
            })
          } else if (res.cancel) {
            let page = getCurrentPages()
            let curr = page[page.length - 2]
            if(curr.route === "pages/index/index"){
              return
            } else {
              wx.reLaunch({
              url: '/pages/index/index',
            })
            }
          }
        }
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/ucenter/set/set',
    })
  },
  login: function() {
    wx.navigateTo({
      url: '/pages/auth/btnAuth/btnAuth',
    })
  },
  navToMyCoupon: function () {
    wx.navigateTo({
      url: '/pages/coupon/coupon',
    })
  },

  /**
   * 点击加载到我的纸巾页面
   */
  getrecord:function() {
    wx.navigateTo({
      url: '../myTissue/myTissue',
    })
  }

})
