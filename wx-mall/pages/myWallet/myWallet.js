const util = require("../../utils/util");

// pages/myWallet/myWallet.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: '',
    nowMoney:'',
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
    select: '',/* 按钮的值 */
    selectId: '',
    payMoney: 0.00,/* 实际支付的值 */
    highligh: 0,/* 按钮“购买纸巾是否高亮” */
    dataList: [],
    canBalanceDeposit: ''
  },

  /**
   * 点击加载到交易明细页面
   */
  transactiondetails:function() {
    wx.navigateTo({
      url: '../TransactionDetails/TransactionDetails',
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
    this.getRechargeLevelList()
    this.getCanBalanceDeposit()
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
    wx.navigateBack({
      delta: 1
    })
  },
  getRechargeLevelList:function(){
    var that = this;
    util.request("rechargeLevel/list").then(function (res) {
      that.setData({
        dataList: res.data
      })
    })
  },
  getCanBalanceDeposit:function(){
    var that = this;
    util.request("rechargeLevel/getCanYue").then(function (res) {
      that.setData({
        canBalanceDeposit: res.data
      })
    })
  },
  getUserInfo: function() {
    var that = this;
    util.request("user/userInfo").then(function (res) {
      that.setData({
        userInfo: res.data,
        nowMoney: res.data.balance
      })
    })
  },
  submitSub: function () {
    var that = this;
    var selectId = that.data.selectId
    if (!selectId) {
      util.showMsg('请选择充值金额');
      return false;
    }
    util.request('pay/prepayYue', {
      fromType: 1,
      tradeType: 'JSAPI',
      rechargeId: selectId
    }, 'POST').then(function (res) {
      if (res.code === 0) {
        var jsConfig = res.data;
        wx.requestPayment({
          timeStamp: jsConfig.timeStamp,
          nonceStr: jsConfig.nonceStr,
          package: jsConfig.packageValue,
          signType: jsConfig.signType,
          paySign: jsConfig.paySign,
          success: function (res) {
            util.showMsg('支付成功', true, 1000);
            that.setData({
              nowMoney: parseFloat(that.data.nowMoney) + parseFloat(that.data.select)
            });
          },
          fail: function (res) {
            util.showMsg('支付失败', true, 1000);
          },
          complete: function (res) {
            if (res.errMsg == 'requestPayment:cancel') {
              util.showMsg('取消支付', true, 1000);
            }
          },
        })
      } else {
        util.showMsg(res.msg, true, 1000);
      }
    })
  },
  toIndex: function () {
    wx.navigateTo({
      url: '../index/index',
    })
  }
})