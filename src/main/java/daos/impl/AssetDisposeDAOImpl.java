/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AssetDisposeDAO;
import javax.inject.Inject;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import mappers.AssetDisposeMapper;
import models.AssetDisposeModel;

/**
 *
 * @author tinhlam
 */
public class AssetDisposeDAOImpl implements AssetDisposeDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetDisposeModel> findAll() {
        String sql = "select * from AssetDispose";
        return genericDAO.executeQuery(sql, new AssetDisposeMapper());
    }

    @Override
    public List<AssetDisposeModel> findById(int id) {
        String sql = "select * from AssetDispose where id = ?";
        return genericDAO.executeQuery(sql, new AssetDisposeMapper(), id);
    }

    @Override
    public List<AssetDisposeModel> findByDisposalDepartmentId(int id) {
        String sql = "select * from AssetDispose where disposalDepartmentId = ?";
        return genericDAO.executeQuery(sql, new AssetDisposeMapper(), id);
    }
    @Override
    public void deleteById(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetDispose where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public int create(Connection connection, AssetDisposeModel assetDispose) throws SQLException {
        String sql = "insert into AssetDispose values (?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, assetDispose.getDisposalDate(), assetDispose.getDisposer().getId(), assetDispose.getDisposalDepartment().getId(), assetDispose.getReason());
    }

    @Override
    public void update(Connection connection, AssetDisposeModel assetDispose) throws SQLException {
        String sql = "update AssetDispose set disposalDate = ?, disposerId = ?, disposalDepartmentId = ?, reason = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, assetDispose.getDisposalDate(), assetDispose.getDisposer().getId(), assetDispose.getDisposalDepartment().getId(), assetDispose.getReason(), assetDispose.getId());
    }

}
