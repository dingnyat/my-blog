<#include "./layout/layout.ftl"/>

<#--parts of dispay page-->
<#macro commonHeaderTags title>
  <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title><#if title?? && title?has_content>${title}</#if></title>
  <link rel="icon" href="<@s.url '/favicon.ico'/>"/>
</#macro>

<#macro customResources>
  <style>
    .post-div {
      border-left: 2px solid gray;
    }

    .post-details {
      font-size: 0.85em;
    }

    .post-div .post-details a {
      margin-left: 2px;
      margin-right: 2px;
    }
  </style>
</#macro>

<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row">
      <div class="col-12">
        <div class="row">
          <div class="col-lg-1 col-md-12 col-sm-12 col-12 text-center">
          </div>
          <div class="col-lg-8 col-12 pl-xl-5 pl-lg-5 pl-md-5 pl-sm-5 pl-4 mb-5">
            <div class="row d-none">Thông báo (chưa code)</div>
            <div class="row post-list-div">
              <div class="col-12 mb-5 px-0">
                <h2>Bài Viết Mới Nhất</h2>
              </div>
              <#if postsResp??>
                <#list postsResp.data as post>
                  <div class="col-12 mb-3 pl-5 post-div">
                    <div class="row">
                      <h5><a class="text-success" href="${'/post/' + post.code}">${post.title}</a></h5>
                    </div>
                    <div class="row post-details">
                      Đăng bởi <a class="text-secondary" href="${'/author/' + post.authorCode}"> ${post.authorName} </a>
                      | ${post.createdDate}
                    </div>
                    <div class="row post-details">
                      <#if post.categories??>
                        <div class="col-12 px-0">
                          Danh mục:
                          <#list post.categories as cate>
                            <a class="text-secondary"
                               href="${'/category/' + cate.code}">${cate.name + (cate?has_next?then(', ', ''))}</a>
                          </#list>
                        </div>
                      </#if>
                      <#if post.seriesCode??>
                        <div class="col-12 px-0">
                          | Series:
                          <a class="text-secondary" href="${'/series/' + post.seriesCode}">${post.seriesName}</a>
                        </div>
                      </#if>
                    </div>
                  </div>
                </#list>
              </#if>
            </div>
            <div class="row d-flex justify-content-start align-items-center mt-4">
              <#assign pageLink = '/page/'/>
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
          </div>
          <div class="col-lg-3 col-md-12 col-sm-12 col-12 text-center">
            <@leftBannerSide/>
          </div>
        </div>
      </div>
    </div>
  </section>
</#macro>

<@displayPage page_title='Annanjin - Nơi mình viết mấy bài hướng dẫn Java lúc rảnh <3'/>
