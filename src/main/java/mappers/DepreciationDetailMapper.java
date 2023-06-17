/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.AssetModel;
import models.DepreciationDetailModel;
import models.DepreciationModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationDetailMapper implements RowMapper<DepreciationDetailModel> {

    @Override
    public DepreciationDetailModel mapRow(ResultSet res) {
        DepreciationDetailModel model = null;
        try {
            model = new DepreciationDetailModel();
            model.setPeriodDepreciationExpense(res.getInt("periodDepreciationExpense"));
            model.setAsset(new AssetModel(res.getInt("assetId")));
            model.setTotalValue(res.getInt("totalValue"));
            model.setRemainingValue(res.getInt("remainingValue"));
            model.setAccumulatedDepreciation(res.getInt("accumulatedDepreciation"));
            model.setPeriodDepreciationExpense(res.getInt("periodDepreciationExpense"));
            model.getAsset().setName(res.getString("name"));
            model.getAsset().setImage(res.getString("image"));
            model.setDepreciationPeriod(res.getInt("depreciationPeriod"));
            model.setPercentageDepreciation(res.getInt("percentageDepreciation"));
            if (res.getMetaData().getColumnCount() == 11) {
                model.setDepreciation(new DepreciationModel(res.getInt("depreciationId")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return model;
    }

}
