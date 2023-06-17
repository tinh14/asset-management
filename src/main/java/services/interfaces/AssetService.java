/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.AssetModel;

/**
 *
 * @author tinhlam
 */
public interface AssetService {

    public List<AssetModel> findAll();

    public List<AssetModel> findById(int id);

    public List<AssetModel> findByName(String name);

    public List<AssetModel> findByWeight(int weight);
    
    public List<AssetModel> findByAssetNameAndDepartmentId(String name, int id);
    
    public List<AssetModel> findByDepartmentId(int id);
    
    public ResponseMessage create(AssetModel asset);

    public ResponseMessage update(AssetModel asset);

}
