<footer class="container-fluid">
  <div class="col-md-12 text-dark bg-light text-center">
    <div class="row pt-4">
      <div class="col-md-4">
        <h5 class="font-weight-normal">THỂ LOẠI</h5>
        <ul class="pl-0 footer-link">
          <li><a href="/">Java</a></li>
          <li><a href="/">Servlet/JSP</a></li>
          <li><a href="/">Spring Framework</a></li>
          <li><a href="/">JPA/Hibernate</a></li>
          <li><a href="/">AngularJS</a></li>
          <li><a href="/">AngularCLI</a></li>
        </ul>
      </div>
      <div class="col-md-4">
        <h5 class="font-weight-normal">HƯỚNG DẪN</h5>
        <ul class="pl-0 footer-link">
          <li><a href="/">Lập trình Web với Servlet/JSP</a></li>
          <li><a href="/">Lập trình Web với Spring Framework</a></li>
          <li><a href="/">Tự học Angular JS</a></li>
          <li><a href="/">Lập trình cơ bản Java</a></li>
        </ul>
      </div>
      <div class="col-md-4">
        <h5 class="font-weight-normal">THÔNG TIN - LIÊN HỆ</h5>
        <ul class="pl-0 footer-link">
          <li><a href="/">Giới thiệu blog</a></li>
          <li><a href="/">Liên hệ quảng cáo</a></li>
          <li><a href="/">Tuyển người viết bài</a></li>
        </ul>
      </div>
    </div>
    <div class="row">
      <p class="col-md-12 mt-5 mb-3">Annanjin<sup>&copy;</sup> 2019 - ${(.now)?string.yyyy}</p>
    </div>
  </div>
</footer>
<#--scroll up button-->
<div>
  <button id="scroll-up-btn" class="btn btn-warning"><i class="fas fa-arrow-up"></i></button>
  <script>
      window.onscroll = function () {
          if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
              document.getElementById("scroll-up-btn").style.display = "inline-block";
          } else {
              document.getElementById("scroll-up-btn").style.display = "none";
          }
      };

      document.getElementById("scroll-up-btn").onclick = function () {
          document.body.scrollTop = 0;
          document.documentElement.scrollTop = 0;
      };
  </script>
</div>