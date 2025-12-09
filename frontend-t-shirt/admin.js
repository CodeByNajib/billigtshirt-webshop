// admin.js

const API_URL = "http://localhost:8080/api/admin/gift-config";

// URL for stock endpoint ---
const STOCK_API_URL = "http://localhost:8080/api/gift-products/admin/all";


// Vent til hele siden er indlæst
document.addEventListener("DOMContentLoaded", () => {
    fetchCurrentThreshold();

    //  loads when page opens 
    fetchGiftStock();

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



// Gets the stock data    
async function fetchGiftStock() {
    try {
        // 1. ASK: Send a request to "http://localhost:8080/.../all"
        // 'await' means: "Pause here and wait for the server to reply."
        const response = await fetch(STOCK_API_URL);

        // 2. CHECK: Did the server say "OK" (200)?
        if (!response.ok) throw new Error("Kunne ikke hente gaver");
        
        // 3. CONVERT: The server sends text (JSON). We convert it to a Javascript Array.
        const gifts = await response.json();

        // 4. HAND OFF: Now we have the data, give it to the function that draws the table.
        renderGiftTable(gifts);
        
    } catch (error) {
        // 5. ERROR: If server is down, show a red error message in the table
        console.error("Fejl (Stock):", error);
        const tableBody = document.getElementById("gift-table-body");
        if(tableBody) tableBody.innerHTML = `<tr><td colspan="4" style="color:red">Kunne ikke hente data</td></tr>`;
    }
}



// Shows stock data 
function renderGiftTable(gifts) {
    // 1. FIND: Look for the <tbody> tag we made in the HTML
    const tableBody = document.getElementById("gift-table-body");
    if(!tableBody) return; // Safety check: if it doesn't exist, stop.
    
    // 2. CLEAN: Wipe out any "Loading..." text or old rows
    tableBody.innerHTML = ""; 

    // 3. LOOP: Go through every single gift in the list
    gifts.forEach(gift => {
        
        // Default Look: Green text, shows the number
        let statusClass = "status-ok"; 
        let statusText = gift.stockQuantity;
        let activeIcon = gift.active ? "✅" : "❌";

        // Logic: Is it sold out?
        if (gift.stockQuantity === 0) {
            statusClass = "status-out"; // Change class to Red (defined in CSS)
            statusText = "UDSOLGT";     // Change text to "Sold Out"
        } 
        // Logic: Is it low stock?
        else if (gift.stockQuantity < 5) {
            statusClass = "status-low"; // Change class to Orange
            statusText = `${gift.stockQuantity} (Lavt!)`; // Add warning text
        }

        // 4. BUILD: Create a new HTML Table Row (<tr>)
        const row = document.createElement("tr");
        
        // 5. FILL: Put the variables into the HTML columns (<td>)
        // Notice class="${statusClass}" <- This applies the Red/Green color!
        row.innerHTML = `
            <td>${gift.id}</td>
            <td>${gift.name}</td>
            <td class="${statusClass}">${statusText}</td>
            <td>${activeIcon}</td>
        `;

        // 6. INSERT: Add the finished row to the table
        tableBody.appendChild(row);
    });

}