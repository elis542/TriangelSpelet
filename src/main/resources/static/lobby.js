let socket;

//Startup sequence

window.onload = async () => {
    socket = new WebSocket('ws://localhost:8080/ws/game');
    const gameId = sessionStorage.getItem("gameId");

    socket.addEventListener("open", (event) => {
    sendMessage("chatMessage", "HEJSAN!");
    console.log("CONNECTED!");
})

document.getElementById("Start").addEventListener("click", () => {
    sendMessage("LobbyAction", "start");
})

document.getElementById("Leave").addEventListener("click", () => {
    socket.close();
    window.location.href = "index.html";
})

socket.addEventListener("open", () => {
    sendMessage("connect", gameId);
})

socket.addEventListener("close", () => {
    window.location.href = "index.html";
})

socket.addEventListener("message", (message) => {
    handleMessage(message);
})
}

//end startup sequence

async function handleMessage(messageJson) {

    let message = await JSON.parse(messageJson.data);

    switch (message.type) {
        case "connection": 
            if (message.data.state) {
                //setPlayerNumber(message.data.players);
                console.log("Successful game connection!");
            } else if (!message.data.state) {
                socket.close();
                console.log("Tried to connect to nonexistent game!");
                window.location.href = "index.html";
            }
    }
}


function sendMessage(type, data) {
    const message = {
        type: type,
        timestamp: Date.now(),
        data: data
    }
    socket.send(JSON.stringify(message));
}

