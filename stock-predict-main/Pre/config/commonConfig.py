# liquor_list = ['sh.600000']
import pandas as pd
import os
from pathlib import Path
import pymysql.cursors

#从数据库获取最新的股票代码和股票名称
cnn = pymysql.connect(host='127.0.0.1',
                      user='YOUR_DB_USER',
                      password='YOUR_DB_PASSWORD',
                      port=3306,
                      charset='utf8',
                      db='stock_sys_db')

cursor = cnn.cursor()
cursor.execute('select sec_code ,sec_name from stock_business')
data = cursor.fetchall()
code = [row[0] for row in data]
company_name = [row[1] for row in data]
cursor.close()
cnn.close()

#保存路径
path = os.getcwd()
# print(path)
path11 =os.path.dirname(path)
# print(path11)
path2 = os.path.join(path11, 'data.xlsx')
# print(path2)

pic_save_path = os.path.join(path11,'pic')
exc_path = os.path.join(path11,'excel')




