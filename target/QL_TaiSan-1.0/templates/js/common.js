

// Hàm hiển thị lên thông báo
export function showMessage(message, titleClassName, contentClassName, buttonClassName) {
    $('#message-dialog-title').removeClass();
    $('#message-dialog-content').removeClass();
    $('#message-dialog-button').removeClass();
    $('#message-dialog-title').addClass(titleClassName);
    $('#message-dialog-content').addClass(contentClassName);
    $('#message-dialog-button').addClass(buttonClassName);
    $('#message-dialog-content').text(message);
    $('#message-dialog').modal('show');
}

// Hàm hiển thị lên thông báo thành công
export function showSuccessfulMessage(message) {
    showMessage(message, "text-success", 'text-success', 'btn btn-success');
}

// Hàm hiển thị lên thông báo lỗi
export function showErrorMessage(message) {
    showMessage(message, 'text-danger', 'text-danger', 'btn btn-danger');
}

// Hàm hiển thị thông báo thành công dành cho modal
export function showSuccessfulChildModal(message) {
    $("#message-dialog").css("z-index", parseInt($("#update_modal").css("z-index")) + 1);
    showSuccessfulMessage(message);
}

// Hàm hiển thị thông báo lỗi dành cho modal
export function showErrorChildModal(message) {
    $("#message-dialog").css("z-index", parseInt($("#update_modal").css("z-index")) + 1);
    showErrorMessage(message);
}

// Hàm gửi request
export function sendRequest(url, method, data, contentType, success, error) {
    $.ajax({
        url: url,
        method: method,
        data: data,
        contentType: contentType,
        success: success,
        error: error
    });
}

// Hàm lấy base64 encoded
export function getBase64String(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            resolve(reader.result);
        };
        reader.onerror = () => {
            reject(reader.error);
        };
    });
}

export function changePage(response) {
    $('html').html(response);
}

// Hàm lấy dữ liệu từ form
export function getFormData(formId) {
    let formData = new FormData($(formId)[0]);
    var data = {};
    for (let pair of formData.entries()) {
        data[pair[0]] = pair[1];
    }

    return data;
}

// Lấy url kết hợp với baseurl
export function getURL(url) {
    return "/QL_TaiSan/" + url;
}

// Lấy method từ form (put or post)
export function getMethod() {
    return $('#save-form').attr("method");
}

// Lấy dữ liệu json
export function getJsonData(data) {
    return JSON.stringify(data);
}

// Lấy contentType
export function getContentType(type) {
    if (type === "json") {
        return "application/json";
    }
    return "text";
}

// Hàm cho phép chọn ảnh và lưu dưới dạng base64 encoded
export function callInputImageEvent() {
    $('body').on('change', '#inputImageFile', async function () {
        if (!this.files[0].type.startsWith('image/')) {
            $('#inputImage').attr('src', "");
            return;
        }
        $('#inputImage').attr('src', await getBase64String(this.files[0]));
    });
    $('body').on('click', '#inputImage', function () {
        $('#inputImageFile').click();
    });
}

// Hàm định nghĩa datepicker cho phép thao tác với ngày tháng
export function callDatePickerEvent() {
    $('body').on('focus', 'input.datepicker', function () {
        $('.datepicker').datepicker({
            dateFormat: 'dd/mm/yy'
        });
    });


    $('body').on('focus', 'input.datetimepicker', function () {
        $(this).datetimepicker({
            dateFormat: 'dd/mm/yy',
            timeFormat: 'HH:mm',
            controlType: 'select',
            oneLine: true,
            stepMinute: 5
        });
    });
}

export function disabledButton(button) {
    $(button).attr('disabled', true);
}

export function getCurrentResource() {
    return window.location.href.split("/")[4];
}

