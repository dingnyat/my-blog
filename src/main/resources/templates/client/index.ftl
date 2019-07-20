<!DOCTYPE html>
<html lang="en">
<head>
  <#include "fragment/layout-header.ftl">
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <div class="col-md-12">
    <div class="row">
      <div class="col-md-8">
        content
      </div>
      <div class="col-md-4">
        <#include "./fragment/left-banner-side.ftl">
      </div>
    </div>
  </div>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>