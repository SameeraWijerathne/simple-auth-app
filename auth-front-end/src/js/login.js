import {API_BASE_URL} from "./main.js";

const frmLogin = $("#frm-login");
const username = $("#username");
const password = $("#password");

frmLogin.on('submit', (evt) => {
    evt.preventDefault();

    const jqxhr = $.ajax(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        contentType: 'application/json',
        xhrFields: {
            withCredentials: true
        },
        data: JSON.stringify({
            username: username.val().trim(),
            password: password.val().trim()
        })
    });

    jqxhr.done(() => {
        localStorage.setItem("username", username.val().trim());
        location.replace('index.html');
    });

    jqxhr.fail(() => {
        if (jqxhr.status === 401) {
            alert("Invalid login credentials");
        } else {
            alert("Something went wrong, please try again!");
        }
        username.trigger('focus');
    });
});