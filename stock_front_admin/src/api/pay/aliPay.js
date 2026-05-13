// axios 发送ajax请求
// import request from '@/utils/request'
import axios from '@/api'

export default{

  //发起支付请求
  tradePagePay(productId) {
    return axios.request({
      url: '/ali-pay/trade/page/pay/' + productId,
      method: 'post'
    })
  },

  cancel(orderNo) {
    return axios.request({
      url: '/ali-pay/trade/close/' + orderNo,
      method: 'post'
    })
  },

  refunds(orderNo, reason) {
    return axios.request({
      url: '/ali-pay/trade/refund/' + orderNo + '/' + reason,
      method: 'post'
    })
  }
}
