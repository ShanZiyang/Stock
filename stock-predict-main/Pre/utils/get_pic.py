import os.path
import shutil
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from Pre.config import commonConfig as config



class StockAnalysis:
    def __init__(self, exc_path):
        self.exc_path = exc_path
        self.df = pd.read_excel(exc_path)


        if not os.path.exists(config.pic_save_path):
            os.makedirs(config.pic_save_path)
        else:
            shutil.rmtree(config.pic_save_path)
            os.makedirs(config.pic_save_path)

        self.model_path_c = os.path.join(config.pic_save_path, f"{config.company_name}的历史收盘价")
        self.model_path_t = os.path.join(config.pic_save_path, f"{config.company_name}的每日交易量")
        self.model_path_avg = os.path.join(config.pic_save_path, f"{config.company_name}的移动平均线")
        self.model_path_return_sca = os.path.join(config.pic_save_path, f"{config.company_name}的日回报率散点图")
        self.model_path_return_his = os.path.join(config.pic_save_path, f"{config.company_name}的日回报率直方图")
        self.model_path_cp = os.path.join(config.pic_save_path, f"{config.company_name}的自身收益率")

        sns.set_style('whitegrid')
        plt.style.use("fivethirtyeight")
        plt.rcParams['font.sans-serif'] = ['SimHei']

    def close_price_pic(self):
        plt.figure(figsize=(15, 8))
        self.df['close'].plot()
        plt.ylabel('close')
        plt.xlabel(None)
        plt.title(f"{config.company_name}的历史收盘价")
        plt.savefig(self.model_path_c)


    def daily_trading_pic(self):
        plt.figure(figsize=(15, 8))
        self.df['volume'].plot()
        plt.ylabel('volume')
        plt.xlabel(None)
        plt.title(f"{config.company_name}的每日交易量")
        plt.savefig(self.model_path_t)


    def migAeage_pic(self):
        ma_day = [10, 20, 50]
        for ma in ma_day:
            column_name = f"MA for {ma} days"
            self.df[column_name] = self.df['close'].rolling(ma).mean()
        plt.figure(figsize=(15, 8))
        self.df[['close', 'MA for 10 days', 'MA for 20 days', 'MA for 50 days']].plot()
        plt.title(f"{config.company_name}的移动平均线")
        plt.savefig(self.model_path_avg)



    def daily_return_sca(self):
        self.df['Daily Return'] = self.df['close'].pct_change()
        plt.figure(figsize=(15, 8))
        self.df['Daily Return'].plot(legend=True, linestyle='--', marker='o')
        plt.title(f"{config.company_name}的日回报率散点图")
        plt.savefig(self.model_path_return_sca)
        plt.show()



    def daily_return_his(self):
        self.df['Daily Return'] = self.df['close'].pct_change()
        plt.figure(figsize=(15, 8))
        sns.distplot(self.df['Daily Return'].dropna(), bins=100, color='purple')
        plt.ylabel('Daily Return')
        plt.title(f"{config.company_name}的日回报率直方图")
        plt.savefig(self.model_path_return_his)
        plt.show()



    def comp(self):
        closing_df = pd.DataFrame()
        temp_df = pd.DataFrame(index=self.df['date'], data=self.df['close'].values, columns=[config.company_name])
        closing_df = pd.concat([closing_df, temp_df], axis=1)
        sns.jointplot(x=config.company_name, y=config.company_name,
                      data=closing_df, kind='scatter',
                      color='seagreen')
        plt.savefig(self.model_path_cp)
        plt.show()


# 使用示例
exc_path = os.path.join(config.exc_path, f"{config.company_name}.xlsx")
stock_analyzer = StockAnalysis(exc_path)
stock_analyzer.close_price_pic()
stock_analyzer.daily_trading_pic()
stock_analyzer.migAeage_pic()
stock_analyzer.daily_return_sca()
stock_analyzer.daily_return_his()
stock_analyzer.comp()
