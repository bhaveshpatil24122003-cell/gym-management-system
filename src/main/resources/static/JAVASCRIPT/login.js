const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("loginMessage");

const existingToken = localStorage.getItem("token");
const existingRole = localStorage.getItem("role");

if (existingToken && existingRole) {

    if (existingRole === "ADMIN") {
        window.location.href = "/HTML/admin-dashboard.html";
    }

    if (existingRole === "MEMBER") {
        window.location.href = "/HTML/member-dashboard.html";
    }
}


loginForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    loginMessage.textContent = "Logging in...";
    loginMessage.className = "message";

    try {

        const response = await fetch("/auth/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                email: email,
                password: password
            })

        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "Invalid email or password");
        }
		
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);

        loginMessage.textContent = "Login successful";
        loginMessage.className = "message success-message";


        if (data.role === "ADMIN") {

            window.location.href =
                "/HTML/admin-dashboard.html";

        } else if (data.role === "MEMBER") {

            window.location.href =
                "/HTML/member-dashboard.html";

        } else {

            localStorage.clear();

            loginMessage.textContent =
                "Invalid user role";
        }

    } catch (error) {

        loginMessage.textContent = error.message;
        loginMessage.className = "message error-message";

    }

});