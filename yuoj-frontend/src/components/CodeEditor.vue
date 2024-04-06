<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 65vh" />
</template>

<script setup lang="ts">
import * as monaco from "monaco-editor";
import { defineProps, onMounted, ref, toRaw, watch, withDefaults } from "vue";

interface Props {
  value: string;
  language?: string;
  handleChange: (v: string) => void;
}

const codeEditorRef = ref();
const codeEditor = ref();

const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  language: () => "java",
  handleChange: (v: string) => {
    console.log(v);
  },
});

/**
 * 在使用 monaco-editor 时，更改内容（如更改语言等）会卡死，
 * 原因是数据改动可能触发界面重新渲染，这时候使用toRaw函数获取元数据，在调用函数，就不会卡死了
 * https://cloud.tencent.com/developer/article/2199740?areaSource=102001.6&traceId=iV_EJ7diEhQTDV_Vr_c7H
 */
watch(
  () => props.language,
  (newValue) => {
    if (codeEditor.value) {
      let model = codeEditor.value.getModel();
      monaco.editor.setModelLanguage(toRaw(model), newValue);
      console.log(model.getLanguageId());
    }
  }
);

onMounted(() => {
  if (!codeEditorRef.value) {
    return;
  }

  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.value,
    language: props.language,
    automaticLayout: true,
    colorDecorators: true,
    minimap: {
      enabled: true,
    },
    readOnly: false,
    theme: "vs-dark",
  });

  codeEditor.value.onDidChangeModelContent(() => {
    props.handleChange(toRaw(codeEditor.value).getValue());
  });
});
</script>

<style scoped></style>
