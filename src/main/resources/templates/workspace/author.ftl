<#include "../layout/layout.ftl"/>

<#macro customResources>
  <script>
      let csrf_token = '${_csrf.token}';
      let authorTable;
      let selectedItems = new Set();
      let selectedId;
      $(function () {
          authorTable = $("#author-table").DataTable({
              processing: true,
              serverSide: true,
              ajax: {
                  url: "/admin/author/list",
                  type: "POST",
                  dataType: "json",
                  contentType: "application/json",
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: function (datatableRequest) {
                      return JSON.stringify(datatableRequest);
                  }
              },
              deferRender: true,
              columns: [
                  {data: "id"},
                  {data: "id"},
                  {data: "code"},
                  {data: "name"},
                  {data: "avatarUrl"},
                  {data: "description"},
                  {data: "socialLinks.length"},
                  {data: "id"}
              ],
              columnDefs: [
                  {orderable: false, targets: [0, 4, 5, 6, 7]},
                  {
                      render: function () {
                          return "";
                      },
                      className: 'select-checkbox',
                      targets: 0
                  },
                  {
                      render: function (data) {
                          return '<img width="80px" height="auto" src="${'/image/user/'}' + data + '" alt="">';
                      },
                      targets: 4
                  },
                  {
                      className: "description-details",
                      orderable: false,
                      data: "description",
                      defaultContent: "",
                      render: function (data) {
                          let description = data.toString();
                          if (description.length > 30) description = description.substr(0, 30) + '...';
                          return '<p style="cursor: pointer;" data-toggle="tooltip" title="Nhấn để xem chi tiết">' + description + '</p>';
                      },
                      targets: 5
                  },
                  {
                      className: "details-control",
                      orderable: false,
                      data: "socialLinks.length",
                      defaultContent: "",
                      render: function (data) {
                          return '<button class="btn btn-outline-secondary" data-toggle="tooltip" title="Nhấn để xem chi tiết"><i class="fas fa-plus-circle"></i> ' + data + ' liên kết </button>';
                      },
                      targets: 6
                  },
                  {
                      render: function (data, type, row) {
                          return '<button data-toggle="modal" data-target="#update-account-modal" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-success ml-1"><i class="fas fa-user-shield"></i></button>'
                              + '<button data-toggle="modal" data-target="#update-author-modal" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-info ml-1"><i class="fas fa-edit"></i></button>'
                              + '<button data-toggle="modal" data-target="#delete-confirm-dialog" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-danger ml-1"><i class="fas fa-trash"></i></button>';
                      },
                      targets: 7
                  }
              ],
              select: {
                  style: 'multi',
                  selector: 'td:first-child'
              },
              order: [1, 'desc'],
              language: {
                  url: "http://cdn.datatables.net/plug-ins/1.10.19/i18n/Vietnamese.json",
                  searchPlaceholder: ""
              },
              lengthMenu: [10, 25, 50, 100, 200],
              initComplete: function (settings, json) {
              }
          });

          $("#author-table tbody").on("click", "td.details-control", function () {
              let tr = this.parentNode;
              let row = authorTable.row(tr);
              if (row.child.isShown()) {
                  row.child.hide();
                  $(tr).removeClass("shown");
              } else {
                  let btn = '<button data-toggle="modal" data-target="#add-link-modal" class="btn btn-sm btn-primary mb-1" data-id="' + row.data().id + '">Thêm</button>';
                  let content = '<table class="table table-bordered" style="margin:0;padding:0;">';
                  content += '<thead>' +
                      '<th>Tên</th>' +
                      '<th>Liên kết</th>' +
                      '<th>#</th>' +
                      '</thead><tbody>';
                  row.data()['socialLinks'].forEach(function (data) {
                      content += '<tr>' +
                          '<td>' + data.name + '</td>' +
                          '<td><a target="_blank" href="' + data.link + '">' + data.link + '</a></td>' +
                          '<td><button class="btn btn-danger" onclick="deleteSocialLink(' + data.id + ', ' + row.data().id + ')"><i class="fas fa-trash"></i></button></td>' +
                          '</tr>';
                  });
                  content += '</tbody></table>';

                  row.child(btn + content).show();
                  $(tr).addClass("shown");
              }
          }).on("click", "td.description-details", function () {
              let tr = this.parentNode;
              let row = authorTable.row(tr);
              if (row.child.isShown()) {
                  row.child.hide();
                  $(tr).removeClass("shown");
              } else {
                  let content = '<h5>Giới thiệu về tác giả [ ' + row.data().name + ' ]</h5>';
                  content += '<p>' + row.data().description + '</p>';
                  row.child(content).show();
                  $(tr).addClass("shown");
              }
          });

          $("#multiple-delete-btn").on("click", function () {
              if (selectedItems.size < 1) {
                  $("#notification-dialog .modal-body p").html("Chọn ít nhất một mục để xóa!");
                  $("#notification-dialog").modal("show");
              } else {
                  $("#multiple-delete-confirm-dialog").modal("show");
              }
          });

          authorTable.on("select", function (e, dt, type, indexes) {
              let rowData = authorTable.rows(indexes).data();
              selectedItems.add(rowData[0]);
          }).on("deselect", function (e, dt, type, indexes) {
              let rowData = authorTable.rows(indexes).data();
              selectedItems.delete(rowData[0]);
          });

          $("#delete-confirm-dialog").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#delete-confirm-dialog .modal-body p").html("Mã: " + row.code + " / Tên: " + row.name);
              $("#delete-confirm-dialog .modal-footer .btn-danger").attr("onclick", "deleteAuthor(" + row.id + ")");
          });

          $("#update-author-modal").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#update-author-modal #input-name").val(row.name);
              $("#update-author-modal #input-code").val(row.code);
              $("#update-author-modal #input-description").val(row.description);
              $("#update-author-modal #input-id").val(row.id);
          });

          $("#update-account-modal").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $.ajax({
                  url: "/admin/author/get-account/" + row.id,
                  type: "POST",
                  dataType: "json",
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  success: function (data) {
                      $("#update-account-modal #input-id").val(data.id);
                      $("#update-account-modal #input-username").val(data.username);
                      $("#update-account-modal #input-email").val(data.email);
                  }
              })
          });

          $("#add-link-modal").on("show.bs.modal", function (e) {
              selectedId = $(e.relatedTarget).data("id");
          });

          $("#add-account-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/account/add",
                  type: "POST",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#add-account-modal form")[0].reset();
                      $("#input-file-label").removeClass("selected").html("Choose thumbnail image...").css("color", "white");
                      $("#add-account-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm tác giả!");
                      $("#notification-dialog").modal("show");
                      authorTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#update-author-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/author/update",
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#update-author-modal form")[0].reset();
                      $("#input-file-label").removeClass("selected").html("Choose thumbnail image...").css("color", "white");
                      $("#update-author-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã cập nhật tác giả!");
                      $("#notification-dialog").modal("show");
                      authorTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#update-account-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/account/update",
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#update-account-modal form")[0].reset();
                      $("#update-account-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã cập nhật tài khoản!");
                      $("#notification-dialog").modal("show");
                      authorTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#add-link-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/author/add-link/" + selectedId,
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#add-link-modal form")[0].reset();
                      $("#add-link-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm liên kết!");
                      $("#notification-dialog").modal("show");
                      authorTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          })
      });

      function multipleDelete() {
          let deleteIds = [];
          selectedItems.forEach(function (value) {
              deleteIds.push(value.id);
          });
          $.ajax({
              url: "/admin/author/multiple-delete/" + deleteIds.toString(),
              type: "GET",
              dataType: "text",
              headers: {'X-CSRF-TOKEN': csrf_token},
              success: function () {
                  $("#multiple-delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa tác giả!");
                  $("#notification-dialog").modal("show");
                  authorTable.draw();
                  selectedItems.clear();
              },
              error: function () {
                  $("#multiple-delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }

      function deleteAuthor(id) {
          $.ajax({
              url: "/admin/author/delete/" + id,
              type: "DELETE",
              dataType: "text",
              headers: {'X-CSRF-TOKEN': csrf_token},
              success: function () {
                  $("#delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa tác giả!");
                  $("#notification-dialog").modal("show");
                  authorTable.draw();
              },
              error: function () {
                  $("#delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }

      function deleteSocialLink(linkId, authorId) {
          $.ajax({
              url: "/admin/author/delete-link",
              type: "POST",
              dataType: "text",
              headers: {'X-CSRF-TOKEN': csrf_token},
              data: {linkId: linkId, authorId: authorId},
              success: function () {
                  $("#notification-dialog .modal-body p").html("Đã xóa liên kết!");
                  $("#notification-dialog").modal("show");
                  authorTable.draw();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }

      function changeSelectedFilename(e) {
          let fileName = e.value.split("\\").pop();
          $(".custom-file label").addClass("selected").html(fileName).css("color", "green");
      }
  </script>
  <style>
    .form-modal .modal-body {
      overflow-y: auto;
      height: calc(100vh - 200px);
    }

    .custom-form-div {
      margin: 0 15px 0 15px;
    }

    td {
      word-break: break-all;
      vertical-align: middle !important;
    }

    table.dataTable tbody td.select-checkbox,
    table.dataTable thead th.select-checkbox {
      position: relative;
    }

    table.dataTable tbody td.select-checkbox:before,
    table.dataTable tbody td.select-checkbox:after,
    table.dataTable thead th.select-checkbox:before,
    table.dataTable thead th.select-checkbox:after {
      display: block;
      position: absolute;
      top: 50%;
      left: 50%;
      width: 12px;
      height: 12px;
      box-sizing: border-box;
    }

    table.dataTable tbody td.select-checkbox:before,
    table.dataTable thead th.select-checkbox:before {
      content: ' ';
      margin-top: -6px;
      margin-left: -6px;
      border: 1px solid black;
      border-radius: 3px;
    }
  </style>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5 px-5">
    <div class="row">
      <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="row my-4">
          <div class="col-8 col-sm-8 col-md-8 col-lg-6 col-xl-6">
            <h3>Danh Sách Tác Giả</h3>
          </div>
          <div class="col-4 col-sm-4 col-md-4 col-lg-6 col-xl-6">
            <button type="button" data-target="#add-account-modal" data-toggle="modal"
                    class="btn btn-primary pull-right">Thêm Mới
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 table-responsive">
            <table id="author-table" class="table table-borderless table-hover">
              <thead>
              <tr>
                <th style="width: 5%;"></th>
                <th style="width: 5%;">Id</th>
                <th style="width: 10%;">Mã</th>
                <th>Họ Tên</th>
                <th>Avatar</th>
                <th>Giới thiệu</th>
                <th style="width: 15%;">Liên Kết</th>
                <th style="width: 15%;">#</th>
              </tr>
              </thead>
              <tfoot>
              <tr>
                <th>
                  <button class="btn btn-sm btn-danger" id="multiple-delete-btn" data-toggle="tooltip"
                          title="Xóa mục đã chọn">
                    <i class="fas fa-trash"></i>
                  </button>
                </th>
                <th style="width: 5%;">Id</th>
                <th>Mã</th>
                <th>Họ Tên</th>
                <th>Avatar</th>
                <th>Giới thiệu</th>
                <th>Liên Kết</th>
                <th>#</th>
              </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </div>
    </div>
    <#--add account dialog-->
    <div class="modal form-modal fade" role="dialog" id="add-account-modal">
      <div class="modal-dialog modal-md">
        <div class="modal-content">
          <form method="post" action="${'/admin/account/add'}" enctype="multipart/form-data">
            <div class="modal-header">
              <h3 class="modal-title">Thêm thành viên</h3>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <h5>Tài khoản</h5>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-username">Tên đăng nhập:</label>
                <div class="col-md-12">
                  <input class="form-control" name="username" id="input-username" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-password">Mật mã:</label>
                <div class="col-md-12">
                  <input class="form-control" name="password" id="input-password" type="password">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-email">Email:</label>
                <div class="col-md-12">
                  <input class="form-control" name="email" id="input-email" type="email">
                </div>
              </div>
              <hr/>
              <h5>Thông tin tác giả</h5>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-name">Tên tác giả:</label>
                <div class="col-md-12">
                  <input class="form-control" name="author.name" id="input-name" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-code">Mã tác giả:</label>
                <div class="col-md-12">
                  <input class="form-control" name="author.code" id="input-code" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-file">Avatar:</label>
                <div class="custom-form-div">
                  <div class="custom-file col-md-12">
                    <input type="file" name="author.avatarFile" id="input-file"
                           class="custom-file-input"
                           onchange="changeSelectedFilename(this)">
                    <label class="custom-file-label" for="input-file" id="input-file-label">
                      Choose thumbnail image...
                    </label>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-description">Giới thiệu:</label>
                <div class="col-md-12">
                                <textarea class="form-control" name="author.description"
                                          id="input-description"></textarea>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-primary">Thêm</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <!--nofitication dialog-->
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
            <button class="btn btn-warning" data-dismiss="modal">Tắt</button>
          </div>
        </div>
      </div>
    </div>
    <!--multiple delete dialog-->
    <div class="modal" role="alertdialog" id="multiple-delete-confirm-dialog">
      <div class="modal-dialog modal-md modal-dialog-centered">
        <div class="modal-content text-center">
          <div class="model-header">
            <h3 class="modal-title">Xác Nhận</h3>
          </div>
          <div class="modal-body">
            <p>Bạn có muốn xóa các mục đã chọn?</p>
          </div>
          <div class="modal-footer">
            <button class="btn btn-warning" data-dismiss="modal">Hủy</button>
            <button class="btn btn-danger" data-dismiss="modal" onclick="multipleDelete()">Xóa</button>
          </div>
        </div>
      </div>
    </div>
    <!--delete confirm dialog-->
    <div class="modal" role="alertdialog" id="delete-confirm-dialog">
      <div class="modal-dialog modal-sm modal-dialog-centered">
        <div class="modal-content text-center">
          <div class="model-header">
            <h3 class="modal-title">Xác nhận</h3>
          </div>
          <div class="modal-body">
            Bạn có muốn xóa mục này?
            <p class="text-danger"></p>
          </div>
          <div class="modal-footer">
            <button class="btn btn-warning" data-dismiss="modal">Hủy</button>
            <button class="btn btn-danger">Xóa</button>
          </div>
        </div>
      </div>
    </div>
    <#--update author dialog-->
    <div class="modal form-modal fade" role="dialog" id="update-author-modal">
      <div class="modal-dialog modal-md">
        <div class="modal-content">
          <form method="post" action="${'/admin/author/update'}" enctype="multipart/form-data">
            <div class="modal-header">
              <h3 class="modal-title">Sửa thông tin tác giả</h3>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <h5>Thông tin tác giả</h5>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-id">ID :</label>
                <div class="col-md-12">
                  <input class="form-control" name="id" id="input-id" readonly="readonly" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-name">Tên tác giả:</label>
                <div class="col-md-12">
                  <input class="form-control" name="name" id="input-name" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-code">Mã tác giả:</label>
                <div class="col-md-12">
                  <input class="form-control" name="code" id="input-code" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-file">Avatar:</label>
                <div class="custom-form-div">
                  <div class="custom-file col-md-12">
                    <input type="file" name="avatarFile" id="input-file" class="custom-file-input"
                           onchange="changeSelectedFilename(this)">
                    <label class="custom-file-label" for="input-file" id="input-file-label">
                      Choose thumbnail image...
                    </label>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-description">Giới thiệu:</label>
                <div class="col-md-12">
                  <textarea class="form-control" name="description" id="input-description"></textarea>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-info">Sửa</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <#--update account dialog-->
    <div class="modal form-modal fade" role="dialog" id="update-account-modal">
      <div class="modal-dialog modal-md">
        <div class="modal-content">
          <form method="post" action="${'/admin/account/update'}" enctype="multipart/form-data">
            <div class="modal-header">
              <h3 class="modal-title">Cập nhật thông tin bảo mật</h3>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <h5>Thông tin tài khoản</h5>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-id">ID :</label>
                <div class="col-md-12">
                  <input class="form-control" name="id" id="input-id" readonly="readonly" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-username">Tên tài khoản:</label>
                <div class="col-md-12">
                  <input class="form-control" name="username" readonly="readonly" id="input-username"
                         type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-password">Mật khẩu:</label>
                <div class="col-md-12">
                  <input class="form-control" name="password"
                         placeholder="Bỏ trống nếu không muốn đổi mật khẩu"
                         id="input-password" type="password">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-email">Email:</label>
                <div class="col-md-12">
                  <input class="form-control" name="email" id="input-email" type="email">
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-info">Cập nhật</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <#--add link dialog-->
    <div class="modal form-modal fade" role="dialog" id="add-link-modal">
      <div class="modal-dialog modal-md">
        <div class="modal-content">
          <form method="post" action="${'/admin/author/add-link'}" enctype="multipart/form-data">
            <div class="modal-header">
              <h3 class="modal-title">Thêm liên kết tác giả</h3>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label class="control-label col-md-4" for="input-social-name">Tên:</label>
                <div class="col-md-12">
                  <input class="form-control" name="name" id="input-social-name" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-link">Link:</label>
                <div class="col-md-12">
                  <input class="form-control" name="link" id="input-link" type="text">
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-primary">Thêm mới</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</#macro>

<@displayPage page_title="Quản Lý Tác Giả"/>
