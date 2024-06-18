document.addEventListener("DOMContentLoaded", function() {
    const chatMessages = document.getElementById('chat-messages');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');

    let socket = new WebSocket("ws://localhost:8080/chat");

    socket.onopen = function(event) {
        console.log("Connected to WebSocket server.");
    };

    socket.onmessage = function(event) {
        console.log("Received message from server:", event.data);
        displayMessage(JSON.parse(event.data));
    };

    socket.onclose = function(event) {
        console.log("Disconnected from WebSocket server.");
    };

    socket.onerror = function(error) {
        console.log("WebSocket error:", error);
    };

    sendButton.addEventListener('click', function() {
        let messageContent = messageInput.value;
        if (messageContent.trim() !== "") {
            let message = {
                chatroomId: "1",
                sender: "user1",
                message: messageContent
            };
            socket.send(JSON.stringify(message));
            displayMessage(message);
            messageInput.value = '';
        }
    });

    messageInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });

    function displayMessage(message) {
        let messageElement = document.createElement('div');
        messageElement.textContent = message.sender + ": " + message.message;
        chatMessages.appendChild(messageElement);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
});
