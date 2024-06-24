let timeoutId;

document.addEventListener("DOMContentLoaded", function() {
    function getQueryParam(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    function showAlert() {
        const p = document.getElementById("warn");
        p.style.visibility = "visible";

        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(function() {
            p.style.visibility = "hidden";
            timeoutId = null;
        }, 3000);
    }

    const errorParam = getQueryParam("error");
    if (errorParam && errorParam === "true") {
        showAlert();
    }
});