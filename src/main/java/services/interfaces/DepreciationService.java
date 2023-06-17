/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.DepreciationModel;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public interface DepreciationService {
    public List<DepreciationModel> findAll();
    public List<DepreciationModel> findById(int id);
    public List<DepreciationModel> findByAccountingPeriodName(String name);
    public ResponseMessage create(DepreciationModel model);
}
