const hostAdress = "http://localhost:8080/api";

window.onload = async () => {
    try { //Bör skaffa nåpon timeout här
        const response = await fetch(`${hostAdress}/status`
        );
        if (!response.ok) {
            showStatusMessage("BAD RESPONSE");
        }

        const statusAnswer = await response.json(); 

        if (statusAnswer) {
            showStatusMessage("OK");
        } else if (!statusAnswer) {
            showStatusMessage("NOT OK");
        }
    } catch (error) {
        showStatusMessage("NO ANSWER");
    }

    document.getElementById("SkapaKnapp").addEventListener("click", async () => {
       createGame();
    })

    document.getElementById("AnslutKnapp").addEventListener("click", async () => {
        joinGame(document.getElementById("gameIdVal").value);
    })

    loadGameList();
};

async function createGame() {
    try {
        const response = await fetch(`${hostAdress}/CreateGame`);
        if (!response.ok) {
            showErrorMessage("Create game failed!");
            return;
        }

        let newGame = await response.json();
        if (newGame.successful) {
            sessionStorage.setItem("gameId", newGame.gameId);
            window.location.href = "lobby.html";
            
        } else {
            showErrorMessage("something went wrong! Response OK!");
        }
    } catch (error) {
        showErrorMessage("something went wrong! Error thrown");
    }
}

async function loadGameList() {
    const list = document.getElementById("ServerList");
    let serverList;
    try {
        const response = await fetch(`${hostAdress}/GetServers`);

        if (!response.ok) {
            showErrorMessage("failed to load servers! Server error");
            return;
        }

        serverList = await response.json();
        console.log(serverList);

    } catch (error) {
        showErrorMessage("something went wrong! Error thrown");
    }

    for (server of serverList) {
        let listItem = document.createElement("li");
        let button = document.createElement("button");
        button.innerHTML = "join"
        button.addEventListener("click", () => {
            joinGame(server.gameId);
        })

        listItem.innerHTML = `gameId: ${server.gameId} with ${server.numberOfPlayers} players`;
        listItem.appendChild(button);
        list.appendChild(listItem);
    }
}

async function joinGame(id) {
    const gameIdInput = id;

    try {
        const response = await fetch(`${hostAdress}/joinGame`, {
             method: "POST",
             body: gameIdInput
        });

        if(!response.ok) {
            showErrorMessage("Join game failed! Match does not exist!");
            return;
        }

            sessionStorage.setItem("gameId", gameIdInput);
            window.location.href = "lobby.html";

    } catch (error) {
        showErrorMessage("Something went wrong! Error thrown");
    }
}

function showStatusMessage(message) {
    let statusMessage = document.getElementById("StatusMessage");
    statusMessage.innerHTML = `Server Status: ${message}`
}

function showErrorMessage(message) {
    let paragraf = document.getElementById("ErrorMessage");

    paragraf.innerHTML = `Error message: ${message}`;
    setTimeout(() => {
        paragraf.innerHTML = "";
    }, 5000);
}