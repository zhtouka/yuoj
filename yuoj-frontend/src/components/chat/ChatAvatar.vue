<template>
  <div :onClick="() => handleClick(props.id)" v-if="props.show">
    <a-space direction="vertical">
      <a-avatar>
        <img alt="avatar" :src="props.avatar" />
      </a-avatar>
      <span>{{ props.name }}</span>
    </a-space>
  </div>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

const defauleAvatar =
  "https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp";

interface Props {
  id: string;
  name?: string;
  avatar?: string;
  show: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => "",
  name: () => "昵称",
  avatar: () => defauleAvatar,
  show: () => false,
});

const handleClick = (id: string) => {
  console.log(id);
};
</script>

<style scoped></style>
