/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AccountingPeriodModel;
import models.DepreciationMethodModel;
import models.DepreciationModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationMapper implements RowMapper<DepreciationModel> {

    @Override
    public DepreciationModel mapRow(ResultSet res) {
        DepreciationModel model = null;
        try {
            model = new DepreciationModel();
            model.setId(res.getInt("id"));
            model.setDepreciationDate(res.getDate("depreciationDate"));
            model.setPeriodDepreciationExpense(res.getInt("periodDepreciationExpense"));
            model.setRemainingValue(res.getInt("remainingValue"));
            model.setTotalValue(res.getInt("totalValue"));
            model.setAccumulatedDepreciation(res.getInt("accumulatedDepreciation"));
            model.setAccountingPeriod(new AccountingPeriodModel(res.getInt("accountingPeriodId")));
            model.setCreator(new UserModel(res.getInt("creatorId")));
            model.setDepreciationMethod(new DepreciationMethodModel(res.getString("depreciationMethodId")));
        } catch (SQLException ex) {
            Logger.getLogger(DepreciationMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
