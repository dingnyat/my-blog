<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
  <#assign title = "Workspace">
  <#include "./fragment/header-html.ftl">
  <script type="text/javascript" src="${'/ckeditor/ckeditor.js'}"></script>
  <script type="text/javascript" src="${'/client/script.js'}"></script>
  <script>
      function showAutoComplete(element, data) {

      }

      window.onload = function () {
          var searchBox = document.getElementById("autocomplete-box");
          getAllTags(function (respData) {
              showAutoComplete(searchBox, respData);
          });

          var form = document.getElementById("new-article-form");
          form.onsubmit = function (ev) {
              ev.preventDefault();
              var data = {
                  code: document.getElementsByName("code")[0].value,
                  title: document.getElementsByName("title")[0].value,
                  content: CKEDITOR.instances["input-content"].getData()
              };
              addNewArticle(data, "/api" + window.location.pathname);
          };
      };
  </script>
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <h3 class="text-left">Write a new article</h3>
  <form id="new-article-form" class="text-left" action="${springMacroRequestContext.getRequestUri()}" method="post">
    <div class="form-group">
      <label>
        Code
        <input class="form-control" type="text" name="code" value="">
      </label>
    </div>
    <div class="form-group">
      <label>
        Title
        <input class="form-control" type="text" name="title" value="">
      </label>
    </div>
    <div class="form-group">
      <label>
        Tags:
        <input id="autocomplete-box" class="form-control" type="text" name="tag" autocomplete="off">
      </label>
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