// Hàm gọi đến trang add
export function callAddButtonEvent() {
    $('body').on('click', 'button[name="add"]', function () {
        let url = getCurrentResource() + "/add";
        const $this = $(this);
        sendRequest(getURL(url), "GET", {}, "text",
                function (response) {
                    if ($('body').find("#update_modal").length !== 0) {
                        $('body').find("#update_modal").remove();
                    }
                    $('body').append(response);
                    $("#update_modal").modal('show');
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}



// Load lại số bản ghi
export function reloadNumRecords(records, id) {
    const len = records.not('.emptyRow').length;
    $(id).text(len);
}

export function reloadButtonClick(callback1, callback2) {
    $('body').on('click', '.reloadBtn', function () {
        reloadData(callback1, callback2);
    });
}

export function destroyReloadButtonClick() {
    $('body').off('click', '.reloadBtn');
}

// Load lại dữ liệu
export function reloadData(callback1, callback2) {
    let url = getCurrentResource() + "/view";

    if (typeof callback1 === 'function') {
        url += callback1();
    }

    sendRequest(getURL(url), "GET", {}, {},
            function (response) {
                $('.content-wrapper table tbody tr').detach();
                var records = $(response).find('.content-wrapper .table tbody tr');
                records.appendTo($('.content-wrapper .table tbody'));
                reloadNumRecords(records, "#numRecords");
                reloadFilter();
                reloadInputSearch();
                reloadDatePicker();

                if (typeof callback2 === 'function') {
                    callback2();
                }
            },
            function (xhr) {
                showErrorMessage(xhr.responseText);
            }
    );
}

// Hàm lưu và tạo mới
export function saveAndNew(data) {
    const url = getCurrentResource() + "/edit";
    sendRequest(getURL(url), getMethod(), getJsonData(data), getContentType("json"),
            function () {
                $('#update_modal').modal('hide');
                $("#update_modal").on("hidden.bs.modal", function () {
                    $('button[name="add"]').click();
                });
                reloadData();
            },
            function (xhr) {
                $("#message-dialog").css("z-index", parseInt($("#update_modal").css("z-index")) + 1);
                showErrorChildModal(xhr.responseText);
            }
    );
}

// Lưu và đóng
export function saveAndClose(data) {
    const url = getCurrentResource() + "/edit";
    const reloadURL = getCurrentResource() + "/view";

    sendRequest(getURL(url), getMethod(), getJsonData(data), getContentType("json"),
            function () {
                $('#update_modal').modal('hide');
                reloadData();
            },
            function (xhr) {
                $("#message-dialog").css("z-index", parseInt($("#update_asset").css("z-index")) + 1);
                showErrorChildModal(xhr.responseText);
            }
    );
}

export function callRemoveModal() {
    $('body').on('hidden.bs.modal', '#update_modal', function () {
        $(this).remove();
    });
}
// Hàm gọi đén trang update
export function callTableRowClickEvent() {
    $('body').on('click', '.content-wrapper .table tbody tr:not(.emptyRow)', function () {
        const oldURL = getCurrentResource() + "/view";
        var tr = $(this);
        var id = tr.find("td:first").text();
        var url = oldURL + "?id=" + id;
        sendRequest(getURL(url), "GET", {}, "text",
                function (response) {
                    $('body').append(response);
                    $("#update_modal").modal('show');
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}

export function showModalEvent(callback) {
    $('body').on('shown.bs.modal', '.modal', function () {
        if (typeof callback === "function") {
            callback();
        }
    });
}

export function destroyShowModalEvent() {
    $('body').off('shown.bs.modal', '.modal');
}

export function destroyTableRowClickEvent() {
    $('body').off('click', '.content-wrapper .table tbody tr:not(.emptyRow)');
}

// Load lại tên filter
export function reloadFilter() {
    $('.filter').prev().text("Tất cả");
}

export function reloadInputSearch() {
    $('body').find('#search-input').val('');
}

export function reloadDatePicker() {
    $('body').find('#startDate').val('');
    $('body').find('#endDate').val('');
}

export function dateFilter() {
    var format = "DD/MM/YYYY HH:mm";
    var startDate = moment($('body').find("#startDate").val(), format);
    var endDate = moment($('body').find("#endDate").val(), format);

    if (!startDate.isValid()) {
        startDate = moment("1/1/0001 00:00", format);
    }

    if (!endDate.isValid()) {
        endDate = moment("31/12/9999 23:59", format);
    }

    var trs = $('body').find('.table-wrapper table tbody tr:not(.emptyRow)');

    trs.each(function () {
        var date = moment($(this).find("td.dateIndex").text(), format);

        if (date.isBetween(startDate, endDate, null, "[]")) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    var records = trs.filter(function () {
        return $(this).css('display') !== 'none';
    });

    reloadNumRecords(records, "#numRecords");
}


export function nameFilter() {
    let filterName = $('body').find('.filter').prev().text();

    const tempName = (filterName === "Tất cả") ? "" : filterName;

    $('body').find('.filter').prev().text(filterName);

    $('body').find('.content-wrapper .table tbody tr:not(.emptyRow)').each(function () {
        const name = $(this).find("td.index").text();
        if (name.includes(tempName)) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    const records = $('body').find('.content-wrapper .table tbody tr:not(.emptyRow)').filter(function () {
        return $(this).css('display') !== 'none';
    });

    reloadNumRecords(records, "#numRecords");
}

export function dateAndNameFilter() {
    let filterName = $('body').find('.filter').prev().text();
    var format = "DD/MM/YYYY";
    var startDate = moment($('body').find("#startDate").val(), format);
    var endDate = moment($('body').find("#endDate").val(), format);

    if (!startDate.isValid()) {
        startDate = moment("1/1/0001", format);
    }

    if (!endDate.isValid()) {
        endDate = moment("31/12/9999", format);
    }

    var trs = $('body').find('.table-wrapper table tbody tr:not(.emptyRow)');

    let tempName = (filterName === "Tất cả") ? '' : filterName;

    trs.each(function () {
        var name = $(this).find("td.index").text();
        var date = moment($(this).find("td.dateIndex").text(), format);

        if (name.includes(tempName) && date.isBetween(startDate, endDate, "day", "[]")) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    var records = trs.filter(function () {
        return $(this).css('display') !== 'none';
    });

    reloadNumRecords(records, "#numRecords");
}

export function callFilterEvent() {
    $('body').on('click', '.filter .dropdown-item', function () {
        $(this).parent().prev().text($(this).text());
        if ($('body').find('#startDate').length === 0) {
            nameFilter();
        } else {
            dateAndNameFilter();
        }
    });

    $('body').on('change', '#startDate, #endDate', function () {
        if ($('body').find('.filter').length === 0) {
            dateFilter();
        } else {
            dateAndNameFilter();
        }
    });

//    $('body').on('click', '.filter .dropdown-item', function () {
//        $(this).parent().prev().text($(this).text());
//        dateAndNameFilter();
//    });
//
//    $('body').on('change', '#startDate, #endDate', function () {
//        dateAndNameFilter();
//    });
}

// Hàm cho phép tìm kiếm
export function callInputSearchEvent(callback1) {
    $('body').on('keypress', '#search-input', function (e) {
        if (e.keyCode === 13) {
            let oldURL = getCurrentResource() + "/view";
            var searchText = $(this).val();
            var data = {
                action: "search",
                key: searchText
            };
            var url = oldURL + "?" + $.param(data);

            if (typeof callback1 === 'function') {
                url = oldURL + "?action=searchAsset&key=" + searchText + callback1();
            }

            sendRequest(getURL(url), "GET", {}, "text",
                    function (response) {
                        $('.table tbody tr').detach();
                        var records = $(response).find('.table tbody tr');
                        records.appendTo($('.table tbody'));

                        reloadNumRecords(records, "#numRecords");
                        reloadFilter();
                        reloadDatePicker();
                    },
                    function (xhr) {
                        showErrorMessage(xhr.responseText);
                    }
            );
        }
    });
}

export function destroyInputSearchEvent() {
    $('body').off('keypress', '#search-input');
}

function checkEmptyTable(tbody) {
    const rowCount = $(tbody).find('tr:not(.emptyRow)').length;
    if (rowCount === 0) {
        $(tbody).find('.emptyRow').removeClass('d-none');
    } else {
        $(tbody).find('.emptyRow').addClass('d-none');
    }
}

export function emptyDropdownMenuEvent() {
    $('body').on('change', '.table tbody', function () {
        const rowCount = $('.table tbody tr:not(.emptyRow)').length;

        if (rowCount === 0) {
            $('.emptyRow').show();
        } else {
            $('.emptyRow').hide();
        }
    });
}

export function destroySearchDropdownMenuEvent() {
    $('body').off("input", ".modal input.action");
}

// Hàm cho phép tìm kiếm các dữ liệu hiển thị dưới dạng dropdown
export function callSearchDropdownMenuEvent(callback) {
    var timer;
    $('body').on("input", ".modal input.action", function () {
        const action = $(this).attr('action');

        if (typeof callback === 'function') {
            if (callback(action) === false) {
                return false;
            }
        }

        const oldURL = getCurrentResource() + "/view";

        var parent = $(this).parent();
        var dropdownMenu = parent.find('.dropdown-menu');
        var dropdownItem = dropdownMenu.find('.dropdown-item:not(.emptyRow)');

        $(this).attr('data-value', "");

        const value = $(this).val();

        if (value.length > 0) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                var url = oldURL + "?action=" + action + "&key=" + value;

                if (typeof callback === 'function') {
                    if (action === 'searchAsset') {
                        const departmentId = $('input.action').filter(function () {
                            return $(this).attr('action') === "searchDepartment";
                        }).attr('data-value');

                        url += "&departmentId=" + departmentId;
                    }
                }

                sendRequest(getURL(url), "GET", {}, "text",
                        function (response) {
                            dropdownItem.detach();

                            var responseDropdownItem = $(response).find('input.action').filter(function () {
                                return $(this).attr('action') === action;
                            }).next().find('.dropdown-item:not(.emptyRow)');

                            if ($(responseDropdownItem).length === 0) {
                                $(dropdownMenu).find('.emptyRow').show();
                            } else {
                                $(dropdownMenu).find('.emptyRow').hide();
                                $(responseDropdownItem).appendTo($(dropdownMenu));
                            }
                            $(dropdownMenu).addClass('show');
                        },
                        function (xhr) {
                            showErrorMessage(xhr.reponseText);
                        }
                );
            }, 500);
        } else {
            $(dropdownItem).detach();
            $(dropdownMenu).removeClass('show');
        }
    });
}

export function chooseItemFromDropdownMenu() {
    $('body').on('click', '.modal .dropdown-item:not(.table-item)', function () {
        var id = $(this).find('span').first().attr('data-value');
        var name = $(this).find('span').eq(1).attr('data-value');
        var dropdownMenu = $(this).parent();

        var searchInput = $(dropdownMenu).prev();
        $(searchInput).attr('data-value', id);
        $(searchInput).val(name);
        if (dropdownMenu.attr('display-id') === "true") {
            $(searchInput).val(id);
        }

        dropdownMenu.find('.dropdown-item').detach();
        dropdownMenu.removeClass('show');
    });
}

export function destroyChooseItemFromDropdownMenu() {
    $('body').off('click', '.modal .dropdown-item:not(.table-item)');
}

export function destroyActionButtonClickEvent() {
    $('body').off('click', 'button.action');
}

export function callTableInputEvent(callback) {
    $('body').on("input", ".modal table input", function () {
        callback(this);
    });
}

export function destroyTableInputEvent() {
    $('body').off("input", ".modal table input");
}

export function callDeleteTableItemClickEvent(callback) {
    $('body').on("click", ".modal table button", function (e) {
        const tbody = $(e.target.closest('tr')).parent();
        $(e.target.closest('tr')).remove();
        callback(e);
        checkEmptyTable(tbody);
        reloadNumRecords($('.modal table tbody tr'), "#modalNumRecords");
    });
}

export function destroyDeleteTableItemClickEvent() {
    $('body').off("click", ".modal table button");
}

export function validNumberTypeInput() {
    $('body').on('input', 'input.checkNumberType', function (e) {
        const $this = $(this);

        let val = $this.val();
        let newVal = val.replace(/[^0-9]/g, "");

        if (newVal !== val) {
            event.target.value = newVal;
            return false;
        }

        const max = parseInt($this.attr('max'));
        const min = parseInt($this.attr('min'));

        newVal = (newVal > max) ? max : (newVal < min) ? min : newVal;

        $this.val(newVal);
    });
}

export function callTableItemClickEvent(callback1, callback2) {
    $('body').on('click', '.modal .dropdown-item.table-item', function () {
        var key = $(this).find('span').first().attr('data-value');

        if (typeof callback1 === "function") {
            if (callback1(this) === false) {
                return false;
            }
        }

        var dropdownMenu = $(this).parent();
        dropdownMenu.find('.dropdown-item').detach();
        dropdownMenu.removeClass('show');
        dropdownMenu.prev().val('');
        let url = getCurrentResource() + "/view?action=addItem&key=" + key;

        if (getCurrentResource() === "assetDispose") {
            const departmentId = $('input.action').filter(function () {
                return $(this).attr('action') === "searchDepartment";
            }).attr('data-value');

            url += "&departmentId=" + departmentId;
        }

        sendRequest(getURL(url), "GET", {}, "",
                function (response) {
                    var flag = false;
                    $('.modal table tbody').find('tr').each(function () {

                        // Nếu tài sản được chọn đã tồn tại trong dropdown thì tăng số lượng + 1
                        // và tình toán các ô nhập liệu
                        if ($(this).find('button').attr('data-value') === key) {
                            flag = true;
                            return false;
                        }
                    });

                    // Nếu tài sản được chọn chưa tồn tại thì append vào table
                    // và set lại các ô input trong table cũng như trong form
                    if (flag) {
                        return false;
                    }

                    $(response).find('table tbody tr:not(.emptyRow)').appendTo($('.modal table'));

                    if (typeof callback2 === "function") {
                        callback2();
                    }

                    checkEmptyTable($('.modal table tbody'));
                    reloadNumRecords($('.modal table tbody tr:not(.emptyRow)'), "#modalNumRecords");
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}

export function destroyTableItemClickEvent() {
    $('body').off('click', '.modal .dropdown-item.table-item');
}