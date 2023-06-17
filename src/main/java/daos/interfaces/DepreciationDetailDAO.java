/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.AccountingPeriodModel;
import models.DepreciationDetailModel;

/**
 *
 * @author tinhlam
 */
public interface DepreciationDetailDAO {

    public List<DepreciationDetailModel> findByDepreciationId(int id);

    public List<DepreciationDetailModel> findByAccountingPeriod(AccountingPeriodModel model);

//    public List<DepreciationDetailModel> findByDepreciationIdAndAssetId(int depreciationId, int assetId);
    
    public void create(Connection connection, DepreciationDetailModel model) throws SQLException;
}
