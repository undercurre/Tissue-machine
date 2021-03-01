// pages/problemFeedback/problemFeedback.js
var util = require("../../utils/util");
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    form: {
      machineId: "",
      picturePath: [],
      introduce: "",
    },
    head: [],
    textNumber: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      'form.machineId': options.machineId
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
  bindIntroduceInput: function (res) {
    var form = this.data.form;
    form.introduce = res.detail.value;
    this.setData({
      form: form,
      textNumber: form.introduce.length
    })
    
  },
  /* 上传照片 */
  upload: function () {
    var _this = this;
    wx.showActionSheet({
      itemList: ['拍照', '从手机相册选择'],
      success: function (res) {
        let sourceType = "camera";
        if (res.tapIndex == 0) {
          sourceType = "camera"; //相机
        } else if (res.tapIndex == 1) {
          sourceType = "album"; //相册
        }
        wx.chooseImage({
          count: 4, // 默认9      
          sizeType: ['original', 'compressed'],
          // 指定是原图还是压缩图，默认两个都有      
          sourceType: [sourceType],
          success: function (res) {
            var tempFilePaths = res.tempFilePaths;
            var head = tempFilePaths;
            var token = wx.getStorageSync('token')
            //图片路径为 https
            var form = _this.data.form;
            // 返回选定照片的本地文件路径tempFilePath可以作为img标签的src属性显示图片        
            _this.setData({
              head: tempFilePaths
            })
            for (let i in head) {
              //文件上传
              wx.uploadFile({
                filePath: head[i],
                name: 'file',
                // url: 'http://172.19.0.113:8889/platform-api/app/upload/upload', //本机地址
                url: 'https://tissue.xinglian.info/platform-api/app/upload/upload',
                formData: {
                  'token': token
                },
                header: {
                  'content-type': 'multipart/form-data'
                }, // 设置请求的 header
                success(res) {
                  var data = JSON.parse(res.data) //注：wx.uploadFile返回的是[字符串]，需要自己转为JSON格式
                  form.picturePath.push(data.url)
                  _this.setData({
                    form: form
                  })
                }
              })
            }
          }
        })
      },
      fail: function (res) {
        console.log(res.errMsg)
      }
    })
  },
  removePic: function (res) {
    var form = this.data.form;
    var index = this.data.form.picturePath.indexOf(res.target.dataset.id);
    form.picturePath.splice(index, 1);
    this.setData({
      form: form
    })
  },
  submitForm: function (res) {
    var that = this;
    util.request("problemFeedBack/submitProblem",{
      "machineId": that.data.form.machineId,
      "introduce": that.data.form.introduce,
      "picturePath": that.data.form.picturePath
    },"post").then(function (res) {
      if(res.code === 0){
        wx.showModal({
          title: '提示',
          content: '感谢您的反馈，祝您生活愉快',
          showCancel:false,
          success (res) {
            if (res.confirm) {
              setTimeout(function () {
                wx.reLaunch({
                url: "/pages/index/index"
              })
            }, 1000)
            }
          }
        })
       /*  setTimeout(function () {
            wx.reLaunch({
            url: "/pages/index/index"
          })
        }, 1000) */
      }
    })
  }
})