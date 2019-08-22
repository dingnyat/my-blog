<#include "../layout/layout.ftl"/>

<#macro customResources>
  <script type="text/javascript" src="<@s.url '/vendor/ckeditor/ckeditor.js'/>"></script>
  <#--load content truoc khi load ckeditor nen bi loi luc show duoc content, luc ko-->
  <script>
      let csrf_token = '${_csrf.token}';
      let selectedTags = new Map();
      window.onload = function () {
          let searchBox = document.getElementById("autocomplete-tags");

          getAllTags(function (respData) {
              searchAutocomplete(searchBox, respData, function (selectedItem) {
                  selectedTags.set(selectedItem.id, selectedItem);
                  showSelectedTags(document.getElementById("selected-tags"), selectedTags);
              });
          });

          let form = document.getElementById("update-post-form");
          form.onsubmit = function (ev) {
              ev.preventDefault();
              let data = {
                  id: document.getElementsByName("id")[0].value,
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

              updatePost(data, "/user/post/update");
          };

          getPostById(${postId}); // freemarker
      };

      function getAllTags(func) {
          let req = new XMLHttpRequest();
          req.open("POST", "/user/post/get-tags", true);
          req.responseType = "json";
          req.setRequestHeader("X-CSRF-TOKEN", csrf_token);
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

      function showSelectedTags(element, selectedTags) {
          element.innerHTML = '';
          let content = '';
          selectedTags.forEach(function (value, key) {
              content += '<span class="chip my-1 ml-1">' + value.name + '<i onclick="deselectTag(' + key + ')" class="close fas fa-times"></i></span>';
          });
          element.innerHTML = content;
      }

      function deselectTag(id) {
          selectedTags.delete(id);
          showSelectedTags(document.getElementById("selected-tags"), selectedTags);
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

      function getPostById(id) {
          let data = null;
          let req = new XMLHttpRequest();
          req.open("POST", "/user/post/get/" + id, true);
          req.responseType = "text";
          req.setRequestHeader("X-CSRF-TOKEN", csrf_token);
          req.onreadystatechange = function () {
              if (this.readyState !== XMLHttpRequest.DONE) {
                  return;
              }
              if (this.status !== 200) {
                  console.log("Can't get post");
              } else {
                  data = JSON.parse(this.responseText);
                  document.getElementById("input-id").value = data.id;
                  document.getElementById("input-code").value = data.code;
                  document.getElementById("input-title").value = data.title;
                  document.getElementById("input-position").value = data.positionInSeries;
                  if (data.seriesCode != null)
                      document.querySelector('#select-series [value="' + data.seriesCode + '"]').selected = true;
                  CKEDITOR.instances["input-content"].setData(data.content);
                  selectedTags.clear();
                  data.tags.forEach(function (value) {
                      selectedTags.set(value.id, value);
                  });
                  showSelectedTags(document.getElementById("selected-tags"), selectedTags);
              }
          };
          req.send();
      }

      function updatePost(post, url) {
          let req = new XMLHttpRequest();
          req.open("PUT", url, true);
          req.setRequestHeader("Content-Type", "application/json");
          req.responseType = "text";
          req.setRequestHeader("X-CSRF-TOKEN", csrf_token);
          req.onreadystatechange = function () {
              if (this.readyState !== XMLHttpRequest.DONE) {
                  return;
              }
              if (this.status !== 200) {
                  alert("Error! Can't save post!");
              } else {
                  $("#notification-dialog .modal-body p").html("Đã chỉnh sửa bài viết!");
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
      border-radius: 8px;
    }

    .autocomplete-items div:hover {
      background-color: #2eb7ff;
    }

    .autocomplete-active {
      background-color: DodgerBlue !important;
      color: #ffffff;
    }

    .chip {
      display: inline-block;
      height: 32px;
      font-size: 13px;
      font-weight: 500;
      color: rgba(0, 0, 0, .6);
      line-height: 32px;
      padding: 0 12px;
      border-radius: 16px;
      cursor: pointer;
      transition: all .3s linear;
      background-color: #f1c3e2;
    }

    .chip .close {
      cursor: pointer;
      float: right;
      font-size: 16px;
      line-height: 32px;
      padding-left: 8px;
      transition: all .1s linear;
    }

    .chip .close:hover {
      color: #ff1744;
    }

    form .sign.fas {
      color: rgba(0, 0, 0, 0.6);
      margin-right: 10px;
    }

    #update-post-form label {
      width: 100%;
    }

    .form-group, .form-row {
      padding-left: 15px;
      padding-right: 15px;
    }
  </style>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5 px-5 text-center">
    <h3 class="mt-3">Chỉnh Sửa Bài Viết</h3>
    <form id="update-post-form" class="text-left" action="${'/user/post/update'}" method="post">
      <div class="form-group">
        <label for="input-id">ID: </label>
        <input id="input-id" class="form-control" type="text" name="id" readonly>
      </div>
      <div class="form-group">
        <label for="input-code">Mã bài viết: </label>
        <input id="input-code" class="form-control" type="text" name="code" value="">
      </div>
      <div class="form-group">
        <label for="input-title">Tiêu đề: </label>
        <input id="input-title" class="form-control" type="text" name="title" value="">
      </div>
      <div class="form-group">
        <label for="autocomplete-tags">Thẻ :</label>
        <input id="autocomplete-tags" class="form-control" type="text" name="tag" autocomplete="off">
      </div>
      <div class="my-3 ml-3">
        <i class="sign fas fa-tags"></i>
        <span id="selected-tags"></span>
      </div>
      <div class="form-row">
        <div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
          <label for="select-series">Series: </label>
          <select class="form-control" id="select-series">
            <option value="" selected>Chọn series...</option>
            <#if seriesList??>
              <#list seriesList as series>
                <option value="${series.code}">${series.name}</option>
              </#list>
            </#if>
          </select>
        </div>
        <div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
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
            CKEDITOR.replace('input-content', {
                filebrowserImageBrowseUrl: '/file-manager?type=Image',
                filebrowserImageUploadUrl: '/vendor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Image',
            });
            CKEDITOR.config.height = 500;
        </script>
      </div>
      <br/><br/>
      <div class="form-group text-center">
        <button class="btn btn-lg btn-warning text-center" type="submit">Chỉnh Sửa</button>
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
</#macro>

<@displayPage page_title="Chỉnh sửa bài viết"/>
