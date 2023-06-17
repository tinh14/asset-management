import {getFormData, showErrorChildModal} from "./common.js";

export function addTableItemCallback(dropdownItem) {
    const quantity = $(dropdownItem).find('span').last().attr('data-value');
    if (quantity === "0") {
        showErrorChildModal("Tồn kho không đủ");
        return false;
    }
    return true;
}

export function tableInputCallback(input) {
    var row = $(input).closest('tr');
    $(input).parent().next().text(parseInt($(input).attr('max')) - parseInt($(input).val()));
}

export function deleteTableItemCallback(e) {
}


export function getData() {
    const data = getFormData("#save-form");
    data["transferorId"] = $('input[name="transferorId"]').attr("data-value");
    data["receivingDepartmentId"] = $('input[name="receivingDepartmentId"]').attr('data-value');

    var list = {
        "assetTransferDetailList": []
    };

    $('.modal .table tbody tr:not(.emptyRow)').each(function () {
        var row = $(this);

        var assetId = row.find('button').attr("data-value");
        var quantity = row.find('input').eq(0).val();

        list.assetTransferDetailList.push({
            "assetTransferId": data["id"],
            "assetId": assetId,
            "quantity": quantity
        });
    });

    data["assetTransferDetailList"] = list.assetTransferDetailList;
    return data;
}