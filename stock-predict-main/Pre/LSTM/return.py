
# ①买入：如果 T+1 天预测最高价超过 T 天收盘价的 102%即预计收益高于 2%时，
# 则买入并于 T+1 天卖出，
# ②卖出：限制卖出只有以下两种操作：第一，当 T+1 天最高价大于等于 T+1 天预
# 测最高价，则在 T+1 天预测最高价实现买卖；第二，当 T+1 天最高价小于 T+1 天预测
# 最高价，则在 T+1 天收盘价实现买卖。
# ③其他情况下则不进行股票交易。
# 根据上文预测的数据进行计算，现假设每次买卖 100 手股票，股票单位股价为 1
# 元时，每次操作成本为 20 元（主要指有交易费用和印花税）

# 假设的数据
predicted_high_prices = [7.088335514,7.108604431,7.134804726,7.034081936,7.024304867,7.037112236]  # 预测的最高价数据，以列表形式表示
closing_prices = [7.13,7.1,7.03,7.01,7.02,7.14]  # 收盘价数据，以列表形式表示

# 参数
buy_sell_unit = 100  # 每次买卖的股票手数
cost_per_transaction = 20  # 每次操作的成本，包括交易费用和印花税

# 初始化
balance = 0  # 初始余额
shares = 0  # 持有的股票数量
total_return = 0  # 总收益

# 模拟交易
for t in range(len(predicted_high_prices) - 1):
    predicted_high = predicted_high_prices[t + 1]
    closing_price = closing_prices[t]

    # 买入条件
    if predicted_high > closing_price * 1.02:
        cost = (buy_sell_unit * closing_price) + cost_per_transaction
        balance -= cost
        shares += buy_sell_unit

    # 卖出条件
    if predicted_high >= predicted_high_prices[t + 1]:
        cost = (buy_sell_unit * predicted_high_prices[t + 1]) + cost_per_transaction
        balance += cost
        shares -= buy_sell_unit
    elif predicted_high < predicted_high_prices[t + 1]:
        cost = (buy_sell_unit * closing_prices[t + 1]) + cost_per_transaction
        balance += cost
        shares -= buy_sell_unit

    total_return = balance + (shares * closing_price)
total_return_percent = (total_return / (buy_sell_unit * closing_prices[0])) * 100
# 总收益
print(f"Total Return: {total_return:.2f} 元")
#收益率百分比
print(f"Total Return Rate: {total_return_percent:.2f}%")