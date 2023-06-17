<%-- 
    Document   : newjsp
    Created on : Apr 24, 2023, 1:59:10 PM
    Author     : tinhlam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="confirm-dialog" class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thông báo</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body" id="confirm-dialog-content">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="confirm-btn">Xóa</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
            </div>
        </div>
    </div>
</div>