/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetModel;
import models.AssetTransferDetailModel;
import models.AssetTransferModel;

/**
 *
 * @author tinhlam
 */
public class AssetTransferDetailMapper implements RowMapper<AssetTransferDetailModel> {

    @Override
    public AssetTransferDetailModel mapRow(ResultSet res) {
        AssetTransferDetailModel assetTransferDetailModel = null;
        try {
            assetTransferDetailModel = new AssetTransferDetailModel();
            assetTransferDetailModel.setAsset(new AssetModel(res.getInt("assetId")));
            assetTransferDetailModel.setQuantity(res.getInt("quantity"));
            if (res.getMetaData().getColumnCount() == 2) {
                return assetTransferDetailModel;
            }
            assetTransferDetailModel.setAssetTransfer(new AssetTransferModel(res.getInt("assetTransferId")));
        } catch (SQLException ex) {
            Logger.getLogger(AssetTransferDetailMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assetTransferDetailModel;
    }

}
