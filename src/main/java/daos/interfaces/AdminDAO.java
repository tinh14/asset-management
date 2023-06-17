/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.util.List;
import models.AdminModel;

/**
 *
 * @author tinhlam
 */
public interface AdminDAO{
    public List<AdminModel> findByPersonId(int personId);
}
