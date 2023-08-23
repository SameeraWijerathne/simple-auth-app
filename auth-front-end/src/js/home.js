import {API_BASE_URL, existLoggedUser} from "./main.js";

const tbodyElm = $("#tbl-customers tbody");

if (!existLoggedUser()) {
    location.replace("login.html");
} else {
    $("#username").text(localStorage.getItem("username"));
    const jqxhr = $.ajax(`${API_BASE_URL}/customers`, {
       xhrFields: {
           withCredentials: true
       }
    });

    jqxhr.done((customerList) => {
        if (customerList.length) $("#tbl-customers tfoot").hide();
        customerList.forEach(customer => {
            const trElm = $(`<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                            </tr>`);
            tbodyElm.append(trElm);
        });
    });

    jqxhr.fail(() => {
        if (jqxhr.status === 401) {
            localStorage.removeItem("username");
            location.replace('login.html');
        }
    });
}