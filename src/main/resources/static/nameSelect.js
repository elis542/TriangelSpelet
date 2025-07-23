window.onload = () => {
    document.getElementById("fortsattKnapp").onclick = () => {
        const nameInput = document.getElementById("nameInput").value;
        const errorMessage = document.getElementById("ErrorMessage");

        if (nameInput.length < 4 || nameInput.length > 12) {
            errorMessage.innerHTML = "Error: Namn för långt eller kort";
            setTimeout(() => {
                errorMessage.innerHTML = "";
            }, 3000)
            return;
        } else {
            localStorage.setItem("name", nameInput);
        }
    }
}