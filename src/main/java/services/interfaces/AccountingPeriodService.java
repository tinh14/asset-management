/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.AccountingPeriodModel;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public interface AccountingPeriodService {
    public List<AccountingPeriodModel> findAll();
    public List<AccountingPeriodModel> findById(int id);
    public List<AccountingPeriodModel> findByName(String name);
    public ResponseMessage create(AccountingPeriodModel accountingPeriod);
}
