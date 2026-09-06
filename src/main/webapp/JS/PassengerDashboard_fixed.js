// ============================================================
// Passenger Dashboard JavaScript
// ============================================================


// ============================================================
// 1. LOAD PASSENGER TICKET DETAILS
// ============================================================

function loadPassengerDetails() {

     console.log("Starting loadPassengerDetails...");

     fetch("../PassengerServlet?action=myTicket")
         .then(response => {

             console.log("Response received:", response.status, response.statusText);

             if (!response.ok) {
                 throw new Error("Failed to load passenger details");
             }

             return response.json();
         })
         .then(data => {

             console.log("Passenger Ticket Response:", data);
             console.log("Data keys:", Object.keys(data));

             // Populate passenger name in header
             const passengerNameHeader = 
                 document.getElementById("passengerName");
             if (passengerNameHeader) {
                 passengerNameHeader.textContent = 
                     data.passengerName || "Passenger";
                 console.log("Set passengerName to:", data.passengerName);
             } else {
                 console.warn("Element 'passengerName' not found in DOM");
             }

             // Populate flight info section
             if (data.flightNumber) {
                 const flightNumberEl = document.getElementById("flightNumber");
                 if (flightNumberEl) {
                     flightNumberEl.textContent = data.flightNumber;
                     console.log("Set flightNumber to:", data.flightNumber);
                 } else {
                     console.warn("Element 'flightNumber' not found");
                 }
             }

             if (data.source) {
                 const sourceTextEl = document.getElementById("sourceText");
                 if (sourceTextEl) {
                     sourceTextEl.textContent = data.source;
                     console.log("Set sourceText to:", data.source);
                 } else {
                     console.warn("Element 'sourceText' not found");
                 }
             }

             if (data.destination) {
                 const destinationTextEl = document.getElementById("destinationText");
                 if (destinationTextEl) {
                     destinationTextEl.textContent = data.destination;
                     console.log("Set destinationText to:", data.destination);
                 } else {
                     console.warn("Element 'destinationText' not found");
                 }
             }

             if (data.airline) {
                 const airlineTextEl = document.getElementById("airlineText");
                 if (airlineTextEl) {
                     airlineTextEl.textContent = data.airline;
                     console.log("Set airlineText to:", data.airline);
                 } else {
                     console.warn("Element 'airlineText' not found");
                 }
             }

             if (data.departureTime) {
                 const departureEl = document.getElementById("departure");
                 if (departureEl) {
                     departureEl.textContent = data.departureTime;
                     console.log("Set departure to:", data.departureTime);
                 } else {
                     console.warn("Element 'departure' not found");
                 }
             }

             if (data.arrivalTime) {
                 const arrivalEl = document.getElementById("arrival");
                 if (arrivalEl) {
                     arrivalEl.textContent = data.arrivalTime;
                     console.log("Set arrival to:", data.arrivalTime);
                 } else {
                     console.warn("Element 'arrival' not found");
                 }
             }

             // Populate flight info grid
             if (data.bookingId) {
                 const bookingIdEl = document.getElementById("bookingId");
                 if (bookingIdEl) {
                     bookingIdEl.textContent = data.bookingId;
                     console.log("Set bookingId to:", data.bookingId);
                 } else {
                     console.warn("Element 'bookingId' not found");
                 }
             }

             if (data.passengerName) {
                 const nameEl = document.getElementById("name");
                 if (nameEl) {
                     nameEl.textContent = data.passengerName;
                     console.log("Set name to:", data.passengerName);
                 } else {
                     console.warn("Element 'name' not found");
                 }
             }

             if (data.seatNo) {
                 const seatEl = document.getElementById("seat");
                 if (seatEl) {
                     seatEl.textContent = data.seatNo;
                     console.log("Set seat to:", data.seatNo);
                 } else {
                     console.warn("Element 'seat' not found");
                 }
             }

             if (data.terminal) {
                 const terminalEl = document.getElementById("terminal");
                 if (terminalEl) {
                     terminalEl.textContent = data.terminal;
                     console.log("Set terminal to:", data.terminal);
                 } else {
                     console.warn("Element 'terminal' not found");
                 }
             }

             if (data.gateNumber) {
                 const gateNumberEl = document.getElementById("gateNumber");
                 if (gateNumberEl) {
                     gateNumberEl.textContent = data.gateNumber;
                     console.log("Set gateNumber to:", data.gateNumber);
                 } else {
                     console.warn("Element 'gateNumber' not found");
                 }
             }

             if (data.bookingStatus) {
                 const statusEl = document.getElementById("status");
                 if (statusEl) {
                     statusEl.textContent = data.bookingStatus;
                     console.log("Set status to:", data.bookingStatus);
                 } else {
                     console.warn("Element 'status' not found");
                 }
             }

             // Store passenger ID for chatbot
             if (data.passengerId) {
                 sessionStorage.setItem(
                     "passengerId",
                     data.passengerId
                 );
                 console.log("Stored passengerId in sessionStorage:", data.passengerId);
             } else {
                 console.warn("passengerId not in response data");
             }

             // Store flight number for chatbot and delay prediction
             if (data.flightNumber) {

                 sessionStorage.setItem(
                     "flightNumber",
                     data.flightNumber
                 );
                 console.log("Stored flightNumber in sessionStorage:", data.flightNumber);

             }

             // Store flight details for delay prediction
             if (data.airline) {
                 sessionStorage.setItem("airline", data.airline);
                 console.log("Stored airline in sessionStorage:", data.airline);
             }
             if (data.source) {
                 sessionStorage.setItem("source", data.source);
                 console.log("Stored source in sessionStorage:", data.source);
             }
             if (data.destination) {
                 sessionStorage.setItem("destination", data.destination);
                 console.log("Stored destination in sessionStorage:", data.destination);
             }
             if (data.departureHour) {
                 sessionStorage.setItem("departureHour", data.departureHour);
                 console.log("Stored departureHour in sessionStorage:", data.departureHour);
             }

             console.log("loadPassengerDetails completed successfully");

         })
         .catch(error => {

             console.error(
                 "Error loading passenger details:",
                 error
             );
             console.error("Error stack:", error.stack);

         });

}



