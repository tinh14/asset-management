/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.impl;

import constants.Constants;
import daos.interfaces.AssetDAO;
import utilz.ResponseMessage;
import daos.interfaces.AssetTransferDAO;
import daos.interfaces.AssetTransferDetailDAO;
import daos.interfaces.DepartmentDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.AssetTransferDetailModel;
import models.AssetTransferModel;
import services.interfaces.*;

/**
 *
 * @author tinhlam
 */
public class AssetTransferServiceImpl implements AssetTransferService {

    @Inject
    private AssetTransferDAO assetTransferDAO;

    @Inject
    private AssetTransferDetailDAO assetTransferDetailDAO;

    @Inject
    private UserService userService;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<AssetTransferModel> findAll() {
        List<AssetTransferModel> assetTransferList = assetTransferDAO.findAll();
        for (AssetTransferModel assetTransfer : assetTransferList) {
            assetTransfer.setTransferor(userService.findByPersonId(assetTransfer.getTransferor().getId()).get(0));
            assetTransfer.setReceivingDepartment(departmentDAO.findById(assetTransfer.getReceivingDepartment().getId()).get(0));
        }
        return assetTransferList;
    }

    @Override
    public List<AssetTransferModel> findById(int id) {
        List<AssetTransferModel> assetTransferList = assetTransferDAO.findById(id);
        for (AssetTransferModel assetTransfer : assetTransferList) {
            assetTransfer.setTransferor(userService.findByPersonId(assetTransfer.getTransferor().getId()).get(0));
            assetTransfer.setReceivingDepartment(departmentDAO.findById(assetTransfer.getReceivingDepartment().getId()).get(0));
            List<AssetTransferDetailModel> assetTransferDetailList = assetTransferDetailDAO.findByAssetTransferId(assetTransfer.getId());
            for (AssetTransferDetailModel detail : assetTransferDetailList) {
                detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            }
            assetTransfer.setAssetTransferDetailList(assetTransferDetailList);
        }
        return assetTransferList;
    }

    private ResponseMessage checkForeignKey(int id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (departmentDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Phòng ban không tồn tại\n");
        }
        return response;
    }

    @Override
    public ResponseMessage create(AssetTransferModel assetTransfer) {

        ResponseMessage response = checkForeignKey(assetTransfer.getReceivingDepartment().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = assetTransferDAO.create(transactionManager.getConnection(), assetTransfer);
            assetTransfer.setId(id);
            for (AssetTransferDetailModel detail : assetTransfer.getAssetTransferDetailList()) {
                detail.getAssetTransfer().setId(id);
                assetTransferDetailDAO.create(transactionManager.getConnection(), detail);
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(AssetTransferModel assetTransfer) {
        ResponseMessage response = checkForeignKey(assetTransfer.getReceivingDepartment().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            assetTransferDAO.update(transactionManager.getConnection(), assetTransfer);
            assetTransferDetailDAO.deleteByAssetTransferId(transactionManager.getConnection(), assetTransfer.getId());
            for (AssetTransferDetailModel detail : assetTransfer.getAssetTransferDetailList()) {
                assetTransferDetailDAO.create(transactionManager.getConnection(), detail);
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage deleteById(int id) {
//        Connection connection = transactionManager.getConnection();
//        if (!assetTransferDetailDAO.deleteByAssetTransferId(connection, id)){
//            return false;
//        }
//        boolean res = assetTransferDAO.deleteById(connection, id);
//        transactionManager.commitAndCloseConnection();
//        return res;
        return null;
    }

    @Override
    public List<Integer> getQuantities(List<AssetTransferDetailModel> assetTransferDetailList) {
        List<Integer> quantities = new ArrayList<>();
        for (AssetTransferDetailModel detail : assetTransferDetailList) {
            quantities.add(inventoryService.getQuantityFormAssetId(detail.getAsset().getId()));
        }
        return quantities;
    }
}
