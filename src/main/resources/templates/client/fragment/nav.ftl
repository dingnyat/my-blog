<header class="container-fluid mb-5">
  <nav class="navbar navbar-expand-md navbar-light fixed-top bg-light">
    <a class="navbar-brand text-success font-weight-bold" href="${'/'}">Annanjin</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu-bar"
            aria-controls="menu-bar" aria-expanded="false" aria-label="Menu Toggler">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="menu-bar">
        <#if !link??>
            <#assign link = "">
        </#if>
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link <#if link == "java">active</#if>" href="${'/java'}">Java</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <#if link == "servlet">active</#if>" href="${'/servlet'}">Servlet/JSP</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <#if link == "spring">active</#if>" href="${'/spring'}">Spring Framework</a>
        </li>
        <li class="nav-item">
          <a class="nav-link <#if link == "jpa-hibernate">active</#if>" href="${'/jpa-hibernate'}">JPA/Hibernate</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link <#if link == "angular-js" || link == "angular-cli">active</#if> dropdown-toggle"
             href="#" id="angular-dropdown" data-toggle="dropdown" aria-haspopup="true"
             aria-expanded="false">Angular</a>
          <div class="dropdown-menu" aria-labelledby="angular-dropdown">
            <a class="dropdown-item" href="${'/angular-js'}">Angular JS</a>
            <a class="dropdown-item" href="${'/angular-cli'}">Angular CLI</a>
          </div>
        </li>
      </ul>
      <form class="form-inline mt-2 mt-md-0">
        <div class="input-group">
          <label>
            <input type="text" class="form-control" placeholder="Tìm kiếm trong blog">
          </label>
          <div class="input-group-append">
            <button class="btn btn-warning" type="button">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      </form>
    </div>
  </nav>
</header>