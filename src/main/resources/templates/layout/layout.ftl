<#import "/spring.ftl" as s>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>

<#macro displayPage page_title="">
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <@commonHeaderTags title=page_title/>
    <@commonResources/>
    <@customResources/>
  </head>
  <body>
  <@headerFragment/>
  <@bodyFragment/>
  <@footerFragment/>
  </body>
  </html>
</#macro>


<#--parts of dispay page-->
<#macro commonHeaderTags title>
  <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title><#if title?? && title?has_content>${title + " | "}</#if>Annanjin</title>
  <link rel="icon" href="<@s.url '/favicon.ico'/>"/>
</#macro>
<#macro commonResources>
  <!--jQuery-->
  <script type="text/javascript" src="<@s.url '/vendor/jquery/jquery-3.3.1.min.js'/>"></script>
  <!--Bootstrap jQuery-->
  <script type="text/javascript" src="<@s.url '/vendor/bootstrap/popper.min.js'/>"></script>
  <script type="text/javascript" src="<@s.url '/vendor/bootstrap/bootstrap.min.js'/>"></script>
  <!--Bootstrap css-->
  <link rel="stylesheet" type="text/css" href="<@s.url '/vendor/bootstrap/bootstrap.min.css'/>">
  <!--font awesome-->
  <link rel="stylesheet" type="text/css" href="<@s.url '/vendor/fontawesome-5.9.0/css/all.min.css'/>">
  <#if currentRequestUriHasPrefix(['/workspace', '/admin', '/user'])>
    <!--js jquery table-->
    <script type="text/javascript"
            src="<@s.url '/vendor/datatables-jquery/jquery.dataTables-1.10.19.min.js'/>"></script>
    <!--bootstrap jquery table-->
    <script type="text/javascript"
            src="<@s.url '/vendor/datatables-jquery/dataTables.bootstrap4-1.10.19.min.js'/>"></script>
    <script type="text/javascript" src="<@s.url '/vendor/datatables-jquery/dataTables.select-1.3.0.min.js'/>"></script>
    <!--bootstrap css jquery table-->
    <link rel="stylesheet" type="text/css"
          href="<@s.url '/vendor/datatables-jquery/dataTables.bootstrap4-1.10.19.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<@s.url '/vendor/datatables-jquery/select.bootstrap4-1.3.0.min.css'/>">
  </#if>
  <link rel="stylesheet" href="<@s.url '/css/style.css'/>">
</#macro>
<#macro customResources>
</#macro>
<#macro headerFragment>
  <header class="container-fluid px-0">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
      <a class="navbar-brand text-success font-weight-bold" href="${'/'}">Annanjin</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu-bar"
              aria-controls="menu-bar" aria-expanded="false" aria-label="Menu Toggler">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="menu-bar">
        <ul class="navbar-nav mr-auto">
          <#if currentRequestUriHasPrefix(['/workspace', '/admin', '/user'])>
            <li class="nav-item dropdown">
              <a class="nav-link  dropdown-toggle ${currentRequestUriHasPrefix(['/admin/author', '/admin/category', '/admin/series', '/user/post'])?then('active', '')}"
                 href="#" id="manage-dropdown-btn" data-toggle="dropdown" aria-haspopup="true"
                 aria-expanded="false">Quản Lý</a>
              <div class="dropdown-menu" aria-labelledby="manage-dropdown-btn">
                <@sec.authorize access="hasRole('ROLE_ADMIN')">
                  <a class="dropdown-item" href="${'/admin/author'}">Tác Giả</a>
                  <a class="dropdown-item" href="${'/admin/category'}">Danh Mục/Thẻ</a>
                  <a class="dropdown-item" href="${'/admin/series'}">Series</a>
                </@sec.authorize>
                <@sec.authorize access="hasRole('ROLE_AUTHOR')">
                  <a class="dropdown-item" href="${'/user/post'}">Bài Viết</a>
                </@sec.authorize>
              </div>
            </li>
          <#else>
            <li class="nav-item">
              <a class="nav-link ${currentRequestUriHasPrefix(['/category/java'])?then('active', '')}"
                 href="${'/category/java'}">Java</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle ${currentRequestUriHasPrefix(['/category/servlet-jsp', '/category/spring-framework'])?then('active', '')}"
                 href="#" id="angular-dropdown" data-toggle="dropdown" aria-haspopup="true"
                 aria-expanded="false">Java Web</a>
              <div class="dropdown-menu" aria-labelledby="angular-dropdown">
                <a class="dropdown-item" href="${'/category/servlet-jsp'}">Servlet/JSP</a>
                <a class="dropdown-item" href="${'/category/spring-framework'}">Spring Framework</a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle ${currentRequestUriHasPrefix(['/category/jpa-hibernate', '/category/mybatis'])?then('active', '')}"
                 href="#" id="angular-dropdown" data-toggle="dropdown" aria-haspopup="true"
                 aria-expanded="false">Java Persistence</a>
              <div class="dropdown-menu" aria-labelledby="angular-dropdown">
                <a class="dropdown-item" href="${'/category/jpa-hibernate'}">JPA/Hibernate</a>
                <a class="dropdown-item" href="${'/category/mybatis'}">MyBatis</a>
              </div>
            </li>
          </#if>
        </ul>
        <#if currentRequestUriHasPrefix(['/workspace', '/admin', '/user'])>
          <@sec.authorize access="isAuthenticated()">
            <div>
              <a class="nav-link text-danger" href="${'/logout'}">Đăng Xuất</a>
            </div>
          </@sec.authorize>
        <#else>
          <form class="form-inline mt-2">
            <div class="input-group">
              <label>
                <input type="text" class="form-control" placeholder="Tìm kiếm trong blog">
              </label>
              <div class="input-group-append search-nav-btn">
                <button class="btn btn-warning" type="button">
                  <i class="fas fa-search"></i>
                </button>
              </div>
            </div>
          </form>
        </#if>
      </div>
    </nav>
  </header>
