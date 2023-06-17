/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetTypeModel;

/**
 *
 * @author tinhlam
 */
public interface AssetTypeDAO {
    public List<AssetTypeModel> findAll();
    public List<AssetTypeModel> findById(int id);
    public List<AssetTypeModel> findByName(String name);
    public List<AssetTypeModel> findByDepreciationPeriod(int depreciationPeriod);
    public int create(Connection connection, AssetTypeModel assetType) throws SQLException;
    public void update(Connection connection, AssetTypeModel assetType) throws SQLException;
    public void delete(Connection connection, int id) throws SQLException;
}
