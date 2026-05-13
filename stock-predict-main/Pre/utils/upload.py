import qiniu
from Pre.config import qiniuConfig, commonConfig
from Pre.config import commonConfig as config

class QiniuUploader:
    def __init__(self):
        self.access_key = qiniuConfig.AK
        self.secret_key = qiniuConfig.SK
        self.bucket_name = qiniuConfig.bucket_name

        self.q = qiniu.Auth(self.access_key, self.secret_key)

    def upload_excel(self, stock_name):
        key = f"stock_excel/{stock_name}"
        local_file_path = f"../excel/{stock_name}.xlsx"
        up_token = self.q.upload_token(self.bucket_name, key)
        qiniu.put_file(up_token, key, local_file_path)
        print(f"{stock_name}.xlsx 上传成功")

    def upload_pic(self, name):
        key = f"stock_pic/{name}"
        local_file_path = f"../pic/{name}.png"
        up_token = self.q.upload_token(self.bucket_name, key)
        qiniu.put_file(up_token, key, local_file_path)
        print(f"{name}.png 上传成功")

# 使用示例
uploader = QiniuUploader()
for stock_name in commonConfig.company_name:
    uploader.upload_excel(stock_name)

# 以某个名字为例，上传图片
# uploader.upload_pic(f"{config.company_name}的历史收盘价")
# uploader.upload_pic(f"{config.company_name}的每日交易量")
# uploader.upload_pic(f"{config.company_name}的移动平均线")
# uploader.upload_pic(f"{config.company_name}的日回报率散点图")
# uploader.upload_pic(f"{config.company_name}的日回报率直方图")
# uploader.upload_pic(f"{config.company_name}的自身收益率")


