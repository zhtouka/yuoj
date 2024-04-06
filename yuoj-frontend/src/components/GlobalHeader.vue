<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <div>
        <a-menu
          mode="horizontal"
          :selected-keys="selectedKeys"
          :default-selected-keys="['1']"
          @menu-item-click="doMenuClick"
        >
          <a-menu-item
            key="0"
            :style="{ padding: 0, marginRight: '38px' }"
            disabled
          >
            <div class="title-bar">
              <img class="logo" src="../assets/oj-logo.png" />
              <div class="title">鱼皮OJ</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in visibleRoutes" :key="item.path"
            >{{ item.name }}
          </a-menu-item>
        </a-menu>
      </div>
    </a-col>
    <a-col flex="100px">
      <a-space size="medium">
        <a-dropdown @select="handleSelect">
          <div style="min-width: 48px">
            {{ user?.loginUser?.userName ?? "未登录" }}
          </div>
          <template #content>
            <div v-if="isLogin">
              <a-doption>Option 1</a-doption>
              <a-doption disabled>Option 2</a-doption>
              <a-doption :value="{ value: 'Option3' }">Option 3</a-doption>
              <a-doption :onClick="doLogout">退出</a-doption>
            </div>
            <div v-else>
              <a-doption :onClick="doLogin">登录</a-doption>
            </div>
          </template>
        </a-dropdown>

        <a-avatar :size="48">
          <img alt="avatar" :src="user?.loginUser?.userAvatar" />
        </a-avatar>
      </a-space>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";

const isLogin = ref(false);
const doLogin = () => {
  // router.push({
  //   path: "/user/login?redirect=/",
  // });
  isLogin.value = true;
};
const doLogout = () => {
  isLogin.value = false;
};

const handleSelect = (v: any) => {
  console.log(v);
};

const router = useRouter();
const store = useStore();

const user = store.state.user;

const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});

setTimeout(() => {
  console.log(user?.loginUser);
}, 3000);

router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const selectedKeys = ref(["/"]);
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#globalHeader {
}

.title-bar {
  display: flex;
  align-items: center;
}

.logo {
  height: 48px;
}

.title {
  color: #444;
  margin-left: 16px;
}
</style>
