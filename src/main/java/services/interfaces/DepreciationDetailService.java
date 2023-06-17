/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.AccountingPeriodModel;
import models.DepreciationDetailModel;
import models.DepreciationModel;

/**
 *
 * @author tinhlam
 */
public interface DepreciationDetailService {

    public List<DepreciationDetailModel> findByAccoutingPeriod(AccountingPeriodModel accountingPeriod);

}
