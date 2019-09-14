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
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row">
      <div class="col-12">
        <div class="row">
          <div class="col-lg-9 col-12 pl-xl-5 pl-lg-5 pl-md-5 pl-sm-5 pl-4 mb-5 author-div">
            <div class="row mb-4" style="display: flex; align-items: center;">
              <div class="col-md-3 col-sm-4 col-12 p-md-3 p-sm-3 p-5 author-avatar">
                <img class="rounded-circle" src="<@s.url '/image/user/' + author.avatarUrl/>" width="100%"
                     height="100%"
                     alt="avatar"/>
              </div>
              <div class="col-md-6 col-sm-8 col-12">
                <h3 class="text-xl-left text-lg-left text-center"><span>${author.name}</span></h3>
                <hr/>
                <div>${author.description}</div>
              </div>
              <div class="col-md-3 col-sm-12 col-12 mt-3 text-left text-xl-left text-lg-left text-md-left text-sm-right">
                <h5>Theo dõi tác giả tại: </h5>
                <#list author.socialLinks as link>
                  >> <a href="${link.link}">${link.name}</a>
                  <br/>
                </#list>
              </div>
            </div>
            <div class="row mt-5 post-author-div">
              <h3 class="mb-5">Xem thêm bài viết của <span class="text-dark">${author.name}</span></h3>
              <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="row post-list-div">
                  <#if postsResp??>
                    <#list postsResp.data as post>
                      <div class="post-div col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12 px-4 mb-5">
                        <div class="row">
                          <a href="${'/post/' + post.code}" class="post-title post-link">
                            <h5 class="text-success">${post.title}</h5>
                          </a>
                        </div>
                        <div class="row">
                          <#if post.tags?? && (post.tags?size > 0)>
                            <p class="text-secondary py-0 my-0">Danh mục:
                              <#list post.categories as cate>
                                <a href="${'/category/' + cate.code}"
                                   class="text-secondary">${cate.name}</a>${cate?has_next?then(',', '')}
                              </#list>
                            </p>
                          </#if>
                        </div>
                        <div class="row">
                          <p class="post-desc">${post.description}</p>
                        </div>
                        <div class="row">
                          <a href="${'/post/' + post.code}" class="text-secondary post-link"><h6><u>Xem thêm --></u>
                            </h6></a>
                        </div>
                      </div>
                    </#list>
                  </#if>
                </div>
                <div class="row d-flex justify-content-center align-items-center mt-4">
                  <#assign pageLink = springMacroRequestContext.requestUri + '?page='/>
                  <#assign curPage = postsResp.draw + 1/>
                  <#assign totalPage = postsResp.totalDraw + 1/>
                  <div class="draw-select col-xl-7 col-lg-6 col-md-8 col-sm-10 col-12 text-center px-0">
                    <a href="${(curPage > 1)?then(pageLink + '1', '#')}"
                       class="mb-2 btn btn-sm btn-light ${(curPage <= 1)?then('disabled', '')}">Đầu</a>
                    <a href="${(curPage > 1)?then(pageLink + (curPage - 1), '#')}"
                       class="mb-2 btn btn-sm btn-light ${(curPage <= 1)?then('disabled', '')}"> &lt;</a>

                    <#assign start = (curPage <= 4)?then(1, ((curPage + 4 > totalPage)?then((totalPage - 6 < 1)?then(1, totalPage - 6), curPage - 3)))>
                    <#assign end = (curPage + 4 <= totalPage)?then(curPage + 3, totalPage)>
                    <#if (start > 1)>
                      <a href="#"
                         class="mb-2 btn btn-sm btn-light disabled">...</a>
                    </#if>
                    <#list start..end as x>
                      <a href="${pageLink + x}"
                         class="mb-2 btn btn-sm ${(postsResp.draw + 1 == x)?then('disabled btn-primary', 'btn-light')}">${x}</a>
                    </#list>
                    <#if (end < totalPage)>
                      <a href="#"
                         class="mb-2 btn btn-sm btn-light disabled">...</a>
                    </#if>

                    <a href="${(curPage < totalPage)?then(pageLink + (curPage + 1), '#')}"
                       class="mb-2 btn btn-sm btn-light ${(curPage >= totalPage)?then('disabled', '')}">&gt;</a>
                    <a href="${(curPage < totalPage)?then(pageLink + totalPage, '#')}"
                       class="mb-2 btn btn-sm btn-light ${(curPage >= totalPage)?then('disabled', '')}">Cuối</a>
                  </div>
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
</#macro>

<@displayPage page_title=author.name/>
