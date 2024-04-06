<template>
  <div class="home">
    <MdEditor :value="mdValue" :handle-change="onMdChange" />
    <CodeEditor
      :value="codeValue"
      :handle-change="onCodeChange"
      style="min-height: 400px"
    />
  </div>
</template>

<script lang="ts" setup>
import MdEditor from "@/components/MdEditor.vue";
import { ref } from "vue";
import CodeEditor from "@/components/CodeEditor.vue"; // @ is an alias to /src

const mdValue = ref("");
const codeValue = ref("");

const onMdChange = (v: string) => {
  mdValue.value = v;
};

const onCodeChange = (v: string) => {
  codeValue.value = v;
  // console.log(v);
};
</script>
