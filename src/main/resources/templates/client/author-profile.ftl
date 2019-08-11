<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = author.name>
    <#include "fragment/layout-header.ftl">
  <style>
    @media (max-width: 800px) {
      .author-avatar {
        padding: 20%;
      }
    }
  </style>
</head>
<body>
<#include "./fragment/nav.ftl">
<section class="container-fluid pt-5 pb-5">
  <div class="row">
    <div class="col-md-12">
      <div class="row">
        <div class="col-md-9 author-div">
          <div class="row mb-4" style="display: flex; align-items: center;">
            <div class="col-md-3 author-avatar">
              <img class="rounded-circle" src="<@s.url '/public/images/' + author.avatarUrl/>" width="100%" height="100%"
                   alt="avatar"/>
            </div>
            <div class="col-md-6">
              <h3>${author.name}</h3>
              <hr/>
              <div>${author.description}</div>
            </div>
            <div class="col-md-3">
              <h5>Theo dõi tác giả tại: </h5>
                <#list author.socialLinks as link>
                  >> <a href="${link.link}">${link.name}</a>
                  <br/>
                </#list>
            </div>
          </div>
          <div class="row post-author-div">
            <div class="col-md-12">
              <h3>Xem thêm bài viết của ${author.name}</h3>
              <div class="col-md-4 post-div">
                <#--TODO chưa làm-->
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3 text-center">
            <#include "./fragment/left-banner-side.ftl">
        </div>
      </div>
    </div>
  </div>
</section>
<#include "./fragment/footer.ftl">
</body>
</html>
