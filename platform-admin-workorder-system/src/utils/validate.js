/**
 * 判断是否是iPhone手机
 */
export function isIOS() {
  if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
    // 苹果端
    return true
  } else {
    return false
  }
}

/**
 * 判断是否是安卓手机
 */
export function isAndroid() {
  if (/(Android)/i.test(navigator.userAgent)) {
    // 安卓手机
    return true
  } else {
    return false
  }
}

/**
 * 是否是微信
 */
export function isWeiXin() {
  let ua = window.navigator.userAgent.toLowerCase()
  if (ua.match(/MicroMessenger/i) == 'micromessenger') {
    return true
  } else {
    return false
  }
}
