/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.AccountingPeriodTypeDAO;
import java.util.List;
import javax.inject.Inject;
import models.AccountingPeriodTypeModel;
import services.interfaces.AccountingPeriodTypeService;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodTypeServiceImpl implements AccountingPeriodTypeService {

    @Inject
    private AccountingPeriodTypeDAO accountingPeriodTypeDAO;

    @Override
    public List<AccountingPeriodTypeModel> findAll() {
        return accountingPeriodTypeDAO.findAll();
    }

    @Override
    public List<AccountingPeriodTypeModel> findByName(String name) {
        return accountingPeriodTypeDAO.findByName(name);
    }
}
