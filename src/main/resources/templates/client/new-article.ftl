<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = "Workspace">
    <#include "fragment/layout-header.ftl">
  <script type="text/javascript" src="${'/vendor/ckeditor/ckeditor.js'}"></script>
  <script type="text/javascript" src="${'/client/script.js'}"></script>
  <script>
      window.onload = function () {
          let selectedTags = new Map();
          let searchBox = document.getElementById("autocomplete-box");
          getAllTags(function (respData) {
              searchAutocomplete(searchBox, respData, function (selectedItem) {
                  selectedTags.set(selectedItem.code, selectedItem);
                  showTagList(document.getElementById("selected-tag-list"), selectedTags);
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
                  category: function () {
                      let select = document.getElementById("select-category");
                      return select.options[select.selectedIndex].value;
                  }(),
                  series: function () {
                      let select = document.getElementById("select-series");
                      return select.options[select.selectedIndex].value;
                  }(),
                  positionInSeries: document.getElementById("input-position").value
              };
              console.log(data);
              // addNewArticle(data, "/api" + window.location.pathname);
          };
      };
  </script>
  <style>
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
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <h3 class="mt-3">Write a new post</h3>
  <form id="new-post-form" class="text-left" action="${springMacroRequestContext.getRequestUri()}" method="post">
    <div class="form-group">
      <label for="input-code">Code</label>
      <input id="input-code" class="form-control" type="text" name="code" value="">
    </div>
    <div class="form-group">
      <label for="input-title">Title</label>
      <input id="input-title" class="form-control" type="text" name="title" value="">
    </div>
    <div class="form-group">
      <label for="autocomplete-box">Tags:</label>
      <input id="autocomplete-box" class="form-control" type="text" name="tag" autocomplete="off">
    </div>
    <div class="form-group">
      <div id="selected-tag-list" class="d-flex">
      </div>
    </div>
    <div class="form-group">
      <label for="select-category">Category</label>
      <select class="form-control" id="select-category">
          <#list categories as category>
            <option value="${category.code}">${category.name}</option>
          </#list>
      </select>
    </div>
    <div class="form-row">
      <div class="form-group col-md-6">
        <label for="select-series">Series</label>
        <select class="form-control" id="select-series">
            <#list seriesList as series>
              <option value="${series.code}">${series.name}</option>
            </#list>
        </select>
      </div>
      <div class="form-group col-md-6">
        <label for="input-position">Position in Series</label>
        <input id="input-position" class="form-control" type="text" autocomplete="off">
      </div>
    </div>
    <div class="form-group">
      <label>
        Article content:
        <textarea id="input-content"></textarea>
        <script>
            CKEDITOR.replace('input-content');
        </script>
      </label>
    </div>
    <button class="btn btn-warning" type="submit">Publish</button>
  </form>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>