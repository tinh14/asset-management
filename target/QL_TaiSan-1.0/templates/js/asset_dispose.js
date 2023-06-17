import {getFormData, showErrorChildModal} from './common.js';

export function callback(action) {
    let flag = true;

    if (action === "searchDepartment") {
        return flag;
    }

    const departmentId = $('input.action').filter(function () {
        return $(this).attr('action') === "searchDepartment";
    }).attr('data-value');

    if (departmentId === "") {
        showErrorChildModal("Vui lòng chọn phòng ban trước");
        $('input.action').val('');
        flag = false;
    }
    return flag;
}

export function addTableItemCallback(dropdownItem) {
    const quantity = $(dropdownItem).find('span').last().attr('data-value');
    if (quantity === "0") {
        showErrorChildModal("Tồn kho không đủ");
        return false;
    }
    return true;
}

export function deleteTableItemCallback() {
}

export function getData() {
    const data = getFormData("#save-form");

    data["disposerId"] = $('input[name="disposerId"]').attr("data-value");
    data["disposalDepartmentId"] = $('input[name="disposalDepartmentId"]').attr('data-value');
    data["id"] = (data["id"] === "") ? null : data["id"];

    var list = {
        "assetDisposeDetailList": []
    };

    $('.modal .table tbody tr:not(.emptyRow)').each(function () {
        var row = $(this);

        var assetId = row.find('button').attr("data-value");
        var quantity = row.find('input').eq(0).val();

        list.assetDisposeDetailList.push({
            "assetDisposeId": data["id"],
            "assetId": assetId,
            "quantity": quantity
        });
    });

    data["assetDisposeDetailList"] = list.assetDisposeDetailList;

    return data;
}