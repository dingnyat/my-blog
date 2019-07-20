<!DOCTYPE html>
<html lang="en">
<head>
  <#assign title = "Login">
  <#include "fragment/layout-header.ftl">
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid text-center pt-5 pb-5">
  <form class="form-login" action="/login" method="post">
    <h1 class="h3 mb-5 font-weight-normal">Đăng nhập</h1>
    <#if RequestParameters.error?? && RequestParameters.error == "true">
      <p class="text-danger small">Tên tài khoản hoặc mật khẩu không đúng!</p>
    </#if>
    <label for="username" class="sr-only">Tên tài khoản</label>
    <input type="text" name="username" id="username" class="form-control" placeholder="Tên tài khoản" required
           autofocus>
    <label for="password" class="sr-only">Mật khẩu</label>
    <input type="password" name="password" id="password" class="form-control" placeholder="Mật khẩu" required>
    <div class="custom-control custom-checkbox mb-3">
      <input type="checkbox" class="custom-control-input" id="remember-me" name="remember-me">
      <label class="custom-control-label" for="remember-me">Giữ đăng nhập</label>
    </div>
    <button class="btn btn-lg btn-warning btn-block" type="submit">Đăng nhập</button>
  </form>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>