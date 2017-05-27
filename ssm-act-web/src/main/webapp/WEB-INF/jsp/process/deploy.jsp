<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>流程管理-流程部署</title>
</head>
<body>
<div class="container-fluid">
    <div class="page-header">
        <label class="h4">流程管理-流程部署</label>
        <label class="h4 pull-right">
            <small>SSM-PROCESS-DEPLOY</small>
        </label>
    </div>
    <form id="form" enctype="multipart/form-data" class="form-horizontal form-group-sm" role="form">
        <fieldset class="bs-content" data-content="流程部署">
            <div class="form-group">
                <label class="control-label col-sm-1" for="name">流程名称:</label>
                <div class="col-sm-3">
                    <input id="name" name="name" type="text" class="form-control" placeholder="流程名称"
                           data-bv-notempty="true"
                           data-bv-stringlength="true"
                           data-bv-stringlength-min="4"
                           data-bv-stringlength-max="20">
                </div>
                <label class="control-label col-sm-1" for="file">流程文件:</label>
                <div class="col-sm-3">
                    <input id="file" name="file" type="file" class="form-control" placeholder="流程文件"
                           data-bv-notempty="true">
                </div>
                <div class="col-sm-4">
                    <button id="btnUpload" type="button" class="btn btn-primary btn-sm">
                        <i class="fa fa-upload"></i> 上传
                    </button>
                    <button type="reset" class="btn btn-default btn-sm">
                        <i class="glyphicon glyphicon-refresh"></i> 重置
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
    <div class="bs-content" data-content="流程部署列表">
        <table id="deployList" class="table table-bordered table-hover table-condensed" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>部署ID</th>
                <th>部署名称</th>
                <th>部署时间</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="bs-content" data-content="流程定义列表">
        <table id="procdefList" class="table table-bordered table-hover table-condensed" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>流程定义ID</th>
                <th>部署ID</th>
                <th>流程定义名称</th>
                <th>流程定义KEY</th>
                <th>流程定义版本</th>
                <th>流程定义文件名称</th>
                <th>流程定义图片名称</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script id="template" type="text/x-handlebars-template">
    <div class="modal fade">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <label class="modal-title">{{title}}</label>
                </div>
                <div class="modal-body">
                    <div class="text-center">
                        <img src="${contextPath}/process/getProcessDiagram?pdId={{pdId}}" alt="{{title}}"
                             class="img-thumbnail" style="border: none;">
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>

