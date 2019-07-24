<#--noinspection CssUnusedSymbol-->
<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = "Bài Viết Mới">
    <#include "./fragment/layout-header.ftl">
    <script type="text/javascript" src="${'/vendor/ckeditor/ckeditor.js'}"></script>
    <script>
        window.onload = function () {
            let selectedTags = new Map();
            let searchBox = document.getElementById("autocomplete-box");

            getAllTags(function (respData) {
                searchAutocomplete(searchBox, respData, function (selectedItem) {
                    selectedTags.set(selectedItem.id, selectedItem);
                    showSelectedTags(document.getElementById("selected-tag-list"), selectedTags);
                });
            });

            let form = document.getElementById("new-post-form");
            form.onsubmit = function (ev) {
                ev.preventDefault();
                let data = {
                    code: document.getElementsByName("code")[0].value,
                    title: document.getElementsByName("title")[0].value,
                    content: CKEDITOR.instances["input-content"].getData(),
                    tags: Array.from(selectedTags.values()),
                    'seriesCode': function () {
                        let select = document.getElementById("select-series");
                        return select.options[select.selectedIndex].value;
                    }(),
                    positionInSeries: document.getElementById("input-position").value
                };

                addNewPost(data, "/user/post/new");
            };
        };

        function getAllTags(func) {
            let req = new XMLHttpRequest();
            req.open("POST", "/user/post/get-tags", true);
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
                        itemDiv.setAttribute("data-id", elmnt.id);
                        itemDiv.setAttribute("data-name", item);
                        itemDiv.addEventListener("click", function () {
                            getSelectedItemFunc({
                                id: this.getAttribute("data-id"),
                                name: this.getAttribute("data-name")
                            });
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

        function showSelectedTags(element, selectedTags) {
            element.innerHTML = "";
            selectedTags.forEach(function (value, key) {
                let tagDiv = document.createElement("div");
                tagDiv.setAttribute("class", "alert alert-info post-tag");
                tagDiv.innerHTML = value.name;
                let delBtn = document.createElement("button");
                delBtn.setAttribute("type", "button");
                delBtn.setAttribute("class", "close");
                delBtn.setAttribute("data-dismiss", "alert");
                delBtn.innerHTML = "&#10005;";
                delBtn.addEventListener("click", function () {
                    selectedTags.delete(key);
                    showSelectedTags(element, selectedTags);
                });
                tagDiv.appendChild(delBtn);
                element.appendChild(tagDiv);
            });
        }

        function addNewPost(post, url) {
            let req = new XMLHttpRequest();
            req.open("POST", url, true);
            req.setRequestHeader("Content-Type", "application/json");
            req.responseType = "text";
            req.onreadystatechange = function () {
                if (this.readyState !== XMLHttpRequest.DONE) {
                    return;
                }
                if (this.status !== 200) {
                    alert("Error! Can't save post!");
                } else {
                    $("#notification-dialog .modal-body p").html("Đã lưu thêm bài viết!");
                    $("#notification-dialog").modal("show");
                }
            };
            req.send(JSON.stringify(post));
        }
    </script>
    <style>
        .autocomplete-items div {
            padding: 10px;
            cursor: pointer;
            background-color: #f6f7ff;
            border-bottom: 1px solid #d4d4d4;
        }

        /*when hovering an item:*/
        .autocomplete-items div:hover {
            background-color: #2eb7ff;
        }

        /*when navigating through the items using the arrow keys:*/
        .autocomplete-active {
            background-color: DodgerBlue !important;
            color: #ffffff;
        }

        .post-tag {
            width: 20%;
            margin: 1px 5px 10px 5px;
            max-height: 30px;
            line-height: 30px;
            padding: 1px 4px 30px 12px;
        }

        #new-post-form label {
            width: 100%;
        }

        .form-group, .form-row {
            padding-left: 15px;
            padding-right: 15px;
        }
    </style>
</head>
<body>
<header class="container mb-5">
    <#assign link = "post">
    <#include "./fragment/nav.ftl">
</header>
<section class="container pt-3 text-center">
    <h3 class="mt-3">Bài Viết Mới</h3>
    <form id="new-post-form" class="text-left" action="${'/user/post/new'}" method="post">
        <div class="form-group">
            <label for="input-code">Mã bài viết: </label>
            <input id="input-code" class="form-control" type="text" name="code" value="">
        </div>
        <div class="form-group">
            <label for="input-title">Tiêu đề: </label>
            <input id="input-title" class="form-control" type="text" name="title" value="">
        </div>
        <div class="form-group">
            <label for="autocomplete-box">Thẻ :</label>
            <input id="autocomplete-box" class="form-control" type="text" name="tag" autocomplete="off">
        </div>
        <div class="form-group">
            <div id="selected-tag-list" class="d-flex">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="select-series">Series: </label>
                <select class="form-control" id="select-series">
                    <option value="" selected disabled hidden>Chọn series...</option>
                    <#if seriesList??>
                        <#list seriesList as series>
                            <option value="${series.code}">${series.name}</option>
                        </#list>
                    </#if>
                </select>
            </div>
            <div class="form-group col-md-6">
                <label for="input-position">Số thứ tự trong series: </label>
                <input id="input-position" placeholder="Điền số thứ tự trong series nếu có" class="form-control"
                       type="text" autocomplete="off">
            </div>
        </div>
        <br/>
        <div class="form-group">
            <label for="input-content">Nội dung:</label>
            <textarea id="input-content" name="content"></textarea>
            <script>
                CKEDITOR.replace('input-content');
            </script>
        </div>
        <br/><br/>
        <div class="form-group text-center">
            <button class="btn btn-lg btn-warning text-center" type="submit">Đăng Bài</button>
        </div>
    </form>


    <!--nofitication dialog-->
    <div class="modal fade" role="dialog" id="notification-dialog">
        <div class="modal-dialog modal-sm modal-dialog-centered">
            <div class="modal-content text-center">
                <div class="model-header">
                    <h3 class="modal-title">Thông báo</h3>
                </div>
                <div class="modal-body">
                    <p></p>
                </div>
                <div class="modal-footer mx-auto d-block">
                    <a href="${'/user/post'}" class="btn btn-warning">OK</a>
                </div>
            </div>
        </div>
    </div>
</section>
<footer class="sticky-bottom container">
    <#include "./fragment/footer.ftl">
</footer>
</body>
</html>