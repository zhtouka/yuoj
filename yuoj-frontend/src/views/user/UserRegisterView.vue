<template>
  <div class="userRegisterView">
    <h2>用户注册</h2>
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
      <a-form-item field="checkPassword" label="确认密码">
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请输入密码..."
        />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          style="width: 120px"
          size="large"
          html-type="submit"
          >注册
        </a-button>
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
import { UserControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

const store = useStore();
const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
});
const handleSubmit = async () => {
  const res = await UserControllerService.userRegisterUsingPost(form);
  if (res.code === 0) {
    alert("注册成功, " + res.data.userName);
    await store.dispatch("user/getLoginUser");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("注册失败, " + res.data.message);
  }
};
</script>
