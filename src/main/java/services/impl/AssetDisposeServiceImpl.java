/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.AssetDAO;
import daos.interfaces.AssetDisposeDAO;
import daos.interfaces.AssetDisposeDetailDAO;
import daos.interfaces.DepartmentDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.AssetDisposeDetailModel;
import models.AssetDisposeModel;
import models.DepartmentModel;
import services.interfaces.AssetDisposeService;
import services.interfaces.InventoryService;
import services.interfaces.UserService;

/**
 *
 * @author tinhlam
 */
public class AssetDisposeServiceImpl implements AssetDisposeService {

    @Inject
    private AssetDisposeDAO assetDisposeDAO;

    @Inject
    private AssetDisposeDetailDAO assetDisposeDetailDAO;

    @Inject
    private TransactionManager transactionManager;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private UserService userService;
    
    @Inject
    private InventoryService inventoryService;

    @Inject
    private AssetDAO assetDAO;

    @Override
    public List<AssetDisposeModel> findAll() {
        List<AssetDisposeModel> assetDisposeList = assetDisposeDAO.findAll();
        for (AssetDisposeModel assetDispose : assetDisposeList) {
            DepartmentModel disposalDepartment = departmentDAO.findById(assetDispose.getDisposalDepartment().getId()).get(0);
            assetDispose.setDisposalDepartment(disposalDepartment);
            assetDispose.setDisposer(userService.findById(assetDispose.getDisposer().getId()).get(0));
        }
        return assetDisposeList;
    }

    @Override
    public List<AssetDisposeModel> findById(int id) {
        List<AssetDisposeModel> assetDisposeList = assetDisposeDAO.findById(id);

        for (AssetDisposeModel assetDispose : assetDisposeList) {
            DepartmentModel disposalDepartment = departmentDAO.findById(assetDispose.getDisposalDepartment().getId()).get(0);
            assetDispose.setDisposalDepartment(disposalDepartment);
            assetDispose.setDisposer(userService.findById(assetDispose.getDisposer().getId()).get(0));
            List<AssetDisposeDetailModel> assetDisposeDetailList = assetDisposeDetailDAO.findByAssetDisposeId(id);

            for (AssetDisposeDetailModel assetDisposeDetail : assetDisposeDetailList) {
                assetDisposeDetail.setAsset(assetDAO.findById(assetDisposeDetail.getAsset().getId()).get(0));
            }

            assetDispose.setAssetDisposeDetailList(assetDisposeDetailList);
        }
        return assetDisposeList;
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
    public ResponseMessage create(AssetDisposeModel assetDispose) {

        ResponseMessage response = checkForeignKey(assetDispose.getDisposalDepartment().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = assetDisposeDAO.create(transactionManager.getConnection(), assetDispose);
            assetDispose.setId(id);
            for (AssetDisposeDetailModel detail : assetDispose.getAssetDisposeDetailList()) {
                detail.getAssetDispose().setId(id);
                assetDisposeDetailDAO.create(transactionManager.getConnection(), detail);
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
    public ResponseMessage update(AssetDisposeModel assetDispose) {
        ResponseMessage response = checkForeignKey(assetDispose.getDisposalDepartment().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            assetDisposeDAO.update(transactionManager.getConnection(), assetDispose);
            assetDisposeDetailDAO.deleteByAssetDisposeId(transactionManager.getConnection(), assetDispose.getId());
            for (AssetDisposeDetailModel detail : assetDispose.getAssetDisposeDetailList()) {
                assetDisposeDetailDAO.create(transactionManager.getConnection(), detail);
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
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            assetDisposeDetailDAO.deleteByAssetDisposeId(transactionManager.getConnection(), id);
            assetDisposeDAO.deleteById(transactionManager.getConnection(), id);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public List<Integer> getQuantities(int departmentId, List<AssetDisposeDetailModel> details) {
        List<Integer> quantities = new ArrayList<>();
        for (AssetDisposeDetailModel detail : details) {
            quantities.add(inventoryService.getDepartmentalAssetQuantities(detail.getAsset().getId(), departmentId));
        }
        return quantities;
    }

}
