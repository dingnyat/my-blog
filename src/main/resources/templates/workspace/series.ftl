<#include "../layout/layout.ftl"/>

<#macro customResources>
  <script>
      let csrf_token = '${_csrf.token}';
      let seriesTable;
      let selectedId;
      let selectedItems = new Set();
      $(function () {
          seriesTable = $("#series-table").DataTable({
              processing: true,
              serverSide: true,
              ajax: {
                  url: "/admin/series/list",
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
                  {data: "description"},
                  {data: "id"}
              ],
              columnDefs: [
                  {orderable: false, targets: [0, 4, 5]},
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
                      render: function (data, type, row) {
                          return '<button data-toggle="modal" data-target="#update-series-modal" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-info ml-1"><i class="fas fa-edit"></i></button>'
                              + '<button data-toggle="modal" data-target="#delete-confirm-dialog" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-danger ml-1"><i class="fas fa-trash"></i></button>';
                      },
                      targets: 5
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

          $("#multiple-delete-btn").on("click", function () {
              if (selectedItems.size < 1) {
                  $("#notification-dialog .modal-body p").html("Chọn ít nhất một mục để xóa!");
                  $("#notification-dialog").modal("show");
              } else {
                  $("#multiple-delete-confirm-dialog").modal("show");
              }
          });

          seriesTable.on("select", function (e, dt, type, indexes) {
              let rowData = seriesTable.rows(indexes).data();
              selectedItems.add(rowData[0]);
          }).on("deselect", function (e, dt, type, indexes) {
              let rowData = seriesTable.rows(indexes).data();
              selectedItems.delete(rowData[0]);
          });

          $("#series-table tbody")
              .on("click", "td.description-details", function () {
                  let tr = this.parentNode;
                  let row = seriesTable.row(tr);
                  if (row.child.isShown()) {
                      row.child.hide();
                      $(tr).removeClass("shown");
                  } else {
                      let content = '<h5>Mô Tả Series</h5>';
                      content += '<p>' + row.data().description + '</p>';
                      row.child(content).show();
                      $(tr).addClass("shown");
                  }
              });


          $("#delete-confirm-dialog").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#delete-confirm-dialog .modal-body p").html("Mã: " + row.code + " / Tên: " + row.name);
              $("#delete-confirm-dialog .modal-footer .btn-danger").attr("onclick", "deleteSeries(" + row.id + ")");
          });

          $("#update-series-modal").on("show.bs.modal", function (e) {
              let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
              $("#update-series-modal #input-name").val(row.name);
              $("#update-series-modal #input-code").val(row.code);
              $("#update-series-modal #input-description").val(row.description);
              $("#update-series-modal #input-id").val(row.id);
          });


          $("#add-series-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/series/add",
                  type: "POST",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#add-series-modal form")[0].reset();
                      $("#add-series-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã thêm series!");
                      $("#notification-dialog").modal("show");
                      seriesTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });

          $("#update-series-modal form").submit(function (e) {
              e.preventDefault();
              let formData = new FormData(this);
              $.ajax({
                  url: "/admin/series/update",
                  type: "PUT",
                  dataType: "text",
                  contentType: false,
                  processData: false,
                  headers: {'X-CSRF-TOKEN': csrf_token},
                  data: formData,
                  success: function () {
                      $("#update-series-modal form")[0].reset();
                      $("#update-series-modal").modal("hide");
                      $("#notification-dialog .modal-body p").html("Đã cập nhật series!");
                      $("#notification-dialog").modal("show");
                      seriesTable.draw();
                  },
                  error: function () {
                      $("#notification-dialog .modal-body p").html("Lỗi xảy ra!");
                      $("#notification-dialog").modal("show");
                  }
              });
          });
      });

      function deleteSeries(id) {
          $.ajax({
              url: "/admin/series/delete/" + id,
              type: "DELETE",
              dataType: "text",
              headers: {'X-CSRF-TOKEN': csrf_token},
              success: function () {
                  $("#delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa series!");
                  $("#notification-dialog").modal("show");
                  seriesTable.draw();
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
              url: "/admin/series/multiple-delete/" + deleteIds.toString(),
              type: "GET",
              dataType: "text",
              headers: {'X-CSRF-TOKEN': csrf_token},
              success: function () {
                  $("#multiple-delete-confirm-dialog").modal("hide");
                  $("#notification-dialog .modal-body p").html("Đã xóa series!");
                  $("#notification-dialog").modal("show");
                  seriesTable.draw();
                  selectedItems.clear();
              },
              error: function () {
                  $("#notification-dialog .modal-body p").html("Lỗi xảy ra. Thử lại sau!");
                  $("#notification-dialog").modal("show");
              }
          });
      }
  </script>
</#macro>
<#macro bodyFragment>
  <section class="container-fluid mt-5 mb-5 px-5">
    <div class="row">
      <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="row my-4">
          <div class="col-8 col-sm-8 col-md-8 col-lg-6 col-xl-6">
            <h3>Danh Sách Series</h3>
          </div>
          <div class="col-4 col-sm-4 col-md-4 col-lg-6 col-xl-6">
            <button type="button" data-target="#add-series-modal" data-toggle="modal"
                    class="btn btn-primary pull-right">Thêm Mới
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 table-responsive">
            <table id="series-table" class="table table-borderless table-hover">
              <thead>
              <tr>
                <th style="width: 5%;"></th>
                <th style="width: 5%;">Id</th>
                <th style="width: 20%;">Mã</th>
                <th>Tên</th>
                <th>Mô tả</th>
                <th style="width: 10%;">#</th>
              </tr>
              </thead>
              <tfoot>
              <tr>
                <th class="text-center">
                  <button class="btn btn-sm btn-danger" id="multiple-delete-btn" data-toggle="tooltip"
                          title="Xóa mục đã chọn">
                    <i class="fas fa-trash"></i>
                  </button>
                </th>
                <th>Id</th>
                <th>Mã</th>
                <th>Tên</th>
                <th>Mô tả</th>
                <th>#</th>
              </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </div>
    </div>

    <#--add category dialog-->
    <div class="modal form-modal fade" role="dialog" id="add-series-modal">
      <div class="modal-dialog modal-md">
        <div class="modal-content">
          <form method="post" action="${'/admin/series/add'}" enctype="multipart/form-data">
            <div class="modal-header">
              <h3 class="modal-title">Thêm Series</h3>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label class="control-label col-md-4" for="input-code">Mã Series: </label>
                <div class="col-md-12">
                  <input class="form-control" name="code" id="input-code" type="text">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-4" for="input-name">Tên Series</label>
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
    <#--update series dialog-->
    <div class="modal form-modal fade" role="dialog" id="update-series-modal">
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
</#macro>

<@displayPage page_title="Quản Lý Series"/>
