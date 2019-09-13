<#include "./layout/layout.ftl"/>

<#macro customResources>
  <script type="text/javascript">
      $(function () {
          $('.toggle-password').on('click', function () {
              $(this).toggleClass('fa-eye fa-eye-slash');
              let input = $(this).parent().find("input");
              if (input.attr('type') === 'password') {
                  input.attr('type', 'text');
              } else {
                  input.attr('type', 'password');
              }
          });
      });
  </script>
  <style>
    span.field-icon {
      position: absolute;
      display: inline-block;
      cursor: pointer;
      right: 1.1rem;
      top: 8rem;
      z-index: 2;
    }
  </style>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid text-center mt-5 mb-5">
    <div class="row d-flex justify-content-center align-items-center">
      <form class="col-8 col-sm-6 col-md-6 col-lg-4 col-xl-3" action="${'/login'}" method="post">
        <h1 class="h3 mb-5 font-weight-normal">Đăng Nhập</h1>
        <#if RequestParameters.error?? && RequestParameters.error == "true">
          <p class="text-danger small">Tên tài khoản hoặc mật khẩu không đúng!</p>
        </#if>
        <input name="${_csrf.parameterName}" value="${_csrf.token}" hidden>
        <div class="form-group my-1">
          <label for="username" class="sr-only">Tên tài khoản</label>
          <input type="text" name="username" id="username" class="form-control" placeholder="Tên tài khoản" required
                 autofocus>
        </div>
        <div class="form-group">
          <input type="password" name="password" id="password" placeholder="Mật khẩu" class="form-control" required>
          <span class="btn fas fa-eye field-icon toggle-password"></span>
        </div>
        <div class="custom-control custom-checkbox mb-3">
          <input type="checkbox" class="custom-control-input" id="remember-me" name="remember-me">
          <label class="custom-control-label" for="remember-me">Giữ đăng nhập</label>
        </div>
        <button class="btn btn-lg btn-warning btn-block" type="submit">Đăng Nhập</button>
      </form>
    </div>
  </section>
</#macro>

<@displayPage page_title="Đăng Nhập"/>
