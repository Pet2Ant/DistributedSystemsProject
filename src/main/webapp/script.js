function createMeasurement() {
    document.getElementById("errorDiv").innerHTML = '';
    const url = "http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/";
    const method = "POST";
    const data = {
        id: Number(document.getElementById("healthId").value),
        blood_glucose_level: Number(document.getElementById("bloodGlucoseLevel").value),
        carb_intake: Number(document.getElementById("carbIntake").value),
        medication_dose: Number(document.getElementById("medicationDose").value),
        date: new Date(document.getElementById("date").value).toISOString()
    };
    fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(element => {
                document.getElementById("resultDiv").innerHTML += "id = " + element.id +
                    " blood_glucose_level = " + element.blood_glucose_level + " carb_intake = " + element.carb_intake +
                    " medication_dose = " + element.medication_dose + " date = " + convertMillisecondsToDate(element.date) + "<br>"

        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:', error);
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:';
            }
        });
}


function findHealthDataById() {
    document.getElementById("errorDiv").innerHTML = '';
    const url = "http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/";
    const method = "GET";
    const healthId = Number(document.getElementById("healthId").value);

    fetch(url + healthId, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(d => {
            document.getElementById("resultDiv").innerHTML = JSON.stringify(d);
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:', error);
                document.getElementById("errorDiv").innerHTML = 'There was a problem with the Fetch operation:';
            }
        });
}

function convertMillisecondsToDate(milliseconds) {
    let date = new Date(milliseconds);
    console.log(date.toLocaleDateString());
    return date.toLocaleDateString();
}


function getAllMeasurements() {
    document.getElementById("errorDiv").innerHTML = '';

    const url = "http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/";
    const method = "GET";


    fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })


        .then(res => res.json())
        .then(dataArray => {
            document.getElementById("resultDiv").innerHTML = "";

            dataArray.forEach(element =>
                document.getElementById("resultDiv").innerHTML += "id = " + element.id +
                    " blood_glucose_level = " + element.blood_glucose_level + " carb_intake = " + element.carb_intake +
                    " medication_dose = " + element.medication_dose + " date = " + convertMillisecondsToDate(element.date) + "<br>"
            );
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:');
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:' + error;
            }
        });
}

function getAvgBloodGlucoseLevel() {
    document.getElementById("errorDiv").innerHTML = '';

    // Get the startDate and endDate from the input fields
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    // Create a new URL object
    const url = new URL("http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/avgGlucose");

    // Append the search parameters
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);

    const method = "GET";

    fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("resultDiv").innerHTML = "Average blood glucose level: " + data;
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:');
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:' + error;
            }
        });
}

function getAvgCarbIntake() {
    document.getElementById("errorDiv").innerHTML = '';

    // Get the startDate and endDate from the input fields
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    // Create a new URL object
    const url = new URL("http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/avgCarbs");

    // Append the search parameters
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);

    const method = "GET";

    fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("resultDiv").innerHTML = "Average carb intake: " + data;
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:');
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:' + error;
            }
        });
}
function displayGlucoseChart()
{
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    // Create a new URL object
    const url = new URL("http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/glucoseLevelOverTimePeriod");

    // Append the search parameters
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);
    document.getElementById('glucoseChart').src = url;
}
function carbIntakeChart()
{
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;


    const url = new URL("http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/carbIntakeOverTimePeriod");


    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);
    document.getElementById('carbIntakeChart').src = url;
}
function displayOverAPeriodOfTime() {
    document.getElementById("errorDiv").innerHTML = '';
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const url = new URL("http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/otp");
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);
    const method = "GET";


    fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(dataArray => {
            document.getElementById("resultDiv").innerHTML = "";

            dataArray.forEach(element =>
                document.getElementById("resultDiv").innerHTML += "id = " + element.id +
                    " blood_glucose_level = " + element.blood_glucose_level + " carb_intake = " + element.carb_intake +
                    " medication_dose = " + element.medication_dose + " date = " + convertMillisecondsToDate(element.date) + "<br>"
            );
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:');
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:' + error;
            }
        });
}

function changeHealthData() {
    document.getElementById("errorDiv").innerHTML = '';
    const url = "http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/";
    const method = "PUT";

    const data = {
        id: Number(document.getElementById("healthId").value),
        blood_glucose_level: document.getElementById("bloodGlucoseLevel").value,
        carb_intake: Number(document.getElementById("carbIntake").value),
        medication_dose: Number(document.getElementById("medicationDose").value),
        date: new Date(document.getElementById("date").value).toISOString()
    };


    fetch(url + data.id, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(data), // body data type must match "Content-Type" header
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(element => {
                document.getElementById("resultDiv").innerHTML += "id = " + element.id +
                    " blood_glucose_level = " + element.blood_glucose_level + " carb_intake = " + element.carb_intake +
                    " medication_dose = " + element.medication_dose + " date = " + convertMillisecondsToDate(element.date) + "<br>"
            })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:', error);
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:';
            }
        });
}


function deleteHealthData() {
    document.getElementById("errorDiv").innerHTML = '';
    const url = "http://localhost:8080/DistributedSystems_war_exploded/api/healthdata/";
    const method = "DELETE";
    const healthId = Number(document.getElementById("healthId").value);

    fetch(url + healthId, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //  body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(res => res.json())
        .then(d => {
            document.getElementById("resultDiv").innerHTML = JSON.stringify(d);
        })
        .catch(error => {
            if (error instanceof TypeError && error.message.includes('API key')) {
                console.error('Invalid API key:', error);
                document.getElementById("errorDiv").innerHTML = 'Invalid API key:' + error;
            } else {
                console.error('There was a problem with the Fetch operation:', error);
                document.getElementById("errorDiv").innerHTML = 'here was a problem with the Fetch operation:';
            }
        });
}
