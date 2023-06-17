/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.util.List;
import models.ActionModel;

/**
 *
 * @author tinhlam
 */
public interface ActionDAO {

    public List<ActionModel> findAll();

    public List<ActionModel> findByResourceId(String id);

    public List<ActionModel> findByIdAndResourceId(String id, String resourceId);

}
