import json
import subprocess
from datetime import datetime

import pymysql.cursors

from flask import Flask, request, jsonify
from excel_utils import to_excel
from Pre.LSTM.lstm_predict import LSTM_predict

app = Flask(__name__)

# 配置数据库连接
cnn = pymysql.connect(host='127.0.0.1',
                      user='YOUR_DB_USER',
                      password='YOUR_DB_PASSWORD',
                      port=3306,
                      charset='utf8',
                      db='stock_sys_db')


@app.route('/getData', methods=['post'])
def getData():
    cursor = cnn.cursor()
    cursor.execute('select sec_code ,sec_name from stock_business')

    data = cursor.fetchall()
    code = [row[0] for row in data]
    name = [row[1] for row in data]
    cursor.close()
    cnn.close()

    to_excel(code,name)
    res = {
        "code": 200,
        "msg": "数据获取成功",
        "data": {}
    }
    return jsonify(res)


@app.route('/train_stock', methods=['POST'])
def train_stock():
    from Pre.LSTM import lstm_train

    res = {
        "code": 200,
        "msg": "模型训练成功",
        "data": {}
    }
    return jsonify(res)


@app.route('/predict_stock', methods=['POST'])
def predict_stock():
    code = request.json.get('code')
    days = request.json.get('day')
    # print(code)
    # print(days)
    predict = LSTM_predict(code, days)
    # print(predict)
    res = {
        "code": 200,
        "msg": "股票预测成功",
        "data": predict
    }

    return jsonify(res)


@app.route('/uploadStock', methods=['post'])
def uploadStock():
    data = request.get_json()
    stock_name = data.get('stockName')
    stock_code = data.get('stockCode')

    # 获取当前时间
    current_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    # print(stock_name,stock_code)

    try:
        # 使用cursor()方法获取操作游标
        cursor = cnn.cursor()

        # 构建SQL插入语句，注意要使用参数化查询，以防止SQL注入
        insert_query = "INSERT INTO stock_business (sec_code, sec_name,update_time) VALUES (%s, %s,%s)"
        cursor.execute(insert_query, (stock_code, stock_name, current_time))
        cnn.commit()

        cursor.close()

        res = {
            "code": 200,
            "msg": "数据插入成功",
            "data": {}
        }
        return jsonify(res)

    except Exception as e:
        cnn.rollback()  # 回滚事务
        res = {
            "code": 500,
            "msg": "数据插入失败",
            "data": {}
        }
        return jsonify(res)


if __name__ == '__main__':
    app.run(port=5000)
