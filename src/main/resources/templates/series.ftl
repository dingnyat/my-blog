<#include "./layout/layout.ftl"/>

<#macro customResources>
  <style>

  </style>
</#macro>

<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row">
      <div class="col-12">
        <div class="row">
          <div class="col-lg-1 col-12 text-center">
          </div>
          <div class="col-lg-8 col-12 pl-sm-5 pl-4 mb-5">
            <div class="row post-list-div">
              <div class="col-12 mb-3 px-0">
                <h2>Series: ${series.name}</h2>
                <br/>
                <p>${series.description}</p>
              </div>
              <div class="col-12 mb-5 px-0">
                <h5 class="mb-5">Bài Viết Trong Series</h5>
                <#if postsResp??>
                  <#list postsResp.data as post>
                    <div class="col-12 mb-3 pl-5 post-div">
                      <div class="row">
                        <h5>
                          <i class="fas fa-long-arrow-alt-right mr-2"></i>
                          <a class="text-success" href="${'/post/' + post.code}">${post.title}</a>
                        </h5>
                      </div>
                    </div>
                  </#list>
                </#if>
              </div>
            </div>
            <#if (postsResp.totalDraw + 1 > 1)>
              <div class="row d-flex justify-content-start align-items-center mt-4">
                <#assign pageLink = '/series/' + series.code +'/page/'/>
                <#assign curPage = postsResp.draw + 1/>
                <#assign totalPage = postsResp.totalDraw + 1/>
                <div class="draw-select col-xl-7 col-lg-6 col-md-8 col-sm-10 col-12 text-center px-0">
                  <a href="${(curPage > 1)?then('/', '#')}"
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
                       class="mb-2 btn btn-sm ${(postsResp.draw + 1 == x)?then('disabled btn-warning', 'btn-light')}">${x}</a>
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
            </#if>
          </div>
          <div class="col-lg-3 col-12 text-center">
            <@leftBannerSide/>
          </div>
        </div>
      </div>
    </div>
  </section>
</#macro>

<@displayPage page_title=series.name/>
