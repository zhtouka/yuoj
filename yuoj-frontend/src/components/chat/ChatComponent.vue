<template>
  <div id="chatComponent">
    <a-scrollbar
      style="overflow: auto"
      :scrollTop="data.length * 100"
      :handleScroll="handleScroll"
    >
      <div v-if="isLoading" class="loading-overlay">加载中...</div>
      <a-table
        :columns="columns as TableColumnData[]"
        :data="data"
        :pagination="false"
        size="large"
        :show-header="false"
        :bordered="false"
        :hoverable="false"
        style="max-height: 400px"
      >
        <template #td>
          <td class="myTd"></td>
        </template>
        <template #target="{ record }">
          <div v-if="record.position === 'target'">
            <ChatAvatar
              :id="record.userId"
              :name="record.name"
              :avatar="record.avatar"
              :show="true"
            />
          </div>
        </template>
        <template #message="{ record }">
          <div v-if="record.position === 'target'">
            <a-space>
              <a-card
                size="medium"
                style="background-color: aqua; border-radius: 10px"
                >{{ record.message }}
              </a-card>
              <span style="width: 100px"></span>
            </a-space>
          </div>
          <div v-else-if="record.position === 'self'" style="float: right">
            <a-space>
              <span style="width: 100px"></span>
              <a-card
                size="medium"
                style="background-color: aqua; border-radius: 10px"
                >{{ record.message }}
              </a-card>
            </a-space>
          </div>
        </template>
        <template #self="{ record }">
          <div v-if="record.position === 'self'">
            <ChatAvatar
              :id="record.userId"
              :name="record.name"
              :avatar="record.avatar"
              :show="true"
            />
          </div>
        </template>
      </a-table>
    </a-scrollbar>
    <div>
      <!--      <a-space>-->
      <!--        <a-upload-->
      <!--          action="/"-->
      <!--          :auto-upload="false"-->
      <!--          :on-before-upload="-->
      <!--            (file) => {-->
      <!--              console.log(file);-->
      <!--              fileList.push(file);-->
      <!--              return true;-->
      <!--            }-->
      <!--          "-->
      <!--        ></a-upload>-->
      <!--        <a-button :onClick="() => imageSubmit(fileList)">qwe</a-button>-->
      <!--      </a-space>-->
      <ChatTextarea :handle-submit="handleSubmit" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect, withDefaults } from "vue";
import ChatAvatar from "@/components/chat/ChatAvatar.vue";
import { TableColumnData } from "@arco-design/web-vue";
import ChatTextarea from "@/components/chat/ChatTextarea.vue";
import { ChatControllerService } from "../../../generated";
import { useWebSocket } from "@/components/chat/Socket";

const isLoading = ref(false);

const { webSocket } = useWebSocket();

interface Props {
  id: string;
  targetId: string;
  peerId?: string;
  name?: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => "",
  targetId: () => "",
});

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

const columns = [
  {
    title: "Target",
    slotName: "target",
    align: "center",
    width: 100,
  },
  {
    title: "Message",
    slotName: "message",
  },
  {
    title: "Self",
    slotName: "self",
    align: "center",
    width: 100,
  },
];
type DisplayMessage = {
  key: string;
  position: string;
  message?: string;
  userId?: string;
  name?: string;
  avatar?: string;
};
const data = ref<Array<DisplayMessage>>([]);

const convert = (loaddata: Array<Message>, currentUserId: string) => {
  data.value = loaddata
    .filter((item) => item.messageType === MessageType.CommonMessage)
    .map((item) => {
      let detail = item.messageDetail;
      return {
        key: String(data.value.length + 1),
        position: detail.userId === currentUserId ? "self" : "target",
        message: detail.content,
        userId: detail.userId,
        name: detail.username,
      } as DisplayMessage;
    });
};

const remoteData = ref<Array<Message>>([]);

const loadData = async () => {
  if (props.targetId && props.targetId.length > 0) {
    const res = await ChatControllerService.getMessagesUsingPost({
      targetId: props.targetId,
      current: 1,
      pageSize: 10,
    });
    if (res.code === 0) {
      // console.log("res.data = ", res.data.records);
      remoteData.value = res.data.records.map((item: any) => {
        return {
          messageType: MessageType.CommonMessage,
          messageDetail: {
            userId: item.userId,
            roomId: item.targetId,
            content: item.content,
            username: item.userName,
          },
        } as Message;
      });
      convert(remoteData.value, props.id);
    }
  }
};

watchEffect(() => {
  loadData();

  webSocket.onmessage = (event: any) => {
    const str = event.data;
    const obj: Message = JSON.parse(str);
    if (obj.messageType === MessageType.CommonMessage) {
      let detail = obj.messageDetail;
      if (detail.roomId === props.targetId) {
        data.value.push({
          key: String(data.value.length + 1),
          position: detail.userId === props.id ? "self" : "target",
          message: detail.content,
          userId: detail.userId,
          name: detail.username,
        });
      }
    }
  };
});

const handleSubmit = (value: string) => {
  const obj = {
    key: String(data.value.length + 1),
    position: "self",
    message: value,
    userId: props.id,
    name: props.name,
  };
  data.value.push(obj);
  const msg = {
    messageType: MessageType.CommonMessage,
    messageDetail: {
      userId: obj.userId,
      roomId: props.targetId,
      content: obj.message,
      username: obj.name,
    },
  } as Message;
  const str = JSON.stringify(msg);
  webSocket.send(str);
};

const handleScroll = (event: any) => {
  const { scrollTop, scrollDown, scrollHeight, clientHeight } = event.target;
  console.log(
    "@@@@@@@@@@@@@@@@@",
    scrollTop,
    scrollDown,
    scrollHeight,
    clientHeight
  );
  if (scrollHeight - (scrollTop + clientHeight) < 1 && !isLoading.value) {
    loadMoreData();
  }
};

const loadMoreData = () => {
  isLoading.value = true;
  // 模拟异步加载数据
  setTimeout(() => {
    console.log("loading ");
  }, 1000);
};

const fileList = ref<Array<File>>([]);
const fileStrList = ref<Array<any>>([]);
const imageSubmit = (list: Array<File>) => {
  console.log(fileList);
  list.forEach((file) => {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (ev) => {
      console.log(ev.target?.result);
      fileStrList.value.push(ev.target?.result as any);
    };
  });
  console.log(fileStrList.value);
  const x = fileStrList.value.map((dataUrl) => {
    var arr = dataUrl.split(","),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], "abc", { type: mime });
  });
  x.forEach((file) => {
    fileList.value.push(file);
  });
  console.log(fileList.value);
};
</script>

<style scoped>
#chatComponent {
  margin: 20px 20px 0 20px;
  bottom: 0;
  position: absolute;
  width: 95%;
}

.myTd {
  font-size: 14px;
  border-bottom: none;
}
</style>
