
import {getFormData,showErrorMessage,reloadNumRecords, sendRequest, getCurrentResource, getURL, showErrorChildModal} from './common.js';

export function chooseItemFromDropdownMenu() {
    $('body').on('click', '.modal .dropdown-item:not(.table-item)', function () {

        const depreciationMethodAction = $(this).parent().prev().attr('action');
        const deprectiationMethodDataValue = $('input.action[action="searchDepreciationMethod"]').attr('data-value');

        if (depreciationMethodAction !== "searchDepreciationMethod" && deprectiationMethodDataValue === "") {
            showErrorChildModal("Vui lòng chọn phương pháp khấu hao trước");
            return false;
        }

        var id = $(this).find('span').first().attr('data-value');
        var name = $(this).find('span').eq(1).attr('data-value');

        var dropdownMenu = $(this).parent();

        var searchInput = $(dropdownMenu).prev();
        $(searchInput).attr('data-value', id);
        $(searchInput).val(name);

        dropdownMenu.find('.dropdown-item').detach();
        dropdownMenu.removeClass('show');

        if (dropdownMenu.attr("getDepreciationDetailList") === 'true') {
            const url = getCurrentResource() + "/view" + "?action=getDepreciationDetailList&key=" + id;
            sendRequest(getURL(url), "GET", {}, "text",
                    function (response) {
                        $('.modal .table tbody tr:not(.table-item)').detach();
                        var records = $(response).find('table tbody tr:not(.table-item)');
                        records.appendTo($('.modal .table tbody'));
                        reloadNumRecords(records, "#modalNumRecords");
                    },
                    function (xhr) {
                        showErrorMessage(xhr.responseText);
                    }
            );
        }
    });
}

export function getData() {
    const data = getFormData("#save-form");

    data["id"] = (data["id"] === "") ? null : data["id"];
    data["creatorId"] = $('input[name="creatorId"]').attr("data-value");
    data["accountingPeriodId"] = $('input[name="accountingPeriodId"]').attr("data-value");
    data["depreciationMethodId"] = $('input[name="depreciationMethodId"]').attr("data-value");
    data["totalValue"] = 0;
    data["periodDepreciationExpense"] = 0;
    data["accumulatedDepreciation"] = 0;
    data["remainingValue"] = 0;

    const details = {
        "depreciationDetailList": []
    };

    $('.modal .table tbody tr:not(.emptyRow)').each(function () {
        const row = $(this);
        const assetId = row.find('td').eq(0).text();
        const depreciationPeriod = row.find('td').eq(3).text();
        const percentageDepreciation = row.find('td').eq(4).text();
        const totalValue = row.find('td').eq(5).text();
        const periodDepreciationExpense = row.find('td').eq(6).text();
        const accumulatedDepreciation = row.find('td').eq(7).text();
        const remainingValue = row.find('td').eq(8).text();

        data["totalValue"] += parseInt(totalValue);
        data["periodDepreciationExpense"] += parseInt(periodDepreciationExpense);
        data["accumulatedDepreciation"] += parseInt(accumulatedDepreciation);
        data["remainingValue"] += parseInt(remainingValue);

        details.depreciationDetailList.push({
            "depreciationId": data["id"],
            "assetId": assetId,
            "depreciationPeriod": depreciationPeriod,
            "percentageDepreciation": percentageDepreciation,
            "totalValue": totalValue,
            "periodDepreciationExpense": periodDepreciationExpense,
            "accumulatedDepreciation": accumulatedDepreciation,
            "remainingValue": remainingValue
        });
    });
    data["depreciationDetailList"] = details.depreciationDetailList;

    return data;
}