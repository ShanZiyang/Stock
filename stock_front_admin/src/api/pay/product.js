// axios 发送ajax请求
import request from '@/utils/request'
import axios from '@/api'

export default{

  //查询商品列表
  list() {
    return axios.request({
      url: '/product/list',
      method: 'get'
    })
  }
}
