/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AccountModel;

/**
 *
 * @author tinhlam
 */
public class AccountMapper implements RowMapper<AccountModel> {

    @Override
    public AccountModel mapRow(ResultSet res) {
        AccountModel account = null;

        try {
            account = new AccountModel();
            account.setUsername(res.getString("username"));
            account.setPassword(res.getString("password"));
            account.setStatus(res.getBoolean("status"));
        } catch (SQLException ex) {
            Logger.getLogger(AccountMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }

}
