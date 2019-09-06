<#include "../layout/layout.ftl"/>

<#macro customResources>
  <script type="text/javascript" src="<@s.url '/vendor/prism/prism.js'/>"></script>
  <link rel="stylesheet" href="<@s.url '/vendor/prism/prism.css'/>">
  <script>
      let csrf_token = '${_csrf.token}';
      $(function () {
          $("#add-comment").on("show.bs.modal", function (e) {
              let parentCommentId = $(e.relatedTarget).data("comment-id");
              let postId = $(e.relatedTarget).data("post-id");
              $("#add-comment form input[name='postId']").val(postId);
              $("#add-comment form input[name='parentCommentId']").val(parentCommentId);
          });

          $("#add-comment form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: '/post/add-comment',
                  type: 'POST',
                  dataType: 'text',
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      window.location.reload();
                      $("#add-comment form")[0].reset();
                      $("#add-comment").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm bình luận!");
                      $("#notification-dialog").modal("show");
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra, vui lòng thử lại!");
                      $("#notification-dialog").modal("show");
                  }
              });
          })
      });
  </script>
  <style>
    .form-modal .modal-body {
      overflow-y: auto;
      height: auto;
    }

    .chip {
      display: inline-block;
      height: 25px;
      font-size: 12px;
      font-weight: 400;
      color: rgba(0, 0, 0, .6);
      line-height: 25px;
      padding: 0 7px;
      border-radius: 16px;
      cursor: pointer;
      transition: all .3s linear;
      background-color: #c1bebe;
    }

    .chip:hover {
      background-color: #979494;
    }

    .sign.fas {
      color: rgba(0, 0, 0, 0.6);
    }

    .post-div {
      padding-left: 5vw;
      padding-right: 5vw;
    }

    .comment-div {
      border-left: 1px solid #ffc107;
      padding-top: 10px;
      padding-right: 10px;
    }

    @media (max-width: 800px) {
      .post-title {
        font-size: 80%;
      }
    }
  </style>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5">
    <div class="row">
      <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
        <div class="row">
          <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12 post-div">
            <h3 class="display-4"><strong class="post-title">${post.title}</strong></h3>
            <hr/>
            <div class="row">
              <div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 col-12 pl-5">
                Chỉnh sửa lần cuối: ${post.lastModifiedDate} <br/>
                Tác giả: <a href="${'/author/' + post.authorCode}">${post.authorName}</a>
              </div>
              <div class="col-xl-8 col-lg-8 col-md-8 col-sm-12 col-12 mt-2 pr-5 text-right">
                <i class="sign fas fa-tags"></i>
                <#list post.tags as tag>
                  <span class="chip my-1 ml-1">
                    <a style="text-decoration: none; color: #ffffff" href="${'/tag/' + tag.code}">${tag.name}</a>
                  </span>
                </#list>
              </div>
            </div>
            <hr/>
            <div class="row mb-4">
              <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mt-5 post-content">
                ${post.content}
              </div>
            </div>
            <hr/>
            <div class="row mb-3">
              <div class="col-xl-3 col-lg-3 col-md-3 col-sm-6 col-6">
                <h4>Comments</h4>
              </div>
              <div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-6" style="display : flex; align-items: center;">
                <a data-toggle="modal" data-post-id="${post.id}"
                   data-target="#add-comment" href="javascript:void(0)">Để lại bình luận</a>
              </div>
            </div>
            <div class="row px-3">
              <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 post-comment">
                <div class="row mb-3">
                  <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 comment-list-div">
                    <#list post.comments as comment>
                      <@showComment comment/>
                    </#list>
                  </div>
                </div>
              </div>
            </div>

            <div class="modal form-modal fade" role="dialog" id="add-comment">
              <div class="modal-dialog modal-dialog-centered modal-md">
                <div class="modal-content">
                  <form method="post" action="${'/post/add-comment'}">
                    <div class="modal-header">
                      <h3 class="modal-title">Bình luận</h3>
                      <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                      <input name="postId" type="text" readonly hidden>
                      <input name="parentCommentId" type="text" readonly hidden>
                      <div class="form-group">
                        <label class="control-label col-md-4" for="input-name">Tên (*)</label>
                        <div class="col-md-12">
                          <input class="form-control" placeholder="Tên (bắt buộc)" name="commentBy" id="input-name"
                                 type="text" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-4" for="input-description">Nội Dung (*)</label>
                        <div class="col-md-12">
                        <textarea class="form-control" placeholder="Nội dung (bắt buộc)" name="content"
                                  id="input-description" rows="12" required></textarea>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
                      <button type="submit" class="btn btn-primary">Gửi</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>

            <div class="modal" role="dialog" id="notification-dialog">
              <div class="modal-dialog modal-sm modal-dialog-centered">
                <div class="modal-content text-center">
                  <div class="model-header">
                    <h3 class="modal-title">Thông báo</h3>
                  </div>
                  <div class="modal-body">
                    <p></p>
                  </div>
                  <div class="modal-footer mx-auto d-block">
                    <button class="btn btn-warning" data-dismiss="modal">OK</button>
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


<#macro showComment comment>
  <div class="row bg-light mb-0 comment-div">
    <div class="col-md-12">
      <div class="row mb-2">
        <div class="col-md-12">
                              <span>${comment.commentBy} | ${comment.createdDate} |
                                <span>
                                  <a data-toggle="modal" data-comment-id="${comment.id}"
                                     data-target="#add-comment" href="javascript:void(0)">Trả lời</a>
                                </span>
                              </span>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <span>${comment.content}</span>
        </div>
      </div>
      <hr/>
      <#list comment.childComments as cmt>
        <div class="row pl-4">
          <div class="col-md-12 child-comments">
            <@showComment cmt/>
          </div>
        </div>
      </#list>
    </div>
  </div>
</#macro>

<@displayPage page_title=post.title/>
