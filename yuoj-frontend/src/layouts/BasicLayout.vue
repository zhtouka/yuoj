<template>
  <id id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader />
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
#basicLayout {
}

#basicLayout .header {
  box-shadow: #777 1px 1px 5px;
}

#basicLayout .content {
  //background: linear-gradient(to right, #bbb, #fff);
  padding: 16px;
}

#basicLayout .footer {
  background: #efefef;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>
