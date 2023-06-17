/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AccountingPeriodTypeModel;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodTypeMapper implements RowMapper<AccountingPeriodTypeModel> {

    @Override
    public AccountingPeriodTypeModel mapRow(ResultSet res) {
        AccountingPeriodTypeModel model = null;
        try {
            model = new AccountingPeriodTypeModel();
            model.setId(res.getString("id"));
            model.setName(res.getString("name"));
            model.setNumberOfMonths(res.getInt("numberOfMonths"));
        } catch (SQLException ex) {
            Logger.getLogger(AccountingPeriodTypeMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
