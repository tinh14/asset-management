/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetTransferModel;
import models.DepartmentModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class AssetTransferMapper implements RowMapper<AssetTransferModel> {

    @Override
    public AssetTransferModel mapRow(ResultSet res) {
        AssetTransferModel assetTransfer = null;
        try {
            assetTransfer = new AssetTransferModel();
            assetTransfer.setId(res.getInt("id"));
            assetTransfer.setTransferDate(res.getDate("transferDate"));
            assetTransfer.setTransferor(new UserModel(res.getInt("transferorId")));
            assetTransfer.setReceivingDepartment(new DepartmentModel(res.getInt("receivingDepartmentId")));
        } catch (SQLException ex) {
            Logger.getLogger(AssetTransferMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assetTransfer;
    }

}
