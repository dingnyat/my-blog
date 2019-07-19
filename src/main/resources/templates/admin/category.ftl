<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = "Quản Lý Danh Mục">
    <#include "./fragment/layout-header.ftl">
  <script>
      let categoryTable;
      let selectedId;
      let selectedItems = new Set();
      $(function () {
          categoryTable = $("#category-table").DataTable({
              processing: true,
              serverSide: true,
              ajax: {
                  url: "/admin/category/list",
                  type: "POST",
                  dataType: "json",
                  contentType: "application/json",
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
                  {data: "description"},
                  {data: "tags.length"},
                  {data: "series.length"},
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
                      className: "description-details",
                      orderable: false,
                      data: "description",
                      defaultContent: "",
                      render: function (data) {
                          let description = data.toString();
                          if (description.length > 30) description = description.substr(0, 30) + '...';
                          return '<p style="cursor: pointer;" data-toggle="tooltip" title="Nhấn để xem chi tiết">' + description + '</p>';
                      },
                      targets: 4
                  },
                  {
                      className: "tag-details",
                      orderable: false,
                      data: "tags.length",
                      defaultContent: "",
                      render: function (data) {
                          return '<button class="btn btn-outline-secondary" data-toggle="tooltip" title="Nhấn để xem chi tiết"><i class="fas fa-plus-circle"></i> ' + data + ' liên kết </button>';
                      },
                      targets: 5
                  },
                  {
                      className: "series-details",
                      orderable: false,
                      data: "series.length",
                      defaultContent: "",
                      render: function (data) {
                          return '<button class="btn btn-outline-secondary" data-toggle="tooltip" title="Nhấn để xem chi tiết"><i class="fas fa-plus-circle"></i> ' + data + ' liên kết </button>';
                      },
                      targets: 6
                  },
                  {
                      render: function (data, type, row) {
                          return '<button data-toggle="modal" data-target="#update-category-modal" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-info ml-1"><i class="fas fa-edit"></i></button>'
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

          $("#multiple-delete-btn").on("click", function (e) {
              if (selectedItems.size < 1) {
                  $("#notification-dialog .modal-body p").html("Chọn ít nhất một mục để xóa!");
                  $("#notification-dialog").modal("show");
              } else {
                  $("#multiple-delete-confirm-dialog").modal("show");
              }
          });

          categoryTable.on("select", function (e, dt, type, indexes) {
              let rowData = categoryTable.rows(indexes).data();
              selectedItems.add(rowData[0]);
          }).on("deselect", function (e, dt, type, indexes) {
              let rowData = categoryTable.rows(indexes).data();
              selectedItems.delete(rowData[0]);
          });

          $("#category-table tbody")
              .on("click", "td.description-details", function () {
                  let tr = this.parentNode;
                  let row = categoryTable.row(tr);
                  if (row.child.isShown()) {
                      row.child.hide();
                      $(tr).removeClass("shown");
                  } else {
                      let content = '<h5>Mô Tả Danh Mục</h5>';
                      content += '<p>' + row.data().description + '</p>';
                      row.child(content).show();
                      $(tr).addClass("shown");
                  }
              })
              .on("click", "td.tag-details", function () {
                  let tr = this.parentNode;
                  let row = categoryTable.row(tr);
                  if (row.child.isShown()) {
                      row.child.hide();
                      $(tr).removeClass("shown");
                  } else {
                      let btn = '<button data-toggle="modal" data-target="#link-tag-category-modal" class="btn btn-sm btn-primary mb-1" data-id="' + row.data().id + '">Gắn Thêm Thẻ</button>';
                      let content = '<table class="table table-bordered" style="margin:0;padding:0;">';
                      content += '<thead>' +
                          '<th>Tên Thẻ</th>' +
                          '<th>Mã Thẻ</th>' +
                          '<th>#</th>' +
                          '</thead><tbody>';
                      row.data()['tags'].forEach(function (data) {
                          content += '<tr>' +
                              '<td>' + data.name + '</td>' +
                              '<td>' + data.code + '</a></td>' +
                              '<td><button class="btn btn-danger" onclick="deleteTag(' + data.id + ')"><i class="fas fa-trash"></i></button></td>' +
                              '</tr>';
                      });
                      content += '</tbody></table>';

                      row.child(btn + content).show();
                      $(tr).addClass("shown");
                  }
              })
              .on("click", "td.series-details", function () {
                  let tr = this.parentNode;
                  let row = categoryTable.row(tr);
                  if (row.child.isShown()) {
                      row.child.hide();
                      $(tr).removeClass("shown");
                  } else {
                      let btn = '<button data-toggle="modal" data-target="#link-series-category-modal" class="btn btn-sm btn-primary mb-1" data-id="' + row.data().id + '">Gắn thêm series</button>';
                      let content = '<table class="table table-bordered" style="margin:0;padding:0;">';
                      content += '<thead>' +
                          '<th>Tên Series</th>' +
                          '<th>Mã Series</th>' +
                          '<th>#</th>' +
                          '</thead><tbody>';
                      row.data()['series'].forEach(function (data) {
                          content += '<tr>' +
                              '<td>' + data.name + '</td>' +
                              '<td>' + data.code + '</a></td>' +
                              '<td><button class="btn btn-danger" onclick="deselectSeries(' + data.id + ', ' + row.data().id + ')"><i class="fas fa-times"></i></button></td>' +
                              '</tr>';
                      });
                      content += '</tbody></table>';

                      row.child(btn + content).show();
                      $(tr).addClass("shown");
                  }
              });

          $("#link-tag-category-modal").on("show.bs.modal", function (e) {
              selectedId = $(e.relatedTarget).data("id");
          });

          $("#link-series-category-modal").on("show.bs.modal", function (e) {
              selectedId = $(e.relatedTarget).data("id");
          });

          $("#delete-confirm-dialog").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#delete-confirm-dialog .modal-body p").html("Mã: " + row.code + " / Tên: " + row.name);
              $("#delete-confirm-dialog .modal-footer .btn-danger").attr("onclick", "deleteCategory(" + row.id + ")");
          });

          $("#update-category-modal").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#update-category-modal #input-name").val(row.name);
              $("#update-category-modal #input-code").val(row.code);
              $("#update-category-modal #input-description").val(row.description);
              $("#update-category-modal #input-id").val(row.id);
          });


          $("#add-category-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/category/add",
                  type: "POST",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  data: formData,
                  success: function (data) {
                      $("#add-category-modal form")[0].reset();
                      $("#add-category-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm danh mục!");
                      $("#notification-dialog").modal("show");
                      categoryTable.draw();
                  },
                  error: function (data) {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#link-tag-category-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/category/add-tag/" + selectedId,
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  data: formData,
                  success: function (data) {
                      $("#link-tag-category-modal form")[0].reset();
                      $("#link-tag-category-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm thẻ!");
                      $("#notification-dialog").modal("show");
                      categoryTable.draw();
                  },
                  error: function (data) {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#link-series-category-modal form").submit(function (e) {
              e.preventDefault();
              let seriesId = $("#link-series-category-modal form #select-series").val();
              $.ajax({
                  url: "/admin/category/link-series",
                  type: "PUT",
                  dataType: "text",
                  data: {seriesId: seriesId, categoryId: selectedId},
                  success: function () {
                      $("#link-series-category-modal form")[0].reset();
                      $("#link-series-category-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã gắn series vào danh mục!");
                      $("#notification-dialog").modal("show");
                      categoryTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#update-category-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/category/update",
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  data: formData,
                  success: function (data) {
                      $("#update-category-modal form")[0].reset();
                      $("#update-category-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã cập nhật danh mục!");
                      $("#notification-dialog").modal("show");
                      categoryTable.draw();
                  },
                  error: function (data) {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });
      });

      function deleteTag(tagId) {
          $.ajax({
              url: "/admin/category/delete-tag/" + tagId,
              type: "DELETE",
              dataType: "text",
              success: function () {
                  $("#notification-dialog .modal-body p").html("Đã xóa thẻ!");
                  $("#notification-dialog").modal("show");
                  categoryTable.draw();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }

      function deleteCategory(id) {
          $.ajax({
              url: "/admin/category/delete/" + id,
              type: "DELETE",
              dataType: "text",
              success: function () {
                  $("#delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa danh mục!");
                  $("#notification-dialog").modal("show");
                  categoryTable.draw();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }

      function multipleDelete() {
          let deleteIds = [];
          selectedItems.forEach(function (value) {
              deleteIds.push(value.id);
          });
          $.ajax({
              url: "/admin/category/multiple-delete/" + deleteIds.toString(),
              type: "GET",
              dataType: "text",
              success: function () {
                  $("#multiple-delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa danh mục!");
                  $("#notification-dialog").modal("show");
                  categoryTable.draw();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
          selectedItems.clear();
      }

      function deselectSeries(seriesId, categoryId) {
          $.ajax({
              url: "/admin/category/deselect-series",
              type: "DELETE",
              dataType: "text",
              data: {seriesId: seriesId, categoryId: categoryId},
              success: function () {
                  $("#notification-dialog .modal-body p").html("Đã bỏ gắn series!");
                  $("#notification-dialog").modal("show");
                  categoryTable.draw();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }
  </script>
  <style>
    .form-modal .modal-body {
      overflow-y: auto;
      height: calc(100vh - 200px);
    }

    td {
      word-break: break-all;
      vertical-align: middle !important;
    }
  </style>
</head>
<body>
<header class="container mb-5">
    <#assign link = "category">
    <#include "./fragment/nav.ftl">
</header>
<section class="container pt-3">
  <div class="row">
    <div class="col-md-12">
      <div class="row my-4">
        <div class="col-md-6">
          <h3>Danh Sách Danh Mục</h3>
        </div>
        <div class="col-md-6">
          <button type="button" data-target="#add-category-modal" data-toggle="modal"
                  class="btn btn-primary pull-right">Thêm Mới
          </button>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12 table-responsive-md">
          <table id="category-table" class="table table-borderless table-hover">
            <thead>
            <tr>
              <th style="width: 5%;">#</th>
              <th style="width: 5%;">Id</th>
              <th style="width: 10%;">Mã</th>
              <th>Tên</th>
              <th>Mô tả</th>
              <th style="width: 13%;">Thẻ</th>
              <th style="width: 13%;">Series</th>
              <th style="width: 10%;">#</th>
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
              <th style="width: 10%;">Mã</th>
              <th>Tên</th>
              <th>Mô tả</th>
              <th>Thẻ</th>
              <th>Series</th>
              <th>#</th>
            </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </div>

    <#--add category dialog-->
  <div class="modal form-modal fade" role="dialog" id="add-category-modal">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
        <form method="post" action="${'/admin/category/add'}" enctype="multipart/form-data">
          <div class="modal-header">
            <h3 class="modal-title">Thêm danh mục</h3>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="control-label col-md-4" for="input-code">Mã danh mục: </label>
              <div class="col-md-12">
                <input class="form-control" name="code" id="input-code" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-name">Tên danh mục</label>
              <div class="col-md-12">
                <input class="form-control" name="name" id="input-name" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-description">Mô tả:</label>
              <div class="col-md-12">
                <textarea class="form-control" name="description" id="input-description"></textarea>
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
    <#--update category dialog-->
  <div class="modal form-modal fade" role="dialog" id="update-category-modal">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
        <form method="post" action="${'/admin/category/update'}" enctype="multipart/form-data">
          <div class="modal-header">
            <h3 class="modal-title">Cập nhật danh mục</h3>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="control-label col-md-4" for="input-id">ID :</label>
              <div class="col-md-12">
                <input class="form-control" name="id" id="input-id" type="text" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-code">Mã Danh Mục :</label>
              <div class="col-md-12">
                <input class="form-control" name="code" id="input-code" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-name">Tên Danh Mục:</label>
              <div class="col-md-12">
                <input class="form-control" name="name" id="input-name" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-description">Mô tả:</label>
              <div class="col-md-12">
                <textarea class="form-control" name="description" id="input-description"></textarea>
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
    <#--link tag category dialog-->
  <div class="modal fade" role="dialog" id="link-tag-category-modal">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
        <form method="post" action="${'/admin/category/add-tag'}" enctype="multipart/form-data">
          <div class="modal-header">
            <h3 class="modal-title">Gắn Thêm Thẻ</h3>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="control-label col-md-4" for="input-code">Mã thẻ:</label>
              <div class="col-md-12">
                <input class="form-control" name="code" id="input-code" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-4" for="input-name">Tên Thẻ:</label>
              <div class="col-md-12">
                <input class="form-control" name="name" id="input-name" type="text">
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
    <#--select series modal-->
  <div class="modal fade" role="dialog" id="link-series-category-modal">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
        <form method="post" action="${'/admin/category/link-series'}" enctype="multipart/form-data">
          <div class="modal-header">
            <h3 class="modal-title">Gắn Series</h3>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <div class="modal-body">
            <label for="select-series">Series</label>
            <select class="form-control" id="select-series">
                <#list seriesList as series>
                  <option value="${series.id}">${series.name}</option>
                </#list>
            </select>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-warning" data-dismiss="modal">Hủy</button>
            <button type="submit" class="btn btn-primary">Gắn</button>
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
</section>
<footer class="sticky-bottom container">
    <#include "./fragment/footer.ftl">
</footer>
</body>
</html>