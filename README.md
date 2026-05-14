# Stock

本科毕业设计项目 理财学习模拟平台。
Stock is a stock prediction platform composed of:

- `stock_parent`: Java Maven parent project, including the Spring Boot backend and XXL-JOB admin module.
- `stock_front_admin`: Vue admin frontend for stock dashboards, prediction views, users, roles, logs, Swagger, SQL monitor, and payment pages.
- `stock-predict-main`: Python stock prediction experiments and utilities, including BP, LSTM, and ARIMA scripts plus sample datasets/models.

Before running in a real environment, update the placeholder values in configuration files with local database, OSS, and payment sandbox credentials.

