<%@include file="/common/lib.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@include file="/common/assets/main_css.jsp"%>
        <%@include file="/common/assets/datepicker_css.jsp"%>
        <link href="data:image/x-icon;base64,AAABAAEAEBAQAAEABAAoAQAAFgAAACgAAAAQAAAAIAAAAAEABAAAAAAAgAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAA/4QAAP+7cwDZcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACIiIiIwAAAAIiIiIjMAAAAiIiIiMzAAACIiIiIzMwAAIiIiIjMzAAAiIiIiMzMAACIiIiIzMwAAIiIiIjMzAAARERERMzMAAAERERETMwAAABEREREzAAAAARERERMAAAAAEREREQAAAAAAAAAAD//wAA//8AAOAPAADgBwAA4AMAAOABAADgAQAA4AEAAOABAADgAQAA4AEAAPABAAD4AQAA/AEAAP4BAAD//wAA" rel="icon" type="image/x-icon" />
        <title>Khấu hao tài sản</title>
    </head>
    <body>
        <div class="container-fluid">
            <%@include file="/common/web/sidebar.jsp"%>
            <%@include file="/common/web/message_dialog.jsp"%>
            <div class="content-wrapper">
                <div class="form-wrapper">
                    <div class="row p-3 border-bottom" id="control-div">
                        <div class="col-3 col-sm-3 col-md-2 col-lg-1 col-xl-1">
                            <button id="hide-sidebar-button" class="btn form-control btn-primary"><i class="fas fa-bars"></i></button>
                        </div>
                        <div class="col-9 col-sm-9 col-md-4 col-lg-6 col-xl-6">
                            <h5 class="text-primary">TÍNH KHẤU HAO</h5>
                        </div>
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-3 mt-2-lg-0 d-flex align-items-center">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-white">
                                        <i class="fa fa-search text-dark"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="Tìm theo kỳ kế toán" id="search-input">
                            </div>
                        </div>
                        <div class="col-12 col-sm-6 col-md-2 col-lg-2 col-xl-2">                        
                            <button class="btn form-control btn-primary" data-value="assets/add" type="button" name="add">
                                <i class="fas fa-plus mr-2"></i>Tạo mới</button>
                        </div>
                    </div>
                    <div class="row p-3 align-items-center">
                        <div class="col-lg-4 col-md-4 text-secondary">
                            <i class="fas fa-cogs mr-2"></i>Phương pháp tính
                            <a href="javascript:void(0)" class="dropdown-toggle text-dark font-weight-bold" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tất cả</a>
                            <div class="dropdown-menu filter">
                                <a class="dropdown-item" href="javascript:void(0)" data-value="all">Tất cả</a>
                                <c:forEach var="depreciationMethod" items="${depreciationMethods}">
                                    <a class="dropdown-item" href="javascript:void(0)" data-value="${depreciationMethod.id}">${depreciationMethod.name}</a>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-lg-7 col-md-7 text-secondary ">
                            <div class="form-row align-items-center">
                                <div class="form-row align-items-center">
                                    <div class="col-2">
                                        Ngày hủy:
                                    </div>
                                    <div class="col-lg-3 col-md-4 col-sm-5 col-5 p-1">
                                        <input id="startDate" placeholder="Từ" autocomplete="off" type="text" class="form-control datepicker text-dark font-weight-bold invoiceDate" name="invoiceDate" value="<fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy"/>">
                                    </div>
                                    <div class="col-lg-3 col-md-4 col-sm-5 col-5 p-1">
                                        <input id="endDate" placeholder="Đến" autocomplete="off" type="text" class="form-control datepicker text-dark font-weight-bold invoiceDate" name="invoiceDate" value="<fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy"/>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-12 col-12 text-secondary">
                            <button class="btn btn-secondary reloadBtn"><i class="fas fa-sync"></i></button>
                        </div>
                    </div>
                    <div class="table-wrapper border-bottom">
                        <table class="table table-hover">
                            <thead>
                                <tr class="text-primary">
                                    <th class="text-center">Mã số</th>
                                    <th>Ngày lập</th>
                                    <th>Người lập</th>
                                    <th>Phương pháp tính</th>
                                    <th>Kỳ kế toán</th>
                                    <th>Tổng giá trị</div>
                                    <th>Khấu hao kỳ</div>
                                    <th>Khấu hao tích lũy</div>
                                    <th>Giá trị còn lại</div>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${fn:length(depreciationSchedules) == 0}">
                                    <tr class="text-center text-center text-secondary emptyRow">
                                        <td colspan="12">
                                            Không có dữ liệu
                                        </td>
                                    </tr>
                                </c:if>
                                <c:forEach var="depreciationSchedule" items="${depreciationSchedules}">
                                    <tr>
                                        <td class="text-center">${depreciationSchedule.id}</td>
                                        <td class="dateIndex"><fmt:formatDate value="${depreciationSchedule.depreciationDate}" pattern="dd/MM/yyyy"/></td>
                                        <td>${depreciationSchedule.creator.getId()} - ${depreciationSchedule.creator.getFullName()}</td>
                                        <td class="index">${depreciationSchedule.depreciationMethod.name}</td>
                                        <td>${depreciationSchedule.accountingPeriod.name}</td>
                                        <td>${depreciationSchedule.totalValue}</td>
                                        <td>${depreciationSchedule.periodDepreciationExpense}</td>
                                        <td>${depreciationSchedule.accumulatedDepreciation}</td>
                                        <td>${depreciationSchedule.remainingValue}</td>
                                    </tr> 
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="row mt-2 justify-content-end">
                        <div class="col-sm-6"></div>
                        <div class="col-sm-6 text-secondary text-right mr-3">
                            Tổng số <span id="numRecords">${fn:length(depreciationSchedules)}</span> bản ghi
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/common/assets/main_js.jsp"%>
    <%@include file="/common/assets/datepicker_js.jsp"%>
    <script type="module" src="<c:url value="/templates/js/app.js"></c:url>"></script>
</body>
</html>