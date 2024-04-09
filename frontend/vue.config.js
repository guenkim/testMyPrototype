module.exports = {
  devServer: {
    proxy: {
      '/api':{
        "target":'http://localhost:8082', // Spring boot의 주소 및 포트
        changeOrigin: true,
        ws:true
      }
    }
  }
}
