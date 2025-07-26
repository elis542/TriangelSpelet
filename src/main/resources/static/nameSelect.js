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
            let name = nameInput;
            sessionStorage.setItem("name", sanitize(name));
            window.location.href = "mainPage.html";
        }
    }
}

function sanitize(string) {
    const map = {
      '&': '&amp;',
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#x27;',
      "/": '&#x2F;',
    };

    const reg = /[&<>"'/]/ig;
    return string.replace(reg, (match) => map[match]);
}