<script type="text/javascript">
    <!--
    require([
        'jquery',
        'noty',
        'handlebars',
        'bootstrap',
        'bootstrapValidator',
        'datatables.net',
        'datatables.net-bs',
        'datatables.net-buttons',
        'datatables.net-buttons-bs',
        'datatables.net-buttons-colVis',
        'datatables.net-select'
    ], function ($, noty, Handlebars) {

        var template = Handlebars.compile($('#template').html());

        $('#form').on('reset', function () {
            $(this).data('bootstrapValidator').resetForm();
            $('#btnUpload').removeAttr('disabled');
        }).bootstrapValidator({
            group: 'div.col-sm-3'
        });

        $('#btnUpload').click(function () {
            var $form = $('#form');
            if ($form.validate()) {
                var $this = $(this);
                $.ajax({
                    url: '${contextPath}/process/deploySubmit',
                    type: 'POST',
                    data: new FormData($form.get(0)),
                    cache: false,
                    processData: false,
                    contentType: false,
                    dataType: 'json',
                    success: function (result) {
                        result.success && $this.attr({disabled: true});
                        showResult(result);
                    }
                });
            }
        });

        var showResult = function (result) {
            noty.message(result.message, result.success);
            if (result.success) {
                $deployTable.ajax.reload(function () {
                    $deployTable.buttons(['delete:name']).enable(false);
                });
                $procdefTable.ajax.reload(function () {
                    $procdefTable.buttons(['show:name']).enable(false);
                });
            }
        };

        $.extend($.fn.dataTable.ext.buttons, {
            reload: {
                text: 'Reload',
                action: function (e, dt, node, config) {
                    dt.ajax.reload();
                }
            }
        });

        var $deployTable = $('#deployList').DataTable({
            info: false,
            paging: false,
            ordering: false,
            lengthChange: false,
            searching: true,
            rowId: 'id',
            select: {
                style: 'single' // {none, single, multi, os}
            },
            language: {
                select: {
                    rows: {
                        _: '(已選擇 %d 行)',
                        0: '(點擊選擇行)'
                    }
                }
            },
            buttons: [
                {
                    enabled: false,
                    text: '<i class="fa fa-trash"></i> 删除部署',
                    name: 'delete',
                    className: 'btn-sm',
                    titleAttr: 'Click To Delete',
                    action: function () {
                        noty.confirm('确认删除吗?', function (yes) {
                            if (yes) {
                                var deployId = $deployTable.row({selected: true}).id();
                                $.ajax({
                                    url: '${contextPath}/process/deleteDeployment',
                                    type: 'POST',
                                    data: {deployId: deployId},
                                    dataType: 'json',
                                    success: function (result) {
                                        showResult(result);
                                    }
                                });
                            }
                        });
                    }
                },
                {
                    extend: 'reload',
                    enabled: true,
                    text: '<i class="fa fa-refresh"></i> 刷新表格',
                    name: 'reload',
                    className: 'btn-sm',
                    titleAttr: 'Click To Reload'
                },
                {
                    extend: 'colvis',
                    enabled: true,
                    text: '<i class="fa fa-columns"></i> 隐藏列',
                    name: 'colvis',
                    className: 'btn-sm',
                    titleAttr: 'Click To Hidden Column'
                }
            ],
            ajax: {
                url: '${contextPath}/process/getDeploymentList',
                type: 'POST',
                dataType: 'json',
                dataSrc: function (data) {
                    return data;
                }
            },
            columns: [
                {data: 'id'},
                {data: 'name'},
                {data: 'deploymentTime'}
            ],
            fnInitComplete: function (settings, json) {
                $deployTable.buttons().container().appendTo($('.col-sm-6:eq(0)', $deployTable.table().container()));
                $deployTable.buttons().nodes().attr({'data-toggle': 'tooltip'});
            }
        }).on('click', 'tbody tr', function () {
            var rows = $deployTable.rows({selected: true}).data();
            $deployTable.buttons(['delete:name']).enable(rows.length === 1);
        });

        var $procdefTable = $('#procdefList').DataTable({
            info: false,
            paging: false,
            ordering: false,
            lengthChange: false,
            searching: true,
            rowId: 'id',
            select: {
                style: 'single' // {none, single, multi, os}
            },
            language: {
                select: {
                    rows: {
                        _: '(已選擇 %d 行)',
                        0: '(點擊選擇行)'
                    }
                }
            },
            buttons: [
                {
                    enabled: false,
                    text: '<i class="fa fa-eye"></i> 查看流程图',
                    name: 'view',
                    className: 'btn-sm',
                    titleAttr: 'Click To View Image',
                    action: function () {
                        var pdId = $procdefTable.row({selected: true}).id();
                        var rowData = $procdefTable.row({selected: true}).data();
                        $(template({
                            title: rowData['diagramResourceName'],
                            pdId: pdId
                        })).insertBefore('#template').modal({
                            show: true
                        }).on('hidden.bs.modal', function () {
                            $(this).remove();
                        });
                    }
                },
                {
                    extend: 'reload',
                    enabled: true,
                    text: '<i class="fa fa-refresh"></i> 刷新表格',
                    name: 'reload',
                    className: 'btn-sm',
                    titleAttr: 'Click To Reload'
                },
                {
                    extend: 'colvis',
                    enabled: true,
                    text: '<i class="fa fa-columns"></i> 隐藏列',
                    name: 'colvis',
                    className: 'btn-sm',
                    titleAttr: 'Click To Hidden Column'
                }
            ],
            ajax: {
                url: '${contextPath}/process/getProcessDefinitionList',
                type: 'POST',
                dataType: 'json',
                dataSrc: function (data) {
                    return data;
                }
            },
            columns: [
                {data: 'id'},
                {data: 'deploymentId'},
                {data: 'name'},
                {data: 'key'},
                {data: 'version'},
                {data: 'resourceName'},
                {data: 'diagramResourceName'}
            ],
            fnInitComplete: function (settings, json) {
                $procdefTable.buttons().container().appendTo($('.col-sm-6:eq(0)', $procdefTable.table().container()));
                $procdefTable.buttons().nodes().attr({'data-toggle': 'tooltip'});
            }
        }).on('click', 'tbody tr', function () {
            var rows = $procdefTable.rows({selected: true}).data();
            $procdefTable.buttons(['view:name']).enable(rows.length === 1);
        });

    });
    -->
</script>
</body>
</html>
