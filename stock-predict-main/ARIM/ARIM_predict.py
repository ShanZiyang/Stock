import numpy as np
import matplotlib.pyplot as plt
# import tensorflow as tf
import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
# 如果是tensorflow2版本就取消这行注释

date = np.linspace(1, 30, 30)  #
beginPrice = np.array([2923.19, 2928.06, 2943.92, 2946.26, 2944.40, 2920.85, 2861.33, 2854.58, 2776.69, 2789.02,
                       2784.18, 2805.59, 2781.98, 2798.05, 2824.49, 2762.34, 2817.57, 2835.52, 2879.08, 2875.47,
                       2887.66, 2885.15, 2851.02, 2879.52, 2901.63, 2896.00, 2907.38, 2886.94, 2925.94, 2927.75])
endPrice = np.array([2937.36, 2944.54, 2941.01, 2952.34, 2932.51, 2908.77, 2867.84, 2821.50, 2777.56, 2768.68,
                     2794.55, 2774.75, 2814.99, 2797.26, 2808.91, 2815.80, 2823.82, 2883.10, 2880.00, 2880.33,
                     2883.44, 2897.43, 2863.57, 2902.19, 2893.76, 2890.92, 2886.24, 2924.11, 2930.15, 2957.41])

for i in range(0, 30):  # 画柱状图
    dateOne = np.zeros([2])
    dateOne[0] = i
    dateOne[1] = i
    priceOne = np.zeros([2])
    priceOne[0] = beginPrice[i]
    priceOne[1] = endPrice[i]
    if endPrice[i] > beginPrice[i]:
        plt.plot(dateOne, priceOne, 'r', lw=6)
    else:
        plt.plot(dateOne, priceOne, 'g', lw=6)
plt.xlabel("date")
plt.ylabel("price")
# plt.show()

dateNormal = np.zeros([30, 1])
priceNormal = np.zeros([30, 1])
# 归一化
for i in range(0, 30):
    dateNormal[i, 0] = i / 29.0
    priceNormal[i, 0] = endPrice[i] / 3000.0

x = tf.placeholder(tf.float32, [None, 1])
y = tf.placeholder(tf.float32, [None, 1])
# X->hidden_layer
w1 = tf.Variable(tf.random_uniform([1, 25], 0, 1))
b1 = tf.Variable(tf.zeros([1, 25]))
wb1 = tf.matmul(x, w1) + b1
layer1 = tf.nn.relu(wb1)  # 激励函数
# hidden_layer->output
w2 = tf.Variable(tf.random_uniform([25, 1], 0, 1))
b2 = tf.Variable(tf.zeros([30, 1]))
wb2 = tf.matmul(layer1, w2) + b2
layer2 = tf.nn.relu(wb2)
loss = tf.reduce_mean(tf.square(y - layer2))  # y为真实数据， layer2为网络预测结果
# 梯度下降
train_step = tf.train.GradientDescentOptimizer(0.1).minimize(loss)
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    for i in range(0, 20000):
        sess.run(train_step, feed_dict={x: dateNormal, y: priceNormal})
    # 预测， X  w1w2 b1b2 -->layer2
    pred = sess.run(layer2, feed_dict={x: dateNormal})
    date1 = np.linspace(0, 29, 30)  #
    plt.plot(date1, pred*3000, 'b', lw=3)
plt.show()
