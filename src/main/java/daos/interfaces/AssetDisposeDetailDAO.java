/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetDisposeDetailModel;

/**
 *
 * @author tinhlam
 */
public interface AssetDisposeDetailDAO {

    public List<AssetDisposeDetailModel> findByAssetId(int id);
    
    public List<AssetDisposeDetailModel> findByAssetIdWithGroupByAssetId(int id);

    public List<AssetDisposeDetailModel> findByDepartmentIdWithGroupByDeparmentId(int id);
    
    public List<AssetDisposeDetailModel> findByAssetDisposeId(int id);
    
    public List<AssetDisposeDetailModel> findByAssetIdAndDeparmentId(int assetId, int departmentId);

    public void create(Connection connection, AssetDisposeDetailModel assetDisposeDetail) throws SQLException;

    public void update(Connection connection, AssetDisposeDetailModel assetDisposeDetail) throws SQLException;

    public void deleteByAssetDisposeId(Connection connection, int id) throws SQLException;

    public void deleteByAssetId(Connection connection, int id) throws SQLException;
}
