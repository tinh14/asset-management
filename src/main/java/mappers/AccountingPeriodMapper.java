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
import models.AccountingPeriodTypeModel;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodMapper implements RowMapper<AccountingPeriodModel> {

    @Override
    public AccountingPeriodModel mapRow(ResultSet res) {
        AccountingPeriodModel model = null;
        try {
            model = new AccountingPeriodModel();
            model.setId(res.getInt("id"));
            model.setName(res.getString("name"));
            model.setStartDate(res.getDate("startDate"));
            model.setEndDate(res.getDate("endDate"));
            model.setAccountingPeriodType(new AccountingPeriodTypeModel(res.getString("accountingPeriodTypeId")));
        } catch (SQLException ex) {
            Logger.getLogger(AccountingPeriodMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
