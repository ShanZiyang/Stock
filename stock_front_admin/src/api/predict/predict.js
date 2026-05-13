import axios from '@/api'



//获取股票
export const predictStock = (data) => {
    return axios.request({
        url: '/predict/predict_stock',
        method: 'post',
        data: data
    });
};
