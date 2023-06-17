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
import models.AssetTypeModel;

/**
 *
 * @author tinhlam
 */
public class AssetMapper implements RowMapper<AssetModel> {

    @Override
    public AssetModel mapRow(ResultSet res) {
        AssetModel asset = null;
        try {
            asset = new AssetModel();
            asset.setId(res.getInt("id"));
            asset.setName(res.getString("name"));
            asset.setUnit(res.getString("unit"));
            if (res.getMetaData().getColumnCount() == 3) {
                return asset;
            }
            asset.setAssetType(new AssetTypeModel(res.getInt("assetTypeId")));
            if (res.getMetaData().getColumnCount() == 4) {
                return asset;
            }
            asset.setWeight(res.getInt("weight"));
            asset.setPrice(res.getInt("price"));
            asset.setPercentageDepreciation(res.getInt("percentageDepreciation"));
            asset.setQuantity(res.getInt("quantity"));
            asset.setTotal(res.getInt("total"));
            asset.setImage(res.getString("image"));
        } catch (SQLException ex) {
            Logger.getLogger(AssetMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return asset;
    }

}
