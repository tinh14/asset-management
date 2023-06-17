/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.ActionModel;

/**
 *
 * @author tinhlam
 */
public interface ActionService {

    public List<ActionModel> findAll();

    public List<ActionModel> findByIdAndResourceId(String id, String resourceId);

}
