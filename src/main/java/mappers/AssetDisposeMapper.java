/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetDisposeModel;
import models.DepartmentModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class AssetDisposeMapper implements RowMapper<AssetDisposeModel> {

    @Override
    public AssetDisposeModel mapRow(ResultSet res) {
        AssetDisposeModel assetDispose = null;
        try {
            assetDispose = new AssetDisposeModel();
            assetDispose.setId(res.getInt("id"));
            assetDispose.setDisposalDate(res.getDate("disposalDate"));
            assetDispose.setReason(res.getString("reason"));
            assetDispose.setDisposer(new UserModel(res.getInt("AssetDispose.disposerId")));
            assetDispose.setDisposalDepartment(new DepartmentModel(res.getInt("disposalDepartmentId")));
        } catch (SQLException ex) {
            Logger.getLogger(AssetDisposeMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assetDispose;
    }

}
