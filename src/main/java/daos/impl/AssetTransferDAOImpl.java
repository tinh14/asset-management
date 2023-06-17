/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AssetTransferDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.AssetTransferMapper;
import models.AssetTransferModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class AssetTransferDAOImpl implements AssetTransferDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AssetTransferModel> findAll() {
        String sql = "select * from AssetTransfer";
        return genericDAO.executeQuery(sql, new AssetTransferMapper());
    }
    
    @Override
    public List<AssetTransferModel> findById(int id) {
        String sql = "select * from AssetTransfer where id = ?";
        return genericDAO.executeQuery(sql, new AssetTransferMapper(), id);
    }

    @Override
    public int create(Connection connection, AssetTransferModel assetTransfer) throws SQLException {
        String sql = "insert into AssetTransfer values (?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, assetTransfer.getTransferDate(), assetTransfer.getReceivingDepartment().getId(), assetTransfer.getTransferor().getId());
    }

    @Override
    public void update(Connection connection, AssetTransferModel assetTransfer) throws SQLException {
        String sql = "update AssetTransfer set transferDate = ?, receivingDepartmentId = ?, transferorId = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, assetTransfer.getTransferDate(), assetTransfer.getReceivingDepartment().getId(), assetTransfer.getTransferor().getId(), assetTransfer.getId());
    }

    @Override
    public void deleteById(Connection connection, int id) throws SQLException {
        String sql = "delete from AssetTransfer where id = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

}
