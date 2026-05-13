// import request from '@/utils/request'
import axios from '@/api'

export default{

  downloadBillWxPay(billDate, type) {
    return axios.request({
      url: '/wx-pay/downloadbill/' + billDate + '/' + type,
      method: 'get'
    })
  },

  downloadBillAliPay(billDate, type) {
    return axios.request({
      url: '/ali-pay/bill/downloadurl/query/' + billDate + '/' + type,
      method: 'get'
    })
  }
}
