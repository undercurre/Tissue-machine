const util = require("../../utils/util");

// pages/myWallet/myWallet.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: '',
    nowTissue:'',
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
    bottomOfWhite: 0, /* 上半部分白色的高，上半部分就是紫色区域里的白色区域 */
    marginTopOfWhite:25,/* 白色区域距离余额的距离 */
    highligh: 0,/* 按钮“购买纸巾是否高亮” */
    dataList: [],
    canBalanceDeposit: '',
    latitude: '',
    longitude: ''
  },

  /**
   * 点击加载到领取记录页面
   */
  getrecord:function() {
    wx.navigateTo({
      url: '../gettingRecord/gettingRecord',
    })
  },

  choiceMoney: function (e) {
    this.setData({
      selectId: e.currentTarget.dataset.id,
      select: e.currentTarget.dataset.money,
      payMoney: e.currentTarget.dataset.realpay
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
     /* 获取屏幕宽高和胶囊 */
     var buttonbound = wx.getMenuButtonBoundingClientRect()

    this.getCenterLocation()
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
    });
    this.getUserInfo()
    /* 获取上半部分白色区域的信息 */
    var query = wx.createSelectorQuery();
    var margintop =this.data.marginTopOfWhite;
     setTimeout(function () {
      query.select('.BackGround').fields({id: true, dataset: true, size: true ,rect: true});
      query.select('.count').fields({id: true, dataset: true, size: true ,rect: true});
      query.exec((res) => {
        that.setData({
          bottomOfWhite: res[0].bottom,
        });
        
      });
     } , 300);
  },

  goToNearMachine: function(e){
    var that = this
    var latitude = that.data.latitude
    var longitude = that.data.longitude
      wx.navigateTo({
        url: '/pages/nearMachine/nearMachine?latitude='+latitude+'&longitude='+longitude,
      })
  },
  getCenterLocation: function () {
    var that = this
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
  backIndex:function(){
    /* wx.redirectTo({
      url: '/pages/personalCenter/personalCenter',
    }) */
    wx.navigateBack({
      delta: 1
    })
  },
//获取当前用户信息
  getUserInfo: function() {
    var that = this;
    util.request("user/userInfo").then(function (res) {
      that.setData({
        userInfo: res.data,
        nowTissue: res.data.tissueCount
      })
    })
  },
})