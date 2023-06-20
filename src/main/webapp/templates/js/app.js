import * as common_module from './common.js';
import * as sidebar_module from './sidebar.js';
import * as asset_module from './assets.js';
import * as asset_type_module from './asset_types.js';
import * as invoice_module from './invoices.js';
import * as inventory_transaction_module from './inventory_transactions.js';
import * as asset_transfer_module from './asset_transfer.js';
import * as asset_dispose_module from './asset_dispose.js';
import * as departmental_asset_module from './departmental_assets.js';
import * as depreciation_module from './depreciation.js';
import * as accounting_period_module from './accounting_periods.js';
import * as department_module from './departments.js';
import * as user_module from './users.js';
import * as account_module from './accounts.js';
import * as authorization_module from './authorization.js';
import * as supplier_module from './suppliers.js';


const module_array = [
    {"assets": asset_module},
    {"assetTypes": asset_type_module},
    {"invoices": invoice_module},
    {"inventoryTransactions": inventory_transaction_module},
    {"assetTransfer": asset_transfer_module},
    {"assetDispose": asset_dispose_module},
    {"departmentalAssets": departmental_asset_module},
    {"depreciation": depreciation_module},
    {"accountingPeriods": accounting_period_module},
    {"departments": department_module},
    {"users": user_module},
    {"accounts": account_module},
    {"authorization": authorization_module},
    {"suppliers": supplier_module}
];

common_module.callDatePickerEvent(); // always
common_module.callAddButtonEvent(); // always
common_module.callInputImageEvent(); // always
common_module.validNumberTypeInput(); // always
common_module.callRemoveModal(); // always
common_module.callFilterEvent(); // always
account_module.permissionEvent(); // always

let isTableRowClickEventCalled = false;

function check() {
    const resource = common_module.getCurrentResource();
    const tableNotClick = ["departmentalAssets", "inventory", "accountingPeriods", "accountingPeriodTypes", "depreciationMethods"];

    if (tableNotClick.indexOf(resource) !== -1) {
        common_module.destroyTableRowClickEvent();
        isTableRowClickEventCalled = false;
    } else {
        if (!isTableRowClickEventCalled) {
            common_module.callTableRowClickEvent();
            isTableRowClickEventCalled = true;
        }
    }

    if (resource === "inventoryTransactions") {
        // Hủy sự kiện chọn tài sản từ dropdown vào table và set mới
        common_module.destroyTableItemClickEvent();
        common_module.callTableItemClickEvent(null, inventory_transaction_module.addTableItemCallback);
        common_module.destroyTableInputEvent();
        common_module.callTableInputEvent(inventory_transaction_module.tableInputCallback);
        common_module.destroyDeleteTableItemClickEvent();
        common_module.callDeleteTableItemClickEvent(inventory_transaction_module.deleteTableItemCallback);

        common_module.destroyShowModalEvent();
        common_module.showModalEvent(inventory_transaction_module.showModalCallback);

        common_module.destroySearchDropdownMenuEvent();
        common_module.callSearchDropdownMenuEvent();
    } else if (resource === "assetTransfer") {
        // Hủy sự kiện chọn tài sản từ dropdown vào table và set mới
        common_module.destroyTableItemClickEvent();
        common_module.callTableItemClickEvent(asset_transfer_module.addTableItemCallback, null);
        common_module.destroyTableInputEvent();
        common_module.callTableInputEvent(asset_transfer_module.tableInputCallback);
        common_module.destroyDeleteTableItemClickEvent();
        common_module.callDeleteTableItemClickEvent(asset_transfer_module.deleteTableItemCallback);
        common_module.destroySearchDropdownMenuEvent();
        common_module.callSearchDropdownMenuEvent();
        common_module.destroyChooseItemFromDropdownMenu(); //alt
        common_module.chooseItemFromDropdownMenu(); // alt

        common_module.destroyShowModalEvent();
    } else if (resource === "assetDispose") {
        // Hủy sự kiện chọn tài sản từ dropdown vào table và set mới
        common_module.destroyTableItemClickEvent();
        common_module.callTableItemClickEvent(asset_dispose_module.addTableItemCallback, null);
        common_module.destroyTableInputEvent();
        common_module.callTableInputEvent(asset_dispose_module.tableInputCallback);
        common_module.destroyDeleteTableItemClickEvent();
        common_module.callDeleteTableItemClickEvent(asset_dispose_module.deleteTableItemCallback);
        common_module.destroySearchDropdownMenuEvent();
        common_module.callSearchDropdownMenuEvent(asset_dispose_module.callback);
        common_module.destroyChooseItemFromDropdownMenu(); //alt
        common_module.chooseItemFromDropdownMenu(); // alt

        common_module.destroyShowModalEvent();
    } else if (resource === "departmentalAssets") {
        common_module.destroyReloadButtonClick();
        common_module.reloadButtonClick(departmental_asset_module.reloadButtonCallback1, departmental_asset_module.reloadButtonCallback2);

        departmental_asset_module.searchDepartmentalAsset();

        common_module.destroyInputSearchEvent();
        common_module.callInputSearchEvent(departmental_asset_module.callInputSearchCallback);
    } else if (resource === "depreciation") {
        common_module.destroyChooseItemFromDropdownMenu();
        depreciation_module.chooseItemFromDropdownMenu();

        common_module.destroySearchDropdownMenuEvent();
        common_module.callSearchDropdownMenuEvent();

        common_module.destroyInputSearchEvent();
        common_module.callInputSearchEvent();
    } else {
        common_module.destroySearchDropdownMenuEvent(); // alt
        common_module.callSearchDropdownMenuEvent(); // alt

        common_module.destroyChooseItemFromDropdownMenu(); //alt
        common_module.chooseItemFromDropdownMenu(); // alt

        common_module.destroyShowModalEvent(); // alt

        common_module.destroyReloadButtonClick(); //alt
        common_module.reloadButtonClick(); // alt

        common_module.destroyInputSearchEvent(); // alt
        common_module.callInputSearchEvent(); // alt

    }
}

$(document).on('routeChange', function () {
    check();
});

$(document).ready(function () {
    check();
});

// handle add-update action
$('body').on('click', '.modal button.action', function () {
    const $this = $(this);
    const resource = common_module.getCurrentResource();
    for (const module of module_array) {
        for (const [key, value] of Object.entries(module)) {
            if (resource === key) {
                const data = value.getData();
                if ($this.attr('action') === "saveAndClose") {
                    common_module.saveAndClose(data);
                } else {
                    common_module.saveAndNew(data);
                }
                return false;
            }
        }
    }
});

for (const prop in sidebar_module) {
    if (typeof sidebar_module[prop] === 'function') {
        sidebar_module[prop]();
    }
}