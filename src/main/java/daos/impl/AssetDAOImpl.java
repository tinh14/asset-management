/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AssetDAO;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import mappers.AssetMapper;
import models.AssetModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
@Default
public class AssetDAOImpl implements AssetDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetModel> findAll() {
        String sql = "select * from Asset";
        return genericDAO.executeQuery(sql, new AssetMapper());
    }

    @Override
    public List<AssetModel> findById(int id) {
        String sql = "select * from Asset "
                + "where Asset.id = ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id);
    }

    @Override
    public List<AssetModel> findByName(String name) {
        String sql = "select * from Asset "
                + "where Asset.name like ? order by Asset.name";
        return genericDAO.executeQuery(sql, new AssetMapper(), "%" + name + "%");
    }

    @Override
    public List<AssetModel> findByWeight(int weight) {
        String sql = "select * from Asset "
                + "where Asset.weight >= ? order by Asset.weight";
        return genericDAO.executeQuery(sql, new AssetMapper(), weight);
    }

    @Override
    public List<AssetModel> findByAssetTypeId(int id) {
        String sql = "select * from Asset where assetTypeId = ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id);
    }

    @Override
    public List<AssetModel> findByIdAndName(int id, String name) {
        String sql = "select * from Asset "
                + "where Asset.id = ? and Asset.name like ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id, "%" + name + "%");
    }

    @Override
    public List<AssetModel> findByIdAndWeight(int id, int weight) {
        String sql = "select * from Asset "
                + "where Asset.id = ? and Asset.weight >= ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id, weight);
    }

    @Override
    public List<AssetModel> findByAssetNameAndDepartmentId(String name, int id) {
        String sql = "select asset.id, asset.name, asset.unit, asset.assetTypeId "
                + "from asset, assettransfer, assettransferdetail "
                + "where asset.id = assettransferdetail.assetId "
                + "and assettransfer.id = assettransferdetail.assetTransferId and receivingDepartmentId = ? "
                + "and asset.name like ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id, "%" + name + "%");
    }

    @Override
    public List<AssetModel> findByDepartmentId(int id) {
        String sql = "select asset.id, asset.name, asset.unit, asset.assetTypeId "
                + "from asset, assettransfer, assettransferdetail "
                + "where asset.id = assettransferdetail.assetId "
                + "and assettransfer.id = assettransferdetail.assetTransferId "
                + "and receivingDepartmentId = ?";
        return genericDAO.executeQuery(sql, new AssetMapper(), id);
    }

    @Override
    public int create(Connection connection, AssetModel asset) throws SQLException {
        String sql = "insert into Asset "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, asset.getId(), asset.getName(),
                asset.getWeight(), asset.getUnit(), asset.getPrice(), 
                asset.getPercentageDepreciation(), asset.getQuantity(), asset.getTotal(), 
                asset.getImage(), asset.getAssetType().getId());
    }

    @Override
    public void update(Connection connection, AssetModel asset) throws SQLException {
        String sql = "update Asset "
                + "set name = ?, weight = ?, unit = ?, price = ?, percentageDepreciation = ?, "
                + "image = ?, assetTypeId = ? "
                + "where id = ?";
        genericDAO.executeUpdate(connection, sql, asset.getName(), asset.getWeight(),
                asset.getUnit(), asset.getPrice(), asset.getPercentageDepreciation(), 
                asset.getImage(), asset.getAssetType().getId(), 
                asset.getId());
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        String sql = "delete from Asset where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

}
