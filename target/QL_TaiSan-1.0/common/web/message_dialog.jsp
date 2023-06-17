<%-- 
    Document   : message_dialog
    Created on : Apr 24, 2023, 1:47:01 PM
    Author     : tinhlam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="message-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="message-dialog-title">Thông báo</h5>
            </div>
            <div class="modal-body" >
                <div style="white-space: pre-line" id="message-dialog-content"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="message-dialog-button" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>