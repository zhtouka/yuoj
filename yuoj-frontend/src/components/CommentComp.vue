<template>
  <div>
    <CommentOpts :questionId="props.id" :userName="`题目${props.id}`" />
    <a-list
      class="list-demo-action-layout"
      :bordered="false"
      :data="dataSource"
      :reach-bottom="loadData"
      :bottom-offset="1"
    >
      <template #item="{ item }">
        <a-list-item class="list-demo-item" action-layout="vertical">
          <a-list-item-meta :title="item.userName" :description="item.content">
            <template #avatar>
              <a-avatar :size="50">
                <img
                  alt="avatar"
                  :src="item.userAvatar ? item.userAvatar : defaultAvatar"
                />
              </a-avatar>
            </template>
          </a-list-item-meta>
          <CommentOpts
            :questionId="item.questionId"
            :commentId="item.id"
            :userName="item.userName"
          />
          <ReplyComponent
            :commentId="item.id"
            :reply-total="item.replies"
            :data-source="item.commentReplies"
          />
        </a-list-item>
      </template>
      <template #scroll-loading>
        <div v-if="bottom">No more data</div>
        <div v-else-if="stopLoad">
          <a-button :onClick="doContinue" long>继续加载</a-button>
        </div>
        <a-spin v-else />
      </template>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect } from "vue";
import { CommentControllerService } from "../../generated";
import message from "@arco-design/web-vue/es/message";
import ReplyComponent from "@/components/ReplyComponent.vue";
import CommentOpts from "@/components/CommentOpts.vue";

interface Props {
  id: string;
}

const props = defineProps<Props>();

const dataSource = ref([]);

const bottom = ref(false);
const stopLoad = ref(false);

const current = ref(1);
const pageSize = 5;
let total = 0;
const commentsNum = ref(2);

const doContinue = () => {
  stopLoad.value = false;
};

const loadData = async () => {
  if (
    total < commentsNum.value &&
    (current.value - 1) * pageSize <= commentsNum.value &&
    !stopLoad.value
  ) {
    window.setTimeout(async () => {
      const res =
        await CommentControllerService.getCommentsByQuestionIdUsingPost({
          // TODO: questionId
          questionId: props.id as any,
          current: current.value,
          pageSize: pageSize,
        });
      if (res.code === 0) {
        dataSource.value = dataSource.value.concat(res.data.records);
        total += res.data.records.length;
        commentsNum.value = res.data.total;
        current.value += 1;
        stopLoad.value = true;
      } else {
        message.error("加载失败, " + res.message);
      }
    }, 2000);
  } else if (total >= commentsNum.value) {
    bottom.value = true;
    stopLoad.value = true;
  }
};

watchEffect(() => {
  loadData();
});

const defaultAvatar =
  "https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp";
</script>

<style scoped>
.action {
  display: inline-block;
  padding: 0 4px;
  color: var(--color-text-1);
  line-height: 24px;
  background: transparent;
  border-radius: 2px;
  cursor: pointer;
  transition: all 0.1s ease;
}

.action:hover {
  background: var(--color-fill-3);
}
</style>
