<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = "Workspace">
    <#include "fragment/layout-header.ftl">
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <a class="btn btn-primary" href="${springMacroRequestContext.getRequestUri() + '/new-post'}">New</a>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>