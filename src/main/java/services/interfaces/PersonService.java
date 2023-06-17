/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.PersonModel;

/**
 *
 * @author tinhlam
 */
public interface PersonService {
    public List<PersonModel> findByAccountUsername(String username);
}
