let gameId;
let username;

let playersInLobby;

const svg = document.getElementById('Game');
const triangleSize = 45;
const rows = 77;
const cols = 132;

// Funktion för att skapa en polygon med angivna punkter och attribut

window.onload = () => {
    socket = new WebSocket('ws://localhost:8080/ws/game');
    gameId = sessionStorage.getItem("gameId");
    username = sessionStorage.getItem("name");

    socket.addEventListener("message", (message) => {
        handleMessage(message);
    })

    socket.addEventListener("open", () => {
    sendMessage("setName", username);
    sendMessage("connect", gameId);
})


    const svg = document.getElementById('Game');
    const triangleSize = 45;
    const rows = 77;
    const cols = 132;

    function createTriangle(points, id) {
    const poly = document.createElementNS("http://www.w3.org/2000/svg", "polygon");
    poly.setAttribute("points", points);
    poly.setAttribute("id", id);
    poly.addEventListener('click', () => {
        alert(`Klickade på triangel ${id}`);
        poly.style.fill = poly.style.fill === 'yellow' ? 'white' : 'yellow';
    });
    return poly;
}

// Bygg rutnätet med växlande uppåt- och nedåtriktade trianglar
for (let row = 0; row < rows; row++) {
    for (let col = 0; col < cols; col++) {
        const x = col * (triangleSize / 2);
        const y = row * (triangleSize * 0.866);

        let points;
        if ((row + col) % 2 === 0) {
            // Uppåtriktad triangel
            points = `${x},${y + triangleSize * 0.866} ${x + triangleSize / 2},${y} ${x + triangleSize},${y + triangleSize * 0.866}`;
        } else {
            // Nedåtriktad triangel
            points = `${x},${y} ${x + triangleSize / 2},${y + triangleSize * 0.866} ${x + triangleSize},${y}`;
        }

        const tri = createTriangle(points, `${row}, ${col}`);
        svg.appendChild(tri);
    }
}}

function sendMessage(type, data) {
    const message = {
        type: type,
        timestamp: Date.now(),
        data: data
    }
    socket.send(JSON.stringify(message));
}

async function handleMessage(messageJson) {

    let message = await JSON.parse(messageJson.data);

    switch (message.type) {
        case "connection": 
            if (message.data.state) {
                console.log("Successful game connection!");
                playersInLobby = message.data.players;
 

            } else if (!message.data.state) {
                socket.close();
                console.log("Tried to connect to nonexistent game!");
                window.location.href = "mainPage.html";
            }
            break;
            
        case "playerUpdate": 
            
            break;

        case "chatMessage": 
            const senderName = message.data.name;
            const senderMessage = message.data.msg;

            let x = document.createElement("li");
            x.innerHTML = `${senderName}: ${senderMessage}`;

            document.getElementById("ChatMessages").appendChild(x);
        break;

        case "startGame": 
            if (message.data == true) {
                window.location.href = "game.html"
            }

        break;
            
        default:
            console.error("Bad WebSocket-message received!");
        break;
    }
}