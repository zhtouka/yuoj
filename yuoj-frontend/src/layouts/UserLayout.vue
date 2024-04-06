<template>
  <id id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img class="logo" src="../assets/oj-logo.png" />
          <div>鱼皮OJ</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer"
        ><a href="https://yupi.icu" target="_blank"
          >鱼皮OJ YUOJ</a
        ></a-layout-footer
      >
    </a-layout>
  </id>
</template>

<style>
#userLayout {
  text-align: center;
  background: url(https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png)
    0% 0% / 100% 100%;
}

#userLayout .header {
  box-shadow: #777 1px 1px 5px;
}

#userLayout .content {
  margin-top: 16px;
  padding: 16px;
}

#userLayout .footer {
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}

#userLayout .logo {
  width: 64px;
  height: 64px;
}
</style>
<script setup lang="ts"></script>
