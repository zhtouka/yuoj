<template>
  <div id="viewQuestionView">
    <a-row :gutter="[24, 24]">
      <a-split
        :style="{
          height: '80vh',
          width: '100%',
          minWidth: '500px',
          border: '1px solid var(--color-border)',
        }"
        v-model:size="size"
        min="80px"
      >
        <template #first>
          <a-col :md="24" xs="24">
            <a-tabs default-active-key="question">
              <a-tab-pane key="question" title="题目">
                <a-card v-if="question" :title="question.title">
                  <a-descriptions
                    title="判题条件"
                    :column="{ xs: 1, md: 2, lg: 3 }"
                  >
                    <a-descriptions-item label="时间限制">
                      {{ question.judgeConfig.timeLimit ?? 0 }}
                    </a-descriptions-item>
                    <a-descriptions-item label="内存限制">
                      {{ question.judgeConfig.memoryLimit ?? 0 }}
                    </a-descriptions-item>
                    <a-descriptions-item label="堆栈限制">
                      {{ question.judgeConfig.stackLimit ?? 0 }}
                    </a-descriptions-item>
                  </a-descriptions>
                  <MdViewer :value="question.content || ''" />
                  <template #extra>
                    <a-space wrap>
                      <a-tag
                        v-for="(tag, index) of question.tags"
                        :key="index"
                        color="green"
                      >
                        {{ tag }}
                      </a-tag>
                    </a-space>
                  </template>
                </a-card>
                <a-card>
                  <div>{{ question?.content }}</div>
                </a-card>
              </a-tab-pane>
              <a-tab-pane key="comment" title="评论" id="commentList">
                <CommentComp :id="props.id" />
              </a-tab-pane>
              <a-tab-pane key="answer" title="题解"> 题解</a-tab-pane>
            </a-tabs>
          </a-col>
        </template>
        <template #second>
          <a-typography-paragraph>
            <a-col :md="24" xs="24">
              <a-form label-align="left">
                <a-form-item
                  field="language"
                  label="编程语言"
                  style="max-width: 360px"
                >
                  <a-select
                    style="max-width: 200px"
                    placeholder="请选择编程语言"
                    v-model="form.language"
                  >
                    <a-option>java</a-option>
                    <a-option>cpp</a-option>
                    <a-option>go</a-option>
                    <a-option>rust</a-option>
                  </a-select>
                </a-form-item>
              </a-form>
              <CodeEditor
                :value="form.code as string"
                :language="form.language"
                :handleChange="handleChange"
              />
              <a-divider size="0" />
              <a-button
                type="primary"
                size="large"
                style="float: right"
                @click="doSubmit"
              >
                提交
              </a-button>
            </a-col>
          </a-typography-paragraph>
        </template>
      </a-split>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { defineProps, onMounted, ref, withDefaults } from "vue";
import {
  QuestionControllerService,
  QuestionSubmitAddRequest,
  QuestionVO,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import CodeEditor from "@/components/CodeEditor.vue";
import MdViewer from "@/components/MdViewer.vue";
import CommentComp from "@/components/CommentComp.vue";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => "",
});

const size = ref(0.5);

const question = ref<QuestionVO>();

const form = ref<QuestionSubmitAddRequest>({
  language: "java",
  code: "public class Main {\n\n}",
});

const handleChange = (v: string) => {
  form.value.code = v;
};

const loadData = async () => {
  const res = await QuestionControllerService.getQuestionVoByIdUsingGet(
    props.id as any
  );
  if (res.code === 0) {
    question.value = res.data;
  } else {
    message.error("加载失败, " + res.message);
  }
};

onMounted(() => {
  loadData();
});

const doSubmit = async () => {
  if (props.id === "") {
    return;
  }
  const res = await QuestionControllerService.doQuestionSubmitUsingPost({
    ...form.value,
    questionId: props.id as any,
  });
  if (res.code === 0) {
    message.success("提交成功");
  } else {
    message.error("提交失败, " + res.message);
  }
};
</script>

<style scoped>
#viewQuestionView {
}

#viewQuestionView .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
</style>
