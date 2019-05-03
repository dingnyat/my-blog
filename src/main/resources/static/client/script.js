function getArticleByCode(code) {
    var data = null;
    var req = new XMLHttpRequest();
    req.open("GET", "/api/article/" + code, true);
    req.responseType = "json";
    req.onreadystatechange = function () {
        if (this.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (this.status !== 200) {
            console.log("Can't get article");
        } else {
            data = JSON.parse(this.responseText);
        }
    };
    req.send();
    return data;
}

function addNewArticle(article, url) {
    var req = new XMLHttpRequest();
    req.open("POST", url, true);
    req.setRequestHeader("Content-Type", "application/json");
    req.responseType = "text";
    req.onreadystatechange = function () {
        if (this.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (this.status !== 200) {
            console.log("Error! Can't save article!");
        } else {
            console.log(this.responseText);
        }
    };
    req.send(JSON.stringify(article));
}

function getAllTags(func) {
    var req = new XMLHttpRequest();
    req.open("GET", "/api/get-all-tags", true);
    req.responseType = "json";
    req.onreadystatechange = function () {
        if (this.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (this.status !== 200) {
            console.log("Can't get tags");
        } else {
            func(this.response);
        }
    };
    req.send();
}