const token = localStorage.getItem("token");
const role = localStorage.getItem("role");


if (!token || role !== "MEMBER") {

    localStorage.clear();

    window.location.href =
        "/HTML/login.html";

}


function logout() {

    localStorage.removeItem("token");
    localStorage.removeItem("role");

    window.location.href =
        "/HTML/login.html";

}


async function loadMemberDashboard() {

    try {

        const response =
            await fetch(
                "/dashboard/me",
                {

                    method: "GET",

                    headers: {

                        "Authorization":
                            "Bearer " + token

                    }

                }
            );


        if (response.status === 401 ||
            response.status === 403) {

            localStorage.clear();

            window.location.href =
                "/HTML/login.html";

            return;

        }


        const data =
            await response.json();


        if (!response.ok) {

            throw new Error(
                data.message ||
                "Unable to load dashboard"
            );

        }


        displayProfile(data.profile);

        displayMembership(
            data.membership
        );


    } catch (error) {

        document.getElementById(
            "memberMessage"
        ).textContent =
            error.message;

        document.getElementById(
            "memberMessage"
        ).className =
            "message error-message";

    }

}

function displayProfile(profile) {

    if (!profile) {
        return;
    }


    setText(
        "welcomeName",
        profile.username
    );


    setText(
        "profileId",
        profile.id
    );


    setText(
        "profileSequence",
        profile.sequenceNo
    );


    setText(
        "profileRoll",
        profile.rollNo
    );


    setText(
        "profileName",
        profile.username
    );


    setText(
        "profileEmail",
        profile.email
    );


    setText(
        "profilePhone",
        profile.phone
    );


    setText(
        "profileAddress",
        profile.address
    );


    setText(
        "profileGender",
        profile.gender
    );


    setText(
        "profileBlood",
        profile.bloodGroup
    );

}


function displayMembership(membership) {

    if (!membership) {

        document.getElementById(
            "memberMessage"
        ).textContent =
            "Membership not available";

        return;

    }


    setText(
        "membershipIdDisplay",
        membership.membershipId
    );


    setText(
        "membershipPlan",
        membership.subscriptionPlan
    );


    setText(
        "membershipPrice",
        "₹" + membership.price
    );


    setText(
        "registrationDate",
        membership.registrationDate
    );


    setText(
        "expiryDate",
        membership.expiryDate
    );


    setText(
        "paymentModeDisplay",
        membership.paymentMode
    );


    const status =
        document.getElementById(
            "membershipStatus"
        );


    status.textContent =
        membership.status || "-";


    status.className =
        membership.status === "ACTIVE"
            ? "status active"
            : "status expired";

}


function setText(id, value) {

    document.getElementById(
        id
    ).textContent =
        value ??
        "-";

}


document.addEventListener(
    "DOMContentLoaded",
    loadMemberDashboard
);