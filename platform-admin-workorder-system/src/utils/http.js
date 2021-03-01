/**
 * 基于axios封装的http请求
 */
import axios from 'axios'

let $http = axios.create({
  timeout: 1000 * 30,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

if (process.env.NODE_ENV === 'development') {
  // $http.defaults.baseURL = '/api'
  $http.defaults.baseURL = '/api'
} else if (process.env.NODE_ENV === 'production') {
  $http.defaults.baseURL = 'http://172.19.0.12:10889/api'
}

// 请求拦截器
$http.interceptors.request.use(
  config => {
    config.headers['token'] = sessionStorage.getItem('wxtoken') // 请求头带上
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
$http.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    if (response.data && response.data.code === 401) {
      // 401, token失效
    }
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

// get 请求
$http.httpGet = (url, params = {}) => {
  return new Promise((resolve, reject) => {
    $http
      .get(url, {
        params
      })
      .then(res => {
        resolve(res.data)
      })
      .catch(err => {
        reject(err)
      })
  })
}

// post请求
$http.httpPost = (url, data = {}, params = {}) => {
  return new Promise((resolve, reject) => {
    $http({
      url,
      method: 'post',
      // 发送的数据
      data,
      // url参数
      params
    })
      .then(res => {
        resolve(res.data)
      })
      .catch(err => {
        reject(err)
      })
  })
}

export default $http
