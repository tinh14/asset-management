/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.util.List;
import models.AccountingPeriodTypeModel;

/**
 *
 * @author tinhlam
 */
public interface AccountingPeriodTypeDAO {

    public List<AccountingPeriodTypeModel> findAll();
    
    public List<AccountingPeriodTypeModel> findById(String id);

    public List<AccountingPeriodTypeModel> findByName(String name);
    
}
