/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.AssetDisposeDetailDAO;
import daos.interfaces.AssetTransferDetailDAO;
import daos.interfaces.InventoryTransactionDetailDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import models.AssetDisposeDetailModel;
import models.AssetModel;
import models.AssetTransferDetailModel;
import models.InventoryTransactionDetailModel;
import models.viewmodels.AssetWithQuantityModel;
import services.interfaces.InventoryService;

/**
 *
 * @author tinhlam
 */
public class InventoryServiceImpl implements InventoryService {

    @Inject
    private InventoryTransactionDetailDAO inventoryTransactionDetailDAO;

    @Inject
    private AssetTransferDetailDAO assetTransferDetailDAO;

    @Inject
    private AssetDisposeDetailDAO assetDisposeDetailDAO;

    @Override
    public int getQuantityFormAssetId(int id) {
        int quantity = 0;
        List<InventoryTransactionDetailModel> inventoryTransactionDetailList = inventoryTransactionDetailDAO.findByAssetIdWithGroupByAssetId(id);
        if (!inventoryTransactionDetailList.isEmpty()) {
            quantity = inventoryTransactionDetailList.get(0).getQuantity();
        }

        List<AssetTransferDetailModel> assetTransferDetailList = assetTransferDetailDAO.findByAssetIdWithGroupByAssetId(id);
        if (!assetTransferDetailList.isEmpty()) {
            quantity -= assetTransferDetailList.get(0).getQuantity();
        }

        return quantity;
    }

    @Override
    public int getDepartmentalAssetQuantities(int assetId, int departmentId) {
        int quantity = 0;
        List<AssetTransferDetailModel> assetTransferDetailList = assetTransferDetailDAO.findByAssetIdAndDepartmentId(assetId, departmentId);
        if (!assetTransferDetailList.isEmpty()) {
            quantity = assetTransferDetailList.get(0).getQuantity();
        }

        List<AssetDisposeDetailModel> assetDisposeDetailList = assetDisposeDetailDAO.findByAssetIdAndDeparmentId(assetId, departmentId);
        if (!assetDisposeDetailList.isEmpty()) {
            quantity -= assetDisposeDetailList.get(0).getQuantity();
        }
        return quantity;
    }
    
    @Override
    public List<AssetWithQuantityModel> findDepartmentalAssetWithQuantity(int departmentId, List<AssetModel> assets){
        List<AssetWithQuantityModel> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (AssetModel asset : assets) {
            AssetWithQuantityModel model = new AssetWithQuantityModel();
            model.setAsset(asset);
            model.setQuantity(0);

            map.put(asset.getId(), getDepartmentalAssetQuantities(asset.getId(), departmentId));
            
            AssetWithQuantityModel awq = new AssetWithQuantityModel();
            awq.setAsset(asset);
            list.add(awq);
        }

        for (AssetWithQuantityModel model : list) {
            if (map.containsKey(model.getAsset().getId())) {
                model.setQuantity(map.get(model.getAsset().getId()));
            }
        }

        return list;
    } 

    @Override
    public List<AssetWithQuantityModel> findAssetWithQuantity(List<AssetModel> assets) {
        List<AssetWithQuantityModel> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (AssetModel asset : assets) {
            AssetWithQuantityModel model = new AssetWithQuantityModel();
            model.setAsset(asset);
            model.setQuantity(0);

            map.put(asset.getId(), getQuantityFormAssetId(asset.getId()));
            
            AssetWithQuantityModel awq = new AssetWithQuantityModel();
            awq.setAsset(asset);
            list.add(awq);
        }

        for (AssetWithQuantityModel model : list) {
            if (map.containsKey(model.getAsset().getId())) {
                model.setQuantity(map.get(model.getAsset().getId()));
            }
        }

        return list;
    }

}
