/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AssetTransferDetailModel;

/**
 *
 * @author tinhlam
 */
public interface AssetTransferDetailDAO {

    public List<AssetTransferDetailModel> findByAssetTransferId(int id);

    public List<AssetTransferDetailModel> findByAssetId(int id);
    
    public List<AssetTransferDetailModel> findByAssetIdWithGroupByAssetId(int id);

    public void create(Connection connection, AssetTransferDetailModel assetTransferDetail) throws SQLException;

    public void deleteByAssetTransferId(Connection connection, int assetTransferId) throws SQLException;
    
    public List<AssetTransferDetailModel> findByAssetIdAndDepartmentId(int assetId, int departmentId);
    
    public List<AssetTransferDetailModel> findByAssetNameAndDepartmentId(int assetId, int departmentId);
}
