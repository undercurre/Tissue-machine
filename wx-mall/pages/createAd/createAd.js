var util = require('../../utils/util');
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
    showUnitName: 0,/* 是否显示单位名称选项 */
    showPop: 0,/* 是否显示弹窗 */
    radioValue: [
      {
        value: 1,
        name: '企业'
      },
      {
        value: 2,
        name: '商家'
      },
      {
        value: 3,
        name: '个人'
      },
    ],
    checkboxValue: [],
    form:{
      name: '',
      mobile: '',
      workUnitType: '',
      workUnitName: '',
      advertType: ''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
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
    util.request('advert/showUnitType').then(function(res) {
      if(res.code == 0) {
        that.setData({
          checkboxValue: res.data
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
  /* 返回首页 */
  backIndex: function () {
    wx.navigateTo({
      url: '/pages/index/index',
    })
  },
  /* radio被选中 */
  radioValue: function (e) {
    if (e.detail.value === '1' || e.detail.value === '2') {
      this.setData({
        showUnitName: 1
      })
    }
    else {
      this.setData({
        showUnitName: 0
      })
    }
  },
  /* 复选框被选中 */
  checkboxValue: function (e) {
    console.log('checkbox',e);
  },
  /* 表单提交 */
  formSubmit: function (e) {
   let that = this;
   let form = that.data.form;
   form.name = e.detail.value.name;
   form.mobile = e.detail.value.phone;
   form.workUnitType = e.detail.value.radio;
   form.workUnitName = e.detail.value.unitName;
   form.advertType = e.detail.value.checkbox;
   util.request('advert/adverting', form, 'POST').then((res) => {
      if (res.code == 0) {
        that.setData({
          showPop: 1
        })
      }
   })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})