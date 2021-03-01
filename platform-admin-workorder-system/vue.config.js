module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/mp/' : '/',
  devServer: {
    proxy: {
      "/api": {
        // 不要直接修改这里
        // 如果要修改API路径，应在项目根目录下创建".env.local"文件
        // 然后写入本地变量PROXY_API_URL
        // 例如：PROXY_API_URL = http://demo.open.renren.io/renren-fast-server/
        // 注意：".env.local"文件不需要添加到Git
        // 具体参考 https://cli.vuejs.org/zh/guide/mode-and-env.html
        target: process.env.VUE_APP_API_URL,
        pathRewrite: { "^/api": "" },
        logLevel: "info",
        ws: true,
        changeOrigin: true
      },
      "/location": {
        target: "https://apis.map.qq.com/",
        pathRewrite: { "/location": "" },
        changeOrigin: true
      }
    },
    disableHostCheck: true
  }
}
