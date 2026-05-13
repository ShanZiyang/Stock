import numpy as np
from sklearn import metrics

# real_stock_price = [7.13,7.1,7.03,7.01,7.02,7.14]
# predicted_stock_price = [7.088335514,7.108604431,7.134804726,7.034081936,7.024304867,7.037112236]#5day
real_stock_price = np.array([7.13, 7.1, 7.03, 7.01, 7.02, 7.14])
predicted_stock_price = np.array([7.088335514, 7.108604431, 7.134804726, 7.034081936, 7.024304867, 7.037112236])
# predicted_stock_price = [7.096248627,7.138045311,7.076424599,7.09612369,7.078661919,7.094450951]#15day
# predicted_stock_price = [7.1602325439453125,7.132423401,7.175432682,7.16023922,7.16196537,7.150107384]#30day


MSE = metrics.mean_squared_error(predicted_stock_price, real_stock_price)
# MSE1 = np.sum((predicted_stock_price - real_stock_price)**2) / len(real_stock_price)
RMSE = metrics.mean_squared_error(predicted_stock_price, real_stock_price) ** 0.5
MAE = metrics.mean_absolute_error(predicted_stock_price, real_stock_price)
R2 = metrics.r2_score(predicted_stock_price, real_stock_price)

# 均方误差 (MSE) 是预测值与真实值之间差异的平方的均值，用于衡量模型的精度，MSE越低表示模型越准确。
# 均方根误差 (RMSE) 是MSE的平方根，它以与原始数据相同的度量单位表示，更容易解释。
# 平均绝对误差 (MAE) 是预测值与真实值之间差异的绝对值的平均，也用于衡量模型的精度，MAE越低表示模型越准确。
# R²分数（决定系数）衡量了模型对方差的解释程度，取值范围从-∞到1，R²越接近1表示模型对数据的拟合越好，而R²越接近0表示模型的拟合效果差。


print('均方误差: %.5f' % MSE)
# print('均方误差: %.5f' % MSE1)
print('均方根误差: %.5f' % RMSE)
print('平均绝对误差: %.5f' % MAE)
print('R2: %.5f' % R2)




