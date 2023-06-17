/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetTypeModel;

/**
 *
 * @author tinhlam
 */
public class AssetTypeMapper implements RowMapper<AssetTypeModel> {

    @Override
    public AssetTypeModel mapRow(ResultSet res) {
        AssetTypeModel assetType = null;
        try {
            assetType = new AssetTypeModel();
            assetType.setId(res.getInt("id"));
            assetType.setName(res.getString("name"));
            assetType.setDepreciationPeriod(res.getInt("depreciationPeriod"));
        } catch (SQLException ex) {
            Logger.getLogger(AssetTypeMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assetType;
    }

}
