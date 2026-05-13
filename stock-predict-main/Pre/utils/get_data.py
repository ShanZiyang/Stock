from datetime import datetime
import baostock as bs
import numpy as np

def get_data(code):
    end = datetime.now()
    start = datetime(end.year - 1, end.month, end.day).strftime('%Y-%m-%d')
    end = end.strftime('%Y-%m-%d')
    # 登陆系统
    bs.login()
    # 获取沪深A股历史K线数据
    rs_result = bs.query_history_k_data_plus(
            code=code,
            fields="date,open,high,low,close,volume",
            start_date=start,
            end_date=end,
            frequency="d",
            adjustflag="3")
    df_result = rs_result.get_data()
    # 登出系统
    bs.logout()
    df_result['date'] = df_result['date'].map(lambda x: datetime.strptime(x,'%Y-%m-%d'))
    _res = df_result.set_index('date')
    _res = _res.replace('', np.nan)
    _res = _res.astype(float)
    return _res


