<template>
  <div>
    <div>
      <a-textarea
        style="max-width: 90%"
        :auto-size="{
          minRows: 3,
          maxRows: 3,
        }"
        :onInput="handleInput"
        :model-value="input"
      >
        <template #append>
          <a-button>发送</a-button>
        </template>
      </a-textarea>
      <a-button
        type="primary"
        size="large"
        style="width: 10%; float: right; height: 75px"
        long
        :onClick="handleButton"
        :disabled="input.length === 0"
        >发送
      </a-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, withDefaults, defineProps } from "vue";

interface Props {
  handleSubmit: (value: string) => void;
}

const props = withDefaults(defineProps<Props>(), {
  handleSubmit: (v: string) => {
    console.log(v);
  },
});

const input = ref("");

const handleInput = (value: string) => {
  // console.log(value);
  input.value = value;
};

const handleButton = () => {
  // console.log(input.value);
  props.handleSubmit(input.value);
  input.value = "";
};
</script>

<style scoped></style>
