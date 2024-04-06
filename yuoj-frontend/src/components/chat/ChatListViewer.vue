<template>
  <a-list>
    <a-list-item v-for="item in dataList" :key="item">
      <a-list-item-meta></a-list-item-meta>
      <template #meta>
        <div :onClick="() => handleClick(item)">
          {{ item.name }}
        </div>
      </template>
      <template #actions>
        <div onclick="">{{ idUnreadNumMap.get(item.targetId) ?? 0 }}</div>
        <icon-delete />
      </template>
    </a-list-item>
  </a-list>
</template>

<script setup lang="ts">
import {
  defineProps,
  reactive,
  ref,
  watch,
  watchEffect,
  withDefaults,
} from "vue";
import { ChatControllerService } from "../../../generated";
import { useWebSocket } from "@/components/chat/Socket";

const clickId = ref();

interface Props {
  currentKey: string;
  getTargetId: (targetId?: string, name?: string) => void;
}

const props = withDefaults(defineProps<Props>(), {
  currentKey: () => "record",
  getTargetId: (targetId?: string, name?: string) => {
    console.log(targetId, name);
  },
});

const idUnreadNumMap = reactive(new Map());

type ChatRecord = {
  name?: string;
  unread?: number;
  id?: string;
  targetId?: string;
};

const dataList = ref<Array<ChatRecord>>([
  {
    name: "张三",
    unread: 0,
  },
]);

const loadData = async () => {
  let data: Array<ChatRecord>;
  if (props.currentKey === "record") {
    const str = localStorage.getItem(props.currentKey);
    if (str !== null) {
      data = JSON.parse(str);
      dataList.value = data;
    } else {
      dataList.value = [];
    }
  } else if (props.currentKey === "friends") {
    const res = await ChatControllerService.listFriendsUsingPost({
      current: 1,
      pageSize: 10,
      agree: true,
    });
    if (res.code === 0) {
      console.log(res.data);
      dataList.value = res.data.records.map((item: any) => {
        idUnreadNumMap.set(item.id, item.unreadNum);
        return {
          name: item.friend.userName,
          unread: item.unreadNum,
          id: item.friend.id,
          targetId: item.id,
        } as ChatRecord;
      });
    }
  } else {
    const res = await ChatControllerService.listGroupsUsingPost({
      current: 1,
      pageSize: 10,
      agree: true,
    });
    if (res.code === 0) {
      console.log("group data = ", res.data);
      dataList.value = res.data.records.map((item: any) => {
        idUnreadNumMap.set(item.id, item.unreadNum);
        return {
          name: item.groupName,
          unread: item.unreadNum,
          id: item.id,
          targetId: item.id,
        } as ChatRecord;
      });
    }
  }
};

const handleClick = (item: ChatRecord) => {
  props.getTargetId(item.targetId, item.name);
  clickId.value = item.targetId;
};

const { webSocket } = useWebSocket();

enum MessageType {
  CommonMessage = "CommonMessage",
  SystemMessage = "SystemMessage",
}

type MessageDetail = {
  userId: string;
  roomId: string;
  content: string;
  username: string;
};
type Message = {
  messageType: MessageType;
  messageDetail: MessageDetail;
};

watchEffect(() => {
  loadData().then(() => {
    console.log("idUnreadNumMap", idUnreadNumMap);
  });
  webSocket.onmessage = (event: any) => {
    const str = event.data;
    const obj: Message = JSON.parse(str);
    const id = obj.messageDetail.roomId;
    if (id !== clickId.value) {
      const unread = idUnreadNumMap.get(id);
      idUnreadNumMap.set(id, unread);
    }
  };
});

watch(
  () => idUnreadNumMap,
  () => {
    console.log("idUnreadNumMap = ", idUnreadNumMap);
  }
);
</script>

<style scoped></style>
