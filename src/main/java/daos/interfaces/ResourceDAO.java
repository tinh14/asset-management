/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.util.List;
import models.ResourceModel;

/**
 *
 * @author tinhlam
 */
public interface ResourceDAO {
    public List<ResourceModel> findAll();
}
