import os
import shutil

from Pre.config import commonConfig


if not os.path.exists(commonConfig.exc_path):
    os.makedirs(commonConfig.exc_path)
else:
    shutil.rmtree(commonConfig.exc_path)
    os.makedirs(commonConfig.exc_path)
code ="sh.600000"
name = "浦发银行"
from Pre.utils.get_data import  get_data
df = get_data(code)
stock_dict = dict(zip(code, name))
stock_name = stock_dict.get(code, "Unknown")
parts = code.split(".")
codename = parts[1]
df["company_name"] = stock_name
exc_path = os.path.join(commonConfig.exc_path, f"{codename}.xlsx")
# print(exc_path)
df.to_excel(exc_path)
# print(df)
print(f"{codename}.xlsx""保存成功")