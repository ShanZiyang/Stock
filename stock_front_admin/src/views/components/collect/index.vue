<template>
  <div class="collect">
    <el-input v-model="secCode" placeholder="请输入股票代码"></el-input>
    <el-input v-model="secName" placeholder="请输入股票名称"></el-input>
    <el-button @click="onPredict">开始上传</el-button>

    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          prop="code"
          label="股票代码"
          width="180">
      </el-table-column>
      <el-table-column
          prop="label"
          label="股票名称"
          width="180">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { uploadStock, getList } from "../../../api/collect/collect";

export default {
  name: "collect",
  data() {
    return {
      options: [],
      selectedOption: '',
      secCode: '',
      secName: '',

      tableData: [], // 用于存储表格数据的数组
    };

  },
  created() {
    this.fetchOptions();
  },
  methods: {
    fetchOptions() {
      getList()
          .then(response => {
            const data = response.data.data.options;
            this.options = data;
            // 将预测结果数据赋值给 tableData 变量
            this.tableData = data;

          })
          .catch(error => {
            console.error("获取选项数据失败", error);
          });
    },
    onPredict() {
      // 调用后端接口，将输入的股票代码和名称传递给 getOneData API
      this.fetchOneData(this.secCode, this.secName);
    },

    fetchOneData(secCode, secName) {
      // 构造股票数据对象
      const data = {
        secCode: secCode,
        secName: secName
      };

      // 调用 getOneData API 并传递输入的股票代码和名称
      uploadStock(data)
          .then(response => {
            // 处理成功响应
            // console.log("获取数据成功", response);

            // 假设API返回了股票数据对象

            const secCode = response.data.data.secCode;
            const secName = response.data.data.secName;

            // 构造一个包含 secCode 和 secName 的对象
            const secCodeAndName = {
              code: secCode,
              label: secName
            };
            // console.log(secCodeAndName)

            // 添加 secCodeAndName 对象到表格数据数组
            this.tableData.push(secCodeAndName);


          })
          .catch(error => {
            // 处理错误
            console.error("获取数据失败", error);
          });
    }
  }
};
</script>
