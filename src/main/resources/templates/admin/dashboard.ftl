<!DOCTYPE html>
<html lang="en">
<head>
  <#assign title = "Dashboard">
  <#include "./fragment/layout-header.ftl">
</head>
<body>
<header class="container">
  <#assign activeLink = "dashboard">
  <#include "./fragment/nav.ftl">
</header>
<section class="container">
  dashboard
</section>
<footer class="sticky-bottom container">
  <#include "./fragment/footer.ftl">
</footer>
</body>
</html>