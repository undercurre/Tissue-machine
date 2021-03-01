// pages/result/result.js
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
    popShow: false,
    getStatus: 1,
    sn: '',
    machineId: '',
    orderPatch: '',
    machineInfo: '',
    machineGoodsInfo: '',
    userInfo: '',
    goodsBanner: '',
    number: 1,
    totalPrice: 1,
    status: 0,
    wechat:true,/* 是否为微信支付 */
    hasSelect: 0,/* 是否选了优惠卷 */
    volume:3,/* 有几张优惠卷 */
    showSelect:0, /*是否显示优惠券弹窗  */
    couponType: '',
    couponId: '', /*优惠券id*/
    sumSubPrice: '',/* 代金券金额 */
    useBalance: '',/* 用户抵扣余额 */
    actualPrice: '',/* 实际支付金额 */
    couponPrice: '',/* 优惠券抵扣金额 */
    payType: ''/* 支付类型 1：微信支付，2：余额支付 */
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    if (options.sn) {
      this.setData({
      sn: options.sn
    })
    }
    util.request('tissueMachine/queryBySn', {
      sn: that.data.sn
     }).then(function (res) {
       if (res.code === 0) {
         that.setData({
           orderPatch: res.data.orderPatch,
           machineInfo: res.data,
           machineId: res.data.id
         })
       }
     })
     this.getUserInfo()
     util.request('tissueMachine/goodsInfo', {
       sn: that.data.sn
     }).then(function (res) {
       if (res.code === 0) {
         that.setData({
           goodsBanner: res.data[0].goodsBanner,
           totalPrice: res.data[0].price,
           machineGoodsInfo: res.data
         })
       }
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
    var that = this
    var number = that.data.number

    this.checkout(number);
    wx.setStorageSync('navUrl', '/pages/result/result')
    this.getUserInfo()
    util.request('tissueMachine/goodsInfo', {
      sn: that.data.sn
    }).then(function (res) {
      if (res.code === 0) {
        that.setData({
          goodsBanner: res.data[0].goodsBanner,
          totalPrice: res.data[0].price,
          machineGoodsInfo: res.data
        })
      }
    })
    util.request('tissueMachine/queryBySn', {
      sn: that.data.sn
    }).then(function (res) {
      if (res.code === 0) {
        that.setData({
          orderPatch: res.data.orderPatch,
          machineInfo: res.data,
          machineId: res.data.id
        })
      }
    })
    util.request('coupon/list',{
      status: 0,
    }).then(function(res){
      if (res.code === 0) {
        that.setData({
          volume: res.number
        })
      }
    })
    
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
    // 显示顶部刷新图标
    wx.showNavigationBarLoading();
    this.onLoad();
    // 隐藏导航栏加载框
    wx.hideNavigationBarLoading();
    // 停止下拉动作
    wx.stopPullDownRefresh();
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
  subNumber: function (e) {

  },
  changeNumber: function (event) {
    var that = this
    let number = event.detail.value;
    let remainBuyToday = that.data.userInfo.remainBuyToday
    if (number <= 0){
      that.setData({
        number: 1
      })
      wx.showToast({
        title: "购买商品数量必须大于0",
        icon:"none",
        duration: 2000
      })
      return;
    }
    else if (remainBuyToday < number) {
      that.setData({
        number: remainBuyToday
      })
      wx.showToast({
        title: "购买商品数量不能超过每日可购买数量",
        icon:"none",
        duration: 2000
      })
      return;
    }
    else{
      this.checkout(number);
      this.whichGoods(number)
    }
  },
  // 校验订单
  checkout: function (number) {
    let that = this;
    util.request('order/checkout',{
      machineId: that.data.machineId,
      number: number,
      couponId: that.data.couponId
    }).then(function(res){
      if (res.code === 0) {
        that.setData({
          useBalance: res.useBalance,
          actualPrice: res.actualPrice,
          sumSubPrice: res.sumSubPrice,
          number: number,
          payType: res.payType
        })
        // 余额支付
        if (res.payType == 1) {
          that.setData({
            wechat: false
          })
        }
        // 微信支付 
        else {
          that.setData({
            wechat: true
          })
        }
      }
    })
  },
  updatePrice: function (number, price) {
    var totalPrice = number * price
    return totalPrice
  },
  // 选择优惠券
  gotoChoose: function () {
    wx.navigateTo({
      url: '/pages/selectVolume/selectVolume?number=' + this.data.number
    })
  },
  //判断是什么商品
  whichGoods: function (number) {
    var that = this
    var totalStock = that.data.machineInfo.tissueNumber
    var goodsInfo = that.data.machineGoodsInfo
    var totalPrice = 0
    if (totalStock > number) {
      for (let i in goodsInfo) {
        //如果数量小于当前商品库存
        if (number <= goodsInfo[i].stock) {
          var totalPrice = totalPrice + that.updatePrice(number, goodsInfo[i].price)
          that.setData({
            totalPrice: totalPrice
          })
          break;
        }
        var totalPrice = totalPrice + that.updatePrice(goodsInfo[i].stock, goodsInfo[i].price)
        number -= goodsInfo[i].stock
      }
    }
  },

  //免费领取
  goToGet: function (e) {
    var that = this
    var activityId = e.target.dataset.activityid
    var orderType = 2
    var fromType = 1
    var machineId = that.data.machineId
    var number = 1
    if (that.data.userInfo.userLevelId === '1' && that.data.userInfo.tissueCount === 0) {
      wx.navigateTo({
        url: '/pages/userCard/userCard?' + 'from=' + '0',
      })
    } else {
      util.request('/order/submitOrder', {
        orderType: orderType,
        fromType: fromType,
        machineId: machineId,
        activityId: activityId,
        number: number
      }, 'POST').then(function (res) {
        if(res.isUse==1){
          wx.reLaunch({
            url: '/pages/machineErrorStatus/machineErrorStatus?sn='+that.data.sn,
          })
        }else{
        that.setData({
          getStatus: 0
        })
        wx.redirectTo({
          url: '/pages/inShipment/inShipment?sn=' + that.data.sn+'&orderId='+res.data.id
        })
      }
    })
    }
    
  },


  //微信支付
  goToSubmitOrder: function () {
    var that = this
    var number = that.data.number
    var totalPrice = that.data.totalPrice
    var payType = that.data.payType
    var machineId = that.data.machineId
    // 机柜是否在用
    if (that.data.machineInfo.isUse == 1) {
      wx.reLaunch({
        url: '/pages/machineErrorStatus/machineErrorStatus?sn=' + that.data.machineInfo.sn,
      })
    } else {
      // 下单
      util.request('/order/submitOrder', {
        orderType: 1,
        fromType: 1,
        price: that.data.actualPrice,
        comsumePrice: that.data.useBalance,
        machineId: machineId,
        number: number,   
        couponId: that.data.couponId
      }, 'POST').then(function (res) {
        if (res.code === 0) {
            // 微信支付
            if (payType == 2) {
              const orderId = res.data.id;
              const sn = that.data.sn;
              pay.payOrder(orderId).then(res => {
                wx.reLaunch({
                  url: '/pages/inShipment/inShipment?sn=' + sn + '&orderId='+ orderId
                })
              })
            } 
            // 余额支付
            else {
              const orderId = res.data.id;
              const sn = that.data.sn;
              util.request('/pay/buyByYue', {
                orderId: orderId,
                fromType: 1
              }, 'POST').then(function (res) {
                if (res.code === 0) {
                  wx.reLaunch({
                    url: '/pages/inShipment/inShipment?sn=' + sn + '&orderId='+ orderId
                  })
                }
              })
            }
        }
      })
    }
  },

  getUserInfo: function () {
    var that = this;
    util.request("user/userInfo").then(function (res) {
      that.setData({
        userInfo: res.data
      })
      if ((res.data.tissueCount === 0) && (res.data.userLevelId === '2')) {
        that.setData({
          status: 1
        })
      }
    })
  },
 
  backIndex: function () {
    wx.navigateTo({
      url: '/pages/index/index',
    })
  },
  statusA: function () {
    this.setData({
      status: 0
    })
  },
  /* 消除下单弹窗 */
closeWin:function () {
  this.setData({
    popShow: false,
    couponId: '',/* 用户代金券id */
    sumSubPrice: '',/* 代金券金额 */
    useBalance: '',/* 用户抵扣余额 */
    actualPrice: '',/* 实际支付金额 */
    couponPrice: '',/* 优惠券抵扣金额 */
    hasSelect: 0,/* 是否选了优惠券 */
  })
},
  statusB: function () {
    this.setData({
      status: 1
    })
  },
  showPop: function() {
    let that = this;
    let remainBuyToday = this.data.userInfo.remainBuyToday
    if (remainBuyToday <= 0) {
      wx.showModal({
        title: '提示',
        content: '已超过今日可购买数量！',
      })
    } else {
      that.checkout(1);
      that.setData({
        popShow: !that.data.popShow
      })
    }
  },
})