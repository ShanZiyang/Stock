import numpy as np
import os
import pandas as pd
import random
import torch
from matplotlib import pyplot as plt
import re
from Pre.LSTM import config_lstm
from Pre.LSTM.model import LstmModel


def set_seed(seed):
    random.seed(seed)
    torch.manual_seed(seed)
    np.random.seed(seed)
    torch.cuda.manual_seed_all(seed)


set_seed(123)


def LSTM_predict(code, days=None):
    dirs = os.path.join('./model', code)
    print('dirs--------', dirs)
    device = torch.device("cpu")

    path = r'G:\python\stock-predict-main\Pre\excel'
    shuju = pd.read_excel(os.path.join(path, f"{code}.xlsx"))
    pattern = re.compile(r' ')
    cols = [re.sub(pattern, '', col) for col in list(shuju.columns)]
    shuju.columns = cols

    save_cols = ['date', 'open', 'high', 'low', 'close']
    shuju = shuju[save_cols]
    data = shuju[['open', 'high', 'low', 'close']]
    data_array = data.values

    TIMESTEPS = 5

    end_res = []

    if days is None:
        default_days = ['5day', '15day', '30day']
    else:
        default_days = [days]

    for days in default_days:
        print('~~~~~~~~~~~~~~~~~~~')
        print(f'预测{days}的情况：')

        TIMESTEPS = int(days.split('day')[0])
        predict_data = data_array[-TIMESTEPS:]
        predict_data = predict_data.reshape(-1, TIMESTEPS, 4)

        model = LstmModel(4, 16, 64)
        res = []

        for i in range(5):
            path_model = os.path.join(dirs, days, str(i) + '.pth')
            model.load_state_dict(torch.load(path_model))
            model.to(device)

            model.eval()
            x = predict_data
            x = torch.tensor(x, dtype=torch.float32).to(device)
            pred = model(x, y=None)
            print(pred[0])
            res.append(pred[0].detach().numpy())

        res = np.array(res).reshape(-1, 3)
        mean_res = np.mean(res, axis=0)
        print('{}的最高价均值:{},最低价均值：{}，收盘价均值：{}'.format(days, mean_res[0], mean_res[1], mean_res[2]))
        end_res.append(mean_res[2].tolist())

    return end_res

LSTM_predict("600000")