<%@include file="/common/lib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="update_modal" tabindex="-1" role="dialog" aria-labelledby="update_asset" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-primary"><c:if test="${isUpdatePage == false}">TẠO TÀI SẢN</c:if><c:if test="${isUpdatePage == true}">SỬA TÀI SẢN</c:if></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                        <form id="save-form" method="<c:if test="${isUpdatePage == true}">PUT</c:if><c:if test="${isUpdatePage == false}">POST</c:if>" >
                        <div class="form-row">
                            <div class="form-group col-sm-6 col-md-6 col-lg-4">
                                <label for="inputId">Mã tài sản</label>
                                    <input type="number" class="form-control" <c:if test="${isUpdatePage == true}">name="id"</c:if> value="${asset.id}" readonly>
                            </div>
                            <div class="form-group col-sm-6 col-md-6 col-lg-4">
                                <label for="inputName">Tên</label>
                                <input type="text" class="form-control" name="name" value="${asset.name}">
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-lg-4">
                            <label for="inputWeight">Khối lượng (gam)</label>
                            <input type="text" class="form-control checkNumberType" name="weight" min="1" value="${asset.weight}">
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-lg-4">
                            <label >Giá nhập (đ)</label>
                            <input type="text" class="form-control checkNumberType" name="price" min="0" value="${asset.price}">
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-lg-4">
                            <label>Phần trăm khấu hao</label>
                            <input type="text" class="form-control checkNumberType" name="percentageDepreciation" min="0" value="${asset.percentageDepreciation}">
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-lg-4">
                            <label>Đơn vị tính</label>
                            <input type="text" class="form-control" name="unit" value="${asset.unit}">
                        </div>
                        <div class="form-group col-sm-6 col-md-6 col-lg-4">
                            <label for="searchAssetType">Loại tài sản</label>
                            <input type="text" class="form-control action" action="searchAssetType" autocomplete="off" name="assetTypeId" action="searchAssetType" aria-haspopup="false" aria-expanded="false" data-value="${asset.assetType.id}" placeholder="Tìm và chọn loại tài sản" value="${asset.assetType.name}">
                            <div class="dropdown-menu" aria-labelledby="searchAssetType">
                                <c:if test="${fn:length(assetTypes) == 0}">
                                    <div class="dropdown-item emptyRow">
                                        <div class="text-center text-secondary font-italic">Không có dữ liệu</div>
                                    </div>
                                </c:if>
                                <c:forEach var="assetType" items="${assetTypes}">   
                                    <a class="dropdown-item border-bottom" href="javascript:void(0)">
                                        <div class="row">
                                            <div class="col-sm-4 d-flex align-items-center">
                                                <span data-value="${assetType.id}">Mã: ${assetType.id}</span>
                                            </div>
                                            <div class="col-sm-8 d-flex align-items-center">
                                                <span data-value="${assetType.name}">Tên: ${assetType.name}</span>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-sm-12 col-md-12 col-lg-4">
                            <label for="inputImage">Ảnh</label>
                            <img src="${asset.image}" 
                                 class="img-fluid rounded btn border border-secondary img-thumb" role="button" id="inputImage" alt="Chưa có ảnh">
                            <input type="file" class="d-none" id="inputImageFile" accept="image/*"/>
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