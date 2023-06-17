/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.DepreciationMethodModel;

/**
 *
 * @author tinhlam
 */
public interface DepreciationMethodService {
    public List<DepreciationMethodModel> findAll();
    public List<DepreciationMethodModel> findById(String id);
    public List<DepreciationMethodModel> findByName(String name);
}
