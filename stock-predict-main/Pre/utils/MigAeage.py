import os

import pandas as pd
from matplotlib import pyplot as plt

from Pre import config

exc_path = os.path.join(config.exc_save_path, f"{config.company_name}.xlsx")

df = pd.read_excel(exc_path)
print(df)
# company = config.company_name
# 设置移动天数
ma_day = [10, 20, 50]
for ma in ma_day:
    column_name = f"MA for {ma} days"
    df[column_name] = df['close'].rolling(ma).mean()

fig, axes = plt.subplots(nrows=2, ncols=2)
fig.set_figheight(8)
fig.set_figwidth(15)
df[['close', 'MA for 10 days', 'MA for 20 days', 'MA for 50 days']].plot(ax=axes[0,0])
axes[0,0].set_title('浦发银行')
fig.tight_layout()
