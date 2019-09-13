<#include "./layout/layout.ftl"/>

<#macro customResources>
  <style>
    .post-title:hover {
      text-decoration: underline #28a745;
    }

    @media (max-width: 800px) {
      .author-avatar {
        padding: 20%;
      }
    }
  </style>

  <script>
      let csrf_token = '${_csrf.token}';
      $.ajax({
          url: "/post/search",
          type: "POST",
          dataType: "json",
          contentType: "application/json",
          headers: {'X-CSRF-TOKEN': csrf_token},
          data: JSON.stringify({
              draw: 0,
              start: 0,
              length: 12,
              searchCriteria: [{"key": "authorCode", "operator": "EQUALITY", "value": '${author.code}'}]
          }),
          success: function (resp) {
              let samplePost = $("#sample-post");
              resp.data.forEach(function (value) {
                  let postDiv = samplePost.clone().removeClass("d-none").removeAttr("id");
                  $(postDiv).find(".post-title h5").html(value.title);
                  if (value.description != null) $(postDiv).find(".post-desc").html(value.description);
                  $(postDiv).find(".post-link").attr("href", "/post/" + value.code);
                  $(".post-list-div").append(postDiv);
              });
          },
          error: function (resp) {

          }
      })
  </script>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row">
      <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
        <div class="row">
          <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12 pl-5 author-div">
            <div class="row mb-4" style="display: flex; align-items: center;">
              <div class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-12 p-5 author-avatar">
                <img class="rounded-circle" src="<@s.url '/image/user/' + author.avatarUrl/>" width="100%"
                     height="100%"
                     alt="avatar"/>
              </div>
              <div class="col-xl-6 col-lg-6 col-md-6 col-sm-8 col-12">
                <h3 class="text-xl-left text-lg-left text-center"><span>${author.name}</span></h3>
                <hr/>
                <div>${author.description}</div>
              </div>
              <div class="col-xl-3 col-lg-3 col-md-3 col-sm-12 col-12 mt-3 text-left text-xl-left text-lg-left text-md-left text-sm-right">
                <h5>Theo dõi tác giả tại: </h5>
                <#list author.socialLinks as link>
                  >> <a href="${link.link}">${link.name}</a>
                  <br/>
                </#list>
              </div>
            </div>
            <div class="row mt-5 post-author-div">
              <h3 class="mb-5">Xem thêm bài viết của <span class="text-dark">${author.name}</span> </h3>
              <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="row post-list-div">
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12 text-center">
            <@leftBannerSide/>
          </div>
        </div>
      </div>
    </div>
  </section>

  <div id="sample-post" class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12 post-div px-4 mb-4 d-none">
    <div class="row">
      <a href="#" class="post-title post-link">
        <h5 class="text-success"></h5>
      </a>
    </div>
    <div class="row">
      <p class="post-desc"></p>
    </div>
    <div class="row">
      <a href="#" class="text-secondary post-link"><h6><u>Xem thêm --></u></h6></a>
    </div>
  </div>
</#macro>

<@displayPage page_title=author.name/>
