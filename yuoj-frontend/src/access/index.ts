import router from "@/router";
import store from "@/store";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";
import message from "@arco-design/web-vue/es/message";

router.beforeEach(async (to, from, next) => {
  let loginUser = store.state.user.loginUser;

  if (!loginUser || !loginUser.userRole) {
    await store.dispatch("user/getLoginUser");
    loginUser = store.state.user.loginUser;
  }

  const needAccess = to.meta?.access ?? ACCESS_ENUM.NOT_LOGIN;

  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    if (
      !loginUser ||
      !loginUser.userRole ||
      loginUser.userRole === ACCESS_ENUM.NOT_LOGIN
    ) {
      message.warning("用户未登录");
      next(`/user/login?redirect=${to.fullPath}`);
      return;
    }
    if (!checkAccess(loginUser, needAccess as string)) {
      return next("/noAuth");
    }
  }
  next();
});
