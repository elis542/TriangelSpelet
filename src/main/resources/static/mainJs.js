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
};

async function createGame() {
    try {
        const response = await fetch(`${hostAdress}/CreateGame`);
        if (!response.ok) {
            showErrorMessage("Create game failed!");
            return;
        }

        let newGame = await response.json();
        console.log(newGame);
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

async function joinGame() {

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