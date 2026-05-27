let gameId;
let username;

let myTriangles;
let playersInLobby;

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

    renderPlayingField();
}

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

function renderNumberInTriangle(x, y, number, size) {
    const svg = document.getElementById("MyTriangels");
    const text = document.createElementNS("http://www.w3.org/2000/svg", "text");

    text.setAttribute("x", x);
    text.setAttribute("y", y);
    text.setAttribute("font-size", `${size}`);
    text.setAttribute("fill", "black");

    text.textContent = number;
    svg.appendChild(text);
}

function renderMyTriangels() {
    console.log(myTriangles.length); //TODO: remove this
    const svg = document.getElementById("MyTriangels");
    const triangleSize = 42;

    for (let j = 0; j < myTriangles.length; j++) {
        const x = j * (triangleSize + 3);
        const y = 42;

        let points;

        points = `${x},${y+5} ${x+triangleSize},${y+5} ${x + triangleSize/2},${5}`;
        const tri = createTriangle(points, j);
        svg.appendChild(tri);

        renderNumberInTriangle(x+6, y+3, myTriangles[j].values[0], 14);
        renderNumberInTriangle(x+triangleSize-12, y+3, myTriangles[j].values[1], 14);
        renderNumberInTriangle(x+triangleSize/2-5, 25, myTriangles[j].values[2], 14);
    }


}

function renderPlayingField() {
    const svg = document.getElementById('Game');
    const triangleSize = 45;
    const rows = 100; //Is there a reason for these numbers?
    const cols = 100;

for (let row = 0; row < rows; row++) {
    for (let col = 0; col < cols; col++) {
        const x = col * (triangleSize / 2);
        const y = row * (triangleSize * 0.866);

        let points;
        if ((row + col) % 2 === 0) {
            
            points = `${x},${y + triangleSize * 0.866} ${x + triangleSize / 2},${y} ${x + triangleSize},${y + triangleSize * 0.866}`;
        } else {
            
            points = `${x},${y} ${x + triangleSize / 2},${y + triangleSize * 0.866} ${x + triangleSize},${y}`;
        }

        const tri = createTriangle(points, `${row}, ${col}`);
        svg.appendChild(tri);
        if (row == 5 & column == 5) {
            renderNumberInTriangle(x+6, y+3, 3, 5);
            renderNumberInTriangle(x+triangleSize-12, y+3, 4, 5);
            renderNumberInTriangle(x+triangleSize/2-5, 25, 5, 5);
        }
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

            case "YourTriangels":

            myTriangles = message.data;

            renderMyTriangels();
        break;

        case "startGame": 
            if (message.data == true) {
                window.location.href = "game.html"
            }
        break;
            
        default:
            console.error("Bad WebSocket-message received!"); //Keep
        break;
    }
}