<!DOCTYPE html>
<html lang="en">
<head>
    <#assign title = "Quản Lý Bài Viết">
    <#include "./fragment/layout-header.ftl">
    <script>
        let postTable;
        let selectedId;
        let selectedItems = new Set();
        $(function () {
            postTable = $("#post-table").DataTable({
                processing: true,
                serverSide: true,
                ajax: {
                    url: "/user/post/list",
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
                    {data: "title"},
                    {data: "authorName"},
                    {data: "createdDate"},
                    {data: "id"}
                ],
                columnDefs: [
                    {orderable: false, targets: [0, 5]},
                    {
                        render: function () {
                            return "";
                        },
                        className: 'select-checkbox',
                        targets: 0
                    },
                    {
                        render: function (data, type, row) {
                            return '<a data-toggle="tooltip" title="Xem bài viết" href="/post/' + row.code +'" class="btn btn-sm btn-success ml-1"><i class="fas fa-arrow-right"></i></a>'
                                + '<a data-toggle="tooltip" title="Chỉnh Sửa" href="/user/post/update/' + row.id +'" class="btn btn-sm btn-info ml-1"><i class="fas fa-edit"></i></a>'
                                + '<button data-toggle="tooltip" title="Xóa bài viết" data-toggle="modal" data-target="#delete-confirm-dialog" data-json="' + encodeURI(JSON.stringify(row)) + '" class="btn btn-sm btn-danger ml-1"><i class="fas fa-trash"></i></button>';
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
                    url: "${'/vendor/datatables-jquery/Vietnamese.json'}",
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

            postTable.on("select", function (e, dt, type, indexes) {
                let rowData = postTable.rows(indexes).data();
                selectedItems.add(rowData[0]);
            }).on("deselect", function (e, dt, type, indexes) {
                let rowData = postTable.rows(indexes).data();
                selectedItems.delete(rowData[0]);
            });

            $("#delete-confirm-dialog").on("show.bs.modal", function (e) {
                let row = JSON.parse(decodeURI($(e.relatedTarget).data("json")));
                $("#delete-confirm-dialog .modal-body p").html("Mã: " + row.id + " / Tiêu Đề: " + row.title);
                $("#delete-confirm-dialog .modal-footer .btn-danger").attr("onclick", "deletePost(" + row.id + ")");
            });
        });

        function deletePost(id) {
            $.ajax({
                url: "/user/post/delete/" + id,
                type: "DELETE",
                dataType: "text",
                success: function () {
                    $("#delete-confirm-dialog").modal("hide");
                    $("#notification-dialog .modal-body p").html("Đã xóa bài viết!");
                    $("#notification-dialog").modal("show");
                    postTable.draw();
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
                url: "/user/post/multiple-delete/" + deleteIds.toString(),
                type: "GET",
                dataType: "text",
                success: function () {
                    $("#multiple-delete-confirm-dialog").modal("hide");
                    $("#notification-dialog .modal-body p").html("Đã xóa các bài viết!");
                    $("#notification-dialog").modal("show");
                    postTable.draw();
                    selectedItems.clear();
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
</head>
<body>
<header class="container mb-5">
    <#assign link = "post">
    <#include "./fragment/nav.ftl">
</header>
<section class="container pt-3">
    <div class="row">
        <div class="col-md-12">
            <div class="row my-4">
                <div class="col-md-6">
                    <h3>Danh Sách Bài Viết</h3>
                </div>
                <div class="col-md-6">
                    <a href="${'/user/post/new'}" class="btn btn-primary pull-right text-white">Thêm Mới</a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 table-responsive-md">
                    <table id="post-table" class="table table-borderless table-hover">
                        <thead>
                        <tr>
                            <th style="width: 5%;"></th>
                            <th style="width: 5%;">Id</th>
                            <th>Tiêu Đề</th>
                            <th>Tác Giả</th>
                            <th>Ngày Viết</th>
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
                            <th>Id</th>
                            <th>Tiêu Đề</th>
                            <th>Tác Giả</th>
                            <th>Ngày Viết</th>
                            <th>#</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
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