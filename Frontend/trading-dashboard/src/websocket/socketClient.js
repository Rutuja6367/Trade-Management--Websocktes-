let socket;

export const connectWebSocket = (onMessageCallback) => {
  socket = new WebSocket('ws://localhost:8080/trades');

  socket.onopen = () => console.log('WebSocket Connected');
  socket.onmessage = (event) => onMessageCallback(JSON.parse(event.data));
  socket.onclose = () => console.log('WebSocket Disconnected');
};

export const disconnectWebSocket = () => {
  if (socket) socket.close();
};
