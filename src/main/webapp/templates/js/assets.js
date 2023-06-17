import {getFormData} from './common.js';

export function getData() {
    const data = getFormData("#save-form");
    data["image"] = $('#inputImage').attr('src');
    data["assetTypeId"] = $('input.action').filter(function () {
        return $(this).attr('action') === "searchAssetType";
    }).attr('data-value');
    return data;
}