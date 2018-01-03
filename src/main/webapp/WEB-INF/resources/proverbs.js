function postComment() {

    // Client validating data
    var errorMessages = "";
    var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    var comment = document.getElementById("comment").value.trim();
    if (!comment || isEmpty(comment)) {
        errorMessages += "- Comment is required.\n";
    }

    var name = document.getElementById("name").value.trim();
    if (!name || isEmpty(name)) {
        errorMessages += "- Name is required.\n";
    } else if (isOutOfRange(name, 3, 45)) {
        errorMessages += "- Invalid name.\n";
    }

    var email = document.getElementById("email").value.trim();
    if (!email || isEmpty(email)) {
        errorMessages += "- Email is required.\n";
    } else if (isOutOfRange(email, 5, 254) || !matchesPattern(email, emailPattern)) {
        errorMessages += "- Invalid email.\n";
    }

    if (errorMessages.length > 0) {
        alert(errorMessages);
        return false;
    }

    // Using form data format.
    var proverbId = document.getElementById("proverbId").value;
    var json = {'comment': comment, 'name': name, 'email': email};

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status = 200) {
                updateCommentList(this.responseText);
                document.getElementById("commentForm").reset();
            } else if (this.status == 400) {
                var errorMessage = "";
                var errorArray = JSON.parse(this.responseText);
                for (var index = 0; index < errorArray.length; index++) {
                    errorMessage += "- " + errorArray[index];
                }
                alert(errorMessage);
            } else if (this.status == 500) {
                alert("Server Error");
            }
        }
    };
    xhttp.open("POST", proverbId + "/comments", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Accept-Type", "application/json");
    xhttp.send(JSON.stringify(json));

    return true;
}

function isEmpty(data) {
    return data.length === 0;
}

function isOutOfRange(data, minLength, maxLength) {

    return (data.length < minLength || data.length > maxLength);
}

function matchesPattern(data, pattern) {

    return pattern.test(data);
}

function updateCommentList(response) {

    var comment = JSON.parse(response);
    // Getting parent container
    var commentsContainer = document.getElementById("comments-container");

    // Creating HTML for new comments.
    var commentContainer = document.createElement("div");
    commentContainer.setAttribute("class", "comment-container");

    var commentParagraph = document.createElement("p");
    commentParagraph.setAttribute("class", "comment");
    commentParagraph.innerHTML = comment.comment;
    commentContainer.appendChild(commentParagraph);

    var commenterContainer = document.createElement("div");
    commenterContainer.setAttribute("class", "commenter-container");
    commenterContainer.innerHTML = comment.name;
    commentContainer.appendChild(commenterContainer);

    commentsContainer.appendChild(commentContainer);
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}