var util = require('../../utils/util.js');
const chooseLocation = requirePlugin('chooseLocation');
const routPlan = requirePlugin('routePlan');
const key = 'WPABZ-DYVLX-OMV4L-7ZRYP-YUAN6-MZBAJ';
const referer = '便民纸巾设备';
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    hasOrderPatch: false,
    textLength: 0,
    isStart: 0,
    /* 跑马灯文本的长度 */
    textTime: 5,
    /* 跑马灯文本播放时间,秒 */
    buttonboundRight: 0,
    /* 胶囊距离导航栏右侧的距离 */
    commentHeight: 0,
    /* 导航栏除状态栏的高度 */
    bulletinBoard: 0,
    /*  */
    topHeight: 0,
    /* 整个导航栏高 */
    bbTopX: 0,
    /* 胶囊 上下外边距*/
    buttonboundHeight: 0,
    /* 胶囊的高 */
    buttonboundWidth: 0,
    /* 胶囊宽 */
    FlexBoxWidth: 0,
    /* FelxBox宽 */
    statusHeight: 0,
    /* 状态栏高 */
    textLeft: 0,
    /*标题的左外边距 */
    rate: 0,
    /*750rpx：屏幕宽度（px）的比例  */
    WidthP: 0,
    /* 屏幕的宽度 */
    Height: 0,
    notice: '',
    showHeight: false,
    userInfo: '',
    latitude: 23.021433245663903,
    longitude: 113.12170018170164,
    choiceMachineId: '',
    customCalloutMarkerIds: [],
    markers: [],
    hasMarkers: false,
    showMachineDetail: false,
    /* orderPatch:[
      {
        id:1,
        imageUrl:'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/bulletinBoard.png'


      },
      {
        id:3,
        imageUrl:'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/bulletinBoard.png'
      },
      {
        id:4,
        imageUrl:'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/bulletinBoard.png'
      }
    ], */
    orderPatch: [],
    machineInfo: {
      id: '',
      tissueNumber: 50,
      workTime: '周一至周日00：00-00：00',
      address: '张槎街道江湾一路44号第一层',
      name: '老鸭炮(创产店)'
    }
  },

  bindmarkertap: function (res) {
    var that = this;
    //如果选的是当前位置点或者图片标记点 则直接return
    if (res.markerId === -1) {
      that.setData({
        showMachineDetail: false
      })
      return
    }
    this.setData({
      choiceMachineId: res.markerId
    })
    var marId = res.markerId
    if (res.markerId.length > 32) {
      marId = res.markerId.substring(0, res.markerId.length - 1)
    }
    util.request("tissueMachine/queryById", {
      machineId: marId
    }).then(function (res) {
      that.setData({
        machineInfo: res.data,
        showMachineDetail: true
      })
    })
  },

  bindtapOther: function (res) {
    this.setData({
      showMachineDetail: false
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    // this.getCenterLocation();
    this.getOrderPatch();
    this.getNoticeData();
    wx.getLocation({
      type: "gcj02",
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude
        that.setData({
          latitude: res.latitude,
          longitude: res.longitude,
        })
        util.request("tissueMachine/list").then(function (res) {
          if (res.code === 0) {
            for (let i in res.data) {
              if (res.data[i].isShowLogo == 1) {
                res.data.push({
                  id: res.data[i].id + ',',
                  latitude: res.data[i].latitude,
                  longitude: res.data[i].longitude,
                  iconPath: res.data[i].machineLogo,
                  width: 40,
                  height: 30,
                  anchor: {
                    x: .5,
                    y: .1
                  }
                })
              }
              if (res.data[i].status != 1) {
                res.data[i].iconPath = 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/EquipmentAbnormalZpq3x.png'
                res.data[i].width = 60
                res.data[i].height = 60
              } else if (res.data[i].status == 1) {
                res.data[i].iconPath = 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/merchantsZpq3x.png'
                res.data[i].width = 60
                res.data[i].height = 60
              }

            }
            res.data.push({
              id: -1,
              latitude: latitude,
              longitude: longitude,
              iconPath: 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/UserLocationZpq3x.png',
              width: 20,
              height: 30.5,
              callout: {
                content: '附近设备 >',
                color: '#082F40',
                fontSize: 16,
                bgColor: '#ffffff',
                borderRadius: 10,
                padding: 5,
                display: 'BYCLICK',
                textAlign: 'center'
              }
            })
            that.setData({
              markers: res.data,
              hasMarkers: true
            })
          }
        })
      }
    })
    if (options.sn) {
      let qrUrl = decodeURIComponent(options.sn)
      sn = util.getQueryString(qrUrl, 'sn')
      if (sn != null || sn != '') {
        this.jumpToAdScan(sn)
      }
    }
    /* 获取时间 */
    var time = util.getNowTime();
    this.setData({
      time: time
    });
    /* 获取机器信息列表 */

    this.getUserInfo();

    /* 获取当前设备屏幕高度 */
    var that = this
    var buttonbound = wx.getMenuButtonBoundingClientRect()
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          Height: res.windowHeight,
          WidthP: res.windowWidth,
          topHeight: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5,
          commentHeight: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5 - res.statusBarHeight,
          buttonboundRight: res.windowWidth - buttonbound.right,
          statusHeight: res.statusBarHeight,
          bbTopX: buttonbound.top - res.statusBarHeight,
          buttonboundHeight: buttonbound.height,
          buttonboundWidth: buttonbound.width,
          FlexBoxWidth: res.windowWidth - (res.windowWidth - buttonbound.right) - 15,
          textLeft: (res.windowWidth - (res.windowWidth - buttonbound.right) - 15) * 0.34,
          rate: 750 / res.windowWidth,
          bulletinBoard: buttonbound.height + res.statusBarHeight + (buttonbound.top - res.statusBarHeight) * 2.5 + 15,
          showHeight: true,
        })
      },
    })


  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    let that = this;
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

  getNoticeData: function () {
    var that = this
    util.request('index/queryMarqueeTextList').then(function (res) {
      if (res.code === 0) {
        let notice = '';
        for (let i = 0; i < res.data.length; i++) {
          notice += '   ' + (i + 1) + '：' + res.data[i].content
        }
        that.setData({
          notice: notice
        });
      }
    });

  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    wx.setStorageSync('navUrl', '/pages/index/index');
    wx.setStorageSync('menuButtonCss', wx.getMenuButtonBoundingClientRect());
    this.mapCtx = wx.createMapContext('myMap')

    var that = this
    const location = chooseLocation.getLocation()
    if (location != null) {
      that.setData({
        latitude: location.latitude,
        longitude: location.longitude
      })
    }
    that.getTissueMachine()

    //  监听跑马灯文本宽度
    var query = wx.createSelectorQuery();
    setTimeout(function () {
      query.selectAll('.tui-notice1,.tui-scorll-view').fields({
        id: true,
        dataset: true,
        size: true,
        rect: true
      }, function (res) {
        if (res[1]) {
          /* res[1].width为文本的宽度，res[0].wdith为显示区域的宽度 */
          if (res[1].width <= (2 * res[0].width)) {
            if (res[1].width <= res[0].width) {
              that.setData({
                isStart: 1,
                textLength: res[1].width,
                textTime: 18
              })
            } else {
              that.setData({
                isStart: 1,
                textLength: res[1].width,
                textTime: 28
              })
            }
          } else {
            var n = parseInt(res[1].width / (res[0].width * 2));

            that.setData({
              isStart: 1,
              textLength: res[1].width,
              textTime: 5 + (n * 28)
            })
          }
        } else {
          console.log('res=null')
        }
      }).exec();
    }, 500);

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
    chooseLocation.setLocation(null);
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
    this.onLoad(e)
  },

  /* 打开扫一扫 */
  /*  openScan: function (event) {
     wx.reLaunch({
       url: '/pages/result/result',
     })
   }, */
  /* 点击悬赏任务图标 */
  gotoTask: function (event) {
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
            let curr = page[page.length - 1]
            if (curr.route === "pages/index/index") {
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
      url: '/pages/task/task',
    })
  },
  /* 点击我要投广告 */
  gotoCreateAd: function () {
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
            let curr = page[page.length - 1]
            if (curr.route === "pages/index/index") {
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
      url: '/pages/createAd/createAd',
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
            let curr = page[page.length - 1]
            if (curr.route === "pages/index/index") {
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
  navToSearch: function () {
    var that = this
    const key = 'WPABZ-DYVLX-OMV4L-7ZRYP-YUAN6-MZBAJ'; //使用在腾讯位置服务申请的key
    const referer = '便民纸巾设备'; //调用插件的app的名称
    const location = JSON.stringify({
      latitude: that.data.latitude,
      longitude: that.data.longitude
    });
    wx.navigateTo({
      url: 'plugin://chooseLocation/index?key=' + key + '&referer=' + referer + '&location=' + location
    })
  },
  getTissueMachine: function () {
    var that = this;
    util.request("tissueMachine/list").then(function (res) {
      if (res.code === 0) {
        for (let i in res.data) {
          if (res.data[i].isShowLogo == 1) {
            res.data.push({
              latitude: res.data[i].latitude,
              longitude: res.data[i].longitude,
              iconPath: res.data[i].machineLogo,
              width: 40,
              height: 30,
              anchor: {
                x: .5,
                y: .1
              }
            })
          }
          if (res.data[i].status != 1) {
            res.data[i].iconPath = 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/EquipmentAbnormalZpq3x.png'
            res.data[i].width = 60
            res.data[i].height = 60
          } else {
            res.data[i].iconPath = 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/merchantsZpq3x.png'
            res.data[i].width = 60
            res.data[i].height = 60
          }
        }

        res.data.push({
          id: -1,
          latitude: that.data.latitude,
          longitude: that.data.longitude,
          iconPath: 'https://admachine-1254098248.cos.ap-guangzhou.myqcloud.com/UserLocationZpq3x.png',
          width: 20,
          height: 30.5,
          callout: {
            content: '附近设备 >',
            color: '#082F40',
            fontSize: 12,
            bgColor: '#ffffff',
            borderRadius: 10,
            padding: 5,
            display: 'BYCLICK',
            textAlign: 'center'
          }

        })
        that.setData({
          markers: res.data,
          hasMarkers: true
        })
      }
    })
  },
  navToPersonalCenter: function (e) {
    wx.navigateTo({
      url: '/pages/personalCenter/personalCenter',
    })
  },
  getUserInfo: function () {
    var that = this;
    util.request("user/userInfo").then(function (res) {
      that.setData({
        userInfo: res.data
      })
    })
  },
  getOrderPatch: function () {
    let that = this
    util.request('patch/getOrderPatch').then(function (res) {
      if (res.data.length <= 0) {
        that.setData({
          hasOrderPatch: false
        })
      } else {
        that.setData({
          hasOrderPatch: true
        })
      }
      that.setData({
        orderPatch: res.data,
      })
    })
  },
  toFeedBack: function (e) {
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
            let curr = page[page.length - 1]
            if (curr.route === "pages/index/index") {
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
      url: '/pages/problemFeedback/problemFeedback?machineId=' + e.currentTarget.dataset.id
    })
  },
  toMachineDetail: function (e) {
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
            let curr = page[page.length - 1]
            if (curr.route === "pages/index/index") {
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
      url: '/pages/machineDetail/machineDetail?machineId=' + e.currentTarget.dataset.id
    })
  },
  toHere: function (e) {
    const machine = e.currentTarget.dataset.id;
    const endPoint = JSON.stringify({
      'name': machine.name,
      'latitude': machine.latitude,
      'longitude': machine.longitude
    });
    wx.navigateTo({
      url: 'plugin://routePlan/index?key=' + key + '&referer=' + referer + '&endPoint=' + endPoint
    })
  },
  openScan: function () {
    var that = this
    wx.scanCode({
      onlyFromCamera: true,
      success: function (res) {
        let qrUrl = decodeURIComponent(res.result)
        let sn = util.getQueryString(qrUrl, 'sn')
        if (sn != null || sn != '') {
          // 若初次登陆，扫码成功后，该设备为该用户的注册来源
          util.request('index/firstRegistration', {
            sn: sn
          })
          util.request('tissueMachine/queryBySn',{
            sn:sn
          }).then(function (result) {
            if(result.code === '0') {
              if(result.data.status === 1) {
                util.request('scan/getScan').then(function(res) {
                  if(res.data.imageUrl !== '' || res.data.imageUrl !== null) {
                    that.jumpToAdScan(sn)
                  } else {
                    var pageUrl = '/pages/result/result?stauts=' + status;
                    wx.navigateTo({
                      url: pageUrl,
                    })
                  }
                })
              }
            }
           else {
             var pageUrl = '/pages/machineErrorStatus/machineErrorStatus?sn=' + sn;
             wx.navigateTo({
               url: 'pageUrl',
             })
           }
            // that.jumpToAdScan(sn)
          })
          //先查询设备状态，正常再判断是否设置了广告再决定跳广告页面还是下单页面跳转到下单页
          util.request('tissueMachine/queryBySn', {
            sn: sn
          }).then(function (result) {
            if (result.code === 0) {
              if (result.data.status === 1) {
                util.request('scan/getScan').then(function (res) {
                  if (res.data.imageUrl !== '' || res.data.imageUrl !== null) {
                    that.jumpToAdScan(sn)
                  } else {
                    var pageUrl = '/pages/result/result?sn=' + sn;
                    wx.navigateTo({
                      url: pageUrl,
                    })
                  }
                })
              } else {
                var pageUrl = '/pages/machineErrorStatus/machineErrorStatus?sn=' +sn;
                wx.navigateTo({
                  url: pageUrl,
                })
              }
            }
          })
        }
      }
    })
  },

  jumpToAdScan: function (sn) {
    wx.redirectTo({
      url: '/pages/adScan/adScan?sn=' + sn,
    })
  },
  bindMapMakerCallouttap: function (e) {

    var latitude = this.data.latitude
    var longitude = this.data.longitude
    if (e.detail.markerId === -1) {
      wx.navigateTo({
        url: '/pages/nearMachine/nearMachine?latitude=' + latitude + '&longitude=' + longitude,
      })
    }
  },

})

