/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.AccountingPeriodTypeModel;

/**
 *
 * @author tinhlam
 */
public interface AccountingPeriodTypeService {
    public List<AccountingPeriodTypeModel> findAll();
    public List<AccountingPeriodTypeModel> findByName(String name);
}
