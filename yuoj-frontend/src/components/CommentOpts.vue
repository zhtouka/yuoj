<template>
  <div id="commentOpts">
    <a-button type="text" style="color: #898989" :onClick="handleClick">
      <IconMessage />
      回复
    </a-button>
    <a-button type="text" style="color: #898989">
      <IconThumbUp />
      点赞
    </a-button>
  </div>
  <a-modal
    v-model:visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    draggable
    ok-text="发布"
  >
    <template #title> 回复@ {{ userName }}</template>
    <div>
      <a-textarea
        :onInput="onChange"
        :max-length="100"
        allow-clear
        show-word-limit
      />
    </div>
  </a-modal>
</template>
<script setup lang="ts">
import { defineProps, ref } from "vue";
import { CommentControllerService } from "../../generated";
import message from "@arco-design/web-vue/es/message";

interface Props {
  questionId?: string;
  commentId?: number;
  replyId?: number;
  userName?: string;
}

const props = defineProps<Props>();

const content = ref("");

const onChange = (value: string) => {
  content.value = value;
};

const handleClick = () => {
  visible.value = true;
};

// eslint-disable-next-line no-undef
const visible = ref(false);

const handleOk = async () => {
  visible.value = false;
  const res = await CommentControllerService.addCommentUsingPost({
    questionId: String(props.questionId) as any,
    content: content.value,
    commentId: props.commentId as any,
    replyId: props.replyId as any,
  });

  if (res.code === 0) {
    message.success("发布成功");
  }
};
const handleCancel = () => {
  visible.value = false;
};
</script>

<style scoped></style>
