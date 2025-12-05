// admin.js

const API_URL = "http://localhost:8080/api/admin/gift-config";

// Vent til hele siden er indlæst
document.addEventListener("DOMContentLoaded", () => {
    fetchCurrentThreshold();

    // Lyt efter klik på knappen
    const saveBtn = document.getElementById("saveBtn");
    saveBtn.addEventListener("click", updateThreshold);
});

// Funktion til at hente nuværende værdi
async function fetchCurrentThreshold() {
    try {
        const response = await fetch(API_URL);
        const data = await response.json();
        document.getElementById("currentThreshold").innerText = data.thresholdAmount;
    } catch (error) {
        console.error("Fejl:", error);
        showMessage("Kunne ikke forbinde til backend!", "red");
    }
}

// Funktion til at opdatere værdien
async function updateThreshold() {
    const amountInput = document.getElementById("newAmount");
    const newValue = amountInput.value;

    if (!newValue) {
        showMessage("Indtast venligst et tal.", "orange");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            // Husk vi rettede backend til at tage imod et Map/Objekt
            body: JSON.stringify({ "thresholdAmount": parseFloat(newValue) })
        });

        if (response.ok) {
            showMessage("Succes! Grænsen er opdateret.", "green");
            document.getElementById("currentThreshold").innerText = newValue;
            amountInput.value = ""; // Ryd feltet
        } else {
            const errorText = await response.text();
            showMessage("Fejl: " + errorText, "red");
        }

    } catch (error) {
        console.error("Netværksfejl:", error);
        showMessage("Der skete en teknisk fejl.", "red");
    }
}

// Hjælpefunktion til at vise beskeder
function showMessage(text, color) {
    const msgElement = document.getElementById("message");
    msgElement.innerText = text;
    msgElement.style.color = color;
}