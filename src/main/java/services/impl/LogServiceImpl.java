/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import constants.Constants;
import daos.interfaces.ActionDAO;
import daos.interfaces.AssetDAO;
import daos.interfaces.LogAssetDetailDAO;
import daos.interfaces.LogDAO;
import daos.interfaces.LogDetailDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AccountModel;
import models.AccountingPeriodModel;
import models.ActionModel;
import models.AssetDisposeDetailModel;
import models.AssetDisposeModel;
import models.AssetModel;
import models.AssetTransferDetailModel;
import models.AssetTransferModel;
import models.AssetTypeModel;
import models.DepartmentModel;
import models.DepreciationDetailModel;
import models.DepreciationModel;
import models.InventoryTransactionDetailModel;
import models.InventoryTransactionModel;
import models.InvoiceModel;
import models.LogAssetDetailModel;
import models.LogDetailModel;
import models.LogModel;
import models.PersonModel;
import models.ResourceModel;
import models.SupplierModel;
import models.UserModel;
import services.interfaces.LogService;
import services.interfaces.UserService;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public class LogServiceImpl implements LogService {

    @Inject
    private LogDAO logDAO;

    @Inject
    private LogDetailDAO logDetailDAO;

    @Inject
    private LogAssetDetailDAO logAssetDetailDAO;

    @Inject
    private ActionDAO actionDAO;

    @Inject
    private UserService userService;

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<LogModel> findAll() {
        List<LogModel> logList = logDAO.findAll();
        for (LogModel log : logList) {
            log.setAction(actionDAO.findByIdAndResourceId(log.getAction().getId(), log.getAction().getResource().getId()).get(0));
            log.setUser(userService.findByPersonId(log.getUser().getId()).get(0));
        }
        return logList;
    }

    @Override
    public List<LogModel> findByLogId(int id) {
        List<LogModel> logList = logDAO.findById(id);
        for (LogModel log : logList) {
            log.setLogDetailList(logDetailDAO.findByLogId(log.getId()));
            log.setLogAssetDetailList(logAssetDetailDAO.findByLogId(log.getId()));
        }
        return logList;
    }

    private LogModel getCommonLogData(HttpServletRequest req) {
        String resourceId = (String) req.getAttribute("resourceId");
        String actionId = (String) req.getAttribute("actionId");
        int userId = ((PersonModel) req.getSession().getAttribute("sessionPerson")).getId();

        LogModel log = new LogModel();

        log.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        log.setUser(new UserModel(userId));
        ActionModel action = new ActionModel(actionId);
        action.setResource(new ResourceModel(resourceId));
        log.setAction(action);

        return log;
    }

    private LogModel getLogDataFromAsset(HttpServletRequest req, Object oldObject, Object newObject) {

        LogModel log = getCommonLogData(req);

        AssetModel newAsset = (AssetModel) newObject;

        log.setRecordId(String.valueOf(newAsset.getId()));

        List<LogDetailModel> logDetailList;

        AssetModel oldAsset = (AssetModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldAsset.getId());
        String name = (oldObject == null) ? "-" : String.valueOf(oldAsset.getName());
        String weight = (oldObject == null) ? "-" : String.valueOf(oldAsset.getWeight());
        String unit = (oldObject == null) ? "-" : String.valueOf(oldAsset.getUnit());
        String price = (oldObject == null) ? "-" : String.valueOf(oldAsset.getPrice());
        String percentageDepreciation = (oldObject == null) ? "-" : String.valueOf(oldAsset.getPercentageDepreciation());
        String assetType = (oldObject == null) ? "-" : String.valueOf(oldAsset.getAssetType().getName());
        String image = (oldObject == null) ? "-" : String.valueOf(oldAsset.getImage());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã tài sản", id, String.valueOf(newAsset.getId()), log),
                new LogDetailModel(-1, "Tên tài sản", name, String.valueOf(newAsset.getName()), log),
                new LogDetailModel(-1, "Khối lượng", weight, String.valueOf(newAsset.getWeight()), log),
                new LogDetailModel(-1, "Đơn vị tính", unit, String.valueOf(newAsset.getUnit()), log),
                new LogDetailModel(-1, "Giá nhập", price, String.valueOf(newAsset.getPrice()), log),
                new LogDetailModel(-1, "Phần trăm khấu hao", percentageDepreciation, String.valueOf(newAsset.getPercentageDepreciation()), log),
                new LogDetailModel(-1, "Loại", assetType, String.valueOf(newAsset.getAssetType().getName()), log),
                new LogDetailModel(-1, "Ảnh", image, String.valueOf(newAsset.getImage()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromAssetType(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        AssetTypeModel newAssetType = (AssetTypeModel) newObject;

        log.setRecordId(String.valueOf(newAssetType.getId()));

        List<LogDetailModel> logDetailList;

        AssetTypeModel oldAssetType = (AssetTypeModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldAssetType.getId());
        String name = (oldObject == null) ? "-" : String.valueOf(oldAssetType.getName());
        String weight = (oldObject == null) ? "-" : String.valueOf(oldAssetType.getDepreciationPeriod());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã loại tài sản", id, String.valueOf(newAssetType.getId()), log),
                new LogDetailModel(-1, "Tên loại tài sản", name, String.valueOf(newAssetType.getName()), log),
                new LogDetailModel(-1, "Thời gian trích khấu hao", weight, String.valueOf(newAssetType.getDepreciationPeriod()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromDepartment(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        DepartmentModel newDeparmtent = (DepartmentModel) newObject;

        log.setRecordId(String.valueOf(newDeparmtent.getId()));

        List<LogDetailModel> logDetailList;

        DepartmentModel oldDeparment = (DepartmentModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldDeparment.getId());
        String name = (oldObject == null) ? "-" : String.valueOf(oldDeparment.getName());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã phòng ban", id, String.valueOf(newDeparmtent.getId()), log),
                new LogDetailModel(-1, "Tên phòng ban", name, String.valueOf(newDeparmtent.getName()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromUser(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        UserModel newUser = (UserModel) newObject;

        log.setRecordId(String.valueOf(newUser.getId()));

        List<LogDetailModel> logDetailList;

        UserModel oldUser = (UserModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldUser.getId());
        String dateOfBirth = (oldObject == null) ? "-" : String.valueOf(oldUser.getDateOfBirth());
        String lastName = (oldObject == null) ? "-" : String.valueOf(oldUser.getLastName());
        String firstName = (oldObject == null) ? "-" : String.valueOf(oldUser.getFirstName());
        String deparment = (oldObject == null) ? "-" : String.valueOf(oldUser.getDepartment().getName());
        String address = (oldObject == null) ? "-" : String.valueOf(oldUser.getAddress());
        String account = (oldObject == null) ? "-" : String.valueOf(oldUser.getAccount().getUsername());
        String avatar = (oldObject == null) ? "-" : String.valueOf(oldUser.getAvatar());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã người dùng", id, String.valueOf(newUser.getId()), log),
                new LogDetailModel(-1, "Ngày sinh", dateOfBirth, String.valueOf(newUser.getDateOfBirth()), log),
                new LogDetailModel(-1, "Họ", lastName, String.valueOf(newUser.getLastName()), log),
                new LogDetailModel(-1, "Tên", firstName, String.valueOf(newUser.getFirstName()), log),
                new LogDetailModel(-1, "Phòng ban", deparment, String.valueOf(newUser.getDepartment().getName()), log),
                new LogDetailModel(-1, "Địa chỉ", address, String.valueOf(newUser.getAddress()), log),
                new LogDetailModel(-1, "Tài khoản", account, String.valueOf(newUser.getAccount().getUsername()), log),
                new LogDetailModel(-1, "Ảnh", avatar, String.valueOf(newUser.getAvatar()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromAccount(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        AccountModel newAccount = (AccountModel) newObject;

        log.setRecordId(newAccount.getUsername());

        List<LogDetailModel> logDetailList;

        AccountModel oldAccount = (AccountModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldAccount.getUsername());
        String status = (oldObject == null) ? "-" : String.valueOf(oldAccount.getStatus());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Tên tài khoản", id, String.valueOf(newAccount.getUsername()), log),
                new LogDetailModel(-1, "Trạng thái", status, String.valueOf(newAccount.getStatus()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromSupplier(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        SupplierModel newSupplier = (SupplierModel) newObject;

        log.setRecordId(String.valueOf(newSupplier.getId()));

        List<LogDetailModel> logDetailList;

        SupplierModel oldSupplier = (SupplierModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldSupplier.getId());
        String name = (oldObject == null) ? "-" : String.valueOf(oldSupplier.getName());
        String phoneNumber = (oldObject == null) ? "-" : String.valueOf(oldSupplier.getPhoneNumber());
        String address = (oldObject == null) ? "-" : String.valueOf(oldSupplier.getAddress());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã nhà cung cấp", id, String.valueOf(newSupplier.getId()), log),
                new LogDetailModel(-1, "Tên nhà cung cấp", name, String.valueOf(newSupplier.getName()), log),
                new LogDetailModel(-1, "Số điện thoại", phoneNumber, String.valueOf(newSupplier.getPhoneNumber()), log),
                new LogDetailModel(-1, "Địa chỉ", address, String.valueOf(newSupplier.getAddress()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromInvoice(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        InvoiceModel newInvoice = (InvoiceModel) newObject;

        log.setRecordId(String.valueOf(newInvoice.getId()));

        List<LogDetailModel> logDetailList;

        InvoiceModel oldInvoice = (InvoiceModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldInvoice.getId());
        String number = (oldObject == null) ? "-" : String.valueOf(oldInvoice.getNumber());
        String invoiceDate = (oldObject == null) ? "-" : String.valueOf(oldInvoice.getInvoiceDate());
        String supplier = (oldObject == null) ? "-" : String.valueOf(oldInvoice.getSupplier().getName());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã hóa đơn", id, String.valueOf(newInvoice.getId()), log),
                new LogDetailModel(-1, "Số hóa đơn", number, String.valueOf(newInvoice.getNumber()), log),
                new LogDetailModel(-1, "Ngày lập hóa đơn", invoiceDate, String.valueOf(newInvoice.getInvoiceDate()), log),
                new LogDetailModel(-1, "Nhà cung cấp", supplier, String.valueOf(newInvoice.getSupplier().getName()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromInventoryTransaction(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);
        InventoryTransactionModel newInventoryTransaction = (InventoryTransactionModel) newObject;
        List<LogDetailModel> logDetailList;
        List<LogAssetDetailModel> logAssetDetailList = new ArrayList<>();

        log.setRecordId(String.valueOf(newInventoryTransaction.getId()));

        InventoryTransactionModel oldInventoryTransaction;

        if (oldObject == null) {
            oldInventoryTransaction = new InventoryTransactionModel();
            oldInventoryTransaction.setInventoryTransactionDetailList(new ArrayList<InventoryTransactionDetailModel>());
        } else {
            oldInventoryTransaction = (InventoryTransactionModel) oldObject;
        }

        String id = (oldObject == null) ? "-" : String.valueOf(oldInventoryTransaction.getId());
        String receiptDate = (oldObject == null) ? "-" : String.valueOf(oldInventoryTransaction.getReceiptDate());
        String warehouseReceiver = (oldObject == null) ? "-" : String.valueOf(oldInventoryTransaction.getWarehouseReceiver().getId() + " - " + oldInventoryTransaction.getWarehouseReceiver().getFullName());
        String invoice = (oldObject == null) ? "-" : String.valueOf(oldInventoryTransaction.getInvoice().getId());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã nhập kho", id, String.valueOf(newInventoryTransaction.getId()), log),
                new LogDetailModel(-1, "Ngày nhập kho", receiptDate, String.valueOf(newInventoryTransaction.getReceiptDate()), log),
                new LogDetailModel(-1, "Người nhập kho", warehouseReceiver, String.valueOf(newInventoryTransaction.getWarehouseReceiver().getId() + " - " + newInventoryTransaction.getWarehouseReceiver().getFullName()), log),
                new LogDetailModel(-1, "Mã hóa đơn tham chiếu", invoice, String.valueOf(newInventoryTransaction.getInvoice().getId()), log)
        ));

        Map<Integer, InventoryTransactionDetailModel> oldMap = new HashMap<>();
        Map<Integer, InventoryTransactionDetailModel> newMap = new HashMap<>();

        for (InventoryTransactionDetailModel detail : oldInventoryTransaction.getInventoryTransactionDetailList()) {
            oldMap.put(detail.getAsset().getId(), detail);
        }

        for (InventoryTransactionDetailModel detail : newInventoryTransaction.getInventoryTransactionDetailList()) {
            detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            newMap.put(detail.getAsset().getId(), detail);
        }

        for (Map.Entry<Integer, InventoryTransactionDetailModel> entry : oldMap.entrySet()) {
            int key = entry.getKey();
            InventoryTransactionDetailModel oldDetail = entry.getValue();

            InventoryTransactionDetailModel newDetail = newMap.get(key);

            String image1 = oldDetail.getAsset().getImage();
            String name1 = oldDetail.getAsset().getName();
            String price1 = String.valueOf(oldDetail.getPrice());
            String quantity1 = String.valueOf(oldDetail.getQuantity());
            String vat1 = String.valueOf(oldDetail.getVAT());

            String image2 = "-";
            String name2 = "-";
            String price2 = "-";
            String quantity2 = "-";
            String vat2 = "-";

            if (newDetail != null) {
                // Detail được cập nhật, so sánh chi tiết và ghi nhật ký
                image2 = newDetail.getAsset().getImage();
                name2 = newDetail.getAsset().getName();
                price2 = String.valueOf(newDetail.getPrice());
                quantity2 = String.valueOf(newDetail.getQuantity());
                vat2 = String.valueOf(newDetail.getVAT());
            }

            logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên mặt hàng", name1, name2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn giá", price1, price2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng", quantity1, quantity2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "VAT", vat1, vat2, log));
        }

        for (Map.Entry<Integer, InventoryTransactionDetailModel> newEntry : newMap.entrySet()) {
            Integer key = newEntry.getKey();
            InventoryTransactionDetailModel newDetail = newEntry.getValue();

            if (oldMap.get(key) == null) {
                // Detail mới đã được thêm, ghi nhật ký
                String image1 = "-";
                String name1 = "-";
                String price1 = "-";
                String quantity1 = "-";
                String vat1 = "-";

                String image2 = newDetail.getAsset().getImage();
                String name2 = newDetail.getAsset().getName();
                String price2 = String.valueOf(newDetail.getPrice());
                String quantity2 = String.valueOf(newDetail.getQuantity());
                String vat2 = String.valueOf(newDetail.getVAT());

                logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên mặt hàng", name1, name2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn giá", price1, price2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng", quantity1, quantity2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "VAT", vat1, vat2, log));
            }
        }
        log.setLogDetailList(logDetailList);
        log.setLogAssetDetailList(logAssetDetailList);
        return log;
    }

    private LogModel getLogDataFromAssetTransfer(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);
        AssetTransferModel newAssetTransfer = (AssetTransferModel) newObject;
        List<LogDetailModel> logDetailList;
        List<LogAssetDetailModel> logAssetDetailList = new ArrayList<>();

        log.setRecordId(String.valueOf(newAssetTransfer.getId()));

        AssetTransferModel oldAssetTransfer;

        if (oldObject == null) {
            oldAssetTransfer = new AssetTransferModel();
            oldAssetTransfer.setAssetTransferDetailList(new ArrayList<AssetTransferDetailModel>());
        } else {
            oldAssetTransfer = (AssetTransferModel) oldObject;
        }

        String id = (oldObject == null) ? "-" : String.valueOf(oldAssetTransfer.getId());
        String transferDate = (oldObject == null) ? "-" : String.valueOf(oldAssetTransfer.getTransferDate());
        String transferor = (oldObject == null) ? "-" : String.valueOf(oldAssetTransfer.getTransferor().getId() + " - " + oldAssetTransfer.getTransferor().getFullName());
        String department = (oldObject == null) ? "-" : String.valueOf(oldAssetTransfer.getReceivingDepartment().getName());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã chuyển tài sản", id, String.valueOf(newAssetTransfer.getId()), log),
                new LogDetailModel(-1, "Ngày chuyển", transferDate, String.valueOf(newAssetTransfer.getTransferDate()), log),
                new LogDetailModel(-1, "Người chuyển", transferor, String.valueOf(newAssetTransfer.getTransferor().getId() + " - " + newAssetTransfer.getTransferor().getFullName()), log),
                new LogDetailModel(-1, "Phòng ban nhận", department, String.valueOf(newAssetTransfer.getReceivingDepartment().getName()), log)
        ));

        Map<Integer, AssetTransferDetailModel> oldMap = new HashMap<>();
        Map<Integer, AssetTransferDetailModel> newMap = new HashMap<>();

        for (AssetTransferDetailModel detail : oldAssetTransfer.getAssetTransferDetailList()) {
            oldMap.put(detail.getAsset().getId(), detail);
        }

        for (AssetTransferDetailModel detail : newAssetTransfer.getAssetTransferDetailList()) {
            detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            newMap.put(detail.getAsset().getId(), detail);
        }

        for (Map.Entry<Integer, AssetTransferDetailModel> entry : oldMap.entrySet()) {
            int key = entry.getKey();
            AssetTransferDetailModel oldDetail = entry.getValue();

            AssetTransferDetailModel newDetail = newMap.get(key);

            String image1 = oldDetail.getAsset().getImage();
            String name1 = oldDetail.getAsset().getName();
            String unit1 = oldDetail.getAsset().getUnit();
            String quantity1 = String.valueOf(oldDetail.getQuantity());

            String image2 = "-";
            String name2 = "-";
            String unit2 = "-";
            String quantity2 = "-";

            if (newDetail != null) {
                // Detail được cập nhật, so sánh chi tiết và ghi nhật ký
                image2 = newDetail.getAsset().getImage();
                name2 = newDetail.getAsset().getName();
                unit2 = String.valueOf(newDetail.getAsset().getUnit());
                quantity2 = String.valueOf(newDetail.getQuantity());
            }

            logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên tài sản", name1, name2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn vị tính", unit1, unit2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng chuyển", quantity1, quantity2, log));
        }

        for (Map.Entry<Integer, AssetTransferDetailModel> newEntry : newMap.entrySet()) {
            Integer key = newEntry.getKey();
            AssetTransferDetailModel newDetail = newEntry.getValue();

            if (oldMap.get(key) == null) {
                // Detail mới đã được thêm, ghi nhật ký
                String image1 = "-";
                String name1 = "-";
                String unit1 = "-";
                String quantity1 = "-";

                String image2 = newDetail.getAsset().getImage();
                String name2 = newDetail.getAsset().getName();
                String unit2 = String.valueOf(newDetail.getAsset().getUnit());
                String quantity2 = String.valueOf(newDetail.getQuantity());

                logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên mặt hàng", name1, name2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn vị tính", unit1, unit2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng chuyển", quantity1, quantity2, log));
            }
        }
        log.setLogDetailList(logDetailList);
        log.setLogAssetDetailList(logAssetDetailList);
        return log;
    }

    private LogModel getLogDataFromAssetDispose(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);
        AssetDisposeModel newAssetDispose = (AssetDisposeModel) newObject;
        List<LogDetailModel> logDetailList;
        List<LogAssetDetailModel> logAssetDetailList = new ArrayList<>();

        log.setRecordId(String.valueOf(newAssetDispose.getId()));

        AssetDisposeModel oldAssetDispose;

        if (oldObject == null) {
            oldAssetDispose = new AssetDisposeModel();
            oldAssetDispose.setAssetDisposeDetailList(new ArrayList<AssetDisposeDetailModel>());
        } else {
            oldAssetDispose = (AssetDisposeModel) oldObject;
        }

        String id = (oldObject == null) ? "-" : String.valueOf(oldAssetDispose.getId());
        String transferDate = (oldObject == null) ? "-" : String.valueOf(oldAssetDispose.getDisposalDate());
        String transferor = (oldObject == null) ? "-" : String.valueOf(oldAssetDispose.getDisposer().getId() + " - " + oldAssetDispose.getDisposer().getFullName());
        String department = (oldObject == null) ? "-" : String.valueOf(oldAssetDispose.getDisposalDepartment().getName());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã chuyển hủy", id, String.valueOf(newAssetDispose.getId()), log),
                new LogDetailModel(-1, "Ngày hủy", transferDate, String.valueOf(newAssetDispose.getDisposalDate()), log),
                new LogDetailModel(-1, "Người hủy", transferor, String.valueOf(newAssetDispose.getDisposer().getId() + " - " + newAssetDispose.getDisposer().getFullName()), log),
                new LogDetailModel(-1, "Phòng ban hủy", department, String.valueOf(newAssetDispose.getDisposalDepartment().getName()), log)
        ));

        Map<Integer, AssetDisposeDetailModel> oldMap = new HashMap<>();
        Map<Integer, AssetDisposeDetailModel> newMap = new HashMap<>();

        for (AssetDisposeDetailModel detail : oldAssetDispose.getAssetDisposeDetailList()) {
            oldMap.put(detail.getAsset().getId(), detail);
        }

        for (AssetDisposeDetailModel detail : newAssetDispose.getAssetDisposeDetailList()) {
            detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            newMap.put(detail.getAsset().getId(), detail);
        }

        for (Map.Entry<Integer, AssetDisposeDetailModel> entry : oldMap.entrySet()) {
            int key = entry.getKey();
            AssetDisposeDetailModel oldDetail = entry.getValue();

            AssetDisposeDetailModel newDetail = newMap.get(key);

            String image1 = oldDetail.getAsset().getImage();
            String name1 = oldDetail.getAsset().getName();
            String unit1 = oldDetail.getAsset().getUnit();
            String quantity1 = String.valueOf(oldDetail.getQuantity());

            String image2 = "-";
            String name2 = "-";
            String unit2 = "-";
            String quantity2 = "-";

            if (newDetail != null) {
                // Detail được cập nhật, so sánh chi tiết và ghi nhật ký
                image2 = newDetail.getAsset().getImage();
                name2 = newDetail.getAsset().getName();
                unit2 = String.valueOf(newDetail.getAsset().getUnit());
                quantity2 = String.valueOf(newDetail.getQuantity());
            }

            logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên mặt hàng", name1, name2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn vị tính", unit1, unit2, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng hủy", quantity1, quantity2, log));
        }

        for (Map.Entry<Integer, AssetDisposeDetailModel> newEntry : newMap.entrySet()) {
            Integer key = newEntry.getKey();
            AssetDisposeDetailModel newDetail = newEntry.getValue();

            if (oldMap.get(key) == null) {
                // Detail mới đã được thêm, ghi nhật ký
                String image1 = "-";
                String name1 = "-";
                String unit1 = "-";
                String quantity1 = "-";

                String image2 = newDetail.getAsset().getImage();
                String name2 = newDetail.getAsset().getName();
                String unit2 = String.valueOf(newDetail.getAsset().getUnit());
                String quantity2 = String.valueOf(newDetail.getQuantity());

                logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", image1, image2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên mặt hàng", name1, name2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Đơn vị tính", unit1, unit2, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Số lượng hủy", quantity1, quantity2, log));
            }
        }
        log.setLogDetailList(logDetailList);
        log.setLogAssetDetailList(logAssetDetailList);
        return log;
    }

    private LogModel getLogDataFromAccountingPeriod(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);

        AccountingPeriodModel newAccountingPeriod = (AccountingPeriodModel) newObject;

        log.setRecordId(String.valueOf(newAccountingPeriod.getId()));

        List<LogDetailModel> logDetailList;

        AccountingPeriodModel oldAccountingPeriod = (AccountingPeriodModel) oldObject;
        String id = (oldObject == null) ? "-" : String.valueOf(oldAccountingPeriod.getId());
        String name = (oldObject == null) ? "-" : String.valueOf(oldAccountingPeriod.getName());
        String startDate = (oldObject == null) ? "-" : String.valueOf(oldAccountingPeriod.getStartDate());
        String endDate = (oldObject == null) ? "-" : String.valueOf(oldAccountingPeriod.getEndDate());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã kỳ kế toán", id, String.valueOf(newAccountingPeriod.getId()), log),
                new LogDetailModel(-1, "Tên kỳ kế toán", name, String.valueOf(newAccountingPeriod.getName()), log),
                new LogDetailModel(-1, "Ngày bắt đầu", startDate, String.valueOf(newAccountingPeriod.getStartDate()), log),
                new LogDetailModel(-1, "Ngày kết thúc", endDate, String.valueOf(newAccountingPeriod.getEndDate()), log)
        ));

        log.setLogDetailList(logDetailList);
        return log;
    }

    private LogModel getLogDataFromDepreciation(HttpServletRequest req, Object oldObject, Object newObject) {
        LogModel log = getCommonLogData(req);
        DepreciationModel newDepreciation = (DepreciationModel) newObject;
        List<LogDetailModel> logDetailList;
        List<LogAssetDetailModel> logAssetDetailList = new ArrayList<>();

        log.setRecordId(String.valueOf(newDepreciation.getId()));

        DepreciationModel oldDepreciation;

        if (oldObject == null) {
            oldDepreciation = new DepreciationModel();
            oldDepreciation.setDepreciationDetailList(new ArrayList<DepreciationDetailModel>());
        } else {
            oldDepreciation = (DepreciationModel) oldObject;
        }

        String id = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getId());
        String depreciationDate = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getDepreciationDate());
        String creator = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getCreator().getId() + " - " + oldDepreciation.getCreator().getFullName());
        String depreciationMethod = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getDepreciationMethod().getId());
        String accountingPeriod = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getAccountingPeriod().getId());
        String totalValue = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getTotalValue());
        String periodDepreciationExpense = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getPeriodDepreciationExpense());
        String accumulatedDepreciation = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getAccumulatedDepreciation());
        String remainingValue = (oldObject == null) ? "-" : String.valueOf(oldDepreciation.getRemainingValue());

        logDetailList = new ArrayList<>(Arrays.asList(
                new LogDetailModel(-1, "Mã số", id, String.valueOf(newDepreciation.getId()), log),
                new LogDetailModel(-1, "Ngày lập", depreciationDate, String.valueOf(newDepreciation.getDepreciationDate()), log),
                new LogDetailModel(-1, "Người người lập", creator, String.valueOf(newDepreciation.getCreator().getId() + " - " + newDepreciation.getCreator().getFullName()), log),
                new LogDetailModel(-1, "Phương pháp tính", depreciationMethod, String.valueOf(newDepreciation.getDepreciationMethod().getId()), log),
                new LogDetailModel(-1, "Kỳ kế toán", accountingPeriod, String.valueOf(newDepreciation.getAccountingPeriod().getId()), log),
                new LogDetailModel(-1, "Tổng giá trị", totalValue, String.valueOf(newDepreciation.getTotalValue()), log),
                new LogDetailModel(-1, "Khấu hao kỳ", periodDepreciationExpense, String.valueOf(newDepreciation.getPeriodDepreciationExpense()), log),
                new LogDetailModel(-1, "Khấu hao tích lũy", accumulatedDepreciation, String.valueOf(newDepreciation.getAccumulatedDepreciation()), log),
                new LogDetailModel(-1, "Giá trị còn lại", remainingValue, String.valueOf(newDepreciation.getRemainingValue()), log)
        ));

        Map<Integer, DepreciationDetailModel> oldMap = new HashMap<>();
        Map<Integer, DepreciationDetailModel> newMap = new HashMap<>();

        for (DepreciationDetailModel detail : oldDepreciation.getDepreciationDetailList()) {
            oldMap.put(detail.getAsset().getId(), detail);
        }

        for (DepreciationDetailModel detail : newDepreciation.getDepreciationDetailList()) {
            newMap.put(detail.getAsset().getId(), detail);
        }

        for (Map.Entry<Integer, DepreciationDetailModel> entry : oldMap.entrySet()) {
            int key = entry.getKey();
            DepreciationDetailModel oldDetail = entry.getValue();

            DepreciationDetailModel newDetail = newMap.get(key);

            String s1_image = oldDetail.getAsset().getImage();
            String s1_name = oldDetail.getAsset().getName();
            String s1_depreciationPeriod = String.valueOf(oldDetail.getDepreciationPeriod());
            String s1_percentageDepreciation = String.valueOf(oldDetail.getPercentageDepreciation());
            String s1_totalValue = String.valueOf(oldDetail.getTotalValue());
            String s1_periodDepreciationExpense = String.valueOf(oldDetail.getPeriodDepreciationExpense());
            String s1_accumulatedDepreciation = String.valueOf(oldDetail.getAccumulatedDepreciation());
            String s1_remainingValue = String.valueOf(oldDetail.getRemainingValue());

            String s2_image = "-";
            String s2_name = "-";
            String s2_depreciationPeriod = "-";
            String s2_percentageDepreciation = "-";
            String s2_totalValue = "-";
            String s2_periodDepreciationExpense = "-";
            String s2_accumulatedDepreciation = "-";
            String s2_remainingValue = "-";

            if (newDetail != null) {
                // Detail được cập nhật, so sánh chi tiết và ghi nhật ký
                s1_image = oldDetail.getAsset().getImage();
                s2_name = oldDetail.getAsset().getName();
                s2_depreciationPeriod = String.valueOf(oldDetail.getDepreciationPeriod());
                s2_percentageDepreciation = String.valueOf(oldDetail.getPercentageDepreciation());
                s2_totalValue = String.valueOf(oldDetail.getTotalValue());
                s2_periodDepreciationExpense = String.valueOf(oldDetail.getPeriodDepreciationExpense());
                s2_accumulatedDepreciation = String.valueOf(oldDetail.getAccumulatedDepreciation());
                s2_remainingValue = String.valueOf(oldDetail.getRemainingValue());
            }

            logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", s1_image, s2_image, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên tài sản", s1_name, s2_name, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Thời gian trích khấu hao", s1_depreciationPeriod, s2_depreciationPeriod, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Phần trăm khấu hao", s1_percentageDepreciation, s2_percentageDepreciation, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Tổng giá trị", s1_totalValue, s2_totalValue, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Khấu hao kỳ", s1_periodDepreciationExpense, s2_periodDepreciationExpense, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Khấu hao tích lũy", s1_accumulatedDepreciation, s2_accumulatedDepreciation, log));
            logAssetDetailList.add(new LogAssetDetailModel(-1, "Giá trị còn lại", s1_remainingValue, s2_remainingValue, log));

        }

        for (Map.Entry<Integer, DepreciationDetailModel> newEntry : newMap.entrySet()) {
            Integer key = newEntry.getKey();
            DepreciationDetailModel newDetail = newEntry.getValue();

            if (oldMap.get(key) == null) {
                // Detail mới đã được thêm, ghi nhật ký

                String s1_image = "-";
                String s1_name = "-";
                String s1_depreciationPeriod = "-";
                String s1_percentageDepreciation = "-";
                String s1_totalValue = "-";
                String s1_periodDepreciationExpense = "-";
                String s1_accumulatedDepreciation = "-";
                String s1_remainingValue = "-";

                String s2_image = newDetail.getAsset().getImage();
                String s2_name = newDetail.getAsset().getName();
                String s2_depreciationPeriod = String.valueOf(newDetail.getDepreciationPeriod());
                String s2_percentageDepreciation = String.valueOf(newDetail.getPercentageDepreciation());
                String s2_totalValue = String.valueOf(newDetail.getTotalValue());
                String s2_periodDepreciationExpense = String.valueOf(newDetail.getPeriodDepreciationExpense());
                String s2_accumulatedDepreciation = String.valueOf(newDetail.getAccumulatedDepreciation());
                String s2_remainingValue = String.valueOf(newDetail.getRemainingValue());

                logAssetDetailList.add(new LogAssetDetailModel(-1, "Ảnh", s1_image, s2_image, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Tên tài sản", s1_name, s2_name, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Thời gian trích khấu hao", s1_depreciationPeriod, s2_depreciationPeriod, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Phần trăm khấu hao", s1_percentageDepreciation, s2_percentageDepreciation, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Tổng giá trị", s1_totalValue, s2_totalValue, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Khấu hao kỳ", s1_periodDepreciationExpense, s2_periodDepreciationExpense, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Khấu hao tích lũy", s1_accumulatedDepreciation, s2_accumulatedDepreciation, log));
                logAssetDetailList.add(new LogAssetDetailModel(-1, "Giá trị còn lại", s1_remainingValue, s2_remainingValue, log));
            }
        }
        log.setLogDetailList(logDetailList);
        log.setLogAssetDetailList(logAssetDetailList);
        return log;
    }

    @Override
    public ResponseMessage create(HttpServletRequest req, Object oldObject, Object newObject) {

        LogModel log = null;

        if (newObject instanceof AssetModel) {
            log = getLogDataFromAsset(req, oldObject, newObject);
        } else if (newObject instanceof AssetTypeModel) {
            log = getLogDataFromAssetType(req, oldObject, newObject);
        } else if (newObject instanceof DepartmentModel) {
            log = getLogDataFromDepartment(req, oldObject, newObject);
        } else if (newObject instanceof UserModel) {
            log = getLogDataFromUser(req, oldObject, newObject);
        } else if (newObject instanceof AccountModel) {
            log = getLogDataFromAccount(req, oldObject, newObject);
        } else if (newObject instanceof SupplierModel) {
            log = getLogDataFromSupplier(req, oldObject, newObject);
        } else if (newObject instanceof InvoiceModel) {
            log = getLogDataFromInvoice(req, oldObject, newObject);
        } else if (newObject instanceof InventoryTransactionModel) {
            log = getLogDataFromInventoryTransaction(req, oldObject, newObject);
        } else if (newObject instanceof AssetTransferModel) {
            log = getLogDataFromAssetTransfer(req, oldObject, newObject);
        } else if (newObject instanceof AssetDisposeModel) {
            log = getLogDataFromAssetDispose(req, oldObject, newObject);
        } else if (newObject instanceof AccountingPeriodModel) {
            log = getLogDataFromAccountingPeriod(req, oldObject, newObject);
        } else if (newObject instanceof DepreciationModel) {
            log = getLogDataFromDepreciation(req, oldObject, newObject);
        }

        transactionManager.initConnection();
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        try {
            int id = logDAO.create(transactionManager.getConnection(), log);
            log.setId(id);
            for (LogDetailModel logDetail : log.getLogDetailList()) {
                logDetail.setLog(log);
                logDetailDAO.create(transactionManager.getConnection(), logDetail);
            }
            if (log.getLogAssetDetailList() != null) {
                for (LogAssetDetailModel logAssetDetail : log.getLogAssetDetailList()) {
                    logAssetDetail.setLog(log);
                    logAssetDetailDAO.create(transactionManager.getConnection(), logAssetDetail);
                }
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage(Constants.SAVE_FAIL);
            response.setMessage(e.getMessage());
            System.out.println(e);
            transactionManager.closeConnection();
        }

        return response;
    }

    @Override
    public List<LogModel> findByRecordId(String id) {
        List<LogModel> logList = logDAO.findByRecordId(id);
        for (LogModel log : logList) {
            log.setLogDetailList(logDetailDAO.findByLogId(log.getId()));
            log.setLogAssetDetailList(logAssetDetailDAO.findByLogId(log.getId()));
        }
        return logList;
    }
}
