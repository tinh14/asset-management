/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetTransferModel;

/**
 *
 * @author tinhlam
 */
public interface AssetTransferDAO {
    public List<AssetTransferModel> findAll();
    public List<AssetTransferModel> findById(int id);
    public int create(Connection connection, AssetTransferModel assetTransfer) throws SQLException;
    public void update(Connection connection, AssetTransferModel assetTransfer) throws SQLException;
    public void deleteById(Connection connection, int id) throws SQLException;
}
