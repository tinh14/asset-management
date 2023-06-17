/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.AssetTransferDetailModel;
import models.AssetTransferModel;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public interface AssetTransferService {
    public List<AssetTransferModel> findAll(); 
    public List<Integer> getQuantities(List<AssetTransferDetailModel> details); 
    public List<AssetTransferModel> findById(int id); 
    public ResponseMessage create(AssetTransferModel assetTransfer);
    public ResponseMessage update(AssetTransferModel assetTransfer);
    public ResponseMessage deleteById(int id);
}
