import {getFormData} from './common.js';

export function getData() {
    const data = getFormData("#save-form");
    data["avatar"] = $('#inputImage').attr('src');
    data["departmentId"] = $('input.action').filter(function () {
        return $(this).attr('action') === "searchDepartment";
    }).attr('data-value');
    data["accountUsername"] = $('input.action').filter(function () {
        return $(this).attr('action') === "searchAccount";
    }).attr('data-value');
    
    return data;
}