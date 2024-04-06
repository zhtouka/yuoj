<template>
  <Viewer :value="value" :plugins="plugins" />
</template>

<script lang="ts" setup>
import gfm from "@bytemd/plugin-gfm";
import { Viewer } from "@bytemd/vue-next";
import highlight from "@bytemd/plugin-highlight";
import { defineProps, withDefaults } from "vue";

interface Props {
  value: string;
}

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];

const props = withDefaults(defineProps<Props>(), {
  value: () => "",
});
</script>

<style>
.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child {
  display: none;
}
</style>
