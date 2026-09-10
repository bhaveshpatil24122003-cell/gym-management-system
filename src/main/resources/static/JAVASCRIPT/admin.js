const token = localStorage.getItem("token");
const role = localStorage.getItem("role");


// ===============================
// SECURITY CHECK
// ===============================

if (!token || role !== "ADMIN") {

    localStorage.clear();

    window.location.href =
        "/HTML/login.html";

}


// ===============================
// LOGOUT
// ===============================

function logout() {

    localStorage.removeItem("token");
    localStorage.removeItem("role");

    window.location.href =
        "/HTML/login.html";

}


// ===============================
// COMMON API FUNCTION
// ===============================

async function apiRequest(url, options = {}) {

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token
    };


    const response = await fetch(url, options);


    // JWT invalid / expired
    if (response.status === 401 ||
        response.status === 403) {

        localStorage.clear();

        alert("Session expired or access denied");

        window.location.href =
            "/HTML/login.html";

        throw new Error("Unauthorized");
    }


    const contentType =
        response.headers.get("content-type");


    let data;


    if (contentType &&
        contentType.includes("application/json")) {

        data = await response.json();

    } else {

        data = await response.text();

    }


    if (!response.ok) {

        if (typeof data === "object") {

            const validationMessages =
                Object.values(data).join(", ");

            throw new Error(
                data.message ||
                validationMessages ||
                "Request failed"
            );

        }

        throw new Error(data || "Request failed");

    }


    return data;
}


// ===============================
// DASHBOARD
// ===============================

async function loadDashboard() {

    try {

        const data =
            await apiRequest("/dashboard/admin");


        document.getElementById(
            "totalMembers"
        ).textContent =
            data.totalMembers ?? 0;


        document.getElementById(
            "activeMemberships"
        ).textContent =
            data.activeMemberships ?? 0;


        document.getElementById(
            "expiredMemberships"
        ).textContent =
            data.expiredMemberships ?? 0;


        document.getElementById(
            "totalRevenue"
        ).textContent =
            "₹" + (data.totalRevenue ?? 0);

    } catch (error) {

        console.error(error);

    }

}


// ===============================
// MEMBER
// ===============================

const memberForm =
    document.getElementById("memberForm");


memberForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const id =
            document.getElementById(
                "memberId"
            ).value;


        const password =
            document.getElementById(
                "memberPassword"
            ).value;


        const body = {

            sequenceNo:
                Number(
                    document.getElementById(
                        "sequenceNo"
                    ).value
                ),

            rollNo:
                document.getElementById(
                    "rollNo"
                ).value.trim(),

            username:
                document.getElementById(
                    "username"
                ).value.trim(),

            address:
                document.getElementById(
                    "address"
                ).value.trim(),

            email:
                document.getElementById(
                    "memberEmail"
                ).value.trim(),

            phone:
                document.getElementById(
                    "phone"
                ).value.trim(),

            gender:
                document.getElementById(
                    "gender"
                ).value,

            bloodGroup:
                document.getElementById(
                    "bloodGroup"
                ).value,

            role:
                document.getElementById(
                    "memberRole"
                ).value
        };


        // Add me password mandatory
        // Update me optional

        if (password.trim() !== "") {
            body.password = password;
        }


        try {

            let result;


            if (id) {

                result =
                    await apiRequest(
                        "/members/" + id,
                        {
                            method: "PUT",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(body)
                        }
                    );

            } else {

                if (!password.trim()) {

                    throw new Error(
                        "Password is required for new member"
                    );

                }


                body.password = password;


                result =
                    await apiRequest(
                        "/members",
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(body)
                        }
                    );

            }


            showMessage(
                "memberMessage",
                id
                    ? "Member updated successfully"
                    : "Member added successfully",
                true
            );


            resetMemberForm();

            await loadMembers();
            await loadDashboard();


        } catch (error) {

            showMessage(
                "memberMessage",
                error.message,
                false
            );

        }

    }
);


// ===============================
// LOAD MEMBERS
// ===============================

