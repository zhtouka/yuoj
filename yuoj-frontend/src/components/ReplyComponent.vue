<template>
  <div id="replyList">
    <div v-if="flatten">
      <a-list
        class="list-demo-action-layout"
        :bordered="false"
        :data="dataListShort"
        size="small"
        split
      >
        <template #item="{ item }">
          <a-list-item
            class="list-demo-item"
            action-layout="vertical"
            :key="item.index"
          >
            <a-list-item-meta
              :title="item.userName"
              :description="item.content"
            >
              <template #avatar>
                <a-avatar>
                  <img
                    alt="avatar"
                    :src="item.userAvatar ? item.userAvatar : defaultAvatar"
                  />
                </a-avatar>
              </template>
            </a-list-item-meta>
            <CommentOpts
              :questionId="item.questionId"
              :commentId="item.commentId"
              :userName="item.userName"
              :replyId="item.id"
            />
          </a-list-item>
        </template>
        <template #empty></template>
      </a-list>
    </div>
    <div v-else>
      <a-list
        class="list-demo-action-layout"
        :bordered="false"
        :data="dataListLarge"
        :pagination-props="{
          showTotal: true,
          pageSize: searchParams.pageSize,
          current: searchParams.current,
          total: searchParams.total,
        }"
        size="small"
        :onPageChange="doPageChange"
        split
      >
        <template #item="{ item }">
          <a-list-item
            class="list-demo-item"
            action-layout="vertical"
            :key="item.index"
          >
            <a-list-item-meta
              :title="item.userName"
              :description="item.content"
            >
              <template #avatar>
                <a-avatar>
                  <!-- userAvatar必须为链接，不然浏览器会请求avatar -->
                  <img
                    alt="avatar"
                    :src="item.userAvatar ? item.userAvatar : defaultAvatar"
                  />
                </a-avatar>
              </template>
            </a-list-item-meta>
            <CommentOpts
              :questionId="item.questionId"
              :commentId="item.commentId"
              :userName="item.userName"
              :replyId="item.id"
            />
          </a-list-item>
        </template>
      </a-list>
    </div>
    <div v-if="!disabled">
      <a-button :disabled="disabled" type="text" :onClick="doFlatten">
        {{ `${flatten ? "展开" : "关闭"}` }}
      </a-button>
      <span v-if="flatten" style="float: right">
        {{ `有${props.replyTotal}条回复` }}
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect } from "vue";
import { CommentControllerService, ReplyVO } from "../../generated";
import message from "@arco-design/web-vue/es/message";
import CommentOpts from "@/components/CommentOpts.vue";

interface Props {
  commentId?: number;
  dataSource?: Array<ReplyVO>;
  replyTotal?: number;
}

const props = defineProps<Props>();

const disabled = props.dataSource?.length ? props.dataSource.length <= 3 : true;

const convertContent = (item: ReplyVO) => {
  let content = item.content;
  if (item.replyId !== 0 && !content?.startsWith("回复@")) {
    content = `回复@ ${item.replyId}: ${content}`;
  }
  item.content = content;
  return item;
};

const defaultAvatar =
  "//p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/a8c8cdb109cb051163646151a4a5083b.png~tplv-uwbnlip3yd-webp.webp";
const dataSource = ref(props.dataSource?.map((item) => convertContent(item)));
const dataListShort = ref(dataSource.value?.slice(0, 3));
const dataListLarge = ref(dataSource.value);

const flatten = ref(true);

const doFlatten = () => {
  flatten.value = !flatten.value;
};

const doPageChange = (current: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: current,
  };
};

const searchParams = ref({
  current: 1,
  pageSize: 10,
  total: props.replyTotal,
});

const loadData = async () => {
  let res = await CommentControllerService.getRepliesByQuestionIdUsingPost({
    current: searchParams.value.current,
    commentId: props.commentId,
    pageSize: searchParams.value.pageSize,
  });
  if (res.code === 0) {
    dataSource.value = res.data.records;
    dataListLarge.value = dataSource.value?.map((item) => convertContent(item));
  } else {
    message.error("加载错误, ", res.msg);
  }
};

watchEffect(() => {
  loadData();
});
</script>

<style scoped>
.list-demo-action-layout .-area {
  width: 183px;
  height: 119px;
  border-radius: 2px;
  overflow: hidden;
}

.list-demo-action-layout .list-demo-item {
  padding: 20px 0;
  border-bottom: 1px solid var(--color-fill-3);
}

.list-demo-action-layout .image-area img {
  width: 100%;
}

.list-demo-action-layout .arco-list-item-action .arco-icon {
  margin: 0 4px;
}

.arco-drawer-mask {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  //background-color: var(--color-mask-bg);
}
</style>
