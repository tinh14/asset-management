import {getURL, sendRequest, showErrorMessage, reloadNumRecords} from './common.js';

export function reloadButtonCallback1() {
    const deparmentName = $('.departmentFilter').prev().text();

    var departmentId = $('.departmentFilter .dropdown-item').filter(function () {
        return $(this).text().indexOf(deparmentName) !== -1;
    }).attr('data-value');

    return (departmentId === "") ? departmentId : "?key=" + departmentId;
}

export function reloadButtonCallback2() {
    $('.assetTypeFilter').prev().text("Tất cả");
}

//export function reloadButtonClickEvent() {
//    $('.reloadBtn').click(function () {
//        var deparmentName = $('.departmentFilter').prev().text();
//
//        var $this = $('.departmentFilter .dropdown-item').filter(function () {
//            return $(this).text().indexOf(deparmentName) !== -1;
//        });
//
//        var departmentId = $this.attr('data-value');
//
//        var data = {
//            key: (departmentId !== "") ? departmentId : null
//        };
//        var url = "departmentalAssets/view?" + $.param(data);
//
//        sendRequest(getURL(url), "GET", {}, "text",
//                function (response) {
//                    $('.content-wrapper .table tbody tr').detach();
//                    var trs = $(response).find('.content-wrapper .table tbody tr');
//                    trs.appendTo('.content-wrapper .table tbody');
//                    reloadNumRecords(trs, "#numRecords");
//                    $('.assetTypeFilter').prev().text("Tất cả");
//                    $('#search-input').val('');
//                },
//                function (xhr) {
//                    showErrorMessage(xhr.responseText);
//                }
//        );
//    });
//}

export function searchDepartmentalAsset() {
    $('body').on('click', '.departmentFilter .dropdown-item', function () {
        var departmentId = $(this).attr('data-value');
        var $this = $(this);
        var data = {
            key: (departmentId !== "") ? departmentId : null
        };
        var url = "departmentalAssets/view?" + $.param(data);

        sendRequest(getURL(url), "GET", {}, "text",
                function (response) {
                    $('.content-wrapper .table tbody tr').detach();
                    var trs = $(response).find('.content-wrapper .table tbody tr');
                    trs.appendTo('.content-wrapper .table tbody');
                    reloadNumRecords(trs, "#numRecords");
                    $this.parent().prev().text($this.text());
                    $('.assetTypeFilter').prev().text("Tất cả");
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}

//export function assetTypeFilter() {
//    $('body').on('click', '.assetTypeFilter .dropdown-item', function () {
//        var trs = $('.content-wrapper .table tbody tr');
//
//        var $this = $(this);
//
//        var filterName = $this.text();
//
//        if (filterName === "Tất cả") {
//            trs.each(function () {
//                $(this).show();
//            });
//        } else {
//            trs.each(function () {
//                var name = $(this).find("td").eq(3).text();
//                if (name.includes($this.text())) {
//                    $(this).show();
//                } else {
//                    $(this).hide();
//                }
//            });
//        }
//
//        var records = $('.content-wrapper .table tbody tr').filter(function () {
//            return $(this).css('display') !== 'none';
//        });
//        reloadNumRecords(records, "#numRecords");
//        $this.parent().prev().text(filterName);
//    });
//}

export function callInputSearchCallback() {
    const deparmentName = $('.departmentFilter').prev().text();
    if (deparmentName === "Chọn") {
        return "";
    }
    const departmentId = $('.departmentFilter .dropdown-item').filter(function () {
        return $(this).text().indexOf(deparmentName) !== -1;
    }).attr('data-value');

    return "&departmentId=" + departmentId;
}

//export function callInputSearchCallback2() {
//    $('.filter').prev().text('Tất cả');
//}

//export function callInputSearchEvent() {
//    $('body').on('keypress', '#search-input', function (e) {
//        if (e.keyCode === 13) {
//
//            var $this = $(this);
//            var deparmentName = $('.departmentFilter').prev().text();
//
//            if (deparmentName === "Chọn") {
//                return false;
//            }
//
//            var $thiss = $('.departmentFilter .dropdown-item').filter(function () {
//                return $(this).text().indexOf(deparmentName) !== -1;
//            });
//
//            var departmentId = $thiss.attr('data-value');
//
//            var url = getURL('departmentalAssets/view') + "?action=searchAsset&key=" + $this.val() + "&departmentId=" + departmentId;
//
//            sendRequest(url, "GET", {}, "text",
//                    function (response) {
//                        var trs = $(response).find('.table tbody tr');
//                        $('.table tbody tr').detach();
//                        trs.appendTo('.table tbody');
//                        $('.assetTypeFilter').prev().text('Tất cả');
//                    },
//                    function (xhr) {
//                        showErrorMessage(xhr.reponseText);
//                    }
//            );
//        }
//    });
//}