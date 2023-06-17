/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.impl;

import daos.interfaces.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.AssetTransferDetailMapper;
import models.AssetTransferDetailModel;

/**
 *
 * @author tinhlam
 */
public class AssetTransferDetailDAOImpl implements AssetTransferDetailDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetTransferDetailModel> findByAssetTransferId(int id) {
        String sql = "select * from AssetTransferDetail where assetTransferId = ?";
        return genericDAO.executeQuery(sql, new AssetTransferDetailMapper(), id);
    }

    @Override
    public List<AssetTransferDetailModel> findByAssetId(int id) {
        String sql = "select * from AssetTransferDetail where assetId = ?";
        return genericDAO.executeQuery(sql, new AssetTransferDetailMapper(), id);
    }

    @Override
    public void create(Connection connection, AssetTransferDetailModel assetTransferDetail) throws SQLException {
        String sql = "insert into AssetTransferDetail values(?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, assetTransferDetail.getAssetTransfer().getId(), assetTransferDetail.getAsset().getId(), assetTransferDetail.getQuantity());
    }

    @Override
    public void deleteByAssetTransferId(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetTransferDetail where assetTransferId = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public List<AssetTransferDetailModel> findByAssetIdWithGroupByAssetId(int id) {
        String sql = "select assetId, sum(quantity) as quantity from AssetTransferDetail where assetId = ? group by assetId";
        return genericDAO.executeQuery(sql, new AssetTransferDetailMapper(), id);
    }

    @Override
    public List<AssetTransferDetailModel> findByAssetIdAndDepartmentId(int assetId, int departmentId) {
        String sql = "select assetId, sum(quantity) as quantity from assettransfer, assettransferdetail where assettransfer.id = assettransferdetail.assetTransferId and assetId = ? and receivingDepartmentId = ? group by assetId";
        return genericDAO.executeQuery(sql, new AssetTransferDetailMapper(), assetId, departmentId);
    }

    @Override
    public List<AssetTransferDetailModel> findByAssetNameAndDepartmentId(int assetId, int departmentId) {
        String sql = "select assetId, assettransferdetail.quantity from asset, assettransfer, assettransferdetail where asset.id = assettransferdetail.assetId and assettransfer.id = assettransferdetail.assetTransferId and receivingDepartmentId = ? and asset.name like ?";
        return genericDAO.executeQuery(sql, new AssetTransferDetailMapper(), assetId, departmentId);
    }
}
