/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.DepartmentModel;

/**
 *
 * @author tinhlam
 */
public interface DepartmentService {
    public List<DepartmentModel> findAll();
    public List<DepartmentModel> findById(int id);
    public List<DepartmentModel> findByName(String name);
    public ResponseMessage create(DepartmentModel department);
    public ResponseMessage update(DepartmentModel department);
    public ResponseMessage delete(int id);
}
