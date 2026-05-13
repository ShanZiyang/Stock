<template>
  <div class="pay">

    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#/" title="今日指数">
            <img src="@/assets/images/logo2.jpg" width="100%" alt="今日指数">
          </a>
        </h1>
        <div>
          <ul class="nav">
            <router-link to="/pay" tag="li" active-class="current" exact>
              <a>购买插件</a>
            </router-link>
            <router-link to="/orders" tag="li" active-class="current">
              <a>我的订单</a>
            </router-link>
            <router-link to="/bill" tag="li" active-class="current">
              <a>下载账单</a>
            </router-link>
          </ul>
        </div>
        <div class="clear"></div>
      </section>
    </header>

    <section id="index" class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">插件列表</span>
        </h2>
      </header>
      <ul>
        <li v-for="product in productList" :key="product.id">
          <a
              :class="[
              'orderBtn',
              { current: payOrder.productId === product.id },
            ]"
              @click="selectItem(product.id)"
              href="javascript:void(0);"
          >
            {{ product.title }}
            ¥{{ product.price / 100 }}
          </a>
        </li>
      </ul>

      <div class="PaymentChannel_payment-channel-panel">
        <h3 class="PaymentChannel_title">选择支付方式</h3>
        <div class="PaymentChannel_channel-options">
<!--          &lt;!&ndash; 选择微信 &ndash;&gt;-->
<!--          <div-->
<!--              :class="[-->
<!--              'ChannelOption_payment-channel-option',-->
<!--              { current: payOrder.payType === 'wxpay' },-->
<!--            ]"-->
<!--              @click="selectPayType('wxpay')"-->
<!--          >-->
<!--            <div class="ChannelOption_channel-icon">-->
<!--              <img src="@/assets/images/wxpay.png" class="ChannelOption_icon" />-->
<!--            </div>-->
<!--            <div class="ChannelOption_channel-info">-->
<!--              <div class="ChannelOption_channel-label">-->
<!--                <div class="ChannelOption_label">微信支付</div>-->
<!--                <div class="ChannelOption_sub-label"></div>-->
<!--                <div class="ChannelOption_check-option"></div>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->

          <!-- 选择支付宝 -->
          <div
              :class="[
              'ChannelOption_payment-channel-option',
              { current: payOrder.payType === 'alipay' },
            ]"
              @click="selectPayType('alipay')"
          >
            <div class="ChannelOption_channel-icon">
              <img src="@/assets/images/alipay.png" class="ChannelOption_icon" />
            </div>
            <div class="ChannelOption_channel-info">
              <div class="ChannelOption_channel-label">
                <div class="ChannelOption_label">支付宝</div>
                <div class="ChannelOption_sub-label"></div>
                <div class="ChannelOption_check-option"></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="payButtom">
        <el-button
            :disabled="payBtnDisabled"
            type="warning"
            round
            style="width: 280px; height: 44px; font-size: 18px"
            @click="toPay()"
        >
          确认支付（支付宝）
        </el-button>
<!--        <el-button-->
<!--            :disabled="payBtnDisabled"-->
<!--            type="warning"-->
<!--            round-->
<!--            style="width: 280px; height: 44px; font-size: 18px"-->
<!--            @click="toPayV2()"-->
<!--        >-->
<!--          确认支付（微信V2）-->
<!--        </el-button>-->
      </div>
    </section>

<!--    &lt;!&ndash; 微信支付二维码 &ndash;&gt;-->
<!--    <el-dialog-->
<!--        :visible.sync="codeDialogVisible"-->
<!--        :show-close="false"-->
<!--        @close="closeDialog"-->
<!--        width="350px"-->
<!--        center-->
<!--    >-->
<!--      <qriously :value="codeUrl" :size="300" />-->
<!--      &lt;!&ndash; <img src="../assets/img/code.png" alt="" style="width:100%"><br> &ndash;&gt;-->
<!--      使用微信扫码支付-->
<!--    </el-dialog>-->


    <!-- 公共底 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.atguigu.com" title="xiaoyang" target="_blank">杨森</a>
            </li>
          </ul>
          <div class="clear"/>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：123456789</span>
                <span>Email：952332000@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©xiaoyang</span>
              </section>
            </section>
          </section>
          <div class="clear"/>
        </div>
      </section>
    </footer>
  </div>


