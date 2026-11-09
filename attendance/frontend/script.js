

document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const role = document.getElementById("role").value;
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (username === "" || password === "") {
        alert("Please enter username and password!");
        return;
    }

    if (role === "Faculty") {
        window.location.href = "faculty-dashboard.html";
    }
    else if (role === "Student") {
        window.location.href = "student-dashboard.html";
    }
    else {
        alert("Please select a valid role!");
    }
});
