<template>
  <div id="addQuestionView">
    <h2>创建题目</h2>
    <a-form
      v-model="form"
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
import { onMounted, reactive, ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { QuestionControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRoute } from "vue-router";

const route = useRoute();

const updatePage = route.path.includes("update");
const id = route.query.id;

/**
 * 根据 id 查询数据
 */
const loadDate = async () => {
  if (!id) {
    return;
  }
  const res = await QuestionControllerService.getQuestionByIdUsingGet(
    id as any
  );
  console.log("loadData: ", res.data);
  if (res.code === 0) {
    form.value = res.data as any;
    if (res.data?.tags) {
      form.value.tags = JSON.parse(res.data.tags);
    } else {
      form.value.tags = [];
    }
    if (res.data?.judgeCase) {
      form.value.judgeCase = JSON.parse(res.data.judgeCase);
    } else {
      form.value.judgeCase = [
        {
          input: "",
          output: "",
        },
      ];
    }
    if (res.data?.judgeConfig) {
      console.log("judgeConfig: ", res.data.judgeConfig);
      form.value.judgeConfig = JSON.parse(res.data.judgeConfig);
    } else {
      form.value.judgeConfig = {
        memoryLimit: 1000,
        stackLimit: 1000,
        timeLimit: 1000,
      };
    }
    console.log("form: ", form.value);
  } else {
    message.error("加载失败, " + res.message);
  }
};

onMounted(() => {
  loadDate();
});

const form = ref({
  title: "",
  tags: [],
  content: "",
  answer: "",
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  judgeCase: [
    {
      input: "",
      output: "",
    },
  ],
});
const handleSubmit = (data: any) => {
  console.log(data);
};

const handleDelete = (index: number) => {
  form.value.judgeCase.splice(index, 1);
};

const handleAdd = () => {
  form.value.judgeCase.push({
    input: "",
    output: "",
  });
};

const onContentChange = (v: string) => {
  form.value.content = v;
};

const onAnswerChange = (v: string) => {
  form.value.answer = v;
};

const doSubmit = async () => {
  if (!updatePage) {
    const res = await QuestionControllerService.addQuestionUsingPost(
      form.value
    );
    if (res.code === 0) {
      message.success("登录成功");
    } else {
      message.error("登录失败, " + res.message);
    }
  } else {
    const res = await QuestionControllerService.updateQuestionUsingPost(
      form.value
    );
    if (res.code === 0) {
      message.success("更新成功");
    } else {
      message.error("加载成功, " + res.message);
    }
  }
};
</script>

<style scoped>
# add-question-view {
}
</style>
