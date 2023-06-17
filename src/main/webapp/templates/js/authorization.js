import {showErrorChildModal, sendRequest, getURL} from './common.js';

export function init() {
    $('body').on('shown.bs.modal', '#permission_modal', function () {
        $('.container').children("div:nth-child(2)").find("input").each(function () {
            var total = $(this).closest('.row').children().length;
            var cnt = $(this).closest('.row').find('input').filter(":checked").length;
            $(this).closest('.container').children('div:first-child').find('input').prop('checked', total === cnt);
        });
        
        $('.container').children("div:first-child").find("input").click(function () {
            $(this).closest('.container').find('div:nth-child(2)').find('input').prop('checked', $(this).is(':checked'));
        });
        
        $('.container').children("div:nth-child(2)").find("input").click(function () {
            var total = $(this).closest('.row').children().length;
            var cnt = $(this).closest('.row').find('input').filter(":checked").length;
            $(this).closest('.container').children('div:first-child').find('input').prop('checked', total === cnt);
        });
    });
}

export function save() {
    $('body').on('click', '#saveBtn', function () {
        var data = [];
        $('.container').each(function () {
            $(this).children('div:nth-child(2)').find("input").filter(":checked").each(function () {
                var arr = $(this).attr("id").toString().split("-");
                var temp = {};
                temp["resourceId"] = arr[1];
                temp["actionId"] = arr[0];
                temp["username"] = $('input[name="username"]').val();
                data.push(temp);
            });
        });

        if (data.length === 0) {
            data.push({
                resourceId: null,
                actionId: null,
                username: $('input[name="username"]').val()
            });
        }

        sendRequest(getURL("accounts/authorization"), "PUT", JSON.stringify(data), "application/json",
                function () {
                    $('#permission_modal').modal('hide');
                },
                function (xhr) {
                    $("#message-dialog").css("z-index", parseInt($("#permission_modal").css("z-index")) + 1);
                    showErrorChildModal(xhr.responseText);
                }
        );
    });
}