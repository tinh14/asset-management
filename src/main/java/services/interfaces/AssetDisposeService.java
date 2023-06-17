/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.AssetDisposeDetailModel;
import models.AssetDisposeModel;

/**
 *
 * @author tinhlam
 */
public interface AssetDisposeService {

    public List<AssetDisposeModel> findAll();

    public List<AssetDisposeModel> findById(int id);

    public List<Integer> getQuantities(int departmentId, List<AssetDisposeDetailModel> details);

    public ResponseMessage create(AssetDisposeModel assetDispose);

    public ResponseMessage update(AssetDisposeModel assetDispose);

    public ResponseMessage deleteById(int id);
}