// ============================================================
// 2. FLIGHT DELAY PREDICTION
// ============================================================

function predictFlightDelay() {

     const predictionDisplay =
         document.getElementById("prediction");

     const confidenceDisplay =
         document.getElementById("confidence");

     if (!predictionDisplay) {
         console.error("prediction element not found");
         return;
     }

     // Get flight details from session storage
     const airline = sessionStorage.getItem("airline");
     const source = sessionStorage.getItem("source");
     const destination = sessionStorage.getItem("destination");
     const departureHour = sessionStorage.getItem("departureHour");

     if (!airline || !source || !destination || !departureHour) {
         predictionDisplay.textContent = "Flight details not available.";
         return;
     }

     // Get weather and other details from form
     const weatherElement = document.getElementById("weather");
     const holidayElement = document.getElementById("holiday");
     const previousDelayElement = document.getElementById("previousDelay");
     
     const weather = weatherElement ? weatherElement.value : "Clear";
     const holiday = holidayElement ? holidayElement.value : "No";
     const previousDelay = previousDelayElement ? previousDelayElement.value : "0";

     predictionDisplay.textContent = "Checking flight delay prediction...";

     fetch(
         "../PassengerServlet?action=predictDelay" +
         "&airline=" + encodeURIComponent(airline) +
         "&source=" + encodeURIComponent(source) +
         "&destination=" + encodeURIComponent(destination) +
         "&departureHour=" + encodeURIComponent(departureHour) +
         "&weather=" + encodeURIComponent(weather) +
         "&holiday=" + encodeURIComponent(holiday) +
         "&previousDelay=" + encodeURIComponent(previousDelay),
         {
             method: "GET"
         }
     )

         .then(response => {

             if (!response.ok) {
                 throw new Error(
                     "Delay prediction request failed"
                 );
             }

             return response.json();
         })

         .then(data => {

             console.log(
                 "Delay Prediction Response:",
                 data
             );


             if (data.success) {

                 let predictionText = "";

                 if (
                     data.prediction === 1 ||
                     data.prediction === "1"
                 ) {

                     predictionText =
                         "🛑 Flight is likely to be delayed";

                 } else {

                     predictionText =
                         "✅ Flight is likely to be on time";

                 }

                 predictionDisplay.textContent = predictionText;
                 
                 if (confidenceDisplay) {
                     confidenceDisplay.textContent = data.confidence || "--";
                 }

             } else {

                 predictionDisplay.textContent = 
                     data.message || "Unable to predict flight delay.";
                     
                 if (confidenceDisplay) {
                     confidenceDisplay.textContent = "--";
                 }

             }

         })

         .catch(error => {

             console.error(
                 "Delay prediction error:",
                 error
             );

             predictionDisplay.textContent = 
                 "Unable to connect to delay prediction service.";
                 
             if (confidenceDisplay) {
                 confidenceDisplay.textContent = "--";
             }

         });

}



