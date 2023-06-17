/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import services.interfaces.AssetTypeService;
import daos.interfaces.AssetDAO;
import daos.interfaces.AssetTypeDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.AssetTypeModel;

/**
 *
 * @author tinhlam
 */
public class AssetTypeServiceImpl implements AssetTypeService {

    @Inject
    private AssetTypeDAO assetTypeDAO;

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<AssetTypeModel> findAll() {
        return assetTypeDAO.findAll();
    }

    @Override
    public List<AssetTypeModel> findById(int id) {
        return assetTypeDAO.findById(id);
    }

    @Override
    public List<AssetTypeModel> findByName(String name) {
        return assetTypeDAO.findByName(name);
    }

    @Override
    public List<AssetTypeModel> findByDepreciationPeriod(int depreciationPeriod) {
        return assetTypeDAO.findByDepreciationPeriod(depreciationPeriod);
    }

    @Override
    public ResponseMessage create(AssetTypeModel assetType) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = assetTypeDAO.create(transactionManager.getConnection(), assetType);
            assetType.setId(id);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(AssetTypeModel assetType) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            assetTypeDAO.update(transactionManager.getConnection(), assetType);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }
}
