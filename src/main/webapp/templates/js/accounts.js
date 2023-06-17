import {getFormData, getURL, showErrorMessage, sendRequest} from './common.js';
import {init, save} from './authorization.js';

export function permissionEvent() {
    $('body').on('click', '#permissionBtn', function () {
        var url = "accounts/authorization?username=" + $('input[name="username"]').val();
        sendRequest(getURL(url), "GET", {}, "application/json",
                function (response) {
                    if ($('body').find("#permission_modal").length !== 0) {
                        $('body').find("#permission_modal").remove();
                    }
                    $('body').append(response);
                    $("#permission_modal").css("z-index", parseInt($("#update_modal").css("z-index")) + 1);
                    $("#permission_modal").modal('show');
                    
                    init();
                    save();
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}

export function getData() {
    return getFormData("#save-form");
}