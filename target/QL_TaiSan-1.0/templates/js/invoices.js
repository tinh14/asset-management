import {getFormData} from './common.js';

export function getData() {
    const data = getFormData("#save-form");
    data["supplierId"] = $('input.action').filter(function(){
        return $(this).attr('action') === "searchSupplier";
    }).attr('data-value');
    return data;
}