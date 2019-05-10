function getArticleByCode(code) {
    let data = null;
    let req = new XMLHttpRequest();
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
    let req = new XMLHttpRequest();
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
    let req = new XMLHttpRequest();
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

function searchAutocomplete(element, data, getSelectedItemFunc) {
    let currentFocus;
    element.addEventListener("input", function () {
        let inputValue = this.value;
        closeResultList();
        if (!inputValue) return false;
        currentFocus = -1;
        let $div = document.createElement("div");
        $div.setAttribute("id", this.id + "autocomplete-list");
        $div.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild($div);
        data.forEach(function (elmnt) {
            let item = elmnt.name;
            if (item.substr(0, inputValue.length).toUpperCase() === inputValue.toUpperCase()) {
                let itemDiv = document.createElement("div");
                itemDiv.innerHTML = "<strong>" + item.substr(0, inputValue.length) + "</strong>";
                itemDiv.innerHTML += item.substr(inputValue.length);
                itemDiv.setAttribute("data-code", elmnt.code);
                itemDiv.setAttribute("data-name", item);
                itemDiv.addEventListener("click", function () {
                    getSelectedItemFunc({code: this.getAttribute("data-code"), name: this.getAttribute("data-name")});
                    element.value = "";
                    closeResultList();
                });
                $div.appendChild(itemDiv);
            }
        })
    });

    element.addEventListener("keydown", function (ev) {
        let x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (ev.keyCode === 40) {
            currentFocus++;
            addActive(x);
        } else if (ev.keyCode === 38) {
            currentFocus--;
            addActive(x);
        } else if (ev.keyCode === 13) {
            ev.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });

    document.addEventListener("click", function (e) {
        closeResultList(e.target);
    });

    function closeResultList(elmnt) {
        let x = document.getElementsByClassName("autocomplete-items");
        for (let i = 0; i < x.length; i++) {
            if (elmnt !== x[i] && elmnt !== element) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(x) {
        for (let i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
}

function showTagList(element, selectedTags) {
    element.innerHTML = "";
    selectedTags.forEach(function (value, key) {
        let tagDiv = document.createElement("div");
        tagDiv.setAttribute("class", "alert alert-info article-tag");
        tagDiv.innerHTML = value.name;
        let delBtn = document.createElement("button");
        delBtn.setAttribute("type", "button");
        delBtn.setAttribute("class", "close");
        delBtn.setAttribute("data-dismiss", "alert");
        delBtn.innerHTML = "&#10005;";
        delBtn.addEventListener("click", function () {
            selectedTags.delete(key);
            showTagList(element, selectedTags);
        });
        tagDiv.appendChild(delBtn);
        element.appendChild(tagDiv);
    });
}