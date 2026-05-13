import matplotlib.pyplot as plt

# 提供的epoch和loss数值
epochs = [0, 50, 100, 150, 200, 250, 300, 350, 400, 450]
loss_values = [53.39, 0.42, 0.30, 0.32, 0.29, 0.26, 0.23, 0.25, 0.19, 0.27]

# 绘制损失曲线
plt.figure(figsize=(10, 5))
plt.plot(epochs, loss_values, marker='o', linestyle='-')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.title('Training Loss Curve')
plt.grid(True)
plt.show()