async function loadMembers() {

    try {

        const members =
            await apiRequest("/members");


        const tableBody =
            document.getElementById(
                "membersTableBody"
            );


        tableBody.innerHTML = "";


        members.forEach(member => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${safe(member.id)}</td>

                <td>${safe(member.sequenceNo)}</td>

                <td>${safe(member.rollNo)}</td>

                <td>${safe(member.username)}</td>

                <td>${safe(member.email)}</td>

                <td>${safe(member.phone)}</td>

                <td>${safe(member.gender)}</td>

                <td>${safe(member.bloodGroup)}</td>

                <td>${safe(member.role)}</td>

                <td class="action-buttons">

                    <button
                        class="edit-btn"
                        onclick="editMember(${member.id})">

                        Edit

                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteMember(${member.id})">

                        Delete

                    </button>

                </td>
            `;


            tableBody.appendChild(row);

        });


    } catch (error) {

        console.error(error);

    }

}


// ===============================
// EDIT MEMBER
// ===============================

async function editMember(id) {

    try {

        const member =
            await apiRequest(
                "/members/" + id
            );


        document.getElementById(
            "memberId"
        ).value = member.id;


        document.getElementById(
            "sequenceNo"
        ).value =
            member.sequenceNo ?? "";


        document.getElementById(
            "rollNo"
        ).value =
            member.rollNo ?? "";


        document.getElementById(
            "username"
        ).value =
            member.username ?? "";


        document.getElementById(
            "address"
        ).value =
            member.address ?? "";


        document.getElementById(
            "memberEmail"
        ).value =
            member.email ?? "";


        document.getElementById(
            "phone"
        ).value =
            member.phone ?? "";


        document.getElementById(
            "gender"
        ).value =
            member.gender ?? "";


        document.getElementById(
            "bloodGroup"
        ).value =
            member.bloodGroup ?? "";


        document.getElementById(
            "memberRole"
        ).value =
            member.role || "MEMBER";


        document.getElementById(
            "memberPassword"
        ).value = "";


        document.getElementById(
            "memberFormTitle"
        ).textContent =
            "Update Member";


        window.scrollTo({
            top: 350,
            behavior: "smooth"
        });


    } catch (error) {

        alert(error.message);

    }

}


// ===============================
// DELETE MEMBER
// ===============================

async function deleteMember(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this member?"
        );


    if (!confirmDelete) {
        return;
    }


    try {

        await apiRequest(
            "/members/" + id,
            {
                method: "DELETE"
            }
        );


        alert(
            "Member deleted successfully"
        );


        await loadMembers();
        await loadDashboard();


    } catch (error) {

        alert(error.message);

    }

}


// ===============================
// RESET MEMBER FORM
// ===============================

function resetMemberForm() {

    document.getElementById(
        "memberForm"
    ).reset();


    document.getElementById(
        "memberId"
    ).value = "";


    document.getElementById(
        "memberFormTitle"
    ).textContent =
        "Add Member";


    document.getElementById(
        "memberRole"
    ).value =
        "MEMBER";

}


// ===============================
// MEMBERSHIP FORM
// ===============================

const membershipForm =
    document.getElementById(
        "membershipForm"
    );


membershipForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const membershipId =
            document.getElementById(
                "membershipId"
            ).value;


        const memberId =
            document.getElementById(
                "membershipMemberId"
            ).value;


        const body = {

            subscriptionPlan:
                document.getElementById(
                    "subscriptionPlan"
                ).value,

            price:
                Number(
                    document.getElementById(
                        "price"
                    ).value
                ),

            paymentMode:
                document.getElementById(
                    "paymentMode"
                ).value

        };


        try {

            if (membershipId) {

                await apiRequest(
                    "/memberships/" +
                    membershipId,
                    {

                        method: "PUT",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(body)

                    }
                );


                showMessage(
                    "membershipMessage",
                    "Membership updated successfully",
                    true
                );


            } else {

                await apiRequest(
                    "/memberships/member/" +
                    memberId,
                    {

                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(body)

                    }
                );


                showMessage(
                    "membershipMessage",
                    "Membership added successfully",
                    true
                );

            }


            resetMembershipForm();

            await loadMemberships();
            await loadDashboard();


        } catch (error) {

            showMessage(
                "membershipMessage",
                error.message,
                false
            );

        }

    }
);


// ===============================
// LOAD MEMBERSHIPS
// ===============================

async function loadMemberships() {

    try {

        const memberships =
            await apiRequest(
                "/memberships"
            );


        const tableBody =
            document.getElementById(
                "membershipsTableBody"
            );


        tableBody.innerHTML = "";


        memberships.forEach(
            membership => {


                const row =
                    document.createElement(
                        "tr"
                    );


                row.innerHTML = `

                    <td>
                        ${safe(membership.membershipId)}
                    </td>

                    <td>
                        ${safe(membership.memberId)}
                    </td>

                    <td>
                        ${safe(membership.subscriptionPlan)}
                    </td>

                    <td>
                        ₹${safe(membership.price)}
                    </td>

                    <td>
                        ${safe(membership.registrationDate)}
                    </td>

                    <td>
                        ${safe(membership.expiryDate)}
                    </td>

                    <td>
                        ${safe(membership.paymentMode)}
                    </td>

                    <td>
                        <span class="status ${membership.status === "ACTIVE"
                            ? "active"
                            : "expired"}">

                            ${safe(membership.status)}

                        </span>
                    </td>

                    <td class="action-buttons">

                        <button
                            class="edit-btn"
                            onclick="editMembership(
                                ${membership.membershipId}
                            )">

                            Edit

                        </button>

                        <button
                            class="delete-btn"
                            onclick="deleteMembership(
                                ${membership.membershipId}
                            )">

                            Delete

                        </button>

                    </td>
                `;


                tableBody.appendChild(row);

            }
        );


    } catch (error) {

        console.error(error);

    }

}


// ===============================
// EDIT MEMBERSHIP
// ===============================

async function editMembership(id) {

    try {

        const memberships =
            await apiRequest(
                "/memberships"
            );


        const membership =
            memberships.find(
                item =>
                    item.membershipId === id
            );


        if (!membership) {

            throw new Error(
                "Membership not found"
            );

        }


        document.getElementById(
            "membershipId"
        ).value =
            membership.membershipId;


        document.getElementById(
            "membershipMemberId"
        ).value =
            membership.memberId;


        document.getElementById(
            "membershipMemberId"
        ).disabled = true;


        document.getElementById(
            "subscriptionPlan"
        ).value =
            membership.subscriptionPlan;


        document.getElementById(
            "price"
        ).value =
            membership.price;


        document.getElementById(
            "paymentMode"
        ).value =
            membership.paymentMode;


        document.getElementById(
            "membershipFormTitle"
        ).textContent =
            "Update Membership";


        window.scrollTo({
            top:
                document.body.scrollHeight / 2,
            behavior: "smooth"
        });


    } catch (error) {

        alert(error.message);

    }

}


// ===============================
// DELETE MEMBERSHIP
// ===============================

async function deleteMembership(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this membership?"
        );


    if (!confirmDelete) {
        return;
    }


    try {

        await apiRequest(
            "/memberships/" + id,
            {
                method: "DELETE"
            }
        );


        alert(
            "Membership deleted successfully"
        );


        await loadMemberships();
        await loadDashboard();


    } catch (error) {

        alert(error.message);

    }

}


// ===============================
// RESET MEMBERSHIP FORM
// ===============================

function resetMembershipForm() {

    document.getElementById(
        "membershipForm"
    ).reset();


    document.getElementById(
        "membershipId"
    ).value = "";


    document.getElementById(
        "membershipMemberId"
    ).disabled = false;


    document.getElementById(
        "membershipFormTitle"
    ).textContent =
        "Add Membership";

}


// ===============================
// CHECK EXPIRY
// ===============================

async function checkExpiry() {

    try {

        const result =
            await apiRequest(
                "/memberships/check-expiry",
                {
                    method: "PUT"
                }
            );


        alert(result);


        await loadMemberships();
        await loadDashboard();


    } catch (error) {

        alert(error.message);

    }

}


// ===============================
// MESSAGE
// ===============================

function showMessage(
    elementId,
    text,
    success
) {

    const element =
        document.getElementById(
            elementId
        );


    element.textContent = text;


    element.className =
        success
            ? "message success-message"
            : "message error-message";

}


// ===============================
// SAFE HTML
// ===============================

function safe(value) {

    if (value === null ||
        value === undefined) {
        return "";
    }


    return String(value)

        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}


// ===============================
// REFRESH
// ===============================

async function refreshAll() {

    await loadDashboard();
    await loadMembers();
    await loadMemberships();

}


// ===============================
// PAGE LOAD
// ===============================

document.addEventListener(
    "DOMContentLoaded",
    refreshAll
);