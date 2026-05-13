import numpy as np
import os
import pandas as pd
import random
import torch
from torch.utils.data import _utils
from matplotlib import pyplot as plt
import re
from Pre.LSTM import config_lstm
from Pre.LSTM.model import FeedBackDataset, LstmModel
import shutil

class LSTMLearner:
    def __init__(self, model_dir, device="cpu"):
        self.model_dir = model_dir
        self.device = torch.device(device)
        self.lr = config_lstm.lr

    def set_seed(self, seed):
        random.seed(seed)
        torch.manual_seed(seed)
        np.random.seed(seed)
        torch.cuda.manual_seed_all(seed)

    def generate_data(self, seq, TIMESTEPS):
        x = []
        y = []
        for i in range(len(seq) - TIMESTEPS):
            x.append([seq[i:i + TIMESTEPS]])
            y.append([seq[i + TIMESTEPS]])
        return np.array(x, dtype=np.float32), np.array(y, dtype=np.float32)

    def train_lstm_model(self, TIMESTEPS, train_x, train_y, epochs=500):
        train_x = train_x.reshape(-1, TIMESTEPS, 4)
        train_y = train_y.reshape(-1, 4)
        train_y = train_y[:, 1:]

        train_dataset = FeedBackDataset(train_x, train_y, mode='train')
        train_loader = torch.utils.data.DataLoader(train_dataset, batch_size=64, num_workers=0, shuffle=True)

        for i in range(5):
            model_path = os.path.join(self.model_dir, str(i) + '.pth')
            print('保存路径是:', model_path)
            model = LstmModel(4, 16, 64)
            model.to(self.device)
            self.train_model(model, train_loader, epochs, model_path)

    def train_model(self, model, train_loader, epochs, model_path):
        optimizer = torch.optim.Adam(model.parameters(), lr=self.lr)
        model.zero_grad()

        best_loss = 1000000000.0
        best_epcoh = 0
        for e in range(epochs):
            running_loss = 0
            count = 0
            for data in train_loader:
                model.train()
                x, y = data['x'], data['y'].reshape(data['x'].shape[0], 3)
                x = torch.tensor(x).to(self.device)
                y = torch.tensor(y).to(self.device)
                loss, y_pred = model(x, y)
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

                running_loss += loss
                count = count + 1
            loss_epoch = running_loss / count
            if e % 50 == 0:
                print('{} epoch的loss数值是{}'.format(e, loss_epoch.item()))
            if best_loss > loss_epoch:
                best_loss = loss_epoch
                best_epcoh = e
                torch.save(model.state_dict(), model_path)
        print('目前最好的模型分数是{}， {}'.format(best_loss, best_epcoh))
        print('保存成功，地址{}'.format(model_path))


