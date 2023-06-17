/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetDisposeDetailModel;
import models.AssetDisposeModel;
import models.AssetModel;

/**
 *
 * @author tinhlam
 */
public class AssetDisposeDetailMapper implements RowMapper<AssetDisposeDetailModel> {

    @Override
    public AssetDisposeDetailModel mapRow(ResultSet res) {
        AssetDisposeDetailModel assetDisposeDetail = null;
        try {
            assetDisposeDetail = new AssetDisposeDetailModel();
            assetDisposeDetail.setQuantity(res.getInt("quantity"));
            assetDisposeDetail.setAsset(new AssetModel(res.getInt("assetId")));
            if (res.getMetaData().getColumnCount() == 2) {
                return assetDisposeDetail;
            }
            assetDisposeDetail.setAssetDispose(new AssetDisposeModel(res.getInt("assetDisposeId")));
        } catch (SQLException ex) {
            Logger.getLogger(AssetDisposeDetailMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assetDisposeDetail;
    }

}
