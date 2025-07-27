const svg = document.getElementById('Game');
const triangleSize = 45;
const rows = 77;
const cols = 132;

// Funktion för att skapa en polygon med angivna punkter och attribut

window.onload = () => {
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

        const tri = createTriangle(points, `tri_${row}_${col}`);
        svg.appendChild(tri);
    }
}}