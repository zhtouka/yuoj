<template>
  <div id="addQuestionView">
    <h2>创建题目</h2>
    <a-form
      :model="form"
      size="large"
      style="max-width: 1200px"
      @submit="handleSubmit"
    >
      <a-form-item field="title" label="标题">
        <a-input v-model="form.title" placeholder="请输入标题" />
      </a-form-item>
      <a-form-item field="tags" label="标签">
        <a-input-tag v-model="form.tags" placeholder="请输入标签" />
      </a-form-item>
      <a-form-item field="content" label="内容">
        <MdEditor
          style="width: 100%"
          :value="form.content"
          :handle-change="onContentChange"
        />
      </a-form-item>
      <a-form-item field="answer" label="答案">
        <MdEditor
          style="width: 100%"
          :value="form.answer"
          :handle-change="onAnswerChange"
        />
      </a-form-item>
      <a-form-item label="判题配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" style="min-width: 480px">
          <a-form-item field="judgeConfig.memoryLimit" label="内存配置">
            <a-input-number
              v-model="form.judgeConfig.memoryLimit"
              placeholder="请输入内存消耗"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.timeLimit" label="时间配置">
            <a-input-number
              v-model="form.judgeConfig.timeLimit"
              placeholder="请输入时间消耗"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈配置">
            <a-input-number
              v-model="form.judgeConfig.stackLimit"
              placeholder="请输入堆栈消耗"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <a-form-item
        label="测试用例配置"
        :content-flex="false"
        :merge-props="false"
      >
        <a-form-item
          v-for="(judgeCaseItem, index) of form.judgeCase"
          :key="index"
          no-style
        >
          <a-space direction="vertical" style="min-width: 480px">
            <a-form-item
              :field="`judgeCase[${index}].input`"
              :label="`输入用例-${index}`"
              :key="index"
            >
              <a-input
                v-model="judgeCaseItem.input"
                placeholder="请输入测试输入用例"
              />
            </a-form-item>

            <a-form-item
              :field="`judgeCase[${index}].output`"
              :label="`输出用例-${index}`"
              :key="index"
            >
              <a-input
                v-model="judgeCaseItem.output"
                placeholder="请输入测试输出用例"
              />
            </a-form-item>
            <a-button status="danger" @click="handleDelete(index)" size="large"
              >删除
            </a-button>
          </a-space>
        </a-form-item>
        <div style="margin-top: 20px">
          <a-button type="outline" status="success" @click="handleAdd"
            >新增测试用例
          </a-button>
        </div>
      </a-form-item>

      <a-form-item>
        <a-button type="primary" size="large" @click="doSubmit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { QuestionControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message";

let form = reactive({
  title: "A + B",
  tags: ["栈", "简单"],
  content: "a + b",
  answer: "2",
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  judgeCase: [
    {
      input: "1, 2",
      output: "2, 1",
    },
  ],
});
const handleSubmit = (data: any) => {
  console.log(data);
};

const handleDelete = (index: number) => {
  form.judgeCase.splice(index, 1);
};

const handleAdd = () => {
  form.judgeCase.push({
    input: "",
    output: "",
  });
};

const onContentChange = (v: string) => {
  form.content = v;
};

const onAnswerChange = (v: string) => {
  form.answer = v;
};

const doSubmit = async () => {
  const res = await QuestionControllerService.addQuestionUsingPost(form);
  if (res.code === 0) {
    message.success("创建成功");
  } else {
    message.error("创建失败, " + res.message);
  }
};
</script>

<style scoped>
# add-question-view {
}
</style>
