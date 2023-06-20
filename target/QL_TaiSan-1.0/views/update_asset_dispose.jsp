<%@include file="/common/lib.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="update_modal" tabindex="-1" role="dialog" aria-labelledby="update_asset" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" role="document" style="min-width:90%">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-primary"><c:if test="${isUpdatePage == false}">CHUYỂN HỦY MỚI</c:if><c:if test="${isUpdatePage == true}">SỬA CHUYỂN HỦY</c:if></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                        <form id="save-form" method="<c:if test="${isUpdatePage == true}">PUT</c:if><c:if test="${isUpdatePage == false}">POST</c:if>" >
                        <div class="form-row">
                            <div class="form-group col-sm-6 col-md-4 col-lg-3">
                                <label for="inputId">Mã chuyển hủy</label>
                                    <input type="number" class="form-control" name="id" value="${assetDispose.id}" readonly>
                        </div>
                        <div class="form-group col-sm-6 col-md-4 col-lg-3">
                            <label>Người hủy</label>
                            <c:set var="disposerId" value="${assetDispose.disposer.getId()}"/>
                            <c:set var="disposerFullName" value="${assetDispose.disposer.getFullName()}"/>
                            <c:set var="sessionUserId" value="${sessionUser.id}"/>
                            <c:set var="sessionUserFullName" value="${sessionUser.getFullName()}"/>
                            <input type="text" class="form-control" name="disposerId" <c:if test="${isUpdatePage == true}">data-value="${disposerId}" value="${disposerId} - ${disposerFullName}"</c:if> <c:if test="${isUpdatePage == false}">data-value="${sessionUserId}" value="${sessionUserId} - ${sessionUserFullName}"</c:if>readonly>
                            </div>
                            <div class="form-group col-sm-6 col-md-4 col-lg-3">
                                <label>Ngày hủy</label>
                                    <input autocomplete="off" type="text" class="form-control datepicker" name="disposalDate" value="<fmt:formatDate value="${assetDispose.disposalDate}" pattern="dd/MM/yyyy"/>">
                        </div>
                        <div class="form-group col-sm-6 col-md-4 col-lg-3">
                            <label>Phòng ban hủy</label>
                            <input type="text" class="form-control action" action="searchDepartment" autocomplete="off" name="disposalDepartmentId" aria-haspopup="false" aria-expanded="false" placeholder="Tìm và chọn phòng ban" data-value="${assetDispose.disposalDepartment.id}" value="${assetDispose.disposalDepartment.name}" <c:if test="${isUpdatePage == true}">readonly</c:if>>
                                <div class="dropdown-menu" aria-labelledby="searchDepartment">
                                <c:if test="${fn:length(departments) == 0}">
                                    <a class="dropdown-item emptyRow" href="javascript:void(0)">
                                        <div class="text-center text-secondary font-italic">
                                            Không có dữ liệu
                                        </div>
                                    </a>
                                </c:if>
                                <c:forEach var="department" items="${departments}">   
                                    <a class="dropdown-item border-bottom" href="javascript:void(0)">
                                        <div class="row">
                                            <div class="col-sm-6 d-flex align-items-center">
                                                <span data-value="${department.id}">Mã: ${department.id}</span>
                                            </div>
                                            <div class="col-sm-6 d-flex align-items-center">
                                                <span data-value="${department.name}">Tên: ${department.name}</span>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-sm-12 col-md-8 col-lg-6">
                            <label>Lý do hủy</label>
                            <textarea class="form-control" name="reason">${assetDispose.reason}</textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="dropdown col-sm-12">
                            <input type="text" class="form-control action" action="searchAsset"  autocomplete="off" aria-haspopup="false" aria-expanded="false" placeholder="Tìm và thêm tài sản">
                            <div class="dropdown-menu" aria-labelledby="searchAsset">
                                <c:if test="${fn:length(assetWithQuantityList) == 0}">
                                    <div class="dropdown-item emptyRow">
                                        <div class="text-center text-secondary font-italic">Không có dữ liệu</div>
                                    </div>
                                </c:if>
                                <c:forEach var="assetWithQuantity" items="${assetWithQuantityList}" varStatus="status">   
                                    <a class="dropdown-item table-item border-bottom" href="javascript:void(0)">
                                        <div class="row">
                                            <div class="col-sm-2 d-flex align-items-center">
                                                Ảnh: <img src="${assetWithQuantity.asset.image}" class="ml-2" alt=" ">
                                            </div>
                                            <div class="col-sm-1 d-flex align-items-center">
                                                <span data-value="${assetWithQuantity.asset.id}">Mã: ${assetWithQuantity.asset.id}</span>
                                            </div>
                                            <div class="col-sm-2 d-flex align-items-center">
                                                <span>Tên: ${assetWithQuantity.asset.name}</span>
                                            </div>
                                            <div class="col-sm-2 d-flex align-items-center">
                                                <span>Đơn vị tính: ${assetWithQuantity.asset.unit}</span>
                                            </div>
                                            <div class="col-sm-1 d-flex align-items-center">
                                                <span data-value="${assetWithQuantity.quantity}">Tồn kho: ${assetWithQuantity.quantity}</span>
                                            </div>
                                            <div class="col-sm-4"></div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="form-row mt-4">
                        <div class="w-100 table-wrapper-modal border-bottom">
                            <table class="table table-hover">
                                <thead class="sticky-top">
                                    <tr class="text-primary text-center">
                                        <th class="col-1"></th>
                                        <th class="col-1">Ảnh</th>
                                        <th class="col-2">Tên tài sản</th>
                                        <th class="col-2">Đơn vị tính</th>
                                        <th class="col-2">Số lượng hủy</th>
                                        <th class="col-2">Tồn kho phòng ban</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${fn:length(assetDispose.assetDisposeDetailList) == 0}">
                                        <tr class="text-center emptyRow">
                                            <td colspan="6" class="text-center text-secondary font-italic">
                                                Không có dữ liệu
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="assetDisposeDetail" items="${assetDispose.assetDisposeDetailList}" varStatus="status">
                                        <tr class="text-center">
                                            <td>
                                                <button class="btn form-control btn-delete text-danger" data-value="${assetDisposeDetail.asset.id}"><span class="fa fa-times"></span></button>
                                            </td>
                                            <td>
                                                <img src="${assetDisposeDetail.asset.image}" alt=" " class="table-img">
                                            </td>
                                            <td>${assetDisposeDetail.asset.name}</td>
                                            <td>${assetDisposeDetail.asset.unit}</td>
                                            <td>
                                                <input type="text" class="form-control text-center text-dark checkNumberType" min="1" max="${quantities[status.index] + assetDisposeDetail.quantity}" value="${assetDisposeDetail.quantity}">
                                            </td>
                                            <td>${quantities[status.index]}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:forEach var="assetWithQuantity" items="${assetWithQuantityList}">
                                        <tr class="text-center">
                                            <td>
                                                <button class="btn form-control btn-delete text-danger" data-value="${assetWithQuantity.asset.id}" ><span class="fa fa-times"></span></button>
                                            </td>
                                            <td>
                                                <img src="${assetWithQuantity.asset.image}" alt=" " class="table-img">
                                            </td>
                                            <td>${assetWithQuantity.asset.name}</td>
                                            <td>${assetWithQuantity.asset.unit}</td>
                                            <td>
                                                <input type="text" class="form-control text-center text-dark checkNumberType" min="1" max="${assetWithQuantity.quantity}" value="1">
                                            </td>
                                            <td>${assetWithQuantity.quantity - 1}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-sm-6"></div>
                        <div class="col-sm-6 text-secondary text-right">
                            Tổng số <span id="modalNumRecords">${fn:length(assetDispose.assetDisposeDetailList)}</span> bản ghi
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary action" action="saveAndClose">Lưu và đóng</button>
                <button type="button" class="btn btn-primary action <c:if test="${isUpdatePage == true}">d-none</c:if>" action="saveAndNew">Lưu và tạo mới</button>
            </div>
        </div>
    </div>
</div>