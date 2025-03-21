# spring_rest

 frontend На фронт-енді підключіться до WebSocket та обробляйте повідомлення. 
 Наприклад, у JavaScript


const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
stompClient.subscribe('/topic/account/12345', function (message) {
console.log('Received message: ' + message.body);
});
});
