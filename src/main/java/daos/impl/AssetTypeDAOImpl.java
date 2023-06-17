/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AssetTypeDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.AssetTypeMapper;
import models.AssetTypeModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class AssetTypeDAOImpl implements AssetTypeDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetTypeModel> findAll() {
        String sql = "select * from AssetType";
        return genericDAO.executeQuery(sql, new AssetTypeMapper());
    }

    @Override
    public List<AssetTypeModel> findById(int id) {
        String sql = "select * from AssetType where id = ?";
        return genericDAO.executeQuery(sql, new AssetTypeMapper(), id);
    }

    @Override
    public List<AssetTypeModel> findByName(String name) {
        String sql = "select * from AssetType where name like ?";
        return genericDAO.executeQuery(sql, new AssetTypeMapper(), "%" + name + "%");
    }

    @Override
    public List<AssetTypeModel> findByDepreciationPeriod(int depreciationPeriod) {
        String sql = "select * from AssetType where depreciationPeriod >= ?";
        return genericDAO.executeQuery(sql, new AssetTypeMapper(), depreciationPeriod);
    }

    @Override
    public int create(Connection connection, AssetTypeModel assetType) throws SQLException {
        String sql = "insert into AssetType values (?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, assetType.getId(), assetType.getName(),
                assetType.getDepreciationPeriod());
    }

    @Override
    public void update(Connection connection, AssetTypeModel assetType) throws SQLException {
        String sql = "update AssetType set name = ?, depreciationPeriod = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, assetType.getName(),
                assetType.getDepreciationPeriod(), assetType.getId());
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetType where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

}