// ============================================================
// 3. OPEN AI CHATBOT
// ============================================================

function openChat() {

     const chatPopup =
         document.getElementById("chatPopup");

     if (!chatPopup) {

         console.error(
             "chatPopup element not found"
         );

         return;
     }

     chatPopup.style.display = "block";

}



// ============================================================
// 4. CLOSE AI CHATBOT
// ============================================================

function closeChat() {

     const chatPopup =
         document.getElementById("chatPopup");

     if (!chatPopup) {
         return;
     }

     chatPopup.style.display = "none";

}



// ============================================================
// 5. SEND MESSAGE TO AI CHATBOT
// ============================================================

function sendChatMessage() {

     console.log("sendChatMessage() called");

     const messageInput =
         document.getElementById("userQuestion");

     const chatMessages =
         document.getElementById("chatMessages");


     if (!messageInput) {

         console.error(
             "userQuestion input not found"
         );

         return;
     }


     if (!chatMessages) {

         console.error(
             "chatMessages element not found"
         );

         return;
     }


     const message =
         messageInput.value.trim();

     console.log("Message to send:", message);

     if (message === "") {

         console.log("Message is empty, returning");
         return;

     }


     // Get passenger's flight number
     const flightNumber =
         sessionStorage.getItem("flightNumber");

     // Get passenger's ID
     const passengerId =
         sessionStorage.getItem("passengerId");

     console.log("Retrieved from sessionStorage - flightNumber:", flightNumber, "passengerId:", passengerId);

     if (!flightNumber) {

         console.warn("Flight number not available");
         addChatMessage(
             "AI",
             "Your flight number is not available. Please reload the page."
         );

         return;

     }

     if (!passengerId) {

         console.warn("Passenger ID not available");
         addChatMessage(
             "AI",
             "Your passenger ID is not available. Please reload the page."
         );

         return;

     }


     // Display passenger message
     addChatMessage(
         "You",
         message
     );


     // Clear input
     messageInput.value = "";


     // Display temporary AI message
     const loadingMessage =
         document.createElement("div");

     loadingMessage.className =
         "chat-message ai-message";

     loadingMessage.innerHTML =
         "<strong>AI:</strong> Thinking...";

     chatMessages.appendChild(
         loadingMessage
     );


     chatMessages.scrollTop =
         chatMessages.scrollHeight;

     console.log("Sending chat request to PassengerServlet");
     console.log("Request body: message=" + message + "&flightNumber=" + flightNumber + "&passengerId=" + passengerId);

     // Send request to Java Servlet
     fetch(
         "../PassengerServlet?action=chat",
         {
             method: "POST",

             headers: {
                 "Content-Type":
                     "application/x-www-form-urlencoded"
             },

             body:
                 "message=" +
                 encodeURIComponent(message) +

                 "&flightNumber=" +
                 encodeURIComponent(flightNumber) +

                 "&passengerId=" +
                 encodeURIComponent(passengerId)
         }
     )

         .then(response => {

             console.log("Chat response received - Status:", response.status);

             if (!response.ok) {

                 throw new Error(
                     "Chat request failed with status: " + response.status
                 );

             }

             return response.json();

         })

         .then(data => {

             console.log(
                 "Chatbot Response Data:",
                 data
             );
             console.log("Response keys:", Object.keys(data));


             // Remove "Thinking..."
             if (loadingMessage.parentNode) {
                 loadingMessage.remove();
             }


             // Check if response is successful
             if (data.success) {

                 console.log("Response successful, showing message");
                 addChatMessage(
                     "AI",
                     data.response ||
                     data.message ||
                     "I processed your question."
                 );

             } else if (data.message) {
                 
                 // Handle error message from servlet
                 console.log("Response has error message:", data.message);
                 addChatMessage(
                     "AI",
                     data.message
                 );

             } else if (data.reply) {
                 
                 // Handle old format response
                 console.log("Response has reply field:", data.reply);
                 addChatMessage(
                     "AI",
                     data.reply
                 );

             } else {

                 console.warn("Response format not recognized");
                 addChatMessage(
                     "AI",
                     "Sorry, I could not understand your question. Please try again."
                 );

             }

         })

         .catch(error => {

             console.error(
                 "Chatbot error:",
                 error
             );
             console.error("Error message:", error.message);


             // Remove "Thinking..."
             if (loadingMessage.parentNode) {
                 loadingMessage.remove();
             }

             // Provide specific error messages based on error type
             let errorMessage = "Unable to connect to AI Assistant. Please make sure the AI service is running.";
             
             if (error.message && error.message.includes("Failed to fetch")) {
                 errorMessage = "Connection failed. The AI service may not be running at http://127.0.0.1:5000/chat";
                 console.warn("⚠️ Cannot connect to AI API. Ensure Flask server is running on port 5000.");
             } else if (error.message && (error.message.includes("timeout") || error.message.includes("timed out"))) {
                 errorMessage = "Request timed out. The AI service is taking too long to respond. Please try again.";
                 console.warn("⚠️ Request timeout - AI service may be busy or slow to respond.");
             }

             addChatMessage(
                 "AI",
                 errorMessage
             );

         });

}



