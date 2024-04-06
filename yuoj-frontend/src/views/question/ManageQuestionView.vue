<template>
  <div id="manageQuestionView">
    <h2>管理题目</h2>
    <a-table
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @pageChange="onPageChange"
    >
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of strToObj(record.tags)"
            :key="index"
            color="green"
            >{{ tag }}
          </a-tag>
        </a-space>
      </template>
      <template #judgeConfig="{ record }">
        <a-space wrap>
          {{ setJudgeConfig(strToObj(record.judgeConfig)) }}
        </a-space>
      </template>
      <template #input="{ record }">
        <a-space wrap>
          {{ setJudgeCaseInput(strToObj(record.judgeCase)) }}
        </a-space>
      </template>
      <template #output="{ record }">
        <a-space wrap>
          {{ setJudgeCaseOutput(strToObj(record.judgeCase)) }}
        </a-space>
      </template>
      <template #createTime="{ record }">
        {{ moment(record.createTime).format("YYYY-MM-DD") }}
      </template>
      <template #updateTime="{ record }">
        {{ moment(record.updateTime).format("YYYY-MM-DD") }}
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="doUpdate(record)">修改</a-button>
          <a-button status="danger" @click="doDelete(record)">删除</a-button>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import {
  JudgeCase,
  JudgeConfig,
  Question,
  QuestionControllerService,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import moment from "moment/moment";

const dataList = ref([]);
const total = ref(0);
const searchParams = ref({
  pageSize: 10,
  current: 1,
});

const onPageChange = (current: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: current,
  };
};

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionByPageUsingPost(
    searchParams.value
  );
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败, " + res.message);
  }
};

watchEffect(() => {
  loadData();
});

onMounted(() => {
  loadData();
});

const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "标题",
    dataIndex: "title",
  },
  {
    title: "题目内容",
    dataIndex: "content",
  },
  {
    title: "标签",
    slotName: "tags",
  },
  {
    title: "答案",
    dataIndex: "answer",
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
  },
  {
    title: "通过数",
    dataIndex: "acceptedNum",
  },
  {
    title: "判题案例",
    children: [
      {
        title: "输入",
        slotName: "input",
      },
      {
        title: "输出",
        slotName: "output",
      },
    ],
  },
  {
    title: "判题配置(T/M/S)",
    slotName: "judgeConfig",
  },
  {
    title: "用户 id",
    dataIndex: "userId",
  },
  {
    title: "创建时间",
    slotName: "createTime",
  },
  {
    title: "更新时间",
    slotName: "updateTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];

const router = useRouter();

const strToObj = (str: string) => {
  return JSON.parse(str);
};

const setJudgeConfig = (obj: JudgeConfig) => {
  return [obj.timeLimit, obj.memoryLimit, obj.stackLimit];
};

const setJudgeCaseInput = (obj: Array<JudgeCase>) => {
  return obj.map((item) => {
    return item.input;
  });
};

const setJudgeCaseOutput = (obj: Array<JudgeCase>) => {
  return obj.map((item) => {
    return item.output;
  });
};

const doUpdate = (question: Question) => {
  router.push({
    path: "/update/question",
    query: {
      id: question.id,
    },
  });
};

const doDelete = async (question: Question) => {
  const res = await QuestionControllerService.deleteQuestionUsingPost({
    id: question.id,
  });
  if (res.code === 0) {
    message.success("删除成功");
    // todo 更新页面
    loadData();
  } else {
    message.error("删除失败, " + res.message);
  }
};
</script>

<style scoped>
# manageQuestionView {
}
</style>
