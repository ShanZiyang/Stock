// axios 发送ajax请求
// import request from '@/utils/request'
import axios from '@/api'

export default{

  //Native下单
  nativePay(productId) {
    return axios.request({
      url: '/wx-pay/native/' + productId,
      method: 'post'
    })
  },

  //Native下单(v2)
  nativePayV2(productId) {
    return axios.request({
      url: '/wx-pay-v2/native/' + productId,
      method: 'post'
    })
  },

  cancel(orderNo) {
    return axios.request({
      url: '/wx-pay/cancel/' + orderNo,
      method: 'post'
    })
  },

  refunds(orderNo, reason) {
    return axios.request({
      url: '/wx-pay/refunds/' + orderNo + '/' + reason,
      method: 'post'
    })
  }
}
