<template>
  <div class="userRegisterView">
    <h2>用户登录</h2>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      :model="form"
      auto-label-width
      label-align="left"
      @submit="handleSubmit"
    >
      <a-form-item field="username" tooltip="请输入账号" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号..." />
      </a-form-item>
      <a-form-item field="password" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码..."
        />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          style="width: 120px"
          size="large"
          html-type="submit"
          >登录
        </a-button>
        <a-link href="/user/register" style="margin-left: auto"
          >没有账号，请注册
        </a-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<style>
.userRegisterView {
  margin: auto;
}
</style>

<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

const store = useStore();
const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
});
const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  if (res.code === 0) {
    alert("登录成功, " + res.data.userName);
    await store.dispatch("user/getLoginUser");
    const redirect = router.currentRoute.value.redirectedFrom;
    console.log(redirect);
    router.push({
      path: redirect?.path,
      replace: true,
    });
  } else {
    message.error("登录失败, " + res.data.message);
  }
};
</script>
