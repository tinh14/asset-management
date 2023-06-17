import {getFormData} from './common.js';

// Lấy thành tiền và tổng tiền từ hàng
function getSubTotalAndTotalFromRow($row) {
    var price = parseFloat($row.find('input').eq(0).val());
    var quantity = parseInt($row.find('input').eq(1).val());
    var VAT = parseFloat($row.find('input').eq(2).val());
    var subTotal = price * quantity;
    var vatTotal = Math.round(subTotal * VAT / 100);
    var total = subTotal + vatTotal;
    return [subTotal, total];
}

// Set thành tiền và tổng tiền vào ô input
function setSubTotalAndTotalToRow(tr) {
    var $row = $(tr);
    var val = getSubTotalAndTotalFromRow($row);
    $row.find('td').eq(-3).text(val[0]);
    $row.find('td').eq(-2).text(val[1] - val[0]);
    $row.find('td').eq(-1).text(val[1]);
}

// Lấy tổng thành tiền và tổng tiền từ các row
function getAllSubTotalAndTotal() {
    var val = [0, 0];
    $('body .modal table tbody tr:not(.emptyRow)').each(function () {
        var temp = getSubTotalAndTotalFromRow($(this));
        val[0] += temp[0];
        val[1] += temp[1];
    });
    return val;
}

// Đặt tiền VAT, thành tiền, tổng tiền vào label cuối trang
function setSubTotalAndTotalToInput(val) {
    $('#total').text(val[1]);
    $('#subTotal').text(val[0]);
    $('#vat').text(val[1] - val[0]);
}

export function showModalCallback() {
    $('body').find('.modal .table tbody tr:not(.emptyRow)').each(function () {
        setSubTotalAndTotalToRow(this);
    });
    setSubTotalAndTotalToInput(getAllSubTotalAndTotal());
}

export function addTableItemCallback() {
    setSubTotalAndTotalToRow($('.table').find('tr:not(.emptyRow)').last());
    setSubTotalAndTotalToInput(getAllSubTotalAndTotal());
}

export function tableInputCallback(input) {
    var row = $(input).closest('tr');
    setSubTotalAndTotalToRow(row);
    setSubTotalAndTotalToInput(getAllSubTotalAndTotal());
}

export function deleteTableItemCallback(e) {
    var row = $(e.target.closest('tr'));
    setSubTotalAndTotalToRow(row);
    setSubTotalAndTotalToInput(getAllSubTotalAndTotal());
}

export function getData() {
    const data = getFormData("#save-form");
    data["warehouseReceiverId"] = $('input[name="warehouseReceiverId"]').attr("data-value");
    data["invoiceId"] = (data["invoiceId"] === "") ? null : data["invoiceId"];
    var inventoryTransactionDetailList = {
        "inventoryTransactionDetailList": []
    };

    data["id"] = (data["id"] === "") ? null : data["id"];

    $('.modal .table tbody tr:not(.emptyRow)').each(function () {
        var row = $(this);

        var assetId = row.find('button').attr("data-value");
        var price = row.find('input').eq(0).val();
        var quantity = row.find('input').eq(1).val();
        var VAT = row.find('input').eq(2).val();

        inventoryTransactionDetailList.inventoryTransactionDetailList.push({
            "inventoryTransactionId": data["id"],
            "assetId": assetId,
            "quantity": quantity,
            "price": price,
            "VAT": VAT
        });
    });
    data["inventoryTransactionDetailList"] = inventoryTransactionDetailList.inventoryTransactionDetailList;

    return data;
}