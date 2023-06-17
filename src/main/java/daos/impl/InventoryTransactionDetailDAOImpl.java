/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import java.util.List;
import javax.inject.Inject;
import mappers.InventoryTransactionDetailMapper;
import models.InventoryTransactionDetailModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import daos.interfaces.InventoryTransactionDetailDAO;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionDetailDAOImpl implements InventoryTransactionDetailDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<InventoryTransactionDetailModel> findAll() {
        String sql = "select * from InventoryTransactionDetail";
        return genericDAO.executeQuery(sql, new InventoryTransactionDetailMapper());
    }

    @Override
    public List<InventoryTransactionDetailModel> findByAssetId(int id) {
        String sql = "select * from InventoryTransactionDetail where assetId = ?";
        return genericDAO.executeQuery(sql, new InventoryTransactionDetailMapper(), id);
    }
    

    @Override
    public List<InventoryTransactionDetailModel> findByInventoryTransactionId(int id) {
        String sql = "select * from InventoryTransactionDetail where inventoryTransactionId = ?";
        return genericDAO.executeQuery(sql, new InventoryTransactionDetailMapper(), id);
    }

    @Override
    public void deleteByAssetId(Connection connection, int id) throws SQLException {
        String sql = "delete from InventoryTransactionDetaill where assetId = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public void deleteByInventoryTransactionId(Connection connection, int id) throws SQLException {
        String sql = "delete from InventoryTransactionDetail where inventoryTransactionId = ?";
        genericDAO.executeUpdate(connection, sql, id);
    }

    @Override
    public void create(Connection connection, InventoryTransactionDetailModel inventoryTransactionDetailModel) throws SQLException {
        String sql = "insert into InventoryTransactionDetail values (?, ?, ?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, inventoryTransactionDetailModel.getInventoryTransaction().getId(), inventoryTransactionDetailModel.getAsset().getId(), inventoryTransactionDetailModel.getQuantity(), inventoryTransactionDetailModel.getPrice(), inventoryTransactionDetailModel.getVAT());
    }

    @Override
    public List<InventoryTransactionDetailModel> findByAssetIdWithGroupByAssetId(int id) {
        String sql = "select assetId as assetId, sum(quantity) as quantity from InventoryTransactionDetail where assetId = ? group by assetId";
        return genericDAO.executeQuery(sql, new InventoryTransactionDetailMapper(), id);
    }

}
