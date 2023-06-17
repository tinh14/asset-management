/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.AssetModel;
import models.viewmodels.AssetWithQuantityModel;

/**
 *
 * @author tinhlam
 */
public interface InventoryService {

    public int getQuantityFormAssetId(int id);

    public int getDepartmentalAssetQuantities(int assetId, int departmentId);

    public List<AssetWithQuantityModel> findDepartmentalAssetWithQuantity(int departmentId, List<AssetModel> assets);

    public List<AssetWithQuantityModel> findAssetWithQuantity(List<AssetModel> asset);
}
