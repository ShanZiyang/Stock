// import request from '@/utils/request'
import axios from '@/api'

export default{
 
  //查询订单列表
  list() {
    return axios.request({
      url: '/order-info/list',
      method: 'get'
    })
  },

  queryOrderStatus(orderNo) {
    return axios.request({
      url: '/order-info/query-order-status/' + orderNo,
      method: 'get'
    })
  }
}
