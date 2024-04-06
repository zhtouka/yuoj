<template>
  <Editor
    :value="value"
    :mode="mode"
    :plugins="plugins"
    @change="handleChange"
  />
</template>

<script lang="ts" setup>
import gfm from "@bytemd/plugin-gfm";
import { Editor } from "@bytemd/vue-next";
import highlight from "@bytemd/plugin-highlight";
import { defineProps, withDefaults } from "vue";

interface Props {
  value: string;
  mode: string;
  handleChange: (v: string) => void;
}

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];

const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  mode: () => "split",
  handleChange: (v: string) => {
    console.log(v);
  },
});
</script>

<style>
.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child {
  display: none;
}
</style>
