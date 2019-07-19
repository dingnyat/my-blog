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
      <li class="nav-item dropdown">
        <a class="nav-link <#if link == "author" || link == "category" || link == "post">active</#if> dropdown-toggle"
           href="#" id="manage-dropdown-btn" data-toggle="dropdown" aria-haspopup="true"
           aria-expanded="false">Quản Lý</a>
        <div class="dropdown-menu" aria-labelledby="manage-dropdown-btn">
          <a class="dropdown-item" href="${'/admin/author'}">Tác Giả</a>
          <a class="dropdown-item" href="${'/admin/category'}">Danh Mục/Thẻ</a>
          <a class="dropdown-item" href="${'/admin/series'}">Series</a>
          <a class="dropdown-item" href="${'/admin/post'}">Bài Viết</a>
        </div>
      </li>
    </ul>
    <div>
      <a class="nav-link text-danger" href="${'/logout'}">Đăng Xuất</a>
    </div>
  </div>
</nav>