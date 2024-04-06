<template>
  <div id="questionSubmitView">
    <a-form :model="searchParams" layout="inline" size="large">
      <a-form-item
        field="searchParams.questionId"
        label="题目名称"
        style="min-width: 240px"
      >
        <a-input v-model="searchParams.questionId" placeholder="请输入题目id" />
      </a-form-item>
      <a-form-item field="language" label="编程语言" style="max-width: 360px">
        <a-select
          style="max-width: 200px"
          placeholder="请选择编程语言"
          v-model="searchParams.language"
        >
          <a-option value="">不限</a-option>
          <a-option value="java">java</a-option>
          <a-option value="cpp">cpp</a-option>
          <a-option value="go">go</a-option>
          <a-option value="rust">rust</a-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSubmit">查询</a-button>
      </a-form-item>
    </a-form>
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
      <template #questionId="{ record }">
        <a-link :href="'/view/question/' + record.questionId"
          >{{ record.questionId }}
        </a-link>
      </template>
      <template #message="{ record }">
        {{ record.judgeInfo.message }}
      </template>
      <template #time="{ record }">
        {{ record.judgeInfo.time }}
      </template>
      <template #memory="{ record }">
        {{ record.judgeInfo.memory }}
      </template>
      <template #state="{ record }">
        {{ judgeStatus[record.state] }}
      </template>
      <template #createTime="{ record }">
        {{ moment(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import {
  Question,
  QuestionControllerService,
  QuestionSubmitQueryRequest,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import moment from "moment/moment";

const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionSubmitQueryRequest>({
  questionId: undefined,
  language: undefined,
  pageSize: 10,
  current: 1,
});

const judgeStatus = ["等待中", "判题中", "判题成功", "判题失败"];

const onPageChange = (current: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: current,
  };
};

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(
    {
      ...searchParams.value,
      sortField: "createTime",
      sortOrder: "descend",
    }
  );
  if (res.code === 0) {
    const data = res.data.records;
    const userVO = data.userVO;
    console.log(userVO);
    dataList.value = data;
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

// setTimeout(() => {
//   loadData();
// }, 10000);

const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "语言",
    dataIndex: "language",
  },
  // {
  //   title: "判题信息",
  //   slotName: "judgeInfo",
  // },
  {
    title: "判题信息",
    slotName: "message",
  },
  {
    title: "内存",
    slotName: "memory",
  },
  {
    title: "时间",
    slotName: "time",
  },
  {
    title: "判题状态",
    slotName: "state",
  },
  {
    title: "题目 id",
    slotName: "questionId",
  },
  {
    title: "提交用户 id",
    dataIndex: "userId",
  },
  {
    title: "代码大小",
    dataIndex: "codeSize",
  },
  {
    title: "提交时间",
    slotName: "createTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];

const doSubmit = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1,
  };
};
</script>

<style scoped>
# add-question-view {
}
</style>
