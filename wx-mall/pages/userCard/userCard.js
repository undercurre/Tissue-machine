const util = require('../../utils/util.js');
const pay = require('../../services/pay.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonboundRight: 0,/* 胶囊距离导航栏右侧的距离 */
    commentHeight: 0,  /* 导航栏除状态栏的高度 */
    bulletinBoard: 0, /*  */
    topHeight: 0,  /* 整个导航栏高 */
    bbTopX: 0,  /* 胶囊 上下外边距*/
    buttonboundHeight: 0, /* 胶囊的高 */
    buttonboundWidth: 0,/* 胶囊宽 */
    FlexBoxWidth: 0,/* FelxBox宽 */
    statusHeight: 0,/* 状态栏高 */
    textLeft: 0,/*标题的左外边距 */
    rate: 0,/*750rpx：屏幕宽度（px）的比例  */
    WidthP: 0,/* 设备屏幕的宽度 */
    Height: 0,/* 设备屏幕的高度 */
    infoFrameTop:0,/* 信息栏的top */
    fuLiTop:0,/* 限时福利的top */
    showHeight: false,
    price: "",
    number: "",
    userLevel: "",
    showPop:0,/* 是否打开弹窗 */
    showClose: 0,/* 是否打开弹窗的关闭按钮 */
    showCover:0,/* 是否打开弹窗后的遮罩层 */
    closeTop:0,/* 关闭按钮相对遮罩层的top */
    closeLeft:0,/* 关闭按钮相对遮罩层的left */
    fromPersonCenter: 1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    this.getVipInfo()
    this.setData({
      fromPersonCenter: options.from
    })
    /* 获取屏幕宽高和胶囊 */
    var buttonbound = wx.getMenuButtonBoundingClientRect()
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          Height: res.windowHeight,
          WidthP:res.windowWidth,
          topHeight: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5,/* 2.5是为了下半部分更高一下，其实2就可以了（胶囊的上下间距） */
          commentHeight: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5 - res.statusBarHeight,
          buttonboundRight: res.windowWidth - buttonbound.right,
          statusHeight: res.statusBarHeight,
          bbTopX: buttonbound.top- res.statusBarHeight,
          buttonboundHeight: buttonbound.height,
          buttonboundWidth: buttonbound.width,
          FlexBoxWidth: res.windowWidth - (res.windowWidth - buttonbound.right) - 15,
          textLeft: (res.windowWidth - (res.windowWidth - buttonbound.right) - 15) * 0.34,
          rate: 750 / res.windowWidth,
          infoFrameTop: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5 + 5 + 140 - 7,/* infoFrameTop:topHeight+5+140+5 */
          fuLiTop: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5 + 5 + 140 + 16,/* fuLiTop: topHeight+屏幕高的0.19950.......该比例由原型里的数据除以812pt*/
          bulletinBoard: buttonbound.height + buttonbound.top + 21,
          showHeight: true,
        })
      },
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
      this.getVipInfo()
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
  goBack: function () {
    wx.navigateBack({
      delta: 1,
    })
  },
  getVipInfo: function () {
    var that = this
    util.request("vip/info").then(function (res) {
      if (res.code === 0) {
        that.setData({
          price: res.price,
          number: res.number,
          userLevel: res.userLevel
        })
      }
    })
  },
 // 模拟购买会员成功
 handel: function () {
  var that = this;
  that.setData({
    showCover:1,
    showPop:1
  })
    var query = wx.createSelectorQuery();
    setTimeout(function () {
      query.select('.pop').fields({id: true, dataset: true, size: true ,rect: true}, function (res) {
        if (res) {
         that.setData({
          closeTop:res.bottom - 65,
          closeLeft:res.left + res.width * 0.35 + 2,
          showClose:1
        });
        } else {
         console.log('res=null')
        }
      }).exec();
     } ,100);
 },
 closePop: function() {
   this.setData({
     showCover:0
   });
   if (this.data.fromPersonCenter === '1') {
    wx.navigateTo({
      url: '../myTissue/myTissue',
    })
   } else {
    wx.navigateBack({
      delta: 1
    })
   }
 },
  // 提交成为会员订单
  submitOrder: function () {
    var that = this
    util.request("order/submitVipOrder", {
      "fromType": 1,
      "orderType": 3
    }, "POST").then(function (res) {
      if (res.code === 0) {
        pay.payOrder(res.data.id).then(res => {
          // TODO 购买成功打开弹窗
          that.setData({
            showCover:1
          })
        })
      }
    })
  },

  navToNearMachine: function () {
    wx.getLocation({
      type: "gcj02",
      success: function (res) {
        wx.navigateTo({
            url: '/pages/nearMachine/nearMachine?latitude=' + res.latitude + '&longitude=' + res.longitude,
          })
      },
      fail: function (res) {
        wx.showToast({
          title: '获取不到您当前的位置信息!',
          icon: 'none'
        })
      }
    })
  }
})
