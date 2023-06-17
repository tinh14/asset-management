import {getFormData} from './common.js';

export function getData() {
    const data = getFormData("#save-form");
    data["accountingPeriodTypeId"] = $('input.action').filter(function () {
        return $(this).attr('action') === "searchAccountingPeriodType";
    }).attr('data-value');
    return data;
}