</#macro>
<#macro bodyFragment>

</#macro>
<#macro footerFragment>
  <footer class="container-fluid">
    <div class="row">
      <div class="col-12 col-sm-12 col-md-12 col-lg-12 text-dark bg-light text-center">
        <div class="row pt-4">
          <div class="col-12 col-sm-12 col-md-4 col-lg-4">
            <h5 class="font-weight-normal">DANH MỤC</h5>
            <ul class="pl-0 footer-link">
              <li><a href="${'/category/java'}">Java</a></li>
              <li><a href="${'/category/servlet-jsp'}">Servlet/JSP</a></li>
              <li><a href="${'/category/spring-framework'}">Spring Framework</a></li>
              <li><a href="${'/category/jpa-hibernate'}">JPA/Hibernate</a></li>
              <li><a href="${'/category/mybatis'}">MyBatis</a></li>
            </ul>
          </div>
          <div class="col-12 col-sm-12 col-md-4 col-lg-4">
            <h5 class="font-weight-normal">HƯỚNG DẪN</h5>
            <ul class="pl-0 footer-link">
              <li><a href="/">Lập trình Web với Servlet/JSP</a></li>
              <li><a href="/">Lập trình Web với Spring Framework</a></li>
              <li><a href="/">Lập trình cơ bản Java</a></li>
            </ul>
          </div>
          <div class="col-12 col-sm-12 col-md-4 col-lg-4">
            <h5 class="font-weight-normal">THÔNG TIN - LIÊN HỆ</h5>
            <ul class="pl-0 footer-link">
              <li><a href="/">Giới thiệu blog</a></li>
              <li><a href="/">Liên hệ quảng cáo</a></li>
              <li><a href="/">Tuyển người viết bài</a></li>
            </ul>
          </div>
        </div>
        <div class="row">
          <p class="col-12 col-sm-12 col-md-12 col-lg-12 mt-5 mb-3">
            Annanjin &copy; 2019 - ${(.now)?string['yyyy']} <#--or use string.yyyy-->
          </p>
        </div>
      </div>
    </div>
  </footer>
<#--scroll up button-->
  <div>
    <button style="position: fixed;bottom: 40px;right: 40px;" id="scroll-up-btn" class="btn btn-warning">
      <i class="fas fa-arrow-up"></i>
    </button>
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
</#macro>


<#macro leftBannerSide>
  <img alt="banner" class="img-fluid"
       src="https://www.baeldung.com/wp-content/uploads/2018/03/201801_APM_ads_set_b_Java2_300.jpg">
</#macro>

<#-- custom functions -->
<#function currentRequestUriHasPrefix urls>
  <#list urls as url>
    <#if springMacroRequestContext.requestUri?starts_with(url)>
      <#return true>
    </#if>
  </#list>
  <#return false>
</#function>
