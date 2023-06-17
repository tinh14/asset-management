/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetModel;

/**
 *
 * @author tinhlam
 */
public interface AssetDAO {

    public List<AssetModel> findAll();

    public List<AssetModel> findById(int id);

    public List<AssetModel> findByName(String name);

    public List<AssetModel> findByWeight(int weight);

    public List<AssetModel> findByAssetTypeId(int id);

    public List<AssetModel> findByIdAndName(int id, String name);

    public List<AssetModel> findByIdAndWeight(int id, int weight);

    public List<AssetModel> findByDepartmentId(int id);
    
    public List<AssetModel> findByAssetNameAndDepartmentId(String name, int id);

    public int create(Connection connection, AssetModel asset) throws SQLException;

    public void update(Connection connection, AssetModel asset) throws SQLException;

    public void delete(Connection connection, int id) throws SQLException;
}
