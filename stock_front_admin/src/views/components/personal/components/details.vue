<template>
  <div class="personal-details" style="overflow:hidden">
    <div class="left" style="display:flex;align-items: center;margin-left:10%">
        <!-- 头像上传控件，只有在编辑模式下才显示 -->
        <el-upload
            class="avatar-uploader"
            name="img"
            action="http://127.0.0.1:8080/api/uploadImg"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"

        >
          <img
              :src="form.formFields.avatar?form.formFields.avatar:avatarImage"
              @error="handleImageError"
              width="100%"
              height="100%"
              class="avatar"
          >
          <i v-if="form.formFields.avatar" class="avatar-uploader-icon"></i>
          <div slot="tip" class="el-upload__tip" style="margin-top:15px;margin-left: -50px">点击上传头像，只能上传jpg/png文件，且不超过1mb</div>
        </el-upload>

    </div>
    <div class="right" style="display:inline-block;vertical-align:middle">
      <component-form :data="form">
        <template slot="sex">
          <span v-if="form.formFields.sex==1">男</span>
          <span v-if="form.formFields.sex==2">女</span>
        </template>
        <template slot="status">
          <span v-if="form.formFields.status==1" style="color:#67C23A">正常</span>
          <span v-if="form.formFields.status==2" style="color:red">锁定</span>
        </template>
      </component-form>
    </div>
  </div>
</template>
<script>
import {getPersonal,updateUrl} from '@/api/personal'

export default {
  name: 'personal-details',
  data() {
    return {
      avatarImage:require('../../../../assets/images/avatar.png'),
      imageUrl:'',
      uploadURL: '',
      editMode: false, // 添加编辑模式标志
      form: {
        labelWidth: '100px',
        formFields: {
          username: '',
          password: '默认密码 123456',
          phone: '',
          nickName: '',
          realName: '',
          email: '',
          sex: '',
          status: '',
          avatar: '',
        },
        formLabel: [
          {prop: 'username', title: '账号', type: 'text'},
          {prop: 'password', title: '密码', type: 'text'},
          {prop: 'phone', title: '电话', type: 'text'},
          {prop: 'nickName', title: '昵称', type: 'text'},
          {prop: 'realName', title: '姓名', type: 'text'},
          {prop: 'email', title: '邮箱', type: 'text'},
          {prop: 'sex', title: '性别', type: 'slot', slot: 'sex'},
          {prop: 'status', title: '状态', type: 'slot', slot: 'status'},
        ]
      },
    }
  },

  methods: {
    // 图片加载失败时触发的回调函数
    handleImageError(event) {

      // 可以在这里更新图片的 src 为默认头像路径
      event.target.src = '';
    },


    handleAvatarSuccess(res, file) {//上传头像
      console.log("res:",res)

      if (res.code == 200){
        updateUrl({url:res.data}).then((res1) => {
          this.form.formFields.avatar = res.data
        })
        // console.log("data:", res.data)
        // this.form.formFields.avatar = res.data;

      } else {
        this.$message.error('上传图片失败');
      }
    },

    beforeAvatarUpload(file) {//判断头像大小

      const isJPG = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 1;
      // console.log(file);
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 1MB!');
      }
      return isJPG && isLt2M;
    },

  },

  created() {
    this.$nextTick(() => this.request(getPersonal, this.$store.getters.userInfo['id'], this, data => {
      this.form.formFields = data;
      this.form.formFields.password = '默认密码 123456';
    }, false));
    // this.routeChange();
  },

}
</script>
<style lang="scss" scoped>
.personal-details{
  width: 100%;
  height: 100%;
  display: flex;
}

.avatar-uploader .el-upload {
  width: 180px;
  height: 180px;

  img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
}
.avatar-uploader .el-upload img:hover{
  border:1px solid #000;
}

.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  position: absolute;
  top:0;
  left:0;
}
</style>


