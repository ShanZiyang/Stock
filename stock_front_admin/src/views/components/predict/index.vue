<template>
  <div class="predict">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="股票名称">
        <el-select v-model="formInline.stockName" placeholder="股票名称">
          <el-option v-for="option in stockOptions" :key="option.value" :label="option.label"
                     :value="option.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基准天数">
        <el-select v-model="formInline.day" placeholder="基准天数">
          <el-option label="5天" value="5day"></el-option>
          <el-option label="15天" value="15day"></el-option>
          <el-option label="30天" value="30day"></el-option>
        </el-select>
      </el-form-item>

<!--      <el-form-item label="预测方法">-->
<!--        <el-select v-model="formInline.way" placeholder="预测方法">-->
<!--          <el-option label="LSTM" value="lstm"></el-option>-->
<!--          <el-option label="BP" value="bp"></el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->

      <el-form-item>
        <el-button type="primary" @click="onSubmit">开始预测</el-button>
      </el-form-item>
    </el-form>


    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          prop="day"
          label="基准天数"
          width="180">
      </el-table-column>
      <el-table-column
          prop="date"
          label="日期"
          width="180">
      </el-table-column>
      <el-table-column
          prop="name"
          label="股票名称"
          width="180">
      </el-table-column>
      <el-table-column
          prop="res"
          label="预测值">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getList} from "../../../api/collect/collect";
import {predictStock} from "../../../api/predict/predict";

export default {
  data() {
    return {
      formInline: {
        user: '',
        region: '',
        day: '',
        // way: '',
        stockName: "",//选择的股票名称

      },
      stockOptions: [],//存储股票名称选项
      tableData: [], // 用于存储表格数据的数组

    }
  },
  created() {
    // 在组件创建时获取股票名称列表
    this.fetchStockOptions();
  },
  methods: {
    fetchStockOptions() {
      // 调用后端接口获取股票名称选项
      getList()
          .then(response => {
            this.stockOptions = response.data.data.options.map(option => ({
              label: option.label,
              value: option.value
            }));
          })
          .catch(error => {
            console.error("Failed to fetch stock options:", error);
          });
    },

    onSubmit() {
      // console.log('submit!');
      const selectedOption = this.stockOptions.find(option => option.value === this.formInline.stockName);
      // console.log(selectedOption)
      if (selectedOption) {
        const formData = {
          stockName: selectedOption.label, // 使用选项的名称
          day: this.formInline.day,
          // way: this.formInline.way
        };

        predictStock(formData)
            .then(response => {
              // console.log("res:",response);
              // 处理后端返回的预测结果
              // console.log('Prediction result:', response.data.predictionResult);

              // 将预测结果数据赋值给 tableData 变量
              this.tableData = response.data.data;
            })
            .catch(error => {
              console.error('Error:', error);
            });

      }
    },

  }
}
</script>