/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AssetDisposeDetailDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.AssetDisposeDetailMapper;
import models.AssetDisposeDetailModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class AssetDisposeDetailDAOImpl implements AssetDisposeDetailDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetDisposeDetailModel> findByAssetId(int id) {
        String sql = "select * from AssetDisposeDetail where assetId = ?";
        return genericDAO.executeQuery(sql, new AssetDisposeDetailMapper(), id);
    }

    @Override
    public List<AssetDisposeDetailModel> findByAssetDisposeId(int id) {
        String sql = "select * from AssetDisposeDetail where assetDisposeId = ?";
        return genericDAO.executeQuery(sql, new AssetDisposeDetailMapper(), id);
    }

    @Override
    public void create(Connection connection, AssetDisposeDetailModel detail) throws SQLException {
        String sql = "insert into AssetDisposeDetail values (?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, detail.getAssetDispose().getId(), detail.getAsset().getId(), detail.getQuantity());
    }

    @Override
    public void update(Connection connection, AssetDisposeDetailModel detail) throws SQLException {
        String sql = "update AssetDisposeDetail set assetId = ?, quantity = ? where assetDisposeId = ?";
        genericDAO.executeUpdate(connection, sql, detail.getAsset().getId(), detail.getQuantity(), detail.getAssetDispose().getId());
    }

    @Override
    public void deleteByAssetDisposeId(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetDisposeDetail where assetDisposeId = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public void deleteByAssetId(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetDisposeDetail where AssetId = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public List<AssetDisposeDetailModel> findByAssetIdWithGroupByAssetId(int id) {
        String sql = "select assetId, sum(quantity) as quantity from AssetDisposeDetail where assetId = ? group by assetId";
        return genericDAO.executeQuery(sql, new AssetDisposeDetailMapper(), id);
    }

    @Override
    public List<AssetDisposeDetailModel> findByDepartmentIdWithGroupByDeparmentId(int id) {
        String sql = "select assetId, quantity from assetdispose, assetdisposedetail where assetdispose.id = assetdisposedetail.assetDisposeId and disposalDepartmentId = ?";
        return genericDAO.executeQuery(sql, new AssetDisposeDetailMapper(), id);
    }

    @Override
    public List<AssetDisposeDetailModel> findByAssetIdAndDeparmentId(int assetId, int departmentId) {
        String sql = "select assetId, sum(quantity) as quantity from assetdispose, assetdisposedetail where assetdispose.id = assetdisposedetail.assetDisposeId and assetId = ? and disposalDepartmentId = ? group by assetId";
        return genericDAO.executeQuery(sql, new AssetDisposeDetailMapper(), assetId, departmentId);
    }

}
