/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import javax.enterprise.inject.Default;
import models.SupplierModel;

/**
 *
 * @author tinhlam
 */
@Default
public interface SupplierService {
    public List<SupplierModel> findAll();
    public List<SupplierModel> findById(int id);
    public List<SupplierModel> findByName(String name);
    public ResponseMessage create(SupplierModel supplier);
    public ResponseMessage update(SupplierModel supplier);
    
}
