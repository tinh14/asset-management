<%@include file="/common/lib.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="update_modal" tabindex="-1" role="dialog" aria-labelledby="update_asset" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-primary"><c:if test="${isUpdatePage == false}">TẠO HÓA ĐƠN</c:if><c:if test="${isUpdatePage == true}">SỬA HÓA ĐƠN</c:if></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                        <form id="save-form" method="<c:if test="${isUpdatePage == true}">PUT</c:if><c:if test="${isUpdatePage == false}">POST</c:if>" >
                        <div class="form-row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="inputId">Mã hóa đơn</label>
                                    <input type="text" class="form-control" name="id" value="${invoice.id}" <c:if test="${isUpdatePage == true}">readonly</c:if>>
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label>Số hóa đơn</label>
                                <input type="number" class="form-control" name="number" value="${invoice.number}">
                        </div>
                        <div class="form-group col-md-6 col-lg-4">
                            <label>Ngày lập hóa đơn</label>
                            <input autocomplete="off" type="text" class="form-control datepicker" name="invoiceDate" value="<fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy"/>">
                        </div>
                        <div class="form-group col-md-6 col-lg-4">
                            <label for="searchSupplier">Nhà cung cấp</label>
                            <input type="text" class="form-control action" action="searchSupplier" autocomplete="off" name="supplierId" id="searchSupplier" aria-haspopup="false" aria-expanded="false" data-value="${invoice.supplier.id}" placeholder="Tìm và chọn nhà cung cấp" value="${invoice.supplier.name}">
                            <div class="dropdown-menu">
                                <c:if test="${fn:length(suppliers) == 0}">
                                    <a class="dropdown-item emptyRow" href="javascript:void(0)">
                                        <div class="text-center text-secondary font-italic">
                                            Không có dữ liệu
                                        </div>
                                    </a>
                                </c:if>
                                <c:forEach var="supplier" items="${suppliers}">   
                                    <a class="dropdown-item border-bottom" href="javascript:void(0)">
                                        <div class="row">
                                            <div class="col-sm-4 d-flex align-items-center">
                                                <span data-value="${supplier.id}">Mã: ${supplier.id}</span>
                                            </div>
                                            <div class="col-sm-8 d-flex align-items-center">
                                                <span data-value="${supplier.name}">Tên: ${supplier.name}</span>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
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