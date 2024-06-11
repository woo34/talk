document.addEventListener("DOMContentLoaded", function() {
    var socket = new WebSocket("ws://localhost:8080/chat");

    var messageInput = document.getElementById("message-input");
    var sendButton = document.getElementById("send-button");
    var chatMessages = document.getElementById("chat-messages");

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        var messageElement = document.createElement("div");
        messageElement.textContent = message.sender + ": " + message.content;
        chatMessages.appendChild(messageElement);
    };

    sendButton.onclick = function() {
        var message = {
            sender: "User",
            content: messageInput.value
        };
        socket.send(JSON.stringify(message));
        messageInput.value = "";
    };
});
