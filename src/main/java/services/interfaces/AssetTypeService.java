/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.AssetTypeModel;

/**
 *
 * @author tinhlam
 */
public interface AssetTypeService {
    public List<AssetTypeModel> findAll();
    public List<AssetTypeModel> findById(int id);
    public List<AssetTypeModel> findByName(String name);
    public List<AssetTypeModel> findByDepreciationPeriod(int depreciationPeriod);
    public ResponseMessage create(AssetTypeModel assetType);
    public ResponseMessage update(AssetTypeModel assetType);
}
