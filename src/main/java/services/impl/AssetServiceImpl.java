package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import java.util.List;
import javax.inject.Inject;
import services.interfaces.AssetService;
import daos.interfaces.AssetDAO;
import daos.interfaces.AssetTypeDAO;
import daos.interfaces.LogDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletResponse;
import models.AssetModel;

/**
 *
 * @author tinhlam
 */
@Default
public class AssetServiceImpl implements AssetService {

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private AssetTypeDAO assetTypeDAO;

    @Inject
    private LogDAO logDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<AssetModel> findAll() {
        List<AssetModel> assetList = assetDAO.findAll();
        for (AssetModel asset : assetList) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return assetList;
    }

    @Override
    public List<AssetModel> findById(int id) {
        List<AssetModel> assetList = assetDAO.findById(id);
        for (AssetModel asset : assetList) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return assetList;
    }

    @Override
    public List<AssetModel> findByName(String name) {
        List<AssetModel> assetList = assetDAO.findByName(name);
        for (AssetModel asset : assetList) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return assetList;
    }

    @Override
    public List<AssetModel> findByWeight(int weight) {
        List<AssetModel> assetList = assetDAO.findByWeight(weight);
        for (AssetModel asset : assetList) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return assetList;
    }

    private ResponseMessage checkForeignKey(int id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (assetTypeDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Loại tài sản không tồn tại");
        }
        return response;
    }

    @Override
    public ResponseMessage create(AssetModel asset) {
        ResponseMessage response = checkForeignKey(asset.getAssetType().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = assetDAO.create(transactionManager.getConnection(), asset);
            asset.setId(id);
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(AssetModel asset) {
        ResponseMessage response = new ResponseMessage();

        if (assetTypeDAO.findById(asset.getAssetType().getId()).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Loại tài sản không tồn tại");
        }

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            assetDAO.update(transactionManager.getConnection(), asset);
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public List<AssetModel> findByAssetNameAndDepartmentId(String name, int id) {
        List<AssetModel> list = assetDAO.findByAssetNameAndDepartmentId(name, id);
        for (AssetModel asset : list) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return list;
    }

    @Override
    public List<AssetModel> findByDepartmentId(int id) {
        List<AssetModel> list = assetDAO.findByDepartmentId(id);
        for (AssetModel asset : list) {
            asset.setAssetType(assetTypeDAO.findById(asset.getAssetType().getId()).get(0));
        }
        return list;
    }

}