</template>

<script>
import productApi from '../../../api/pay/product'
import wxPayApi from '../../../api/pay/wxPay'
import aliPayApi from '../../../api/pay/aliPay'
import orderInfoApi from '../../../api/pay/orderInfo'


export default {
  name: "pay",
  data() {
    return {
      payBtnDisabled: false, //确认支付按钮是否禁用
      codeDialogVisible: false, //微信支付二维码弹窗
      productList: [], //商品列表
      payOrder: {
        //订单信息
        productId: '', //商品id
        payType: 'wxpay', //支付方式
      },
      codeUrl: '', // 二维码
      orderNo: '', //订单号
      timer: null, // 定时器
    }
  },

  //页面加载时执行
  created() {
    //获取商品列表

    productApi.list().then((response) => {
      // console.log(response.data.data)
      this.productList = response.data.data
      this.payOrder.productId = this.productList.id
    })
  },

  methods: {
    //选择商品
    selectItem(productId) {
      // console.log('商品id：' + productId)
      this.payOrder.productId = productId
      // console.log(this.payOrder)
      //this.$router.push({ path: '/order' })
    },

    //选择支付方式
    selectPayType(type) {
      // console.log('支付方式：' + type)
      this.payOrder.payType = type
      //this.$router.push({ path: '/order' })
    },

    //确认支付
    toPay() {
      //禁用按钮，防止重复提交
      this.payBtnDisabled = true

      //微信支付
      if (this.payOrder.payType === 'wxpay') {
        //调用统一下单接口
        wxPayApi.nativePay(this.payOrder.productId).then((response) => {
          this.codeUrl = response.data.codeUrl
          this.orderNo = response.data.orderNo

          //打开二维码弹窗
          this.codeDialogVisible = true

          //启动定时器
          this.timer = setInterval(() => {
            //查询订单是否支付成功
            this.queryOrderStatus()
          }, 3000)
        })

        //支付宝支付
      } else if (this.payOrder.payType === 'alipay') {

        //调用支付宝统一收单下单并支付页面接口
        aliPayApi.tradePagePay(this.payOrder.productId).then((response) => {
          //将支付宝返回的表单字符串写在浏览器中，表单会自动触发submit提交
          // console.log(response.data.data);
          document.write(response.data.data)
        })
      }
    },

    //确认支付
    toPayV2() {
      //禁用按钮，防止重复提交
      this.payBtnDisabled = true

      //微信支付
      if (this.payOrder.payType === 'wxpay') {
        //调用统一下单接口
        wxPayApi.nativePayV2(this.payOrder.productId).then((response) => {
          this.codeUrl = response.data.codeUrl
          this.orderNo = response.data.orderNo

          //打开二维码弹窗
          this.codeDialogVisible = true

          //启动定时器
          this.timer = setInterval(() => {
            //查询订单是否支付成功
            this.queryOrderStatus()
          }, 3000)
        })
      }
    },

    //关闭微信支付二维码对话框时让“确认支付”按钮可用
    closeDialog() {
      // console.log('close.................')
      this.payBtnDisabled = false
      // console.log('清除定时器')
      clearInterval(this.timer)
    },

    // 查询订单状态
    queryOrderStatus() {
      orderInfoApi.queryOrderStatus(this.orderNo).then((response) => {
        // console.log('查询订单状态：' + response.code)

        // 支付成功后的页面跳转
        if (response.code === 0) {
          // console.log('清除定时器')
          clearInterval(this.timer)
          // 三秒后跳转到支付成功页面
          setTimeout(() => {
            this.$router.push({ path: '/success' })
          }, 3000)
        }
      })
    },
  },
}
</script>