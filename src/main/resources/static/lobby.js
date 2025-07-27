let socket;
let playersInLobby;

//Startup sequence

window.onload = async () => {
    socket = new WebSocket('ws://localhost:8080/ws/game');
    const gameId = sessionStorage.getItem("gameId");
    const name = sessionStorage.getItem("name");

    socket.addEventListener("open", (event) => {
    console.log("CONNECTED!");
})

document.getElementById("Start").addEventListener("click", () => {
    sendMessage("LobbyAction", "start");
})

document.getElementById("Leave").addEventListener("click", () => {
    socket.close();
    window.location.href = "mainPage.html";
})

socket.addEventListener("open", () => {
    sendMessage("setName", name);
    sendMessage("connect", gameId);
})

socket.addEventListener("close", () => {
    window.location.href = "mainPage.html";
})

socket.addEventListener("message", (message) => {
    handleMessage(message);
})

document.getElementById("GameId").innerHTML = `Game ID: ${gameId}`;
}

//end startup sequence

async function handleMessage(messageJson) {

    let message = await JSON.parse(messageJson.data);

    switch (message.type) {
        case "connection": 
            if (message.data.state) {
                console.log("Successful game connection!");
                playersInLobby = message.data.players;
                updatePlayerList();

            } else if (!message.data.state) {
                socket.close();
                console.log("Tried to connect to nonexistent game!");
                window.location.href = "mainPage.html";
            }
            break;
            
        case "playerUpdate": 
            if (message.data.action == "add") {
                playersInLobby.push(message.data.name);
            } else if (message.data.action == "remove") {
                playersInLobby.splice(playersInLobby.indexOf(message.data.name), 1);
            }
            updatePlayerList();
    }
}

function updatePlayerList() {
    let playerDOM = document.getElementById("PlayerList");
    let newPlayerDOM = document.createElement("ul");

    for (player of playersInLobby) {
        let playerObject = document.createElement("li");
        playerObject.innerHTML = player;
        newPlayerDOM.appendChild(playerObject);
    }
    playerDOM.innerHTML = "";
    playerDOM.appendChild(newPlayerDOM);
}

function sendMessage(type, data) {
    const message = {
        type: type,
        timestamp: Date.now(),
        data: data
    }
    socket.send(JSON.stringify(message));
}

