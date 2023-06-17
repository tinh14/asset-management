import {sendRequest, getURL, showErrorMessage, getFormData} from './common.js';

function login() {  
    var data = getFormData("#form");

    sendRequest(getURL("login"), "POST", JSON.stringify(data), "application/json",
            function () {
                location.href = getURL("dashboard/view");
            },
            function (xhr) {
                showErrorMessage(xhr.responseText);
            }
    );
}

$('#loginBtn').click(function () {
    login();
});

$('#password, #username').keydown(function (event) {
    if (event.key === "Enter") {
        login();
    }
});