<template>
  <div id="chatView">
    <div style="float: left; width: 20%">
      <a-input-search
        placeholder="Please enter something"
        :onSearch="handleSearchAll"
      />
    </div>
    <div style="text-align: center; padding: 5px">
      <span>{{ clickPeer.name }}</span>
      <a-button
        style="float: right"
        size="small"
        :disabled="clickPeer.id.length === 0"
        :onClick="handleClick"
        >详情
      </a-button>
      <a-modal
        v-model:visible="visible"
        @ok="handleOk"
        @cancel="handleCancel"
        draggable
      >
        <template #title> 详情</template>
        <div>详细信息, id: {{ targetId }}</div>
      </a-modal>
      <div style="float: right; margin-right: 20px; margin-top: 1px">
        <a-popover v-if="conn">
          <icon-check-circle :size="25" />
          <template #content>
            <p>websocket 连接成功</p>
          </template>
        </a-popover>
        <a-popover v-else>
          <icon-close-circle :size="20" />
          <template #content>
            <p>websocket 连接失败，请刷新重试</p>
          </template>
        </a-popover>
      </div>
    </div>
    <a-split
      :style="{
        height: '72vh',
        width: '100%',
        minWidth: '500px',
        border: '1px solid var(--color-border)',
      }"
      size="0.2"
      min="80px"
    >
      <template #first>
        <a-typography-paragraph>
          <a-tabs v-model:active-key="currentKey">
            <a-tab-pane key="record">
              <template #title>
                <icon-calendar />
                聊天记录
              </template>
              <ChatListViewer
                :current-key="currentKey"
                :get-target-id="getTargetId"
              />
            </a-tab-pane>
            <a-tab-pane key="friends">
              <template #title>
                <icon-clock-circle />
                好友
              </template>
              <ChatListViewer
                :current-key="currentKey"
                :get-target-id="getTargetId"
              />
            </a-tab-pane>
            <a-tab-pane key="groups">
              <template #title>
                <icon-user />
                群组
              </template>
              <ChatListViewer
                :current-key="currentKey"
                :get-target-id="getTargetId"
              />
            </a-tab-pane>
          </a-tabs>
        </a-typography-paragraph>
      </template>
      <template #second>
        <a-typography-paragraph
          style="
            height: 500px;
            position: relative;
            border: 1px solid red;
            border-radius: 25px;
            margin: 10px;
          "
        >
          <ChatComponent :id="loginUser.id" :target-id="targetId" />
        </a-typography-paragraph>
      </template>
    </a-split>
  </div>
</template>

<script setup lang="ts">
import ChatListViewer from "@/components/chat/ChatListViewer.vue";
import { ref } from "vue";
import ChatComponent from "@/components/chat/ChatComponent.vue";
import { useWebSocket } from "@/components/chat/Socket";
import { useStore } from "vuex";

const store = useStore();

const currentKey = ref("record");

const targetId = ref("");
const loginUser = store.state.user.loginUser;

const { conn } = useWebSocket();

type Peer = {
  id: string;
  name?: string;
};
const clickPeer = ref<Peer>({
  id: "",
  name: "",
});

const getTargetId = async (id: string, name?: string) => {
  targetId.value = id;
  clickPeer.value = {
    id: targetId.value,
    name: name,
  };
};

// const loadMap = (data: Array<any>) => {
//   data.forEach((item) => {
//     idUnreadNumMap.value.set(item.id, item.unread);
//   });
// };
//
// const findMap = (id: string) => {
//   return idUnreadNumMap.value.get(id) ?? 0;
// };
//
// const addRecord = (id: string) => {
//   if (id !== targetId.value) {
//     const unread = idUnreadNumMap.value.get(id);
//     idUnreadNumMap.value.set(id, unread + 1);
//   }
// };

const handleSearchAll = (value: string) => {
  window.open(`/chat/search/list?search=${value}`, "_blank");
};

const visible = ref(false);

const handleClick = () => {
  visible.value = true;
};
const handleOk = () => {
  visible.value = false;
};
const handleCancel = () => {
  visible.value = false;
};
</script>

<style scoped>
#chatView {
  height: 100%;
}
</style>
