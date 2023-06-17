/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AccountingPeriodModel;

/**
 *
 * @author tinhlam
 */
public interface AccountingPeriodDAO {

    public List<AccountingPeriodModel> findAll();

    public List<AccountingPeriodModel> findById(int id);

    public List<AccountingPeriodModel> findByName(String name);
    
    public int create(Connection connection, AccountingPeriodModel accountingPeriod) throws SQLException;

}