// ============================================================
// 6. DISPLAY CHAT MESSAGE
// ============================================================

function addChatMessage(
     sender,
     message
) {

     const chatMessages =
         document.getElementById("chatMessages");


     if (!chatMessages) {

         console.error(
             "chatMessages element not found"
         );

         return;

     }


     const messageDiv =
         document.createElement("div");


     if (sender === "You") {

         messageDiv.className =
             "chat-message user-message";

     } else {

         messageDiv.className =
             "chat-message ai-message";

     }


     messageDiv.innerHTML = `

         <strong>${sender}:</strong>

         <span>
             ${message}
         </span>

     `;


     chatMessages.appendChild(
         messageDiv
     );


     // Automatically scroll to latest message
     chatMessages.scrollTop =
         chatMessages.scrollHeight;

}



// ============================================================
// 7. SEND MESSAGE WHEN ENTER KEY IS PRESSED
// ============================================================

function handleChatKeyPress(event) {

     if (event.key === "Enter") {

         event.preventDefault();

         sendChatMessage();

     }

}



// ============================================================
// 8. PAGE LOAD
// ============================================================

document.addEventListener(
     "DOMContentLoaded",
     function () {

         console.log(
             "Passenger Dashboard JavaScript loaded"
         );

         // Log all DOM elements we're looking for
         console.log("=== DOM ELEMENT CHECK ===");
         const elementsToCheck = [
             "passengerName", "flightNumber", "sourceText", "destinationText", "airlineText",
             "departure", "arrival", "bookingId", "name", "seat", "terminal", "gateNumber", "status",
             "prediction", "confidence", "weather", "holiday", "previousDelay",
             "chatPopup", "chatIcon", "closeChat", "sendChatBtn", "userQuestion", "chatMessages",
             "predictDelayBtn"
         ];
         
         elementsToCheck.forEach(function(elementId) {
             const el = document.getElementById(elementId);
             if (el) {
                 console.log("✓ Found element:", elementId);
             } else {
                 console.warn("✗ Missing element:", elementId);
             }
         });
         console.log("=== END DOM CHECK ===");

         // Load passenger ticket details
         loadPassengerDetails();


         // Delay prediction button
         const predictDelayBtn =
             document.getElementById(
                 "predictDelayBtn"
             );


         if (predictDelayBtn) {

             predictDelayBtn.addEventListener(
                 "click",
                 predictFlightDelay
             );
             console.log("Attached click listener to predictDelayBtn");

         } else {
             console.warn("predictDelayBtn not found - delay prediction button will not work");
         }


         // Chat icon
         const chatIcon =
             document.getElementById(
                 "chatIcon"
             );


         if (chatIcon) {

             chatIcon.addEventListener(
                 "click",
                 openChat
             );
             console.log("Attached click listener to chatIcon");

         } else {
             console.warn("chatIcon not found - chat icon will not work");
         }


         // Close chat button
         const closeChatBtn =
             document.getElementById(
                 "closeChat"
             );


         if (closeChatBtn) {

             closeChatBtn.addEventListener(
                 "click",
                 closeChat
             );
             console.log("Attached click listener to closeChat");

         } else {
             console.warn("closeChat button not found - cannot close chat");
         }


         // Send chat button
         const sendChat =
             document.getElementById(
                 "sendChatBtn"
             );


         if (sendChat) {

             sendChat.addEventListener(
                 "click",
                 sendChatMessage
             );
             console.log("Attached click listener to sendChatBtn");

         } else {
             console.warn("sendChatBtn not found - send button will not work");
         }


         // Chat input Enter key
         const chatMessage =
             document.getElementById(
                 "userQuestion"
             );


         if (chatMessage) {

             chatMessage.addEventListener(
                 "keydown",
                 handleChatKeyPress
             );
             console.log("Attached keydown listener to userQuestion input");

         } else {
             console.warn("userQuestion input not found - Enter key binding will not work");
         }

     }
);
