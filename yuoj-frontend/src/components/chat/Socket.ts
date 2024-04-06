import message from "@arco-design/web-vue/es/message";
import { ref } from "vue";

export function useWebSocket() {
  const webSocket = new WebSocket(`ws://localhost:8080/api/ws`);
  const conn = ref(false);
  webSocket.onopen = () => {
    console.log("open");
    message.success("websocket open");
    conn.value = true;
  };

  webSocket.onclose = () => {
    console.log("close");
    conn.value = false;
  };

  webSocket.onerror = () => {
    console.log("onerror");
    conn.value = false;
  };

  webSocket.onmessage = () => {
    console.log("onmessage");
  };
  return {
    webSocket,
    conn,
  };
}
