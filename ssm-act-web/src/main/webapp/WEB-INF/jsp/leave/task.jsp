<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>我的任务</title>
</head>
<body>
<div class="container-fluid">
    <div class="bs-content" data-content="流程详细信息">
        <form id="form" action="${contextPath}/leave/taskSubmit" method="post" class="form-horizontal form-group-sm" role="form">
            <div class="hidden">
                <input name="taskId" type="hidden" value="${taskId}">
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="days">请假天数</label>
                <div class="col-sm-8">
                    <input id="days" name="days" value="${leave.days}" class="form-control" disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="content">请假事由</label>
                <div class="col-sm-8">
                    <input id="content" name="content" value="${leave.content}" class="form-control"
                           disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="remark">备注</label>
                <div class="col-sm-8">
                    <textarea id="remark" name="remark" maxlength="100" class="form-control"
                              disabled="disabled">${leave.remark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="comment">批注</label>
                <div class="col-sm-8">
                    <textarea id="comment" name="comment" maxlength="100" class="form-control"
                              placeholder="批注"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-8 col-sm-offset-2">
                    <c:forEach var="item" items="${outgoingList}">
                        <button type="submit" class="btn btn-sm btn-default">${item}</button>
                    </c:forEach>
                    <%--
                    history.go(-1) => 返回上一页, 原页面表单内容会丢失;
                    history.back() => 返回上一页, 原页面表单内容会保留;
                    --%>
                    <button type="button" class="btn btn-sm btn-default" onclick="history.back();">返回</button>
                </div>
            </div>
        </form>
    </div>
    <div class="bs-content" data-content="历史批注信息">
        <table id="table" class="table table-hover table-condensed" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>批注时间</th>
                <th>批注人</th>
                <th>批注信息</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    require([
        'jquery',
        'noty',
        'bootstrap',
        'datatables.net',
        'datatables.net-bs'
    ], function ($, noty) {

        $('#form').on('submit', function (e) {
            // Prevent form submission
            e.preventDefault();
            // Get the form instance
            var $form = $(e.target);
            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function (result) {
                noty.message(result.message, result.success);
                result.success && $table.ajax.reload(function () {
                    $form.find(':input:not([type="button"])').attr({disabled: 'disabled'});
                });
            }, 'json');
        });

        var $table = $('#table').DataTable({
            info: false,
            paging: false,
            ordering: false,
            lengthChange: false,
            searching: false,
            ajax: {
                url: '${contextPath}/process/getHistoryComments',
                data: {processInstanceId: '${processInstanceId}'},
                type: 'POST',
                dataType: 'json',
                dataSrc: function (data) {
                    return data;
                }
            },
            columns: [
                {data: 'time'},
                {data: 'userId'},
                {data: 'fullMessage'}
            ]
        });

    });
</script>

</body>
</html>
