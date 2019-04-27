<!DOCTYPE html>
<html lang="en">
<head>
  <#assign title = "Workspace">
  <#include "./fragment/header-html.ftl">
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <a class="btn btn-primary" href="${springMacroRequestContext.getRequestUri() + '/new-article'}">New</a>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>