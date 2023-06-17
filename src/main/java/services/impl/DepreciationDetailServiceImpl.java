/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.AssetDAO;
import daos.interfaces.AssetTypeDAO;
import daos.interfaces.DepreciationDAO;
import daos.interfaces.DepreciationDetailDAO;
import java.util.List;
import javax.inject.Inject;
import models.AccountingPeriodModel;
import models.AssetModel;
import models.DepreciationDetailModel;
import models.DepreciationModel;
import services.interfaces.DepreciationDetailService;

/**
 *
 * @author tinhlam
 */
public class DepreciationDetailServiceImpl implements DepreciationDetailService {

    @Inject
    private DepreciationDAO depreciationDAO;

    @Inject
    private DepreciationDetailDAO depreciationDetailDAO;

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private AssetTypeDAO assetTypeDAO;

    @Override
    public List<DepreciationDetailModel> findByAccoutingPeriod(AccountingPeriodModel accountingPeriod) {
        List<DepreciationDetailModel> list = depreciationDetailDAO.findByAccountingPeriod(accountingPeriod);
//        for (DepreciationDetailModel detail : list) {
//            List<DepreciationModel> nearestDepreciation = depreciationDAO.findNearestDepreciationByAccountingPeriodTypeId(accountingPeriod.getAccountingPeriodType().getId());
//            int prevAcu = 0;
//            if (!nearestDepreciation.isEmpty()) {
//                int nearestDepreciationId = nearestDepreciation.get(0).getId();
//                List<DepreciationDetailModel> list2 = depreciationDetailDAO.findByDepreciationIdAndAssetId(nearestDepreciationId, detail.getAsset().getId());
//                prevAcu = list2.get(0).getAccumulatedDepreciation();
//            }
//            detail.setAccumulatedDepreciation(prevAcu + detail.getPeriodDepreciationExpense());
//            detail.setRemainingValue(detail.getTotalValue() - detail.getAccumulatedDepreciation());
//            AssetModel asset = assetDAO.findById(detail.getAsset().getId()).get(0);
//            detail.setDepreciationPeriod(assetTypeDAO.findById(asset.getAssetType().getId()).get(0).getDepreciationPeriod());
//            detail.setPercentageDepreciation(asset.getPercentageDepreciation());
//            detail.setAsset(asset);
//        }
        for (DepreciationDetailModel detail : list) {
            detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
        }
        return list;
    }

}
